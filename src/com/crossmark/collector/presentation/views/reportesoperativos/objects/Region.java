package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.io.Serializable;

/**
 *
 * @author franciscom
 */
public class Region implements Serializable {

    private Integer id;
    private String nombre;
    private boolean activo;
    private int coordinador;
    private Usuario userCoordinador;
//UsuariosOID
    //private int cliente;

    public Region(Integer id, String nombre, boolean activo, int coordinador) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.coordinador = coordinador;
        //this.cliente = cliente;
    }

    public Region(Integer id, String nombre, boolean activo, Usuario userCoordinador) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.userCoordinador = userCoordinador;
    }

    public Region() {
        userCoordinador = new Usuario();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(int coordinador) {
        this.coordinador = coordinador;
    }

    public Usuario getUserCoordinador() {
        return userCoordinador;
    }

    public void setUserCoordinador(Usuario userCoordinador) {
        this.userCoordinador = userCoordinador;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }



}
