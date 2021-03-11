/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.io.Serializable;



/**
 *
 * @author franciscom
 */
public class EquipoTerritorio implements Serializable {
    private int territorioId;
    private String nombre;
    private boolean activo;
    private int equipoId;
    private String equipo;
    /*
    public EquipoTerritorio(int territorioId, String nombre, boolean activo, int equipoId, String equipo){
        this.territorioId = territorioId;
        this.nombre = nombre;
        this.activo = activo;
        this.equipoId = equipoId;
        this.equipo = equipo;
    }*/

    public EquipoTerritorio() {
    }
    
    public int getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(int territorioId) {
        this.territorioId = territorioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    
}
