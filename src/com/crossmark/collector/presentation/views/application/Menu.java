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
public class Menu implements Comparable<Menu>, Serializable {

    private int idMenu;
    private int idPadre;
    private int orden;
    private String nombre;
    private String url;
    private List<Menu> submenu;
    private boolean hasChildren;

    public Menu(int idMenu, int idPadre, int orden, String nombre, String url, List<Menu> submenu) {

        this.idMenu = idMenu;
        this.idPadre = idPadre;
        this.orden = orden;
        this.nombre = nombre;
        this.url = url;
        this.submenu = submenu;
    }

    public Menu() {
        submenu = new ArrayList<>();
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<Menu> submenu) {
        this.submenu = submenu;
    }

    @Override
    public int compareTo(Menu t) {
        if (t.getIdMenu() == getIdMenu()) {
            return 0;
        } else {
            return Integer.compare(getOrden(), t.getOrden());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Menu) {
            Menu menu = (Menu) o;
            return menu == this || menu.getIdMenu() == getIdMenu();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdMenu());
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
