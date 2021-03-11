/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author Francisco Mora
 */
public class ReportAuthenticator extends Authenticator {
    
    private String usuario;
    private String password;
    
    //public ReportAuthenticator() {}
    
    public ReportAuthenticator(String usuario, String password) {
        setUsuario(usuario);
        setPassword(password);
    }
    
    @Override
    protected PasswordAuthentication getPasswordAuthentication()
    {
        //LoadProperties lp = LoadProperties.loadProperties();
        return new PasswordAuthentication(usuario,(password.toCharArray()));
        
        
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
