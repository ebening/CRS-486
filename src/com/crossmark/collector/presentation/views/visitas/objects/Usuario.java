/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.visitas.objects;

import java.io.Serializable;

/**
 *
 * @author christian
 */
public class Usuario implements Serializable{
    
    
    private String userName;
    private String password;
    private String OID;
    private String nroEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int PerfilesId;

    public Usuario() {
        
    }

    public Usuario(String OID, String nroEmpleado, String nombre) {
        this.OID = OID;
        this.nroEmpleado = nroEmpleado;
        this.nombre = nombre;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the OID
     */
    public String getOID() {
        return OID;
    }

    /**
     * @param OID the OID to set
     */
    public void setOID(String OID) {
        this.OID = OID;
    }

    /**
     * @return the nroEmpleado
     */
    public String getNroEmpleado() {
        return nroEmpleado;
    }

    /**
     * @param nroEmpleado the nroEmpleado to set
     */
    public void setNroEmpleado(String nroEmpleado) {
        this.nroEmpleado = nroEmpleado;
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
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * @param apellidoPaterno the apellidoPaterno to set
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * @param apellidoMaterno the apellidoMaterno to set
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * @return the PerfilesId
     */
    public int getPerfilesId() {
        return PerfilesId;
    }

    /**
     * @param PerfilesId the PerfilesId to set
     */
    public void setPerfilesId(int PerfilesId) {
        this.PerfilesId = PerfilesId;
    }
    
    
}
