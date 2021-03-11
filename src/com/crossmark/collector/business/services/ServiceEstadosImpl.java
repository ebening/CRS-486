/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOEstados;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class ServiceEstadosImpl implements ServiceEstados{
    private DAOEstados daoEstados;

    @Override
    public List getEstados() {
        return daoEstados.getEstadosList();
    }

    /**
     * @return the daoEstados
     */
    public DAOEstados getDaoEstados() {
        return daoEstados;
    }

    /**
     * @param daoEstados the daoEstados to set
     */
    public void setDaoEstados(DAOEstados daoEstados) {
        this.daoEstados = daoEstados;
    }
    
}
