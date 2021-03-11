/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usss
 */
public class Listas implements Serializable {
    
    private static long serialVersionUID = 1L;
    private String listasOID;
    private String nombreLista;
    private String activa;
    private boolean catalogo;
    private int idTipoLista;
    private List<Opciones> miListaOpciones = new ArrayList<Opciones>();

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
     * @return the nombreLista
     */
    public String getNombreLista() {
        return nombreLista;
    }

    /**
     * @param nombreLista the nombreLista to set
     */
    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    /**
     * @return the activa
     */
    public String getActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(String activa) {
        this.activa = activa;
    }

    /**
     * @return the catalogo
     */
    public boolean isCatalogo() {
        return catalogo;
    }

    /**
     * @param catalogo the catalogo to set
     */
    public void setCatalogo(boolean catalogo) {
        this.catalogo = catalogo;
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
     * @return the miListaOpciones
     */
    public List<Opciones> getMiListaOpciones() {
        return miListaOpciones;
    }

    /**
     * @param miListaOpciones the miListaOpciones to set
     */
    public void setMiListaOpciones(List<Opciones> miListaOpciones) {
        this.miListaOpciones = miListaOpciones;
    }
    
    
}
