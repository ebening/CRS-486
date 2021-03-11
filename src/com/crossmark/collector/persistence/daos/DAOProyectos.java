package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Archivos;
import com.crossmark.collector.business.domain.Encuestas;
import java.util.Date;
import java.util.List;

import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.domain.UnidadesNegocio;

public interface DAOProyectos {
	
	String getProyecto(Integer clienteId,String claveProyecto,String nombreProyecto,
			Date fechaInicio, Date fechaFin);

	List<Proyectos> listaProyectos(Integer proyectoId,String claveProyecto, String descripcionProyecto,
                String nombreProyecto, Integer clienteId,Integer unidadNegocioId,
			Date fechaInicio, Date fechaFin, Date fechaVisible, boolean activo);
        
	List<Proyectos> listaProyectosReportes(Integer proyectoId, String claveProyecto, String descripcionProyecto,
            String nombreProyecto, Integer clienteId, Integer unidadNegocioId,
            Date fechaInicio, Date fechaFin, Date fechaVisible, boolean activo);
	
	Integer guardaProyecto(int proyectoId,String claveProyecto,String nombreProyecto,String descripcionProyecto,Date fechaVisible,Date fechaInicio,Date fechaFin,
			int status,Integer unidadNegocioId,int visitaAutomatica, Integer clienteId, String imagen, Integer diasVigencias);
	
	
	
        Proyectos proyectoEditar(Integer proyectoId);
        
        //List<Encuestas> listaEncuestasProyecto(Integer proyectoId);
        
        List<Encuestas> listaEncuestasProyecto(Integer inidadesNegociosID, Integer proyectoId);
        
        String agregaEncuesta(int proyectoId, int encuestaId);
        
        String eliminaProyecto(Integer proyectoId);
        
        String eliminaEncuestaProyecto(int proyectoId, int encuestaId);
        
        public List<Encuestas> listaEncuestas(Integer idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, Integer statusId, boolean esPlantilla, boolean activa, Date fechaCreacionEncuesta, String observacionEncuesta, Integer proyectoId, Integer unidadesNegocioEncuestaId);
        
        public List<Archivos> getListaArchivos(String archivosOID, Integer proyectosId);
        
        public void eliminaArchivoProyecto(String archivosOID);
        
        String agregaArchivoProyecto(Archivos archivo);
        
        
}
