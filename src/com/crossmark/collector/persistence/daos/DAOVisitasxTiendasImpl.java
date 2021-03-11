/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOVisitasxTiendasImpl implements DAOVisitasxTiendas{

    private DatabaseStoredProc spVisitasxTiendas;
    
    @Override
    public List<Map<String, Object>> getVisitasByTiendaId(int tiendaId, int equiposId, int territoriosId) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", tiendaId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("EquiposId", equiposId);
        List<Map<String, Object>> response = getSpVisitasxTiendas().execSP(inputs);
        return response;
    }

    /**
     * @return the spVisitasxTiendas
     */
    public DatabaseStoredProc getSpVisitasxTiendas() {
        return spVisitasxTiendas;
    }

    /**
     * @param spVisitasxTiendas the spVisitasxTiendas to set
     */
    public void setSpVisitasxTiendas(DatabaseStoredProc spVisitasxTiendas) {
        this.spVisitasxTiendas = spVisitasxTiendas;
    }
    
}
