/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Categorias;
import com.crossmark.collector.business.domain.SubCategorias;
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
public class DAOCategoriasImpl implements DAOCategorias{
    
    private DatabaseStoredProc spListaCategorias;
    private DatabaseStoredProc spListaSubCategorias;
    

    @Override
    public List<Categorias> listaCategorias() {
        
        List<Categorias> listaCategorias = new ArrayList<Categorias>();

        Categorias categorias;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("CategoriasId", null);
        Map out;

        try {

            out = spListaCategorias.execute(inputs);

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

                                            categorias = new Categorias();
                                            
                                            Short numero = (short)record.get("CategoriasId");
                                            categorias.setIdCategoria(new Integer(numero));
                                            categorias.setNombreCategoria((String)record.get("Nombre"));
                                            listaCategorias.add(categorias);

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

        return listaCategorias;
        
        
        
    }

    /**
     * @return the spListaCategorias
     */
    public DatabaseStoredProc getSpListaCategorias() {
        return spListaCategorias;
    }

    /**
     * @param spListaCategorias the spListaCategorias to set
     */
    public void setSpListaCategorias(DatabaseStoredProc spListaCategorias) {
        this.spListaCategorias = spListaCategorias;
    }

    @Override
    public List<SubCategorias> listaSubCategorias(Integer idCategoria, Integer idSubCategoria) {
        
        List<SubCategorias> listaSubCategorias = new ArrayList<SubCategorias>();

        SubCategorias subCategorias;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("SubCategoriasId", idSubCategoria);
        inputs.put("CategoriasId", idCategoria);
        Map out;
        
        try {
            
            out = spListaSubCategorias.execute(inputs);

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

                                            subCategorias = new SubCategorias();
                                            
                                            subCategorias.setIdSubCategoria(new Integer((short)record.get("SubCategoriasId")));
                                            subCategorias.setIdCategoria(new Integer((short)record.get("CategoriasId")));
                                            subCategorias.setNombreSubCategoria((String)record.get("Nombre"));
                                            listaSubCategorias.add(subCategorias);
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

        return listaSubCategorias;
        
        
        
        
        
    }

    /**
     * @return the spListaSubCategorias
     */
    public DatabaseStoredProc getSpListaSubCategorias() {
        return spListaSubCategorias;
    }

    /**
     * @param spListaSubCategorias the spListaSubCategorias to set
     */
    public void setSpListaSubCategorias(DatabaseStoredProc spListaSubCategorias) {
        this.spListaSubCategorias = spListaSubCategorias;
    }
    
}
