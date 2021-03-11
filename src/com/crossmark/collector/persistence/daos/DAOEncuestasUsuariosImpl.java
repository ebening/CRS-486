/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.EncuestasUsuarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEncuestasUsuariosImpl  implements DAOEncuestasUsuarios{
    private DatabaseStoredProc spEncuestasUsuarios;
    
    @Override
    public List<EncuestasUsuarios> getListaEncuestasUsuarios(String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, 
            Integer equiposId, Integer proyectosId) {
        List<Map<String, Object>> lstResult=null;
        List<EncuestasUsuarios> listado = new ArrayList<EncuestasUsuarios>();
        EncuestasUsuarios encuestasUsuarios;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("ClientesId", clientesId);
        inputs.put("RegionesId", regionesId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("ProyectosId", proyectosId);
        
        lstResult=spEncuestasUsuarios.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                encuestasUsuarios = new EncuestasUsuarios();
                
                encuestasUsuarios.setEncuestasId(Integer.valueOf(String.valueOf(m.get("EncuestasId") == null ? "0" : m.get("EncuestasId")) ));
                encuestasUsuarios.setEncuestaClave(String.valueOf(m.get("EncuestaClave") == null ? " " : m.get("EncuestaClave")  ));
                encuestasUsuarios.setEncuestaNombre(String.valueOf(m.get("EncuestaNombre") == null ? " " : m.get("EncuestaNombre")  ) );
                
                listado.add(encuestasUsuarios);
                
                //ProyectosId, Clave, Descripcion, Nombre, FechaIni, FechaFin, TiendasId, Tienda
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpEncuestasUsuarios() {
        return spEncuestasUsuarios;
    }

    public void setSpEncuestasUsuarios(DatabaseStoredProc spEncuestasUsuarios) {
        this.spEncuestasUsuarios = spEncuestasUsuarios;
    }
    
    
}
