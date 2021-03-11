package com.crossmark.collector.business.domain;

import com.crossmark.collector.presentation.views.visitas.objects.*;

import java.io.Serializable;

/**
 * Created by christian on 05/12/2014.
 */
public class Tienda implements Serializable{

    private Integer id;
    private String nombre;
    private String clave;
    private String direccion;
    private String cp;
    private Ciudad ciudad;
    private Integer numero;
    private boolean activo;
    private String coordenadaX;
    private String coordenadaY;
    private Cadena cadena;
    private Formato formato;
    private Territorio territorioNativo;

    public Tienda() {
        territorioNativo = new Territorio();
        this.ciudad = new Ciudad();
        this.formato = new Formato();
        this.cadena = new Cadena();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setEstado(Estado estado) {
        this.ciudad.setEstado(estado);
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Estado getEstado() {
        return ciudad.getEstado();
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public String getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public Cadena getCadena() {
        return cadena;
    }

    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Territorio getTerritorioNativo() {
        return territorioNativo;
    }

    public void setTerritorioNativo(Territorio territorioNativo) {
        this.territorioNativo = territorioNativo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
}
