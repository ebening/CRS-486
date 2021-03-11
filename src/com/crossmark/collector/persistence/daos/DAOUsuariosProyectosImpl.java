/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.UsuariosProyectos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOUsuariosProyectosImpl  implements DAOUsuariosProyectos{
    private DatabaseStoredProc spUsuariosProyectos;
    
    @Override
    public List<UsuariosProyectos> getListaProyectosPorUsuario(String usuariosOID,Integer territoriosId,Integer unidadesNegociosId, Integer equiposId, 
            String nombreTienda, String nombreProyecto) {
        List<Map<String, Object>> lstResult=null;
        List<UsuariosProyectos> listado = new ArrayList<UsuariosProyectos>();
        UsuariosProyectos usuariosProyectos;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("NombreTienda", nombreTienda);
        inputs.put("NombreProyecto", nombreProyecto);
        
        lstResult=spUsuariosProyectos.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                usuariosProyectos = new UsuariosProyectos();
                usuariosProyectos.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                usuariosProyectos.setClave(String.valueOf(m.get("Clave")));
                usuariosProyectos.setDescripcion(String.valueOf(m.get("Descripcion")));
                usuariosProyectos.setNombre(String.valueOf(m.get("Nombre")));
                usuariosProyectos.setFechaIni((Date) m.get("FechaIni"));
                usuariosProyectos.setFechaFin((Date) m.get("FechaFin"));
                usuariosProyectos.setTiendasId(Integer.valueOf(String.valueOf(m.get("TiendasId"))));
                usuariosProyectos.setTienda(String.valueOf(m.get("Tienda")));
                
                listado.add(usuariosProyectos);
                
                //ProyectosId, Clave, Descripcion, Nombre, FechaIni, FechaFin, TiendasId, Tienda
            }
            
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpUsuariosProyectos() {
        return spUsuariosProyectos;
    }

    public void setSpUsuariosProyectos(DatabaseStoredProc spUsuariosProyectos) {
        this.spUsuariosProyectos = spUsuariosProyectos;
    }
    
    
    
}
