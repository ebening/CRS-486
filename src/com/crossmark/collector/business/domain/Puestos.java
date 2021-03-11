/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class Puestos implements Serializable {
    
    //private static long serialVersionUID = 1L;
    
    private Integer puestosId;
    private String nombre;

    public Integer getPuestosId() {
        return puestosId;
    }

    public void setPuestosId(Integer puestosId) {
        this.puestosId = puestosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
