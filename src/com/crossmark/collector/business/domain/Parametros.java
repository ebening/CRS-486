/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import com.crossmark.collector.presentation.views.utils.SeccionParametro;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author franciscom
 */
public class Parametros implements Serializable,Comparable<Parametros>{
    private Integer parametrosId;
    private String nombre;
    private String valor;
    private String tipoValor;
    private String titulo;
    private boolean visible;
    private SeccionParametro seccionParametro;



    public Parametros() {
       seccionParametro = new SeccionParametro();
    }

    public Integer getParametrosId() {
        return parametrosId;
    }

    public void setParametrosId(Integer parametrosId) {
        this.parametrosId = parametrosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor.toString();
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public SeccionParametro getSeccionParametro() {
        return seccionParametro;
    }

    public void setSeccionParametro(SeccionParametro seccionParametro) {
        this.seccionParametro = seccionParametro;
    }


    @Override
    public int compareTo(Parametros t) {
        if (t.getParametrosId() == getParametrosId()) {
            return 0;
        } else {
            return Integer.compare(getParametrosId(), t.getParametrosId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Parametros) {
            Parametros menu = (Parametros) o;
            return menu == this || menu.getParametrosId() == getParametrosId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParametrosId());
    }
}
