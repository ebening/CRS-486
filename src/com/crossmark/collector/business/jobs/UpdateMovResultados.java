/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.business.jobs;

import com.crossmark.collector.business.services.ServiceJobs;
import com.crossmark.collector.business.services.ServiceLogin;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Francisco Mora
 */
public class UpdateMovResultados {
    
    @ManagedProperty(value="#{serviceJobs}")
    private ServiceJobs serviceJobs;
    
    public void execDWEncuestaInstancia(){
        serviceJobs.execDWEncuestaInstancia();
    }
    
    public ServiceJobs getServiceJobs() {
        return serviceJobs;
    }

    public void setServiceJobs(ServiceJobs serviceJobs) {
        this.serviceJobs = serviceJobs;
    }
    
    
    
    
}
