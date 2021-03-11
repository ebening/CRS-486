/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente.objects;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francisco Mora
 */
public class UsuariosTiendas implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private String tienda;
    private Integer tiendasId;

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public Integer getTiendasId() {
        return tiendasId;
    }

    public void setTiendasId(Integer tiendasId) {
        this.tiendasId = tiendasId;
    }
    
    
}
