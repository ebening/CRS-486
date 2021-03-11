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
public class EncuestasUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private String encuestaNombre;
    private String encuestaClave;
    private Integer encuestasId;

    public String getEncuestaNombre() {
        return encuestaNombre;
    }

    public void setEncuestaNombre(String encuestaNombre) {
        this.encuestaNombre = encuestaNombre;
    }

    public String getEncuestaClave() {
        return encuestaClave;
    }

    public void setEncuestaClave(String encuestaClave) {
        this.encuestaClave = encuestaClave;
    }

    public Integer getEncuestasId() {
        return encuestasId;
    }

    public void setEncuestasId(Integer encuestasId) {
        this.encuestasId = encuestasId;
    }
    
    
    
}
