/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 *
 * @author RIGG
 */
public class Respuestas implements Serializable{
    
    private static long serialVersionUID = 1L;
    private int idTipoRespuesta;
    private boolean activa;
    private String nombreTipoRespuesta;

    /**
     * @return the idTipoRespuesta
     */
    public int getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    /**
     * @param idTipoRespuesta the idTipoRespuesta to set
     */
    public void setIdTipoRespuesta(int idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
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
     * @return the nombreTipoRespuesta
     */
    public String getNombreTipoRespuesta() {
        return nombreTipoRespuesta;
    }

    /**
     * @param nombreTipoRespuesta the nombreTipoRespuesta to set
     */
    public void setNombreTipoRespuesta(String nombreTipoRespuesta) {
        this.nombreTipoRespuesta = nombreTipoRespuesta;
    }
    
    
    
}
