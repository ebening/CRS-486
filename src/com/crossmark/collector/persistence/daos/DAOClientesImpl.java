/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Usss
 */
public class DAOClientesImpl implements DAOClientes , Serializable{
    private DatabaseStoredProc spClientesDel;
    private DatabaseStoredProc spClientesSel;
    private DatabaseStoredProc spClientesUps;
    private DatabaseStoredProc spClientesUnidadesNegociosSel;
    private DatabaseStoredProc spClientesUnidadesNegociosUps;
    private DatabaseStoredProc spClientesUnidadesNegociosDel;

    @Override
    public List<Clientes> listaClientes() {
        
		List<Clientes> lista = new ArrayList<>();
		Clientes clientes;
		Map<String, Object> inputs=new TreeMap<>();
		inputs.put("ClientesId", null);	
                inputs.put("UnidadesNegociosId", 1);
                inputs.put("EquiposId", null);
                inputs.put("TerritoriosId", null);
                inputs.put("UsuariosOID", null);
		Map out;
		try{
			out=spClientesSel.execute(inputs);
			if(out!=null ){
				int tamanio = out.size();
				
				if(tamanio == 0 || tamanio == -1){
				}else{
					Set keySet= out.keySet();
					Iterator it= keySet.iterator();
					String key;
					Object val;
					while(it.hasNext()){
					   key=(String)it.next();
					   val=out.get(key);
					   if(val.equals("")){
					   }else{
                                               
						   List arrValues=(List)val;
						   int tamanioLista = arrValues.size();
						   if(tamanioLista == 0){
						   }else{
							   if( arrValues!=null ){
								   for(int i =0; i < arrValues.size();i++){
									   Map record=(Map)arrValues.get(i);
									   if( record!=null ){
										   clientes = new Clientes();
										   Short num = (short) record.get("ClientesId");
										   clientes.setClienteId(new Integer(num));
										   //clientes.setActivo((int)record.get("Activo"));
										   clientes.setNombreCliente((String) record.get("Nombre"));
										   lista.add(clientes);
									   }
								   }
							   }
						   }
					   }
					}
				}
			}else{
				//System.out.println("out is null ");
			}
		}catch(Exception se){
			se.printStackTrace();
		}
		return lista;
    }

    @Override
    public List<Clientes> listaClientesByParams(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID) {
        
        List<Clientes> lista = new ArrayList<>();
        Clientes clientes;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("ClientesId", clientesId);	
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("UsuariosOID", usuariosOID);
        
        Map out;
        try{
            out=spClientesSel.execute(inputs);
            if(out!=null ){
                
                    int tamanio = out.size();
                    if(tamanio == 0 || tamanio == -1){
                    }else{
                            Set keySet= out.keySet();
                            Iterator it= keySet.iterator();
                            String key;
                            Object val;
                            while(it.hasNext()){
                               key=(String)it.next();
                               val=out.get(key);
                               if(val.equals("")){
                               }else{
                                   
                                       List arrValues=(List)val;
                                       int tamanioLista = arrValues.size();
                                       if(tamanioLista == 0){
                                       }else{
                                               if( arrValues!=null ){
                                                       for(int i =0; i < arrValues.size();i++){
                                                               Map record=(Map)arrValues.get(i);
                                                               if( record!=null ){
                                                                       clientes = new Clientes();
                                                                       Short num = (short)record.get("ClientesId");
                                                                       clientes.setClienteId(new Integer(num));
                               //clientes.setActivo((int)record.get("Activo"));
                                                                       clientes.setNombreCliente((String)record.get("Nombre"));
                                                                       lista.add(clientes);
                                                               }
                                                       }
                                               }
                                       }
                               }
                            }
                    }
            }else{
                //System.out.println("out is null ");
            }
        }catch(Exception se){
                se.printStackTrace();
        }
        return lista;
    }

    /**
     * @return the spClientes
     */
	public DatabaseStoredProc getSpClientesDel() {
		return spClientesDel;
	}

	public void setSpClientesDel(DatabaseStoredProc spClientesDel) {
		this.spClientesDel = spClientesDel;
	}

	public DatabaseStoredProc getSpClientesSel() {
		return spClientesSel;
	}

	public void setSpClientesSel(DatabaseStoredProc spClientesSel) {
		this.spClientesSel = spClientesSel;
	}

	public DatabaseStoredProc getSpClientesUps() {
		return spClientesUps;
	}

	public void setSpClientesUps(DatabaseStoredProc spClientesUps) {
		this.spClientesUps = spClientesUps;
	}

	public DatabaseStoredProc getSpClientesUnidadesNegociosSel() {
		return spClientesUnidadesNegociosSel;
	}

	public void setSpClientesUnidadesNegociosSel(DatabaseStoredProc spClientesUnidadesNegociosSel) {
		this.spClientesUnidadesNegociosSel = spClientesUnidadesNegociosSel;
	}

	public DatabaseStoredProc getSpClientesUnidadesNegociosUps() {
		return spClientesUnidadesNegociosUps;
	}

	public void setSpClientesUnidadesNegociosUps(DatabaseStoredProc spClientesUnidadesNegociosUps) {
		this.spClientesUnidadesNegociosUps = spClientesUnidadesNegociosUps;
	}

	public DatabaseStoredProc getSpClientesUnidadesNegociosDel() {
		return spClientesUnidadesNegociosDel;
	}

	public void setSpClientesUnidadesNegociosDel(DatabaseStoredProc spClientesUnidadesNegociosDel) {
		this.spClientesUnidadesNegociosDel = spClientesUnidadesNegociosDel;
	}

	@Override
	public void crear(Cliente o) {
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", 0);
		inputs.put("Nombre", o.getNombre());
		inputs.put("Encargado", o.getEncargado());
		inputs.put("eMail", o.getEmail());
		inputs.put("Notificaciones", o.isNotificaciones());
		Map out =  spClientesUps.execute(inputs);
                
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				Map element = (Map) object;
				o.setId(Integer.valueOf(element.get("InsertedID").toString()));
			}
			inputs.clear();
			for (UnidadesNegocio u : o.getUnidadesNegocio()) {
				inputs.put("ClientesId", o.getId());
				inputs.put("UnidadesNegociosId",u.getIdUnidadNegocio());
				spClientesUnidadesNegociosUps.execute(inputs);
				inputs.clear();
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).info(e.getMessage());
		}
	}

	@Override
	public void editar(Cliente o) {
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", o.getId());
		inputs.put("Nombre", o.getNombre());
		inputs.put("Encargado", o.getEncargado());
		inputs.put("eMail", o.getEmail());
		inputs.put("Notificaciones", o.isNotificaciones());
                
		Map out =  spClientesUps.execute(inputs);
		try {
			inputs.clear();
			inputs.put("ClientesId", o.getId());
			spClientesUnidadesNegociosDel.execute(inputs);
			inputs.clear();

			for (UnidadesNegocio u : o.getUnidadesNegocio()) {
				inputs.put("ClientesId", o.getId());
				inputs.put("UnidadesNegociosId",u.getIdUnidadNegocio());
				spClientesUnidadesNegociosUps.execute(inputs);
				inputs.clear();
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).info(e.getMessage());
		}
	}

	@Override
	public String eliminar(Cliente o) {
		String resultado = "";
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", o.getId());
		Map out = spClientesDel.execute(inputs);
		try {

			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				Map element = (Map) object;
				resultado = element.get("").toString();
			}

			if(resultado.equals("0")){
				spClientesUnidadesNegociosDel.execute(inputs);
				inputs.clear();

			}

		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).info(e.getMessage());
		}
		return resultado;
	}

	@Override
	public Cliente getById(Integer id) {
		List<Cliente> listado = new ArrayList<>();
		Cliente o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", id);
                
		Map out = spClientesUnidadesNegociosSel.execute(inputs);
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				o = genericObject(object);
				listado.add(o);
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).info(e.getMessage());
		}
                if(listado != null && !listado.isEmpty()){
                    return listado.get(0);
                }else{
                    return null;
                }
	}

	@Override
	public List<Cliente> getAll() {
		List<Cliente> listado = new ArrayList<>();
		Cliente o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", null);
		Map out = spClientesUnidadesNegociosSel.execute(inputs);
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
	public List<Cliente> getAllActivated() {
		List<Cliente> listado = new ArrayList<>();
		Cliente o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("ClientesId", null);
		Map out = spClientesUnidadesNegociosSel.execute(inputs);
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				o = genericObject(object);
				if(o.isActivo()) {
					listado.add(o);
				}
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).info(e.getMessage());
		}
		return listado;
	}

	@Override
	public Cliente genericObject(Object args) {
		Cliente nvo = new Cliente();
		String unidadesNegocios;
		Map element = (Map) args;
		List<UnidadesNegocio> lista = new ArrayList<>();

		nvo.setId(Integer.valueOf(element.get("ClienteID").toString()));
		nvo.setNombre(element.get("Clientes").toString());
		nvo.setEncargado(Utileria.validarStringNull(element.get("Encargado")));
		nvo.setEmail(Utileria.validarStringNull(element.get("eMail")));
		nvo.setNotificaciones(Boolean.valueOf(Utileria.validarBooleanNull(element.get("Notificaciones"))));

		unidadesNegocios = Utileria.validarStringNull(element.get("Unidades"));
		Map<Integer, String> unidadesNegocios_ = Utileria.paseoVallenato(unidadesNegocios);

		//seteando las unidadesNegocios
		if (!unidadesNegocios_.isEmpty()) {
			for (Map.Entry<Integer, String> entry : unidadesNegocios_.entrySet()) {
				lista.add(new UnidadesNegocio(entry.getKey(), entry.getValue()));
			}
		}

		nvo.setUnidadesNegocio(lista);
		return nvo;
	}
}
