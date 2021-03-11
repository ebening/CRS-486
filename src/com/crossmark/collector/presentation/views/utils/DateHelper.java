/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Mora
 */
public class DateHelper {
    
    public static Long nowNumber(){
        String unix_epoch_time = String.valueOf( System.currentTimeMillis());
        return Long.parseLong(unix_epoch_time);
    }
    
    public static Date getNow(){
        return new Date();
    }
    
    public static Date convertirStringToDate(String fecha){
        Date fechasalida = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechasalida = sdf.parse(fecha);
            sdf.format(fechasalida);
        } catch (ParseException ex) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechasalida;
    }
    
    public static Date convertirStringToDateDMYHMS(String fecha){
        Date fechasalida = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fechasalida = sdf.parse(fecha);
            sdf.format(fechasalida);
        } catch (ParseException ex) {
            Logger.getLogger(DateHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechasalida;
    }
    
    public static String convertirDateToString(Date fecha){
        if(fecha == null){
            fecha = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechasalida = sdf.format(fecha);				
        return fechasalida;		
    }
    
    public static String dateFormatDMY(Date fecha){
        
        SimpleDateFormat sdf = null;
        String fechasalida = "";
        
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechasalida = sdf.format(fecha);
        
        return fechasalida;
    }
    
}
