/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.*;
import java.io.Serializable;


/**
 *
 * @author franciscom
 */
public class UsuarioTerritorio implements Serializable {
    private String usuariosOID;
    private String nroEmpleado;
    private String userName;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    //private String calle;
    private String Direccion;
    //private String entreCalle;
    //private String numero;
    private String colonia;
    private String cP;
    private int ciudadesId;
    private boolean activo;//bit
    private int territorioNativoId;
    private String territorioNativo;
    private int perfilesId;
    private int estadosId;
    private int territoriosId;
    private String nombreCiudades;
    private String nombreEstados;

    /*
     U.UsuariosOID, U.NroEmpleado, U.UserName, U.Nombre, U.ApellidoMaterno, U.Direccion,--U.Calle, 
					U.ApellidoPaterno, --U.EntreCalle, U.Numero, 
					U.Colonia, U.CP, U.CiudadesId, 
                      U.Activo, U.TerritorioNativoId, T.Nombre AS TerritorioNativo, U.PerfilesId, UT.TerritoriosId, C.Nombre AS NombreCiudades, EDO.Nombre AS NombreEstados, 
                      EDO.EstadosId
     */
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

    public int getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(int estadosId) {
        this.estadosId = estadosId;
    }

    public int getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(int territoriosId) {
        this.territoriosId = territoriosId;
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
}
