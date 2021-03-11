/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.persistence.daos.DAOClientes;
import java.util.List;

/**
 *
 * @author Usss
 */
public class ServiceClientesImpl implements ServiceClientes {
    
    private DAOClientes daoClientes;


    @Override
    public List<Clientes> listaClientes() {
    
    
        return daoClientes.listaClientes();
    
    
    }

    /**
     * @return the daoClientes
     */
    public DAOClientes getDaoClientes() {
        return daoClientes;
    }

    /**
     * @param daoClientes the daoClientes to set
     */
    public void setDaoClientes(DAOClientes daoClientes) {
        this.daoClientes = daoClientes;
    }
    
}
