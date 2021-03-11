/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class Archivos implements Serializable {
    
    //private static long serialVersionUID = 1L;
    
    private String archivosOID;
    private String nombre;
    private Integer proyectosId;
    private Date fecha;
    private boolean activo;

    public String getArchivosOID() {
        return archivosOID;
    }

    public void setArchivosOID(String archivosOID) {
        this.archivosOID = archivosOID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(Integer proyectosId) {
        this.proyectosId = proyectosId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
