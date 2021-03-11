/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOGruposProyectosTiendasImpl implements DAOGruposProyectosTiendas, Serializable{
    private DatabaseStoredProc spGruposProyectosSel;
    private DatabaseStoredProc spGruposProyectosDel;
    private DatabaseStoredProc spGruposProyectosUps;
    
    private DatabaseStoredProc spGruposProyectosTiendasSel;
    private DatabaseStoredProc spGruposProyectosTiendasDel;
    private DatabaseStoredProc spGruposProyectosTiendasUps;

    @Override
    public List<Map<String, Object>> listaGruposProyectos(int GruposProyectosId, String nombre, int ClientesId) {
        List<Map<String, Object>> response;
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        inputs.put("Nombre", nombre);
        inputs.put("ClientesId", ClientesId);
        response = getSpGruposProyectosSel().execSP(inputs);
        return response;
    }

    @Override
    public HashMap<String, Object> borrarGruposProyectos(int GruposProyectosId) {
        HashMap<String, Object> r = new HashMap<>();
        List<Map<String, Object>> response;
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        try{
            response = getSpGruposProyectosDel().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msj", "Grupo Eliminado");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    @Override
    public HashMap<String, Object> updateGruposProyectos(int GruposProyectosId, String nombre, int ClientesId) {
        HashMap<String, Object> r = new HashMap<>();
        List<Map<String, Object>> response;
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        inputs.put("Nombre", nombre);
        inputs.put("ClientesId", ClientesId);
        try{
            response = getSpGruposProyectosUps().executeSP(inputs);
            for(Map m : response){
                BigDecimal bd = (BigDecimal) m.get("InsertedID");
                int newId = GruposProyectosId != 0 ? GruposProyectosId : bd.intValue();
                r.put("ID",newId);
            }
            r.put("Exito", true);
            r.put("Msj", "Grupo Guardado");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    @Override
    public List<Map<String, Object>> listaGruposProyectosTiendas(int GruposProyectosId) {
        List<Map<String, Object>> response;
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        response = getSpGruposProyectosTiendasSel().execSP(inputs);
        return response;
    }

    @Override
    public HashMap<String, Object> borrarGruposProyectosTiendas(int GruposProyectosId, int TiendasId) {
        List<Map<String, Object>> response;
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        inputs.put("TiendasId", TiendasId);
        try{
            response = getSpGruposProyectosTiendasDel().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msh", "Tienda borrada del Grupo");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    @Override
    public HashMap<String, Object> updateGruposProyectosTiendas(int GruposProyectosId, int TiendasId) {
        List<Map<String, Object>> response;
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("GruposProyectosId", GruposProyectosId);
        inputs.put("TiendasId", TiendasId);
        try{
            response = getSpGruposProyectosTiendasUps().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msh", "Tienda agregada al Grupo");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    
// ********************************** Getters & Setters ************************************************** //
    
    public DatabaseStoredProc getSpGruposProyectosSel() {
        return spGruposProyectosSel;
    }

    public void setSpGruposProyectosSel(DatabaseStoredProc spGruposProyectosSel) {
        this.spGruposProyectosSel = spGruposProyectosSel;
    }

    public DatabaseStoredProc getSpGruposProyectosDel() {
        return spGruposProyectosDel;
    }

    public void setSpGruposProyectosDel(DatabaseStoredProc spGruposProyectosDel) {
        this.spGruposProyectosDel = spGruposProyectosDel;
    }

    public DatabaseStoredProc getSpGruposProyectosUps() {
        return spGruposProyectosUps;
    }

    public void setSpGruposProyectosUps(DatabaseStoredProc spGruposProyectosUps) {
        this.spGruposProyectosUps = spGruposProyectosUps;
    }

    public DatabaseStoredProc getSpGruposProyectosTiendasSel() {
        return spGruposProyectosTiendasSel;
    }

    public void setSpGruposProyectosTiendasSel(DatabaseStoredProc spGruposProyectosTiendasSel) {
        this.spGruposProyectosTiendasSel = spGruposProyectosTiendasSel;
    }

    public DatabaseStoredProc getSpGruposProyectosTiendasDel() {
        return spGruposProyectosTiendasDel;
    }

    public void setSpGruposProyectosTiendasDel(DatabaseStoredProc spGruposProyectosTiendasDel) {
        this.spGruposProyectosTiendasDel = spGruposProyectosTiendasDel;
    }

    public DatabaseStoredProc getSpGruposProyectosTiendasUps() {
        return spGruposProyectosTiendasUps;
    }

    public void setSpGruposProyectosTiendasUps(DatabaseStoredProc spGruposProyectosTiendasUps) {
        this.spGruposProyectosTiendasUps = spGruposProyectosTiendasUps;
    }
    
    
}
