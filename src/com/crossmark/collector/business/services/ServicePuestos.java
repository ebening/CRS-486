/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Puestos;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface ServicePuestos {
    List<Puestos> getListpuestos(Integer puestosId);
    
    Puestos getPuestoById(Integer id);
}
