/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;

/**
 *
 * @author Usss
 */
public class Opciones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String opcionesOID;
    private String listasOID;
    private String textoOpcion;
    private String valorOpcion;
    private String claveOpcion;
    private String condiciones;
    private String preguntaDominanteOID;
    private String tag;
    private String codigoBarras;
    private String seccionAccion;
    private String preguntaAccion;
    private String tipoAccion;
    
    private boolean visible;
    private boolean activa;
    private boolean enabled;
    private boolean condicionada;
    
    private int orden;
    
    
    //
    /**
     * @return the opcionesOID
     */
    public String getOpcionesOID() {
        return opcionesOID;
    }

    /**
     * @param opcionesOID the opcionesOID to set
     */
    public void setOpcionesOID(String opcionesOID) {
        this.opcionesOID = opcionesOID;
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
     * @return the textoOpcion
     */
    public String getTextoOpcion() {
        return textoOpcion;
    }

    /**
     * @param textoOpcion the textoOpcion to set
     */
    public void setTextoOpcion(String textoOpcion) {
        if (!textoOpcion.trim().equals("")){
            this.textoOpcion = textoOpcion.trim();
        }
    }

    /**
     * @return the valorOpcion
     */
    public String getValorOpcion() {
        return valorOpcion;
    }

    /**
     * @param valorOpcion the valorOpcion to set
     */
    public void setValorOpcion(String valorOpcion) {
        this.valorOpcion = valorOpcion;
    }

    /**
     * @return the claveOpcion
     */
    public String getClaveOpcion() {
        return claveOpcion;
    }

    /**
     * @param claveOpcion the claveOpcion to set
     */
    public void setClaveOpcion(String claveOpcion) {
        this.claveOpcion = claveOpcion;
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
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the codigoBarras
     */
    public String getCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
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

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getSeccionAccion() {
        return seccionAccion;
    }

    public void setSeccionAccion(String seccionAccion) {
        this.seccionAccion = seccionAccion;
    }

    public String getPreguntaAccion() {
        return preguntaAccion;
    }

    public void setPreguntaAccion(String preguntaAccion) {
        this.preguntaAccion = preguntaAccion;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public boolean isCondicionada() {
        return condicionada;
    }

    public void setCondicionada(boolean condicionada) {
        this.condicionada = condicionada;
    }
    
    
}
