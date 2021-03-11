/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.EquiposUsuarios;
import com.crossmark.collector.business.domain.TerritoriosUsuariosIn;
import com.crossmark.collector.persistence.daos.DAOEquiposTerritoriosUsuarios;
import com.crossmark.collector.persistence.daos.DAOEquiposTerritoriosUsuariosIn;
import com.crossmark.collector.persistence.daos.DAOEquiposUsuarios;
import com.crossmark.collector.persistence.daos.DAORegiones;
import com.crossmark.collector.persistence.daos.DAOUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.List;

/**
 *
 * @author franciscom
 */
public class ServiceUsuariosImpl implements ServiceUsuarios {
    private DAOUsuarios daoUsuarios;
    private DAORegiones daoRegiones;
    //private DAOEquipos daoEquipos;
    private DAOEquiposUsuarios daoEquiposUsuarios;
    private DAOEquiposTerritoriosUsuariosIn daoEquiposTerritoriosUsuariosIn;
    //private DAOEquiposTerritorios daoEquiposTerritorios;
    private DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios;
    
    @Override
    public List<Usuario> getListUsuarios(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo){
        
        return getDaoUsuarios().getUsuariosByParams(usuariosOID, userName, nombre, apellidoMaterno, apellidoPaterno, direccion,
            colonia, cP, nroEmpleado, ciudadesId,estadosId, territoriosId, territorioNativoId, perfilesId, unidadesNegociosId, equiposId,
            pertenece, activo);
            
    }
    
    @Override
    public void actualizarUsuario(Usuario usuario){
        getDaoUsuarios().actualizarUsuario(usuario);
    }
    
    @Override
    public String eliminarUsuario(String usuariosOID){
        return getDaoUsuarios().eliminarUsuario(usuariosOID);
    }
    
    //Metodos para usuarios territorios
    /*
    @Override
    public List<UsuarioTerritorio> getListUsuariosTerritorios(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, 
            Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, String nroEmpleado,  Integer perfilesId, Integer unidadesNegociosId, Integer equiposId){
        
        return getDaoUsuarios().getListUsuariosTerritorios(usuariosOID, userName, nombre, apellidoMaterno, apellidoPaterno, ciudadesId, estadosId, territoriosId, 
                territorioNativoId, nroEmpleado, perfilesId, unidadesNegociosId, equiposId);
    }
    */
    @Override
    public List<Region> getRegionesByFind(Integer regionId){
        return daoRegiones.getRegionesByFind(regionId);
    }
    
    
    @Override
    public void insertarUsuarioTerritorio(String usuariosOID, Integer territoriosId, Integer equiposId){
        Utileria.logger(getClass()).error("insertarUsuarioTerritorio getUsuariosOID:"+usuariosOID+"      getTerritorioId:"+territoriosId);
        getDaoUsuarios().insertarUsuarioTerritorio(usuariosOID, territoriosId, equiposId);
    }
    
    //Lista de equipos
    @Override
    public List<EquiposUsuarios> getEquiposByUserFind(String usuariosOID,Integer equiposId,Integer pertenece){
        System.out.println("getEquiposByUserFind   usuariosOID:"+usuariosOID+"   equiposId:"+equiposId+"   pertenece:"+pertenece);
        return daoEquiposUsuarios.getListaEquiposUsuarios(usuariosOID, equiposId, pertenece);
        //return daoEquiposUsuarios.getListaEquiposUsuarios(usuariosOID, equiposId,  pertenece);
    }
    
    @Override
    public void actualizarEquiposUsuario(Integer equiposId, String usuariosID, int territoriosId){
        daoEquiposUsuarios.actualizarEquiposUsuario(equiposId, usuariosID, territoriosId);
    }
    
    @Override
    public void eliminarEquiposPorUsuario(String usuariosOID, int equiposId){
        daoEquiposUsuarios.eliminarPorUsuario(usuariosOID, equiposId);
    }
    
    //Lista de territorios
    @Override//String usuariosOID, String cadenaEquipos, Integer territoriosId, byte pertenece
    public List<TerritoriosUsuariosIn> getEquiposTerritoriosInFind(String usuariosOID, String cadenaEquipos, Integer territoriosId, Integer pertenece){
        return daoEquiposTerritoriosUsuariosIn.getEquiposTerritoriosInFind(usuariosOID, cadenaEquipos, territoriosId, pertenece);
    }
    
    @Override
    public List<TerritoriosUsuarios> getTerritoriosByEquipoId(String usuariosOID, int EquiposId) {
        return getDaoEquiposTerritoriosUsuarios().getListaTerritoriosPorUsuario(usuariosOID, EquiposId, null, (byte)0);
    }
    
// ****************************** Getters & Setters *************************** //

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAORegiones getDaoRegiones() {
        return daoRegiones;
    }

    public void setDaoRegiones(DAORegiones daoRegiones) {
        this.daoRegiones = daoRegiones;
    }

    public DAOEquiposUsuarios getDaoEquiposUsuarios() {
        return daoEquiposUsuarios;
    }

    public void setDaoEquiposUsuarios(DAOEquiposUsuarios daoEquiposUsuarios) {
        this.daoEquiposUsuarios = daoEquiposUsuarios;
    }

    public DAOEquiposTerritoriosUsuariosIn getDaoEquiposTerritoriosUsuariosIn() {
        return daoEquiposTerritoriosUsuariosIn;
    }

    public void setDaoEquiposTerritoriosUsuariosIn(DAOEquiposTerritoriosUsuariosIn daoEquiposTerritoriosUsuariosIn) {
        this.daoEquiposTerritoriosUsuariosIn = daoEquiposTerritoriosUsuariosIn;
    }

    public DAOEquiposTerritoriosUsuarios getDaoEquiposTerritoriosUsuarios() {
        return daoEquiposTerritoriosUsuarios;
    }

    public void setDaoEquiposTerritoriosUsuarios(DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios) {
        this.daoEquiposTerritoriosUsuarios = daoEquiposTerritoriosUsuarios;
    } 
    
}
