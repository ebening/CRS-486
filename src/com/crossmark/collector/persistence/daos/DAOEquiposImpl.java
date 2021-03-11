/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author franciscom
 */
public class DAOEquiposImpl implements DAOEquipos, Serializable {
    private DatabaseStoredProc spEquipos;
    private DatabaseStoredProc spEquiposUps;
    private DatabaseStoredProc spEquiposDel;
    private DatabaseStoredProc spEquiposTerritoriosUps;
    private DatabaseStoredProc spEquiposTerritoriosDel;
    /*
    @Override
    public List getEquipos(Map<String, Object> inputs) {
        return getSpEquipos().execSP(inputs);
    }
    */

    @Override
    public List<Equipo> getEquiposByFind(Integer equiposId, Integer regionesId, Integer unidadesNegociosId) {

        List<Map<String, Object>> lstResult = null;
        List<Equipo> listado = new ArrayList<Equipo>();
        Equipo equipo;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", equiposId);
        inputs.put("RegionesId", regionesId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);

        lstResult = spEquipos.execSP(inputs);
        if (lstResult != null) {
            for (Map m : lstResult) {
                equipo = new Equipo();

                equipo.setId(Integer.valueOf(String.valueOf(m.get("EquiposId"))));
                equipo.setNombre(String.valueOf(m.get("Nombre")));
                equipo.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo"))));
                equipo.setRegionId(Integer.valueOf(String.valueOf(m.get("RegionesId"))));
                equipo.setUnidadNegocioId(Integer.valueOf(String.valueOf(m.get("UnidadesNegociosId"))));

                listado.add(equipo);
                //int Equiposd, String Nombre, boolean Activo, int RegionesId
            }
        } else {
            //System.out.println("out is null ");
        }
        
        // TODO Auto-generated method stub
        return listado;
    }

    @Override
    public List<Equipo> getAll() {
        List<Equipo> listado = new ArrayList<>();
        Equipo o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", null);
        inputs.put("RegionesId", null);
        inputs.put("UnidadesNegociosId", null);
        Map out = spEquipos.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Equipo> getAllActivated() {
        List<Equipo> listado = new ArrayList<>();
        Equipo o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", null);
        inputs.put("RegionesId", null);
        inputs.put("UnidadesNegociosId", null);
        Map out = spEquipos.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
            //    if(o.isActivo()){
                    listado.add(o);
              //  }
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }

    /*
    *spEquiposUps
    *@EquiposId
@TerritoriosId
    *spEquiposUps
    */


    @Override
    public void crear(Equipo o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", null);
        inputs.put("Nombre", o.getNombre());
        inputs.put("RegionesId", o.getRegion().getId());
        inputs.put("UnidadesNegociosId", o.getUnidadesNegocio().getIdUnidadNegocio());
        Map out =  spEquiposUps.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                o.setId(Integer.valueOf(element.get("InsertedID").toString()));
            }
            inputs.clear();
            for (Territorio u : o.getListaTerritorios()) {
                inputs.put("EquiposId", o.getId());
                inputs.put("TerritoriosId",u.getId());
                spEquiposTerritoriosUps.execute(inputs);
                inputs.clear();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
    }

    @Override
    public void editar(Equipo o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", o.getId());
        inputs.put("Nombre", o.getNombre());
        inputs.put("RegionesId", o.getRegion().getId());
        inputs.put("UnidadesNegociosId", o.getUnidadesNegocio().getIdUnidadNegocio());
        Map out =  spEquiposUps.execute(inputs);
        try {
            inputs.clear();
            inputs.put("EquiposId", o.getId());
            spEquiposTerritoriosDel.execute(inputs);
            inputs.clear();

            for (Territorio u : o.getListaTerritorios()) {
                inputs.put("EquiposId", o.getId());
                inputs.put("TerritoriosId",u.getId());
                spEquiposTerritoriosUps.execute(inputs);
                inputs.clear();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
    }

    @Override
    public String eliminar(Equipo o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", o.getId());
        Map out = spEquiposDel.execute(inputs);
        try {

            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
            if(resultado.equals("0")){
                spEquiposTerritoriosDel.execute(inputs);
                inputs.clear();
            }

        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }

    @Override
    public Equipo getById(Integer id) {
        List<Equipo> listado = new ArrayList<>();
        Equipo o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EquiposId", id);
        inputs.put("RegionesId", null);
        inputs.put("UnidadesNegociosId", null);
        Map out = spEquipos.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        
        if(listado != null && !listado.isEmpty() ){
            return listado.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Equipo genericObject(Object args) {
        Map element = (Map) args;
        Equipo equipo = new Equipo();
        Region region = new Region();
        String territorios_ = "";
        UnidadesNegocio unidadesNegocio = new UnidadesNegocio();
        List<Territorio> listaTerritorios = new ArrayList<>();


        equipo.setId(Integer.valueOf(element.get("EquiposID").toString()));
        equipo.setNombre(element.get("Nombre").toString());

        region.setId(Integer.valueOf(element.get("RegionesID").toString()));
        region.setNombre(element.get("Regiones").toString());
        equipo.setRegion(region);

        unidadesNegocio.setIdUnidadNegocio(Integer.valueOf(element.get("UnidadesNegociosID").toString()));
        unidadesNegocio.setNombreUnidad(element.get("UnidadesNegocios").toString());
        equipo.setUnidadesNegocio(unidadesNegocio);


        territorios_ = (element.get("Territorios") == null || element.get("Territorios").equals("")) ? "" : element.get("Territorios").toString();
        Map<Integer, String> territorios = Utileria.paseoVallenato(territorios_);

        //seteando los territorios
        if (!territorios.isEmpty()) {
            for (Map.Entry<Integer, String> entry : territorios.entrySet()) {
                listaTerritorios.add(new Territorio(entry.getKey(), entry.getValue()));
            }

        }
        equipo.setListaTerritorios(listaTerritorios);

        return equipo;
    }

    public DatabaseStoredProc getSpEquipos() {
        return spEquipos;
    }

    public void setSpEquipos(DatabaseStoredProc spEquipos) {
        this.spEquipos = spEquipos;
    }

    public DatabaseStoredProc getSpEquiposUps() {
        return spEquiposUps;
    }

    public void setSpEquiposUps(DatabaseStoredProc spEquiposUps) {
        this.spEquiposUps = spEquiposUps;
    }

    public DatabaseStoredProc getSpEquiposTerritoriosUps() {
        return spEquiposTerritoriosUps;
    }

    public void setSpEquiposTerritoriosUps(DatabaseStoredProc spEquiposTerritoriosUps) {
        this.spEquiposTerritoriosUps = spEquiposTerritoriosUps;
    }

    public DatabaseStoredProc getSpEquiposDel() {
        return spEquiposDel;
    }

    public void setSpEquiposDel(DatabaseStoredProc spEquiposDel) {
        this.spEquiposDel = spEquiposDel;
    }

    public DatabaseStoredProc getSpEquiposTerritoriosDel() {
        return spEquiposTerritoriosDel;
    }

    public void setSpEquiposTerritoriosDel(DatabaseStoredProc spEquiposTerritoriosDel) {
        this.spEquiposTerritoriosDel = spEquiposTerritoriosDel;
    }
}
