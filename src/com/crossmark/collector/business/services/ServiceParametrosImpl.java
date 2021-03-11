/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOParametros;
import com.crossmark.collector.business.domain.Parametros;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public class ServiceParametrosImpl implements ServiceParametros, Serializable{
    private DAOParametros daoParametros;
    
    @Override
    public List<Parametros> getParametrosReporting(Integer parametrosId,String nombre){
        return daoParametros.getParametrosByParams(parametrosId, nombre);
    }

    public DAOParametros getDaoParametros() {
        return daoParametros;
    }

    public void setDaoParametros(DAOParametros daoParametros) {
        this.daoParametros = daoParametros;
    }
    
    
}
