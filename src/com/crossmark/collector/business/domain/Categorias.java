/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class Categorias implements Serializable {
    
    private static final long serialVersionUID = -1L;
    private int idCategoria;
    private String nombreCategoria;
    private List<SubCategorias> miListaSubcategorias;

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

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria the nombreCategoria to set
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * @return the miListaSubcategorias
     */
    public List<SubCategorias> getMiListaSubcategorias() {
        return miListaSubcategorias;
    }

    /**
     * @param miListaSubcategorias the miListaSubcategorias to set
     */
    public void setMiListaSubcategorias(List<SubCategorias> miListaSubcategorias) {
        this.miListaSubcategorias = miListaSubcategorias;
    }
    
}
