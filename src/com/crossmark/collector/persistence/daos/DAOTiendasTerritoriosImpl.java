/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author jdominguez
 */
public class DAOTiendasTerritoriosImpl implements DAOTiendasTerritorios{

    DatabaseStoredProc spTiendasTerritoriosups;
    DatabaseStoredProc spTiendasTerritoriosDel;
    
    @Override
    public HashMap<String, Object> insertTiendasByTerritoriosId(int TiendaId, int TerritoriosId, int EquiposId) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> response = new HashMap<>();
        inputs.put("TiendasId", TiendaId);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);
        Map out;
        try{
            out = getSpTiendasTerritoriosups().execute(inputs);
          // List<Map<Integer, Object>> r = getSpTiendasTerritoriosups().execSPI(inputs);
            List<Map<Integer, Object>> r = Utileria.getInsertResponse(out);
            response.put("Exito", true);
            response.put("Msj", "");
        }catch(DuplicateKeyException ex){
            response.put("Exito", false);
            response.put("Msj", "Tienda Duplicada para el Territorio");
        }catch(Exception e){
            response.put("Exito", false);
            response.put("Msj", e.getMessage());
        }
        return response;
    }
    
    @Override
    public HashMap<String, Object> borrarTiendasByTerritoriosId(int TiendaId, int TerritoriosId, int EquiposId) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        inputs.put("TiendasId", TiendaId);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);

        try{
            List<Map<String, Object>> response = getSpTiendasTerritoriosDel().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msj", "Tienda eliminada del Territorio");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    public DatabaseStoredProc getSpTiendasTerritoriosups() {
        return spTiendasTerritoriosups;
    }

    public void setSpTiendasTerritoriosups(DatabaseStoredProc spTiendasTerritoriosups) {
        this.spTiendasTerritoriosups = spTiendasTerritoriosups;
    }

    public DatabaseStoredProc getSpTiendasTerritoriosDel() {
        return spTiendasTerritoriosDel;
    }

    public void setSpTiendasTerritoriosDel(DatabaseStoredProc spTiendasTerritoriosDel) {
        this.spTiendasTerritoriosDel = spTiendasTerritoriosDel;
    }
}
