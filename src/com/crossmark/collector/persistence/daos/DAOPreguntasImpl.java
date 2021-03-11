/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Respuestas;
import com.crossmark.collector.business.domain.SubCategorias;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author RIGG
 */
public class DAOPreguntasImpl implements DAOPreguntas {

    private DatabaseStoredProc spListaTipoRespuesta;
    private DatabaseStoredProc spAgregaPregunta;
    private DatabaseStoredProc spListaPreguntas;
    private DatabaseStoredProc spOpcionesAgregadas;
    private DatabaseStoredProc spEliminaPregunta;
    private DatabaseStoredProc spOpcionesAgregadasDel;
    
    @Override
    public List<Respuestas> listaTipoRespuestas() {
        
        List<Respuestas> listaTipoRespuesta = new ArrayList<Respuestas>();

        Respuestas tipoRespuesta;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("TiposPreguntasId", null);
        Map out;

        try {

            out = spListaTipoRespuesta.execute(inputs);

            if (out != null) {
                
                int tamanio = out.size();

                if (tamanio == 0 || tamanio == -1) {

                } else {

                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    while (it.hasNext()) {
                        key = (String) it.next();
                        val = out.get(key);

                        if (val.equals("")) {

                        } else {
                            List arrValues = (List) val;
                            int tamanioLista = arrValues.size();
                            if (tamanioLista == 0) {

                            } else {
                                if (arrValues != null) {

                                    for (int i = 0; i < arrValues.size(); i++) {

                                        Map record = (Map) arrValues.get(i);
                                        if (record != null) {

                                            tipoRespuesta = new Respuestas();

                                            short numero = (short) record
                                                    .get("TiposPreguntasId");

                                            tipoRespuesta
                                                    .setIdTipoRespuesta((int) (numero));
                                            tipoRespuesta
                                                    .setActiva((boolean) record
                                                            .get("Activa"));
                                            tipoRespuesta
                                                    .setNombreTipoRespuesta((String) record
                                                            .get("Nombre"));
                                            listaTipoRespuesta
                                                    .add(tipoRespuesta);

                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            } else {
                //System.out.println("out is null ");
            }

        } catch (Exception se) {
            se.printStackTrace();
        }

        return listaTipoRespuesta;
    }

    /**
     * @return the spListaTipoRespuesta
     */
    public DatabaseStoredProc getSpListaTipoRespuesta() {
        return spListaTipoRespuesta;
    }

    /**
     * @param spListaTipoRespuesta the spListaTipoRespuesta to set
     */
    public void setSpListaTipoRespuesta(DatabaseStoredProc spListaTipoRespuesta) {
        this.spListaTipoRespuesta = spListaTipoRespuesta;
    }

    public DatabaseStoredProc getSpAgregaPregunta() {
        return spAgregaPregunta;
    }

    public void setSpAgregaPregunta(DatabaseStoredProc spAgregaPregunta) {
        this.spAgregaPregunta = spAgregaPregunta;
    }

    @Override
    public String preguntaAgregada(String preguntaOID, String seccionOID,
            String textoPregunta, String mensaje, int ordenPregunta,
            boolean activa, boolean enabled, boolean visible, String variable,
            int tipoRespuesta, String alerta, int subCategoria,
            boolean bancoPreguntas, int categoria, boolean requerida, int longitudMaxima,
            int valorMinimo, int valorMaximo, int numeroDecimales,String archivoImagen, String valorAlerta) {
        
        String miPreguntaOID = "";
        
        Map<String, Object> inputs = new TreeMap<>();
        
        if (preguntaOID.equals("")) {
            inputs.put("PreguntasOID", null);
        } else {
            inputs.put("PreguntasOID", preguntaOID);
        }
        
        inputs.put("SeccionesOID", seccionOID);
        inputs.put("Descripcion", textoPregunta);
        inputs.put("Mensajes", mensaje);
        inputs.put("Orden", ordenPregunta);
        inputs.put("Activa", activa);
        inputs.put("Enabled", enabled);
        inputs.put("Visible", visible);
        inputs.put("Variable", variable);
        inputs.put("TiposPreguntasId", tipoRespuesta);
        inputs.put("Alerta", alerta);
        inputs.put("SubCategoriasId", subCategoria);
        inputs.put("Banco", bancoPreguntas);
        inputs.put("Minimo", valorMinimo);
        inputs.put("Maximo", valorMaximo);
        inputs.put("NroDecimales", numeroDecimales);
        inputs.put("ArchivoImagen", archivoImagen);
        inputs.put("ValorAlerta", valorAlerta);
        inputs.put("Requerida", requerida);
        
        Map out;
        try {
            out = spAgregaPregunta.execute(inputs);
            if (out.size() > 0) {
                Set keySet = out.keySet();
                Iterator it = keySet.iterator();
                String key;
                Object val;
                while (it.hasNext()) {
                    key = (String) it.next();
                    val = out.get(key);

                    if (val.equals("")) {
                    } else {
                        List arrValues = (List) val;
                        int tamanioLista = arrValues.size();
                        if (tamanioLista == 0) {

                        } else {
                            for (int i = 0; i < arrValues.size(); i++) {
                                Map record = (Map) arrValues.get(i);
                                if (record != null) {
                                    miPreguntaOID = (String) record.get("InsertedID");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
        return miPreguntaOID;
    }

    /**
     * @return the spListaPreguntas
     */
    public DatabaseStoredProc getSpListaPreguntas() {
        return spListaPreguntas;
    }

    /**
     * @param spListaPreguntas the spListaPreguntas to set
     */
    public void setSpListaPreguntas(DatabaseStoredProc spListaPreguntas) {
        this.spListaPreguntas = spListaPreguntas;
    }

    @Override
    public List<Preguntas> listaPreguntas(String preguntaOID, String seccionOID, boolean banco, int idSubCategoria) {
        
        List<Preguntas> listaPreguntas = new ArrayList<Preguntas>();
        Preguntas preguntas;
        Map<String, Object> inputs = new TreeMap<>();
        
        if (!preguntaOID.equals("")) {
            inputs.put("PreguntasOID", preguntaOID);
        } else {
            inputs.put("PreguntasOID", null);
        }
        if (!seccionOID.equals("")) {
            inputs.put("SeccionesOID", seccionOID);
        } else {
            inputs.put("SeccionesOID", null);
        }
        if (idSubCategoria != 0) {
            inputs.put("SubCategoriasID", idSubCategoria);
        } else {
            inputs.put("SubCategoriasID", null);
        }
        inputs.put("Banco", banco);
        
        Map out;

        try {

            out = spListaPreguntas.execute(inputs);

            if (out != null) {
                int tamanio = out.size();

                if (tamanio == 0 || tamanio == -1) {
                    
                } else {
                    
                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    while (it.hasNext()) {
                        key = (String) it.next();
                        val = out.get(key);

                        if (val.equals("")) {

                        } else {
                            List arrValues = (List) val;
                            int tamanioLista = arrValues.size();
                            if (tamanioLista == 0) {
                                
                            } else {
                                if (arrValues != null) {

                                    for (int i = 0; i < arrValues.size(); i++) {

                                        Map record = (Map) arrValues.get(i);
                                        if (record != null) {
                                            
                                            preguntas = new Preguntas();
                                            try {
                                                preguntas.setPreguntaOID((String) (record.get("PreguntasOID") == null ? "" : record.get("PreguntasOID")) );
                                            } catch (NullPointerException npe) {
                                                preguntas.setPreguntaOID("");
                                            }

                                            try {
                                                preguntas.setSeccionOID((String) (record.get("SeccionesOID") == null ? "" : record.get("SeccionesOID")));
                                            } catch (NullPointerException npe) {
                                                preguntas.setSeccionOID("");
                                            }

                                            try {
                                                preguntas.setDescripcionPregunta((String) record.get("Descripcion"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setDescripcionPregunta("");
                                            }

                                            try {
                                                short miOrden = (short) record.get("Orden");

                                                preguntas.setOrden(miOrden);
                                            } catch (NullPointerException npe) {
                                                preguntas.setOrden(0);
                                            }

                                            try {
                                                preguntas.setActiva((boolean) record.get("Activa"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setActiva(false);
                                            }

                                            try {
                                                preguntas.setEnabled((boolean) record.get("Enabled"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setEnabled(false);
                                            }

                                            try {
                                                preguntas.setVisible((boolean) record.get("Visible"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setVisible(false);
                                            }

                                            try {
                                                preguntas.setVariable((String) record.get("Variable"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setVariable("");
                                            }

                                            try {
                                                short miTipoPregunta = (short) record.get("TiposPreguntasId");
                                                preguntas.setIdTipoPregunta(miTipoPregunta);
                                            } catch (NullPointerException npe) {
                                                preguntas.setIdTipoPregunta(0);
                                            }

                                            try {
                                                short miSubCategoria = (short) record.get("SubCategoriasId");
                                                preguntas.setIdSubCategoria(miSubCategoria);
                                            } catch (NullPointerException npe) {
                                                preguntas.setIdSubCategoria(0);
                                            }

                                            try {
                                                preguntas.setMensajesPregunta((String) record.get("Mensajes"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setMensajesPregunta("");
                                            }

                                            try {
                                                preguntas.setAlerta((String) record.get("Alerta"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setAlerta("");
                                            }

                                            try {
                                                preguntas.setBanco((boolean) record.get("Banco"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setBanco(false);
                                            }

                                            try {
                                                //short valorMinimo = (short) record.get("Minimo");
                                                preguntas.setValorMinimo((int) (record.get("Minimo") == null ? 0 : record.get("Minimo")));
                                            } catch (NullPointerException npe) {
                                                preguntas.setValorMinimo(0);
                                            }

                                            try {
                                                //short valorMaximo = (int) (record.get("Maximo") == null ? 0 : record.get("Maximo"));
                                                preguntas.setValorMaximo((int) (record.get("Maximo") == null ? 0 : record.get("Maximo")));
                                            } catch (NullPointerException npe) {
                                                preguntas.setValorMaximo(0);
                                            }
                                            
                                            try {
                                                short valorNroDecimales = (short) record.get("NroDecimales");
                                                preguntas.setNumeroDecimales((int) valorNroDecimales);
                                            } catch (NullPointerException npe) {
                                                preguntas.setNumeroDecimales(0);
                                            }
                                            
                                            try {
                                                preguntas.setArchivoImagen((String) record.get("ArchivoImagen"));
                                            } catch (NullPointerException npe) {
                                                preguntas.setArchivoImagen("");
                                            }
                                            try {
                                                //short longMax = (short) record.get("Maximo");
                                                preguntas.setLongitudMaxima((int) (record.get("Maximo") == null ? 0 : record.get("Maximo")));
                                            } catch (NullPointerException npe) {
                                                preguntas.setLongitudMaxima(0);
                                            }
                                            preguntas.setListaOID((record.get("ListasOID") == null ? "" : record.get("ListasOID")).toString() );
                                            preguntas.setTipoOrdenId(Integer.parseInt( (record.get("TipoOrdenId") == null ? 0 : record.get("TipoOrdenId")).toString() ));
                                            preguntas.setSeleccionadas(Boolean.parseBoolean((record.get("Seleccionadas") == null ? 0 : record.get("Seleccionadas")).toString() ));
                                            preguntas.setPreguntasDominanteOID((record.get("PreguntasDominanteOID") == null ? "" : record.get("PreguntasDominanteOID")).toString() );
                                            preguntas.setOtros(Boolean.parseBoolean((record.get("Otros") == null ? 0 : record.get("Otros")).toString() ));
                                            preguntas.setTipoListasOpcionesId(Integer.parseInt( (record.get("TipoListasOpcionesId") == null ? 0 : record.get("TipoListasOpcionesId")).toString() ));
                                            preguntas.setVariableFiltro(Integer.parseInt((record.get("VariableFiltro") == null ? "0" : record.get("VariableFiltro")).toString()) );
                                            preguntas.setListasFiltroOID((record.get("ListasFiltroOID") == null ? "" : record.get("ListasFiltroOID")).toString() );
                                            preguntas.setRecorridoTotal(Boolean.parseBoolean((record.get("RecorridoTotal") == null ? 0 : record.get("RecorridoTotal")).toString() ));
                                            
                                            preguntas.setControlUsuario(Boolean.parseBoolean((record.get("ControlUsuario") == null ? 0 : record.get("ControlUsuario")).toString() ));
                                            preguntas.setCodigoBarra(Boolean.parseBoolean((record.get("CodigoBarra") == null ? 0 : record.get("CodigoBarra")).toString() ));
                                            preguntas.setHorientacionVertical(Boolean.parseBoolean((record.get("HorientacionVertical") == null ? 0 : record.get("HorientacionVertical")).toString() ));
                                            
                                            preguntas.setCondicionada((record.get("Condicionada") == null ? false : (((int) record.get("Condicionada") > 0 ? true : false )) ));
                                            preguntas.setValorAlerta((record.get("ValorAlerta") == null ? "" : record.get("ValorAlerta")).toString() );
                                            
                                            preguntas.setRequerida(Boolean.parseBoolean((record.get("Requerida") == null ? 0 : record.get("Requerida")).toString() ));
                                            
                                            listaPreguntas.add(preguntas);
                                            
                                        }

                                    }

                                }

                            }

                        }

                    }

                }

            } else {
                //System.out.println("out is null ");
            }

        } catch (Exception se) {
            se.printStackTrace();
        }

        return listaPreguntas;

    }

	@Override
	public String opcionesAgregadas(String listasOID, String preguntaAgregada,
			int tipoOrden, boolean seleccionada, boolean opcionOtros,
			String preguntaDominanteOID, int tipoListasOpciones,
			String listasFiltroOID, Integer variable, boolean recorridoTotal,
			boolean controlUsuario, boolean codigoBarra,
			boolean orientacionVertical, String opcionesPregunta) {
		
		String opcionesAgregadas = "";
		
		
	       Map<String, Object> inputs = new TreeMap<>();

	        if (listasOID.equals("")) {
	            inputs.put("ListasOID", null);
	        } else {
	            inputs.put("ListasOID", listasOID);
	        }
                
	        inputs.put("PreguntasSeccionesOID", preguntaAgregada);
	        inputs.put("TipoOrdenId", tipoOrden);
	        inputs.put("Seleccionadas", seleccionada);
	        inputs.put("Otros", opcionOtros);
                if(preguntaDominanteOID.equals("")){
                    inputs.put("PreguntasDominanteOID", null);
                }else{
                  inputs.put("PreguntasDominanteOID", preguntaDominanteOID);  
                }
	        
	        inputs.put("TipoListasOpciones", tipoListasOpciones);
                if(listasFiltroOID.equals("")){
                    inputs.put("ListasFiltroOID", null);
                }else{
                    inputs.put("ListasFiltroOID", listasFiltroOID);
                }
                
	        inputs.put("Variable", variable);
	        inputs.put("RecorridoTotal", recorridoTotal);
	        inputs.put("ControlUsuario", controlUsuario);
	        inputs.put("CodigoBarra", codigoBarra);
	        inputs.put("HorientacionVertical", orientacionVertical);
	        inputs.put("Opciones", opcionesPregunta);
                
	        Map out;
                
	        try {
	            out = spOpcionesAgregadas.execute(inputs);
	            if (out.size() > 0) {

	                Set keySet = out.keySet();
	                Iterator it = keySet.iterator();
	                String key;
	                Object val;
	                while (it.hasNext()) {
	                    key = (String) it.next();
	                    val = out.get(key);

	                    if (val.equals("")) {

	                    } else {
	                        List arrValues = (List) val;
	                        int tamanioLista = arrValues.size();
	                        if (tamanioLista == 0) {

	                        } else {

	                            for (int i = 0; i < arrValues.size(); i++) {

	                                Map record = (Map) arrValues.get(i);
	                                if (record != null) {

	                                	opcionesAgregadas = (String) record
	                                            .get("InsertedID");
	                                }

	                            }

	                        }

	                    }

	                }

	            }

	        } catch (Exception se) {
	            se.printStackTrace();

	        }

	        return opcionesAgregadas;

	}
        
        @Override
	public String opcionesAgregadasDel(String listasOID, String preguntasSeccionesOID) {
		
		String cadenaDel = "";
		
	       Map<String, Object> inputs = new TreeMap<>();
               
	        if (listasOID.equals("")) {
	            inputs.put("ListasOID", null);
	        } else {
	            inputs.put("ListasOID", listasOID);
	        }
	        inputs.put("PreguntasSeccionesOID", preguntasSeccionesOID);
	        
	        Map out;

	        try {
	            out = spOpcionesAgregadasDel.execute(inputs);
	        } catch (Exception se) {
	            se.printStackTrace();
	        }
	        return cadenaDel;
	}

	public DatabaseStoredProc getSpOpcionesAgregadas() {
		return spOpcionesAgregadas;
	}

	public void setSpOpcionesAgregadas(DatabaseStoredProc spOpcionesAgregadas) {
		this.spOpcionesAgregadas = spOpcionesAgregadas;
	}


    /**
    * @param preguntaEliminar  Identificador de la pregunta a eliminar.
    **/
    @Override
    public void eliminaPregunta(String preguntaEliminar) {
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("PreguntasOID", preguntaEliminar);

        Map out;

        try {

            out = spEliminaPregunta.execute(inputs);

        } catch (Exception se) {
            se.printStackTrace();

        }
    }
    
    /**
     * @return the spEliminaPregunta
     */
    public DatabaseStoredProc getSpEliminaPregunta() {
        return spEliminaPregunta;
    }

    /**
     * @param spEliminaPregunta the spEliminaPregunta to set
     */
    public void setSpEliminaPregunta(DatabaseStoredProc spEliminaPregunta) {
        this.spEliminaPregunta = spEliminaPregunta;
    }

    public DatabaseStoredProc getSpOpcionesAgregadasDel() {
        return spOpcionesAgregadasDel;
    }

    public void setSpOpcionesAgregadasDel(DatabaseStoredProc spOpcionesAgregadasDel) {
        this.spOpcionesAgregadasDel = spOpcionesAgregadasDel;
    }
    
    

}
