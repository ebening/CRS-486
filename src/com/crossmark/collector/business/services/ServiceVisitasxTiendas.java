/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public interface ServiceVisitasxTiendas {
    List<Map<String, Object>> getVisitasxTiendas(int tiendaId, int equiposId, int territoriosId);
}
