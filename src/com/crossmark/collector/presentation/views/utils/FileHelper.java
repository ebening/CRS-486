/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Mora
 */
public class FileHelper {
    /*
    public static void move(String fromFileName, String toFileName){
        try {
            copy(fromFileName, toFileName);
            delete(fromFileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        //throws IOException, Exception
    }
    */
    public static boolean copy(String fromFileName, String toFileName) throws IOException {
        File fromFile = new File(fromFileName);
    	File toFile = new File(toFileName);
        boolean copiado = false;
    	if (!fromFile.exists()) throw new IOException("FileCopy: " + "No se encuentra fichero a copiar: " + fromFileName);
        
    	if (!fromFile.isFile()) throw new IOException("FileCopy: " + "No se puede copiar directorio: " + fromFileName);
        
    	if (!fromFile.canRead()) throw new IOException("FileCopy: " + "No se puede leer fichero a copiar: " + fromFileName);
        
    	if (toFile.isDirectory()) toFile = new File(toFile, fromFile.getName());
        
    	if (toFile.exists()) {
            //throw new IOException("FileCopy: " + "El archivo ya existe y no puede ser sobreescrito");
            toFile.delete();
            
        }
        
    	FileInputStream from = null;
    	FileOutputStream to = null;
    	try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1){
                to.write(buffer, 0, bytesRead); // write
            }
            copiado = true;
    	} finally {
            if (from != null)
                try {
                    from.close();
    		} catch (IOException e) {

                }
            if (to != null)
    		try {
                    to.close();
    		} catch (IOException e) {

                }
            }
        return copiado;
    }
    
    
    /*
    public static boolean delete(String fileName) throws Exception {
        // A File object to represent the filename
        File f = new File(fileName);
        
        // Make sure the file or directory exists and isn't write protected
        if (!f.exists()) {
            throw new Exception("Delete: No se encuentra el fichero o directorio: " + fileName);
        }
        
        if (!f.canWrite()){
            throw new Exception("Delete: Existe proteccion contra escritura: "  + fileName);
        }
        
        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
          String[] files = f.list();
          if (files.length > 0) {
                throw new Exception("Delete: El directorio no se encuentra vacio: " + fileName);
            }
        }
        
        // Attempt to delete it
        boolean success = f.delete();
        
        if (!success) { throw new Exception("Delete: Falla en la eliminacion del fichero"); }
        
        return success;
    }
    
    */
    
    public static boolean delete(String fileName) throws Exception{
        // A File object to represent the filename
        File f = new File(fileName);
        boolean delete = true;
        boolean success = false;
        
        // Make sure the file or directory exists and isn't write protected
        if (!f.exists()) {
            delete = false;
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE,null, "Delete: No se encuentra el fichero o directorio: " + fileName);
            throw new Exception("Delete: No se encuentra el fichero o directorio: " + fileName);
        }
        
        if (!f.canWrite()){
            delete = false;
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE,null, "Delete: Existe proteccion contra escritura: "  + fileName);
            throw new Exception("Delete: Existe proteccion contra escritura: "  + fileName);
        }
        
        // If it is a directory, make sure it is empty
        if (f.isDirectory()) {
          String[] files = f.list();
          if (files.length > 0) {
              delete = false;
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE,null, "Delete: El directorio no se encuentra vacio: " + fileName);
                throw new Exception("Delete: El directorio no se encuentra vacio: " + fileName);
            }
        }
        
        // Attempt to delete it
        if(delete){
            success = f.delete();
            if (!success) {
                delete = false;
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE,null, "Delete: El directorio no se encuentra vacio: " + fileName);
                throw new Exception("Delete: Falla en la eliminacion del fichero"); 
            }
        }
        
        return success;
    }
    
    public static boolean saveByteFile(byte[] file, String dir,String name) throws Exception{
        boolean success = false;
        
        String nombreArch = dir+name;
	File arch = new File(nombreArch);
        if (!arch.canWrite()){
            throw new Exception("saveByteFile: Existe proteccion contra escritura: "  + nombreArch);
        }
        try {
            OutputStream aSalida = new FileOutputStream(arch);
            aSalida.write(file);
            aSalida.flush();
            aSalida.close();
            success = true;
        } catch (IOException ex) {
            success = false;
            throw new Exception("save: Fallo al guardar el archivo.");
        }
	return success;
    }
    
    public static boolean saveInputStreamFile(InputStream in, String dir,String name){
        boolean success = false;
        try {
            
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(dir + name));
            
            int read = 0;
            byte[] bytes = new byte[5024];
            
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            success = true;
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
	return success;
    }
    
    public static boolean existFile(String fileName){
        
        // A File object to represent the filename
        File f = new File(fileName);
        System.out.println("existFile:"+f.exists());
        return f.exists();
        
    }
    
    public static boolean isFile(String fileName){
        
        // A File object to represent the filename
        File f = new File(fileName);
        System.out.println("existFile:"+f.exists());
        if(f.exists()){
            return f.isFile();
        }else{
            return false;
        }
    }
    
    public static boolean isDir(String fileName){
        
        // A File object to represent the filename
        File f = new File(fileName);
        System.out.println("existFile:"+f.exists());
        if(f.exists()){
            return f.isDirectory();
        }else{
            return false;
        }
    }
    
    public static boolean createDir(String dirName){        // A File object to represent the filename
        File f = new File(dirName);
        if(f.exists()){
            if(f.isDirectory()){
                return true;
            }else{
                System.out.println("existee, pero es un archivo.");
                return false;
            }
        }else{
            System.out.println("Directorio creado");
            return f.mkdir();
        }
    }
    
    public static long getSizeFile(String dirName){// A File object to represent the filename
        File f = new File(dirName);
        if(f.exists()){
            return f.length();
        }else{
            return 0;
        }
    }
    
    public static InputStream getFileInputStream(String fileName){
        InputStream is = null;
        
        try {
            if(existFile(fileName)){
                is = new FileInputStream(fileName);
            }else{
                System.out.println( "No existe:"+fileName);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE,null, "Delete: El directorio no se encuentra vacio: " + fileName+"   Exception:"+ ex);
        }
        return is;
    }
}
