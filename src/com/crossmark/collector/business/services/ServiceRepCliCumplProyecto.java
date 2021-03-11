/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.presentation.views.reportescliente.objects.Cadenas;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface ServiceRepCliCumplProyecto {
    
    public List<RegionesUsuarios> getListRegione(String usuariosOID);
    
    public List<TerritoriosUsuarios> getListaTerritoriosPorUsuarioYEq(String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece);
    
    public List<EquiposRegion> getListEquiposPorRegion(String usuariosOID,Integer regionesId);
    
    public List<Cadenas> getListCadenas(Integer cadenaId, String nombre);
    
    public List<Clientes> getListaClientes(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID);
    
    public List<ProyectosUsuarios> getListProyectosPorUsuario(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId);
    
}
