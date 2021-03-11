/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Categorias;
import com.crossmark.collector.business.domain.SubCategorias;
import com.crossmark.collector.persistence.daos.DAOCategorias;
import java.util.List;

/**
 *
 * @author RIGG
 */
public class ServiceCategoriasImpl implements ServiceCategorias {
    
    private DAOCategorias daoCategorias;
    private DAOCategorias daoSubCategorias;

    @Override
    public List<Categorias> listaCategorias() {
        
        return daoCategorias.listaCategorias();
    }

    /**
     * @return the daoCategorias
     */
    public DAOCategorias getDaoCategorias() {
        return daoCategorias;
    }

    /**
     * @param daoCategorias the daoCategorias to set
     */
    public void setDaoCategorias(DAOCategorias daoCategorias) {
        this.daoCategorias = daoCategorias;
    }

    @Override
    public List<SubCategorias> listaSubCategorias(Integer idCategoria, Integer idSubCategoria) {
        
        return daoSubCategorias.listaSubCategorias(idCategoria, idSubCategoria);
    }

    /**
     * @return the daoSubCategorias
     */
    public DAOCategorias getDaoSubCategorias() {
        return daoSubCategorias;
    }

    /**
     * @param daoSubCategorias the daoSubCategorias to set
     */
    public void setDaoSubCategorias(DAOCategorias daoSubCategorias) {
        this.daoSubCategorias = daoSubCategorias;
    }
    
}
