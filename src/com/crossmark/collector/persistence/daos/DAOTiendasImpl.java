/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Tienda;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.*;

/**
 *
 * @author jdominguez
 */
public class DAOTiendasImpl implements DAOTiendas {
    private DatabaseStoredProc spTiendas;
    private DatabaseStoredProc spTiendasNumRegSel;
    private DatabaseStoredProc spTiendasTerritoriosSel;
    private DatabaseStoredProc spTiendasTerritoriosNumRegSel;
    private DatabaseStoredProc spTiendasUps;
    private DatabaseStoredProc spTiendasDel;
    
    @Override
    public List<Map<String, Object>> getTiendasList() {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", "");
        return getSpTiendas().execSP(inputs);
    }

    /**
     * @return the spTiendas
     */
    public DatabaseStoredProc getSpTiendas() {
        return spTiendas;
    }

    /**
     * @param spTiendas the spTiendas to set
     */
    public void setSpTiendas(DatabaseStoredProc spTiendas) {
        this.spTiendas = spTiendas;
    }

    @Override
    public List<Map<String, Object>> getTiendaById(int idTienda) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", idTienda);
        return getSpTiendas().execSP(inputs);
    }

    @Override
    public int tiendasNumReg(HashMap<String, Object> filtros) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", filtros.get("TiendasId") == null ? 0 : (int)filtros.get("TiendasId"));
        inputs.put("Nombre", filtros.get("Nombre") == null ? null : (String)filtros.get("Nombre"));
        inputs.put("Activo", true);
        inputs.put("TerritoriosNativoId", filtros.get("TerritoriosNativoId") == null ? 0 : (int)filtros.get("TerritoriosNativoId"));
        inputs.put("CiudadesId", filtros.get("CiudadesId") == null ? 0 : (int)filtros.get("CiudadesId"));
        inputs.put("FormatosId", filtros.get("FormatosId") == null ? 0 : (int)filtros.get("FormatosId"));
        inputs.put("CadenasId", filtros.get("CadenasId") == null ? 0 : (int)filtros.get("CadenasId"));
        inputs.put("EstadosId", filtros.get("EstadosId") == null ? 0 : (int)filtros.get("EstadosId"));
        inputs.put("TerritoriosId", filtros.get("TerritoriosId") == null ? 0 : (int)filtros.get("TerritoriosId"));
        inputs.put("UnidadesNegociosId", filtros.get("UnidadesNegociosId"));
        inputs.put("EquiposId", filtros.get("EquiposId"));
        inputs.put("ClientesId", filtros.get("ClientesId") == null ? 0 : (int)filtros.get("ClientesId"));
        inputs.put("PageIndex", filtros.get("PageIndex"));
        inputs.put("PageSize", filtros.get("PageSize"));
        inputs.put("TotalCount", filtros.get("TotalCount"));
        
        Utileria.logger(getClass()).info("inputs=>>"+inputs);
        
        Map<String, Object> retorno = getSpTiendasNumRegSel().execSP(inputs).get(0);
        
        return Integer.valueOf(String.valueOf(retorno.get("TotalCount")));
    }
    
    @Override
    public int tiendasTerritoriosNumReg(HashMap<String, Object> filtros) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", filtros.get("TiendasId") == null ? 0 : (int)filtros.get("TiendasId"));
        inputs.put("Nombre", filtros.get("Nombre") == null ? null : (String)filtros.get("Nombre"));
        inputs.put("Activo", true);
        inputs.put("TerritoriosNativoId", filtros.get("TerritoriosNativoId") == null ? 0 : (int)filtros.get("TerritoriosNativoId"));
        inputs.put("CiudadesId", filtros.get("CiudadesId") == null ? 0 : (int)filtros.get("CiudadesId"));
        inputs.put("FormatosId", filtros.get("FormatosId") == null ? 0 : (int)filtros.get("FormatosId"));
        inputs.put("CadenasId", filtros.get("CadenasId") == null ? 0 : (int)filtros.get("CadenasId"));
        inputs.put("EstadosId", filtros.get("EstadosId") == null ? 0 : (int)filtros.get("EstadosId"));
        inputs.put("TerritoriosId", filtros.get("TerritoriosId") == null ? 0 : (int)filtros.get("TerritoriosId"));
        inputs.put("UnidadesNegociosId", filtros.get("UnidadesNegociosId"));
        inputs.put("EquiposId", filtros.get("EquiposId"));
        inputs.put("ClientesId", filtros.get("ClientesId") == null ? 0 : (int)filtros.get("ClientesId"));
        inputs.put("PageIndex", filtros.get("PageIndex"));
        inputs.put("PageSize", filtros.get("PageSize"));
        inputs.put("TotalCount", filtros.get("TotalCount"));
        Map<String, Object> retorno = getSpTiendasTerritoriosNumRegSel().execSP(inputs).get(0);
        return Integer.valueOf(String.valueOf(retorno.get("TotalCount")));
    }
    
    @Override
    public List<Map<String, Object>> tiendasListBySearch(HashMap<String, Object> filtros) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", filtros.get("TiendasId") == null ? 0 : (int)filtros.get("TiendasId"));
        inputs.put("Nombre", filtros.get("Nombre") == null ? null : (String)filtros.get("Nombre"));
        inputs.put("Activo", true);
        inputs.put("TerritoriosNativoId", filtros.get("TerritoriosNativoId") == null ? 0 : (int)filtros.get("TerritoriosNativoId"));
        inputs.put("CiudadesId", filtros.get("CiudadesId") == null ? 0 : (int)filtros.get("CiudadesId"));
        inputs.put("FormatosId", filtros.get("FormatosId") == null ? 0 : (int)filtros.get("FormatosId"));
        inputs.put("CadenasId", filtros.get("CadenasId") == null ? 0 : (int)filtros.get("CadenasId"));
        inputs.put("EstadosId", filtros.get("EstadosId") == null ? 0 : (int)filtros.get("EstadosId"));
        inputs.put("TerritoriosId", filtros.get("TerritoriosId") == null ? 0 : (int)filtros.get("TerritoriosId"));
        inputs.put("UnidadesNegociosId", filtros.get("UnidadesNegociosId"));
        inputs.put("EquiposId", filtros.get("EquiposId"));
        inputs.put("ClientesId", filtros.get("ClientesId") == null ? 0 : (int)filtros.get("ClientesId"));
        inputs.put("PageIndex", filtros.get("PageIndex"));
        inputs.put("PageSize", filtros.get("PageSize"));
        inputs.put("TotalCount", filtros.get("TotalCount"));
        
        Utileria.logger(getClass()).info("tiendasListBySearch inputs=>>"+inputs);
        return getSpTiendasTerritoriosSel().execSP(inputs);
    }
    
    @Override
    public List<Map<String, Object>> getTiendasList(HashMap<String, Object> filtros) {
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("TiendasId", filtros.get("TiendasId") == null ? 0 : (int)filtros.get("TiendasId"));
        inputs.put("Nombre", filtros.get("Nombre") == null ? null : (String)filtros.get("Nombre"));
        //inputs.put("Activo", filtros.get("Activo") == null ? false : (boolean)filtros.get("Activo"));
        inputs.put("Activo", true);
        inputs.put("TerritoriosNativoId", filtros.get("TerritoriosNativoId") == null ? 0 : (int)filtros.get("TerritoriosNativoId"));
        inputs.put("CiudadesId", filtros.get("CiudadesId") == null ? 0 : (int)filtros.get("CiudadesId"));
        inputs.put("FormatosId", filtros.get("FormatosId") == null ? 0 : (int)filtros.get("FormatosId"));
        inputs.put("CadenasId", filtros.get("CadenasId") == null ? 0 : (int)filtros.get("CadenasId"));
        inputs.put("EstadosId", filtros.get("EstadosId") == null ? 0 : (int)filtros.get("EstadosId"));
        inputs.put("TerritoriosId", filtros.get("TerritoriosId") == null ? null : (int)filtros.get("TerritoriosId"));
        inputs.put("UnidadesNegociosId", filtros.get("UnidadesNegociosId"));
        inputs.put("EquiposId", filtros.get("EquiposId"));
        inputs.put("ClientesId", filtros.get("ClientesId") == null ? 0 : (int)filtros.get("ClientesId"));
        inputs.put("PageIndex", filtros.get("PageIndex"));
        inputs.put("PageSize", filtros.get("PageSize"));
        inputs.put("TotalCount", filtros.get("TotalCount"));
        Utileria.logger(getClass()).info("getTiendasList inputs=>>"+inputs);
        return getSpTiendas().execSP(inputs);
    }

      @Override
    public void crear(Tienda o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiendasId", null);
        inputs.put("TerritoriosNativoId", o.getTerritorioNativo().getId());
        inputs.put("Nombre", o.getNombre());
        inputs.put("FormatosId", o.getFormato().getId());
        inputs.put("Direccion", o.getDireccion());
        inputs.put("CoordenadaY",Utileria.validarFloatNull(o.getCoordenadaY()));
        inputs.put("CoordenadaX",Utileria.validarFloatNull(o.getCoordenadaX()));
        inputs.put("CiudadesId", o.getCiudad().getId());
        inputs.put("CadenasId", o.getCadena().getId());
        inputs.put("CP", o.getCp());
        inputs.put("Clave", o.getClave());
        
        spTiendasUps.execute(inputs);

    }

    @Override
    public void editar(Tienda o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiendasId", o.getId());
        inputs.put("TerritoriosNativoId", o.getTerritorioNativo().getId());
        inputs.put("Nombre", o.getNombre());
        inputs.put("FormatosId", o.getFormato().getId());
        inputs.put("Direccion", o.getDireccion());
        inputs.put("CoordenadaY", Float.valueOf(o.getCoordenadaY()));
        inputs.put("CoordenadaX", Float.valueOf(o.getCoordenadaX()));
        inputs.put("CiudadesId", o.getCiudad().getId());
        inputs.put("CadenasId", o.getCadena().getId());
        inputs.put("CP", Utileria.validarIntegerNull(o.getCp()));
        inputs.put("Clave", o.getClave());
        
        spTiendasUps.execute(inputs);

    }

    @Override
    public String eliminar(Tienda o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        Utileria.logger(getClass()).info(o.getId());
        inputs.put("TiendasId", o.getId());
        Map out = spTiendasDel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        Utileria.logger(getClass()).info(resultado);
        return resultado;
    }

    @Override
    public Tienda getById(Integer id) {
        List<Tienda> listado = new ArrayList<>();
        Tienda o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiendasId", id);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        inputs.put("TerritoriosNativoId", null);
        inputs.put("CiudadesId", null);
        inputs.put("FormatosId", null);
        inputs.put("CadenasId", null);
        inputs.put("EstadosId", null);
        inputs.put("TerritoriosId", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClientesId", null);
        Map out = getSpTiendas().execute(inputs);
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
        return listado.get(0);
    }

    @Override
    public List<Tienda> getAll(int pageIndex, int pageSize, Map<String, Object> inputs) {
        List<Tienda> listado = new ArrayList<>();
        Tienda o;
        //Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiendasId", 0);
        //inputs.put("Nombre", null);
        inputs.put("Activo", true);
        inputs.put("TerritoriosNativoId", 0);
        //inputs.put("CiudadesId", 0);
        //inputs.put("FormatosId", 0);
        //inputs.put("CadenasId", 0);
        //inputs.put("EstadosId", 0);
        inputs.put("TerritoriosId", 0);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClientesId", 0);
        inputs.put("PageIndex", pageIndex);
        inputs.put("PageSize", pageSize);
        inputs.put("TotalCount", null);
        //Map out = getSpTiendas().execute(inputs);
        Map out = getSpTiendasTerritoriosSel().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
               /* if (o.isActivo()) {
                    listado.add(o);
                } */
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Tienda> getAllActivated(int pageIndex, int pageSize) {
        List<Tienda> listado = new ArrayList<>();
        Tienda o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiendasId", null);
        inputs.put("Nombre", null);
        inputs.put("Activo", null);
        inputs.put("TerritoriosNativoId", null);
        inputs.put("CiudadesId", null);
        inputs.put("FormatosId", null);
        inputs.put("CadenasId", null);
        inputs.put("EstadosId", null);
        inputs.put("TerritoriosId", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClientesId", null);
        inputs.put("PageIndex", pageIndex);
        inputs.put("PageSize", pageSize);
        inputs.put("TotalCount", null);
        Map out = getSpTiendas().execute(inputs);
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

    @Override
    public Tienda genericObject(Object args) {
        Map element = (Map)args;
        Tienda tienda = new Tienda();
        
        tienda.setId(Integer.valueOf(element.get("TiendasId").toString()));
        tienda.setNombre(element.get("Nombre").toString());
        tienda.setClave(Utileria.validarStringNull(element.get("Clave")));
        
        tienda.setDireccion(Utileria.validarStringNull(element.get("Direccion")));
        tienda.setCp(Utileria.validarStringNull(element.get("CP")));
        tienda.getCiudad().setId(Integer.valueOf(element.get("CiudadesId").toString()));
        tienda.getCiudad().setNombre(element.get("Ciudades").toString());
        tienda.getCiudad().getEstado().setId(Integer.valueOf(element.get("EstadosId").toString()));
        tienda.getCiudad().getEstado().setNombre(element.get("Estados").toString());
        tienda.setNumero(Utileria.validarIntegerNull(element.get("Numero") == null ? element.get("Numero") : 0));
        tienda.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        tienda.setCoordenadaX(Utileria.validarStringNull(element.get("CoordenadaX")));
        tienda.setCoordenadaY(Utileria.validarStringNull(element.get("CoordenadaY")));
        tienda.getCadena().setId(Integer.valueOf(element.get("CadenasId").toString()));
        tienda.getCadena().setNombre(element.get("Cadena").toString());
        tienda.getFormato().setId(Integer.valueOf(element.get("FormatosId").toString()));
        tienda.getFormato().setNombre(element.get("Formato").toString());
        tienda.getTerritorioNativo().setId(Integer.valueOf(element.get("TerritoriosNativoId").toString()));
        tienda.getTerritorioNativo().setNombre(Utileria.validarStringNull(element.get("TerritoriosNativos")));

        
        return tienda;
    }
    
    @Override
    public List<Tienda> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tienda> getAllActivated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

/* *********************************Getters & Setters *********************************************** */
    public DatabaseStoredProc getSpTiendasUps() {
        return spTiendasUps;
    }

    public void setSpTiendasUps(DatabaseStoredProc spTiendasUps) {
        this.spTiendasUps = spTiendasUps;
    }

    public DatabaseStoredProc getSpTiendasDel() {
        return spTiendasDel;
    }

    public void setSpTiendasDel(DatabaseStoredProc spTiendasDel) {
        this.spTiendasDel = spTiendasDel;
    }

    public DatabaseStoredProc getSpTiendasTerritoriosSel() {
        return spTiendasTerritoriosSel;
    }

    public void setSpTiendasTerritoriosSel(DatabaseStoredProc spTiendasTerritoriosSel) {
        this.spTiendasTerritoriosSel = spTiendasTerritoriosSel;
    } 

    public DatabaseStoredProc getSpTiendasTerritoriosNumRegSel() {
        return spTiendasTerritoriosNumRegSel;
    }

    public void setSpTiendasTerritoriosNumRegSel(DatabaseStoredProc spTiendasTerritoriosNumRegSel) {
        this.spTiendasTerritoriosNumRegSel = spTiendasTerritoriosNumRegSel;
    }

    public DatabaseStoredProc getSpTiendasNumRegSel() {
        return spTiendasNumRegSel;
    }

    public void setSpTiendasNumRegSel(DatabaseStoredProc spTiendasNumRegSel) {
        this.spTiendasNumRegSel = spTiendasNumRegSel;
    }

    
}
