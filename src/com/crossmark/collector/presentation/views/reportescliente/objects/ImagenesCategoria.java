/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente.objects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class ImagenesCategoria implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private String opcionesOID;
    private String listasOID;
    private String texto;
    private Integer valor;
    private String clave;
    private Integer orden;
    private String tag;
    private String codigoBarra;

    public String getOpcionesOID() {
        return opcionesOID;
    }

    public void setOpcionesOID(String opcionesOID) {
        this.opcionesOID = opcionesOID;
    }

    public String getListasOID() {
        return listasOID;
    }

    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }
    
    
}
