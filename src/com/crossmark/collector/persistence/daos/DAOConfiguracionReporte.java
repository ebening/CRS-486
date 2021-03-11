package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Agrupador;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestaCaptura;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Pregunta;
import java.util.List;

/**
 * Acceso a acciones de datos sobre configuraciones de reporte de solución de
 * incidencias.
 */
public interface DAOConfiguracionReporte {

    /**
     * Regresa la lista de agrupadores existentes.
     *
     * @param equiposId el id del equipo (cero para todos)
     * @param agrupadoresId el id del agrupador (cero para todos)
     * @return lista de agrupadores
     */
    public List<Agrupador> listaAgrupadores(int equiposId, int agrupadoresId);

    /**
     * Crea o actualiza agrupadores.
     *
     * @param agrupadorId id del agrupador (cero para nuevo agrupador)
     * @param equipoId id del equipo (requerido)
     * @param nombre nombre del agrupador (requerido)
     * @return {@code true} si no hubo errores
     */
    public int actualizaAgrupador(int agrupadorId, int equipoId, String nombre);

    /**
     * Elimina un agrupador.
     *
     * @param agrupadorId id del agrupador a eliminar (requerido)
     * @return {@code true} si no hubo errores
     */
    public boolean eliminaAgrupador(int agrupadorId);

    /**
     * Regresa lista de encuestas disponibles para configurar el reporte.
     *
     * @param equiposId
     * @return
     */
    public List<EncuestaCaptura> listaEncuestasCaptura(int equiposId);

    /**
     * Regresa lista de opciones disponibles para configurar el reporte.
     *
     * @param equiposId
     * @return
     */
    public List<Pregunta> listaOpcionesCaptura(int equiposId);

    /**
     * Regresa lista de encuestas que pueden participar en la cuenta de no
     * solucionados.
     *
     * @param equiposId
     * @return
     */
    public List<EncuestaCaptura> listaEncuestasNoSolucion(int equiposId);

    /**
     * Regresa lista de opciones disponibles para contar las no solucionadas.
     *
     * @param equiposId
     * @return
     */
    public List<Pregunta> listaOpcionesNoSolucion(int equiposId);

    /**
     * Crea o actualiza una relacion entre opcion y cuenta de no solucion.
     *
     * @param opcion
     * @return
     */
    public int actualizaOpcionesNoSolucion(Pregunta opcion);

    /**
     * Elimina una relacion entre opcion y cuenta de no solucion.
     *
     * @param opcion
     * @return
     */
    public boolean eliminaOpcionesNoSolucion(Pregunta opcion);

    /**
     * Relaciona una opcion de pregunta con una categoria de incidencia.
     *
     * @param opcion
     * @return
     */
    public int actualizaOpcionesCaptura(Pregunta opcion);

    /**
     * Elimina una relacion entre opcion de pregunta y categoria de incidencia.
     *
     * @param opcion
     * @return
     */
    public boolean eliminaOpcionesCaptura(Pregunta opcion);

}
