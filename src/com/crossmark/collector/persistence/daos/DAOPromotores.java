/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface DAOPromotores {
    List<Map<String, Object>> getPromotorByTerritorioId(Map<String, Object> inputs);
    List<Map<String, Object>> listaProyectosByPromotor(String UsuariosOID, int TerritoriosId,
            int UnidadesNegociosId, int EquiposId, String nombreTienda, String nombreProyecto);
}
