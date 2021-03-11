/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author jdominguez
 */
public class DAOPromotoresBusImpl implements DAOPromotoresBus{
    private DatabaseStoredProc spPromotoresBus;
    private DatabaseStoredProc spUsuariosTerritoriosUps;

    @Override
    public List<Map<String, Object>> buscarPromotores(Map<String, Object> inputs) {
        //Map<String, Object> inputs=new TreeMap<>();
        List<Map<String, Object>> r = getSpPromotoresBus().execSP(inputs);
        return r;
    }
    
    @Override
    public HashMap<String, Object> guardarPromotoresTerritorios(String UsuariosOID, int TerritoriosId, int EquiposId) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> response = new HashMap<>();
        inputs.put("UsuariosOID", UsuariosOID);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);
        try{
            Map<String, Object> mresponse = getSpUsuariosTerritoriosUps().execute(inputs);
            response.put("Exito", true);
            response.put("Msj", "Promotor Guardado");
        }catch(DuplicateKeyException ex){
            response.put("Exito", false);
            response.put("Msj", ex.getMessage());
        }
        return response;
    }
    
    public DatabaseStoredProc getSpPromotoresBus() {
        return spPromotoresBus;
    }

    public void setSpPromotoresBus(DatabaseStoredProc spPromotoresBus) {
        this.spPromotoresBus = spPromotoresBus;
    }

    public DatabaseStoredProc getSpUsuariosTerritoriosUps() {
        return spUsuariosTerritoriosUps;
    }

    public void setSpUsuariosTerritoriosUps(DatabaseStoredProc spUsuariosTerritoriosUps) {
        this.spUsuariosTerritoriosUps = spUsuariosTerritoriosUps;
    }
 
}
