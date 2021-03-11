/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Condiciones;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author franciscom
 */
public interface DAOCondiciones {
    
    public List<Condiciones> getListCondiciones(String seccionesOID, String preguntasOID, String opcionesOID, Boolean seleccionadas);
    
    public void crearCondicion(Condiciones o);
    
    String eliminarCondicion(Condiciones o);
    
    public Condiciones genericObjectCondiciones(Object args);
}
