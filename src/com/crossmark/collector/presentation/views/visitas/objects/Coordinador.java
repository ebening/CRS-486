package com.crossmark.collector.presentation.views.visitas.objects;

import java.io.Serializable;

public class Coordinador implements Serializable{
    
    private String usuariosOID;
    private String nombreUsuario;
    private int perfilesId;
    private String nombrePerfil;
    private int equiposId;
    private int territoriosId;
    private boolean selected;
    
    public Coordinador(){
        
    }

    public String getUsuariosOID() {
        return usuariosOID;
    }

    public void setUsuariosOID(String usuariosOID) {
        this.usuariosOID = usuariosOID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(int perfilesId) {
        this.perfilesId = perfilesId;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

    public int getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(int territoriosId) {
        this.territoriosId = territoriosId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
}
