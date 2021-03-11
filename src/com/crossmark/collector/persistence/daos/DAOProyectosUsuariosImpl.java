/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOProyectosUsuariosImpl  implements DAOProyectosUsuarios{
    private DatabaseStoredProc spProyectosUsuarios;
    
    @Override
    public List<ProyectosUsuarios> getListaProyectosPorUsuario(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId) {
        List<Map<String, Object>> lstResult=null;
        List<ProyectosUsuarios> listado = new ArrayList<ProyectosUsuarios>();
        ProyectosUsuarios proyectosUsuarios;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("ClientesId", clientesId);
        inputs.put("RegionesId", regionesId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("EquiposId", equiposId);
        
        lstResult=spProyectosUsuarios.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                proyectosUsuarios = new ProyectosUsuarios();
                proyectosUsuarios.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                proyectosUsuarios.setClave(String.valueOf(m.get("Clave")));
                proyectosUsuarios.setDescripcion(String.valueOf(m.get("Descripcion")));
                proyectosUsuarios.setNombre(String.valueOf(m.get("Nombre")));
                
                listado.add(proyectosUsuarios);
                
                //ProyectosId, Clave, Descripcion, Nombre, FechaIni, FechaFin, TiendasId, Tienda
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpProyectosUsuarios() {
        return spProyectosUsuarios;
    }

    public void setSpProyectosUsuarios(DatabaseStoredProc spProyectosUsuarios) {
        this.spProyectosUsuarios = spProyectosUsuarios;
    }
    
    
    
}
