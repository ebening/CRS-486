package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Agrupador;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestaCaptura;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Pregunta;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 * Implementación de acceso a datos para configuración de reporte de incidencias
 * no solucionadas.
 *
 */
public class DAOConfiguracionReporteImpl implements DAOConfiguracionReporte {

    private static final Logger LOGGER = Utileria.logger(DAOConfiguracionReporteImpl.class);

    private DatabaseStoredProc spAgrupacionAltaCambio;
    private DatabaseStoredProc spAgrupacionBaja;
    private DatabaseStoredProc spAgrupacionConsulta;

    private DatabaseStoredProc spCapturaConsultaEncuestas;
    private DatabaseStoredProc spCapturaConsultaPreguntas;
    private DatabaseStoredProc spCapturaAltaCambio;
    private DatabaseStoredProc spCapturaBaja;

    private DatabaseStoredProc spNoSolucionConsultaEncuestas;
    private DatabaseStoredProc spNoSolucionConsultaPreguntas;
    private DatabaseStoredProc spNoSolucionAltaCambio;
    private DatabaseStoredProc spNoSolucionBaja;

    public DAOConfiguracionReporteImpl() {
    }

    @Override
    public List<Agrupador> listaAgrupadores(int equiposId, int agrupadoresId) {
        List<Agrupador> agrupadores = new ArrayList<>();

        Map<String, Object> inputs = new HashMap<>();

        inputs.put("EquiposId", equiposId == 0 ? null : equiposId);
        inputs.put("AgrupadoresId", agrupadoresId == 0 ? null : agrupadoresId);

        Map<String, Object> out = spAgrupacionConsulta.execute(inputs);

        Object resultset = null;

        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }
        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    Agrupador agrupador = new Agrupador();

                    agrupador.setAgrupadoresId(MapUtils.getIntValue(registro, "AgrupadoresId", 0));
                    agrupador.setEquiposId(MapUtils.getIntValue(registro, "EquiposId", 0));
                    agrupador.setNombre(MapUtils.getString(registro, "nombre", ""));

                    agrupadores.add(agrupador);
                }
            }
        }

        return agrupadores;
    }

    @Override
    public int actualizaAgrupador(int agrupadorId, int equipoId, String nombre) {
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("AgrupadoresId", agrupadorId == 0 ? null : agrupadorId);
        inputs.put("EquiposId", equipoId);
        inputs.put("nombre", nombre);

        int idNuevo = 0;

        try {
            Map<String, Object> out = spAgrupacionAltaCambio.execute(inputs);
            idNuevo = MapUtils.getIntValue(out, "AgrupadoresId", 0);
        } catch (DataAccessException ex) {
            LOGGER.info(ex);
        }

        return idNuevo;
    }

    @Override
    public boolean eliminaAgrupador(int agrupadorId) {
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("AgrupadoresId", agrupadorId);

        try {
            Map<String, Object> out = spAgrupacionBaja.execute(inputs);
        } catch (DataAccessException dataAccessException) {
            return false;
        }

        return true;
    }

    @Override
    public List<EncuestaCaptura> listaEncuestasCaptura(int equiposId) {
        List<EncuestaCaptura> resultado = new ArrayList<>();

        Map<String, Object> inputs = ImmutableMap.<String, Object>builder()
                .put("EquiposId", equiposId)
                .build();
        Map<String, Object> out = spCapturaConsultaEncuestas.execute(inputs);

        Object resultset = null;

        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }
        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    EncuestaCaptura encuesta = encuestaDeMapa(registro);
                    resultado.add(encuesta);
                }
            }
        }

        return resultado;
    }

    private EncuestaCaptura encuestaDeMapa(Map registro) {
        EncuestaCaptura encuesta = new EncuestaCaptura();
        encuesta.setEncuestaId(MapUtils.getIntValue(registro, "EncuestasId"));
        encuesta.setNombre(MapUtils.getString(registro, "Nombre"));
        encuesta.setActivo(MapUtils.getIntValue(registro, "Activo", 0) == 1);
        return encuesta;
    }

    @Override
    public List<Pregunta> listaOpcionesCaptura(int equiposId) {
        List<Pregunta> resultado = new ArrayList<>();

        Map<String, Object> inputs = ImmutableMap.<String, Object>builder()
                .put("EquiposId", equiposId)
                .build();
        Map<String, Object> out = spCapturaConsultaPreguntas.execute(inputs);

        Object resultset = null;
        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }

        Map<String, Pregunta> preguntasPorId = new HashMap<>();

        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    Pregunta pregunta = preguntaDeMapa(registro);
                    pregunta.setEquiposId(equiposId);
                    String preguntasOID = pregunta.getPreguntasOID();

                    if (preguntasPorId.containsKey(preguntasOID) == false) {
                        preguntasPorId.put(preguntasOID, pregunta);
                        resultado.add(pregunta);
                    }
                    preguntasPorId.get(preguntasOID).getOpciones().add(pregunta);
                }
            }
        }

        return resultado;
    }

    private Pregunta preguntaDeMapa(Map registro) {
        Pregunta pregunta = new Pregunta();
        pregunta.setEncuestasId(MapUtils.getIntValue(registro, "EncuestasId"));
        pregunta.setEncuesta(MapUtils.getString(registro, "Encuesta"));
        pregunta.setSeccionesOID(MapUtils.getString(registro, "SeccionesOID"));
        pregunta.setSeccion(MapUtils.getString(registro, "Seccion"));
        pregunta.setPreguntasOID(MapUtils.getString(registro, "PreguntasOID"));
        pregunta.setPregunta(MapUtils.getString(registro, "Pregunta"));
        pregunta.setListasOID(MapUtils.getString(registro, "ListasOID"));
        pregunta.setOpcionesOID(MapUtils.getString(registro, "OpcionesOID"));
        pregunta.setOpcion(MapUtils.getString(registro, "Opcion"));
        pregunta.setAgrupadoresID(MapUtils.getIntValue(registro, "AgrupadoresID"));
        pregunta.setAgrupador(MapUtils.getString(registro, "Agrupador"));
        pregunta.setActivo(MapUtils.getIntValue(registro, "Activo") == 1);

        int id1 = MapUtils.getIntValue(registro, "AgrupadoresOpcionesId");
        int id2 = MapUtils.getIntValue(registro, "OpcionesSumatoriaId");
        pregunta.setAgrupadoresOpcionesId(Math.max(id1, id2));

        return pregunta;
    }

    @Override
    public List<EncuestaCaptura> listaEncuestasNoSolucion(int equiposId) {
        List<EncuestaCaptura> resultado = new ArrayList<>();

        Map<String, Object> inputs = ImmutableMap.<String, Object>builder()
                .put("EquiposId", equiposId)
                .build();
        Map<String, Object> out = spNoSolucionConsultaEncuestas.execute(inputs);

        Object resultset = null;

        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }
        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    EncuestaCaptura encuesta = encuestaDeMapa(registro);
                    resultado.add(encuesta);
                }
            }
        }

        return resultado;
    }

    @Override
    public List<Pregunta> listaOpcionesNoSolucion(int equiposId) {
        List<Pregunta> resultado = new ArrayList<>();

        Map<String, Object> inputs = ImmutableMap.<String, Object>builder()
                .put("EquiposId", equiposId)
                .build();
        Map<String, Object> out = spNoSolucionConsultaPreguntas.execute(inputs);

        Object resultset = null;
        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }

        Map<String, Pregunta> preguntasPorId = new HashMap<>();

        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    Pregunta pregunta = preguntaDeMapa(registro);
                    pregunta.setEquiposId(equiposId);
                    String preguntasOID = pregunta.getPreguntasOID();

                    if (preguntasPorId.containsKey(preguntasOID) == false) {
                        preguntasPorId.put(preguntasOID, pregunta);
                        resultado.add(pregunta);
                    }
                    preguntasPorId.get(preguntasOID).getOpciones().add(pregunta);
                }
            }
        }

        return resultado;
    }

    @Override
    public int actualizaOpcionesNoSolucion(Pregunta opcion) {
        Map<String, Object> inputs = new HashMap<>();

        int agrupadoresOpcionesId = opcion.getAgrupadoresOpcionesId();
        inputs.put("OpcionesSumatoriaId", agrupadoresOpcionesId == 0 ? null : agrupadoresOpcionesId);
        inputs.put("EquiposId", opcion.getEquiposId());
        inputs.put("PreguntasOID", opcion.getPreguntasOID());
        inputs.put("ListasOID", opcion.getListasOID());
        inputs.put("OpcionesOID", opcion.getOpcionesOID());

        int idNuevo = 0;

        try {
            Map<String, Object> out = spNoSolucionAltaCambio.execute(inputs);
            idNuevo = MapUtils.getIntValue(out, "OpcionesSumatoriaId", 0);
        } catch (DataAccessException ex) {
            LOGGER.info(ex);
        }

        return idNuevo;
    }

    @Override
    public boolean eliminaOpcionesNoSolucion(Pregunta opcion) {
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("OpcionesSumatoriaId", opcion.getAgrupadoresOpcionesId());

        try {
            Map<String, Object> out = spNoSolucionBaja.execute(inputs);
        } catch (DataAccessException dataAccessException) {
            return false;
        }

        return true;
    }

    @Override
    public int actualizaOpcionesCaptura(Pregunta opcion) {
        Map<String, Object> inputs = new HashMap<>();

        int agrupadoresOpcionesId = opcion.getAgrupadoresOpcionesId();
        inputs.put("AgrupadoresOpcionesId", agrupadoresOpcionesId == 0 ? null : agrupadoresOpcionesId);
        inputs.put("AgrupadoresId", opcion.getAgrupadoresID());
        inputs.put("PreguntasOID", opcion.getPreguntasOID());
        inputs.put("ListasOID", opcion.getListasOID());
        inputs.put("OpcionesOID", opcion.getOpcionesOID());

        int idNuevo = 0;

        try {
            Map<String, Object> out = spCapturaAltaCambio.execute(inputs);
            idNuevo = MapUtils.getIntValue(out, "AgrupadoresOpcionesId", 0);
        } catch (DataAccessException ex) {
            LOGGER.info(ex);
        }

        return idNuevo;
    }

    @Override
    public boolean eliminaOpcionesCaptura(Pregunta opcion) {
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("AgrupadoresOpcionesId", opcion.getAgrupadoresOpcionesId());

        try {
            Map<String, Object> out = spCapturaBaja.execute(inputs);
        } catch (DataAccessException dataAccessException) {
            return false;
        }

        return true;
    }

    public DatabaseStoredProc getSpAgrupacionAltaCambio() {
        return spAgrupacionAltaCambio;
    }

    public void setSpAgrupacionAltaCambio(DatabaseStoredProc spAgrupacionAltaCambio) {
        this.spAgrupacionAltaCambio = spAgrupacionAltaCambio;
    }

    public DatabaseStoredProc getSpAgrupacionBaja() {
        return spAgrupacionBaja;
    }

    public void setSpAgrupacionBaja(DatabaseStoredProc spAgrupacionBaja) {
        this.spAgrupacionBaja = spAgrupacionBaja;
    }

    public DatabaseStoredProc getSpAgrupacionConsulta() {
        return spAgrupacionConsulta;
    }

    public void setSpAgrupacionConsulta(DatabaseStoredProc spAgrupacionConsulta) {
        this.spAgrupacionConsulta = spAgrupacionConsulta;
    }

    public DatabaseStoredProc getSpCapturaConsultaEncuestas() {
        return spCapturaConsultaEncuestas;
    }

    public void setSpCapturaConsultaEncuestas(DatabaseStoredProc spCapturaConsultaEncuestas) {
        this.spCapturaConsultaEncuestas = spCapturaConsultaEncuestas;
    }

    public DatabaseStoredProc getSpCapturaConsultaPreguntas() {
        return spCapturaConsultaPreguntas;
    }

    public void setSpCapturaConsultaPreguntas(DatabaseStoredProc spCapturaConsultaPreguntas) {
        this.spCapturaConsultaPreguntas = spCapturaConsultaPreguntas;
    }

    public DatabaseStoredProc getSpCapturaAltaCambio() {
        return spCapturaAltaCambio;
    }

    public void setSpCapturaAltaCambio(DatabaseStoredProc spCapturaAltaCambio) {
        this.spCapturaAltaCambio = spCapturaAltaCambio;
    }

    public DatabaseStoredProc getSpCapturaBaja() {
        return spCapturaBaja;
    }

    public void setSpCapturaBaja(DatabaseStoredProc spCapturaBaja) {
        this.spCapturaBaja = spCapturaBaja;
    }

    public DatabaseStoredProc getSpNoSolucionConsultaEncuestas() {
        return spNoSolucionConsultaEncuestas;
    }

    public void setSpNoSolucionConsultaEncuestas(DatabaseStoredProc spNoSolucionConsultaEncuestas) {
        this.spNoSolucionConsultaEncuestas = spNoSolucionConsultaEncuestas;
    }

    public DatabaseStoredProc getSpNoSolucionConsultaPreguntas() {
        return spNoSolucionConsultaPreguntas;
    }

    public void setSpNoSolucionConsultaPreguntas(DatabaseStoredProc spNoSolucionConsultaPreguntas) {
        this.spNoSolucionConsultaPreguntas = spNoSolucionConsultaPreguntas;
    }

    public DatabaseStoredProc getSpNoSolucionAltaCambio() {
        return spNoSolucionAltaCambio;
    }

    public void setSpNoSolucionAltaCambio(DatabaseStoredProc spNoSolucionAltaCambio) {
        this.spNoSolucionAltaCambio = spNoSolucionAltaCambio;
    }

    public DatabaseStoredProc getSpNoSolucionBaja() {
        return spNoSolucionBaja;
    }

    public void setSpNoSolucionBaja(DatabaseStoredProc spNoSolucionBaja) {
        this.spNoSolucionBaja = spNoSolucionBaja;
    }

}
