package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.OpcionCruzada;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by christian on 23/12/2014.
 */
public class DAOOpcionCruzadaImpl implements DAOOpcionCruzada {

    private DatabaseStoredProc spOpcionesCruzadasSel;
    private DatabaseStoredProc spOpcionesCruzadasDel;
    private DatabaseStoredProc spOpcionesCruzadasUps;

    public DatabaseStoredProc getSpOpcionesCruzadasSel() {
        return spOpcionesCruzadasSel;
    }

    public void setSpOpcionesCruzadasSel(DatabaseStoredProc spOpcionesCruzadasSel) {
        this.spOpcionesCruzadasSel = spOpcionesCruzadasSel;
    }

    public DatabaseStoredProc getSpOpcionesCruzadasDel() {
        return spOpcionesCruzadasDel;
    }

    public void setSpOpcionesCruzadasDel(DatabaseStoredProc spOpcionesCruzadasDel) {
        this.spOpcionesCruzadasDel = spOpcionesCruzadasDel;
    }

    public DatabaseStoredProc getSpOpcionesCruzadasUps() {
        return spOpcionesCruzadasUps;
    }

    public void setSpOpcionesCruzadasUps(DatabaseStoredProc spOpcionesCruzadasUps) {
        this.spOpcionesCruzadasUps = spOpcionesCruzadasUps;
    }


    @Override
    public void crear(OpcionCruzada o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesCruzadasOID", null);
        inputs.put("ListasOID", o.getOidLista());
        inputs.put("Valor", o.getValor());
        inputs.put("Variable",o.getVariable());
        Map out = spOpcionesCruzadasUps.execute(inputs);
    }

    @Override
    public void editar(OpcionCruzada o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesCruzadasOID", o.getOid());
        inputs.put("ListasOID", o.getOidLista());
        inputs.put("Valor", o.getValor());
        inputs.put("Variable",o.getVariable());
        Map out = spOpcionesCruzadasUps.execute(inputs);
    }

    @Override
    public String eliminar(OpcionCruzada o) {
        String resultado = "Error al Borrar";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("OpcionesCruzadasOID", o.getOid());
        try {
            Map out = spOpcionesCruzadasDel.execute(inputs);
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
    public OpcionCruzada getById(Integer id) {
        return null;
    }

    @Override
    public List<OpcionCruzada> getAll() {
        return null;
    }

    @Override
    public List<OpcionCruzada> getAllActivated() {
        return null;
    }

    @Override
    public List<OpcionCruzada> getByOIDLista(String oid) {
        List<OpcionCruzada> listado = new ArrayList<>();
        OpcionCruzada o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ListasOID", oid);
        Map out = spOpcionesCruzadasSel.execute(inputs);
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
    public void grabar(OpcionCruzada o) {

    }

    @Override
    public OpcionCruzada genericObject(Object args) {
        Map element = (Map) args;
        OpcionCruzada o = new OpcionCruzada();

        o.setOid(Utileria.validarStringNull(element.get("OpcionesCruzadasOID")));
        o.setOidLista(Utileria.validarStringNull(element.get("ListasOID")));
        o.setValor(Utileria.validarStringNull(element.get("Valor")));
        o.setVariable(Utileria.validarStringNull(element.get("Variable")));
        o.setActiva(Boolean.valueOf(element.get("Activa").toString()));

        return o;
    }
}
