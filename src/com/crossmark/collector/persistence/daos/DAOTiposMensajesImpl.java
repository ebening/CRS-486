package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.TipoMensaje;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by christian on 11/12/2014.
 */
public class DAOTiposMensajesImpl implements DAOTiposMensajes {

    private DatabaseStoredProc spTipoMensajesSel;
    private DatabaseStoredProc spTipoMensajesUps;
    private DatabaseStoredProc spTipoMensajesDel;


    public DatabaseStoredProc getSpTipoMensajesSel() {
        return spTipoMensajesSel;
    }

    public void setSpTipoMensajesSel(DatabaseStoredProc spTipoMensajesSel) {
        this.spTipoMensajesSel = spTipoMensajesSel;
    }

    public DatabaseStoredProc getSpTipoMensajesDel() {
        return spTipoMensajesDel;
    }

    public void setSpTipoMensajesDel(DatabaseStoredProc spTipoMensajesDel) {
        this.spTipoMensajesDel = spTipoMensajesDel;
    }

    public DatabaseStoredProc getSpTipoMensajesUps() {
        return spTipoMensajesUps;
    }

    public void setSpTipoMensajesUps(DatabaseStoredProc spTipoMensajesUps) {
        this.spTipoMensajesUps = spTipoMensajesUps;
    }

    @Override
    public void crear(TipoMensaje o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", 0);
        inputs.put("Nombre", o.getNombre());
        spTipoMensajesUps.execute(inputs);
    }

    @Override
    public void editar(TipoMensaje o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", o.getId());
        inputs.put("Nombre", o.getNombre());
        spTipoMensajesUps.execute(inputs);
    }

    @Override
    public String eliminar(TipoMensaje o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", o.getId());
        Map out = spTipoMensajesDel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }

    @Override
    public TipoMensaje getById(Integer id) {
        List<TipoMensaje> listado = new ArrayList<>();
        TipoMensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", null);
        Map out = spTipoMensajesSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<TipoMensaje> getAll() {
        List<TipoMensaje> listado = new ArrayList<>();
        TipoMensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", null);
        Map out = spTipoMensajesSel.execute(inputs);
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
    public List<TipoMensaje> getAllActivated() {
        List<TipoMensaje> listado = new ArrayList<>();
        TipoMensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TipoMensajesId", null);
        Map out = spTipoMensajesSel.execute(inputs);
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
    public TipoMensaje genericObject(Object args) {
        Map element = (Map) args;
        TipoMensaje o = new TipoMensaje();

        o.setId(Integer.valueOf(element.get("TipoMensajesId").toString()));
        o.setNombre(element.get("Nombre").toString());

        return o;
    }


}
