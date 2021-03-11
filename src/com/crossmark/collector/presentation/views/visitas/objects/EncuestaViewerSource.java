/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import com.crossmark.collector.business.domain.Encuestas;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author jdominguez
 */
public class EncuestaViewerSource {
    private List<Encuestas> data;
    private int index;

    public EncuestaViewerSource(List<Encuestas> data) {
        this.data = data;
        index = 0;
    }
    
     public boolean next () throws JRException{
        index++;
        return (index < data.size());
    }
    
    public Object getFieldValue(JRField field){
        Object value = null;
        String fieldName = field.getName();
        
        if(fieldName.equals("encuestaId")){
            value = data.get(index).getEncuestasId();
        }
        
        return value;
    }

// **************************** Getters & Setters **************************** //
    public List<Encuestas> getData() {
        return data;
    }

    public void setData(List<Encuestas> data) {
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
