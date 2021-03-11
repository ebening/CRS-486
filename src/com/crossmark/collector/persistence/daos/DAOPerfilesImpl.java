/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.ModulosPerfiles;
import com.crossmark.collector.business.domain.Perfiles;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOPerfilesImpl  implements DAOPerfiles, Serializable {
    private DatabaseStoredProc spPerfilesDel;
    private DatabaseStoredProc spPerfilesSel;
    private DatabaseStoredProc spPerfilesUps;
    
    private DatabaseStoredProc spModulosPerfilesDel;
    private DatabaseStoredProc spModulosPerfilesSel;
    private DatabaseStoredProc spModulosPerfilesUps;
    
    @Override
    public List<Perfiles> listaPerfiles(Integer perfilesId, Boolean activo) {
        List<Perfiles> listado = new ArrayList<>();
        Map<String, Object> inputs = new TreeMap<>();
        Perfiles perfiles;
        
        inputs.put("PerfilesId", perfilesId == null || perfilesId == 0 ? null : perfilesId);
        inputs.put("Activo", activo);
        
        Map out = spPerfilesSel.execute(inputs);
        
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map m = (Map) object;
                perfiles = new Perfiles();
                
                perfiles.setPerfilesId( Integer.parseInt( String.valueOf(m.get("PerfilesId") == null ? "0" : m.get("PerfilesId")) ) );
                perfiles.setNombre(String.valueOf(m.get("Nombre") == null ? "0" : m.get("Nombre")));
                perfiles.setDescripcion(String.valueOf(m.get("Descripcion") == null ? "" : m.get("Descripcion")));
                perfiles.setAccesoMovil(Boolean.parseBoolean( String.valueOf(m.get("AccesoMovil") == null ? "0" : m.get("AccesoMovil")) ));
                perfiles.setActivo(Boolean.parseBoolean( String.valueOf(m.get("Activo") == null ? "0" : m.get("Activo")) ));
                
                listado.add(perfiles);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return listado;
        
    }

    @Override
    public Perfiles perfilPorId(Integer perfilesId) {
        Map<String, Object> inputs = new TreeMap<>();
        Perfiles perfiles = new Perfiles();
        
        inputs.put("PerfilesId", perfilesId == 0 ? null : perfilesId);
        //inputs.put("Activo", activo);
        
        Map out = spPerfilesSel.execute(inputs);
        
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map m = (Map) object;
                
                perfiles.setPerfilesId( Integer.parseInt( String.valueOf(m.get("PerfilesId") == null ? "0" : m.get("PerfilesId")) ) );
                perfiles.setNombre(String.valueOf(m.get("Nombre") == null ? "0" : m.get("Nombre")));
                perfiles.setDescripcion(String.valueOf(m.get("Descripcion") == null ? "" : m.get("Descripcion")));
                perfiles.setAccesoMovil(Boolean.parseBoolean( String.valueOf(m.get("AccesoMovil") == null ? "0" : m.get("AccesoMovil")) ));
                perfiles.setActivo(Boolean.parseBoolean( String.valueOf(m.get("Activo") == null ? "0" : m.get("Activo")) ));
                break;
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return perfiles;
    }

    @Override
    public String nombrePerfil(Integer perfilesId) {
        String nombre = "";
        List<Map<String, Object>> retorno;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("PerfilesId", perfilesId == 0 ? null : perfilesId);
        
        retorno = spPerfilesSel.execSP(inputs);
        
        for (Map m : retorno) {
            nombre = (String) (m.get("Nombre") == null ? "" : m.get("Nombre"));
        }
        return nombre;
    }

    @Override
    public void crearPerfil(Perfiles perfil) {
        
        Utileria.logger(getClass()).info("Llego a crear perfil");
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("PerfilesId", 0);
        inputs.put("Nombre", perfil.getNombre());
        inputs.put("Descripcion", perfil.getDescripcion());
        inputs.put("AccesoMovil", perfil.isAccesoMovil());
        
        spPerfilesUps.execute(inputs);
    }

    @Override
    public void actualizarPerfil(Perfiles perfil) {
        Utileria.logger(getClass()).info("Llego a editar perfil");
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("PerfilesId", perfil.getPerfilesId());
        inputs.put("Nombre", perfil.getNombre());
        inputs.put("Descripcion", perfil.getDescripcion());
        inputs.put("AccesoMovil", perfil.isAccesoMovil());
        
        spPerfilesUps.execute(inputs);
    }

    @Override
    public String eliminarPerfil(Perfiles perfil) {
        
        String resultado = "";
        Utileria.logger(getClass()).info("Llego a eliminarPerfil");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("PerfilesId", perfil.getPerfilesId());
        Map out = spPerfilesDel.execute(inputs);
        /*try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
               resultado =  element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        */
        return resultado;
    }
    
    
    
    @Override
    public List<ModulosPerfiles> listaModulosPorPerfil(Integer perfilesId) {
        List<ModulosPerfiles> listado = new ArrayList<>();
        Map<String, Object> inputs = new TreeMap<>();
        ModulosPerfiles moduloPerfiles;
        
        inputs.put("PerfilesId", perfilesId == null || perfilesId == 0 ? null : perfilesId);
        
        Map out = spModulosPerfilesSel.execute(inputs);
        
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map m = (Map) object;
                moduloPerfiles = new ModulosPerfiles();
                
                moduloPerfiles.setModulosId(Integer.parseInt( String.valueOf(m.get("ModulosId") == null ? "0" : m.get("ModulosId")) ));
                moduloPerfiles.setModulosPadreId(Integer.parseInt( String.valueOf(m.get("ModulosPadreId") == null ? "0" : m.get("ModulosPadreId")) ));
                moduloPerfiles.setTitulos(String.valueOf(m.get("Titulos") == null ? "" : m.get("Titulos")));
                
                moduloPerfiles.setSeleccionado( (m.get("Seleccionado") == null ? "0" : m.get("Seleccionado").toString()).equals("0") ? false : true );
                
                listado.add(moduloPerfiles);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return listado;
        
    }
    
    @Override
    public void crearModuloPerfiles(Integer moduloPerfilId, Integer perfilesId) {
        
        Utileria.logger(getClass()).info("Llego a crear perfil      getModulosId:"+moduloPerfilId+"    perfilesId:"+perfilesId);
        
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put("ModulosId",moduloPerfilId);
        inputs.put("PerfilesId",perfilesId);
        inputs.put("null", null);
        inputs.put("nul2", null);
        spModulosPerfilesUps.execute(inputs);
    }
    
    @Override
    public String eliminarModulosPerfiles(Integer perfilesId) {
        
        String resultado = "";
        Utileria.logger(getClass()).info("Llego a eliminarPerfil");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("PerfilesId", perfilesId);
        Map out = spModulosPerfilesDel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
               resultado =  element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return resultado;
    }
    

    public DatabaseStoredProc getSpPerfilesDel() {
        return spPerfilesDel;
    }

    public void setSpPerfilesDel(DatabaseStoredProc spPerfilesDel) {
        this.spPerfilesDel = spPerfilesDel;
    }

    public DatabaseStoredProc getSpPerfilesSel() {
        return spPerfilesSel;
    }

    public void setSpPerfilesSel(DatabaseStoredProc spPerfilesSel) {
        this.spPerfilesSel = spPerfilesSel;
    }

    public DatabaseStoredProc getSpPerfilesUps() {
        return spPerfilesUps;
    }

    public void setSpPerfilesUps(DatabaseStoredProc spPerfilesUps) {
        this.spPerfilesUps = spPerfilesUps;
    }

    public DatabaseStoredProc getSpModulosPerfilesDel() {
        return spModulosPerfilesDel;
    }

    public void setSpModulosPerfilesDel(DatabaseStoredProc spModulosPerfilesDel) {
        this.spModulosPerfilesDel = spModulosPerfilesDel;
    }

    public DatabaseStoredProc getSpModulosPerfilesSel() {
        return spModulosPerfilesSel;
    }

    public void setSpModulosPerfilesSel(DatabaseStoredProc spModulosPerfilesSel) {
        this.spModulosPerfilesSel = spModulosPerfilesSel;
    }

    public DatabaseStoredProc getSpModulosPerfilesUps() {
        return spModulosPerfilesUps;
    }

    public void setSpModulosPerfilesUps(DatabaseStoredProc spModulosPerfilesUps) {
        this.spModulosPerfilesUps = spModulosPerfilesUps;
    }
    
    
    
    
    
    
    
    
}
