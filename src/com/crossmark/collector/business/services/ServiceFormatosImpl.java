/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOFormatos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jdominguez
 */
public class ServiceFormatosImpl implements ServiceFormatos{
    DAOFormatos daoFormatosSel;
    DAOFormatos daoFormatosDel;
    DAOFormatos daoFormatosUps;

    @Override
    public List<Map<String, Object>> listaFormatos(int formatosId) {
        return getDaoFormatosSel().listaFormatos(formatosId);
    }

    @Override
    public HashMap<String, Object> borrarFormato(int formatosId) {
        return getDaoFormatosDel().borrarFormato(formatosId);
    }

    @Override
    public HashMap<String, Object> updateFormato(int formatosId, String nombre) {
        return getDaoFormatosUps().updateFormato(formatosId, nombre);
    }

    public DAOFormatos getDaoFormatosSel() {
        return daoFormatosSel;
    }

    public void setDaoFormatosSel(DAOFormatos daoFormatosSel) {
        this.daoFormatosSel = daoFormatosSel;
    }

    public DAOFormatos getDaoFormatosDel() {
        return daoFormatosDel;
    }

    public void setDaoFormatosDel(DAOFormatos daoFormatosDel) {
        this.daoFormatosDel = daoFormatosDel;
    }

    public DAOFormatos getDaoFormatosUps() {
        return daoFormatosUps;
    }

    public void setDaoFormatosUps(DAOFormatos daoFormatosUps) {
        this.daoFormatosUps = daoFormatosUps;
    }
    
    
}
