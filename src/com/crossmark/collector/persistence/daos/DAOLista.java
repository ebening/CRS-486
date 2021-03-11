package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Lista;

/**
 * Created by christian on 29/12/2014.
 */
public interface DAOLista extends AbstractDAO<Lista>{

    public DAOTipoLista getDaoTipoLista();

    public void editar2(Lista o);
}
