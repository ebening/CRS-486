/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOVisitasxTiendas;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceVisitasxTiendasImpl implements ServiceVisitasxTiendas, Serializable{
    
    private DAOVisitasxTiendas daoVisitasxTiendas;

    @Override
    public List<Map<String, Object>> getVisitasxTiendas(int tiendaId, int equiposId, int territoriosId) {
        return getDaoVisitasxTiendas().getVisitasByTiendaId(tiendaId, equiposId, territoriosId);
    }

    /**
     * @return the daoVisitasxTiendas
     */
    public DAOVisitasxTiendas getDaoVisitasxTiendas() {
        return daoVisitasxTiendas;
    }

    /**
     * @param daoVisitasxTiendas the daoVisitasxTiendas to set
     */
    public void setDaoVisitasxTiendas(DAOVisitasxTiendas daoVisitasxTiendas) {
        this.daoVisitasxTiendas = daoVisitasxTiendas;
    }
    
}
