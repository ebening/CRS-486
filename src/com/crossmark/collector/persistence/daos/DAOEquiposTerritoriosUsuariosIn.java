/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.TerritoriosUsuariosIn;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAOEquiposTerritoriosUsuariosIn {
    List<TerritoriosUsuariosIn> getEquiposTerritoriosInFind(String usuariosOID, String cadenaEquipos, Integer territoriosId, Integer pertenece);
    
    TerritoriosUsuariosIn genericObject(Object args);
}
