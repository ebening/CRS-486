package com.crossmark.collector.presentation.views.utils;

/**
 * Created by christian on 04/02/2015.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Francisco Mora
 */
public class Codificacion {

    private String cadenaNormal;
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void proceso(String cadena, boolean isDecode) {

        if (isDecode) {
            resultado = asciiToString(cadena);
        } else {
            resultado = convertirStringToAscii(cadena);
        }
    }

    public Codificacion(){

    }

    public static void main(String[] args) {
        Codificacion cs = new Codificacion();
        cs.proceso("hola",false);
        System.out.println(cs.getResultado());
        cs.proceso(cs.getResultado(),true);
        System.out.println(cs.getResultado());
    }



    private String convertirStringToAscii(String cadena) {
        String ascii_string = "";
        for (char c : cadena.toCharArray()) {

            if (ascii_string.equals("")) {
                ascii_string += (int) c;
            } else {
                ascii_string += "," + (int) c;
            }
        }
        return ascii_string;
    }

    //convierte una cadena a ascci a string
    private String asciiToString(String ascii_string) {
        String cadena = "";
        List<String> valor = Arrays.asList(ascii_string.split(","));
        for (String elemento : valor) {
            char c = (char) Integer.parseInt(elemento);
            cadena += c;
        }
        return cadena;
    }

    public String cadenaParametros(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        List<UrlParameter> urlParameterList = Utileria.obtenerParamtrosDeURL(url);
        int cout = urlParameterList.size();
        for (UrlParameter parameter : urlParameterList) {
            stringBuilder.append(parameter.getKey()).append(",").append(parameter.getValue());
            cout--;
            if (cout > 0) {
                stringBuilder.append("@");
            }
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();

    }
    
    public Map<String, String> obtenerParametros2(String url) {
        return Utileria.paseoVallenato3(url);
    }

    public Map<String, String> obtenerParametros(String url) {
        return Utileria.paseoVallenato2(url);
    }


    public  String parsearUrl(String url) {

        if (url.contains("?query=")) {
            return url.substring(0, url.lastIndexOf("?query="));
        } else {
            return url;
        }
    }

}