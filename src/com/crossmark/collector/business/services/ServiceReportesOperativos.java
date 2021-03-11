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
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EquipoTerritorio;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface ServiceReportesOperativos {
    
    public List<Region> getRegionesByFind(Integer regionId);
    
    public Region getRegionById(Integer regionId);
    
    public List<Equipo> getEquiposByFind(Integer equiposId,Integer regionesId,Integer unidadesNegociosId);
    
    public Equipo getEquipoId(Integer equiposId);
    
    public List<EquipoTerritorio> getEquiposTerritoriosByFind(Integer equipoId, Integer territorioId);
    
    public List<Usuario> getUsuariosByParams(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo);
    
    public List<Seccion> getSeccionesByParams(String seccionesOID,String nombre,Integer encuestasId,Integer orden, Integer activa, Integer enabled,Integer ciclica,Date fechaCreacion,Integer visible);
    
    public List<Clientes> listaClientesByParams(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID);
            
    public List<EncuestasProyecto> getEncuestasByParams(Integer unidadesNegocioEncuestaId, Integer proyectoId);
    
    public List<Proyectos> getProyectosByParams(Integer proyectoId,String claveProyecto, String descripcionProyecto,String nombreProyecto, Integer clienteId, Integer unidadNegocioId,Date fechaInicio, Date fechaFin, Date fechaVisible, Boolean activo);
    
}
