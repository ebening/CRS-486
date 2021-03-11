package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.util.List;

/**
 * Modelo para datos en configuracion de reporte de solucion de incidencias.
 */
public class EncuestaCaptura {

    private int encuestaId;
    private String nombre;
    private boolean activo;

    private List<Pregunta> preguntas;

    public EncuestaCaptura() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEncuestaId() {
        return encuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        this.encuestaId = encuestaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

}
