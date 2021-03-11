/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Plantillas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.persistence.daos.DAOClientes;
import com.crossmark.collector.persistence.daos.DAOEncuestasProyecto;
import com.crossmark.collector.persistence.daos.DAOEquipos;
import com.crossmark.collector.persistence.daos.DAOEquiposTerritorios;
import com.crossmark.collector.persistence.daos.DAOParametros;
import com.crossmark.collector.persistence.daos.DAOProyectos;
import com.crossmark.collector.persistence.daos.DAORegiones;
import com.crossmark.collector.persistence.daos.DAOSecciones;
import com.crossmark.collector.persistence.daos.DAOUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EquipoTerritorio;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author franciscom
 */
public class ServiceReportesOperativosImpl implements ServiceReportesOperativos, Serializable{
    
    private DAORegiones daoRegiones;
    private DAOEquipos daoEquipos;
    private DAOEquiposTerritorios daoEquiposTerritorios;
    private DAOUsuarios daoUsuarios;
    private DAOSecciones daoSecciones;
    private DAOClientes daoClientes;
    private DAOEncuestasProyecto daoEncuestasProyecto;
    private DAOProyectos daoProyectos;
    
    @Override
    public List<Region> getRegionesByFind(Integer regionId){
        return daoRegiones.getRegionesByFind(regionId);
    }
    
    
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
    
    @Override
    public List<Equipo> getEquiposByFind(Integer equiposId,Integer regionesId,Integer unidadesNegociosId){
        return getDaoEquipos().getEquiposByFind(equiposId,regionesId,unidadesNegociosId);
    }
    
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
    
    
    @Override
    public List<EquipoTerritorio> getEquiposTerritoriosByFind(Integer equipoId, Integer territorioId){
        return getDaoEquiposTerritorios().getEquiposTerritoriosByFind(equipoId, territorioId);
    }
    
    
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
    public List<EncuestasProyecto> getEncuestasByParams(Integer unidadesNegocioEncuestaId, Integer proyectoId) {
        return daoEncuestasProyecto.getListaEncuestasProyecto(unidadesNegocioEncuestaId, proyectoId);
    }
    
    @Override
    public List<Proyectos> getProyectosByParams(Integer proyectoId,String claveProyecto, String descripcionProyecto,String nombreProyecto,Integer clienteId, Integer unidadNegocioId,Date fechaInicio, Date fechaFin, Date fechaVisible, Boolean activo){
        // TODO Auto-generated method stub
        
        return daoProyectos.listaProyectosReportes(proyectoId, claveProyecto, descripcionProyecto, nombreProyecto,clienteId, unidadNegocioId, 
                fechaInicio, fechaFin, fechaVisible, activo);
    }
    
    
    
    
    
    
    public DAORegiones getDaoRegiones() {
        return daoRegiones;
    }

    public void setDaoRegiones(DAORegiones daoRegiones) {
        this.daoRegiones = daoRegiones;
    }

    public DAOEquipos getDaoEquipos() {
        return daoEquipos;
    }

    public void setDaoEquipos(DAOEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public DAOEquiposTerritorios getDaoEquiposTerritorios() {
        return daoEquiposTerritorios;
    }

    public void setDaoEquiposTerritorios(DAOEquiposTerritorios daoEquiposTerritorios) {
        this.daoEquiposTerritorios = daoEquiposTerritorios;
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

    public DAOEncuestasProyecto getDaoEncuestasProyecto() {
        return daoEncuestasProyecto;
    }

    public void setDaoEncuestasProyecto(DAOEncuestasProyecto daoEncuestasProyecto) {
        this.daoEncuestasProyecto = daoEncuestasProyecto;
    }
    
    
    public DAOProyectos getDaoProyectos() {
        return daoProyectos;
    }

    public void setDaoProyectos(DAOProyectos daoProyectos) {
        this.daoProyectos = daoProyectos;
    }
    
}
