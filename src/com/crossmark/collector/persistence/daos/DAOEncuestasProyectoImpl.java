/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEncuestasProyectoImpl  implements DAOEncuestasProyecto{
    private DatabaseStoredProc spEncuestasProyecto;
    
    @Override
    public List<EncuestasProyecto> getListaEncuestasProyecto(Integer unidadesNegociosID, Integer proyectosId){
        List<Map<String, Object>> lstResult=null;
        List<EncuestasProyecto> listado = new ArrayList<>();
        EncuestasProyecto encuestasProyecto;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosID", unidadesNegociosID);
        inputs.put("ProyectosId", proyectosId);
        
        lstResult=spEncuestasProyecto.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                encuestasProyecto = new EncuestasProyecto();
                encuestasProyecto.setEncuestasId(Integer.valueOf(String.valueOf(m.get("EncuestasId") == null ? "0" : m.get("EncuestasId")) ));
                encuestasProyecto.setNombre(String.valueOf(m.get("Nombre") == null ? " " : m.get("Nombre")  ));
                
                listado.add(encuestasProyecto);
                
                //ProyectosId, Clave, Descripcion, Nombre, FechaIni, FechaFin, TiendasId, Tienda
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpEncuestasProyecto() {
        return spEncuestasProyecto;
    }

    public void setSpEncuestasProyecto(DatabaseStoredProc spEncuestasProyecto) {
        this.spEncuestasProyecto = spEncuestasProyecto;
    }
    
    
    
}
