/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.TerritoriosUsuariosIn;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEquiposTerritoriosUsuariosInImpl  implements DAOEquiposTerritoriosUsuariosIn{
    private DatabaseStoredProc storeEquiposTerritoriosUsuariosInSel;
    
    @Override
    public List<TerritoriosUsuariosIn> getEquiposTerritoriosInFind(String usuariosOID, String cadenaEquipos, Integer territoriosId, Integer pertenece){
        
        List<TerritoriosUsuariosIn> listado = new ArrayList<>();
        
        Utileria.logger(getClass()).info("usuariosOID:"+usuariosOID+"     cadenaEquipos:"+cadenaEquipos+"        territoriosId:"+territoriosId+"      pertenece:"+pertenece);
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("EquiposId", cadenaEquipos);
//        inputs.put("TerritoriosId", territoriosId);
//        inputs.put("Pertenece", pertenece);
        
        Map out = storeEquiposTerritoriosUsuariosInSel.execute(inputs);
        
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map m = (Map)object;
                String [] ts = String.valueOf(m.get("Territorios")).split("@");
                for(String st : ts){
                    listado.add(genericObject(st));
                } 
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return listado;
    }
    
    
    @Override
    public TerritoriosUsuariosIn genericObject(Object args) {
        TerritoriosUsuariosIn o = new TerritoriosUsuariosIn();
//        Map m = (Map)args;
        String terr = (String)args;
        String [] array = terr.split(",");
        
        o.setTerritoriosId(Integer.valueOf(array[0]));
        o.setNombre(array[1]);
        o.setPertenece(0);
        
//        o.setTerritoriosId(Integer.valueOf(String.valueOf(m.get("TerritoriosId") == null ? 0 : m.get("TerritoriosId"))));
//        o.setNombre(String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre")));
//        o.setPertenece(Integer.valueOf(String.valueOf(m.get("Pertenece") == null ? 0 : m.get("Pertenece"))));
        
        return o;
    }

    public DatabaseStoredProc getStoreEquiposTerritoriosUsuariosInSel() {
        return storeEquiposTerritoriosUsuariosInSel;
    }

    public void setStoreEquiposTerritoriosUsuariosInSel(DatabaseStoredProc storeEquiposTerritoriosUsuariosInSel) {
        this.storeEquiposTerritoriosUsuariosInSel = storeEquiposTerritoriosUsuariosInSel;
    }
    
    
    
    
    
}
