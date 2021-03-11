/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.EquiposUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAOEquiposUsuarios extends Serializable {
    
    List<EquiposUsuarios> getListaEquiposUsuarios(String usuariosOID,Integer equiposId,Integer pertenece);
    
    void actualizarEquiposUsuario(Integer equiposId, String usuariosID, int territoriosId);
    
    void eliminarPorUsuario(String usuariosID, int equiposId);
    
    EquiposUsuarios genericObject(Object args);

    List<Equipo> traerListaEquipoUsuario(UsuarioSession o);
}
