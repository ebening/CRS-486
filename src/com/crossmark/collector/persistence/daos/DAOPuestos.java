/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Puestos;

import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAOPuestos {
    
    public Puestos getPuestoById(Integer id);
    
    Puestos genericObject(Object args);
    
    List<Puestos> getListLuestos(Integer puestosId);
}
