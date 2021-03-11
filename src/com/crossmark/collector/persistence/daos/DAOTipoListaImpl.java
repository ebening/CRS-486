/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.TipoLista;
import com.crossmark.collector.business.domain.TipoListaOpciones;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;

import java.util.*;

/**
 *
 * @author RIGG
 */
public class DAOTipoListaImpl implements DAOTipoLista, Serializable {
    
    private DatabaseStoredProc spTipoLista;
	private DatabaseStoredProc spTipoListaSel;
	private DAOOpcionCruzada daoOpcionCruzada;
	private DAOOpcionPlana daoOpcionPlana;

	public DAOOpcionCruzada getDaoOpcionCruzada() {
		return daoOpcionCruzada;
	}

	public void setDaoOpcionCruzada(DAOOpcionCruzada daoOpcionCruzada) {
		this.daoOpcionCruzada = daoOpcionCruzada;
	}

	public DAOOpcionPlana getDaoOpcionPlana() {
		return daoOpcionPlana;
	}

	public void setDaoOpcionPlana(DAOOpcionPlana daoOpcionPlana) {
		this.daoOpcionPlana = daoOpcionPlana;
	}

	public DatabaseStoredProc getSpTipoListaSel() {
		return spTipoListaSel;
	}

	public void setSpTipoListaSel(DatabaseStoredProc spTipoListaSel) {
		this.spTipoListaSel = spTipoListaSel;
	}


	/**
	 * @return the spTipoLista
	 */
	public DatabaseStoredProc getSpTipoLista() {
		return spTipoLista;
	}

	/**
	 * @param spTipoLista the spTipoLista to set
	 */
	public void setSpTipoLista(DatabaseStoredProc spTipoLista) {
		this.spTipoLista = spTipoLista;
	}
        
	@Override
	public List<TipoListaOpciones> listaTipoLista(Integer tipoListasPreguntasId, boolean secciones, boolean preguntas) {
            //@TipoListasPreguntasId tinyint= null,
//	@Secciones bit = null, @Preguntas bit= null
	    
            List<TipoListaOpciones> lista = new ArrayList<TipoListaOpciones>();
            TipoListaOpciones tipoLista;
            
            Map<String, Object> inputs=new TreeMap<>();
            
            inputs.put("TipoListasPreguntasId", null);
            if(secciones){
                inputs.put("Secciones", secciones);
            }else{
                inputs.put("Secciones", null);
            }
            
            if(preguntas){
                inputs.put("Preguntas", preguntas);
            }else{
                inputs.put("Preguntas", null);
            }
		Map out;
		
		try{
			out=spTipoLista.execute(inputs);
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
										   
										   tipoLista = new TipoListaOpciones();
                                                                                   
                                                                                   Short tipo = (short)record.get("TipoListasPreguntasId");                                                                                   
                                                                                   tipoLista.setTipoListasPreguntasId(new Integer(tipo));
                                                                                   tipoLista.setNombreTipoListaOpciones((String)record.get("Nombre"));
										   lista.add(tipoLista);
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
	public void crear(TipoLista o) {

	}

	@Override
	public void editar(TipoLista o) {

	}

	@Override
	public String eliminar(TipoLista o) {
		return null;
	}

	@Override
	public TipoLista getById(Integer id) {
		List<TipoLista> listado = new ArrayList<>();
		TipoLista o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("TipoListasId", null);
		Map out = spTipoListaSel.execute(inputs);
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				o = genericObject(object);
				listado.add(o);
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).error(e.getMessage());
		}
		return listado.get(0);
	}

	@Override
	public List<TipoLista> getAll() {
		List<TipoLista> listado = new ArrayList<>();
		TipoLista o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("TipoListasId", null);
		Map out = spTipoListaSel.execute(inputs);
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				o = genericObject(object);
				listado.add(o);
			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).error(e.getMessage());
		}
		return listado;
	}

	@Override
	public List<TipoLista> getAllActivated() {
		List<TipoLista> listado = new ArrayList<>();
		TipoLista o;
		Map<String, Object> inputs = new TreeMap<>();
		inputs.put("TipoListasId", null);
		Map out = spTipoListaSel.execute(inputs);
		try {
			List list = (List) out.get("#result-set-1");
			for (Object object : list) {
				o = genericObject(object);
				listado.add(o);

			}
		} catch (NumberFormatException e) {
			Utileria.logger(getClass()).error(e.getMessage());
		}
		return listado;
	}

	@Override
	public TipoLista genericObject(Object args) {
		Map element = (Map) args;
		TipoLista o = new TipoLista();
		o.setId(Integer.valueOf(element.get("TipoListasId").toString()));
		o.setNombre(Utileria.validarStringNull(element.get("Nombre")));
		return o;
	}

    
    
}
