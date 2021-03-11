/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.persistence.daos.DAOClientes;
import com.crossmark.collector.persistence.daos.DAOEncuestasUsuarios;
import com.crossmark.collector.persistence.daos.DAOEquiposRegionUsuarios;
import com.crossmark.collector.persistence.daos.DAOEquiposTerritoriosUsuarios;
import com.crossmark.collector.persistence.daos.DAOProyectosUsuarios;
import com.crossmark.collector.persistence.daos.DAORegionesUsuarios;
import com.crossmark.collector.persistence.daos.DAOSecciones;
import com.crossmark.collector.persistence.daos.DAOUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.EncuestasUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author franciscom
 */
public class ServiceRepCliResultadosImpl implements ServiceRepCliResultados, Serializable{
    
    private DAORegionesUsuarios daoRegionUsuarios;//Regiones
    private DAOEquiposRegionUsuarios daoEquiposRegionUsuarios;//Equipos
    private DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios;//Territorios
    private DAOClientes daoClientes;//Clientes
    private DAOProyectosUsuarios daoProyectosUsuarios;//Proyecto
    
    private DAOUsuarios daoUsuarios;
    private DAOSecciones daoSecciones;
    private DAOEncuestasUsuarios daoEncuestasUsuarios;
    
    @Override
    public List<RegionesUsuarios> getListRegiones(String usuariosOID){
        return daoRegionUsuarios.getListaRegionesPorUsuario(usuariosOID);
    }
    
    /*
    @Override
    public Region getRegionById(Integer regionId){
        Region region = null;
        List<Region> listaRegiones = null;
        listaRegiones = daoRegiones.getRegionesByFind(regionId);
        if(!listaRegiones.isEmpty()){
            region = listaRegiones.get(0);
        }
        return region;
    }
    */
    
    @Override
    public List<EquiposRegion> getListEquiposPorRegion(String usuariosOID,Integer regionesId){
        return daoEquiposRegionUsuarios.getListaEquiposPorRegionYUsuario(usuariosOID,regionesId);
    }
    
    /*
    @Override
    public Equipo getEquipoId(Integer equiposId){
        Equipo equipo = null;
        List<Equipo> listaEquipos = null;
        listaEquipos = getDaoEquipos().getEquiposByFind(equiposId,null,null);
        if(!listaEquipos.isEmpty()){
            equipo = listaEquipos.get(0);
        }
        return equipo;
    }
    */
    
    @Override
    public List<TerritoriosUsuarios> getListaTerritoriosPorUsuarioYEq(String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece){
        return daoEquiposTerritoriosUsuarios.getListaTerritoriosPorUsuario(usuariosOID, equiposId, territoriosId, pertenece);
    }
    
    /*
    @Override
    public EquipoTerritorio getEquiposTerritoriosById(Integer equipoId){
        
        EquipoTerritorio equipoTerritorio = null;
        List<EquipoTerritorio> listaEquipoTerritorio = null;
        listaEquipoTerritorio = getDaoEquiposTerritorios().getEquiposTerritoriosByFind(equipoId,null);
        if(!listaEquipoTerritorio.isEmpty()){
            equipoTerritorio = listaEquipoTerritorio.get(0);
        }
        return equipoTerritorio;
    }
    */
    
    @Override
    public List<Usuario> getUsuariosByParams(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo){
        
        return getDaoUsuarios().getUsuariosByParams(usuariosOID, userName, nombre, apellidoMaterno, apellidoPaterno, direccion,
            colonia, cP, nroEmpleado, ciudadesId,estadosId, territoriosId, territorioNativoId, perfilesId, unidadesNegociosId, equiposId,
            pertenece, activo);
    }
    
    @Override
    public List<Seccion> getSeccionesByParams(String seccionesOID,String nombre,Integer encuestasId,Integer orden, Integer activa, Integer enabled,Integer ciclica,
            Date fechaCreacion,Integer visible){
        return getDaoSecciones().getSeccionesByParams(seccionesOID, nombre, encuestasId, orden,  activa,  enabled, ciclica,
             fechaCreacion, visible);
    }
    
    ///Mandar un correo a los responsables de los daos
    @Override
    public List<Clientes> listaClientesByParams(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID){
        return daoClientes.listaClientesByParams(clientesId, unidadesNegociosId, equiposId, territoriosId, usuariosOID);
    }
    
    @Override
    public List<EncuestasUsuarios> getEncuestasByParams(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId, Integer proyectosId) {
        return daoEncuestasUsuarios.getListaEncuestasUsuarios(usuariosOID, clientesId, regionesId, territoriosId, equiposId, proyectosId);
    }
    
    @Override
    public List<ProyectosUsuarios> getListProyectosPorUsuario(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId){
        return daoProyectosUsuarios.getListaProyectosPorUsuario(usuariosOID, clientesId, regionesId, territoriosId, equiposId);
//.getListaProyectosPorUsuario(usuariosOID, territoriosId, unidadesNegociosId, equiposId, nombreTienda, nombreProyecto);
    }
    
    
    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAOSecciones getDaoSecciones() {
        return daoSecciones;
    }

    public void setDaoSecciones(DAOSecciones daoSecciones) {
        this.daoSecciones = daoSecciones;
    }

    public DAOClientes getDaoClientes() {
        return daoClientes;
    }

    public void setDaoClientes(DAOClientes daoClientes) {
        this.daoClientes = daoClientes;
    }

    public DAOEncuestasUsuarios getDaoEncuestasUsuarios() {
        return daoEncuestasUsuarios;
    }

    public void setDaoEncuestasUsuarios(DAOEncuestasUsuarios daoEncuestasUsuarios) {
        this.daoEncuestasUsuarios = daoEncuestasUsuarios;
    }
    
    public DAORegionesUsuarios getDaoRegionUsuarios() {
        return daoRegionUsuarios;
    }

    public void setDaoRegionUsuarios(DAORegionesUsuarios daoRegionUsuarios) {
        this.daoRegionUsuarios = daoRegionUsuarios;
    }

    public DAOEquiposRegionUsuarios getDaoEquiposRegionUsuarios() {
        return daoEquiposRegionUsuarios;
    }

    public void setDaoEquiposRegionUsuarios(DAOEquiposRegionUsuarios daoEquiposRegionUsuarios) {
        this.daoEquiposRegionUsuarios = daoEquiposRegionUsuarios;
    }

    public DAOEquiposTerritoriosUsuarios getDaoEquiposTerritoriosUsuarios() {
        return daoEquiposTerritoriosUsuarios;
    }

    public void setDaoEquiposTerritoriosUsuarios(DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios) {
        this.daoEquiposTerritoriosUsuarios = daoEquiposTerritoriosUsuarios;
    }

    public DAOProyectosUsuarios getDaoProyectosUsuarios() {
        return daoProyectosUsuarios;
    }

    public void setDaoProyectosUsuarios(DAOProyectosUsuarios daoProyectosUsuarios) {
        this.daoProyectosUsuarios = daoProyectosUsuarios;
    }
    
    
    
    
}
