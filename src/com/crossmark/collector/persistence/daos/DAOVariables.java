/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;

import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAOVariables extends AbstractDAO<Variable>{
    @Override
    public List<Variable> getAll();
    
    @Override
    public Variable getById(Integer id);
    
    @Override
    public Variable genericObject(Object args);
    
}
