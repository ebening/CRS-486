/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Secciones;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RIGG
 */
public interface ServiceSecciones {
    
    List<Secciones> listaSecciones(String seccionesOID,String nombreSeccion, int idEncuesta, short orden,
                                    boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion,
                                    boolean visible);
    
    String guardaSeccion(String seccionesOID,String nombreSeccion, int idEncuesta, short orden,
                                    boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion,
                                    boolean seleccionada, String preguntaDominanteOID, Integer tipoListasOpciones,
                                    String listasFiltroOID, int variable, boolean controlUsuario,boolean codigoBarras,
                                    String listasOID,String claveSeccion, String seccionPadreOID);
    
    void eliminaSeccion(String seccionOID);
    
}
