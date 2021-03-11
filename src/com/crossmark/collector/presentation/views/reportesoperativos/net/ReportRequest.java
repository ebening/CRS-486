/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.services.ServiceReportesOperativos;
import com.crossmark.collector.presentation.views.MBSession;
import com.crossmark.collector.presentation.views.reportescliente.MBRepCliCumplDetalleProyecto;
import com.crossmark.collector.presentation.views.reportescliente.MBRepCliCumplProyecto;
import com.crossmark.collector.presentation.views.reportescliente.MBRepCliImagenes;
import com.crossmark.collector.presentation.views.reportescliente.MBRepCliResultados;
import com.crossmark.collector.presentation.views.reportesoperativos.MBReportesOperativos;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Francisco Mora
 */
@WebServlet(name = "ReportingService", urlPatterns = {"/ReportingService"})
public class ReportRequest extends HttpServlet {
    private ServiceReportesOperativos serviceReportesOperativos;
    private LoadProperties loadProperties;
    private boolean debug;
    private String reportServerUrl;
    private String reportPath;
    private String reportImageServletUrl;
    private int bufferSize;
    private String reportUser;
    private String reportPassword;
    List<Parametros> listaParametros;
    
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        //String fullPath = config.getServletContext().getRealPath("");// + File.separator + log4jConfigFile;
        //System.out.println("fullPath S:"+fullPath);
        //WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        //MBReportesOperativos mBReportesOperativos = ctx.getBean(MBReportesOperativos.class);
        
        //listaParametros = serviceReportesOperativos.getParametrosReporting(null,null);
        
        
            super.init(config);
        
    }
    
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession httpSession = httpRequest.getSession();
            
            //MBReportesOperativos bBReportesOperativos = (MBReportesOperativos) request.getSession().getAttribute("MBReportesOperativos");
            MBReportesOperativos bBReportesOperativos = (MBReportesOperativos) httpSession.getAttribute("MBReportesOperativos");
            
            listaParametros = bBReportesOperativos.getListaParametros();
            
            loadProperties = LoadProperties.loadProperties(listaParametros);
            setDebug(Boolean.parseBoolean(LoadProperties.getRpDebug()));
            setReportServerUrl(LoadProperties.getRpReportServerUrl());
            setReportPath(LoadProperties.getRpReportPath());
            setReportImageServletUrl(LoadProperties.getRpReportImageServletUrl());
            setBufferSize(Integer.parseInt(LoadProperties.getRpBufferSize()));
            setReportUser(LoadProperties.getRpUsername());
            setReportPassword(LoadProperties.getRpPassword());
            
            Authenticator.setDefault(new ReportAuthenticator(getReportUser(), getReportPassword()));
            String parameterString = "&rs:Command=Render&rc:Toolbar=false";
            
            Enumeration paramEnum = request.getParameterNames();
            String reportName = request.getParameter("reportName");
            ArrayList availableParams = GetAvailableParameters.getParameters(reportName, "image");
            
            
            String currentParam;
            while(paramEnum.hasMoreElements()){
                currentParam = (String)paramEnum.nextElement();
                if(availableParams.contains(currentParam)){
                    // If parameter is available, add it to the string.
                    parameterString += "&" + currentParam + "=" + request.getParameter(currentParam);
                }
            }
            
            if (reportName == null){
                // Bail out if no report name is found.
                ServletOutputStream responseErrorStream = response.getOutputStream();
                responseErrorStream.println("ERROR: No se especifico el nbombre del reporte");
                responseErrorStream.close();
                return;
            }
            
            // Establish HTTP POST connection to report server
            String urlString = reportServerUrl + reportPath + reportName+parameterString;
            String user_agent = request.getHeader("User-Agent");
            
            if(user_agent.length() > 0){
                user_agent = user_agent.split(" ")[0];
            }else{
                user_agent = "Mozilla/5.0";
            }
            
            //http://192.168.10.195/ReportServer?%2fDashboard&rs:SessionID=qe00tt55qg3qkqfb2u1hm555&rs:Format=HTML4.0&rs:ImageID=C_17iT1_1
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
            
            PrintWriter repOutStream = new PrintWriter(repCon.getOutputStream());

            repOutStream.println(parameterString);
            repOutStream.close();
            
        }catch (Exception e){
            e.printStackTrace();
            
            // Alert the client there has been an error.
            ServletOutputStream responseErrorStream = response.getOutputStream();
            responseErrorStream.println("Se ha generado un error. Revise el log para mas informacion.");
            responseErrorStream.close();
        }
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
         HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpSession httpSession = httpRequest.getSession();
            
            Enumeration<String> en = httpSession.getAttributeNames();
            Utileria.logger(getClass()).info("Prueba de acceso");
            
            while(en.hasMoreElements()){
                String elemnt = en.nextElement();
                Utileria.logger(getClass()).info("nextElement:"+elemnt);
                if(elemnt.equals("MBSession")){
                    Utileria.logger(getClass()).info("MBSession");
                    MBSession mbS = (MBSession) httpSession.getAttribute("MBSession");
                    this.listaParametros = mbS.getListaParametros();
                }
            }
            
            if(this.listaParametros.isEmpty()){
                Utileria.logger(getClass()).info("No puede accesar a los parametros de configuracion");
                return;
            }
            
            loadProperties = LoadProperties.loadProperties(listaParametros);
        setDebug(Boolean.parseBoolean(LoadProperties.getRpDebug()));
        setReportServerUrl(LoadProperties.getRpReportServerUrl());
        setReportPath(LoadProperties.getRpReportPath());
        setReportImageServletUrl(LoadProperties.getRpReportImageServletUrl());
        setBufferSize(Integer.parseInt(LoadProperties.getRpBufferSize()));
        setReportUser(LoadProperties.getRpUsername());
        setReportPassword(LoadProperties.getRpPassword());
        // Redirect HTTP GET requests to doPost.
        //doPost(request, response);
        
        try{
            
            Authenticator.setDefault(new ReportAuthenticator(getReportUser(), getReportPassword()));
            String parameterString = "";
            
            Enumeration paramEnum = request.getParameterNames();
            String reportName = request.getParameter("reportName");
            ArrayList availableParams = GetAvailableParameters.getParameters(reportName, "image");
            
            
            String currentParam;
            while(paramEnum.hasMoreElements()){
                currentParam = (String)paramEnum.nextElement();
                if(availableParams.contains(currentParam)){
                    // If parameter is available, add it to the string.
                    parameterString += "&" + currentParam + "=" + request.getParameter(currentParam);
                }
            }
            if (reportName == null){
                // Bail out if no report name is found.
                ServletOutputStream responseErrorStream = response.getOutputStream();
                responseErrorStream.println("ERROR: No se especifico el nbombre del reporte");
                responseErrorStream.close();
                return;
            }
            
            // Establish HTTP POST connection to report server
            String urlString = reportServerUrl + reportPath + reportName+parameterString;
            //String urlString = reportServerUrl + "Reserved.ReportViewerWebControl.axd?"+ parameterString;
            String user_agent = request.getHeader("User-Agent");
            
            if(user_agent.length() > 0){
                user_agent = user_agent.split(" ")[0];
            }else{
                user_agent = "Mozilla/5.0";
            }
            
            //http://192.168.10.195/ReportServer?%2fReporteVisitas&rs:Command=Render&rc:Toolbar=false&rs:Format=EXCEL&ClientesId=1&EquiposId=1&TerritoriosId=1&RegionesId=1&FechaIni=2014-10-02&FechaFin=2014-10-24
            System.out.println("ReportRequest - Reporte recuperado: " + urlString);
            
            URL url = new URL(urlString);
            HttpURLConnection repCon = (HttpURLConnection)url.openConnection();
            
            repCon.setRequestMethod("GET");//POST
            repCon.setDoOutput(true);
            repCon.setUseCaches(false);
            repCon.setFollowRedirects(false);
            //repCon.setRequestProperty("User-Agent", user_agent);
            //repCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded" );
            //repCon.setRequestProperty("Content-length", Integer.toString(parameterString.length()));
            int responseCode = repCon.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            forwardResponse(repCon, response);
        }catch (Exception e){
            e.printStackTrace();
            
            // Alert the client there has been an error.
            ServletOutputStream responseErrorStream = response.getOutputStream();
            responseErrorStream.println("Se ha generado un error. Revise el log para mas informacion.");
            responseErrorStream.close();
        }
        
    }
    
    //http://192.168.10.195:80/ReportServer?%2fDashboard&rs:Command=Render&rc:Toolbar=false&rs:Format=HTML4.0&rs:SessionID=2h3iugranutlbnm3eug44o55&rs:ImageID=C_5iT0_1
    
    private void forwardResponse(HttpURLConnection reportCon, HttpServletResponse clientResponse) throws ServletException, IOException{
        String contentType = reportCon.getContentType();
        clientResponse.setContentType(contentType);
        clientResponse.setHeader("Content-disposition", reportCon.getHeaderField("Content-disposition"));
        
        InputStream repInStream = reportCon.getInputStream();
        ServletOutputStream clientOutStream = clientResponse.getOutputStream();
        
        BufferedInputStream bis = new BufferedInputStream(repInStream);
        BufferedOutputStream bos = new BufferedOutputStream(clientOutStream);
        
        byte[] buff = new byte[bufferSize];
        int bytesRead;
        
        while(-1 != (bytesRead = bis.read(buff, 0, bufferSize)))
        {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
        
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    
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

    public String getReportImageServletUrl() {
        return reportImageServletUrl;
    }

    public void setReportImageServletUrl(String reportImageServletUrl) {
        this.reportImageServletUrl = reportImageServletUrl;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
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

    public ServiceReportesOperativos getServiceReportesOperativos() {
        return serviceReportesOperativos;
    }

    public void setServiceReportesOperativos(ServiceReportesOperativos serviceReportesOperativos) {
        this.serviceReportesOperativos = serviceReportesOperativos;
    }

    public List<Parametros> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }
    
    
}
