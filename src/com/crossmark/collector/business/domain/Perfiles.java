/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 *
 * @author Francisco Mora
 */
public class Perfiles implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer perfilesId;
    private String nombre;
    private String descripcion;
    private boolean accesoMovil;
    private boolean activo;

    public Integer getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(Integer perfilesId) {
        this.perfilesId = perfilesId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAccesoMovil() {
        return accesoMovil;
    }

    public void setAccesoMovil(boolean accesoMovil) {
        this.accesoMovil = accesoMovil;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
