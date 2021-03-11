/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Catalogos;
import com.crossmark.collector.business.domain.Condiciones;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Plantillas;
import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Secciones;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usss
 */
public interface ServiceEncuestas {
    
    public List<Encuestas> listaEncuestas(int idEncuesta,
                String nombreEncuesta,
                String claveEncuesta,
                String gpsEncuesta,
                int statusId,
                boolean esPlantilla,
                boolean activa,
                Date fechaCreacionEncuesta,
                String observacionEncuesta,
                int proyectoId,
                int unidadesNegocioEncuestaId);
    
    public Integer creaEncuesta(int idTipoEncuesta, int idPlantilla);
    
    public Integer guardaEncuesta(Integer encuestasId, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId,
                    boolean plantilla, boolean activa, Date fechaCreacion, String observacion, int unidadNeogico, boolean operacion);
    
    public Integer guardaComoPlantilla(Integer idPlantilla, boolean esPlantilla, String nombre);
    
    public List<Plantillas> traePlantilla(Integer idEncuestaPlantilla);
    
    public List<Catalogos> listaCatalogos(String listasOID, boolean catalogo,Integer idTipoLista);
    
    public List<Preguntas> listaPreguntaOrigen(String seccionPadreOID, Integer encuestaId, int ordenSeccion, int ordenPregunta);
    
    public List<Secciones> listaSecciones(Integer idEncuestaPlantilla);
    
    public Encuestas traerEncuesta(Integer idEncuestaPlantilla);
    
    public Encuestas traerEncuestaSeccionesPregunta(Integer encuestasId, String preguntasOID);
    
    public void guardaEncuestaComoPlantilla(Integer idEncuesta,String nombrePlantilla,boolean esPlantilla);
    
    public String eliminaEncuesta(Integer idEncuesta);
    
    public List<Condiciones> getListCondiciones(String seccionesOID, String preguntasOID, String opcionesOID, Boolean seleccionadas);
    
    public void crearCondicion(Condiciones o);
    
    public String eliminarCondicion(Condiciones o);
    
    public List<Preguntas> getListasPreguntasControl(Integer encuestasId, Integer ordenSeccion);
    
}
