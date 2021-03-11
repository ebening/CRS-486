/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Territorio;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface DAOTerritorios extends  AbstractDAO<Territorio> {

    public List<Map<String, Object>> listaTerritoriosNat(int TerritoriosId, String Nombre, boolean Activo);

    String nombreTerritorio(int TerritorioId);
}
