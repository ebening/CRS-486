/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Proyectos;
import com.itextpdf.text.DocumentException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;


/**
 *
 * @author jdominguez
 */
public class ProyectoViewer {
   
    private final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    
    public static List<Encuestas> create(){
        Encuestas enc = new Encuestas();
        enc.setActiva(true);
        enc.setEncuestasId(1);
        enc.setNombreEncuesta("Teste");
        return Arrays.asList(enc);
    }
    
    public void createPreview(Proyectos proyectoSeleccionado) throws JRException, IOException, FileNotFoundException, DocumentException {
            
        System.out.println("IMPRIMIR REPORTES YAAAAA!!!-------->>>");
        ProyectoPdf.createPdf(proyectoSeleccionado);
       // downloadPdf(fileURL, "VistaPreviaProyectos.pdf");
    }
    
    public void downloadPdf(String filePath, String fileName) throws FileNotFoundException, IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        File file = new File(filePath, fileName);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        
        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setContentType("application/pdf");
          //  response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setHeader("Accept-Ranges", "bytes");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Finalize task.
            output.flush();
        }finally {
            // Gently close streams.
            close(output);
            close(input);   
        }
        facesContext.responseComplete();
    }
    
   
    
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                
            }
        }
    }

}
