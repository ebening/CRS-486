/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.TipoListaOpciones;
import com.crossmark.collector.persistence.daos.DAOTipoLista;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class ServiceTipoListaImpl implements ServiceTipoLista, Serializable {
    
    private DAOTipoLista daoTipoLista;

    @Override
    public List<TipoListaOpciones> listaTipoLista(Integer tipoListasPreguntasId, boolean secciones, boolean preguntas) {
        return daoTipoLista.listaTipoLista(tipoListasPreguntasId, secciones, preguntas);
    }

    /**
     * @return the daoTipoLista
     */
    public DAOTipoLista getDaoTipoLista() {
        return daoTipoLista;
    }

    /**
     * @param daoTipoLista the daoTipoLista to set
     */
    public void setDaoTipoLista(DAOTipoLista daoTipoLista) {
        this.daoTipoLista = daoTipoLista;
    }
    
}
