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
public interface DAOPromotoresBus {
    List<Map<String, Object>> buscarPromotores(Map<String, Object> inputs);
    HashMap<String, Object> guardarPromotoresTerritorios(String UsuariosOID, int TerritoriosId, int EquiposId);
}
