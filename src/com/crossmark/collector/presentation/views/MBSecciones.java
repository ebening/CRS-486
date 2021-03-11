/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;


import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.business.services.ServiceSecciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class MBSecciones implements Serializable {
    
    
    private static final long serialVersionUID = -1L;
    
    private ServiceSecciones serviceSecciones;
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
    private short orden;
    private List<Preguntas> miListaPreguntas = new ArrayList<Preguntas>();
    private int idEncuesta;
    private boolean activa;
    private Date fechaCreacion;
    private List<Secciones> listaSeccionesEncuesta = new ArrayList<Secciones>();
    private int variable;
    
    private String preguntaDominanteOID;
    private Integer tipoListaOpciones;
    private String listasFiltroOID;
    private boolean controlUsuario;
    private boolean codigoBarras;
    private String listasOID;
    
    private String claveSeccion;
    
    
    public List<Secciones> obtenerSecciones(){
        
        this.setListaSeccionesEncuesta(serviceSecciones.listaSecciones(
                seccionOID, 
                nombreSeccion, 
                idEncuesta, 
                orden, 
                activa, 
                enabled, 
                ciclica, 
                fechaCreacion, 
                visible));
        
        
        
        return listaSeccionesEncuesta;
        
    }
    
    
    
public String guardaSeccion(){
    
    String miNuevaSeccion = "";
    
    miNuevaSeccion = serviceSecciones.guardaSeccion(seccionOID, 
            nombreSeccion, 
            idEncuesta, 
            orden, 
            activa, 
            enabled, 
            ciclica, 
            fechaCreacion, 
            seleccionada, 
            getPreguntaDominanteOID(), 
            getTipoListaOpciones(), 
            getListasFiltroOID(), 
            getVariable(), 
            isControlUsuario(), 
            isCodigoBarras(), 
            getListasOID(),
            claveSeccion,
            null);
    
    return miNuevaSeccion;
    
}    
    

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
    public short getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(short orden) {
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
     * @return the listaSeccionesEncuesta
     */
    public List<Secciones> getListaSeccionesEncuesta() {
        return listaSeccionesEncuesta;
    }

    /**
     * @param listaSeccionesEncuesta the listaSeccionesEncuesta to set
     */
    public void setListaSeccionesEncuesta(List<Secciones> listaSeccionesEncuesta) {
        this.listaSeccionesEncuesta = listaSeccionesEncuesta;
    }

    /**
     * @return the serviceSecciones
     */
    public ServiceSecciones getServiceSecciones() {
        return serviceSecciones;
    }

    /**
     * @param serviceSecciones the serviceSecciones to set
     */
    public void setServiceSecciones(ServiceSecciones serviceSecciones) {
        this.serviceSecciones = serviceSecciones;
    }

    /**
     * @return the preguntaDominanteOID
     */
    public String getPreguntaDominanteOID() {
        return preguntaDominanteOID;
    }

    /**
     * @param preguntaDominanteOID the preguntaDominanteOID to set
     */
    public void setPreguntaDominanteOID(String preguntaDominanteOID) {
        this.preguntaDominanteOID = preguntaDominanteOID;
    }

    public Integer getTipoListaOpciones() {
        return tipoListaOpciones;
    }

    public void setTipoListaOpciones(Integer tipoListaOpciones) {
        this.tipoListaOpciones = tipoListaOpciones;
    }
    
    /**
     * @return the listasFiltroOID
     */
    public String getListasFiltroOID() {
        return listasFiltroOID;
    }

    /**
     * @param listasFiltroOID the listasFiltroOID to set
     */
    public void setListasFiltroOID(String listasFiltroOID) {
        this.listasFiltroOID = listasFiltroOID;
    }

    /**
     * @return the controlUsuario
     */
    public boolean isControlUsuario() {
        return controlUsuario;
    }

    /**
     * @param controlUsuario the controlUsuario to set
     */
    public void setControlUsuario(boolean controlUsuario) {
        this.controlUsuario = controlUsuario;
    }

    /**
     * @return the codigoBarras
     */
    public boolean isCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(boolean codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * @return the listasOID
     */
    public String getListasOID() {
        return listasOID;
    }

    /**
     * @param listasOID the listasOID to set
     */
    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
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

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }
    
    
}
