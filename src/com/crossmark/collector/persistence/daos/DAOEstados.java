/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Estado;

import java.util.List;

/**
 *
 * @author jdominguez
 */
public interface DAOEstados extends AbstractDAO<Estado>{
    List getEstadosList();
}
