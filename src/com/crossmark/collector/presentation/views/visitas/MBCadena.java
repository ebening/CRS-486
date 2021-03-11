/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceCadena;
import com.crossmark.collector.presentation.views.visitas.objects.Cadena;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class MBCadena {
    private ServiceCadena serviceCadena;
    private List<Cadena> cadenas;
    
    public void cadenasList(){
        List<Map<String, Object>> l = serviceCadena.getCadenas();
        cadenas = new ArrayList<>();
        
        for(Map m : l){
           cadenas.add(new Cadena(String.valueOf(m.get("Nombre")),Integer.valueOf(String.valueOf(m.get("CadenasId"))))); 
        }
    }

    /**
     * @return the serviceCadena
     */
    public ServiceCadena getServiceCadena() {
        return serviceCadena;
    }

    /**
     * @param serviceCadena the serviceCadena to set
     */
    public void setServiceCadena(ServiceCadena serviceCadena) {
        this.serviceCadena = serviceCadena;
    }

    /**
     * @return the cadenas
     */
    public List<Cadena> getCadenas() {
        return cadenas;
    }

    /**
     * @param cadenas the cadenas to set
     */
    public void setCadenas(List<Cadena> cadenas) {
        this.cadenas = cadenas;
    }
}
