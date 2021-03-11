/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEquiposRegionUsuariosImpl  implements DAOEquiposRegionUsuarios{
    private DatabaseStoredProc spEquiposRegionUsuarios;
    
    @Override
    public List<EquiposRegion> getListaEquiposPorRegionYUsuario(String usuariosOID,Integer regionesId){
        List<Map<String, Object>> lstResult=null;
        List<EquiposRegion> listado = new ArrayList<EquiposRegion>();
        EquiposRegion equiposRegion;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("RegionesId", regionesId);
        
        lstResult=spEquiposRegionUsuarios.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                equiposRegion = new EquiposRegion();
                equiposRegion.setEquiposId(Integer.valueOf(String.valueOf(m.get("EquiposId"))));
                equiposRegion.setNombre(String.valueOf(m.get("Nombre")));
                equiposRegion.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo"))));
                listado.add(equiposRegion);
                
                //TerritoriosId, Nombre, Activo, EquiposId, Equipo, Pertenece
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpEquiposRegionUsuarios() {
        return spEquiposRegionUsuarios;
    }

    public void setSpEquiposRegionUsuarios(DatabaseStoredProc spEquiposRegionUsuarios) {
        this.spEquiposRegionUsuarios = spEquiposRegionUsuarios;
    }
    
    
}
