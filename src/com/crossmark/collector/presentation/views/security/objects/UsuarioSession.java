/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.security.objects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author christian
 */
public class UsuarioSession implements Serializable{
    
    
    private String userName;
    private String password;
    private String OID;
    private Integer PerfilesId;
    private Integer equipoId;

    private String usuariosLogOID;
    private Date fechaLogIn;
    private Date fechaLogOff;
    private boolean activo;
    private String ip;




    public UsuarioSession() {
        
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

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public Integer getPerfilesId() {
        return PerfilesId;
    }

    public void setPerfilesId(Integer PerfilesId) {
        this.PerfilesId = PerfilesId;
    }

    public Integer getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    public String getUsuariosLogOID() {
        return usuariosLogOID;
    }

    public void setUsuariosLogOID(String usuariosLogOID) {
        this.usuariosLogOID = usuariosLogOID;
    }

    public Date getFechaLogIn() {
        return fechaLogIn;
    }

    public void setFechaLogIn(Date fechaLogIn) {
        this.fechaLogIn = fechaLogIn;
    }

    public Date getFechaLogOff() {
        return fechaLogOff;
    }

    public void setFechaLogOff(Date fechaLogOff) {
        this.fechaLogOff = fechaLogOff;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
