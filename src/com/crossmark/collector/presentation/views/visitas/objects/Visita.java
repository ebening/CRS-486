/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class Visita {
    
    private Date FechaIni;
    private Date FechaFin;
    
    private int RutasId;
    private int VisitasId;
    private int TiendasId; 
    private int EncuestasId;
    private int ProyectosId;
    private int TerritoriosId;
    private int UnidadesNegocioId;
    private int EquiposId;
    private int TareasId;
    
    private String Nombre;
    private String UserName;
    private String ApellidoMaterno;
    private String ApellidoPaterno;
    private String Descripcion;
    private String Proyectos;
    private String Promotor;
    private String Encuesta;
    private String Tiendas;
    private String Visitas;
    private String Ruta;
    private String UsuariosOID;
    
    private List<VisitaTienda> visitasTiendas;
    

    public Visita(){
        visitasTiendas = new ArrayList<>();
    }
    
    public Visita(String Visitas, String Ruta, String Proyectos, String Encuesta, 
            String Nombre, String ApellidoPaterno, String ApellidoMaterno, Date FechaIni){
        this.Visitas = Visitas;
        this.Ruta = Ruta;
        this.Proyectos = Proyectos;
        this.Encuesta = Encuesta;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.FechaIni = FechaIni;
        this.Promotor = Nombre + " " + ApellidoPaterno + " " + ApellidoMaterno;
        visitasTiendas = new ArrayList<>();
    }
    
    public String getFechaIniFormated(){
        return new SimpleDateFormat("dd/MM/yyyy").format(FechaIni);
    }
    
    public String getFechaFinFormated(){
        return new SimpleDateFormat("dd/MM/yyyy").format(FechaFin);
    }
    /**
     * @return the RutasId
     */
    public int getRutasId() {
        return RutasId;
    }

    /**
     * @param RutasId the RutasId to set
     */
    public void setRutasId(int RutasId) {
        this.RutasId = RutasId;
    }

    /**
     * @return the Ruta
     */
    public String getRuta() {
        return Ruta;
    }

    /**
     * @param Ruta the Ruta to set
     */
    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
    }

    /**
     * @return the VisitasId
     */
    public int getVisitasId() {
        return VisitasId;
    }

    /**
     * @param VisitasId the VisitasId to set
     */
    public void setVisitasId(int VisitasId) {
        this.VisitasId = VisitasId;
    }

    /**
     * @return the Visitas
     */
    public String getVisitas() {
        return Visitas;
    }

    /**
     * @param Visitas the Visitas to set
     */
    public void setVisitas(String Visitas) {
        this.Visitas = Visitas;
    }

    /**
     * @return the TiendasId
     */
    public int getTiendasId() {
        return TiendasId;
    }

    /**
     * @param TiendasId the TiendasId to set
     */
    public void setTiendasId(int TiendasId) {
        this.TiendasId = TiendasId;
    }

    /**
     * @return the Tiendas
     */
    public String getTiendas() {
        return Tiendas;
    }

    /**
     * @param Tiendas the Tiendas to set
     */
    public void setTiendas(String Tiendas) {
        this.Tiendas = Tiendas;
    }

    /**
     * @return the EncuestasId
     */
    public int getEncuestasId() {
        return EncuestasId;
    }

    /**
     * @param EncuestasId the EncuestasId to set
     */
    public void setEncuestasId(int EncuestasId) {
        this.EncuestasId = EncuestasId;
    }

    /**
     * @return the Encuesta
     */
    public String getEncuesta() {
        return Encuesta;
    }

    /**
     * @param Encuesta the Encuesta to set
     */
    public void setEncuesta(String Encuesta) {
        this.Encuesta = Encuesta;
    }

    /**
     * @return the FechaIni
     */
    public Date getFechaIni() {
        return FechaIni;
    }

    /**
     * @param FechaIni the FechaIni to set
     */
    public void setFechaIni(Date FechaIni) {
        this.FechaIni = FechaIni;
    }

    /**
     * @return the FechaFin
     */
    public Date getFechaFin() {
        return FechaFin;
    }

    /**
     * @param FechaFin the FechaFin to set
     */
    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
    }

    /**
     * @return the UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * @param UserName the UserName to set
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * @return the ApellidoMaterno
     */
    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    /**
     * @param ApellidoMaterno the ApellidoMaterno to set
     */
    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    /**
     * @return the ApellidoPaterno
     */
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    /**
     * @param ApellidoPaterno the ApellidoPaterno to set
     */
    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the ProyectosId
     */
    public int getProyectosId() {
        return ProyectosId;
    }

    /**
     * @param ProyectosId the ProyectosId to set
     */
    public void setProyectosId(int ProyectosId) {
        this.ProyectosId = ProyectosId;
    }

    /**
     * @return the TerritoriosId
     */
    public int getTerritoriosId() {
        return TerritoriosId;
    }

    /**
     * @param TerritoriosId the TerritoriosId to set
     */
    public void setTerritoriosId(int TerritoriosId) {
        this.TerritoriosId = TerritoriosId;
    }

    /**
     * @return the UnidadesNegocioId
     */
    public int getUnidadesNegocioId() {
        return UnidadesNegocioId;
    }

    /**
     * @param UnidadesNegocioId the UnidadesNegocioId to set
     */
    public void setUnidadesNegocioId(int UnidadesNegocioId) {
        this.UnidadesNegocioId = UnidadesNegocioId;
    }

    /**
     * @return the EquiposId
     */
    public int getEquiposId() {
        return EquiposId;
    }

    /**
     * @param EquiposId the EquiposId to set
     */
    public void setEquiposId(int EquiposId) {
        this.EquiposId = EquiposId;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the Proyectos
     */
    public String getProyectos() {
        return Proyectos;
    }

    /**
     * @param Proyectos the Proyectos to set
     */
    public void setProyectos(String Proyectos) {
        this.Proyectos = Proyectos;
    }

    /**
     * @return the Promotor
     */
    public String getPromotor() {
        return Promotor;
    }

    /**
     * @param Promotor the Promotor to set
     */
    public void setPromotor(String Promotor) {
        this.Promotor = Promotor;
    }

    public int getTareasId() {
        return TareasId;
    }

    public void setTareasId(int TareasId) {
        this.TareasId = TareasId;
    }

    public String getUsuariosOID() {
        return UsuariosOID;
    }

    public void setUsuariosOID(String UsuariosOID) {
        this.UsuariosOID = UsuariosOID;
    }

    public List<VisitaTienda> getVisitasTiendas() {
        return visitasTiendas;
    }

    public void setVisitasTiendas(List<VisitaTienda> visitasTiendas) {
        this.visitasTiendas = visitasTiendas;
    }
    
    
}
