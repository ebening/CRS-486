package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.OpcionPlana;

import java.util.List;

/**
 * Created by christian on 29/12/2014.
 */
public interface DAOOpcionPlana extends AbstractDAO<OpcionPlana> {

    public List<OpcionPlana> getByOIDLista(String oid);
}
