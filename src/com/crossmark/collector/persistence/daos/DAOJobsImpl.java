/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOJobsImpl implements DAOJobs{
    DatabaseStoredProc spJobMovResultado;
    DatabaseStoredProc spDWEncuestaInstanciaUps;
    
    @Override
    public void execDWEncuestaInstancia() {
        Utileria.logger(getClass()).info(" Estoy en execDWEncuestaInstancia");
        
        Map<String, Object> inputs = new TreeMap<>();
        Map out = getSpDWEncuestaInstanciaUps().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            if(list != null){
                for (Object object : list) {
                    //Procesar la lista de resultados
                }
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
    }
    
    public DatabaseStoredProc getSpJobMovResultado() {
        return spJobMovResultado;
    }

    public void setSpJobMovResultado(DatabaseStoredProc spJobMovResultado) {
        this.spJobMovResultado = spJobMovResultado;
    }

    public DatabaseStoredProc getSpDWEncuestaInstanciaUps() {
        return spDWEncuestaInstanciaUps;
    }

    public void setSpDWEncuestaInstanciaUps(DatabaseStoredProc spDWEncuestaInstanciaUps) {
        this.spDWEncuestaInstanciaUps = spDWEncuestaInstanciaUps;
    }
}
