/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usss
 */
public class Encuestas implements Serializable {
    
    private static long serialVersionUID = 1L;


    private Integer encuestasId;
    private String nombreEncuesta;
    private String claveEncuesta;
    private String gpsEncuesta;
    private short statusEncuesta;
    private boolean plantilla;
    private boolean activa;
    private Date fechaCreacion;
    private String observaciones;
    private Integer proyectosId;
    private short unidadNegocio;
    private Integer cantidadProyectosVigentes;
    private boolean operacion;
    public List<Secciones> listaSecciones = new ArrayList<>();
    
    private String estatus;
    private String tipo;
    
    /**
     * @return the encuestasId
     */
    public Integer getEncuestasId() {
        return encuestasId;
    }

    /**
     * @param encuestasId the encuestasId to set
     */
    public void setEncuestasId(Integer encuestasId) {
        this.encuestasId = encuestasId;
    }

    /**
     * @return the nombreEncuesta
     */
    public String getNombreEncuesta() {
        return nombreEncuesta;
    }

    /**
     * @param nombreEncuesta the nombreEncuesta to set
     */
    public void setNombreEncuesta(String nombreEncuesta) {
        this.nombreEncuesta = nombreEncuesta;
    }

    /**
     * @return the claveEncuesta
     */
    public String getClaveEncuesta() {
        return claveEncuesta;
    }

    /**
     * @param claveEncuesta the claveEncuesta to set
     */
    public void setClaveEncuesta(String claveEncuesta) {
        this.claveEncuesta = claveEncuesta;
    }

    /**
     * @return the gpsEncuesta
     */
    public String getGpsEncuesta() {
        return gpsEncuesta;
    }

    /**
     * @param gpsEncuesta the gpsEncuesta to set
     */
    public void setGpsEncuesta(String gpsEncuesta) {
        this.gpsEncuesta = gpsEncuesta;
    }

     /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the proyectosId
     */
    public Integer getProyectosId() {
        return proyectosId;
    }

    /**
     * @param proyectosId the proyectosId to set
     */
    public void setProyectosId(Integer proyectosId) {
        this.proyectosId = proyectosId;
    }

   
    /**
     * @return the cantidadProyectosVigentes
     */
    public Integer getCantidadProyectosVigentes() {
        return cantidadProyectosVigentes;
    }

    /**
     * @param cantidadProyectosVigentes the cantidadProyectosVigentes to set
     */
    public void setCantidadProyectosVigentes(Integer cantidadProyectosVigentes) {
        this.cantidadProyectosVigentes = cantidadProyectosVigentes;
    }

    /**
     * @return the statusEncuesta
     */
    public short getStatusEncuesta() {
        return statusEncuesta;
    }

    /**
     * @param statusEncuesta the statusEncuesta to set
     */
    public void setStatusEncuesta(short statusEncuesta) {
        this.statusEncuesta = statusEncuesta;
    }

    /**
     * @return the plantilla
     */
    public boolean isPlantilla() {
        return plantilla;
    }

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(boolean plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the unidadNegocio
     */
    public short getUnidadNegocio() {
        return unidadNegocio;
    }

    /**
     * @param unidadNegocio the unidadNegocio to set
     */
    public void setUnidadNegocio(short unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }

    public List<Secciones> getListaSecciones() {
            return listaSecciones;
    }

    public void setListaSecciones(List<Secciones> listaSecciones) {
            this.listaSecciones = listaSecciones;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isOperacion() {
        return operacion;
    }

    public void setOperacion(boolean operacion) {
        this.operacion = operacion;
    }
    
    
}
