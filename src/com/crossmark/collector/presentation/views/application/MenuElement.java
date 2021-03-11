/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author christian
 */
public class MenuElement implements Comparable<MenuElement>, Serializable {

    private Integer modulosId;
    private String nombre;
    private Integer modulosPadreId;
    private Integer orden;
    private String url;
    private List<MenuElement> submenu;
    private boolean hasChildren;
    private boolean esNavDirecta;

    public MenuElement(Integer modulosId, Integer modulosPadreId, Integer orden, String nombre, String url) {
        this.modulosId = modulosId;
        this.nombre = nombre;
        this.modulosPadreId = modulosPadreId;
        this.orden = orden;
        this.url = url;
        this.submenu = new ArrayList<>();
        this.esNavDirecta = false;
    }

    public MenuElement() {
        submenu = new ArrayList<>();
        this.esNavDirecta = false;
    }

    public Integer getModulosId() {
        return modulosId;
    }

    public void setModulosId(Integer modulosId) {
        this.modulosId = modulosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getModulosPadreId() {
        return modulosPadreId;
    }

    public void setModulosPadreId(Integer modulosPadreId) {
        this.modulosPadreId = modulosPadreId;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuElement> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<MenuElement> submenu) {
        this.submenu = submenu;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isEsNavDirecta() {
        return esNavDirecta;
    }

    public void setEsNavDirecta(boolean esNavDirecta) {
        this.esNavDirecta = esNavDirecta;
    }

    @Override
    public int compareTo(MenuElement t) {
        if (t.getModulosId() == getModulosId()) {
            return 0;
        } else {
            return Integer.compare(getOrden(), t.getOrden());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MenuElement) {
            MenuElement menu = (MenuElement) o;
            return menu == this || menu.getModulosId() == getModulosId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModulosId());
    }

    public boolean isHasChildren() {
        hasChildren = !(submenu.isEmpty());
        return hasChildren;
    }

    @Override
    public String toString() {
        return getNombre();
    }

}
