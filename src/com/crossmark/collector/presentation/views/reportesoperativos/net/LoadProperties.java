/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import com.crossmark.collector.business.domain.Parametros;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Mora
 */
public class LoadProperties {
    
    static LoadProperties rsProperties;
    
    private static String rpDebug;
    private static String rpReportServerUrl;
    private static String rpReportServerHost;
    private static String rpReportServerPort;
    private static String rpReportServerAdmin;
    private static String rpReportPath;
    private static String rpReportImageServletUrl;
    private static String rpBufferSize;
    private static String rpUsername;
    private static String rpPassword;
    private static String rpURLProject;
    private static String rpURLImageProdReporting;
    
    //rp.report.server.host=http://192.168.10.195
    //rp.report.server.port=80
    //rp.report.server.admin=/ReportServer
    
    private LoadProperties() { }
    
    
    public static LoadProperties loadProperties(List<Parametros> parametros){
            
            
            if(rsProperties == null){
                //System.out.println("parametros:"+parametros.isEmpty());
                if(parametros != null && !parametros.isEmpty() ) {
                    
                    for(Parametros item : parametros){
                        if(item.getNombre().equals("URLReporting")){
                            System.out.println("URLReporting:"+item.getValor());
                            setRpReportServerUrl(item.getValor());
                        }
                        if(item.getNombre().equals("PathREporting")){
                             System.out.println("PathREporting:"+item.getValor());
                            setRpReportPath(item.getValor());
                        }
                        if(item.getNombre().equals("UserReporting")){
                            System.out.println("UserReporting:"+item.getValor());
                            setRpUsername(item.getValor());
                        }
                        if(item.getNombre().equals("PassReporting")){
                            setRpPassword(item.getValor());
                        }
                        if(item.getNombre().equals("DebugReporting")){
                            setRpDebug(item.getValor());
                        }
                        if(item.getNombre().equals("HostReporting")){
                            setRpReportServerHost(item.getValor());
                        }
                        if(item.getNombre().equals("PortReporting")){
                            setRpReportServerPort(item.getValor());
                        }
                        if(item.getNombre().equals("PathAdminReporting")){
                            setRpReportServerAdmin(item.getValor());
                        }
                        if(item.getNombre().equals("URLProject")){
                            setRpURLProject(item.getValor());
                        }
                        if(item.getNombre().equals("ImageServeletProjectReporting")){
                            setRpReportImageServletUrl(item.getValor());
                        }
                        if(item.getNombre().equals("URLImageProdReporting")){
                            setRpURLImageProdReporting(item.getValor());
                        }
                        if(item.getNombre().equals("BufferSizeReporting")){
                            setRpBufferSize(item.getValor());
                        }
                    }
                }else{
                    Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, "Lista vacia");
                }
                /*
                try {
                    InputStream inputStream = null;
                    Properties propiedades = new Properties();
                    
                    //LoadProperties.class
                    /////String filename = "/home/oem/NetBeansProjects/jsfprueba/src/java/propiedades/reportingservices.properties";
                    String filename = "com/crossmark/collector/business/spring/reportingservices.properties";
                    
                    System.out.println(filename);
                    inputStream = LoadProperties.class.getClassLoader().getResourceAsStream(filename);
                    
                    propiedades.load(inputStream);
                    
                    setRpDebug(propiedades.getProperty("rp.debug"));
                    setRpReportServerUrl(propiedades.getProperty("rp.report.server.url"));
                    
                    setRpReportServerHost(propiedades.getProperty("rp.report.server.host"));
                    setRpReportServerPort(propiedades.getProperty("rp.report.server.port"));
                    setRpReportServerAdmin(propiedades.getProperty("rp.report.server.admin"));
                    
                    setRpReportPath(propiedades.getProperty("rp.report.path"));
                    setRpReportImageServletUrl(propiedades.getProperty("rp.image.servelet.url"));
                    setRpBufferSize(propiedades.getProperty("rp.buffer.size"));
                    setRpUsername(propiedades.getProperty("rp.username"));
                    setRpPassword(propiedades.getProperty("rp.password"));
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
            }
            
        return rsProperties;
    }
    
    
    public static LoadProperties getRsProperties() {
        return rsProperties;
    }

    public static void setRsProperties(LoadProperties rsProperties) {
        LoadProperties.rsProperties = rsProperties;
    }

    public static String getRpDebug() {
        return rpDebug;
    }

    public static void setRpDebug(String rpDebug) {
        LoadProperties.rpDebug = rpDebug;
    }

    public static String getRpReportServerUrl() {
        return rpReportServerUrl;
    }

    public static void setRpReportServerUrl(String rpReportServerUrl) {
        LoadProperties.rpReportServerUrl = rpReportServerUrl;
    }

    public static String getRpReportPath() {
        return rpReportPath;
    }

    public static void setRpReportPath(String rpReportPath) {
        LoadProperties.rpReportPath = rpReportPath;
    }

    public static String getRpReportImageServletUrl() {
        return rpReportImageServletUrl;
    }

    public static void setRpReportImageServletUrl(String rpReportImageServletUrl) {
        LoadProperties.rpReportImageServletUrl = rpReportImageServletUrl;
    }

    public static String getRpBufferSize() {
        return rpBufferSize;
    }

    public static void setRpBufferSize(String rpBufferSize) {
        LoadProperties.rpBufferSize = rpBufferSize;
    }

    public static String getRpUsername() {
        return rpUsername;
    }

    public static void setRpUsername(String rpUsername) {
        LoadProperties.rpUsername = rpUsername;
    }

    public static String getRpPassword() {
        return rpPassword;
    }

    public static void setRpPassword(String rpPassword) {
        LoadProperties.rpPassword = rpPassword;
    }

    public static String getRpReportServerHost() {
        return rpReportServerHost;
    }

    public static void setRpReportServerHost(String rpReportServerHost) {
        LoadProperties.rpReportServerHost = rpReportServerHost;
    }

    public static String getRpReportServerPort() {
        return rpReportServerPort;
    }

    public static void setRpReportServerPort(String rpReportServerPort) {
        LoadProperties.rpReportServerPort = rpReportServerPort;
    }

    public static String getRpReportServerAdmin() {
        return rpReportServerAdmin;
    }

    public static void setRpReportServerAdmin(String rpReportServerAdmin) {
        LoadProperties.rpReportServerAdmin = rpReportServerAdmin;
    }

    public static String getRpURLProject() {
        return rpURLProject;
    }

    public static void setRpURLProject(String rpURLProject) {
        LoadProperties.rpURLProject = rpURLProject;
    }

    public static String getRpURLImageProdReporting() {
        return rpURLImageProdReporting;
    }

    public static void setRpURLImageProdReporting(String rpURLImageProdReporting) {
        LoadProperties.rpURLImageProdReporting = rpURLImageProdReporting;
    }
    
    
    
}
