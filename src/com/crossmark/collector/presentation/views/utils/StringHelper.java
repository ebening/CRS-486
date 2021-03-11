/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 *
 * @author Francisco Mora
 */
public class StringHelper {
    
    public static String readInputStreamAsString(InputStream in) throws IOException {
        
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
            byte b = (byte)result;
            buf.write(b);
            result = bis.read();
        }        
        return buf.toString();
        
    }
    
    
    public static byte[] getBytesFromInputStream(InputStream is){
        ByteArrayOutputStream os = null;
        try{
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];
            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);
            os.flush();
            
            return os.toByteArray();
        }catch (IOException e){
            return null;
        }
    }
    
    public static String getTypeFile(String format){
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
    
    public static String getContentType(String format){
        String contentType = "";
        
        if(format.toUpperCase().equals("HTML4.0")){
            contentType = "text/HTML";
        }
        if(format.toUpperCase().equals("MHTML")){
            contentType = "message/rfc822";
        }
        if(format.toUpperCase().equals("IMAGE")){
            contentType = "image/tiff";
        }
        if(format.toUpperCase().equals("EXCEL")){
            contentType = "application/vnd.ms-excel";
        }
        if(format.toUpperCase().equals("WORD")){
            contentType = "application/msword";
        }
        if(format.toUpperCase().equals("CSV")){
            contentType = "text/csv";
        }
        if(format.toUpperCase().equals("PDF")){
            contentType = "application/pdf";
        }
        if(format.toUpperCase().equals("XML")){
            contentType = "application/xml";
        }
        if(format.toUpperCase().equals("NULL")){
            contentType = "text/plain";
        }
        return contentType;
    }
    
    public static String getContentTypeByFileName(String fileName){
        String contentType = "";
        System.out.println("fileName:"+fileName);
        if(fileName != null && !fileName.equals("") && fileName.contains(".")){
            String contentTypeTmp = fileName.split("\\.")[fileName.split("\\.").length-1];
            if(contentTypeTmp.toLowerCase().equals("html") || contentTypeTmp.toLowerCase().equals("htm") ){
                contentType = "text/HTML";
            }
            if(contentTypeTmp.toLowerCase().equals("jpeg")){
                contentType = "image/jpeg";
            }
            if(contentTypeTmp.toLowerCase().equals("jpg")){
                contentType = "image/jpeg";
            }
            if(contentTypeTmp.toLowerCase().equals("png")){
                contentType = "image/png";
            }
            if(contentTypeTmp.toLowerCase().equals("gif")){
                contentType = "image/gif";
            }
            if(contentTypeTmp.toLowerCase().equals("xls") || contentTypeTmp.toLowerCase().equals("xlsx")){
                contentType = "application/vnd.ms-excel";
            }
            if(contentTypeTmp.toLowerCase().equals("doc") || contentTypeTmp.toLowerCase().equals("docx")){
                contentType = "application/msword";
            }
            if(contentTypeTmp.toLowerCase().equals("ppt") || contentTypeTmp.toLowerCase().equals("pptx")){
                contentType = "application/vnd.ms-powerpoint";
            }
            if(contentTypeTmp.toLowerCase().equals("pdf")){
                contentType = "application/pdf";
            }
        }else{
            contentType = "";
        }
        return contentType;
        
    }
}
