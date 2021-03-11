/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOPromotoresBus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServicePromotoresBusImpl implements ServicePromotoresBus{
    private DAOPromotoresBus daoPromotoresBus;

    @Override
    public List<Map<String, Object>> buscarPromotores(Map<String, Object> inputs) {
         return getDaoPromotoresBus().buscarPromotores(inputs);
    }
    
    @Override
    public HashMap<String, Object> guardarPromotoresTerritorios(String UsuarioOID, int TerritoriosId, int EquiposId) {
        return getDaoPromotoresBus().guardarPromotoresTerritorios(UsuarioOID, TerritoriosId, EquiposId);
    }

    public DAOPromotoresBus getDaoPromotoresBus() {
        return daoPromotoresBus;
    }

    public void setDaoPromotoresBus(DAOPromotoresBus daoPromotoresBus) {
        this.daoPromotoresBus = daoPromotoresBus;
    }
 
}
