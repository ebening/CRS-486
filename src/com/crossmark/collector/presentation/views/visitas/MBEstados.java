/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceEstados;
import com.crossmark.collector.presentation.views.visitas.objects.Estado;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class MBEstados {
    ServiceEstados serviceEstados;
    String selEstado;
    private List<Estado> estados;
    
    public void estadosList(){
        List<Map<String, Object>> l = serviceEstados.getEstados();
        estados = new ArrayList<>();
        if(l != null){
            for(Map m : l){
                estados.add(new Estado(Integer.valueOf(String.valueOf(m.get("EstadosId"))), String.valueOf(m.get("Nombre")),
                        String.valueOf(m.get("Clave")), Boolean.valueOf(String.valueOf(m.get("Activo")))));
            } 
        }
 
    }

    /**
     * @return the serviceEstados
     */
    public ServiceEstados getServiceEstados() {
        return serviceEstados;
    }

    /**
     * @param serviceEstados the serviceEstados to set
     */
    public void setServiceEstados(ServiceEstados serviceEstados) {
        this.serviceEstados = serviceEstados;
    }

    /**
     * @return the selEstado
     */
    public String getSelEstado() {
        return selEstado;
    }

    /**
     * @param selEstado the selEstado to set
     */
    public void setSelEstado(String selEstado) {
        this.selEstado = selEstado;
    }

    /**
     * @return the estados
     */
    public List<Estado> getEstados() {
        return estados;
    }

    /**
     * @param estados the estados to set
     */
    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }
}
