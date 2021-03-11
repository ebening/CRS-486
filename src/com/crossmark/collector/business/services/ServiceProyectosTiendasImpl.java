/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOGruposProyectosTiendas;
import com.crossmark.collector.persistence.daos.DAOProyectosTiendas;
import com.crossmark.collector.persistence.daos.DAOTiendas;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceProyectosTiendasImpl implements ServiceProyectosTiendas, Serializable{
    private DAOProyectosTiendas daoProyectosTiendasSel;
    private DAOProyectosTiendas daoProyectosTiendasUps;
    private DAOProyectosTiendas daoProyectosTiendasDel;
    
    private DAOGruposProyectosTiendas daoGruposProyectosSel;
    private DAOGruposProyectosTiendas daoGruposProyectosDel;
    private DAOGruposProyectosTiendas daoGruposProyectosUps;
    
    private DAOGruposProyectosTiendas daoGruposProyectosTiendasSel;
    private DAOGruposProyectosTiendas daoGruposProyectosTiendasDel;
    private DAOGruposProyectosTiendas daoGruposProyectosTiendasUps;
    
    private DAOTiendas daoTiendas;

    @Override
    public List<Map<String, Object>> listaTiendasProyecto(int proyectoId, int unidadesNegociosId, int equipoId, int clienteId, String nombre, int cadenasId, int formatoId, int estadosId, int ciudadesId) {
         return getDaoProyectosTiendasSel().listaTiendasProyecto(proyectoId, unidadesNegociosId, equipoId, clienteId, nombre, cadenasId, formatoId, estadosId, ciudadesId);
    }

    @Override
    public HashMap<String, Object> guardaTiendaProyecto(int proyectoId, int tiendaId) {
        return getDaoProyectosTiendasUps().guardaTiendaProyecto(proyectoId, tiendaId);
    }
    
    @Override
    public HashMap<String, Object> borraTiendaProyecto(int proyectoId, int tiendaId) {
        return getDaoProyectosTiendasDel().borraTiendaProyecto(proyectoId, tiendaId);
    }

    @Override
    public List<Map<String, Object>> listaGruposProyectos(int GruposProyectosId, String nombre, int ClientesId) {
        return getDaoGruposProyectosSel().listaGruposProyectos(GruposProyectosId, nombre, ClientesId);
    }

    @Override
    public HashMap<String, Object> borrarGruposProyectos(int GruposProyectosId) {
        return getDaoGruposProyectosDel().borrarGruposProyectos(GruposProyectosId);
    }

    @Override
    public HashMap<String, Object> updateGruposProyectos(int GruposProyectosId, String nombre, int ClientesId) {
        return getDaoGruposProyectosUps().updateGruposProyectos(GruposProyectosId, nombre, ClientesId);
    }

    @Override
    public List<Map<String, Object>> listaGruposProyectosTiendas(int GruposProyectosId) {
        return getDaoGruposProyectosTiendasSel().listaGruposProyectosTiendas(GruposProyectosId);
    }

    @Override
    public HashMap<String, Object> borrarGruposProyectosTiendas(int GruposProyectosId, int TiendasId) {
        return getDaoGruposProyectosTiendasDel().borrarGruposProyectosTiendas(GruposProyectosId, TiendasId);
    }

    @Override
    public HashMap<String, Object> updateGruposProyectosTiendas(int GruposProyectosId, int TiendasId) {
        return getDaoGruposProyectosTiendasUps().updateGruposProyectosTiendas(GruposProyectosId, TiendasId);
    }
    
    @Override
    public List<Map<String, Object>> getTiendasList(HashMap<String, Object> filtros) {
        return getDaoTiendas().getTiendasList(filtros);
    }
    
// *********************************** Getters & Setters ***************************************** //
    
    public DAOProyectosTiendas getDaoProyectosTiendasSel() {
        return daoProyectosTiendasSel;
    }

    public void setDaoProyectosTiendasSel(DAOProyectosTiendas daoProyectosTiendasSel) {
        this.daoProyectosTiendasSel = daoProyectosTiendasSel;
    }

    public DAOProyectosTiendas getDaoProyectosTiendasUps() {
        return daoProyectosTiendasUps;
    }

    public void setDaoProyectosTiendasUps(DAOProyectosTiendas daoProyectosTiendasUps) {
        this.daoProyectosTiendasUps = daoProyectosTiendasUps;
    }

    public DAOProyectosTiendas getDaoProyectosTiendasDel() {
        return daoProyectosTiendasDel;
    }

    public void setDaoProyectosTiendasDel(DAOProyectosTiendas daoProyectosTiendasDel) {
        this.daoProyectosTiendasDel = daoProyectosTiendasDel;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosSel() {
        return daoGruposProyectosSel;
    }

    public void setDaoGruposProyectosSel(DAOGruposProyectosTiendas daoGruposProyectosSel) {
        this.daoGruposProyectosSel = daoGruposProyectosSel;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosDel() {
        return daoGruposProyectosDel;
    }

    public void setDaoGruposProyectosDel(DAOGruposProyectosTiendas daoGruposProyectosDel) {
        this.daoGruposProyectosDel = daoGruposProyectosDel;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosUps() {
        return daoGruposProyectosUps;
    }

    public void setDaoGruposProyectosUps(DAOGruposProyectosTiendas daoGruposProyectosUps) {
        this.daoGruposProyectosUps = daoGruposProyectosUps;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosTiendasSel() {
        return daoGruposProyectosTiendasSel;
    }

    public void setDaoGruposProyectosTiendasSel(DAOGruposProyectosTiendas daoGruposProyectosTiendasSel) {
        this.daoGruposProyectosTiendasSel = daoGruposProyectosTiendasSel;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosTiendasDel() {
        return daoGruposProyectosTiendasDel;
    }

    public void setDaoGruposProyectosTiendasDel(DAOGruposProyectosTiendas daoGruposProyectosTiendasDel) {
        this.daoGruposProyectosTiendasDel = daoGruposProyectosTiendasDel;
    }

    public DAOGruposProyectosTiendas getDaoGruposProyectosTiendasUps() {
        return daoGruposProyectosTiendasUps;
    }

    public void setDaoGruposProyectosTiendasUps(DAOGruposProyectosTiendas daoGruposProyectosTiendasUps) {
        this.daoGruposProyectosTiendasUps = daoGruposProyectosTiendasUps;
    }   

    public DAOTiendas getDaoTiendas() {
        return daoTiendas;
    }

    public void setDaoTiendas(DAOTiendas daoTiendas) {
        this.daoTiendas = daoTiendas;
    }

    
}
