/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.persistence.daos.DAOCadenas;
import com.crossmark.collector.persistence.daos.DAOClientes;
import com.crossmark.collector.persistence.daos.DAOEquiposRegionUsuarios;
import com.crossmark.collector.persistence.daos.DAOEquiposTerritoriosUsuarios;
import com.crossmark.collector.persistence.daos.DAOProyectosUsuarios;
import com.crossmark.collector.persistence.daos.DAORegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.Cadenas;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author franciscom
 */
public class ServiceRepCliCumplProyectoImpl implements ServiceRepCliCumplProyecto, Serializable{
    
    private DAORegionesUsuarios daoRegionUsuarios;//Regiones
    private DAOEquiposRegionUsuarios daoEquiposRegionUsuarios;//Equipos
    private DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios;//Territorios
    private DAOCadenas daoCadenas;//Cadena
    private DAOClientes daoClientes;//Clientes
    private DAOProyectosUsuarios daoProyectosUsuarios;//Proyecto
    
    @Override
    public List<RegionesUsuarios> getListRegione(String usuariosOID){
        return daoRegionUsuarios.getListaRegionesPorUsuario(usuariosOID);
    }
    
    @Override
    public List<EquiposRegion> getListEquiposPorRegion(String usuariosOID,Integer regionesId){
        return daoEquiposRegionUsuarios.getListaEquiposPorRegionYUsuario(usuariosOID,regionesId);
    }
    
    @Override
    public List<TerritoriosUsuarios> getListaTerritoriosPorUsuarioYEq(String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece){
        return daoEquiposTerritoriosUsuarios.getListaTerritoriosPorUsuario(usuariosOID, equiposId, territoriosId, pertenece);
    }
    
    @Override
    public List<Cadenas> getListCadenas(Integer cadenaId, String nombre){
        
        return daoCadenas.getListaCadenas(cadenaId, nombre);
    }
    
    
    @Override
    public List<Clientes> getListaClientes(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID){
        return daoClientes.listaClientesByParams(clientesId, unidadesNegociosId, equiposId, territoriosId, usuariosOID);
    }
    
    @Override
    public List<ProyectosUsuarios> getListProyectosPorUsuario(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId){
        return daoProyectosUsuarios.getListaProyectosPorUsuario(usuariosOID, clientesId, regionesId, territoriosId, equiposId);
    }
    
    
    public DAOEquiposRegionUsuarios getDaoEquiposRegionUsuarios() {
        return daoEquiposRegionUsuarios;
    }

    public void setDaoEquiposRegionUsuarios(DAOEquiposRegionUsuarios daoEquiposRegionUsuarios) {
        this.daoEquiposRegionUsuarios = daoEquiposRegionUsuarios;
    }
    
    public DAOClientes getDaoClientes() {
        return daoClientes;
    }

    public void setDaoClientes(DAOClientes daoClientes) {
        this.daoClientes = daoClientes;
    }

    public DAORegionesUsuarios getDaoRegionUsuarios() {
        return daoRegionUsuarios;
    }

    public void setDaoRegionUsuarios(DAORegionesUsuarios daoRegionUsuarios) {
        this.daoRegionUsuarios = daoRegionUsuarios;
    }

    public DAOEquiposTerritoriosUsuarios getDaoEquiposTerritoriosUsuarios() {
        return daoEquiposTerritoriosUsuarios;
    }

    public void setDaoEquiposTerritoriosUsuarios(DAOEquiposTerritoriosUsuarios daoEquiposTerritoriosUsuarios) {
        this.daoEquiposTerritoriosUsuarios = daoEquiposTerritoriosUsuarios;
    }

    public DAOCadenas getDaoCadenas() {
        return daoCadenas;
    }

    public void setDaoCadenas(DAOCadenas daoCadenas) {
        this.daoCadenas = daoCadenas;
    }

    public DAOProyectosUsuarios getDaoProyectosUsuarios() {
        return daoProyectosUsuarios;
    }

    public void setDaoProyectosUsuarios(DAOProyectosUsuarios daoProyectosUsuarios) {
        this.daoProyectosUsuarios = daoProyectosUsuarios;
    }
    
    
}
