package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.OpcionCruzada;

import java.util.List;

/**
 * Created by christian on 23/12/2014.
 */
public interface DAOOpcionCruzada extends  AbstractDAO<OpcionCruzada>{

    public List<OpcionCruzada> getByOIDLista(String oid);

    public void grabar(OpcionCruzada o);
}
