/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOProyectosTiendasImpl implements DAOProyectosTiendas, Serializable{
    
    private DatabaseStoredProc spProyectosTiendasSel;
    private DatabaseStoredProc spProyectosTiendasDel;
    private DatabaseStoredProc spProyectosTiendasUps;

    @Override
    public List<Map<String, Object>> listaTiendasProyecto(int proyectoId, int unidadesNegociosId, int equipoId, int clienteId, String nombre, int cadenasId, int formatoId, int estadosId, int ciudadesId) {
        Map<String, Object> inputs = new TreeMap();
        List<Map<String, Object>> response;
        inputs.put("ProyectosId", proyectoId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equipoId);
        inputs.put("ClientesId", clienteId == 0 ? null : clienteId);
        inputs.put("Nombre", nombre);
        inputs.put("CadenasId", cadenasId);
        inputs.put("FormatosId", formatoId);
        inputs.put("EstadosId", estadosId);
        inputs.put("CiudadesId", ciudadesId);
        response = getSpProyectosTiendasSel().execSP(inputs);
        return response;
    }

    @Override
    public HashMap<String, Object> guardaTiendaProyecto(int proyectoId, int tiendaId) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("ProyectosId", proyectoId);
        inputs.put("TiendasId", tiendaId);
        try{
            List<Map<String, Object>> response = getSpProyectosTiendasUps().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msj", "Tienda agregada al proyecto");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }

    @Override
    public HashMap<String, Object> borraTiendaProyecto(int proyectoId, int tiendaId) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("ProyectosId", proyectoId);
        inputs.put("TiendasId", tiendaId);
        try{
            List<Map<String, Object>> response = getSpProyectosTiendasDel().executeSP(inputs);
            r.put("Exito", true);
            r.put("Msj", "Tienda elimnada del proyecto");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }
    
    public DatabaseStoredProc getSpProyectosTiendasSel() {
        return spProyectosTiendasSel;
    }

    public void setSpProyectosTiendasSel(DatabaseStoredProc spProyectosTiendasSel) {
        this.spProyectosTiendasSel = spProyectosTiendasSel;
    }

    public DatabaseStoredProc getSpProyectosTiendasDel() {
        return spProyectosTiendasDel;
    }

    public void setSpProyectosTiendasDel(DatabaseStoredProc spProyectosTiendasDel) {
        this.spProyectosTiendasDel = spProyectosTiendasDel;
    }

    public DatabaseStoredProc getSpProyectosTiendasUps() {
        return spProyectosTiendasUps;
    }

    public void setSpProyectosTiendasUps(DatabaseStoredProc spProyectosTiendasUps) {
        this.spProyectosTiendasUps = spProyectosTiendasUps;
    }

    
    
    
}
