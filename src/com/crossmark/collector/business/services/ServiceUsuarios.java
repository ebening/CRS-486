/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.EquiposUsuarios;
import com.crossmark.collector.business.domain.TerritoriosUsuariosIn;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface ServiceUsuarios {
    
    List<Usuario> getListUsuarios(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo);
    /*
    List<UsuarioTerritorio> getListUsuariosTerritorios(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, String nroEmpleado,  Integer perfilesId, Integer unidadesNegociosId, Integer equiposId);
    */
    //List<TerritoriosUsuarios> getListTerritoriosUsuarios(String usuariosOID, Integer equiposId, Integer territoriosId, byte pertenece);
    
    void actualizarUsuario(Usuario usuario);
    
    String eliminarUsuario(String usuariosOID);
    
    void eliminarEquiposPorUsuario(String usuariosOID, int equiposId);
    
    List<Region> getRegionesByFind(Integer regionId);
    
    void insertarUsuarioTerritorio(String usuariosOID, Integer territoriosId, Integer equiposId);
    
    List<EquiposUsuarios> getEquiposByUserFind(String usuariosOID,Integer equiposId,Integer pertenece);
    
    void actualizarEquiposUsuario(Integer equiposId, String usuariosID, int territoriosId);
    
    List<TerritoriosUsuariosIn> getEquiposTerritoriosInFind(String usuariosOID, String cadenaEquipos, Integer territoriosId, Integer pertenece);
    
    List<TerritoriosUsuarios> getTerritoriosByEquipoId(String usuariosOID, int EquiposId);
}
