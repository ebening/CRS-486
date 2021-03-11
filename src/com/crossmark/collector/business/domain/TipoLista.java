package com.crossmark.collector.business.domain;

/**
 * Created by christian on 26/12/2014.
 */
public class TipoLista {

    private Integer id;
    private String nombre;

    public TipoLista() {
    }

    public TipoLista(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
