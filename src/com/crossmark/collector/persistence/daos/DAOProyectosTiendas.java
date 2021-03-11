/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface DAOProyectosTiendas {
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
}
