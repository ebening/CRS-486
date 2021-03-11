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
public class EquiposUsuarios implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private Integer equiposId;
    private String nombre;
    private int pertenece;

    public String getStringPertenece(){
        if(pertenece == 1){
            return "Si";
        }else{
            return "No";
        }
    }
    
    public Integer getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(Integer equiposId) {
        this.equiposId = equiposId;
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
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
}
