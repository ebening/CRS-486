package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Motivo;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by christian on 15/12/2014.
 */
public class DAOMotivosImpl implements DAOMotivos {

    private DatabaseStoredProc spMotivosSel;

    private DatabaseStoredProc spMotivosUps;

    private DatabaseStoredProc spMotivosDel;

    public DatabaseStoredProc getSpMotivosDel() {
        return spMotivosDel;
    }

    public DatabaseStoredProc getSpMotivosSel() {
        return spMotivosSel;
    }

    public DatabaseStoredProc getSpMotivosUps() {
        return spMotivosUps;
    }

    public void setSpMotivosDel(DatabaseStoredProc spMotivosDel) {
        this.spMotivosDel = spMotivosDel;
    }

    public void setSpMotivosSel(DatabaseStoredProc spMotivosSel) {
        this.spMotivosSel = spMotivosSel;
    }

    public void setSpMotivosUps(DatabaseStoredProc spMotivosUps) {
        this.spMotivosUps = spMotivosUps;
    }

    @Override
    public void crear(Motivo o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", 0);
        inputs.put("Nombre", o.getNombre());
        spMotivosUps.execute(inputs);
    }

    @Override
    public void editar(Motivo o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", o.getId());
        inputs.put("Nombre", o.getNombre());
        spMotivosUps.execute(inputs);
    }

    @Override
    public String eliminar(Motivo o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", o.getId());
        Map out = spMotivosDel.execute(inputs);
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
    public Motivo getById(Integer id) {
        List<Motivo> listado = new ArrayList<>();
        Motivo Motivo;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", id);
        Map out = spMotivosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Motivo = genericObject(object);
                listado.add(Motivo);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Motivo> getAll() {
        List<Motivo> listado = new ArrayList<>();
        Motivo Motivo;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", null);
        Map out = spMotivosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Motivo = genericObject(object);
                listado.add(Motivo);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Motivo> getAllActivated() {
        List<Motivo> listado = new ArrayList<>();
        Motivo o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MotivosId", null);
        Map out = spMotivosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                if (o.isActivo()) {
                    listado.add(o);
                }

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public Motivo genericObject(Object arg) {
        Map element = (Map) arg;
        Motivo nvo = new Motivo();

        nvo.setId(Integer.valueOf(element.get("MotivosId").toString()));
        nvo.setNombre(element.get("Nombre").toString());
        nvo.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        return nvo;
    }
}
