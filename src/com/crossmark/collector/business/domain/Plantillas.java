/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Usss
 */
public class Plantillas implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer encuestasId;
    private String nombrePlantilla;
    private String clavePlantilla;
    private String gpsPlantilla;
    private boolean operacionPlantilla;
    private short statusIdPlantilla;
    private boolean activaPlantilla;
    private boolean esPlantilla;
    private String seccionesPlantillaOID;
    private String nombreSeccionPlantilla;
    private boolean enabledSeccionPlantilla;
    private boolean seccionCiclicaPlantilla;
    private boolean seccionVisiblePlantilla;
    private short ordenSeccionPlantilla;
    private String preguntasPlantillaOID;
    private String nombrePreguntaPlantilla;
    private boolean preguntaDinamicaPlantilla;
    private String variablePreguntaPlantilla;
    private boolean enabledPreguntaPlantilla;
    private boolean visiblePreguntaPlantilla;
    private short tiposPreguntasPlantilla;
    private String nombreTipoPreguntasPlantilla;
    private String listasPlantillaOID;
    private String nombreListaPlantilla;
    private String opcionesPlantillaOID;
    private String nombreOpcionesPlantilla;
    private boolean visibleOpcionesPlantilla;
    private String preguntaDominantePlantillaOID;
    private String nombrePreguntaDominantePlantilla;

    /**
     * @return the encuestasId
     */
    public Integer getEncuestasId() {
        return encuestasId;
    }

    /**
     * @param encuestasId the encuestasId to set
     */
    public void setEncuestasId(Integer encuestasId) {
        this.encuestasId = encuestasId;
    }

    /**
     * @return the nombrePlantilla
     */
    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    /**
     * @param nombrePlantilla the nombrePlantilla to set
     */
    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    /**
     * @return the clavePlantilla
     */
    public String getClavePlantilla() {
        return clavePlantilla;
    }

    /**
     * @param clavePlantilla the clavePlantilla to set
     */
    public void setClavePlantilla(String clavePlantilla) {
        this.clavePlantilla = clavePlantilla;
    }

    /**
     * @return the gpsPlantilla
     */
    public String getGpsPlantilla() {
        return gpsPlantilla;
    }

    /**
     * @param gpsPlantilla the gpsPlantilla to set
     */
    public void setGpsPlantilla(String gpsPlantilla) {
        this.gpsPlantilla = gpsPlantilla;
    }

    /**
     * @return the operacionPlantilla
     */
    public boolean isOperacionPlantilla() {
        return operacionPlantilla;
    }

    /**
     * @param operacionPlantilla the operacionPlantilla to set
     */
    public void setOperacionPlantilla(boolean operacionPlantilla) {
        this.operacionPlantilla = operacionPlantilla;
    }

    /**
     * @return the statusIdPlantilla
     */
    public short getStatusIdPlantilla() {
        return statusIdPlantilla;
    }

    /**
     * @param statusIdPlantilla the statusIdPlantilla to set
     */
    public void setStatusIdPlantilla(short statusIdPlantilla) {
        this.statusIdPlantilla = statusIdPlantilla;
    }

    /**
     * @return the activaPlantilla
     */
    public boolean isActivaPlantilla() {
        return activaPlantilla;
    }

    /**
     * @param activaPlantilla the activaPlantilla to set
     */
    public void setActivaPlantilla(boolean activaPlantilla) {
        this.activaPlantilla = activaPlantilla;
    }

    /**
     * @return the esPlantilla
     */
    public boolean isEsPlantilla() {
        return esPlantilla;
    }

    /**
     * @param esPlantilla the esPlantilla to set
     */
    public void setEsPlantilla(boolean esPlantilla) {
        this.esPlantilla = esPlantilla;
    }

    /**
     * @return the seccionesPlantillaOID
     */
    public String getSeccionesPlantillaOID() {
        return seccionesPlantillaOID;
    }

    /**
     * @param seccionesPlantillaOID the seccionesPlantillaOID to set
     */
    public void setSeccionesPlantillaOID(String seccionesPlantillaOID) {
        this.seccionesPlantillaOID = seccionesPlantillaOID;
    }

    /**
     * @return the nombreSeccionPlantilla
     */
    public String getNombreSeccionPlantilla() {
        return nombreSeccionPlantilla;
    }

    /**
     * @param nombreSeccionPlantilla the nombreSeccionPlantilla to set
     */
    public void setNombreSeccionPlantilla(String nombreSeccionPlantilla) {
        this.nombreSeccionPlantilla = nombreSeccionPlantilla;
    }

    /**
     * @return the enabledSeccionPlantilla
     */
    public boolean isEnabledSeccionPlantilla() {
        return enabledSeccionPlantilla;
    }

    /**
     * @param enabledSeccionPlantilla the enabledSeccionPlantilla to set
     */
    public void setEnabledSeccionPlantilla(boolean enabledSeccionPlantilla) {
        this.enabledSeccionPlantilla = enabledSeccionPlantilla;
    }

    /**
     * @return the seccionCiclicaPlantilla
     */
    public boolean isSeccionCiclicaPlantilla() {
        return seccionCiclicaPlantilla;
    }

    /**
     * @param seccionCiclicaPlantilla the seccionCiclicaPlantilla to set
     */
    public void setSeccionCiclicaPlantilla(boolean seccionCiclicaPlantilla) {
        this.seccionCiclicaPlantilla = seccionCiclicaPlantilla;
    }

    /**
     * @return the seccionVisiblePlantilla
     */
    public boolean isSeccionVisiblePlantilla() {
        return seccionVisiblePlantilla;
    }

    /**
     * @param seccionVisiblePlantilla the seccionVisiblePlantilla to set
     */
    public void setSeccionVisiblePlantilla(boolean seccionVisiblePlantilla) {
        this.seccionVisiblePlantilla = seccionVisiblePlantilla;
    }

    /**
     * @return the ordenSeccionPlantilla
     */
    public short getOrdenSeccionPlantilla() {
        return ordenSeccionPlantilla;
    }

    /**
     * @param ordenSeccionPlantilla the ordenSeccionPlantilla to set
     */
    public void setOrdenSeccionPlantilla(short ordenSeccionPlantilla) {
        this.ordenSeccionPlantilla = ordenSeccionPlantilla;
    }

    /**
     * @return the preguntasPlantillaOID
     */
    public String getPreguntasPlantillaOID() {
        return preguntasPlantillaOID;
    }

    /**
     * @param preguntasPlantillaOID the preguntasPlantillaOID to set
     */
    public void setPreguntasPlantillaOID(String preguntasPlantillaOID) {
        this.preguntasPlantillaOID = preguntasPlantillaOID;
    }

    /**
     * @return the nombrePreguntaPlantilla
     */
    public String getNombrePreguntaPlantilla() {
        return nombrePreguntaPlantilla;
    }

    /**
     * @param nombrePreguntaPlantilla the nombrePreguntaPlantilla to set
     */
    public void setNombrePreguntaPlantilla(String nombrePreguntaPlantilla) {
        this.nombrePreguntaPlantilla = nombrePreguntaPlantilla;
    }

    /**
     * @return the preguntaDinamicaPlantilla
     */
    public boolean isPreguntaDinamicaPlantilla() {
        return preguntaDinamicaPlantilla;
    }

    /**
     * @param preguntaDinamicaPlantilla the preguntaDinamicaPlantilla to set
     */
    public void setPreguntaDinamicaPlantilla(boolean preguntaDinamicaPlantilla) {
        this.preguntaDinamicaPlantilla = preguntaDinamicaPlantilla;
    }

    /**
     * @return the variablePreguntaPlantilla
     */
    public String getVariablePreguntaPlantilla() {
        return variablePreguntaPlantilla;
    }

    /**
     * @param variablePreguntaPlantilla the variablePreguntaPlantilla to set
     */
    public void setVariablePreguntaPlantilla(String variablePreguntaPlantilla) {
        this.variablePreguntaPlantilla = variablePreguntaPlantilla;
    }

    /**
     * @return the enabledPreguntaPlantilla
     */
    public boolean isEnabledPreguntaPlantilla() {
        return enabledPreguntaPlantilla;
    }

    /**
     * @param enabledPreguntaPlantilla the enabledPreguntaPlantilla to set
     */
    public void setEnabledPreguntaPlantilla(boolean enabledPreguntaPlantilla) {
        this.enabledPreguntaPlantilla = enabledPreguntaPlantilla;
    }

    /**
     * @return the visiblePreguntaPlantilla
     */
    public boolean isVisiblePreguntaPlantilla() {
        return visiblePreguntaPlantilla;
    }

    /**
     * @param visiblePreguntaPlantilla the visiblePreguntaPlantilla to set
     */
    public void setVisiblePreguntaPlantilla(boolean visiblePreguntaPlantilla) {
        this.visiblePreguntaPlantilla = visiblePreguntaPlantilla;
    }

    /**
     * @return the tiposPreguntasPlantilla
     */
    public short getTiposPreguntasPlantilla() {
        return tiposPreguntasPlantilla;
    }

    /**
     * @param tiposPreguntasPlantilla the tiposPreguntasPlantilla to set
     */
    public void setTiposPreguntasPlantilla(short tiposPreguntasPlantilla) {
        this.tiposPreguntasPlantilla = tiposPreguntasPlantilla;
    }

    /**
     * @return the nombreTipoPreguntasPlantilla
     */
    public String getNombreTipoPreguntasPlantilla() {
        return nombreTipoPreguntasPlantilla;
    }

    /**
     * @param nombreTipoPreguntasPlantilla the nombreTipoPreguntasPlantilla to set
     */
    public void setNombreTipoPreguntasPlantilla(String nombreTipoPreguntasPlantilla) {
        this.nombreTipoPreguntasPlantilla = nombreTipoPreguntasPlantilla;
    }

    /**
     * @return the listasPlantillaOID
     */
    public String getListasPlantillaOID() {
        return listasPlantillaOID;
    }

    /**
     * @param listasPlantillaOID the listasPlantillaOID to set
     */
    public void setListasPlantillaOID(String listasPlantillaOID) {
        this.listasPlantillaOID = listasPlantillaOID;
    }

    /**
     * @return the nombreListaPlantilla
     */
    public String getNombreListaPlantilla() {
        return nombreListaPlantilla;
    }

    /**
     * @param nombreListaPlantilla the nombreListaPlantilla to set
     */
    public void setNombreListaPlantilla(String nombreListaPlantilla) {
        this.nombreListaPlantilla = nombreListaPlantilla;
    }

    /**
     * @return the opcionesPlantillaOID
     */
    public String getOpcionesPlantillaOID() {
        return opcionesPlantillaOID;
    }

    /**
     * @param opcionesPlantillaOID the opcionesPlantillaOID to set
     */
    public void setOpcionesPlantillaOID(String opcionesPlantillaOID) {
        this.opcionesPlantillaOID = opcionesPlantillaOID;
    }

    /**
     * @return the nombreOpcionesPlantillaOID
     */
    public String getNombreOpcionesPlantilla() {
        return nombreOpcionesPlantilla;
    }

    /**
     * @param nombreOpcionesPlantillaO the nombreOpcionesPlantillaOID to set
     */
    public void setNombreOpcionesPlantilla(String nombreOpcionesPlantilla) {
        this.nombreOpcionesPlantilla = nombreOpcionesPlantilla;
    }

    /**
     * @return the visibleOpcionesPlantilla
     */
    public boolean isVisibleOpcionesPlantilla() {
        return visibleOpcionesPlantilla;
    }

    /**
     * @param visibleOpcionesPlantilla the visibleOpcionesPlantilla to set
     */
    public void setVisibleOpcionesPlantilla(boolean visibleOpcionesPlantilla) {
        this.visibleOpcionesPlantilla = visibleOpcionesPlantilla;
    }

    /**
     * @return the preguntaDominantePlantillaOID
     */
    public String getPreguntaDominantePlantillaOID() {
        return preguntaDominantePlantillaOID;
    }

    /**
     * @param preguntaDominantePlantillaOID the preguntaDominantePlantillaOID to set
     */
    public void setPreguntaDominantePlantillaOID(String preguntaDominantePlantillaOID) {
        this.preguntaDominantePlantillaOID = preguntaDominantePlantillaOID;
    }

    /**
     * @return the nombrePreguntaDominantePlantilla
     */
    public String getNombrePreguntaDominantePlantilla() {
        return nombrePreguntaDominantePlantilla;
    }

    /**
     * @param nombrePreguntaDominantePlantilla the nombrePreguntaDominantePlantilla to set
     */
    public void setNombrePreguntaDominantePlantilla(String nombrePreguntaDominantePlantilla) {
        this.nombrePreguntaDominantePlantilla = nombrePreguntaDominantePlantilla;
    }
    
    
    
}
