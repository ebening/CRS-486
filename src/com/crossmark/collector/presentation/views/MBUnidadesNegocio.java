package com.crossmark.collector.presentation.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.business.services.ServiceUnidadesNegocio;

@ManagedBean
public class MBUnidadesNegocio implements Serializable {

    private static final long serialVersionUID = 1L;

    ServiceUnidadesNegocio serviceUnidadesNegocio;
    private List<UnidadesNegocio> listaUnidadesNegocio = new ArrayList<UnidadesNegocio>();

    private UnidadesNegocio unidadSeleccionada;

    public List<UnidadesNegocio> miListaUnidadesNegocio() {
        
        this.setListaUnidadesNegocio(serviceUnidadesNegocio.listaUnidadesNegocio());
        return listaUnidadesNegocio;
    }

    public ServiceUnidadesNegocio getServiceUnidadesNegocio() {
        return serviceUnidadesNegocio;
    }

    public void setServiceUnidadesNegocio(
            ServiceUnidadesNegocio serviceUnidadesNegocio) {
        this.serviceUnidadesNegocio = serviceUnidadesNegocio;
    }

    public List<UnidadesNegocio> getListaUnidadesNegocio() {
        return listaUnidadesNegocio;
    }

    public void setListaUnidadesNegocio(List<UnidadesNegocio> listaUnidadesNegocio) {
        this.listaUnidadesNegocio = listaUnidadesNegocio;
    }

    public UnidadesNegocio getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(UnidadesNegocio unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

}
