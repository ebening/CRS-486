package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.OpcionPlana;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by christian on 29/12/2014.
 */
public class DAOOpcionPlanaImpl implements DAOOpcionPlana {

    private DatabaseStoredProc spOpcionesPlanaSel;
    private DatabaseStoredProc spOpcionesPlanasDel;
    private DatabaseStoredProc spOpcionesPlanasUps;

    public DatabaseStoredProc getSpOpcionesPlanaSel() {
        return spOpcionesPlanaSel;
    }

    public void setSpOpcionesPlanaSel(DatabaseStoredProc spOpcionesPlanaSel) {
        this.spOpcionesPlanaSel = spOpcionesPlanaSel;
    }

    public DatabaseStoredProc getSpOpcionesPlanasDel() {
        return spOpcionesPlanasDel;
    }

    public void setSpOpcionesPlanasDel(DatabaseStoredProc spOpcionesPlanasDel) {
        this.spOpcionesPlanasDel = spOpcionesPlanasDel;
    }

    public DatabaseStoredProc getSpOpcionesPlanasUps() {
        return spOpcionesPlanasUps;
    }

    public void setSpOpcionesPlanasUps(DatabaseStoredProc spOpcionesPlanasUps) {
        this.spOpcionesPlanasUps = spOpcionesPlanasUps;
    }

    @Override
    public void crear(OpcionPlana o) {
        Utileria.logger(getClass()).info("Llego a crearOpcionPlana");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesOID",null);
        inputs.put("ListasOID", o.getOidLista());
        inputs.put("Texto", o.getTexto());
        inputs.put("Valor", o.getValor());
        inputs.put("Clave", o.getClave());
        inputs.put("Orden", o.getOrden());
        inputs.put("Tag", o.getTag());
        inputs.put("CodigoBarra", o.getCodigoBarra());
        inputs.put("Visible", o.isVisible());
        inputs.put("Enabled", o.isEnabled());
        spOpcionesPlanasUps.execute(inputs);
    }

    @Override
    public void editar(OpcionPlana o) {
        Utileria.logger(getClass()).info("Llego a editarOpcionPlana");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesOID", o.getOid());
        inputs.put("ListasOID", o.getOidLista());
        inputs.put("Texto", o.getTexto());
        inputs.put("Valor", o.getValor());
        inputs.put("Clave", o.getClave());
        inputs.put("Orden", o.getOrden());
        inputs.put("Tag", o.getTag());
        inputs.put("CodigoBarra", o.getCodigoBarra());
        inputs.put("Visible", o.isVisible());
        inputs.put("Enabled", o.isEnabled());
        spOpcionesPlanasUps.execute(inputs);
    }

    @Override
    public String eliminar(OpcionPlana o) {
        String resultado = "Error al Borrar";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesCruzadasOID", o.getOid());
        try {
            Map out = spOpcionesPlanasDel.execute(inputs);
            return "0";
            //List list = (List) out.get("#result-set-1");
            // for (Object object : list) {
            //   Map element = (Map) object;
            // resultado = element.get("").toString();
            // }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
            return resultado;
                 }
    }

    @Override
    public OpcionPlana getById(Integer id) {
        return null;
    }

    @Override
    public List<OpcionPlana> getAll() {
        return null;
    }

    @Override
    public List<OpcionPlana> getAllActivated() {
        return null;
    }


    @Override
    public List<OpcionPlana> getByOIDLista(String oid) {
            List<OpcionPlana> listado = new ArrayList<>();
            OpcionPlana o;
            Map<String, Object> inputs = new TreeMap<>();
            inputs.put("ListasOID", oid);
            Map out = spOpcionesPlanaSel.execute(inputs);
            try {
                List list = (List) out.get("#result-set-1");
                for (Object object : list) {
                    o = genericObject(object);
                    listado.add(o);
                }
            } catch (NumberFormatException e) {
                Utileria.logger(getClass()).info(e.getMessage());
            }
            return listado;
    }

    @Override
    public OpcionPlana genericObject(Object args) {
            Map element = (Map) args;
            OpcionPlana o = new OpcionPlana();

            o.setOid(Utileria.validarStringNull(element.get("OpcionesOID")));
            o.setTag(Utileria.validarStringNull(element.get("Tag")));
            o.setValor(Utileria.validarStringNull(element.get("Valor")));
            o.setClave(Utileria.validarStringNull(element.get("Clave")));
            o.setTexto(Utileria.validarStringNull(element.get("Texto")));
            o.setOrden(Integer.valueOf(element.get("Orden").toString()));
            o.setActiva(Utileria.validarBooleanNull(element.get("Activa")));
            o.setVisible(Utileria.validarBooleanNull(element.get("Visible")));
            o.setEnabled(Utileria.validarBooleanNull(element.get("Enabled")));
            o.setOidLista(Utileria.validarStringNull(element.get("ListasOID")));
            o.setCodigoBarra(Utileria.validarStringNull(element.get("CodigoBarra")));

            return o;

        }
}
