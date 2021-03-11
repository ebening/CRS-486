/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Puestos;
import com.crossmark.collector.persistence.daos.DAOPuestos;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public class ServicePuestosImpl implements ServicePuestos{
    private DAOPuestos daoPuestos;

    @Override
    public List<Puestos> getListpuestos(Integer puestosId) {
        return getDaoPuestos().getListLuestos(puestosId);
    }
    
    public Puestos getPuestoById(Integer puestosId){
        return getDaoPuestos().getPuestoById(puestosId);
    }

    public DAOPuestos getDaoPuestos() {
        return daoPuestos;
    }

    public void setDaoPuestos(DAOPuestos daoPuestos) {
        this.daoPuestos = daoPuestos;
    }
    
    
}
