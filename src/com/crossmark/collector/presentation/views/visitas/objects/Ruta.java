/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;


import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public final class Ruta implements Serializable{
    private String nombre;
    private List<Promotor> promotores;
    private List<Tienda> tiendas;
    private int id;
    private int territoriosId;
    private int equiposId;
    private int unidadNegocioId;

    

    public Ruta(){
        
    }

    public Ruta(String nombre, int id, int territoriosId, int equiposId) {
        this.nombre = nombre;
        this.id = id;
        this.territoriosId = territoriosId;
        this.equiposId = equiposId;
    }

    public Ruta(int id, String nombre,  int unidadNegocioId) {
        this.nombre = nombre;
        this.id = id;
        this.unidadNegocioId = unidadNegocioId;
    }

    public Ruta(String nombre, String columnPromotores, String columnTiendas) {
        this.nombre = nombre;
        tiendas = new ArrayList<>();
        promotores = new ArrayList<>();
        if(!columnTiendas.equals("0")){
            initTiendaListClave(Utileria.paseoVallenato2(columnTiendas));
        }
        if(!columnPromotores.equals("0")){
            initPromosList(columnPromotores);
        }
      //  genColumnTiendas();
    }

    public Ruta(String nombre, List<Promotor> promotores, List<Tienda> tiendas, int id, int territoriosId, int equiposId) {
        this.nombre = nombre;
        this.promotores = promotores;
        this.tiendas = tiendas;
        this.id = id;
        this.territoriosId = territoriosId;
        this.equiposId = equiposId;
    }
    
    private void initTiendaListClave(Map<String, String> map){
        if(!map.isEmpty()){
            for(Map.Entry<String, String> m : map.entrySet()){
                Tienda t = new Tienda();
                t.setClave(m.getKey());
                t.setNombre(m.getValue());
                tiendas.add(t);
            }
        }
    }

    private void initTiendaList(Map<Integer, String> map){
        if(!map.isEmpty()){
            for(Map.Entry<Integer, String> m : map.entrySet()){
                Tienda t = new Tienda();
                t.setId(m.getKey());
                t.setNombre(m.getValue());
                tiendas.add(t);
            }
        }
    }
    
    private void initPromosList(String columnPromotores){
        if(columnPromotores != null){
            String [] arrobas = columnPromotores.split("@");
            for(String str : arrobas){
                if(!str.equals("")){
                    String [] arr = str.split(",");
                    String [] arrAp = arr[2].split(" ");
                    Promotor p = new Promotor(arrAp[0], "", 
                            arrAp.length < 2  ? "" : arrAp[1], 
                            arrAp.length < 3 ? "" : arrAp[2]);
                    p.setNroEmpleado(arr[1]);
                    p.setUserName(arr[0]);
                    promotores.add(p); 
                }  
            }
        }
    }
    
    public int getUnidadNegocioId() {
        return unidadNegocioId;
    }

    public void setUnidadNegocioId(int unidadNegocioId) {
        this.unidadNegocioId = unidadNegocioId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Promotor> getPromotores() {
        return promotores;
    }

    public void setPromotores(List<Promotor> promotores) {
        this.promotores = promotores;
    }

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(int territoriosId) {
        this.territoriosId = territoriosId;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
