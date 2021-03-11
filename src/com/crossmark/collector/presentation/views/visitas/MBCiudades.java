/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceCiudades;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class MBCiudades {
    ServiceCiudades serviceCiudades;
    
    String selCiudad;
    private List<Ciudad> ciudades;
    private int estadoId;
    
    public List ciudadesList(){
        return parseCiudadObject(getServiceCiudades().getCiudades());   
    }
    
    public void ciudadesByEstado(){
        if(estadoId != 0){
           ciudades = parseCiudadObject(getServiceCiudades().getCiudadesByEstado(0, estadoId, null, true)); 
           ciudades.add(new Ciudad(1, "CIUDAD PRUEBA", 1, true));
        }   
    }
    
    public void resetCiudadList(){
        ciudades = new ArrayList<>();
    }

    /**
     * @return the serviceCiudades
     */
    public ServiceCiudades getServiceCiudades() {
        return serviceCiudades;
    }

    /**
     * @param serviceCiudades the serviceCiudades to set
     */
    public void setServiceCiudades(ServiceCiudades serviceCiudades) {
        this.serviceCiudades = serviceCiudades;
    }

    /**
     * @return the selCiudad
     */
    public String getSelCiudad() {
        return selCiudad;
    }

    /**
     * @param selCiudad the selCiudad to set
     */
    public void setSelCiudad(String selCiudad) {
        this.selCiudad = selCiudad;
    }
    
    public static List<Ciudad> parseCiudadObject(List<Map<String, Object>> l){
        List<Ciudad> lc = new ArrayList<>();
        for(Map m : l){
            lc.add(new Ciudad(Integer.valueOf(String.valueOf(m.get("CiudadesId"))), 
                    String.valueOf(m.get("Nombre")), Integer.valueOf(String.valueOf(m.get("EstadosId"))), 
                    Boolean.valueOf(String.valueOf(m.get("Activo")))));
        }
        return lc;
    }

    /**
     * @return the estadoId
     */
    public int getEstadoId() {
        return estadoId;
    }

    /**
     * @param estadoId the estadoId to set
     */
    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    /**
     * @return the ciudades
     */
    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    /**
     * @param ciudades the ciudades to set
     */
    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
