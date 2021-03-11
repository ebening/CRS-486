/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOVisitas;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceVisitasImpl implements ServiceVisitas, Serializable{

    private DAOVisitas daoVisitas;
    private DAOVisitas daoVisitasDel;
    private DAOVisitas daoVisitasVal;
    private DAOVisitas daoVisitasTiendasSel;
    private DAOVisitas daoVisitasTiendasUps;
    private DAOVisitas daoVisitasTiendasDel;
    private DAOVisitas daoVisitasRutasSel;
    private DAOVisitas daoVisitasRutasUsuariosSel;
    private DAOVisitas daoVisitasUps;
    
    @Override
    public HashMap<String, Object> guardaVisita(int VisitasId, Date FechaIni, int RutasId, String Nombre, String UsuariosOID) {
        return getDaoVisitasUps().guardaVisita(VisitasId, FechaIni, RutasId, Nombre, UsuariosOID);
    }
    
    @Override
    public List<Map<String, Object>> getVisitasByIdTienda(int TiendaId) {
        return daoVisitas.getVisitasByIdTienda(TiendaId);
    }

    @Override
    public List<Map<String, Object>> visitasByTerritorio(int TerritorioId,int EquiposId, int UnidadesNegociosId) {
        return daoVisitas.visitasByTerritorios(TerritorioId, EquiposId, UnidadesNegociosId);
    }
    
    @Override
    public List<Map<String, Object>> visitasTiendas(int RutasId, int VisitasId) {
        return getDaoVisitasTiendasSel().visitasTiendas(RutasId, VisitasId);
    }
    
    @Override
    public List<Map<String, Object>> listaRutasSel(int TerritoriosId, int EquiposId) {
        return getDaoVisitasRutasSel().listaRutasSel(TerritoriosId, EquiposId);
    }

    @Override
    public List<Map<String, Object>> listaRutasUsuariosSel(int RutasId, int TerritoriosId, int EquiposId, int UnidadesNegociosId) {
        return getDaoVisitasRutasUsuariosSel().listaRutasUsuariosSel(RutasId, TerritoriosId, EquiposId, UnidadesNegociosId);
    }
    
    @Override
    public HashMap<String, Object> eliminaVisita(int TareasId) {
        return getDaoVisitasTiendasDel().eliminaVisita(TareasId);
    }
    
    @Override
    public HashMap<String, Object> visitasVal(int VisitasId) {
        return getDaoVisitasVal().visitasVal(VisitasId);
    }
    
    @Override
    public HashMap<String, Object> guardaTiendasVisitas(int VisitasId, int TiendasId, int ProyectosId, int EncuestasId) {
        return getDaoVisitasTiendasUps().guardaTiendasVisitas(VisitasId, TiendasId, ProyectosId, EncuestasId);
    }
    
    /**
     * @return the daoVisitas
     */
    public DAOVisitas getDaoVisitas() {
        return daoVisitas;
    }

    /**
     * @param daoVisitas the daoVisitas to set
     */
    public void setDaoVisitas(DAOVisitas daoVisitas) {
        this.daoVisitas = daoVisitas;
    }

    public DAOVisitas getDaoVisitasTiendasSel() {
        return daoVisitasTiendasSel;
    }

    public void setDaoVisitasTiendasSel(DAOVisitas daoVisitasTiendasSel) {
        this.daoVisitasTiendasSel = daoVisitasTiendasSel;
    }

    public DAOVisitas getDaoVisitasRutasSel() {
        return daoVisitasRutasSel;
    }

    public void setDaoVisitasRutasSel(DAOVisitas daoVisitasRutasSel) {
        this.daoVisitasRutasSel = daoVisitasRutasSel;
    }

    public DAOVisitas getDaoVisitasRutasUsuariosSel() {
        return daoVisitasRutasUsuariosSel;
    }

    public void setDaoVisitasRutasUsuariosSel(DAOVisitas daoVisitasRutasUsuariosSel) {
        this.daoVisitasRutasUsuariosSel = daoVisitasRutasUsuariosSel;
    }

    public DAOVisitas getDaoVisitasDel() {
        return daoVisitasDel;
    }

    public void setDaoVisitasDel(DAOVisitas daoVisitasDel) {
        this.daoVisitasDel = daoVisitasDel;
    }

    public DAOVisitas getDaoVisitasUps() {
        return daoVisitasUps;
    }

    public void setDaoVisitasUps(DAOVisitas daoVisitasUps) {
        this.daoVisitasUps = daoVisitasUps;
    } 

    public DAOVisitas getDaoVisitasVal() {
        return daoVisitasVal;
    }

    public void setDaoVisitasVal(DAOVisitas daoVisitasVal) {
        this.daoVisitasVal = daoVisitasVal;
    }

    public DAOVisitas getDaoVisitasTiendasUps() {
        return daoVisitasTiendasUps;
    }

    public void setDaoVisitasTiendasUps(DAOVisitas daoVisitasTiendasUps) {
        this.daoVisitasTiendasUps = daoVisitasTiendasUps;
    }

    public DAOVisitas getDaoVisitasTiendasDel() {
        return daoVisitasTiendasDel;
    }

    public void setDaoVisitasTiendasDel(DAOVisitas daoVisitasTiendasDel) {
        this.daoVisitasTiendasDel = daoVisitasTiendasDel;
    }

    
 
    
}
