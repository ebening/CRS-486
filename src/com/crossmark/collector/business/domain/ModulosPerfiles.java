/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 *
 * @author Francisco Mora
 */
public class ModulosPerfiles implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer modulosId;
    private String titulos;
    private Integer modulosPadreId;
    private Boolean seleccionado;
    
    public Integer getModulosId() {
        return modulosId;
    }

    public void setModulosId(Integer modulosId) {
        this.modulosId = modulosId;
    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public Integer getModulosPadreId() {
        return modulosPadreId;
    }

    public void setModulosPadreId(Integer modulosPadreId) {
        this.modulosPadreId = modulosPadreId;
    }

    public Boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
}
