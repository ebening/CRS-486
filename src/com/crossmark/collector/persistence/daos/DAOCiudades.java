/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;

import java.util.List;

/**
 *
 * @author jdominguez
 */
public interface DAOCiudades extends AbstractDAO<Ciudad>{
    List getCiudades();
 //   List getCiudadById(int ciudadId);
    List getCiudadByEstado(int ciudadesId, int estadoId, String nombre, boolean activo);

    List<Ciudad> getCiudadesByEstado(Integer idEstado);
}
