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
public class SubCategorias implements Serializable {
    
    private static final long serialVersionUID = -1L;
    private int idSubCategoria;
    private String nombreSubCategoria;
    private int idCategoria;

    /**
     * @return the idSubCategoria
     */
    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    /**
     * @param idSubCategoria the idSubCategoria to set
     */
    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    /**
     * @return the nombreSubCategoria
     */
    public String getNombreSubCategoria() {
        return nombreSubCategoria;
    }

    /**
     * @param nombreSubCategoria the nombreSubCategoria to set
     */
    public void setNombreSubCategoria(String nombreSubCategoria) {
        this.nombreSubCategoria = nombreSubCategoria;
    }

    /**
     * @return the idCategoria
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
}
