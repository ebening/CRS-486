/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;

import java.util.List;

/**
 *
 * @author franciscom
 */
public interface DAOUsuarios {
    
    List getUsuariosByParams(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo);
    
    Usuario getUsuarioByOID(String usuariosOID);
    
    List<Usuario> getAllUsuariosCoordinador();
    
    void actualizarUsuario(Usuario usuario);
    
    String eliminarUsuario(String usuariosOID);
    
    //Meotod para usuarios territorios
    /*
    public List<UsuarioTerritorio> getListUsuariosTerritorios(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, String nroEmpleado,  Integer perfilesId, Integer unidadesNegociosId, Integer equiposId);
    */
    void insertarUsuarioTerritorio(String usuariosOID, Integer territoriosId, Integer equiposId);
    
}
