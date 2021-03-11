/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.HashMap;

/**
 *
 * @author jdominguez
 */
public interface DAOTiendasTerritorios {
    HashMap<String, Object> insertTiendasByTerritoriosId(int TiendaId, int TerritoriosId, int EquiposId);
    
    HashMap<String, Object> borrarTiendasByTerritoriosId(int TiendaId, int TerritoriosId, int EquiposId);
}
