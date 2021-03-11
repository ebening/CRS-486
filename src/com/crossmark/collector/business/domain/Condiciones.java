/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class Condiciones implements Serializable {
    
    private static final long serialVersionUID = -1L;
    private String opcionesOID;
    private String seccionesOID;
    private String preguntasOID;
    private String nombre;
    private Integer habilitar;
    private Boolean seleccionadas;
    private String seccionesAccionesOID;
    private String preguntasAccionesOID;

    public String getOpcionesOID() {
        return opcionesOID;
    }

    public void setOpcionesOID(String opcionesOID) {
        this.opcionesOID = opcionesOID;
    }

    public String getSeccionesOID() {
        return seccionesOID;
    }

    public void setSeccionesOID(String seccionesOID) {
        this.seccionesOID = seccionesOID;
    }

    public String getPreguntasOID() {
        return preguntasOID;
    }

    public void setPreguntasOID(String preguntasOID) {
        this.preguntasOID = preguntasOID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHabilitar() {
        return habilitar;
    }

    public void setHabilitar(Integer habilitar) {
        this.habilitar = habilitar;
    }

    public Boolean isSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(Boolean seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public String getSeccionesAccionesOID() {
        return seccionesAccionesOID;
    }

    public void setSeccionesAccionesOID(String seccionesAccionesOID) {
        this.seccionesAccionesOID = seccionesAccionesOID;
    }

    public String getPreguntasAccionesOID() {
        return preguntasAccionesOID;
    }

    public void setPreguntasAccionesOID(String preguntasAccionesOID) {
        this.preguntasAccionesOID = preguntasAccionesOID;
    }
    
    
}
