/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author jdominguez
 */
public class DAOTerritoriosImpl implements DAOTerritorios, Serializable {

    private DatabaseStoredProc spTerritoriosSel;
    private DatabaseStoredProc spTerritoriosDel;
    private DatabaseStoredProc spTerritoriosUps;

    @Override

    public List<Map<String, Object>> listaTerritoriosNat(int TerritoriosId, String Nombre, boolean Activo) {
        List<Map<String, Object>> retorno;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", TerritoriosId == 0 ? null : TerritoriosId);
        inputs.put("Nombre", Nombre);
        inputs.put("Activo", Activo);
        retorno = getSpTerritoriosSel().execSP(inputs);
        return retorno;
    }

    @Override
    public String nombreTerritorio(int TerritorioId) {
        String nombre = "";
        List<Map<String, Object>> retorno;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", TerritorioId == 0 ? null : TerritorioId);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        retorno = getSpTerritoriosSel().execSP(inputs);
        for (Map m : retorno) {
            nombre = (String) m.get("Nombre");
        }
        return nombre;
    }

    public DatabaseStoredProc getSpTerritoriosSel() {
        return spTerritoriosSel;
    }

    public void setSpTerritoriosSel(DatabaseStoredProc spTerritoriosSel) {
        this.spTerritoriosSel = spTerritoriosSel;
    }

    public DatabaseStoredProc getSpTerritoriosDel() {
        return spTerritoriosDel;
    }

    public void setSpTerritoriosDel(DatabaseStoredProc spTerritoriosDel) {
        this.spTerritoriosDel = spTerritoriosDel;
    }

    public DatabaseStoredProc getSpTerritoriosUps() {
        return spTerritoriosUps;
    }

    public void setSpTerritoriosUps(DatabaseStoredProc spTerritoriosUps) {
        this.spTerritoriosUps = spTerritoriosUps;
    }

    @Override
    public void crear(Territorio o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", 0);
        inputs.put("Nombre", o.getNombre());
        spTerritoriosUps.execute(inputs);
    }

    @Override
    public void editar(Territorio o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", o.getId());
        inputs.put("Nombre", o.getNombre());
        spTerritoriosUps.execute(inputs);
    }

    @Override
    public String eliminar(Territorio territorio) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", territorio.getId());
        Map out = spTerritoriosDel.execute(inputs);
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
    public Territorio getById(Integer id) {
        List<Territorio> listado = new ArrayList<>();
        Territorio o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", id);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        Map out = spTerritoriosSel.execute(inputs);
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
    public List<Territorio> getAll() {
        List<Territorio> listado = new ArrayList<>();
        Territorio o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", null);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        Map out = spTerritoriosSel.execute(inputs);
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
    public List<Territorio> getAllActivated() {
        List<Territorio> listado = new ArrayList<>();
        Territorio o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TerritoriosId", null);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        Map out = spTerritoriosSel.execute(inputs);
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
    public Territorio genericObject(Object arg) {
        Map element = (Map) arg;
        Territorio o = new Territorio();

        o.setId(Integer.valueOf(element.get("TerritoriosId").toString()));
        o.setNombre(element.get("Nombre").toString());
        o.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        return o;
    }
}
