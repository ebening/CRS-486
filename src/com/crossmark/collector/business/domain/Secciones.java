/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

/**
 *
 * @author Usss
 */
public class Secciones implements Serializable {
    
    
    private static long serialVersionUID = 1L;
    
    private int idSeccion;
    private String nombreSeccion;
    private String seccionOID;
    private boolean recursiva;
    private int idTipoLista;
    private int catalogoOrigen;
    private int preguntaOrigen;
    private boolean seleccionada;
    private boolean enabled;
    private boolean ciclica;
    private boolean visible;
    private boolean controlUsuario;
    private boolean filtrada;
    private int orden;
    public List<Preguntas> miListaPreguntas = new ArrayList<>();
    public List<Secciones> secciones;
    private int idEncuesta;
    private boolean activa;
    private Date fechaCreacion;
    private String preguntaDominante;
    private String claveSeccion;
    private String listasOID;
    private DynaFormModel parametros;
    
    //Variable para controlar selec y unselect de configuracion de opciones.
    private boolean habilitadoOk;
    private boolean deshabilitadoOk;
    
    /**
     * @return the idSeccion
     */
    public int getIdSeccion() {
        return idSeccion;
    }

    /**
     * @param idSeccion the idSeccion to set
     */
    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    /**
     * @return the nombreSeccion
     */
    public String getNombreSeccion() {
        return nombreSeccion;
    }

    /**
     * @param nombreSeccion the nombreSeccion to set
     */
    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    /**
     * @return the seccionOID
     */
    public String getSeccionOID() {
        return seccionOID;
    }

    /**
     * @param seccionOID the seccionOID to set
     */
    public void setSeccionOID(String seccionOID) {
        this.seccionOID = seccionOID;
    }

    /**
     * @return the recursiva
     */
    public boolean isRecursiva() {
        return recursiva;
    }

    /**
     * @param recursiva the recursiva to set
     */
    public void setRecursiva(boolean recursiva) {
        this.recursiva = recursiva;
    }

    /**
     * @return the idTipoLista
     */
    public int getIdTipoLista() {
        return idTipoLista;
    }

    /**
     * @param idTipoLista the idTipoLista to set
     */
    public void setIdTipoLista(int idTipoLista) {
        this.idTipoLista = idTipoLista;
    }

    /**
     * @return the catalogoOrigen
     */
    public int getCatalogoOrigen() {
        return catalogoOrigen;
    }

    /**
     * @param catalogoOrigen the catalogoOrigen to set
     */
    public void setCatalogoOrigen(int catalogoOrigen) {
        this.catalogoOrigen = catalogoOrigen;
    }

    /**
     * @return the preguntaOrigen
     */
    public int getPreguntaOrigen() {
        return preguntaOrigen;
    }

    /**
     * @param preguntaOrigen the preguntaOrigen to set
     */
    public void setPreguntaOrigen(int preguntaOrigen) {
        this.preguntaOrigen = preguntaOrigen;
    }

    /**
     * @return the seleccionada
     */
    public boolean isSeleccionada() {
        return seleccionada;
    }

    /**
     * @param seleccionada the seleccionada to set
     */
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the ciclica
     */
    public boolean isCiclica() {
        return ciclica;
    }

    /**
     * @param ciclica the ciclica to set
     */
    public void setCiclica(boolean ciclica) {
        this.ciclica = ciclica;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * @return the miListaPreguntas
     */
    public List<Preguntas> getMiListaPreguntas() {
        return miListaPreguntas;
    }

    /**
     * @param miListaPreguntas the miListaPreguntas to set
     */
    public void setMiListaPreguntas(List<Preguntas> miListaPreguntas) {
        this.miListaPreguntas = miListaPreguntas;
    }

    /**
     * @return the idEncuesta
     */
    public int getIdEncuesta() {
        return idEncuesta;
    }

    /**
     * @param idEncuesta the idEncuesta to set
     */
    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the preguntaDominante
     */
    public String getPreguntaDominante() {
        return preguntaDominante;
    }

    /**
     * @param preguntaDominante the preguntaDominante to set
     */
    public void setPreguntaDominante(String preguntaDominante) {
        this.preguntaDominante = preguntaDominante;
    }
    /**
     * @return the claveSeccion
     */
    public String getClaveSeccion() {
        return claveSeccion;
    }

    /**
     * @param claveSeccion the claveSeccion to set
     */
    public void setClaveSeccion(String claveSeccion) {
        this.claveSeccion = claveSeccion;
    }

    /**
     * @return the parametros
     */
    public DynaFormModel getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(DynaFormModel parametros) {
        this.parametros = parametros;
    }

    public String getListasOID() {
        return listasOID;
    }

    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
    }

    public boolean isControlUsuario() {
        return controlUsuario;
    }

    public void setControlUsuario(boolean controlUsuario) {
        this.controlUsuario = controlUsuario;
    }

    public boolean isHabilitadoOk() {
        return habilitadoOk;
    }

    public void setHabilitadoOk(boolean habilitadoOk) {
        this.habilitadoOk = habilitadoOk;
    }

    public boolean isDeshabilitadoOk() {
        return deshabilitadoOk;
    }

    public void setDeshabilitadoOk(boolean deshabilitadoOk) {
        this.deshabilitadoOk = deshabilitadoOk;
    }

    public boolean isFiltrada() {
        return filtrada;
    }

    public void setFiltrada(boolean filtrada) {
        this.filtrada = filtrada;
    }
    
    public List<Secciones> getSecciones() {
        return secciones;
    }
    
    public void setSecciones(List<Secciones> secciones) {
        this.secciones = secciones;
    }
    
    
    
}
