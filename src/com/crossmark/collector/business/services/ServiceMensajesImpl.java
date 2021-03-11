package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Mensaje;
import com.crossmark.collector.business.domain.TipoMensaje;
import com.crossmark.collector.persistence.daos.DAOMensajes;
import com.crossmark.collector.persistence.daos.DAOTiposMensajes;

import java.util.List;

/**
 * Created by christian on 05/01/2015.
 */
public class ServiceMensajesImpl implements ServiceMensajes {

    private DAOMensajes daoMensajes;

    private DAOTiposMensajes daoTiposMensajes;

    @Override
    public List<TipoMensaje> getAllTipoMensajes() {
        return daoTiposMensajes.getAll();
    }

    public void setDaoTiposMensajes(DAOTiposMensajes daoTiposMensajes) {
        this.daoTiposMensajes = daoTiposMensajes;
    }

    public DAOMensajes getDaoMensajes() {
        return daoMensajes;
    }

    public void setDaoMensajes(DAOMensajes daoMensajes) {
        this.daoMensajes = daoMensajes;
    }

    @Override
    public List<Mensaje> getAllMensajes() {
        return daoMensajes.getAll();
    }

    @Override
    public List<Mensaje> getAllMensajesByUsuarioOID(String oid) {
        return daoMensajes.getAllMensajesByUsuarioOID(oid);
    }




}
