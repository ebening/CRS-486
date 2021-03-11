/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Cadena;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOCadenaImpl implements DAOCadena{

    private DatabaseStoredProc spCadena;
    
    @Override
    public List getCadenas() {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("CadenasId", null);
        inputs.put("Nombre", null);
        return spCadena.execSP(inputs);
    }

    /**
     * @return the spCadena
     */
    public DatabaseStoredProc getSpCadena() {
        return spCadena;
    }

    /**
     * @param spCadena the spCadena to set
     */
    public void setSpCadena(DatabaseStoredProc spCadena) {
        this.spCadena = spCadena;
    }


    @Override
    public void crear(Cadena o) {

    }

    @Override
    public void editar(Cadena o) {

    }

    @Override
    public String eliminar(Cadena o) {
        return null;
    }

    @Override
    public Cadena getById(Integer id) {
        List<Cadena> listado = new ArrayList<>();
        Cadena o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("CadenasId", id);
        inputs.put("Nombre", null);
        Map out = spCadena.execute(inputs);
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
    public List<Cadena> getAll() {
        List<Cadena> listado = new ArrayList<>();
        Cadena o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("CadenasId", 0);
        inputs.put("Nombre", null);
        Map out = spCadena.execute(inputs);
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
    public List<Cadena> getAllActivated() {
        List<Cadena> listado = new ArrayList<>();
        Cadena o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("CadenasId", null);
        inputs.put("Nombre", null);
        Map out = spCadena.execute(inputs);
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
    public Cadena genericObject(Object args) {
        Cadena o = new Cadena();
        Map element = (Map)args;
        o.setId(Integer.valueOf(element.get("CadenasId").toString()));
        o.setNombre(element.get("Nombre").toString());

        return o;
    }
}
