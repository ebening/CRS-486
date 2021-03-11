/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface DAOSecciones {

    //List getSecciones();
    //   List getCiudadById(int ciudadId);
    public List<Seccion> getSeccionesByParams(String seccionesOID, String nombre, Integer encuestasId, Integer orden, Integer activa, Integer enabled, Integer ciclica,
            Date fechaCreacion, Integer visible);

    //List getSeccionesById(String seccionesOID);
    List<Secciones> listaSecciones(String seccionesOID, String nombreSeccion, int idEncuesta, short orden,
            boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion,
            boolean visible);

    String guardaSeccion(String seccionesOID, String nombreSeccion, int idEncuesta, short orden,
            boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion,
            boolean seleccionada, String preguntaDominanteOID, Integer tipoListasOpciones,
            String listasFiltroOID, int variable, boolean controlUsuario, boolean codigoBarras,
            String listasOID, String claveSeccion, String seccionPadreOID);
    
    void eliminaSeccion(String seccionOID);

}
