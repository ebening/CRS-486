package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Mensaje;

import java.util.List;

/**
 * Created by christian on 05/01/2015.
 */
public interface DAOMensajes  extends  AbstractDAO<Mensaje>{

    public List<Mensaje> getAllMensajesByUsuarioOID(String oid);
}
