/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

/**
 *
 * @author jdominguez
 */
public class Promotor extends Usuario{
    private String ciudad;
    private String estado;
    private String ruta;
    private String equipo;
    private boolean pertenece;
    private boolean unselect;
    
    public Promotor(String nombre, String OID, String apellidoPaterno, String apellidoMaterno){
        super();
        super.setNombre(nombre);
        super.setOID(OID);
        super.setApellidoMaterno(apellidoMaterno);
        super.setApellidoPaterno(apellidoPaterno);
        super.setPerfilesId(1);
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isPertenece() {
        return pertenece;
    }

    public void setPertenece(boolean pertenece) {
        this.pertenece = pertenece;
    }

    public boolean isUnselect() {
        return unselect;
    }

    public void setUnselect(boolean unselect) {
        this.unselect = unselect;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    
    
}
