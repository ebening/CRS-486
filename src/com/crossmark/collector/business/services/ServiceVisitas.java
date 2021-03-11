/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface ServiceVisitas {
    List<Map<String, Object>> getVisitasByIdTienda(int TiendaId);
    
    List<Map<String, Object>> visitasByTerritorio(int TerritorioId, int EquiposId, int UnidadesNegociosId);
    
    List<Map<String, Object>> visitasTiendas(int RutasId, int VisitasId);
    
    List<Map<String, Object>> listaRutasSel(int TerritoriosId, int EquiposId);
    
    List<Map<String, Object>> listaRutasUsuariosSel(int RutasId, int TerritoriosId, int EquiposId, int UnidadesNegociosId);
    
    HashMap<String, Object> eliminaVisita(int TareasId);
    
    HashMap<String, Object> guardaVisita(int VisitasId, Date FechaIni, int RutasId, String Nombre, String UsuariosOID);
    
    HashMap<String, Object> visitasVal(int VisitasId);
    
    HashMap<String, Object> guardaTiendasVisitas(int VisitasId, int TiendasId, int ProyectosId, int EncuestasId);
}
