/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOTerritorios;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceTerritoriosImpl implements ServiceTerritorios {
    private DAOTerritorios daoTerritorios;

    @Override
    public List<Map<String, Object>> listaTerritoriosNat(int TerritoriosId, String Nombre, boolean Activo) {
        return getDaoTerritorios().listaTerritoriosNat(TerritoriosId, Nombre, Activo);
    }

    @Override
    public String nombreTerritorio(int TerritorioId) {
        return getDaoTerritorios().nombreTerritorio(TerritorioId);
    }
    
    /**
     * @return the daoTerritorios
     */
    public DAOTerritorios getDaoTerritorios() {
        return daoTerritorios;
    }

    /**
     * @param daoTerritorios the daoTerritorios to set
     */
    public void setDaoTerritorios(DAOTerritorios daoTerritorios) {
        this.daoTerritorios = daoTerritorios;
    }

    
}
