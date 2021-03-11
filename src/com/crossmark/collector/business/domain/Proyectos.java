package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proyectos implements Serializable, Cloneable {
    
    private static final long serialVersionUID = 1L;
    private Integer proyectoId;
    private Integer clienteId;
    private String claveProyecto;
    private String descripcionProyecto;
    private String nombreProyecto;
    private String nombreCliente;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaVisible;

    private Long cantidadTiendas;
    private boolean visitaAutomatica;
    private String archivoLogo;
    private Integer unidadesNegociosId;
    private Integer diasVigencias;
    private List<Encuestas> listaEncuestasProyecto = new ArrayList<Encuestas>();

    private String fechaVisibleFilter;
    private String fechaInicioFilter;
    private String fechaFinFilter;

    private boolean status;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClaveProyecto() {
        return claveProyecto;
    }

    public void setClaveProyecto(String claveProyecto) {
        this.claveProyecto = claveProyecto;
    }

    public String getDescripcionProyecto() {
        return descripcionProyecto;
    }

    public void setDescripcionProyecto(String descripcionProyecto) {
        this.descripcionProyecto = descripcionProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public Date getFechaVisible() {
        return fechaVisible;
    }

    public void setFechaVisible(Date fechaVisible) {
        this.fechaVisible = fechaVisible;
    }

    public Long getCantidadTiendas() {
        return cantidadTiendas;
    }

    public void setCantidadTiendas(Long cantidadTiendas) {
        this.cantidadTiendas = cantidadTiendas;
    }

    public boolean isVisitaAutomatica() {
        return visitaAutomatica;
    }

    public void setVisitaAutomatica(boolean visitaAutomatica) {
        this.visitaAutomatica = visitaAutomatica;
    }

    public String getArchivoLogo() {
        return archivoLogo;
    }

    public void setArchivoLogo(String archivoLogo) {
        this.archivoLogo = archivoLogo;
    }

    public Integer getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(Integer unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public Integer getDiasVigencias() {
        return diasVigencias;
    }

    public void setDiasVigencias(Integer diasVigencias) {
        this.diasVigencias = diasVigencias;
    }

    public List<Encuestas> getListaEncuestasProyecto() {
        return listaEncuestasProyecto;
    }

    public void setListaEncuestasProyecto(List<Encuestas> listaEncuestasProyecto) {
        this.listaEncuestasProyecto = listaEncuestasProyecto;
    }

    public String getFechaVisibleFilter() {
        return fechaVisibleFilter;
    }

    public void setFechaVisibleFilter(String fechaVisibleFilter) {
        this.fechaVisibleFilter = fechaVisibleFilter;
    }

    public String getFechaInicioFilter() {
        return fechaInicioFilter;
    }

    public void setFechaInicioFilter(String fechaInicioFilter) {
        this.fechaInicioFilter = fechaInicioFilter;
    }

    public String getFechaFinFilter() {
        return fechaFinFilter;
    }

    public void setFechaFinFilter(String fechaFinFilter) {
        this.fechaFinFilter = fechaFinFilter;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
        
    
    
}
