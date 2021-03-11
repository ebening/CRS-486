/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.EquiposUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOEquiposUsuariosImpl  implements DAOEquiposUsuarios{
    private DatabaseStoredProc storeEquiposUsuariosSel;
    private DatabaseStoredProc storeEquiposUsuariosUps;
    private DatabaseStoredProc storeEquiposUsuariosDel;
    
    
    @Override
    public List<EquiposUsuarios> getListaEquiposUsuarios(String usuariosOID,Integer equiposId,Integer pertenece) {
        
        List<EquiposUsuarios> listado = new ArrayList<>();
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", equiposId);
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("Pertenece", pertenece);
        
        try {
            Map out = storeEquiposUsuariosSel.execute(inputs);
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                listado.add(genericObject(object));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }
    
    
    @Override
    public void actualizarEquiposUsuario(Integer equiposId, String usuariosID, int territoriosId) {
        Utileria.logger(getClass()).info("Llego a actualizarUsuario equiposId:"+equiposId+"   usuariosID:"+usuariosID);
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", equiposId);
        inputs.put("UsuariosOID", usuariosID);
        inputs.put("TerritoriosId", territoriosId);
        
        storeEquiposUsuariosUps.execute(inputs);
        
    }
    
    @Override
    public void eliminarPorUsuario(String usuariosID, int equiposId) {
        Utileria.logger(getClass()).info("Llego a eliminar");
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosID);
        inputs.put("EquiposId", equiposId);
        
        storeEquiposUsuariosDel.execute(inputs);
        
    }

    @Override
    public List<Equipo> traerListaEquipoUsuario(UsuarioSession w) {
        List<Equipo> listado = new ArrayList<>();
        Equipo o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", w.getEquipoId());
        inputs.put("UsuariosOID", w.getOID());
        inputs.put("Pertenece", 1);
        Map out = storeEquiposUsuariosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map)object;
                o = new Equipo();
                o.setId(Integer.valueOf(element.get("EquiposId").toString()));
                o.setNombre(Utileria.validarStringNull(element.get("Nombre")));
                o.setActivo(true);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }

    @Override
    public EquiposUsuarios genericObject(Object args) {
        EquiposUsuarios o = new EquiposUsuarios();
        Map m = (Map)args;
        o.setEquiposId(Integer.valueOf(String.valueOf(m.get("EquiposId") == null ? 0 : m.get("EquiposId"))));
        o.setNombre(String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre")));
        o.setPertenece(Integer.valueOf(String.valueOf(m.get("Pertenece") == null ? 0 : m.get("Pertenece"))));
        
        return o;
    }

    public DatabaseStoredProc getStoreEquiposUsuariosSel() {
        return storeEquiposUsuariosSel;
    }

    public void setStoreEquiposUsuariosSel(DatabaseStoredProc storeEquiposUsuariosSel) {
        this.storeEquiposUsuariosSel = storeEquiposUsuariosSel;
    }

    public DatabaseStoredProc getStoreEquiposUsuariosUps() {
        return storeEquiposUsuariosUps;
    }

    public void setStoreEquiposUsuariosUps(DatabaseStoredProc storeEquiposUsuariosUps) {
        this.storeEquiposUsuariosUps = storeEquiposUsuariosUps;
    }

    public DatabaseStoredProc getStoreEquiposUsuariosDel() {
        return storeEquiposUsuariosDel;
    }

    public void setStoreEquiposUsuariosDel(DatabaseStoredProc storeEquiposUsuariosDel) {
        this.storeEquiposUsuariosDel = storeEquiposUsuariosDel;
    }
    
    
    
}
