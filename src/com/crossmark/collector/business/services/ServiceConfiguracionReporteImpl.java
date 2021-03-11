package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Agrupador;
import com.crossmark.collector.persistence.daos.DAOConfiguracionReporte;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestaCaptura;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Pregunta;
import java.util.List;

/**
 * Implementación de servicio para configuración de reporte de incidencias no
 * solucionadas.
 *
 */
public class ServiceConfiguracionReporteImpl implements ServiceConfiguracionReporte {

    private DAOConfiguracionReporte daoConfiguracionReporte;

    public ServiceConfiguracionReporteImpl() {
    }

    public DAOConfiguracionReporte getDaoConfiguracionReporte() {
        return daoConfiguracionReporte;
    }

    public void setDaoConfiguracionReporte(DAOConfiguracionReporte daoConfiguracionReporte) {
        this.daoConfiguracionReporte = daoConfiguracionReporte;
    }

    @Override
    public List<Agrupador> listaAgrupador(int equiposId, int agrupadoresId) {
        return daoConfiguracionReporte.listaAgrupadores(equiposId, agrupadoresId);
    }

    @Override
    public void borrarAgrupador(Agrupador agrupador) {
        daoConfiguracionReporte.eliminaAgrupador(agrupador.getAgrupadoresId());
    }

    @Override
    public void guardaAgrupador(Agrupador agrupador) {
        int id = agrupador.getAgrupadoresId();
        int equipo = agrupador.getEquiposId();
        String nombre = agrupador.getNombre();
        int idNuevo = daoConfiguracionReporte.actualizaAgrupador(id, equipo, nombre);
        agrupador.setAgrupadoresId(idNuevo);
    }

    @Override
    public List<EncuestaCaptura> listaEncuestasCaptura(int equiposId) {
        return daoConfiguracionReporte.listaEncuestasCaptura(equiposId);
    }

    @Override
    public List<Pregunta> listaOpcionesCaptura(int equiposId) {
        return daoConfiguracionReporte.listaOpcionesCaptura(equiposId);
    }

    @Override
    public void actualizaOpcionesCaptura(Pregunta pregunta) {
        int nuevoId = daoConfiguracionReporte.actualizaOpcionesCaptura(pregunta);
        pregunta.setAgrupadoresOpcionesId(nuevoId);
    }

    @Override
    public void eliminaOpcionesCaptura(Pregunta pregunta) {
        daoConfiguracionReporte.eliminaOpcionesCaptura(pregunta);
    }

    @Override
    public List<EncuestaCaptura> listaEncuestasNoSolucion(int equiposId) {
        return daoConfiguracionReporte.listaEncuestasNoSolucion(equiposId);
    }

    @Override
    public List<Pregunta> listaOpcionesNoSolucion(int equiposId) {
        return daoConfiguracionReporte.listaOpcionesNoSolucion(equiposId);
    }

    @Override
    public void actualizaOpcionesNoSolucion(Pregunta opcion) {
        int nuevoId = daoConfiguracionReporte.actualizaOpcionesNoSolucion(opcion);
        opcion.setAgrupadoresOpcionesId(nuevoId);
    }

    @Override
    public void eliminaOpcionesNoSolucion(Pregunta opcion) {
        daoConfiguracionReporte.eliminaOpcionesNoSolucion(opcion);
    }

}
