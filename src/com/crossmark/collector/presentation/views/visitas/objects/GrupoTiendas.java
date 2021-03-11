/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class GrupoTiendas {
    private int id;
    private String nombre;
    private List<Tienda> tiendas;

    public GrupoTiendas(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        tiendas = new ArrayList<>();
    }

    public void agregarTienda(Tienda t){
        tiendas.add(t);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }
    
    
}
