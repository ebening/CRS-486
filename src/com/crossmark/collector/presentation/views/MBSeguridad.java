/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views;

import com.crossmark.collector.presentation.views.application.UsuarioController;
import javax.annotation.PostConstruct;

/**
 *
 * @author christian
 */
public class MBSeguridad {
    private UsuarioController usuarioController;

      @PostConstruct
    public void initMBAplicacion() {
        usuarioController = new UsuarioController();
    }
    
    
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }
    
    
    
    
    
    
}
