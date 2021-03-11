/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import java.util.Date;

/**
 *
 * @author jdominguez
 */
public class VisitaTienda {
    private int proyectosId;
    private int tiendasId;
    private int rutasId;
    private int visitaId;
    private int numEncuestas;
    private int tareasId;
    private int ordenTabla;
    private String descripcion;
    private Date fechaIni;
    private Date fechaFin;
    private String nombre;
    private String nombreCliente;
    private int clienteId;
    private boolean selectable;
    private boolean pertenece;

    public VisitaTienda() {
    }

    public int getProyectosId() {
        return proyectosId;
    }

    public void setProyectosId(int proyectosId) {
        this.proyectosId = proyectosId;
    }

    public int getTiendasId() {
        return tiendasId;
    }

    public void setTiendasId(int tiendasId) {
        this.tiendasId = tiendasId;
    }

    public int getRutasId() {
        return rutasId;
    }

    public void setRutasId(int rutasId) {
        this.rutasId = rutasId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public int getVisitaId() {
        return visitaId;
    }

    public void setVisitaId(int visitaId) {
        this.visitaId = visitaId;
    }

    public boolean isPertenece() {
        return pertenece;
    }

    public void setPertenece(boolean pertenece) {
        this.pertenece = pertenece;
    }

    public int getNumEncuestas() {
        return numEncuestas;
    }

    public void setNumEncuestas(int numEncuestas) {
        this.numEncuestas = numEncuestas;
    }

    public int getTareasId() {
        return tareasId;
    }

    public void setTareasId(int tareasId) {
        this.tareasId = tareasId;
    }

    public int getOrdenTabla() {
        return ordenTabla;
    }

    public void setOrdenTabla(int ordenTabla) {
        this.ordenTabla = ordenTabla;
    }
    
    
}
