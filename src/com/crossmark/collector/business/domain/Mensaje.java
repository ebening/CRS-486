package com.crossmark.collector.business.domain;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by christian on 05/01/2015.
 */
public class Mensaje implements Serializable{


    private String oid;
    private Usuario usuario;
    private TipoMensaje tipoMensaje;
    private Equipo equipo;
    private String usuarioDestinatarioOID;
    private Date dFecha;
    private String fecha;
    private String mensaje;


    public Mensaje() {
        usuario = new Usuario();
        equipo = new Equipo();
        tipoMensaje = new TipoMensaje();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String getUsuarioDestinatarioOID() {
        return usuarioDestinatarioOID;
    }

    public void setUsuarioDestinatarioOID(String usuarioDestinatarioOID) {
        this.usuarioDestinatarioOID = usuarioDestinatarioOID;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getdFecha() {
        return dFecha;
    }

    public void setdFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
