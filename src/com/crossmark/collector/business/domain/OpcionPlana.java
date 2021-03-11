package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 * Created by christian on 29/12/2014.
 */
public class OpcionPlana implements Serializable {

    private String oid;
    private String oidLista;
    private String texto;
    private String valor;
    private String clave;
    private Integer orden;
    private String tag;
    private String codigoBarra;
    private boolean activa;
    private boolean enabled;
    private boolean visible;


    public OpcionPlana() {
        this.orden = 0;
        this.activa = false;
        this.enabled =false;
        this.visible =false;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOidLista() {
        return oidLista;
    }

    public void setOidLista(String oidLista) {
        this.oidLista = oidLista;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
