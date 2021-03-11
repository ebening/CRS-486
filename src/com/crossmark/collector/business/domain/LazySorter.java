/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author jdominguez
 */
public class LazySorter implements Comparator<Tienda>{
    
    private final String sortField;
    private final SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Tienda o1, Tienda o2) {
        try{
            Object value1 = Tienda.class.getField(this.sortField).get(o1);
            Object value2 = Tienda.class.getField(this.sortField).get(o2);
            
            int value = ((Comparable)value1).compareTo(value2);
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
            throw new RuntimeException();
        }
    }
    
}
