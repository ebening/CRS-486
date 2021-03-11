/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author franciscom
 */
public class Usuario implements Serializable {
    private String usuariosOID;
    private String nroEmpleado;
    private String userName;
    private String nombre;
    private String nombre2;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String direccion;
    private String colonia;
    private String cP;
    private int ciudadesId;
    private boolean activo;//bit
    private int territorioNativoId;
    private String territorioNativo;
    private int perfilesId;
    private int unidadesNegociosId;
    private int equiposId;
    private int estadosId;
    private String nombreCiudades;
    private String nombreEstados;
    private String estatus;
    private String nombreEquipo;
    private String nombrePerfil;
    private String nombrePuesto;
    private Integer puestosId;
    private String mail;
    private float altitud;
    private float longitud;
    private String password;
    
    
    /*
    U.UsuariosOID, U.NroEmpleado, U.UserName, U.Nombre, U.ApellidoMaterno, U.ApellidoPaterno,U.Direccion, U.Colonia, U.CP, U.CiudadesId, 
				   U.Activo,CASE WHEN U.Activo = 1 THEN 'Activo' ELSE 'Inactivo' END AS Estatus,U.Mail, U.TerritorioNativoId, T.Nombre AS TerritorioNativo, U.PerfilesId, 
				   E.UnidadesNegociosId, U.EquiposId, C.Nombre AS NombreCiudades, EDO.Nombre AS NombreEstados, EDO.EstadosId, E.Nombre AS NombreEquipo, 
				   dbo.Perfiles.Nombre AS NombrePerfil, dbo.Puestos.Nombre AS NombrePuesto, U.PuestosId, U.Altitud, U.Longitud
				   
    */
    public Usuario() {
    }
    
    public List<Equipo> getEquiposByUsuario(){
        List<Equipo> r = new ArrayList<>();
        if(nombreEquipo != null && !nombreEquipo.equals("")){
            String [] a = nombreEquipo.split("@");
            for(String ax : a){
                Equipo eq = new Equipo();
                String [] b = ax.split(",");
                eq.setId(Integer.valueOf(b[0]));
                eq.setNombre(b[1]);
                r.add(eq);
            }
        }
        return r;
    }

    public String getUsuariosOID() {
        return usuariosOID;
    }

    public void setUsuariosOID(String usuariosOID) {
        this.usuariosOID = usuariosOID;
    }

    public String getNroEmpleado() {
        return nroEmpleado;
    }

    public void setNroEmpleado(String nroEmpleado) {
        this.nroEmpleado = nroEmpleado;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getcP() {
        return cP;
    }

    public void setcP(String cP) {
        this.cP = cP;
    }

    public int getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(int ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getTerritorioNativoId() {
        return territorioNativoId;
    }

    public void setTerritorioNativoId(int territorioNativoId) {
        this.territorioNativoId = territorioNativoId;
    }

    public String getTerritorioNativo() {
        return territorioNativo;
    }

    public void setTerritorioNativo(String territorioNativo) {
        this.territorioNativo = territorioNativo;
    }

    public int getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(int perfilesId) {
        this.perfilesId = perfilesId;
    }

    public int getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(int unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

    public int getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(int estadosId) {
        this.estadosId = estadosId;
    }

    public String getNombreCiudades() {
        return nombreCiudades;
    }

    public void setNombreCiudades(String nombreCiudades) {
        this.nombreCiudades = nombreCiudades;
    }

    public String getNombreEstados() {
        return nombreEstados;
    }

    public void setNombreEstados(String nombreEstados) {
        this.nombreEstados = nombreEstados;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public Integer getPuestosId() {
        return puestosId;
    }

    public void setPuestosId(Integer puestosId) {
        this.puestosId = puestosId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public float getAltitud() {
        return altitud;
    }

    public void setAltitud(float altitud) {
        this.altitud = altitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    



}