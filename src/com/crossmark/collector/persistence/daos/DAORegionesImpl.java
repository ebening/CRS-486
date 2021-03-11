/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author franciscom
 */
public class DAORegionesImpl implements DAORegiones, Serializable {

    private DatabaseStoredProc spRegionSel;
    private DatabaseStoredProc spRegionDel;
    private DatabaseStoredProc spRegionUps;
    private DAOUsuarios daoUsuarios;

    @Override
    public List<Region> getRegionesByFind(Integer regionId) {

        List<Map<String, Object>> lstResult = null;
        List<Region> listado = new ArrayList<Region>();
        Region region;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", regionId);

        lstResult = spRegionSel.execSP(inputs);
        if (lstResult != null) {
            for (Map m : lstResult) {
                region = new Region();
                region.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo"))));
                region.setNombre(String.valueOf(m.get("Nombre")));
                region.setId(Integer.valueOf(String.valueOf(m.get("RegionesId"))));

                listado.add(region);
                //int Equiposd, String Nombre, boolean Activo, int RegionesId
            }
        } else {
            //System.out.println("out is null ");
        }
        // TODO Auto-generated method stub
        return listado;
    }

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DatabaseStoredProc getSpRegionSel() {
        return spRegionSel;
    }

    public void setSpRegionSel(DatabaseStoredProc spRegionSel) {
        this.spRegionSel = spRegionSel;
    }

    public DatabaseStoredProc getSpRegionDel() {
        return spRegionDel;
    }

    public void setSpRegionDel(DatabaseStoredProc spRegionDel) {
        this.spRegionDel = spRegionDel;
    }

    public DatabaseStoredProc getSpRegionUps() {
        return spRegionUps;
    }

    public void setSpRegionUps(DatabaseStoredProc spRegionUps) {
        this.spRegionUps = spRegionUps;
    }

    @Override
    public Region getById(Integer regionId) {
        List<Region> listado = new ArrayList<>();
        Region o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", regionId);
        Map out = spRegionSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Region> getAll() {
        List<Region> listado = new ArrayList<>();
        Region o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", null);
        Map out = spRegionSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public Region genericObject(Object args) {
        Map element = (Map) args;
        Region region = new Region();
        Usuario coordinador = daoUsuarios.getUsuarioByOID(element.get("UsuariosOID").toString());
        region.setId(Integer.valueOf(element.get("RegionesId").toString()));
        region.setNombre(element.get("Nombre").toString());
        region.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        region.setUserCoordinador(coordinador);


        return region;
    }

    @Override
    public void editar(Region o) {
        Utileria.logger(getClass()).info("Llego a createTerritorio");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", o.getId());
        inputs.put("Nombre", o.getNombre());
        inputs.put("UsuariosOID", o.getUserCoordinador().getUsuariosOID());
        spRegionUps.execute(inputs);
    }

    @Override
    public void crear(Region o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", 0);
        inputs.put("Nombre", o.getNombre());
        inputs.put("UsuariosOID", o.getUserCoordinador().getUsuariosOID());
        spRegionUps.execute(inputs);
    }

    @Override
    public String eliminar(Region o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", o.getId());
        inputs.put("Nombre", null);
        inputs.put("UsuariosOID", null);
        Map out = spRegionDel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }

    @Override
    public List<Region> getAllActivated() {
        List<Region> listado = new ArrayList<>();
        Region o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RegionesId", null);
        Map out = spRegionSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
        for (Object object : list) {
            o = genericObject(object);
            if (o.isActivo()) {
                listado.add(o);
            }
        }
    } catch (NumberFormatException e) {
        Utileria.logger(getClass()).info(e.getMessage());
    }
    return listado;
    }

}
