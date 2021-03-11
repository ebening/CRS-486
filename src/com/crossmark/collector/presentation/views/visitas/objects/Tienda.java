/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author jdominguez
 */
public class Tienda implements Serializable{
    private int TiendasId;
    private int ProyectosId;
    private String Nombre;
    private String clave;
    private String Calle;
    private String EntreCalle;
    private String colonia;
    private String cp;
    private int EstadosId;
    private int CiudadesId;
    private String numero;
    private int TerritoriosNativoId;
    private boolean Activo;
    private boolean pertenece;
    private boolean enable;
    private boolean unselect;
    private float coordenadaX;
    private float coordenadaY;
    private int CadenasId;
    private int FormatosId;
    private String Ciudades;
    private String TerritoriosNativos;
    private String cadena;
    private String formato;
    private String Estados;

    public Tienda() {
    }
    
    
    
    /**
     * 
     * @param id
     * @param nombre
     * @param clave
     * @param activo
     * @param CadenasId
     * @param EstadosId
     * @param CiudadesId 
     */
    public Tienda(int id, String nombre,String clave, boolean activo, int CadenasId, int EstadosId, int CiudadesId){
        this.TiendasId = id;
        this.Nombre = nombre;
        this.clave = clave;
        this.Activo = activo;
        this.EstadosId = EstadosId;
        this.CiudadesId = CiudadesId;
        this.CadenasId = CadenasId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return TiendasId;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.TiendasId = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return Calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.Calle = calle;
    }

    /**
     * @return the entreCalle
     */
    public String getEntreCalle() {
        return EntreCalle;
    }

    /**
     * @param entreCalle the entreCalle to set
     */
    public void setEntreCalle(String entreCalle) {
        this.EntreCalle = entreCalle;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * @return the idEstado
     */
    public int getIdEstado() {
        return EstadosId;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(int idEstado) {
        this.EstadosId = idEstado;
    }

    /**
     * @return the idCiudad
     */
    public int getIdCiudad() {
        return CiudadesId;
    }

    /**
     * @param idCiudad the idCiudad to set
     */
    public void setIdCiudad(int idCiudad) {
        this.CiudadesId = idCiudad;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return Activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.Activo = activo;
    }

    /**
     * @return the coordenadaX
     */
    public float getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * @param coordenadaX the coordenadaX to set
     */
    public void setCoordenadaX(float coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /**
     * @return the coordenadaY
     */
    public float getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * @param coordenadaY the coordenadaY to set
     */
    public void setCoordenadaY(float coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    /**
     * @return the idCadena
     */
    public int getIdCadena() {
        return CadenasId;
    }

    /**
     * @param idCadena the idCadena to set
     */
    public void setIdCadena(int idCadena) {
        this.CadenasId = idCadena;
    }

    /**
     * @return the idFormato
     */
    public int getIdFormato() {
        return FormatosId;
    }

    /**
     * @param idFormato the idFormato to set
     */
    public void setIdFormato(int idFormato) {
        this.FormatosId = idFormato;
    }

    /**
     * @return the territorio
     */
    public String getTerritorio() {
        return TerritoriosNativos;
    }

    /**
     * @param territorio the territorio to set
     */
    public void setTerritorio(String territorio) {
        this.TerritoriosNativos = territorio;
    }

    /**
     * @return the cadena
     */
    public String getCadena() {
        return cadena;
    }

    /**
     * @param cadena the cadena to set
     */
    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the TerritoriosNativoId
     */
    public int getTerritoriosNativoId() {
        return TerritoriosNativoId;
    }

    /**
     * @param TerritoriosNativoId the TerritoriosNativoId to set
     */
    public void setTerritoriosNativoId(int TerritoriosNativoId) {
        this.TerritoriosNativoId = TerritoriosNativoId;
    }

    public int getTiendasId() {
        return TiendasId;
    }

    public void setTiendasId(int TiendasId) {
        this.TiendasId = TiendasId;
    }

    public int getEstadosId() {
        return EstadosId;
    }

    public void setEstadosId(int EstadosId) {
        this.EstadosId = EstadosId;
    }

    public int getCiudadesId() {
        return CiudadesId;
    }

    public void setCiudadesId(int CiudadesId) {
        this.CiudadesId = CiudadesId;
    }

    public boolean isPertenece() {
        return pertenece;
    }

    public void setPertenece(boolean pertenece) {
        this.pertenece = pertenece;
    }

    public int getCadenasId() {
        return CadenasId;
    }

    public void setCadenasId(int CadenasId) {
        this.CadenasId = CadenasId;
    }

    public int getFormatosId() {
        return FormatosId;
    }

    public void setFormatosId(int FormatosId) {
        this.FormatosId = FormatosId;
    }

    public String getCiudades() {
        return Ciudades;
    }

    public void setCiudades(String Ciudades) {
        this.Ciudades = Ciudades;
    }

    public String getTerritoriosNativos() {
        return TerritoriosNativos;
    }

    public void setTerritoriosNativos(String TerritoriosNativos) {
        this.TerritoriosNativos = TerritoriosNativos;
    }

    public String getEstados() {
        return Estados;
    }

    public void setEstados(String Estados) {
        this.Estados = Estados;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isUnselect() {
        return unselect;
    }

    public void setUnselect(boolean unselect) {
        this.unselect = unselect;
    }

    public int getProyectosId() {
        return ProyectosId;
    }

    public void setProyectosId(int ProyectosId) {
        this.ProyectosId = ProyectosId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.TiendasId;
        hash = 71 * hash + Objects.hashCode(this.Nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tienda other = (Tienda) obj;
        if (this.TiendasId != other.TiendasId) {
            return false;
        }
        return true;
    }
    
    
}
