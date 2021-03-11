/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import java.util.List;

/**
 *
 * @author jdominguez
 */
public interface ServiceCiudades {
    List getCiudades();
 //   List getCiudadById(int ciudadId);
    List getCiudadesByEstado(int ciudadesId, int estadoId, String nombre, boolean activo);
}
