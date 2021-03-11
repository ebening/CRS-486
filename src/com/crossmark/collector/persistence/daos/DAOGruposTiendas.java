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
public interface DAOGruposTiendas {
    List<Map<String, Object>> listaGrupos(int GruposId, String Nombre, int ClientesId, int UnidadesNegociosId);
    List<Map<String, Object>> listaTiendasByGrupoId(int GruposId);
    HashMap<String, Object> gruposUps(int GruposId,String nombre);
    HashMap<String, Object> gruposTiendasUps(int GruposId, int TiendasId);
    HashMap<String, Object> gruposTiendasDel(int GruposId, int TiendasId);
}
