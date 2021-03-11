/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.UsuariosTiendas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOUsuariosTiendasImpl  implements DAOUsuariosTiendas{
    private DatabaseStoredProc spUsuariosTiendas;
    
    @Override
    public List<UsuariosTiendas> getListaTiendasUsuarios(String usuariosOID,Integer territoriosId,Integer unidadesNegociosId, Integer equiposId, 
            Integer clientesId, Integer regionesId, Integer cadenasId) {
        List<Map<String, Object>> lstResult=null;
        List<UsuariosTiendas> listado = new ArrayList<UsuariosTiendas>();
        UsuariosTiendas usuariosTiendas;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("ClientesId", clientesId);
        inputs.put("RegionesId", regionesId);
        inputs.put("CadenasId", cadenasId);
        
        lstResult=spUsuariosTiendas.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                usuariosTiendas = new UsuariosTiendas();
                
                usuariosTiendas.setTiendasId(Integer.valueOf(String.valueOf(m.get("TiendasId") == null ? "0" : m.get("TiendasId")) ));
                usuariosTiendas.setTienda(String.valueOf(m.get("Tienda") == null ? " " : m.get("Tienda")  ));
                
                listado.add(usuariosTiendas);
                
            }
            
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpUsuariosTiendas() {
        return spUsuariosTiendas;
    }

    public void setSpUsuariosTiendas(DatabaseStoredProc spUsuariosTiendas) {
        this.spUsuariosTiendas = spUsuariosTiendas;
    }
    
    
}
