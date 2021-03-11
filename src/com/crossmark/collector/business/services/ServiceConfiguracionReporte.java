package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Agrupador;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestaCaptura;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Pregunta;
import java.util.List;

/**
 *
 */
public interface ServiceConfiguracionReporte {

    // --- Agrupadores
    /**
     * Regresa la lista de agrupadores del equipo.
     *
     * @param equiposId (opcional) cero para todos
     * @param agrupadoresId (opcional) cero para todos
     * @return
     */
    List<Agrupador> listaAgrupador(int equiposId, int agrupadoresId);

    /**
     * Elimina el agrupador de la base de datos.
     *
     * @param agrupador
     */
    void borrarAgrupador(Agrupador agrupador);

    /**
     * Guarda el agrupador, creando o actualizando de acuerdo a como
     * corresponda. Si es un agrupador nuevo tendrá el ID asignado después de
     * ejecutar la inserción.
     *
     * @param agrupador
     */
    void guardaAgrupador(Agrupador agrupador);

    // --- Encuestas de captura
    /**
     * Regresa la lista de encuestas que tienen opciones candidato a ser
     * incidencias.
     *
     * @param equiposId
     * @return lista de encuestas
     */
    List<EncuestaCaptura> listaEncuestasCaptura(int equiposId);

    /**
     * Regresa las opciones/preguntas de una encuesta que se pueden asociar a un
     * agrupador.
     *
     * @param equiposId
     * @return lista de opciones/preguntas
     */
    List<Pregunta> listaOpcionesCaptura(int equiposId);

    /**
     * Guarda la relación de agrupador con opción.
     *
     * @param pregunta
     */
    void actualizaOpcionesCaptura(Pregunta pregunta);

    /**
     * Elimina la relación de agrupador con opción.
     *
     * @param pregunta
     */
    void eliminaOpcionesCaptura(Pregunta pregunta);

    // --- Encuestas de no solución
    /**
     * Regresa la lista de encuestas que se pueden asociar a la cuenta de
     * incidencias no solucionadas.
     *
     * @param equiposId
     * @return
     */
    List<EncuestaCaptura> listaEncuestasNoSolucion(int equiposId);

    /**
     * Regresa las opciones que se pueden asociar a la cuenta de no
     * solucionadas.
     *
     * @param equiposId
     * @return
     */
    List<Pregunta> listaOpcionesNoSolucion(int equiposId);

    /**
     * Guarda la relación de opción con el reporte.
     *
     * @param opcion
     */
    void actualizaOpcionesNoSolucion(Pregunta opcion);

    /**
     * Elimina la relación de opción con el reporte.
     *
     * @param opcion
     */
    void eliminaOpcionesNoSolucion(Pregunta opcion);

}
