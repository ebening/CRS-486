/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOEncuestasProyecto;
import com.crossmark.collector.persistence.daos.DAOPromotores;
import com.crossmark.collector.persistence.daos.DAOPromotoresDel;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServicePromotoresImpl implements ServicePromotores{

    private DAOPromotores daoPromotores;
    private DAOPromotoresDel daoPromotoresDel;
    private DAOPromotores daoUsuariosProyectosSel;
    
    private DAOEncuestasProyecto daoEncuestasProyecto;
    
    @Override
    public List<Map<String, Object>> getPromotoresByTerritorioId(Map<String, Object> inputs) {
        return getDaoPromotores().getPromotorByTerritorioId(inputs);
    }

    @Override
    public List<Map<String, Object>> listaProyectosByPromotor(String UsuariosOID, int TerritoriosId, 
            int UnidadesNegociosId, int EquiposId, String nombreTienda, String nombreProyecto) {
        
        return getDaoUsuariosProyectosSel().listaProyectosByPromotor(UsuariosOID, TerritoriosId, 
                UnidadesNegociosId, EquiposId, nombreTienda, nombreProyecto);
    }

    @Override
    public int deletePromotorTerritorio(String UsuariosOID, int TerritoriosId) {
        return getDaoPromotoresDel().deletePromotorTerritorio(UsuariosOID, TerritoriosId);
    }
    
    @Override
    public List<EncuestasProyecto> getListaEncuestasProyecto(Integer unidadesNegociosID, Integer proyectosId) {
        return getDaoEncuestasProyecto().getListaEncuestasProyecto(unidadesNegociosID, proyectosId);
    }
    
    
// ************************* Getters & Setters *******************************//
    /**
     * @return the daoPromotores
     */
    public DAOPromotores getDaoPromotores() {
        return daoPromotores;
    }

    /**
     * @param daoPromotores the daoPromotores to set
     */
    public void setDaoPromotores(DAOPromotores daoPromotores) {
        this.daoPromotores = daoPromotores;
    }

    public DAOPromotoresDel getDaoPromotoresDel() {
        return daoPromotoresDel;
    }

    public void setDaoPromotoresDel(DAOPromotoresDel daoPromotoresDel) {
        this.daoPromotoresDel = daoPromotoresDel;
    }

    public DAOPromotores getDaoUsuariosProyectosSel() {
        return daoUsuariosProyectosSel;
    }

    public void setDaoUsuariosProyectosSel(DAOPromotores daoUsuariosProyectosSel) {
        this.daoUsuariosProyectosSel = daoUsuariosProyectosSel;
    }

    public DAOEncuestasProyecto getDaoEncuestasProyecto() {
        return daoEncuestasProyecto;
    }

    public void setDaoEncuestasProyecto(DAOEncuestasProyecto daoEncuestasProyecto) {
        this.daoEncuestasProyecto = daoEncuestasProyecto;
    } 
    
}
