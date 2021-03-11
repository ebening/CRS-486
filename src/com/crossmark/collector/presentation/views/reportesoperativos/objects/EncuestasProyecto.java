/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import com.crossmark.collector.presentation.views.reportescliente.objects.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class EncuestasProyecto implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private String nombre;
    private Integer encuestasId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEncuestasId() {
        return encuestasId;
    }

    public void setEncuestasId(Integer encuestasId) {
        this.encuestasId = encuestasId;
    }
    
    
    
}
