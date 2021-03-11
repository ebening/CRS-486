/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.ImagenesCategoria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOReporteImagenesCategoriaImpl  implements DAOReporteImagenesCategoria{
    private DatabaseStoredProc spReporteImagenesCategoria;
    
    @Override
    public List<ImagenesCategoria> getListaCategoriasImages(){
        List<Map<String, Object>> lstResult=null;
        List<ImagenesCategoria> listado = new ArrayList<ImagenesCategoria>();
        ImagenesCategoria imagenesCategoria;
        
        Map<String, Object> inputs = new TreeMap<>();
        //inputs.put("UsuariosOID", usuariosOID);
        
        lstResult=spReporteImagenesCategoria.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                imagenesCategoria = new ImagenesCategoria();
                
                imagenesCategoria.setOpcionesOID(String.valueOf(m.get("OpcionesOID") == null ? " " : m.get("OpcionesOID")  ));
                imagenesCategoria.setListasOID(String.valueOf(m.get("ListasOID") == null ? " " : m.get("ListasOID")  ));
                imagenesCategoria.setTexto(String.valueOf(m.get("Texto") == null ? " " : m.get("Texto")  ));
                imagenesCategoria.setValor(Integer.parseInt(String.valueOf(m.get("Valor") == null ? " " : m.get("Valor")  )));
                imagenesCategoria.setClave(String.valueOf(m.get("Clave") == null ? " " : m.get("Clave")  ));
                imagenesCategoria.setOrden(Integer.parseInt(String.valueOf(m.get("Orden") == null ? " " : m.get("Orden")  )));
                imagenesCategoria.setTag(String.valueOf(m.get("Tag") == null ? " " : m.get("Tag")  ));
                imagenesCategoria.setCodigoBarra(String.valueOf(m.get("CodigoBarra") == null ? " " : m.get("CodigoBarra")  ));
                
                listado.add(imagenesCategoria);
                
            }
        }else{
            //System.out.println("out is null ");
        }
        
        return listado;
    }

    public DatabaseStoredProc getSpReporteImagenesCategoria() {
        return spReporteImagenesCategoria;
    }

    public void setSpReporteImagenesCategoria(DatabaseStoredProc spReporteImagenesCategoria) {
        this.spReporteImagenesCategoria = spReporteImagenesCategoria;
    }
    
    
    
}
