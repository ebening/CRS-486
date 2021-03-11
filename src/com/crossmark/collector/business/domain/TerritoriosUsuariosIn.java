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
public class TerritoriosUsuariosIn implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer territoriosId;
    private String nombre;
    private int pertenece;

    public Integer getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(Integer territoriosId) {
        this.territoriosId = territoriosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPertenece() {
        return pertenece;
    }

    public void setPertenece(int pertenece) {
        this.pertenece = pertenece;
    }
    
    
    
}
