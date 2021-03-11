/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOCadena;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class ServiceCadenaImpl implements ServiceCadena{
    
    private DAOCadena daoCadena;

    @Override
    public List getCadenas() {
        return daoCadena.getCadenas();
    }

    /**
     * @return the daoCadena
     */
    public DAOCadena getDaoCadena() {
        return daoCadena;
    }

    /**
     * @param daoCadena the daoCadena to set
     */
    public void setDaoCadena(DAOCadena daoCadena) {
        this.daoCadena = daoCadena;
    }
    
}
