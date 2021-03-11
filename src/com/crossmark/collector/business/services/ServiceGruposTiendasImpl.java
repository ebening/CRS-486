/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOGruposTiendas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceGruposTiendasImpl implements ServiceGruposTiendas{

    DAOGruposTiendas daoGruposSel; 
    DAOGruposTiendas daoGruposUps;
    
    DAOGruposTiendas daoGruposTiendasUps;
    DAOGruposTiendas daoGruposTiendasSel;
    DAOGruposTiendas daoGruposTiendasDel;
    
    @Override
    public HashMap<String, Object> gruposTiendasDel(int GruposId, int TiendasId) {
        return getDaoGruposTiendasDel().gruposTiendasDel(GruposId, TiendasId);
    }
    
    @Override
    public HashMap<String, Object> gruposTiendasUps(int GruposId, int TiendasId) {
        return getDaoGruposTiendasUps().gruposTiendasUps(GruposId, TiendasId);
    }
    
    @Override
    public List<Map<String, Object>> listaGrupos(int GruposId, String Nombre, int ClientesId, int UnidadesNegociosId) {
        return getDaoGruposSel().listaGrupos(GruposId, Nombre, ClientesId, UnidadesNegociosId);
    }
    
    @Override
    public List<Map<String, Object>> listaTiendasByGrupoId(int GruposId) {
        return getDaoGruposTiendasSel().listaTiendasByGrupoId(GruposId);
    }
    
    @Override
    public HashMap<String, Object> gruposUps(int GruposId, String nombre) {
        return getDaoGruposUps().gruposUps(GruposId, nombre);
    }

    public DAOGruposTiendas getDaoGruposSel() {
        return daoGruposSel;
    }

    public void setDaoGruposSel(DAOGruposTiendas daoGruposSel) {
        this.daoGruposSel = daoGruposSel;
    }

    public DAOGruposTiendas getDaoGruposTiendasSel() {
        return daoGruposTiendasSel;
    }

    public void setDaoGruposTiendasSel(DAOGruposTiendas daoGruposTiendasSel) {
        this.daoGruposTiendasSel = daoGruposTiendasSel;
    }

    public DAOGruposTiendas getDaoGruposUps() {
        return daoGruposUps;
    }

    public void setDaoGruposUps(DAOGruposTiendas daoGruposUps) {
        this.daoGruposUps = daoGruposUps;
    }

    public DAOGruposTiendas getDaoGruposTiendasUps() {
        return daoGruposTiendasUps;
    }

    public void setDaoGruposTiendasUps(DAOGruposTiendas daoGruposTiendasUps) {
        this.daoGruposTiendasUps = daoGruposTiendasUps;
    }

    public DAOGruposTiendas getDaoGruposTiendasDel() {
        return daoGruposTiendasDel;
    }

    public void setDaoGruposTiendasDel(DAOGruposTiendas daoGruposTiendasDel) {
        this.daoGruposTiendasDel = daoGruposTiendasDel;
    }

    
    
    
}
