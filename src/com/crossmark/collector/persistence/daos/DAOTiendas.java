/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Tienda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface DAOTiendas extends AbstractDAO<Tienda> {
    List<Map<String, Object>> getTiendasList();
    List<Map<String, Object>> getTiendasList(HashMap<String, Object> filtros);
    List<Map<String, Object>> getTiendaById(int idTienda);
    List<Map<String, Object>> tiendasListBySearch(HashMap<String, Object> filtros);
    List<Tienda> getAll(int pageIndex, int pageSize, Map<String, Object> filters);
    List<Tienda> getAllActivated(int pageIndex, int pageSize);
    int tiendasTerritoriosNumReg(HashMap<String, Object> filtros);
    int tiendasNumReg(HashMap<String, Object> filtros);
   // int insertTienda(int TiendaId, int TerritorioId);
}
