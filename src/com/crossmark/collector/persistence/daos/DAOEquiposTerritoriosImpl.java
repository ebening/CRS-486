/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.EquipoTerritorio;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author franciscom
 */
public class DAOEquiposTerritoriosImpl implements DAOEquiposTerritorios{
    DatabaseStoredProc spEquiposTerritorios;
    
    @Override
    public List<EquipoTerritorio> getEquiposTerritoriosByFind(Integer equipoId, Integer territorioId) {
        List<Map<String, Object>> lstResult=null;
        
        List<EquipoTerritorio> listado = new ArrayList<EquipoTerritorio>();
        EquipoTerritorio equipoTerritorio;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", equipoId);
        inputs.put("TerritoriosId", territorioId);
        
        lstResult=spEquiposTerritorios.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                equipoTerritorio = new EquipoTerritorio();
                
                equipoTerritorio.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo"))));
                equipoTerritorio.setEquipo(String.valueOf(m.get("Equipo")));
                equipoTerritorio.setEquipoId(Integer.valueOf(String.valueOf(m.get("EquiposId"))));
                equipoTerritorio.setNombre(String.valueOf(m.get("Nombre")));
                equipoTerritorio.setTerritorioId(Integer.valueOf(String.valueOf(m.get("TerritoriosId"))));
                
                listado.add(equipoTerritorio);
                //int Equiposd, String Nombre, boolean Activo, int RegionesId
            }
        }else{
            //System.out.println("out is null ");
        }
        // TODO Auto-generated method stub
        return listado;
        
    }
    
    public DatabaseStoredProc getSpEquiposTerritorios() {
        return spEquiposTerritorios;
    }

    public void setSpEquiposTerritorios(DatabaseStoredProc spEquiposTerritorios) {
        this.spEquiposTerritorios = spEquiposTerritorios;
    }
    
}
