/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jdominguez
 */
public class Proyecto {
    private int id;
    private String clave;
    private String nombre;
    private String tienda;
    private Date fechaInicio;
    private Date fechaFin;
    private String visitaProgramada;

    public Proyecto() {
    }

    public Proyecto(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
    }

    public Proyecto(String clave, String nombre, String tienda, Date fechaInicio, Date fechaFin) {
        this.clave = clave;
        this.nombre = nombre;
        this.tienda = tienda;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFormatedFechaIni(){
        return new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio);
    }
    
    public String getFormatedFechaFin(){
        return new SimpleDateFormat("dd/MM/yyyy").format(fechaFin);
    }

    public String getVisitaProgramada() {
        return visitaProgramada;
    }

    public void setVisitaProgramada(String visitaProgramada) {
        this.visitaProgramada = visitaProgramada;
    }
}
