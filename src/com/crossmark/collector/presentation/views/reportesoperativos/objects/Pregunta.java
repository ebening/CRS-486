package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Pregunta {

    private int encuestasId;
    private String encuesta;
    private String seccionesOID;
    private String seccion;
    private String preguntasOID;
    private String pregunta;
    private String listasOID;
    private String opcionesOID;
    private String opcion;
    private int agrupadoresID;
    private String agrupador;
    private boolean activo;
    private int agrupadoresOpcionesId;
    private int equiposId;

    private Agrupador agrupadorSeleccionado;

    private List<Pregunta> opciones;

    public Pregunta() {
        opciones = new ArrayList<>();
    }

    public int getEncuestasId() {
        return encuestasId;
    }

    public void setEncuestasId(int encuestasId) {
        this.encuestasId = encuestasId;
    }

    public String getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(String encuesta) {
        this.encuesta = encuesta;
    }

    public String getSeccionesOID() {
        return seccionesOID;
    }

    public void setSeccionesOID(String seccionesOID) {
        this.seccionesOID = seccionesOID;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getPreguntasOID() {
        return preguntasOID;
    }

    public void setPreguntasOID(String preguntasOID) {
        this.preguntasOID = preguntasOID;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getListasOID() {
        return listasOID;
    }

    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
    }

    public String getOpcionesOID() {
        return opcionesOID;
    }

    public void setOpcionesOID(String opcionesOID) {
        this.opcionesOID = opcionesOID;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getAgrupadoresID() {
        return agrupadoresID;
    }

    public void setAgrupadoresID(int agrupadoresID) {
        this.agrupadoresID = agrupadoresID;
    }

    public String getAgrupador() {
        return agrupador;
    }

    public void setAgrupador(String agrupador) {
        this.agrupador = agrupador;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Pregunta> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Pregunta> opciones) {
        this.opciones = opciones;
    }

    public Agrupador getAgrupadorSeleccionado() {
        return agrupadorSeleccionado;
    }

    public void setAgrupadorSeleccionado(Agrupador agrupadorSeleccionado) {
        this.agrupadorSeleccionado = agrupadorSeleccionado;
    }

    public int getAgrupadoresOpcionesId() {
        return agrupadoresOpcionesId;
    }

    public void setAgrupadoresOpcionesId(int agrupadoresOpcionesId) {
        this.agrupadoresOpcionesId = agrupadoresOpcionesId;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

}
