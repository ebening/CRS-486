/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.presentation.views.utils.SeccionParametro;

import java.util.List;
import java.util.Map;

/**
 *
 * @author franciscom
 */
public interface DAOParametros extends AbstractDAO<Parametros>{
    
    public List<Parametros> getParametrosByParams(Integer parametrosId,String nombre);

    public List<SeccionParametro> getAllSecciones();

    public void guardar(Map<Integer,String> parametros);
}
