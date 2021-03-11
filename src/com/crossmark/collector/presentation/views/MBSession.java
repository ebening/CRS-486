package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.services.ServiceParametros;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class MBSession implements Serializable {

    private ServiceParametros serviceParametros;
    
    public void init(){
        System.out.println("ServiceParametros");
    }
    
    public List<Parametros> getListaParametros(){
        return serviceParametros.getParametrosReporting(null,null);
    }

    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }
    
}
