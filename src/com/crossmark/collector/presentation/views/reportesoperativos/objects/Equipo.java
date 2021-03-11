/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author franciscom
 */
public class Equipo implements Serializable {
    private Integer id;
    private String nombre;
    private boolean activo;
    private int regionId;
    private int unidadNegocioId;
    private List<Territorio> listaTerritorios;
    private Region region;
    private UnidadesNegocio unidadesNegocio;

    /*
      public Equipo(int id, String nombre, boolean activo, int regionid, int unidadNegocioId){
          this.id = id;
          this.nombre = nombre;
          this.activo = activo;
          this.regionId = regionid;
          this.unidadNegocioId = unidadNegocioId;
      }
  */
    public Equipo() {
        listaTerritorios = new ArrayList<>();
        region = new Region();
        unidadesNegocio = new UnidadesNegocio();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getUnidadNegocioId() {
        return unidadNegocioId;
    }

    public void setUnidadNegocioId(int unidadNegocioId) {
        this.unidadNegocioId = unidadNegocioId;
    }

    public List<Territorio> getListaTerritorios() {
        return listaTerritorios;
    }

    public void setListaTerritorios(List<Territorio> listaTerritorios) {
        this.listaTerritorios = listaTerritorios;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public UnidadesNegocio getUnidadesNegocio() {
        return unidadesNegocio;
    }

    public void setUnidadesNegocio(UnidadesNegocio unidadesNegocio) {
        this.unidadesNegocio = unidadesNegocio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();

    }


}
