/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Formato;

import java.util.*;

/**
 *
 * @author jdominguez
 */
public class DAOFormatosImpl implements DAOFormatos{
    private DatabaseStoredProc spFormatosSel;
    private DatabaseStoredProc spFormatosUps;
    private DatabaseStoredProc spFormatosDel;

    @Override
    public List<Map<String, Object>> listaFormatos(int formatosId) {
        List<Map<String, Object>> response;
        Map<String, Object> inputs = new TreeMap();
        inputs.put("FormatosId", formatosId);
        response = getSpFormatosSel().execSP(inputs);
        return response;
    }

    @Override
    public HashMap<String, Object> borrarFormato(int formatosId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, Object> updateFormato(int formatosId, String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DatabaseStoredProc getSpFormatosSel() {
        return spFormatosSel;
    }

    public void setSpFormatosSel(DatabaseStoredProc spFormatosSel) {
        this.spFormatosSel = spFormatosSel;
    }

    public DatabaseStoredProc getSpFormatosUps() {
        return spFormatosUps;
    }

    public void setSpFormatosUps(DatabaseStoredProc spFormatosUps) {
        this.spFormatosUps = spFormatosUps;
    }

    public DatabaseStoredProc getSpFormatosDel() {
        return spFormatosDel;
    }

    public void setSpFormatosDel(DatabaseStoredProc spFormatosDel) {
        this.spFormatosDel = spFormatosDel;
    }


    @Override
    public void crear(Formato o) {

    }

    @Override
    public void editar(Formato o) {

    }

    @Override
    public String eliminar(Formato o) {
        return null;
    }

    @Override
    public Formato getById(Integer id) {
        List<Formato> listado = new ArrayList<>();
        Formato o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("FormatosId", id);
        Map out = spFormatosSel.execute(inputs);
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
    public List<Formato> getAll() {
        List<Formato> listado = new ArrayList<>();
        Formato o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("FormatosId", null);
        Map out = spFormatosSel.execute(inputs);
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
    public List<Formato> getAllActivated() {
        List<Formato> listado = new ArrayList<>();
        Formato o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("FormatosId", null);
        Map out = spFormatosSel.execute(inputs);
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
    public Formato genericObject(Object args) {
        Formato o = new Formato();
        Map element = (Map)args;
        o.setId(Integer.valueOf(element.get("FormatosId").toString()));
        o.setNombre(element.get("Nombre").toString());

        return o;
    }
}
