/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Respuestas;
import com.crossmark.collector.persistence.daos.DAOPreguntas;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class ServicePreguntasImpl implements ServicePreguntas {
    
    private DAOPreguntas daoPreguntas;
    private DAOPreguntas daoPreguntaAgregada;
    private DAOPreguntas daoListaPreguntas;
    private DAOPreguntas daoOpcionesAgregadas;
    private DAOPreguntas daoEliminaPregunta;

    @Override
    public List<Respuestas> listaTipoRespuestas() {
        
        return daoPreguntas.listaTipoRespuestas();
    }

    /**
     * @return the daoPreguntas
     */
    public DAOPreguntas getDaoPreguntas() {
        return daoPreguntas;
    }

    /**
     * @param daoPreguntas the daoPreguntas to set
     */
    public void setDaoPreguntas(DAOPreguntas daoPreguntas) {
        this.daoPreguntas = daoPreguntas;
    }

	public DAOPreguntas getDaoPreguntaAgregada() {
		return daoPreguntaAgregada;
	}

	public void setDaoPreguntaAgregada(DAOPreguntas daoPreguntaAgregada) {
		this.daoPreguntaAgregada = daoPreguntaAgregada;
	}

	@Override
	public String preguntaAgregada(String preguntaOID, String seccionOID,
			String textoPregunta, String mensaje, int ordenPregunta,
			boolean activa, boolean enabled, boolean visible, String variable,
			int tipoRespuesta, String alerta, int subCategoria,
			boolean bancoPreguntas, int categoria, boolean requerida, int longitudMaxima,
                        int valorMinimo, int valorMaximo, int numeroDecimales,String archivoImagen, String valorAlerta) {
		// TODO Auto-generated method stub
		return daoPreguntaAgregada.preguntaAgregada(preguntaOID, seccionOID, textoPregunta, mensaje, ordenPregunta, activa, enabled, visible, variable, tipoRespuesta, alerta, subCategoria, bancoPreguntas, categoria, requerida, longitudMaxima, valorMinimo, valorMaximo, numeroDecimales,archivoImagen,valorAlerta);
	}

    @Override
    public List<Preguntas> listaPreguntas(String preguntaOID, String seccionOID, boolean banco, int idSubCategoria) {
        return daoListaPreguntas.listaPreguntas(preguntaOID, seccionOID, banco, idSubCategoria);
    }

    /**
     * @return the daoListaPreguntas
     */
    public DAOPreguntas getDaoListaPreguntas() {
        return daoListaPreguntas;
    }

    /**
     * @param daoListaPreguntas the daoListaPreguntas to set
     */
    public void setDaoListaPreguntas(DAOPreguntas daoListaPreguntas) {
        this.daoListaPreguntas = daoListaPreguntas;
    }

    @Override
    public String opcionesAgregadas(String listasOID, String preguntaAgregada,
                    int tipoOrden, boolean seleccionada, boolean opcionOtros,
                    String preguntaDominanteOID, int tipoListasOpciones,
                    String listasFiltroOID, Integer variable, boolean recorridoTotal,
                    boolean controlUsuario, boolean codigoBarra,
                    boolean orientacionVertical, String opcionesPregunta) {
            // TODO Auto-generated method stub
            return daoOpcionesAgregadas.opcionesAgregadas(listasOID, preguntaAgregada, tipoOrden, seleccionada, opcionOtros, preguntaDominanteOID, tipoListasOpciones, listasFiltroOID, variable, recorridoTotal, controlUsuario, codigoBarra, orientacionVertical, opcionesPregunta);
    }
    
    @Override
    public String opcionesAgregadasDel(String listasOID, String preguntasSeccionesOID) {
            // TODO Auto-generated method stub
            return daoOpcionesAgregadas.opcionesAgregadasDel(listasOID, preguntasSeccionesOID);
    }

    public DAOPreguntas getDaoOpcionesAgregadas() {
            return daoOpcionesAgregadas;
    }

    public void setDaoOpcionesAgregadas(DAOPreguntas daoOpcionesAgregadas) {
            this.daoOpcionesAgregadas = daoOpcionesAgregadas;
    }

    /**
     * @return the daoEliminaPregunta
     */
    public DAOPreguntas getDaoEliminaPregunta() {
        return daoEliminaPregunta;
    }

    /**
     * @param daoEliminaPregunta the daoEliminaPregunta to set
     */
    public void setDaoEliminaPregunta(DAOPreguntas daoEliminaPregunta) {
        this.daoEliminaPregunta = daoEliminaPregunta;
    }
    
    /**
     * @param preguntaEliminar  Identificador de la pregunta a eliminar.
     **/
    @Override
    public void eliminaPregunta(String preguntaEliminar) {
        daoEliminaPregunta.eliminaPregunta(preguntaEliminar);
    }
    
}
