/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOPromotoresImpl implements DAOPromotores{

    private DatabaseStoredProc spPromotores;
    private DatabaseStoredProc spUsuariosProyectosSel;
    
    @Override
    public List<Map<String, Object>> getPromotorByTerritorioId(Map<String, Object> inputs) {
        
        List<Map<String, Object>> response =  getSpPromotores().execSP(inputs);
        return response;
    }

    @Override
    public List<Map<String, Object>> listaProyectosByPromotor(String UsuariosOID, int TerritoriosId, int UnidadesNegociosId, int EquiposId, String nombreTienda, String nombreProyecto) {
        Map<String, Object> inputs = new TreeMap<>();
        List<Map<String, Object>> listado;
        inputs.put("UsuariosOID", UsuariosOID);
        inputs.put("TerritoriosId", TerritoriosId == 0 ? null : TerritoriosId);
        inputs.put("UnidadesNegociosId", UnidadesNegociosId == 0 ? null : UnidadesNegociosId);
        inputs.put("EquiposId", EquiposId == 0 ? null : EquiposId);
        inputs.put("NombreTienda", nombreTienda);
        inputs.put("NombreProyecto", nombreProyecto);
        listado = getSpUsuariosProyectosSel().execSP(inputs);
        return listado;
    }
    
    /**
     * @return the spPromotores
     */
    public DatabaseStoredProc getSpPromotores() {
        return spPromotores;
    }

    /**
     * @param spPromotores the spPromotores to set
     */
    public void setSpPromotores(DatabaseStoredProc spPromotores) {
        this.spPromotores = spPromotores;
    }

    public DatabaseStoredProc getSpUsuariosProyectosSel() {
        return spUsuariosProyectosSel;
    }

    public void setSpUsuariosProyectosSel(DatabaseStoredProc spUsuariosProyectosSel) {
        this.spUsuariosProyectosSel = spUsuariosProyectosSel;
    }
  
    
}
