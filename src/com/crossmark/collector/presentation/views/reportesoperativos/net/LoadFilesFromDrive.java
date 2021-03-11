/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import com.crossmark.collector.presentation.views.utils.FileHelper;
import com.crossmark.collector.presentation.views.utils.StringHelper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francisco Mora
 */
@WebServlet(name = "LoadFilesFromDrive", urlPatterns = {"/LoadFilesFromDrive"})
public class LoadFilesFromDrive extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response (param1= Ruta completa del archivo en unidad de disco, param2= Nombre del archivo)
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dir = "";
        String fileName = "";
        if(request.getParameterNames() != null ){
            
            if(request.getParameter("param1") != null){
                dir = request.getParameter("param1");
            }
            if(request.getParameter("param2") != null){
                fileName = request.getParameter("param2");
            }
        }
        if(fileName != null && fileName != null){
            String contentType = StringHelper.getContentTypeByFileName(fileName);
            response.setContentType(contentType);
            response.setHeader("Content-disposition", "Content-Disposition: inline");
            
            //FileHelper.getFileInputStream("W://1EBE5549-B613-43D2-82D4-010B57E7A3F8.jpg");
            
            InputStream repInStream = FileHelper.getFileInputStream(dir+fileName);
            ServletOutputStream clientOutStream = response.getOutputStream();
            
            BufferedInputStream bis = new BufferedInputStream(repInStream);
            BufferedOutputStream bos = new BufferedOutputStream(clientOutStream);
            
            byte[] buff = new byte[2500];
            int bytesRead;
            
            while(-1 != (bytesRead = bis.read(buff, 0, 2500)))
            {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        }
        
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    
}
