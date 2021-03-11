package com.crossmark.collector.presentation.views.reportesoperativos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Agrupador;
import com.crossmark.collector.business.services.ServiceConfiguracionReporte;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestaCaptura;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Pregunta;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.AgrupadorConverter;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Bean para administrar proceso de configuración de reporte de incidencias.
 */
public class MBConfiguracionReporte {

    private ServiceConfiguracionReporte serviceConfiguracionReporte;

    private List<Agrupador> agrupadoresOriginales;
    private List<Agrupador> agrupadores;
    private List<EncuestaCaptura> encuestasIncidencias;
    private List<EncuestaCaptura> encuestasNoSolucion;
    private List<Pregunta> preguntasIncidencias;
    private List<Pregunta> preguntasNoSolucion;

    private String nombreAgrupadorCaptura;
    private String nombreAgrupadorEdicion;
    private int equipoId;

    // Agrupador mostrado en el dialogo siendo editado
    private Agrupador agrupadorEditado;
    // Referencia a la encuesta de link que abre el dialogo
    private EncuestaCaptura encuestaEditada;
    // En el dialogo, la pregunta que se esta configurando
    private Pregunta preguntaEditada;

    private AgrupadorConverter agrupadorConverter;

    /**
     * Creates a new instance of MBConfiguracionReporte
     */
    public MBConfiguracionReporte() {
        agrupadores = new ArrayList<>();
        agrupadorConverter = new AgrupadorConverter();
    }

    public void init() {
        UsuarioSession usuario = Utileria.getSessionAttribute("userLoged");
        if (usuario.getEquipoId() != null) {
            equipoId = usuario.getEquipoId();
        }

        agrupadores = getServiceConfiguracionReporte().listaAgrupador(0, 0);
        agrupadoresOriginales = new ArrayList<>(agrupadores);
        agrupadorConverter.setAgrupadores(agrupadores);

        Map<Integer, Agrupador> agrupadorPorId = new HashMap<>();
        for (Agrupador agrupador : agrupadores) {
            agrupadorPorId.put(agrupador.getAgrupadoresId(), agrupador);
        }

        encuestasIncidencias = serviceConfiguracionReporte.listaEncuestasCaptura(equipoId);
        encuestasNoSolucion = serviceConfiguracionReporte.listaEncuestasNoSolucion(equipoId);

        preguntasIncidencias = serviceConfiguracionReporte.listaOpcionesCaptura(equipoId);
        preguntasNoSolucion = serviceConfiguracionReporte.listaOpcionesNoSolucion(equipoId);

        for (EncuestaCaptura encuesta : encuestasIncidencias) {
            List<Pregunta> preguntas = new ArrayList<>();
            for (Pregunta pregunta : preguntasIncidencias) {
                for (Pregunta p : pregunta.getOpciones()) {
                    p.setAgrupadorSeleccionado(agrupadorPorId.get(p.getAgrupadoresID()));
                }
                if (pregunta.getEncuestasId() == encuesta.getEncuestaId()) {
                    preguntas.add(pregunta);
                }
            }
            encuesta.setPreguntas(preguntas);
        }

        for (EncuestaCaptura encuesta : encuestasNoSolucion) {
            List<Pregunta> preguntas = new ArrayList<>();
            for (Pregunta pregunta : preguntasNoSolucion) {
                for (Pregunta p : pregunta.getOpciones()) {
                    p.setAgrupadorSeleccionado(agrupadorPorId.get(p.getAgrupadoresID()));
                }
                if (pregunta.getEncuestasId() == encuesta.getEncuestaId()) {
                    preguntas.add(pregunta);
                }
            }
            encuesta.setPreguntas(preguntas);
        }
    }

    public void guardar() {
        // Agrupaciones
        List<Agrupador> paraEliminar = new ArrayList<>(agrupadoresOriginales);
        for (Agrupador agrupador : agrupadores) {
            serviceConfiguracionReporte.guardaAgrupador(agrupador);

            // Dejar solo los agrupadores a eliminar en la lista de originales
            for (Iterator<Agrupador> iterator = paraEliminar.iterator(); iterator.hasNext();) {
                Agrupador original = iterator.next();
                if (original.getAgrupadoresId() > 0 && original.getAgrupadoresId() == agrupador.getAgrupadoresId()) {
                    iterator.remove();
                }
            }
        }

        // Eliminar los que ya no están
        for (Agrupador eliminar : paraEliminar) {
            if (eliminar.getAgrupadoresId() > 0) {
                serviceConfiguracionReporte.borrarAgrupador(eliminar);
            }
        }

        // Captura
        for (EncuestaCaptura encuesta : encuestasIncidencias) {
            for (Pregunta pregunta : encuesta.getPreguntas()) {
                for (Pregunta opcion : pregunta.getOpciones()) {
                    if (opcion.getAgrupadorSeleccionado() != null) {
                        opcion.setAgrupadoresID(opcion.getAgrupadorSeleccionado().getAgrupadoresId());
                        if (opcion.getAgrupadoresID() > 0) {
                            serviceConfiguracionReporte.actualizaOpcionesCaptura(opcion);
                        }
                    }
                    else
                    {
                        serviceConfiguracionReporte.eliminaOpcionesCaptura(opcion);
                    }
                }
            }
        }

        // No Solucion
        for (EncuestaCaptura encuesta : encuestasNoSolucion) {
            for (Pregunta pregunta : encuesta.getPreguntas()) {
                for (Pregunta opcion : pregunta.getOpciones()) {
                    if (opcion.isActivo()) {
                        serviceConfiguracionReporte.actualizaOpcionesNoSolucion(opcion);
                    }
                    else
                    {
                        serviceConfiguracionReporte.eliminaOpcionesNoSolucion(opcion);
                    }
                }
            }
        }

        // Recargar los datos
        init();
        Utileria.mensajeSatisfactorio("Configuración guardada.");
    }

    public String regresar() {
        boolean saleSinGuardar = true;

        if (saleSinGuardar) {
            return "/pages/reportesoperativos/ReporteSolucionIncidencias?faces-redirect=true";
        } else {
            return "";
        }
    }

    public void agregarAgrupacion() {
        Agrupador nuevo = new Agrupador();
        nuevo.setNombre(nombreAgrupadorCaptura);
        nuevo.setAgrupadoresId(0);
        nuevo.setEquiposId(equipoId);

        agrupadores.add(nuevo);
        nombreAgrupadorCaptura = "";
    }

    public void editarAgrupacion(Agrupador agrupador) {
        agrupadorEditado = agrupador;
        nombreAgrupadorEdicion = agrupador.getNombre();
    }

    public void guardarCambioAgrupacion() {
        agrupadorEditado.setNombre(nombreAgrupadorEdicion);
    }

    public void eliminarAgrupacion(Agrupador agrupador) {
        agrupadores.remove(agrupador);
    }

    public void abrirEncuestaCaptura(EncuestaCaptura encuesta) {
        encuestaEditada = encuesta;
        preguntaEditada = null;
    }

    public void abrirEncuestaNoSolucion(EncuestaCaptura encuesta) {
        encuestaEditada = encuesta;
        preguntaEditada = null;
    }

    public void editarPregunta(Pregunta pregunta) {
        preguntaEditada = pregunta;
    }

    public ServiceConfiguracionReporte getServiceConfiguracionReporte() {
        return serviceConfiguracionReporte;
    }

    public void setServiceConfiguracionReporte(ServiceConfiguracionReporte serviceConfiguracionReporte) {
        this.serviceConfiguracionReporte = serviceConfiguracionReporte;
    }

    public List<Agrupador> getAgrupadores() {
        return agrupadores;
    }

    public void setAgrupadores(List<Agrupador> agrupadores) {
        this.agrupadores = agrupadores;
    }

    public String getNombreAgrupadorCaptura() {
        return nombreAgrupadorCaptura;
    }

    public void setNombreAgrupadorCaptura(String nombreAgrupadorCaptura) {
        this.nombreAgrupadorCaptura = nombreAgrupadorCaptura;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public String getNombreAgrupadorEdicion() {
        return nombreAgrupadorEdicion;
    }

    public void setNombreAgrupadorEdicion(String nombreAgrupadorEdicion) {
        this.nombreAgrupadorEdicion = nombreAgrupadorEdicion;
    }

    public Agrupador getAgrupadorEditado() {
        return agrupadorEditado;
    }

    public void setAgrupadorEditado(Agrupador agrupadorEditado) {
        this.agrupadorEditado = agrupadorEditado;
    }

    public List<EncuestaCaptura> getEncuestasIncidencias() {
        return encuestasIncidencias;
    }

    public void setEncuestasIncidencias(List<EncuestaCaptura> encuestasIncidencias) {
        this.encuestasIncidencias = encuestasIncidencias;
    }

    public List<EncuestaCaptura> getEncuestasNoSolucion() {
        return encuestasNoSolucion;
    }

    public void setEncuestasNoSolucion(List<EncuestaCaptura> encuestasNoSolucion) {
        this.encuestasNoSolucion = encuestasNoSolucion;
    }

    public EncuestaCaptura getEncuestaEditada() {
        return encuestaEditada;
    }

    public void setEncuestaEditada(EncuestaCaptura encuestaEditada) {
        this.encuestaEditada = encuestaEditada;
    }

    public Pregunta getPreguntaEditada() {
        return preguntaEditada;
    }

    public void setPreguntaEditada(Pregunta preguntaEditada) {
        this.preguntaEditada = preguntaEditada;
    }

    public AgrupadorConverter getAgrupadorConverter() {
        return agrupadorConverter;
    }

}
