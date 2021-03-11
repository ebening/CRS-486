/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.business.domain.TipoLista;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author RIGG
 */
public class DAOOpcionesImpl implements DAOOpciones {

    private DatabaseStoredProc spTraeOpciones;
    private DAOLista daoLista;


    @Override
    public List<Opciones> traerOpciones(String unCatalogoOID, String opcionesOID) {
        List<Opciones> listado = new ArrayList<Opciones>();
        Opciones opciones;

        Map<String, Object> inputs = new TreeMap<>();

        if(unCatalogoOID.equals("")){
            inputs.put("ListasOID", null);
        }else{
            inputs.put("ListasOID", unCatalogoOID);
        }
        
        if(opcionesOID.equals("")){
            inputs.put("OpcionesOID", null);
        }else{
            inputs.put("OpcionesOID", opcionesOID);
        }
        Map out;
        try {
            out = spTraeOpciones.execute(inputs);
            if (out != null) {
                int tamanio = out.size();

                if (tamanio == 0 || tamanio == -1) {

                } else {

                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    while (it.hasNext()) {
                        key = (String) it.next();
                        val = out.get(key);

                        if (val.equals("")) {

                        } else {
                            List arrValues = (List) val;
                            int tamanioLista = arrValues.size();
                            if (tamanioLista == 0) {

                            } else {
                                if (arrValues != null) {

                                    for (int i = 0; i < arrValues.size(); i++) {

                                        Map record = (Map) arrValues.get(i);
                                        if (record != null) {

                                            opciones = new Opciones();
                                            
                                            try{
                                                opciones.setOpcionesOID((String)record.get("OpcionesOID"));
                                            }catch(NullPointerException npe){
                                                opciones.setOpcionesOID("");
                                            }
                                            
                                            try{
                                                opciones.setListasOID((String)record.get("ListasOID"));
                                            }catch(NullPointerException npe){
                                                opciones.setListasOID("");
                                            }
                                            
                                            try{
                                                opciones.setTextoOpcion((String)record.get("Texto"));
                                            }catch(NullPointerException npe){
                                                opciones.setTextoOpcion("");
                                            }
                                            
                                            try{
                                                opciones.setValorOpcion((String)record.get("Valor"));
                                            }catch(NullPointerException npe){
                                                opciones.setValorOpcion("");
                                            }
                                            
                                            try{
                                                opciones.setClaveOpcion((String)record.get("Clave"));
                                            }catch(NullPointerException npe){
                                                opciones.setClaveOpcion("");
                                            }
                                            
                                            try{
                                                
                                                short valor = (short)record.get("Orden");
                                                opciones.setOrden((int)valor);
                                            }catch(NullPointerException npe){
                                                opciones.setOrden(0);
                                            }
                                            
                                            try{
                                                opciones.setTag((String)record.get("Tag"));
                                            }catch(NullPointerException npe){
                                                opciones.setTag("");
                                            }
                                            
                                            try{
                                                opciones.setCodigoBarras((String)record.get("CodigoBarra"));
                                            }catch(NullPointerException npe){
                                                opciones.setCodigoBarras("");
                                            }
                                            
                                            try{
                                                opciones.setVisible((boolean)record.get("Visible"));
                                            }catch(NullPointerException npe){
                                                opciones.setVisible(false);
                                            }
                                            
                                            try{
                                                opciones.setActiva((boolean)record.get("Activa"));
                                            }catch(NullPointerException npe){
                                                opciones.setActiva(false);
                                            }
                                            
                                            try{
                                                opciones.setEnabled((boolean)record.get("Enabled"));
                                            }catch(NullPointerException npe){
                                                opciones.setEnabled(false);
                                            }

                                            listado.add(opciones);
                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            } else {
                //System.out.println("out is null ");
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        // TODO Auto-generated method stub
        return listado;
    }

    /**
     * @return the spTraeOpciones
     */
    public DatabaseStoredProc getSpTraeOpciones() {
        return spTraeOpciones;
    }

    /**
     * @param spTraeOpciones the spTraeOpciones to set
     */
    public void setSpTraeOpciones(DatabaseStoredProc spTraeOpciones) {
        this.spTraeOpciones = spTraeOpciones;
    }

    public DAOLista getDaoLista() {
        return daoLista;
    }

    public void setDaoLista(DAOLista daoLista) {
        this.daoLista = daoLista;
    }
}
