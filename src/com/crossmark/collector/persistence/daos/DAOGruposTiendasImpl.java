/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOGruposTiendasImpl implements DAOGruposTiendas{

    private DatabaseStoredProc spGruposSel;
    private DatabaseStoredProc spGruposUps;
    
    private DatabaseStoredProc spGruposTiendasSel;
    private DatabaseStoredProc spGruposTiendasUps;
    private DatabaseStoredProc spGruposTiendasDel;
    
    @Override
    public HashMap<String, Object> gruposTiendasDel(int GruposId, int TiendasId) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        List<Map<Integer, Object>> out;
        inputs.put("GruposId", GruposId);
        inputs.put("TiendasId", TiendasId);
        
        try{
            out = getSpGruposTiendasDel().execSPI(inputs);
            r.put("Exito", true);
            r.put("Msj", "Tienda eliminada del Grupo");
        }catch(Exception se){
            r.put("Exito", false);
            r.put("Msj", se.getMessage());
	}
        return r;
    }
    
    @Override
    public HashMap<String, Object> gruposTiendasUps(int GruposId, int TiendasId) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        List<Map<Integer, Object>> out;
        inputs.put("GruposId", GruposId);
        inputs.put("TiendasId", TiendasId);
        
        try{
            out = getSpGruposTiendasUps().execSPI(inputs);
            r.put("Exito", true);
            r.put("Msj", "Tienda agregada al Grupo");
        }catch(Exception se){
            r.put("Exito", false);
            r.put("Msj", se.getMessage());
	}
        return r;
    }
    
    @Override
    public HashMap<String, Object> gruposUps(int GruposId, String nombre) {
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        List<Map<Integer, Object>> out;
        inputs.put("GruposId", GruposId);
        inputs.put("Nombre", nombre);
        
        try{
            out = getSpGruposUps().execSPI(inputs);
            BigDecimal bd = (BigDecimal) out.get(0).get("InsertedID");
            int newId = GruposId != 0 ? GruposId : bd.intValue();
            r.put("Exito", true);
            r.put("Msj", "Grupo Guardado");
            r.put("Id", newId);
        }catch(Exception se){
            r.put("Exito", false);
            r.put("Msj", se.getMessage());
	}
        return r;
    }
    
    @Override
    public List<Map<String, Object>> listaGrupos(int GruposId, String Nombre, int ClientesId, int UnidadesNegociosId) {
        List<Map<String, Object>> r;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("GruposId", GruposId == 0 ? null : GruposId);
        inputs.put("Nombre", Nombre);
        inputs.put("ClientesId", ClientesId == 0 ? null : ClientesId);
        inputs.put("UnidadesNegociosId", UnidadesNegociosId == 0 ? null : UnidadesNegociosId);
        Utileria.logger(getClass()).info("getSpGruposSel inputs:"+inputs);
        r = getSpGruposSel().execSP(inputs);
        return r;
    }
    
    @Override
    public List<Map<String, Object>> listaTiendasByGrupoId(int GruposId) {
        List<Map<String, Object>> r;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("GruposId", GruposId);
        r = getSpGruposTiendasSel().execSP(inputs);
        return r;
    }

    public DatabaseStoredProc getSpGruposSel() {
        return spGruposSel;
    }

    public void setSpGruposSel(DatabaseStoredProc spGruposSel) {
        this.spGruposSel = spGruposSel;
    }

    public DatabaseStoredProc getSpGruposTiendasSel() {
        return spGruposTiendasSel;
    }

    public void setSpGruposTiendasSel(DatabaseStoredProc spGruposTiendasSel) {
        this.spGruposTiendasSel = spGruposTiendasSel;
    } 

    public DatabaseStoredProc getSpGruposUps() {
        return spGruposUps;
    }

    public void setSpGruposUps(DatabaseStoredProc spGruposUps) {
        this.spGruposUps = spGruposUps;
    }

    public DatabaseStoredProc getSpGruposTiendasUps() {
        return spGruposTiendasUps;
    }

    public void setSpGruposTiendasUps(DatabaseStoredProc spGruposTiendasUps) {
        this.spGruposTiendasUps = spGruposTiendasUps;
    }

    public DatabaseStoredProc getSpGruposTiendasDel() {
        return spGruposTiendasDel;
    }

    public void setSpGruposTiendasDel(DatabaseStoredProc spGruposTiendasDel) {
        this.spGruposTiendasDel = spGruposTiendasDel;
    }  
}
