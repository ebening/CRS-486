/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.persistence.daos.DAOSecciones;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class ServiceSeccionesImpl implements ServiceSecciones {
    
    private DAOSecciones daoListaSecciones;
    private DAOSecciones daoGuardaSeccion;
    private DAOSecciones daoSecciones;
    private DAOSecciones daoEliminaSeccion;

    @Override
    public List<Secciones> listaSecciones(String seccionesOID, String nombreSeccion, int idEncuesta, short orden, boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion, boolean visible) {
    
            return daoListaSecciones.listaSecciones(seccionesOID, nombreSeccion, idEncuesta, orden, activa, enabled, ciclica, fechaCreacion, visible);
    
    }

    /**
     * @return the daoListaSecciones
     */
    public DAOSecciones getDaoListaSecciones() {
        return daoListaSecciones;
    }

    /**
     * @param daoListaSecciones the daoSecciones to set
     */
    public void setDaoListaSecciones(DAOSecciones daoListaSecciones) {
        this.daoListaSecciones = daoListaSecciones;
    }

    @Override
    public String guardaSeccion(String seccionesOID, String nombreSeccion, int idEncuesta, short orden, boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion, boolean seleccionada, String preguntaDominanteOID, Integer tipoListasOpciones, String listasFiltroOID, int variable, boolean controlUsuario, boolean codigoBarras, String listasOID, String claveSeccion, String seccionPadreOID) {
        return daoGuardaSeccion.guardaSeccion(seccionesOID, nombreSeccion, idEncuesta, orden, activa, enabled, ciclica, fechaCreacion, seleccionada, preguntaDominanteOID, tipoListasOpciones, listasFiltroOID, variable, controlUsuario, codigoBarras, listasOID, claveSeccion, seccionPadreOID);
    
    }

    /**
     * @return the daoGuardaSeccion
     */
    public DAOSecciones getDaoGuardaSeccion() {
        return daoGuardaSeccion;
    }

    /**
     * @param daoGuardaSeccion the daoGuardaSeccion to set
     */
    public void setDaoGuardaSeccion(DAOSecciones daoGuardaSeccion) {
        this.daoGuardaSeccion = daoGuardaSeccion;
    }

    /**
     * @return the daoSecciones
     */
    public DAOSecciones getDaoSecciones() {
        return daoSecciones;
    }

    /**
     * @param daoSecciones the daoSecciones to set
     */
    public void setDaoSecciones(DAOSecciones daoSecciones) {
        this.daoSecciones = daoSecciones;
    }

    @Override
    public void eliminaSeccion(String seccionOID) {
        
        daoEliminaSeccion.eliminaSeccion(seccionOID);
    }

    /**
     * @return the daoEliminaSeccion
     */
    public DAOSecciones getDaoEliminaSeccion() {
        return daoEliminaSeccion;
    }

    /**
     * @param daoEliminaSeccion the daoEliminaSeccion to set
     */
    public void setDaoEliminaSeccion(DAOSecciones daoEliminaSeccion) {
        this.daoEliminaSeccion = daoEliminaSeccion;
    }
    
}
