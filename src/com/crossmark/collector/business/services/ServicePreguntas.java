/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Respuestas;
import java.util.List;

/**
 * 
 * @author RIGG
 */
public interface ServicePreguntas {

	List<Respuestas> listaTipoRespuestas();

	String preguntaAgregada(String preguntaOID, String seccionOID,
			String textoPregunta, String mensaje, int ordenPregunta,
			boolean activa, boolean enabled, boolean visible, String variable,
			int tipoRespuesta, String alerta, int subCategoria,
			boolean bancoPreguntas, int categoria, boolean requerida,int longitudMaxima,
                    int valorMinimo,
                    int valorMaximo,
                    int numeroDecimales,String archivoImagen, String valorAlerta);
        
        List<Preguntas> listaPreguntas(String preguntaOID, String seccionOID, boolean banco, int idSubCategoria);

        
        String opcionesAgregadas(String listasOID, String preguntaAgregada, int tipoOrden, boolean seleccionada,
        		boolean opcionOtros, String preguntaDominanteOID, int tipoListasOpciones, String listasFiltroOID,
        		Integer variable, boolean recorridoTotal, boolean controlUsuario, boolean codigoBarra, 
        		boolean orientacionVertical, String opcionesPregunta);
        
        String opcionesAgregadasDel(String listasOID, String preguntasSeccionesOID);
        
        void eliminaPregunta(String seccionEliminar);
}
