/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.presentation.views.utils.SeccionParametro;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author franciscom
 */
public class DAOParametrosImpl implements DAOParametros, Serializable{
    private DatabaseStoredProc spParametros;
    private DatabaseStoredProc spParametrosUps;
    private DatabaseStoredProc spParametrosSeccion;
    
    
    @Override
    public List<Parametros> getParametrosByParams(Integer parametrosId,String nombre){
        
        List<Map<String, Object>> lstResult=null;
        List<Parametros> listado = new ArrayList<Parametros>();
        Parametros parametros;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ParametrosId", parametrosId);
        inputs.put("Nombre", nombre);
        
        lstResult=spParametros.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                parametros = new Parametros();

                parametros.setParametrosId(Integer.valueOf(String.valueOf(m.get("ParametrosId"))));
                parametros.setNombre(String.valueOf(m.get("Nombre")));
                parametros.setTipoValor(String.valueOf(m.get("TipoValor")));
                parametros.setValor(String.valueOf(m.get("Valor")));

                listado.add(parametros);
            }
        }else{
            //System.out.println("out is null ");
        }
        
        // TODO Auto-generated method stub
        return listado;
    }

    @Override
    public void crear(Parametros o) {

    }

    @Override
    public void editar(Parametros o) {

    }

    @Override
    public String eliminar(Parametros o) {
        return null;
    }

    @Override
    public Parametros getById(Integer id) {
        return null;
    }

    @Override
    public List<Parametros> getAll() {
        List<Parametros> listado = new ArrayList<>();
        Parametros o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ParametrosId", null);
        inputs.put("Nombre", null);
        Map out = spParametros.execute(inputs);
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
    public void guardar(Map<Integer, String> parametros) {
        Map<String, Object> inputs = new TreeMap<>();
        for (Map.Entry<Integer, String> entry : parametros.entrySet()) {
            inputs.put("ParametrosId", entry.getKey());
            inputs.put("Valor", entry.getValue());
            spParametrosUps.execute(inputs);
            inputs.clear();
        }
    }

    @Override
    public List<Parametros> getAllActivated() {
        return null;
    }

    @Override
    public Parametros genericObject(Object args) {
        Map element = (Map) args;
        Parametros o = new Parametros();

        o.setParametrosId(Integer.valueOf(element.get("ParametrosId").toString()));
        o.setNombre(element.get("Nombre").toString());
        o.setTitulo(element.get("Titulo").toString());
        o.setValor(Utileria.validarStringNull(element.get("Valor")));

        o.setTipoValor(element.get("TipoValor").toString());
        o.setVisible(Boolean.valueOf(element.get("Visible").toString()));
        o.getSeccionParametro().setId(Integer.valueOf(element.get("ParametrosSeccionID").toString()));
        o.getSeccionParametro().setNombre(element.get("ParametroSeccion").toString());

        return o;
    }

    @Override
    public List<SeccionParametro> getAllSecciones() {
        List<SeccionParametro> listado = new ArrayList<>();
        SeccionParametro o;
        Map out = spParametrosSeccion.execute();
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = new SeccionParametro();
                Map element = (Map) object;
                o.setId(Integer.valueOf(element.get("ParametrosSeccionID").toString()));
                o.setNombre(element.get("ParametroSeccion").toString());
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    public DatabaseStoredProc getSpParametros() {
        return spParametros;
    }

    public void setSpParametros(DatabaseStoredProc spParametros) {
        this.spParametros = spParametros;
    }

    public DatabaseStoredProc getSpParametrosUps() {
        return spParametrosUps;
    }

    public void setSpParametrosUps(DatabaseStoredProc spParametrosUps) {
        this.spParametrosUps = spParametrosUps;
    }

    public DatabaseStoredProc getSpParametrosSeccion() {
        return spParametrosSeccion;
    }

    public void setSpParametrosSeccion(DatabaseStoredProc spParametrosSeccion) {
        this.spParametrosSeccion = spParametrosSeccion;
    }
}
