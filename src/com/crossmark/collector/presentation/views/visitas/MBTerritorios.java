/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author jdominguez
 */

@ManagedBean
public class MBTerritorios {
    private ServiceTerritorios serviceTerritorios;
    private List<Territorio> territorios;
    private int TerritoriosId = 0;
    private String nombreTerritorio;
    private boolean Activo = true;
    
    
    public void territoriosList(){
        territorios = parseTerritorioObject(getServiceTerritorios().listaTerritoriosNat(
                TerritoriosId, nombreTerritorio, true));
    }
    
    private List<Territorio> parseTerritorioObject (List<Map<String, Object>> l){
        List<Territorio> t = new ArrayList<>();
        for(Map m : l){
            t.add(new Territorio(Integer.valueOf(String.valueOf(m.get("TerritoriosId"))),
                    String.valueOf(m.get("Nombre")), Boolean.valueOf(String.valueOf(m.get("Activo")))));
        }
        return t;
    }

    /**
     * @return the serviceTerritorios
     */
    public ServiceTerritorios getServiceTerritorios() {
        return serviceTerritorios;
    }

    /**
     * @param serviceTerritorios the serviceTerritorios to set
     */
    public void setServiceTerritorios(ServiceTerritorios serviceTerritorios) {
        this.serviceTerritorios = serviceTerritorios;
    }

    /**
     * @return the territorios
     */
    public List<Territorio> getTerritorios() {
        return territorios;
    }

    /**
     * @param territorios the territorios to set
     */
    public void setTerritorios(List<Territorio> territorios) {
        this.territorios = territorios;
    }

    public int getTerritoriosId() {
        return TerritoriosId;
    }

    public void setTerritoriosId(int TerritoriosId) {
        this.TerritoriosId = TerritoriosId;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }
    
    
}
