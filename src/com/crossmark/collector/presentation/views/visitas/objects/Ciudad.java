/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;


import java.io.Serializable;

/**
 *
 * @author jdominguez
 */
public class Ciudad implements Serializable{
    private int id;
    private String nombre;
    private Estado estado;
    private int estadoid;
    private boolean activo;

    public Ciudad() {
        estado = new Estado();
    }

    public Ciudad(int id, String nombre, int estadoid,boolean activo){
        this.id = id;
        this.nombre = nombre;
        this.estadoid = estadoid;
        this.activo = activo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the estadoid
     */
    public int getEstadoid() {
        return estadoid;
    }

    /**
     * @param estadoid the estadoid to set
     */
    public void setEstadoid(int estadoid) {
        this.estadoid = estadoid;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
