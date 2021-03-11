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
public class UsuariosProyectos implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private int proyectosId;
    private String clave;
    private String descripcion;
    private String nombre;
    private Date fechaIni;
    private Date fechaFin; 
    private int tiendasId;
    private String tienda;

    public int getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(int proyectosId) {
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

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public int getTiendasId() {
        return tiendasId;
    }

    public void setTiendasId(int tiendasId) {
        this.tiendasId = tiendasId;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }
    
    
    
}
