/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author jdominguez
 */
public class DAOVisitasImpl implements DAOVisitas, Serializable{

    private DatabaseStoredProc spVisitas;
    private DatabaseStoredProc spVisitasDel;
    private DatabaseStoredProc spVisitasUps;
    private DatabaseStoredProc spVisitasVal;
    private DatabaseStoredProc spVisitasTiendasSel;
    private DatabaseStoredProc spVisitasTiendasUps;
    private DatabaseStoredProc spVisitasTiendasDel;
    
    private DatabaseStoredProc spRutasSel;
    private DatabaseStoredProc spRutasUsuariosSel;
    
    
    private final Map<String, Object> inputs;

    public DAOVisitasImpl() {
        inputs=new TreeMap<>();
        inputs.put("RutasId", null);
        inputs.put("RutasNombre", null);
        inputs.put("VisitasId", null);
        inputs.put("VisitasNombre", null);
        inputs.put("TiendaId", null);
        inputs.put("TiendasNombre", null);
        inputs.put("EncuestasId", null);
        inputs.put("EncuestaNombre", null);
        inputs.put("FechaIni", null);
        inputs.put("FechaFin", null);
        inputs.put("UserName", null);
        inputs.put("NombresUsuarios", null);
        inputs.put("ApellidosUsuarios", null);
        inputs.put("ProyectosNombre", null);
        inputs.put("ProyectosId", null);
        inputs.put("TerritoriosId", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("UsuariosOID", null);
        inputs.put("TareasId", null);
    }

    @Override
    public HashMap<String, Object> guardaTiendasVisitas(int VisitasId, int TiendasId, int ProyectosId, int EncuestasId) {
        Map<String, Object> in = new TreeMap();
        HashMap<String, Object> r = new HashMap<>();
        List<Map<String, Object>> response;
        in.put("VisitasId", VisitasId);
        in.put("TiendasId", TiendasId);
        in.put("ProyectosId", ProyectosId);
        in.put("EncuestasId", EncuestasId == 0 ? 0  : EncuestasId);
        
        try{
            response  = getSpVisitasTiendasUps().executeSP(in);
            r.put("Exito", true);
            r.put("Msj", "Informacion Guardada");
        }catch(Exception ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }
    
    @Override
    public HashMap<String, Object> visitasVal(int VisitasId) {
        Map<String, Object> in = new TreeMap();
        HashMap<String, Object> r = new HashMap<>();
        in.put("VisitasId", VisitasId);
        List<Map<String, Object>> response = getSpVisitasVal().execSP(in);
        try{
           int val = (int)response.get(0).get(""); 
           switch(val){
                case 0: r.put("Exito", true);
                        r.put("Editar", true);
                        r.put("ValMsj", val);
                        break;
                case 1: r.put("Exito", true);
                        r.put("Editar", false);
                        r.put("ValMsj", "No se puede editar la visita debido a que ya está vigente.");
                        break;
                case 2: r.put("Exito", true);
                        r.put("Editar", false);
                        r.put("ValMsj", "No se puede editar la visita debido a que ya tiene encuestas contestadas.");
                        break;
                default :
                        r.put("Exito", true);
                        r.put("Editar", false);
                        r.put("ValMsj", "Error en la informacion recibida");
                        break;
            }
        }catch(ClassCastException ex){
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }
    
    @Override
    public HashMap<String, Object> guardaVisita(int VisitasId, Date FechaIni, int RutasId, String Nombre, String UsuariosOID) {
        Map<String, Object> in = new TreeMap();
        HashMap<String, Object> r = new HashMap<>();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        in.put("VisitasId", VisitasId);
        in.put("FechaIni", fecha.format(FechaIni));
        in.put("RutasId", RutasId);
        in.put("Nombre", Nombre);
        in.put("UsuariosOID", UsuariosOID);
        
        List<Map<Integer, Object>> out;
        try{
            out =  getSpVisitasUps().execSPI(in);
            BigDecimal bd = (BigDecimal) (out == null ? new BigDecimal(VisitasId) : out.get(0).get("InsertedID"));
            int idVisita = bd.intValue();
            r.put("Exito", true);
            r.put("VisitaId", idVisita);
            r.put("Msj", "Visita Guardada");
        }catch(Exception se){
            r.put("Exito", false);
            r.put("Msj", se.getMessage());
	}
        return r;
    }
    
    @Override
    public List<Map<String, Object>> visitasByTerritorios(int TerritorioId,int EquiposId, int UnidadesNegociosId) {
        inputs.put("TerritoriosId", TerritorioId);
        inputs.put("UnidadesNegociosId", UnidadesNegociosId);
        inputs.put("EquiposId", EquiposId);
        return getSpVisitas().execSP(inputs);
    }
    
    @Override
    public List<Map<String, Object>> getVisitasByIdTienda(int idTienda) {
        inputs.put("TiendaId", idTienda);
        return getSpVisitas().execSP(inputs);
    }

    @Override
    public List<Map<String, Object>> visitasTiendas(int RutaId, int VisitasId) {
        Map<String, Object> in = new TreeMap();
        List<Map<String, Object>> response;
        in.put("VisitasId", VisitasId);
        in.put("RutasId", RutaId);
        response = getSpVisitasTiendasSel().execSP(in);
        return response;
    }
    
    @Override
    public List<Map<String, Object>> listaRutasSel(int TerritoriosId, int EquiposId) {
        Map<String, Object> in = new TreeMap();
        List<Map<String, Object>> response = null;
        in.put("RutasId", null);
        in.put("Nombre", null);
        in.put("TerritoriosId", TerritoriosId);
        in.put("EquiposId", EquiposId);
        try{
            response = getSpRutasSel().execSP(in);
            return response;
        }catch(DataAccessResourceFailureException ex){
            Utileria.mensajeErroneo(ex.getMessage());
        }
        return response;
    }

    @Override
    public List<Map<String, Object>> listaRutasUsuariosSel(int RutasId, int TerritoriosId, int EquiposId, int UnidadesNegociosId) {
        Map<String, Object> in = new TreeMap();
        List<Map<String, Object>> response = null;
        in.put("RutasId", RutasId);
        in.put("TerritoriosId", TerritoriosId);
        in.put("EquiposId", EquiposId);
        in.put("UnidadesNegociosId", UnidadesNegociosId);
        in.put("Pertenece", 1);
        
        try{
            response = getSpRutasUsuariosSel().execSP(in);
            return response;
        }catch(DataAccessResourceFailureException ex){
            Utileria.mensajeErroneo(ex.getMessage());
        }
        return response;
    }
    
    @Override
    public HashMap<String, Object> eliminaVisita(int TareasId) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> in = new TreeMap();
        List<Map<String, Object>> response;
        //in.put("VisitasId", VisitaId);
        in.put("TareasId", TareasId);
        try{
            //response = getSpVisitasDel().executeSP(in);
            response = getSpVisitasTiendasDel().executeSP(in);
            r.put("Exito", true);
            r.put("Msj", "Visita Eliminada");
        }catch (Exception ex) {
            Logger.getLogger(DAOVisitasImpl.class.getName()).log(Level.SEVERE, null, ex);
            r.put("Exito", false);
            r.put("Msj", ex.getMessage());
        }
        return r;
    }
    
    /**
     * @return the spVisitas
     */
    public DatabaseStoredProc getSpVisitas() {
        return spVisitas;
    }

    /**
     * @param spVisitas the spVisitas to set
     */
    public void setSpVisitas(DatabaseStoredProc spVisitas) {
        this.spVisitas = spVisitas;
    }

    public DatabaseStoredProc getSpVisitasTiendasSel() {
        return spVisitasTiendasSel;
    }

    public void setSpVisitasTiendasSel(DatabaseStoredProc spVisitasTiendasSel) {
        this.spVisitasTiendasSel = spVisitasTiendasSel;
    }

    public DatabaseStoredProc getSpRutasSel() {
        return spRutasSel;
    }

    public void setSpRutasSel(DatabaseStoredProc spRutasSel) {
        this.spRutasSel = spRutasSel;
    }

    public DatabaseStoredProc getSpRutasUsuariosSel() {
        return spRutasUsuariosSel;
    }

    public void setSpRutasUsuariosSel(DatabaseStoredProc spRutasUsuariosSel) {
        this.spRutasUsuariosSel = spRutasUsuariosSel;
    }

    public DatabaseStoredProc getSpVisitasDel() {
        return spVisitasDel;
    }

    public void setSpVisitasDel(DatabaseStoredProc spVisitasDel) {
        this.spVisitasDel = spVisitasDel;
    }

    public DatabaseStoredProc getSpVisitasUps() {
        return spVisitasUps;
    }

    public void setSpVisitasUps(DatabaseStoredProc spVisitasUps) {
        this.spVisitasUps = spVisitasUps;
    }

    public DatabaseStoredProc getSpVisitasVal() {
        return spVisitasVal;
    }

    public void setSpVisitasVal(DatabaseStoredProc spVisitasVal) {
        this.spVisitasVal = spVisitasVal;
    }

    public DatabaseStoredProc getSpVisitasTiendasUps() {
        return spVisitasTiendasUps;
    }

    public void setSpVisitasTiendasUps(DatabaseStoredProc spVisitasTiendasUps) {
        this.spVisitasTiendasUps = spVisitasTiendasUps;
    }

    public DatabaseStoredProc getSpVisitasTiendasDel() {
        return spVisitasTiendasDel;
    }

    public void setSpVisitasTiendasDel(DatabaseStoredProc spVisitasTiendasDel) {
        this.spVisitasTiendasDel = spVisitasTiendasDel;
    }

    
}
