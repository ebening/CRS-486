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
public class Catalogos implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private String listasOID;
    private String nombreCatalogo;
    private boolean activa;
    private boolean catalogo;
    private int tipoListasId;

    /**
     * @return the listasOID
     */
    public String getListasOID() {
        return listasOID;
    }

    /**
     * @param listasOID the listasOID to set
     */
    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
    }

    /**
     * @return the nombreCatalogo
     */
    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    /**
     * @param nombreCatalogo the nombreCatalogo to set
     */
    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
    }



    /**
     * @return the catalogo
     */
    public boolean isCatalogo() {
        return catalogo;
    }

    /**
     * @param catalogo the catalogo to set
     */
    public void setCatalogo(boolean catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * @return the tipoListasId
     */
    public int getTipoListasId() {
        return tipoListasId;
    }

    /**
     * @param tipoListasId the tipoListasId to set
     */
    public void setTipoListasId(int tipoListasId) {
        this.tipoListasId = tipoListasId;
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
    
    
    
    
    
}
