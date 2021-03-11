package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 09/12/2014.
 *
 */
public class Cliente implements Serializable {
    private Integer id;
    private String nombre;
    private String encargado;
    private String email;
    public boolean notificaciones;
    private boolean activo;
    private List<UnidadesNegocio> unidadesNegocio;

    public Cliente() {
        setActivo(true);
        this.unidadesNegocio = new ArrayList<>();
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<UnidadesNegocio> getUnidadesNegocio() {
        return unidadesNegocio;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void setUnidadesNegocio(List<UnidadesNegocio> unidadesNegocio) {
        this.unidadesNegocio = unidadesNegocio;
    }
}
