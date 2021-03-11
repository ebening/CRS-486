/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOEstadosImpl implements DAOEstados{
    private DatabaseStoredProc spEstados;

    @Override
    public List getEstadosList() {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("EstadosId", "");
        return getSpEstados().execSP(inputs);
    }

    /**
     * @return the spEstados
     */
    public DatabaseStoredProc getSpEstados() {
        return spEstados;
    }

    /**
     * @param spEstados the spEstados to set
     */
    public void setSpEstados(DatabaseStoredProc spEstados) {
        this.spEstados = spEstados;
    }

    @Override
    public void crear(Estado o) {

    }

    @Override
    public void editar(Estado o) {

    }

    @Override
    public String eliminar(Estado o) {
        return null;
    }

    @Override
    public Estado getById(Integer id) {
        List<Estado> listado = new ArrayList<>();
        Estado o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("EstadosId",id);
        Map out = getSpEstados().execute(inputs);
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
        return listado.get(0);
    }

    @Override
    public List<Estado> getAll() {
        List<Estado> listado = new ArrayList<>();
        Estado o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("EstadosId", null);
        Map out = getSpEstados().execute(inputs);
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
    public List<Estado> getAllActivated() {
        List<Estado> listado = new ArrayList<>();
        Estado o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("EstadosId", null);
        Map out = getSpEstados().execute(inputs);
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
    public Estado genericObject(Object args) {
        Estado estado = new Estado();
        Map element = (Map)args;
        estado.setId(Integer.valueOf(element.get("EstadosId").toString()));
        estado.setNombre(element.get("Nombre").toString());
        estado.setClave(element.get("Clave").toString());
        estado.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        return estado;
    }
}
