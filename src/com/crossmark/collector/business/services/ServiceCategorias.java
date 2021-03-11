/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Categorias;
import com.crossmark.collector.business.domain.SubCategorias;
import java.util.List;

/**
 *
 * @author RIGG
 */
public interface ServiceCategorias {
    
    public List<Categorias> listaCategorias();
    
    public List<SubCategorias> listaSubCategorias(Integer idCategoria, Integer idSubCategoria);
}
