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
public interface ServiceFormatos {
    List<Map<String, Object>> listaFormatos(int formatosId);
    
    HashMap<String, Object> borrarFormato(int formatosId);
    
    HashMap<String, Object> updateFormato(int formatosId, String nombre);
}
