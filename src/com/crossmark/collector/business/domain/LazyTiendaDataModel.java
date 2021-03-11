/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import com.crossmark.collector.business.services.ServiceCatalogos;
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
public class LazyTiendaDataModel extends LazyDataModel<Tienda>{
    
    private List<Tienda> datasource;
    private final ServiceCatalogos serviceCatalogos;
    private final int rowCount;
    Map<String, Object> filtros;

    public LazyTiendaDataModel(ServiceCatalogos serviceCatalogos, int rowCount, Map<String, Object> filtros) {
        this.serviceCatalogos = serviceCatalogos;
        this.rowCount = rowCount;
        this.filtros = filtros;
    }
    
    @Override
    public int getRowCount(){
        return rowCount;
    }
    
    @Override
    public List<Tienda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
        List<Tienda> data = new ArrayList<>();
        datasource = serviceCatalogos.getAllTiendas((first/pageSize) + 1, pageSize, this.filtros);
        
        for(Tienda t : datasource){
            boolean match = true;
            
            if(filters != null){
                for(Iterator<String> it = filters.keySet().iterator(); it.hasNext();){
                    try{
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(t.getClass().getField(filterProperty).get(t));
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())){
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
