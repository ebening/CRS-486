/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface ServiceProyectosTiendas {
    
    List<Map<String, Object>> listaTiendasProyecto(
            int proyectoId, 
            int unidadesNegociosId, 
            int equipoId, 
            int clienteId, 
            String nombre, 
            int cadenasId, 
            int formatoId, 
            int estadosId, 
            int ciudadesId);
    
    HashMap<String, Object> guardaTiendaProyecto(int proyectoId, int tiendaId);
    
    HashMap<String, Object> borraTiendaProyecto(int proyectoId, int tiendaId);
    
    List<Map<String, Object>> listaGruposProyectos(int GruposProyectosId, String nombre, int ClientesId);
    
    HashMap<String, Object> borrarGruposProyectos(int GruposProyectosId);
    
    HashMap<String, Object> updateGruposProyectos(int GruposProyectosId, String nombre, int ClientesId);
    
    List<Map<String, Object>> listaGruposProyectosTiendas(int GruposProyectosId);
    
    HashMap<String, Object> borrarGruposProyectosTiendas(int GruposProyectosId, int TiendasId);
    
    HashMap<String, Object> updateGruposProyectosTiendas(int GruposProyectosId, int TiendasId);
    
    List<Map<String, Object>> getTiendasList(HashMap<String, Object> filtros);
 
}
