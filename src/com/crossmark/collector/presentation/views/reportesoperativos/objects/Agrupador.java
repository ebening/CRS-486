package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.util.UUID;

/**
 * Agrupacion para reporte de solucion de incidencias.
 */
public class Agrupador {

    private String idInterno;

    private int agrupadoresId;
    private int equiposId;
    private String nombre;

    public Agrupador() {
        idInterno = UUID.randomUUID().toString();
    }

    public int getAgrupadoresId() {
        return agrupadoresId;
    }

    public void setAgrupadoresId(int agrupadoresId) {
        this.agrupadoresId = agrupadoresId;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

}
