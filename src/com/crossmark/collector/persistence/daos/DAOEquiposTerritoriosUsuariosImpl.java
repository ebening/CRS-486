/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEquiposTerritoriosUsuariosImpl  implements DAOEquiposTerritoriosUsuarios{
    private DatabaseStoredProc spEquiposTerritoriosUsuarios;
    
    @Override
    public List<TerritoriosUsuarios> getListaTerritoriosPorUsuario(String usuariosOID,Integer equiposId, Integer territoriosId,byte pertenece) {
        List<Map<String, Object>> lstResult;
        List<TerritoriosUsuarios> listado = new ArrayList<>();
        TerritoriosUsuarios equiposTerritoriosUsuarios;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("EquiposId", equiposId);
        
        lstResult=spEquiposTerritoriosUsuarios.execSP(inputs);
        
        if(lstResult!=null){
            for(Map m : lstResult){
                
                equiposTerritoriosUsuarios = new TerritoriosUsuarios();
                equiposTerritoriosUsuarios.setTerritoriosId(Integer.valueOf(String.valueOf(m.get("TerritoriosId"))));
                
                equiposTerritoriosUsuarios.setNombre(String.valueOf(m.get("Nombre")));
                equiposTerritoriosUsuarios.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo"))));
                //equiposTerritoriosUsuarios.setEquiposId(Integer.valueOf(String.valueOf(m.get("EquiposId"))));
                //equiposTerritoriosUsuarios.setEquipo(String.valueOf(m.get("Equipo")));
                equiposTerritoriosUsuarios.setPertenece(Integer.valueOf(String.valueOf(m.get("Pertenece"))));
                
                listado.add(equiposTerritoriosUsuarios);
                
                //TerritoriosId, Nombre, Activo, EquiposId, Equipo, Pertenece
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpEquiposTerritoriosUsuarios() {
        return spEquiposTerritoriosUsuarios;
    }

    public void setSpEquiposTerritoriosUsuarios(DatabaseStoredProc spEquiposTerritoriosUsuarios) {
        this.spEquiposTerritoriosUsuarios = spEquiposTerritoriosUsuarios;
    }
    
    
    
    
}
