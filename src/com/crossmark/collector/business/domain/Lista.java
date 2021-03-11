package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 29/12/2014.
 */
public class Lista implements Serializable{

    private String oid;
    private String nombre;
    private String clave;
    private TipoLista tipoLista;
    private List<OpcionPlana> opcionPlanaList;
    private List<OpcionCruzada> opcionCruzadasList;


    public Lista() {
        oid = "noOid";
        tipoLista = new TipoLista();
        opcionCruzadasList = new ArrayList<>();
        opcionPlanaList = new ArrayList<>();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TipoLista getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(TipoLista tipoLista) {
        this.tipoLista = tipoLista;
    }

    public List<OpcionPlana> getOpcionPlanaList() {
        return opcionPlanaList;
    }

    public void setOpcionPlanaList(List<OpcionPlana> opcionPlanaList) {
        this.opcionPlanaList = opcionPlanaList;
    }

    public List<OpcionCruzada> getOpcionCruzadasList() {
        return opcionCruzadasList;
    }

    public void setOpcionCruzadasList(List<OpcionCruzada> opcionCruzadasList) {
        this.opcionCruzadasList = opcionCruzadasList;
    }
}
