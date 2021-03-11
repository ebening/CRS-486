/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAORegionesUsuariosImpl  implements DAORegionesUsuarios{
    private DatabaseStoredProc spRegionUsuarios;
    
    @Override
    public List<RegionesUsuarios> getListaRegionesPorUsuario(String usuariosOID) {
        List<Map<String, Object>> lstResult=null;
        List<RegionesUsuarios> listado = new ArrayList<RegionesUsuarios>();
        RegionesUsuarios regionesUsuarios;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        
        lstResult=spRegionUsuarios.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                regionesUsuarios = new RegionesUsuarios();
                regionesUsuarios.setRegionesId(Integer.valueOf(String.valueOf(m.get("RegionesId"))));
                regionesUsuarios.setNombre(String.valueOf(m.get("Nombre")));
                regionesUsuarios.setActivo(Boolean.parseBoolean(String.valueOf(m.get("Activo"))));
                listado.add(regionesUsuarios);
                
                //RegionesId, Nombre, Activo
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpRegionUsuarios() {
        return spRegionUsuarios;
    }

    public void setSpRegionUsuarios(DatabaseStoredProc spRegionUsuarios) {
        this.spRegionUsuarios = spRegionUsuarios;
    }
    
    
    
}
