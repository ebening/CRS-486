package com.crossmark.collector.persistence.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class DAOCatalogosImpl implements DAOCatalogos, Serializable {
	
	private DatabaseStoredProc spListaCatClientes; //= new DatabaseStoredProc();
	private DatabaseStoredProc spGuardaCatClientes;
	private DatabaseStoredProc spEliminaCatClientes;
	
	@Override
	public List<Map<String, Object>> listaCatClientes() {
		List<Map<String, Object>> listado = new ArrayList<>();
                
		Map<String, Object> inputs=new TreeMap<>();
        inputs.put("ClienteId", 0);

        List out;

        try{
			out = getSpListaCatClientes().execSP(inputs);
			if(out!=null ){
				int size = out.size();
				if(size == 0 || size == -1){
					
				}else{
					Iterator it = out.iterator();
					int i;
					for(i = 0; i < size; i++) {
					   Map<String, Object> map = new HashMap<>();
					   ArrayList<String> sublist = new ArrayList<>();
					   int j = 0;
					   String uN = (String)map.get("UnidadNegocio");
					   
					   //Map<String, Object> m = new HashMap<String, Object>();
					   map = (Map)out.get(i);
					   
					   if( map !=null ){
                                                    
						   listado.add(map);
					   }     
					}	
				}
			}else{
				//System.out.println("out is null ");
			}
		}catch(Exception se){
			se.printStackTrace();
		}
		// TODO Auto-generated method stub
		return listado;
	}
	
	@Override
    public Integer guardaClienteCat(String nombreCliente, String unidadNegocio, String nombreContacto) {
		Map<String, Object> inputs=new TreeMap<>();
        inputs.put("ClienteId", 0);
        inputs.put("Nombre", nombreCliente);
        inputs.put("UnidadNegocio", unidadNegocio);
        inputs.put("Contacto", nombreContacto);
        
        List<Map<String, Object>> out = getSpGuardaCatClientes().execSP(inputs);
        
        Integer clId = 0;
        return clId;
	}
	
	public DatabaseStoredProc getSpGuardaCatClientes() {
		return spGuardaCatClientes;
	}

	public void setSpGuardaCatClientes(DatabaseStoredProc spGuardaCatClientes) {
		this.spGuardaCatClientes = spGuardaCatClientes;
	}

	public DatabaseStoredProc getSpEliminaCatClientes() {
		return spEliminaCatClientes;
	}

	public void setSpEliminaCatClientes(DatabaseStoredProc spEliminaCatClientes) {
		this.spEliminaCatClientes = spEliminaCatClientes;
	}

	@Override
	public Boolean eliminaClienteCat(Integer clienteId) {
		Map<String, Object> inputs=new TreeMap<>();
        inputs.put("ClienteId", 0);
        
        List<Map<String, Object>> out = getSpEliminaCatClientes().execSP(inputs);
        
        boolean res = false;
        return res;
	}


	public DatabaseStoredProc getSpListaCatClientes() {
		return spListaCatClientes;
	}

	public void setSpListaCatClientes(DatabaseStoredProc spListaCatClientes) {
		this.spListaCatClientes = spListaCatClientes;
	}

	
	
}
