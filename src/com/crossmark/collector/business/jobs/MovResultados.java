/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.business.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Francisco Mora
 */
public class MovResultados extends QuartzJobBean {
    
    private UpdateMovResultados updateMovResultados;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        updateMovResultados.execDWEncuestaInstancia();
    }
    
    public void setUpdateMovResultados(UpdateMovResultados updateMovResultados) {
        this.updateMovResultados = updateMovResultados;
    }
    
}
