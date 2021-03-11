/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author franciscom
 */
public class Seccion implements Serializable {
    
    private String seccionesOID;
    private String nombre;
    private int orden;
    private boolean activa;
    private boolean enabled;
    private boolean ciclica;
    private Date fechaCreacion;
    private String seccionesPadreOID;
    private boolean visible;
    private int  encuestasId;
    /*
    public Seccion(String seccionesOID,String nombre,int orden,boolean activa,boolean enabled,boolean ciclica,Date fechaCreacion,
            String seccionesPadreOID,boolean visible, int  encuestasId){
        
        this.seccionesOID=seccionesOID;
        this.nombre=nombre;
        this.orden=orden;
        this.activa=activa;
        this.enabled=enabled;
        this.ciclica=ciclica;
        this.fechaCreacion=fechaCreacion;
        this.seccionesPadreOID=seccionesPadreOID;
        this.visible=visible;
        this.encuestasId=encuestasId;
        
    }
*/
    public Seccion() {
    }
    
    public String getSeccionesOID() {
        return seccionesOID;
    }

    public void setSeccionesOID(String seccionesOID) {
        this.seccionesOID = seccionesOID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCiclica() {
        return ciclica;
    }

    public void setCiclica(boolean ciclica) {
        this.ciclica = ciclica;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getSeccionesPadreOID() {
        return seccionesPadreOID;
    }

    public void setSeccionesPadreOID(String seccionesPadreOID) {
        this.seccionesPadreOID = seccionesPadreOID;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int isEncuestasId() {
        return encuestasId;
    }

    public void setEncuestasId(int encuestasId) {
        this.encuestasId = encuestasId;
    }
    
}
