/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author jdominguez
 */
public class DAOPromotoresDelImpl implements DAOPromotoresDel{

    DatabaseStoredProc spPromotoresDel;
    
    @Override
    public int deletePromotorTerritorio(String UsuariosOID, int EquiposId) {
        Map<String, Object> inputs = new TreeMap<>();
        int retorno = 0;
        inputs.put("UsuariosOID", UsuariosOID);
        inputs.put("EquiposId", EquiposId);
        List<Map<Integer, Object>> r = getSpPromotoresDel().execSPI(inputs);
        return retorno;
    }

    public DatabaseStoredProc getSpPromotoresDel() {
        return spPromotoresDel;
    }

    public void setSpPromotoresDel(DatabaseStoredProc spPromotoresDel) {
        this.spPromotoresDel = spPromotoresDel;
    }
    
    
}
