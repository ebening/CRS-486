package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.Date;

public class UnidadesNegocio implements Serializable,Comparable<UnidadesNegocio> {

    private static final long serialVersionUID = 1L;

    private Integer idUnidadNegocio;
    private String nombreUnidad;
    private boolean activo;
    private String horaIni;
    private String horaFin;
    private Date dHoraIni;
    private Date dHoraFin;
    private boolean clienteUnidad;

    public UnidadesNegocio(Integer idUnidadNegocio,String nombreUnidad) {
        this.idUnidadNegocio = idUnidadNegocio;
        this.nombreUnidad = nombreUnidad;
    }

    public UnidadesNegocio() {
    }

    public UnidadesNegocio(Integer id){
        this.idUnidadNegocio = id;
    }

    public Integer getIdUnidadNegocio() {
        return idUnidadNegocio;
    }

    public void setIdUnidadNegocio(Integer idUnidadNegocio) {
        this.idUnidadNegocio = idUnidadNegocio;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Date getdHoraFin() {
        return dHoraFin;
    }

    public void setdHoraFin(Date dHoraFin) {
        this.dHoraFin = dHoraFin;
    }

    public Date getdHoraIni() {
        return dHoraIni;
    }

    public void setdHoraIni(Date dHoraIni) {
        this.dHoraIni = dHoraIni;
    }

    public void setClienteUnidad(boolean clienteUnidad) {
        this.clienteUnidad = clienteUnidad;
    }

    public boolean isClienteUnidad() {
        return clienteUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadNegocio != null ? idUnidadNegocio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadesNegocio)) {
            return false;
        }
        UnidadesNegocio other = (UnidadesNegocio) object;
        if ((this.idUnidadNegocio == null && other.idUnidadNegocio != null) || (this.idUnidadNegocio != null && !this.idUnidadNegocio.equals(other.idUnidadNegocio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombreUnidad();
    }


    @Override
    public int compareTo(UnidadesNegocio o) {
     return  Integer.compare(o.getIdUnidadNegocio(),getIdUnidadNegocio());
    }
}
