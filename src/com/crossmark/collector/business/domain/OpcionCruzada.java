package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 * Created by christian on 29/12/2014.
 */
public class OpcionCruzada implements Serializable{

    private String oid;
    private String oidLista;
    private String valor;
    private String variable;
    private boolean activa;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
