/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.UsuarioTerritorio;
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
public class DAOUsuariosImpl implements DAOUsuarios, Serializable {

    DatabaseStoredProc storeUsuariosSel;
    DatabaseStoredProc storeUsuariosDel;
    DatabaseStoredProc storeUsuariosUps;
    //DatabaseStoredProc storeUsuariosTerritoriosSel;
    //DatabaseStoredProc storeUsuariosTerritoriosDel;
    DatabaseStoredProc storeUsuariosTerritoriosUps;
    
    
    @Override
    public List<Usuario> getUsuariosByParams(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo) {
        
        List<Map<String, Object>> lstResult = null;
        List<Usuario> listado = new ArrayList<>();
        Usuario usuario;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("UserName", userName);
        inputs.put("Nombre", nombre);
        inputs.put("ApellidoMaterno", apellidoMaterno);
        inputs.put("ApellidoPaterno", apellidoPaterno);
        inputs.put("Direccion", direccion);
        inputs.put("Colonia", colonia);
        inputs.put("CP", cP);
        inputs.put("NroEmpleado", nroEmpleado);
        inputs.put("CiudadesId", ciudadesId);
        inputs.put("EstadosId", estadosId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("TerritorioNativoId", territorioNativoId);
        inputs.put("PerfilesId", perfilesId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("Pertenece", pertenece);
        inputs.put("Activo", activo);
        
        Map out = storeUsuariosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                listado.add(genericObjectUsuario(object));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return listado;
    }

    @Override
    public Usuario getUsuarioByOID(String usuariosOID) {
        List<Usuario> listado = new ArrayList<>();
        Usuario usuario;
        
        Map<String, Object> inputs = new TreeMap<>();
        
        
        inputs.put("UsuariosOID", usuariosOID);
        inputs.put("UserName", null);
        inputs.put("Nombre", null);
        inputs.put("ApellidoMaterno", null);
        inputs.put("ApellidoPaterno", null);
        inputs.put("Direccion", null);
        inputs.put("Colonia", null);
        inputs.put("CP", null);
        inputs.put("NroEmpleado", null);
        inputs.put("CiudadesId", null);
        inputs.put("EstadosId", null);
        inputs.put("TerritoriosId", null);
        inputs.put("TerritorioNativoId", null);
        inputs.put("PerfilesId", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("Pertenece", null);
        inputs.put("Activo", null);
        
        Map out = storeUsuariosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                listado.add(genericObjectUsuario(object));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Usuario> getAllUsuariosCoordinador() {
        List<Usuario> listado = new ArrayList<>();
        Usuario o;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", null);
        inputs.put("UserName", null);
        inputs.put("Nombre", null);
        inputs.put("ApellidoMaterno", null);
        inputs.put("ApellidoPaterno", null);
        inputs.put("Direccion", null);
        inputs.put("Colonia", null);
        inputs.put("CP", null);
        inputs.put("NroEmpleado", null);
        inputs.put("CiudadesId", null);
        inputs.put("EstadosId", null);
        inputs.put("TerritoriosId", null);
        inputs.put("TerritorioNativoId", null);
        inputs.put("PerfilesId", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("EquiposId", null);
        inputs.put("Pertenece", null);
        inputs.put("Activo", null);
        
        Map out = storeUsuariosSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObjectUsuario(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }
    
    @Override
    public void actualizarUsuario(Usuario usuario) {
        Utileria.logger(getClass()).info("Llego a actualizarUsuario");
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuario.getUsuariosOID());
        inputs.put("UserName", usuario.getUserName());
        inputs.put("Nombre", usuario.getNombre());
        inputs.put("ApellidoMaterno", usuario.getApellidoMaterno());
        inputs.put("ApellidoPaterno", usuario.getApellidoPaterno());
        inputs.put("Colonia", usuario.getColonia());
        
        inputs.put("CP", usuario.getcP());
        inputs.put("CiudadesId", usuario.getCiudadesId());
        inputs.put("Activo", usuario.isActivo());
        inputs.put("TerritorioNativoId", usuario.getTerritorioNativoId());
        
        inputs.put("EquiposId", usuario.getEquiposId());
        inputs.put("NroEmpleado", usuario.getNroEmpleado());
        inputs.put("PerfilesId", usuario.getPerfilesId());
        inputs.put("PuestosId", usuario.getPuestosId());
        inputs.put("Altitud", usuario.getAltitud());
        inputs.put("Longitud", usuario.getLongitud());
        
        storeUsuariosUps.execute(inputs);
        
    }
    
    @Override
    public String eliminarUsuario(String usuariosOID) {
        
        String resultado = "";
        Utileria.logger(getClass()).info("Llego a eliminarUsuario");
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", usuariosOID);
        Map out = storeUsuariosDel.execute(inputs);
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
    
    public Usuario genericObjectUsuario(Object args) {
        Map m = (Map) args;
        Usuario usuario = new Usuario();
        
        usuario.setUsuariosOID(String.valueOf(m.get("UsuariosOID") == null ? "" : m.get("UsuariosOID") ));
        usuario.setNroEmpleado(String.valueOf(m.get("NroEmpleado") == null ? "" : m.get("NroEmpleado")  ));
        usuario.setUserName(String.valueOf(m.get("UserName") == null ? "" : m.get("UserName") ));
        usuario.setPassword(String.valueOf(m.get("pass") == null ? "" : m.get("pass")));
        usuario.setNombre(String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre") ));
        usuario.setApellidoMaterno(String.valueOf(m.get("ApellidoMaterno") == null ? "" : m.get("ApellidoMaterno")  ));
        usuario.setApellidoPaterno(String.valueOf(m.get("ApellidoPaterno") == null ? "" : m.get("ApellidoPaterno")  ));
        usuario.setDireccion(String.valueOf(m.get("Direccion") == null ? "" : m.get("Direccion")  ));
        //usuario.setCalle(String.valueOf(m.get("Calle") == null ? "" : m.get("Calle")  ));
        //usuario.setEntreCalle(String.valueOf(m.get("EntreCalle") == null ? "" : m.get("EntreCalle") ));
        //usuario.setNumero(String.valueOf(m.get("Numero") == null ? "" : m.get("Numero")  ));
        usuario.setColonia(String.valueOf(m.get("Colonia") == null ? "" : m.get("Colonia")  ));
        usuario.setcP(String.valueOf(m.get("CP") == null ? "" : m.get("CP")  ));

        usuario.setCiudadesId(Integer.valueOf(String.valueOf(m.get("CiudadesId") == null ? 0 : m.get("CiudadesId") )));
        //usuario.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo") == null ? "0" : m.get("Activo") )));
        usuario.setActivo( m.get("Estatus") == null ? false : String.valueOf(m.get("Estatus")).toLowerCase().equals("activo") ? true : false );
        usuario.setEstatus((String.valueOf(m.get("Estatus") == null ? "" : m.get("Estatus")  )));
        usuario.setMail((String.valueOf(m.get("Mail") == null ? "" : m.get("Mail")  )));

        usuario.setEstadosId(Integer.valueOf(String.valueOf(m.get("EstadosId") == null ? 0 : m.get("EstadosId") )));
        //usuario.setTerritoriosId(Integer.valueOf(String.valueOf(m.get("TerritoriosId"))));
        usuario.setTerritorioNativoId(Integer.valueOf(String.valueOf(m.get("TerritorioNativoId") == null ? 0 : m.get("TerritorioNativoId") )));
        usuario.setTerritorioNativo(String.valueOf(m.get("TerritorioNativo") == null ? "" : m.get("TerritorioNativo")  ));

        usuario.setPerfilesId(Integer.valueOf(String.valueOf(m.get("PerfilesId") == null ? 0 : m.get("PerfilesId") )));
        usuario.setUnidadesNegociosId(Integer.valueOf(String.valueOf(m.get("UnidadesNegociosId") == null ? 0 : m.get("UnidadesNegociosId") )));
        usuario.setEquiposId(Integer.valueOf(String.valueOf(m.get("EquiposId") == null ? 0 : m.get("EquiposId"))));

        usuario.setNombreCiudades(String.valueOf(m.get("NombreCiudades") == null ? "" : m.get("NombreCiudades")  ));
        usuario.setNombreEstados((String.valueOf(m.get("NombreEstados") == null ? "" : m.get("NombreEstados")  )));
        usuario.setNombreEquipo((String.valueOf(m.get("NombreEquipo") == null ? "" : m.get("NombreEquipo")  )));
        usuario.setNombrePerfil((String.valueOf(m.get("NombrePerfil") == null ? "" : m.get("NombrePerfil")  )));
        usuario.setNombrePuesto((String.valueOf(m.get("NombrePuesto") == null ? "" : m.get("NombrePuesto")  )));
        usuario.setPuestosId(Integer.valueOf(String.valueOf(m.get("PuestosId") == null ? 0 : m.get("PuestosId"))));

        usuario.setAltitud(Float.parseFloat(String.valueOf(m.get("Altitud") == null ? 0 : m.get("Altitud"))));
        usuario.setLongitud(Float.parseFloat(String.valueOf(m.get("Longitud") == null ? 0 : m.get("Longitud"))));
        
        return usuario;
    }
    
    @Override
    public void insertarUsuarioTerritorio(String usuariosOID, Integer territoriosId, Integer equiposId){
        Utileria.logger(getClass()).info("Llego a editar insertarUsuarioTerritorio");
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID",usuariosOID);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("EquiposId", equiposId);
        
        storeUsuariosTerritoriosUps.execute(inputs);
        
    }
    
    public UsuarioTerritorio genericObjectUsuarioTerritorio(Object args) {
        Map m = (Map) args;
        UsuarioTerritorio usuario = new UsuarioTerritorio();
        
        usuario.setUsuariosOID(String.valueOf(m.get("UsuariosOID") == null ? "" : m.get("UsuariosOID") ));
        usuario.setNroEmpleado(String.valueOf(m.get("NroEmpleado") == null ? "" : m.get("NroEmpleado")  ));
        usuario.setUserName(String.valueOf(m.get("UserName") == null ? "" : m.get("UserName") ));
        usuario.setNombre(String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre") ));
        usuario.setApellidoMaterno(String.valueOf(m.get("ApellidoMaterno") == null ? "" : m.get("ApellidoMaterno")  ));
        usuario.setApellidoPaterno(String.valueOf(m.get("ApellidoPaterno") == null ? "" : m.get("ApellidoPaterno")  ));
        usuario.setDireccion(String.valueOf(m.get("Direccion") == null ? "" : m.get("Direccion")  ));
        //usuario.setEntreCalle(String.valueOf(m.get("EntreCalle") == null ? "" : m.get("EntreCalle") ));
        //usuario.setNumero(String.valueOf(m.get("Numero") == null ? "" : m.get("Numero")  ));
        usuario.setColonia(String.valueOf(m.get("Colonia") == null ? "" : m.get("Colonia")  ));
        usuario.setcP(String.valueOf(m.get("CP") == null ? "" : m.get("CP")  ));
        usuario.setCiudadesId(Integer.valueOf(String.valueOf(m.get("CiudadesId") == null ? 0 : m.get("CiudadesId") )));
        usuario.setActivo(Boolean.valueOf(String.valueOf(m.get("Activo") == null ? "false" : m.get("Activo") )));
        usuario.setEstadosId(Integer.valueOf(String.valueOf(m.get("EstadosId") == null ? 0 : m.get("EstadosId") )));
        usuario.setTerritorioNativoId(Integer.valueOf(String.valueOf(m.get("TerritorioNativoId") == null ? 0 : m.get("TerritorioNativoId") )));
        usuario.setTerritorioNativo(String.valueOf(m.get("TerritorioNativo") == null ? "" : m.get("TerritorioNativo")  ));
        usuario.setPerfilesId(Integer.valueOf(String.valueOf(m.get("PerfilesId") == null ? 0 : m.get("PerfilesId") )));
        usuario.setNombreCiudades(String.valueOf(m.get("NombreCiudades") == null ? "" : m.get("NombreCiudades")  ));
        usuario.setNombreEstados((String.valueOf(m.get("NombreEstados") == null ? "" : m.get("NombreEstados")  )));
        
        return usuario;
    }
    
    
    public DatabaseStoredProc getStoreUsuariosSel() {
        return storeUsuariosSel;
    }

    public void setStoreUsuariosSel(DatabaseStoredProc storeUsuariosSel) {
        this.storeUsuariosSel = storeUsuariosSel;
    }

    public DatabaseStoredProc getStoreUsuariosDel() {
        return storeUsuariosDel;
    }

    public void setStoreUsuariosDel(DatabaseStoredProc storeUsuariosDel) {
        this.storeUsuariosDel = storeUsuariosDel;
    }

    public DatabaseStoredProc getStoreUsuariosUps() {
        return storeUsuariosUps;
    }

    public void setStoreUsuariosUps(DatabaseStoredProc storeUsuariosUps) {
        this.storeUsuariosUps = storeUsuariosUps;
    }
/*
    public DatabaseStoredProc getStoreUsuariosTerritoriosSel() {
        return storeUsuariosTerritoriosSel;
    }

    public void setStoreUsuariosTerritoriosSel(DatabaseStoredProc storeUsuariosTerritoriosSel) {
        this.storeUsuariosTerritoriosSel = storeUsuariosTerritoriosSel;
    }

    public DatabaseStoredProc getStoreUsuariosTerritoriosDel() {
        return storeUsuariosTerritoriosDel;
    }

    public void setStoreUsuariosTerritoriosDel(DatabaseStoredProc storeUsuariosTerritoriosDel) {
        this.storeUsuariosTerritoriosDel = storeUsuariosTerritoriosDel;
    }
*/
    public DatabaseStoredProc getStoreUsuariosTerritoriosUps() {
        return storeUsuariosTerritoriosUps;
    }

    public void setStoreUsuariosTerritoriosUps(DatabaseStoredProc storeUsuariosTerritoriosUps) {
        this.storeUsuariosTerritoriosUps = storeUsuariosTerritoriosUps;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
