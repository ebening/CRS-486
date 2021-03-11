/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface DAOGruposProyectosTiendas {
    List<Map<String, Object>> listaGruposProyectos(int GruposProyectosId, String nombre, int ClientesId);
    
    HashMap<String, Object> borrarGruposProyectos(int GruposProyectosId);
    
    HashMap<String, Object> updateGruposProyectos(int GruposProyectosId, String nombre, int ClientesId);
    
    List<Map<String, Object>> listaGruposProyectosTiendas(int GruposProyectosId);
    
    HashMap<String, Object> borrarGruposProyectosTiendas(int GruposProyectosId, int TiendasId);
    
    HashMap<String, Object> updateGruposProyectosTiendas(int GruposProyectosId, int TiendasId);
}
