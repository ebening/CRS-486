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
public class Variable implements Serializable{
    private int variablesId;
    private String nombre;

    public int getVariablesId() {
        return variablesId;
    }

    public void setVariablesId(int variablesId) {
        this.variablesId = variablesId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
