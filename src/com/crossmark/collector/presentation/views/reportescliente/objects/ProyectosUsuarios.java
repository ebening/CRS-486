/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente.objects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class ProyectosUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer proyectosId;
    private String clave;
    private String descripcion;
    private String nombre;

    public Integer getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Integer proyectosId) {
        this.proyectosId = proyectosId;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
