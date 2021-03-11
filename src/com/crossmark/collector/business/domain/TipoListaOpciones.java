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
public class TipoListaOpciones implements Serializable{
    
    private static long serialVersionUID = 1L;
    
    private int tipoListasPreguntasId;
    private String nombreTipoListaOpciones;
    private boolean activoNombreTipoListaOpciones;

    /**
     * @return the tipoListasPreguntasId
     */
    public int getTipoListasPreguntasId() {
        return tipoListasPreguntasId;
    }

    /**
     * @param tipoListasPreguntasId the tipoListasPreguntasId to set
     */
    public void setTipoListasPreguntasId(int tipoListasPreguntasId) {
        this.tipoListasPreguntasId = tipoListasPreguntasId;
    }

    /**
     * @return the nombreTipoListaOpciones
     */
    public String getNombreTipoListaOpciones() {
        return nombreTipoListaOpciones;
    }

    /**
     * @param nombreTipoListaOpciones the nombreTipoListaOpciones to set
     */
    public void setNombreTipoListaOpciones(String nombreTipoListaOpciones) {
        this.nombreTipoListaOpciones = nombreTipoListaOpciones;
    }

    /**
     * @return the activoNombreTipoListaOpciones
     */
    public boolean isActivoNombreTipoListaOpciones() {
        return activoNombreTipoListaOpciones;
    }

    /**
     * @param activoNombreTipoListaOpciones the activoNombreTipoListaOpciones to set
     */
    public void setActivoNombreTipoListaOpciones(boolean activoNombreTipoListaOpciones) {
        this.activoNombreTipoListaOpciones = activoNombreTipoListaOpciones;
    }
    
    
}
