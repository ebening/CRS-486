/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOCiudadesImpl implements DAOCiudades{
    DatabaseStoredProc spCiudades;
    
    @Override
    public List getCiudades() {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("EstadosId", null);
        return getSpCiudades().execSP(inputs);
    }
    

    /**
     * @return the spCiudades
     */
    public DatabaseStoredProc getSpCiudades() {
        return spCiudades;
    }

    /**
     * @param spCiudades the spCiudades to set
     */
    public void setSpCiudades(DatabaseStoredProc spCiudades) {
        this.spCiudades = spCiudades;
    }

    @Override
    public List getCiudadByEstado(int ciudadesId, int estadoId, String nombre, boolean activo) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("CiudadesId", ciudadesId);
        inputs.put("EstadosId", estadoId);
        inputs.put("Nombre", nombre);
        inputs.put("Activo", activo);
        return getSpCiudades().execSP(inputs);
    }

    @Override
    public List<Ciudad> getCiudadesByEstado(Integer idEstado) {
        Utileria.logger(getClass()).info(" Estoy en getCiudadesByEstado");
        List<Ciudad> listado = new ArrayList<>();
        Ciudad ciudad;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CiudadesId", 0);
        inputs.put("EstadosId", idEstado);
        inputs.put("Nombre", null);
        inputs.put("Activo", true);
        Map out =getSpCiudades().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                ciudad = genericObject(object);
                if (ciudad.isActivo()) {
                    listado.add(ciudad);
                }
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public Ciudad genericObject(Object args) {
        Ciudad ciudad = new Ciudad();
        Map element = (Map)args;
        ciudad.setId(Integer.valueOf(String.valueOf(element.get("CiudadesId"))));
        ciudad.setNombre(String.valueOf(element.get("Nombre")));
        ciudad.getEstado().setId(Integer.valueOf(String.valueOf(element.get("EstadosId"))));
        ciudad.setActivo(Boolean.valueOf(String.valueOf(element.get("Activo"))));

        return ciudad;
    }

    @Override
    public void crear(Ciudad o) {

    }

    @Override
    public void editar(Ciudad o) {

    }

    @Override
    public String eliminar(Ciudad o) {
        return null;
    }

    @Override
    public Ciudad getById(Integer id) {
        Utileria.logger(getClass()).info(" Estoy en getCiudadesByEstado");
        List<Ciudad> listado = new ArrayList<>();
        Ciudad ciudad;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CiudadesId", id);
        inputs.put("EstadosId", 0);
        inputs.put("Nombre", null);
        inputs.put("Activo", true);
        Map out =getSpCiudades().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                ciudad = genericObject(object);
                    listado.add(ciudad);
                }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Ciudad> getAll() {
        Utileria.logger(getClass()).info(" Estoy en getCiudadesByEstado");
        List<Ciudad> listado = new ArrayList<>();
        Ciudad ciudad;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CiudadesId", 0);
        inputs.put("EstadosId", 0);
        inputs.put("Nombre", null);
        inputs.put("Activo", true);
        Map out =getSpCiudades().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                ciudad = genericObject(object);
                listado.add(ciudad);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Ciudad> getAllActivated() {
        Utileria.logger(getClass()).info(" Estoy en getCiudadesByEstado");
        List<Ciudad> listado = new ArrayList<>();
        Ciudad ciudad;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CiudadesId", 0);
        inputs.put("EstadosId", 0);
        inputs.put("Nombre", null);
        inputs.put("Activo", true);
        Map out =getSpCiudades().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                ciudad = genericObject(object);
                if (ciudad.isActivo()) {
                    listado.add(ciudad);
                }
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }
}
