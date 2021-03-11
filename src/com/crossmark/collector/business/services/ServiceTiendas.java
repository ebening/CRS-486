/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface ServiceTiendas {
    List<Map<String, Object>> getTiendasList();
    List<Map<String, Object>> getTiendasList(HashMap<String, Object> filtros);
    List<Map<String, Object>> getTiendaById(int idTienda);
    List<Map<String, Object>> tiendasListBySearch(HashMap<String, Object> filtros);
    HashMap<String, Object> insertTienda(int TiendaId, int TerritorioId, int EquiposId);
    HashMap<String, Object> borrarTienda(int TiendaId, int TerritorioId, int EquiposId);
    int tiendasTerritoriosNumRegSel(HashMap<String, Object> filtros);
    int tiendasNumRegSel(HashMap<String, Object> filtros);
}
