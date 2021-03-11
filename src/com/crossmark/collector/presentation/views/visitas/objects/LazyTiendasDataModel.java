/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas.objects;

import com.crossmark.collector.business.services.ServiceTiendas;
import com.crossmark.collector.presentation.views.visitas.MBTiendas;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author jdominguez
 */
public class LazyTiendasDataModel extends LazyDataModel<Tienda>{
    
    private List<Tienda> datasource;
    private final ServiceTiendas serviceTiendas; 
    private final HashMap<String, Object> filtros;
    private final int rowCount;
    private boolean search = false;

    public LazyTiendasDataModel(List<Tienda> datasource) {
        this.datasource = datasource;
        this.serviceTiendas = null;
        this.filtros = null;
        this.rowCount = 0;
    }

    
    
    public LazyTiendasDataModel(ServiceTiendas serviceTiendas, HashMap<String, Object> filtros, int rowCount) {
        this.serviceTiendas = serviceTiendas;
        this.filtros = filtros;
        this.rowCount = rowCount;
    }

    public LazyTiendasDataModel(ServiceTiendas serviceTiendas, HashMap<String, Object> filtros, int rowCount, boolean search) {
        this.serviceTiendas = serviceTiendas;
        this.filtros = filtros;
        this.rowCount = rowCount;
        this.search = search;
    }
    
    public void resetDatasource(){
        datasource = new ArrayList<>();
    }
    
    public void addTiendaDatasource(Tienda t){
        datasource.add(t);
    }
    
    private void initDataSource(){
        if(search){
            datasource = MBTiendas.parseTiendaObject(serviceTiendas.tiendasListBySearch(filtros));
        }else{
            datasource = MBTiendas.parseTiendaObject(serviceTiendas.getTiendasList(filtros));  
        }
    }
    
    @Override
    public int getRowCount(){
       return rowCount;
    }
    
    @Override
    public Tienda getRowData(String rowKey){
        for(Tienda t : datasource){
            if(t.getId() == Integer.valueOf(rowKey)){
                return t;
            }
        }
        return null;
    }
    
    @Override
    public List<Tienda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
        List<Tienda> data = new ArrayList<>();
        if(serviceTiendas != null){
            filtros.put("PageIndex", (first/pageSize) + 1);
            filtros.put("PageSize", pageSize);
            filtros.put("TotalCount", 0);

            System.out.println("LAZY LOAD -> " + filtros);

            initDataSource();
        }
        
        //filter
        for(Tienda t : datasource){
            boolean match = true;
            
            if(filters != null){
                for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();){
                    try{
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field field = t.getClass().getDeclaredField(filterProperty);
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(t));
                        if(filterValue == null || fieldValue.contains(filterValue.toString())){
                            match = true;
                        }else{
                            match = false;
                            break;
                        }
                    }catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
                        match = false;
                    }
                }
            }
            if(match){
                data.add(t);
            }
        }
        
        //sort
        if(sortField != null){
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
        
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
        
        //paginate
        if(dataSize > pageSize){
            try{
                return data.subList(first, first + pageSize);
            }catch (IndexOutOfBoundsException e){
                return data.subList(first, first + (dataSize % pageSize));
            }
        }else{
            return data;
        }
    }
}
