/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente.objects;

import java.io.Serializable;

/**
 *
 * @author Francisco Mora
 */
public class Cadenas implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int cadenasId;
    private String nombre;

    public int getCadenasId() {
        return cadenasId;
    }

    public void setCadenasId(int cadenasId) {
        this.cadenasId = cadenasId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
