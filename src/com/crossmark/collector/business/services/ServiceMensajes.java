package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Mensaje;
import com.crossmark.collector.business.domain.TipoMensaje;

import java.util.List;

/**
 * Created by christian on 05/01/2015.
 */
public interface ServiceMensajes {

    public List<Mensaje> getAllMensajes();

    public List<Mensaje> getAllMensajesByUsuarioOID(String oid);

    public List<TipoMensaje> getAllTipoMensajes();

}
