/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.TipoLista;
import com.crossmark.collector.business.domain.TipoListaOpciones;
import java.util.List;

/**
 *
 * @author RIGG
 */
public interface DAOTipoLista extends AbstractDAO<TipoLista> {
    
    public List<TipoListaOpciones> listaTipoLista(Integer tipoListasPreguntasId, boolean secciones, boolean preguntas);

    public DAOOpcionCruzada getDaoOpcionCruzada();
    public DAOOpcionPlana getDaoOpcionPlana();
    
}
