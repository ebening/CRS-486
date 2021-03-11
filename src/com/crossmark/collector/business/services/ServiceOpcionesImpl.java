/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.persistence.daos.DAOOpciones;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class ServiceOpcionesImpl implements ServiceOpciones {
    
    private DAOOpciones daoOpciones;

    @Override
    public List<Opciones> traerOpciones(String unCatalogoOID, String opcionesOID) {
        
        return getDaoOpciones().traerOpciones(unCatalogoOID, opcionesOID);
    }

    /**
     * @return the daoOpciones
     */
    public DAOOpciones getDaoOpciones() {
        return daoOpciones;
    }

    /**
     * @param daoOpciones the daoOpciones to set
     */
    public void setDaoOpciones(DAOOpciones daoOpciones) {
        this.daoOpciones = daoOpciones;
    }
    
}
