/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.services.ServiceClientes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usss
 */
public class MBClientes {
    
    private ServiceClientes serviceClientes;
    
    private List<Clientes> listaClientes = new ArrayList<Clientes>();

    
    public List<Clientes> obtenerClientes(){
        
        
        this.setListaClientes(serviceClientes.listaClientes());
        
        return listaClientes;
    }
    
    
    
    
    /**
     * @return the serviceClientes
     */
    public ServiceClientes getServiceClientes() {
        return serviceClientes;
    }

    /**
     * @param serviceClientes the serviceClientes to set
     */
    public void setServiceClientes(ServiceClientes serviceClientes) {
        this.serviceClientes = serviceClientes;
    }

    /**
     * @return the listaClientes
     */
    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientes the listaClientes to set
     */
    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
    
    
}
