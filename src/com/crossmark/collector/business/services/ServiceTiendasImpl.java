package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOTiendas;
import com.crossmark.collector.persistence.daos.DAOTiendasTerritorios;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceTiendasImpl implements ServiceTiendas{
    private DAOTiendas daoTiendas;
    private DAOTiendas daoTiendasNumReg;
    private DAOTiendas daoTiendasTerritoriosSel;
    private DAOTiendas daoTiendasTerritoriosNumRegSel;
    private DAOTiendasTerritorios daoTiendasTerritorios;
    private DAOTiendasTerritorios daoTiendasTerritoriosDel;
    /**
     * @return the daoTiendas
     */
    public DAOTiendas getDaoTiendas() {
        return daoTiendas;
    }

    @Override
    public List getTiendasList() {
        return daoTiendas.getTiendasList();
    }

    @Override
    public List<Map<String, Object>> getTiendaById(int idTienda) {
        return daoTiendas.getTiendaById(idTienda);
    }

    @Override
    public List<Map<String, Object>> getTiendasList(HashMap<String, Object>filtros) {
        return daoTiendas.getTiendasList(filtros);
    }

 /*   @Override
    public int insertTienda(int TiendaId, int TerritorioId) {
        return daoTiendas.insertTienda(TiendaId, TerritorioId);
    } */

    @Override
    public HashMap<String, Object> insertTienda(int TiendaId, int TerritorioId, int EquiposId) {
        return daoTiendasTerritorios.insertTiendasByTerritoriosId(TiendaId, TerritorioId, EquiposId);
    }
    
    @Override
    public HashMap<String, Object> borrarTienda(int TiendaId, int TerritorioId, int EquiposId) {
        return getDaoTiendasTerritoriosDel().borrarTiendasByTerritoriosId(TiendaId, TerritorioId, EquiposId);
    }
    
    @Override
    public List<Map<String, Object>> tiendasListBySearch(HashMap<String, Object> filtros) {
        return getDaoTiendasTerritoriosSel().tiendasListBySearch(filtros);
    }
    
    @Override
    public int tiendasTerritoriosNumRegSel(HashMap<String, Object> filtros) {
        return getDaoTiendasTerritoriosNumRegSel().tiendasTerritoriosNumReg(filtros);
    }
    
    @Override
    public int tiendasNumRegSel(HashMap<String, Object> filtros) {
        return getDaoTiendasNumReg().tiendasNumReg(filtros);
    }
    
// ***************************************** Getters & Setters ****************************** //    

    public DAOTiendasTerritorios getDaoTiendasTerritorios() {
        return daoTiendasTerritorios;
    }

    public void setDaoTiendasTerritorios(DAOTiendasTerritorios daoTiendasTerritorios) {
        this.daoTiendasTerritorios = daoTiendasTerritorios;
    }

    public void setDaoTiendas(DAOTiendas daoTiendas) {
        this.daoTiendas = daoTiendas;
    }

    public DAOTiendasTerritorios getDaoTiendasTerritoriosDel() {
        return daoTiendasTerritoriosDel;
    }

    public void setDaoTiendasTerritoriosDel(DAOTiendasTerritorios daoTiendasTerritoriosDel) {
        this.daoTiendasTerritoriosDel = daoTiendasTerritoriosDel;
    }

    public DAOTiendas getDaoTiendasTerritoriosSel() {
        return daoTiendasTerritoriosSel;
    }

    public void setDaoTiendasTerritoriosSel(DAOTiendas daoTiendasTerritoriosSel) {
        this.daoTiendasTerritoriosSel = daoTiendasTerritoriosSel;
    } 

    public DAOTiendas getDaoTiendasTerritoriosNumRegSel() {
        return daoTiendasTerritoriosNumRegSel;
    }

    public void setDaoTiendasTerritoriosNumRegSel(DAOTiendas daoTiendasTerritoriosNumRegSel) {
        this.daoTiendasTerritoriosNumRegSel = daoTiendasTerritoriosNumRegSel;
    }

    public DAOTiendas getDaoTiendasNumReg() {
        return daoTiendasNumReg;
    }

    public void setDaoTiendasNumReg(DAOTiendas daoTiendasNumReg) {
        this.daoTiendasNumReg = daoTiendasNumReg;
    }

    
}
