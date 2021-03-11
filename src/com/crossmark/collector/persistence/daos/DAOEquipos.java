/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author franciscom
 */
public interface DAOEquipos extends AbstractDAO<Equipo>{
    //List getEquipos();
 //   List getCiudadById(int ciudadId);
    List<Equipo> getEquiposByFind(Integer equiposId,Integer regionesId,Integer unidadesNegociosId);
    //List getEquiposById(int equiposId);
}
