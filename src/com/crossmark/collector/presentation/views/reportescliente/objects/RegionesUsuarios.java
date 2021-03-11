/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente.objects;

import java.io.Serializable;

/**
 *
 * @author Francisco Mora
 */
public class RegionesUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int regionesId;
    private String nombre;
    private boolean activo;

    public int getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(int regionesId) {
        this.regionesId = regionesId;
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
    
    
}
