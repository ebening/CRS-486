/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.Cadenas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOCadenasImpl  implements DAOCadenas, Serializable {
    private DatabaseStoredProc spCadenas;
    
    @Override
    public List<Cadenas> getListaCadenas(int cadenasId, String nombre){
        List<Map<String, Object>> lstResult=null;
        List<Cadenas> listado = new ArrayList<Cadenas>();
        Cadenas cadena;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CadenasId", cadenasId);
        inputs.put("Nombre", nombre);
        
        lstResult=spCadenas.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                cadena = new Cadenas();
                cadena.setCadenasId(Integer.valueOf(String.valueOf(m.get("CadenasId"))));
                cadena.setNombre(String.valueOf(m.get("Nombre")));
                listado.add(cadena);
                
                //TerritoriosId, Nombre, Activo, EquiposId, Equipo, Pertenece
            }
            //spCadena.execSP(inputs)
        }else{
            System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpCadenas() {
        return spCadenas;
    }

    public void setSpCadenas(DatabaseStoredProc spCadenas) {
        this.spCadenas = spCadenas;
    }
    
    
}
