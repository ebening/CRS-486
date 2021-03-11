/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Puestos;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOPuestosImpl implements DAOPuestos{
    private DatabaseStoredProc storePuestosSel;

    @Override
    public Puestos getPuestoById(Integer id) {
        List<Puestos> listado = new ArrayList<>();
        Puestos o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("PuestosId",id);
        Map out = getStorePuestosSel().execute(inputs);
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
    public List<Puestos> getListLuestos(Integer puestosId) {
        List<Puestos> listado = new ArrayList<>();
        Puestos o;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("PuestosId", puestosId);
        Map out = getStorePuestosSel().execute(inputs);
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
    public Puestos genericObject(Object args) {
        Puestos puesto = new Puestos();
        Map element = (Map)args;
        puesto.setPuestosId(Integer.valueOf(element.get("PuestosId").toString()));
        puesto.setNombre(element.get("Nombre").toString());
        return puesto;
    }

    public DatabaseStoredProc getStorePuestosSel() {
        return storePuestosSel;
    }

    public void setStorePuestosSel(DatabaseStoredProc storePuestosSel) {
        this.storePuestosSel = storePuestosSel;
    }
    
    
    
}
