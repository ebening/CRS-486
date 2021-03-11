/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.presentation.views.reportescliente.objects.EncuestasUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface ServiceRepCliResultados {
    
    //public List<Region> getRegionesByFind(Integer regionId);
    public List<RegionesUsuarios> getListRegiones(String usuariosOID);
    
    //public List<Equipo> getEquiposByFind(Integer equiposId,Integer regionesId,Integer unidadesNegociosId);
    public List<EquiposRegion> getListEquiposPorRegion(String usuariosOID,Integer regionesId);
    
    //public List<EquipoTerritorio> getEquiposTerritoriosByFind(Integer equipoId, Integer territorioId);
    public List<TerritoriosUsuarios> getListaTerritoriosPorUsuarioYEq(String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece);
    
    public List<Clientes> listaClientesByParams(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID);
    
    //Pendiente
    public List<Usuario> getUsuariosByParams(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo);
    
    //Ok
    public List<ProyectosUsuarios> getListProyectosPorUsuario(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId);
    
    //Ok
    public List<EncuestasUsuarios> getEncuestasByParams(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId, Integer proyectosId);
    
    //Pendiente
    public List<Seccion> getSeccionesByParams(String seccionesOID,String nombre,Integer encuestasId,Integer orden, Integer activa, Integer enabled,Integer ciclica,Date fechaCreacion,Integer visible);
    
    
}
