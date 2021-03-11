/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface ServicePromotores {
    List<Map<String, Object>> getPromotoresByTerritorioId(Map<String, Object> inputs);
    int deletePromotorTerritorio(String UsuariosOID, int TerritoriosId);
    List<Map<String, Object>> listaProyectosByPromotor(String UsuariosOID, int TerritoriosId, 
            int UnidadesNegociosId, int EquiposId, String nombreTienda, String nombreProyecto);
    List<EncuestasProyecto> getListaEncuestasProyecto(Integer unidadesNegociosID, Integer proyectosId);
}
