/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import com.crossmark.collector.business.domain.Parametros;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Mora
 */
public class AccessReportingService {
    
    private LoadProperties loadProperties;
    private boolean debug;
    private String reportServerUrl;
    private String reportPath;
    private String reportItemServletUrl;
    private int BufferSize;
    private String reportUser;
    private String reportPassword;
    
    public AccessReportingService(List<Parametros> parametros){
        loadProperties = LoadProperties.loadProperties(parametros);
        
        setDebug(Boolean.parseBoolean(LoadProperties.getRpDebug()));
        setReportServerUrl(LoadProperties.getRpReportServerUrl());
        setReportPath(LoadProperties.getRpReportPath());
        setReportItemServletUrl(LoadProperties.getRpReportImageServletUrl());
        //setBUFFER_SIZE(Integer.parseInt(LoadProperties.getRpBufferSize()));
        setReportUser(LoadProperties.getRpUsername());
        setReportPassword(LoadProperties.getRpPassword());
        
        System.out.println(
                "    getREPORT_SERVER_URL:"+getReportServerUrl()+
                "    getREPORT_PATH:"+getReportPath()+
                "    getREPORT_ITEM_SERVLET_URL:"+getReportItemServletUrl()+
                "    getREPORT_USER:"+getReportUser()+
                "    getREPORT_PASSWORD:"+getReportPassword());
    }
    
    public String sendGetReportingService(LinkedHashMap<String, String> parametros){
        String dir_file = "";
        try {
            
            Authenticator.setDefault(new ReportAuthenticator(getReportUser(), getReportPassword()));
            System.out.println("loadProperties:"+reportServerUrl + reportPath);
            
            String parameterString = "&rs:Command=Render&rc:Toolbar=false";
            
            String reportName = parametros.get("reportName");
            String format = parametros.get("rs:Format");
            
            System.out.println("reportName:"+reportName);
            ArrayList availableParams = GetAvailableParameters.getParameters(reportName, "reporte");
            
            Iterator it = parametros.entrySet().iterator();
            String currentParam;
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                String key = (String) me.getKey();
                String value = (String) me.getValue();
                
                if(availableParams.contains(me.getKey()) ){
                    // If parameter is available, add it to the string.
                    parameterString += "&" + key + "=" + value;
                }
            }
            
            if (reportName == null){
                System.out.println("No se encontro el nombre del reporte");
                return "";
            }
            
            String urlString = reportServerUrl + reportPath + reportName+parameterString;
            //http://192.168.10.195/ReportServer?%2fReporteVisitas&rs:Command=Render&rc:Toolbar=false&rs:Format=EXCEL&ClientesId=1&EquiposId=1&TerritoriosId=1&RegionesId=1&FechaIni=2014-10-02&FechaFin=2014-10-24
            System.out.println("ReportRequest - Reporte recuperado: " + urlString);
            
            URL url = new URL(urlString);
            HttpURLConnection repCon = (HttpURLConnection)url.openConnection();
            
            repCon.setRequestMethod("GET");//POST
            repCon.setDoOutput(true);
            repCon.setUseCaches(false);
            repCon.setFollowRedirects(false);
            
            int responseCode = repCon.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            BufferedInputStream bis = new BufferedInputStream(repCon.getInputStream());
            //BufferedOutputStream bos2 = new BufferedOutputStream(clientOutStream);//Comentado por jpakoery
            dir_file = System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+reportName+getFechaActualYMDH()+getTypeFile(format);
            
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dir_file),BufferSize);
            
            System.out.println("File dir:"+dir_file);
            BufferedReader in = new BufferedReader(new InputStreamReader(repCon.getInputStream()));
            //String inputLine;
            //StringBuffer response = new StringBuffer();
            
            byte[] buff = new byte[BufferSize];
            int bytesRead;
            
            while(-1 != (bytesRead = bis.read(buff, 0, BufferSize)))
            {
                bos.write(buff, 0, bytesRead);
            }
            bos.close();
            bis.close();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(AccessReportingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccessReportingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dir_file;
    }
    
    
    public InputStream sendGetReportingServiceInputStream(LinkedHashMap<String, String> parametros){
        InputStream  is = null;
        try {
            
            Authenticator.setDefault(new ReportAuthenticator(getReportUser(), getReportPassword()));
            System.out.println("loadProperties:"+reportServerUrl + reportPath);
            
            String parameterString = "&rs:Command=Render&rc:Toolbar=false";
            
            String reportName = parametros.get("reportName");
            String format = parametros.get("rs:Format");
            
            System.out.println("reportName:"+reportName);
            ArrayList availableParams = GetAvailableParameters.getParameters(reportName, "reporte");
            
            Iterator it = parametros.entrySet().iterator();
            String currentParam;
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                String key = (String) me.getKey();
                String value = (String) me.getValue();
                
                if(availableParams.contains(key) || (availableParams.contains(key+":isnull=true") && (value != null && !value.equals("0") && !value.equals("null") && !value.equals("") )) ){
                    // If parameter is available, add it to the string.
                    parameterString += "&" + key + "=" + value;
                }
                if(availableParams.contains(key+":isnull=true") && (value == null || value.equals("0") || value.equals("null") || value.equals("") ) ){
                    // If parameter is available, add it to the string.
                    parameterString += "&" + key+":isnull=true";
                }
            }
            
            if (reportName == null){
                System.out.println("No se encontro el nombre del reporte");
                return is;
            }
            
            String urlString = reportServerUrl + reportPath + reportName+parameterString;
            //http://192.168.10.195/ReportServer?%2fReporteVisitas&rs:Command=Render&rc:Toolbar=false&rs:Format=EXCEL&ClientesId=1&EquiposId=1&TerritoriosId=1&RegionesId=1&FechaIni=2014-10-02&FechaFin=2014-10-24
            System.out.println("ReportRequest - Reporte recuperado: " + urlString);
            
            URL url = new URL(urlString);
            HttpURLConnection repCon = (HttpURLConnection)url.openConnection();
            
            repCon.setRequestMethod("GET");//POST
            repCon.setDoOutput(true);
            repCon.setUseCaches(false);
            repCon.setFollowRedirects(false);
            
            int responseCode = repCon.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            if(responseCode <= 400){
                is = repCon.getInputStream();
            }else{
                is = null;
            }
            //is = new BufferedInputStream(
            return repCon.getInputStream();
            //);
            //is.close();
        } catch (MalformedURLException ex) {
            is = null;
            Logger.getLogger(AccessReportingService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            is = null;
            Logger.getLogger(AccessReportingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return is;
    }
    
    public String getTypeFile(String format){
        String type = "";
        
        if(format.toUpperCase().equals("HTML4.0")){
            type = ".html";
        }
        if(format.toUpperCase().equals("MHTML")){
            type = ".mhtml";
        }
        if(format.toUpperCase().equals("IMAGE")){
            type = ".tif";
        }
        if(format.toUpperCase().equals("EXCEL")){
            type = ".xls";
        }
        if(format.toUpperCase().equals("WORD")){
            type = ".doc";
        }
        if(format.toUpperCase().equals("CSV")){
            type = ".csv";
        }
        if(format.toUpperCase().equals("PDF")){
            type = ".pdf";
        }
        if(format.toUpperCase().equals("XML")){
            type = ".xml";
        }
        if(format.toUpperCase().equals("NULL")){
            type = ".null";
        }
        return type;
    }
    
    public static String getFechaActualYMDH(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechasalida = sdf.format(new Date());
        fechasalida = fechasalida.replace("-", "").replace(" ", "").replace(":", "");
        return fechasalida;
    }
    
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getReportServerUrl() {
        return reportServerUrl;
    }

    public void setReportServerUrl(String reportServerUrl) {
        this.reportServerUrl = reportServerUrl;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public String getReportItemServletUrl() {
        return reportItemServletUrl;
    }

    public void setReportItemServletUrl(String reportItemServletUrl) {
        this.reportItemServletUrl = reportItemServletUrl;
    }

    public int getBufferSize() {
        return BufferSize;
    }

    public void setBufferSize(int BufferSize) {
        this.BufferSize = BufferSize;
    }

    public LoadProperties getLoadProperties() {
        return loadProperties;
    }

    public void setLoadProperties(LoadProperties loadProperties) {
        this.loadProperties = loadProperties;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }
    
    
    
}
