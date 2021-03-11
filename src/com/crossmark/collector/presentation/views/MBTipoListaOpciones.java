/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.TipoListaOpciones;
import com.crossmark.collector.business.services.ServiceTipoLista;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class MBTipoListaOpciones implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private List<TipoListaOpciones> listaTipoLista = new ArrayList<TipoListaOpciones>();
    private ServiceTipoLista serviceTipoLista;
    
    public List<TipoListaOpciones> comboTipoLista(){
        this.setListaTipoLista(serviceTipoLista.listaTipoLista(null, false, false));
        return listaTipoLista;
    }
    
    public List<TipoListaOpciones> comboTipoListaSecciones(){
        
        this.setListaTipoLista(serviceTipoLista.listaTipoLista(null, true, false));
        return listaTipoLista;
    }
    
    public List<TipoListaOpciones> comboTipoListaPreguntas(){
        
        this.setListaTipoLista(serviceTipoLista.listaTipoLista(null, false, true));
        return listaTipoLista;
    }
    
    /**
     * @return the listaTipoLista
     */
    public List<TipoListaOpciones> getListaTipoLista() {
        return listaTipoLista;
    }

    /**
     * @param listaTipoLista the listaTipoLista to set
     */
    public void setListaTipoLista(List<TipoListaOpciones> listaTipoLista) {
        this.listaTipoLista = listaTipoLista;
    }

    /**
     * @return the serviceTipoLista
     */
    public ServiceTipoLista getServiceTipoLista() {
        return serviceTipoLista;
    }

    /**
     * @param serviceTipoLista the serviceTipoLista to set
     */
    public void setServiceTipoLista(ServiceTipoLista serviceTipoLista) {
        this.serviceTipoLista = serviceTipoLista;
    }
    
    
}
