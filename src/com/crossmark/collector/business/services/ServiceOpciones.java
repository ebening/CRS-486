/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Opciones;
import java.util.List;

/**
 *
 * @author RIGG
 */
public interface ServiceOpciones {
    
    List<Opciones> traerOpciones(String unCatalogoOID, String opcionesOID);
    
}
