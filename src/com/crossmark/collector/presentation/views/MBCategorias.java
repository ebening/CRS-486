/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Categorias;
import com.crossmark.collector.business.services.ServiceCategorias;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class MBCategorias implements Serializable {
    
    private ServiceCategorias serviceCategorias;
    
    public List<Categorias> listaCategorias(){
        
        return serviceCategorias.listaCategorias();
        
    }
    
    
    
    
    
    
    

    /**
     * @return the serviceCategorias
     */
    public ServiceCategorias getServiceCategorias() {
        return serviceCategorias;
    }

    /**
     * @param serviceCategorias the serviceCategorias to set
     */
    public void setServiceCategorias(ServiceCategorias serviceCategorias) {
        this.serviceCategorias = serviceCategorias;
    }
    
}
