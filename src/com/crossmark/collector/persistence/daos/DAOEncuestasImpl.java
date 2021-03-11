/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Catalogos;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.business.domain.Plantillas;
import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Usss
 */
public class DAOEncuestasImpl implements DAOEncuestas {

    private DatabaseStoredProc spEncuestas;
    private DatabaseStoredProc spCreaEncuesta;
    private DatabaseStoredProc spGuardaEncuesta;
    private DatabaseStoredProc spGuardaComoPlantilla;
    private DatabaseStoredProc spTraePlantilla;
    private DatabaseStoredProc spListaCatalogos;
    private DatabaseStoredProc spListaPreguntaOrigen;
    private DatabaseStoredProc spListaSecciones;
    private DatabaseStoredProc spTraeEncuesta;
    private DatabaseStoredProc spGuardaEncuestaComoPlantilla;
    private DatabaseStoredProc spEliminaEncuesta;
    private DatabaseStoredProc spEncuestaCondicionesJsonUps;
    private DatabaseStoredProc spListasPreguntasControlSel;

    private JSONArray listaSecciones = null;
    private JSONArray listaPreguntas = null;
    private JSONArray listaOpciones = null;
    
    private Encuestas miEncuesta = new Encuestas();

    /**
     * @return the spEncuestas
     */
    public DatabaseStoredProc getSpEncuestas() {
        return spEncuestas;
    }

    /**
     * @param spEncuestas the spEncuestas to set
     */
    public void setSpEncuestas(DatabaseStoredProc spEncuestas) {
        this.spEncuestas = spEncuestas;
    }

    @Override
    public Integer creaEncuesta(int idTipoEncuesta, int idPlantilla) {

        Integer miIdPlantilla = 0;

        Map<String, Object> inputs = new TreeMap<>();
        return miIdPlantilla;

    }

    /**
     * @return the spCreaEncuesta
     */
    public DatabaseStoredProc getSpCreaEncuesta() {
        return spCreaEncuesta;
    }

    /**
     * @param spCreaEncuesta the spCreaEncuesta to set
     */
    public void setSpCreaEncuesta(DatabaseStoredProc spCreaEncuesta) {
        this.spCreaEncuesta = spCreaEncuesta;
    }

    @Override
    public Integer guardaEncuesta(Integer encuestasId, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId,
            boolean plantilla, boolean activa, Date fechaCreacion, String observacion, int unidadNegocio, boolean operacion) {

        Integer encuestaId = 0;
        statusId = 1;

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", encuestasId);
        inputs.put("Nombre", nombreEncuesta);
        inputs.put("Clave", claveEncuesta);
        inputs.put("GPS", gpsEncuesta);
        inputs.put("StatusId", statusId);//Por default 1, este campo ya no se utilizara, se cambio por Operacion. Pero aun sigue ne base de datos
        inputs.put("Plantilla", plantilla);
        inputs.put("Activa", activa);
        inputs.put("FechaCreacion", fechaCreacion);
        inputs.put("Observaciones", observacion);
        inputs.put("UnidadesNegociosId", unidadNegocio);
        inputs.put("Operacion", operacion);
        
        try {

            Map out = spGuardaEncuesta.execute(inputs);

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

                                    //BigDecimal numero = (BigDecimal) record.get("InsertedID");
                                    //encuestaId = Integer.valueOf(numero.intValue());
                                    encuestaId = Integer.parseInt((record.get("InsertedID") == null ? "0" : record.get("InsertedID")).toString());

                                    //encuestaId = (Integer) record.get("InsertedID");
                                }

                            }

                        }

                    }

                }

            }

        } catch (Exception se) {
            se.printStackTrace();
        }

        return encuestaId;
    }

    /**
     * @return the spGuardaEncuesta
     */
    public DatabaseStoredProc getSpGuardaEncuesta() {
        return spGuardaEncuesta;
    }

    /**
     * @param spGuardaEncuesta the spGuardaEncuesta to set
     */
    public void setSpGuardaEncuesta(DatabaseStoredProc spGuardaEncuesta) {
        this.spGuardaEncuesta = spGuardaEncuesta;
    }

    @Override
    public Integer guardaComoPlantilla(Integer idPlantilla, boolean esPlantilla, String nombre) {
        
        Integer encuestaId = 0;
        CallableStatement cstmt;

        List<Map<Integer, Object>> lst = null;

        List<LinkedHashMap<String, LinkedHashMap>> lstResult = null;

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", idPlantilla);
        //aquí va el otro parámetro
        inputs.put("Plantilla", esPlantilla);
        inputs.put("Nombre", nombre);
        
        Integer miValor;
        try {

            Map out = spGuardaComoPlantilla.execute(inputs);

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
                        val = out.get("#result-set-1");

                        if (val.equals("")) {

                        } else {
                            
                            List arrValues = (List) val;
                            int tamanioLista = arrValues.size();
                            if (tamanioLista == 0) {

                            } else {
                                if (arrValues != null) {
                                    Map record = (Map) arrValues.get(0);
                                    if (record != null) {
                                        Long numero = (Long) record.get("EncuestaPlantillaId");
                                        encuestaId = Integer.valueOf(numero.intValue());
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
        
        return encuestaId;
    }

    @Override
    public List<Plantillas> traePlantilla(Integer idEncuestaPlantilla) {

        Plantillas plantilla;
        List<Plantillas> listaPlantilla = new ArrayList<Plantillas>();

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", idEncuestaPlantilla);
        
        Map out;

        try {
            out = spTraePlantilla.execute(inputs);
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

                                plantilla = null;

                            } else {
                                if (arrValues != null) {

                                    for (int i = 0; i < arrValues.size(); i++) {

                                        Map record = (Map) arrValues.get(i);
                                        if (record != null) {

                                            String[] secciones;

                                            JSONArray listaSecciones = null;
                                            JSONArray listaPreguntas = null;

                                            String objEncuestas = (String) record.get("EncuestaJson");
                                            
                                            JSONObject jsonEncuesta = new JSONObject(objEncuestas);

                                            String nombrePlantilla = jsonEncuesta.getString("Nombre");
                                            
                                            String observacionesPlantilla = jsonEncuesta.getString("Observaciones");
                                            boolean activa = jsonEncuesta.getBoolean("Activa");

                                            listaSecciones = jsonEncuesta.getJSONArray("Secciones");

                                            secciones = new String[listaSecciones.length()];

                                            for (int is = 0; is < listaSecciones.length(); is++) {
                                                JSONObject objEncuesta = listaSecciones.getJSONObject(is);
                                                plantilla = new Plantillas();

                                                plantilla.setEncuestasId((Integer) record.get("Encuestas"));
                                                plantilla.setNombrePlantilla((String) record.get("Nombre"));
                                           // plantilla.setClavePlantilla((String) record.get("Clave"));
                                            
                                                //plantilla.setStatusIdPlantilla((short) record.get("StatusId"));
                                                //plantilla.setActivaPlantilla((boolean) record.get("Activa"));
                                                //plantilla.setEsPlantilla((boolean) record.get("Plantilla"));
                                                plantilla.setSeccionesPlantillaOID((String) record.get("SeccionesOID"));
                                                //plantilla.setNombreSeccionPlantilla((String) record.get("Seccion"));
                                                //plantilla.setEnabledSeccionPlantilla((boolean) record.get("Enabled"));
                                                plantilla.setSeccionCiclicaPlantilla((boolean) record.get("Ciclica"));
                                                //plantilla.setSeccionVisiblePlantilla((boolean) record.get("Visible"));
                                                plantilla.setOrdenSeccionPlantilla((short) record.get("Orden"));

                                                listaPreguntas = jsonEncuesta.getJSONArray("Preguntas");

                                                for (int lp = 0; lp < listaPreguntas.length(); lp++) {

                                                }

                                                plantilla.setPreguntasPlantillaOID((String) record.get("PreguntasOID"));
                                                plantilla.setNombrePreguntaPlantilla((String) record.get("Pregunta"));
                                                plantilla.setPreguntaDinamicaPlantilla((boolean) record.get("Dinamica"));
                                                try {

                                                    plantilla.setVariablePreguntaPlantilla((String) record.get("Variable"));

                                                } catch (NullPointerException npe) {

                                                    plantilla.setVariablePreguntaPlantilla("");

                                                }
                                                plantilla.setEnabledPreguntaPlantilla((boolean) record.get("EnabledPregunta"));
                                                plantilla.setVisiblePreguntaPlantilla((boolean) record.get("VisiblePregunta"));
                                                plantilla.setTiposPreguntasPlantilla((short) record.get("TiposPreguntasId"));
                                                plantilla.setNombreTipoPreguntasPlantilla((String) record.get("TipoPregunta"));
                                                plantilla.setListasPlantillaOID((String) record.get("ListasOID"));
                                                plantilla.setNombreListaPlantilla((String) record.get("Lista"));
                                                plantilla.setOpcionesPlantillaOID((String) record.get("OpcionesOID"));
                                                plantilla.setNombreOpcionesPlantilla((String) record.get("Opcion"));
                                                plantilla.setVisibleOpcionesPlantilla((boolean) record.get("VisibleOpciones"));
                                                try {
                                                    plantilla.setPreguntaDominantePlantillaOID((String) record.get("PreguntasDominanteOID"));

                                                } catch (NullPointerException npe) {

                                                    plantilla.setPreguntaDominantePlantillaOID(null);
                                                }

                                                try {

                                                    plantilla.setNombrePreguntaDominantePlantilla((String) record.get("PreguntaDominante"));

                                                } catch (NullPointerException npe) {

                                                    plantilla.setNombrePreguntaDominantePlantilla("");
                                                }

                                                listaPlantilla.add(plantilla);

                                            }
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

        return listaPlantilla;

    }

    /**
     * @return the spGuardaComoPlantilla
     */
    public DatabaseStoredProc getSpGuardaComoPlantilla() {
        return spGuardaComoPlantilla;
    }

    /**
     * @param spGuardaComoPlantilla the spGuardaComoPlantilla to set
     */
    public void setSpGuardaComoPlantilla(DatabaseStoredProc spGuardaComoPlantilla) {
        this.spGuardaComoPlantilla = spGuardaComoPlantilla;
    }

    /**
     * @return the spTraePlantilla
     */
    public DatabaseStoredProc getSpTraePlantilla() {
        return spTraePlantilla;
    }

    /**
     * @param spTraePlantilla the spTraePlantilla to set
     */
    public void setSpTraePlantilla(DatabaseStoredProc spTraePlantilla) {
        this.spTraePlantilla = spTraePlantilla;
    }

    @Override
    public List<Catalogos> listaCatalogos(String listasOID, boolean catalogo, Integer idTipoLista) {
        
        List<Catalogos> listaCatalogos = new ArrayList<Catalogos>();

        Catalogos catalogos;

        Map<String, Object> inputs = new TreeMap<>();

        if (listasOID.equals("")) {
            inputs.put("ListasOID", null);
        } else {
            inputs.put("ListasOID", listasOID);
        }
        inputs.put("Catalogo", catalogo);
        inputs.put("TipoListasId", idTipoLista);
        
        Map out;
        try {
            out = spListaCatalogos.execute(inputs);
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
                                            
                                            catalogos = new Catalogos();
                                            catalogos.setListasOID((String) record.get("ListasOID"));
                                            catalogos.setNombreCatalogo((String) record.get("Nombre"));
                                            //catalogos.setActiva((boolean) record.get("Activa"));
                                            //catalogos.setCatalogo((boolean) record.get("Catalogo"));
                                            Short tipo = (short) record.get("TipoListasId");
                                            catalogos.setTipoListasId((int) (tipo));
                                            listaCatalogos.add(catalogos);

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
        return listaCatalogos;
    }

    /**
     * @return the spListaCatalogos
     */
    public DatabaseStoredProc getSpListaCatalogos() {
        return spListaCatalogos;
    }

    /**
     * @param spListaCatalogos the spListaCatalogos to set
     */
    public void setSpListaCatalogos(DatabaseStoredProc spListaCatalogos) {
        this.spListaCatalogos = spListaCatalogos;
    }

    @Override
    public List<Preguntas> listaPreguntaOrigen(String seccionPadreOID, Integer encuestaId, int ordenSeccion, int ordenPregunta) {
        
        List<Preguntas> listaPreguntaOrigen = new ArrayList<Preguntas>();

        Map<String, Object> inputs = new TreeMap<>();
        if (encuestaId == 0) {
            inputs.put("EncuestasId", null);
        } else {
            inputs.put("EncuestasId", encuestaId);
        }

        if (ordenSeccion == 0) {
            inputs.put("OrdenSeccion", null);
        } else {
            inputs.put("OrdenSeccion", ordenSeccion);
        }

        if (ordenPregunta == 0) {
            inputs.put("OrdenPregunta", null);
        } else {
            inputs.put("OrdenPregunta", ordenPregunta);
        }
        inputs.put("seccionPadreOID", seccionPadreOID);
        Map out;
        
        try {

            out = spListaPreguntaOrigen.execute(inputs);

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
                                            
                                            Preguntas preguntas = new Preguntas();
                                            
                                            preguntas.setPreguntaOID((String) (record.get("PreguntasOID") == null ? 0 : record.get("PreguntasOID")));
                                            preguntas.setDescripcionPregunta((String) (record.get("Descripcion") == null ? 0 : record.get("Descripcion")));
                                            preguntas.setOrden(Integer.parseInt((record.get("OrdenPregunta") == null ? "0" : record.get("OrdenPregunta")).toString()));
                                            listaPreguntaOrigen.add(preguntas);
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
        
        return listaPreguntaOrigen;

    }

    /**
     * @return the spListaPreguntaOrigen
     */
    public DatabaseStoredProc getSpListaPreguntaOrigen() {
        return spListaPreguntaOrigen;
    }

    /**
     * @param spListaPreguntaOrigen the spListaPreguntaOrigen to set
     */
    public void setSpListaPreguntaOrigen(DatabaseStoredProc spListaPreguntaOrigen) {
        this.spListaPreguntaOrigen = spListaPreguntaOrigen;
    }

    @Override
    public List<Encuestas> listaEncuestas(int idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId, boolean esPlantilla, boolean activa, Date fechaCreacionEncuesta, String observacionEncuesta, int proyectoId, int unidadesNegocioEncuestaId) {
        
        List<Encuestas> listaEncuestas = new ArrayList<>();

        Encuestas encuestas;

        Map<String, Object> inputs = new TreeMap<>();

        if (idEncuesta == 0) {
            inputs.put("EncuestasId", null);
        } else {
            inputs.put("EncuestasId", idEncuesta);
        }

        if (nombreEncuesta.equals("")) {
            inputs.put("Nombre", null);
        } else {
            inputs.put("Nombre", nombreEncuesta);
        }

        if (claveEncuesta.equals("")) {
            inputs.put("Clave", null);
        } else {
            inputs.put("Clave", claveEncuesta);
        }

        if (gpsEncuesta.equals("")) {
            inputs.put("GPS", null);
        } else {
            inputs.put("GPS", gpsEncuesta);
        }

        if (statusId == 0) {
            inputs.put("StatusId", null);
        } else {
            inputs.put("StatusId", statusId);
        }
        
        if (esPlantilla == false) {
            inputs.put("Plantilla", null);
        } else {
            inputs.put("Plantilla", esPlantilla);
        }
        
        inputs.put("Activa", activa);

        if (fechaCreacionEncuesta == null) {
            inputs.put("FechaCreacion", null);
        } else {
            inputs.put("FechaCreacion", fechaCreacionEncuesta);
        }

        try {
            if (observacionEncuesta.equals("")) {
                inputs.put("Observaciones", null);
            } else {
                inputs.put("Observaciones", observacionEncuesta);
            }
        } catch (NullPointerException nop) {
            inputs.put("Observaciones", null);
        }

        if (proyectoId == 0) {
            inputs.put("ProyectosID", null);
        } else {
            inputs.put("ProyectosID", proyectoId);
        }

        if (unidadesNegocioEncuestaId == 0) {
            inputs.put("UnidadesNegociosId", null);
        } else {
            inputs.put("UnidadesNegociosId", unidadesNegocioEncuestaId);
        }
        Map out;
        
        try {
            out = spEncuestas.execute(inputs);
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

                                            encuestas = new Encuestas();
                                            try {
                                                encuestas.setEncuestasId((Integer) record.get("EncuestasId"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setEncuestasId(0);
                                            }

                                            try {
                                                encuestas.setNombreEncuesta((String) record.get("Nombre"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setNombreEncuesta("");
                                            }

                                            try {
                                                encuestas.setClaveEncuesta((String) record.get("Clave"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setClaveEncuesta("");
                                            }

                                            try {
                                                encuestas.setGpsEncuesta((String) record.get("GPS"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setGpsEncuesta("");
                                            }

                                            try {
                                                encuestas.setStatusEncuesta((short) record.get("StatusId"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setStatusEncuesta((short) 0);
                                            }

                                            try {
                                                encuestas.setPlantilla((boolean) record.get("Plantilla"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setPlantilla(false);
                                            }

                                            //encuestas.setFechaCreacion((Date) record.get("FechaCreacion"));
                                            try {
                                                encuestas.setObservaciones((String) record.get("Observaciones"));

                                            } catch (NullPointerException npe) {
                                                encuestas.setObservaciones("");
                                            }

                                            try {
                                                encuestas.setCantidadProyectosVigentes((Integer) record.get("ProyectosVigentes"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setCantidadProyectosVigentes(0);
                                            }

                                            try {
                                                encuestas.setUnidadNegocio((short) record.get("UnidadesNegociosID"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setUnidadNegocio((short) 0);
                                            }

                                            try {
                                                encuestas.setEstatus((String) record.get("Estatus"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setEstatus("");
                                            }

                                            try {
                                                encuestas.setTipo((String) record.get("Tipo"));
                                            } catch (NullPointerException npe) {
                                                encuestas.setTipo("");
                                            }

                                            listaEncuestas.add(encuestas);

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

        return listaEncuestas;

    }

    @Override
    public List<Secciones> listaSecciones(Integer idEncuestaPlantilla) {
        
        //Secciones seccion;
        List<Secciones> listaPlantilla = new ArrayList<Secciones>();

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", idEncuestaPlantilla);
        Map out;

        try {
            out = spTraePlantilla.execute(inputs);
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
                        val = out.get("#result-set-2");

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

                                            String[] secciones;
                                            Secciones seccion;

                                            String objEncuestas = (String) record.get("EncuestaJson");
                                            JSONObject jsonEncuesta = new JSONObject(objEncuestas);

                                            String nombreEncuesta = jsonEncuesta.getString("Nombre");
                                            getMiEncuesta().setNombreEncuesta(nombreEncuesta);
                                            String observacionEncuesta = jsonEncuesta.getString("Observaciones");
                                            getMiEncuesta().setObservaciones(observacionEncuesta);
                                            String esActiva = jsonEncuesta.getString("Activa");
                                            switch (esActiva) {
                                                case "1":
                                                    getMiEncuesta().setActiva(true);
                                                    break;
                                                case "0":
                                                    getMiEncuesta().setActiva(false);
                                                    break;
                                            }

                                            try {

                                                if (jsonEncuesta.isNull("Secciones")) {
                                                    seccion = new Secciones();
                                                    listaPlantilla.add(seccion);
                                                } else {

                                                    setListaSecciones(jsonEncuesta.getJSONArray("Secciones"));

                                                    secciones = new String[getListaSecciones().length()];

                                                    for (int is = 0; is < getListaSecciones().length(); is++) {
                                                        JSONObject objEncuesta = getListaSecciones().getJSONObject(is);
                                                        seccion = new Secciones();

                                                        //Preguntas pregunta;
                                                        //Opciones opciones;
                                                        if (objEncuesta.isNull("Encuestas")) {

                                                            seccion.setIdEncuesta(0);

                                                        } else {

                                                            seccion.setIdEncuesta(objEncuesta.getInt("Encuestas"));

                                                        }

                                                        if (objEncuesta.isNull("Nombre")) {

                                                            seccion.setNombreSeccion("");

                                                        } else {

                                                            seccion.setNombreSeccion(objEncuesta.getString("Nombre"));

                                                        }

                                                        if (objEncuesta.isNull("SeccionesOID")) {

                                                            seccion.setSeccionOID("");

                                                        } else {

                                                            seccion.setSeccionOID(objEncuesta.getString("SeccionesOID"));

                                                        }

                                                        //plantilla.setNombreSeccionPlantilla((String) record.get("Seccion"));
                                                        //plantilla.setEnabledSeccionPlantilla((boolean) record.get("Enabled"));
                                                        if (objEncuesta.isNull("Ciclica")) {

                                                            seccion.setCiclica(false);

                                                        } else {

                                                            int miCiclica = objEncuesta.getInt("Ciclica");

                                                            if (miCiclica == 1) {

                                                                seccion.setCiclica(true);

                                                            } else {
                                                                seccion.setCiclica(false);
                                                            }

                                                        }

                                                        //plantilla.setSeccionVisiblePlantilla((boolean) record.get("Visible"));
                                                        if (objEncuesta.isNull("Orden")) {

                                                            seccion.setOrden(0);

                                                        } else {

                                                            seccion.setOrden(objEncuesta.getInt("Orden"));

                                                        }

                                                        if (objEncuesta.isNull("PreguntaDominante")) {

                                                            seccion.setPreguntaDominante("");

                                                        } else {

                                                            seccion.setPreguntaDominante(objEncuesta.getString("PreguntaDominante"));

                                                        }

                                                        if (objEncuesta.isNull("Preguntas")) {
                                                            setListaPreguntas(null);
                                                            seccion.setMiListaPreguntas(null);
                                                        } else {

                                                            setListaPreguntas(objEncuesta.getJSONArray("Preguntas"));

                                                            seccion.setMiListaPreguntas(this.unaListaPreguntas(getListaPreguntas()));

                                                        }
                                                        listaPlantilla.add(seccion);
                                                    }

                                                }

                                            } catch (NullPointerException npe) {

                                            }

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

        return listaPlantilla;

    }

    public List<Preguntas> unaListaPreguntas(JSONArray lista) throws JSONException {

        Preguntas pregunta;
        List<Preguntas> miListaPreguntas = new ArrayList<Preguntas>();

        try {

            if (lista.length() > 0) {

                for (int lp = 0; lp < lista.length(); lp++) {
                    pregunta = new Preguntas();
                    JSONObject objPregunta = lista.getJSONObject(lp);

                    if (objPregunta.isNull("PreguntasOID")) {
                        pregunta.setPreguntaOID("");

                    } else {
                        pregunta.setPreguntaOID(objPregunta.getString("PreguntasOID"));
                    }

                    if (objPregunta.isNull("Descripcion")) {
                        pregunta.setDescripcionPregunta("");
                    } else {
                        pregunta.setDescripcionPregunta(objPregunta.getString("Descripcion"));
                    }
                    
                    if (objPregunta.isNull("Condicionada")) {
                        pregunta.setCondicionada(false);
                    } else {
                        int x = objPregunta.getInt("Condicionada");
                        if (x == 1) {
                            pregunta.setCondicionada(true);
                        } else {
                            pregunta.setCondicionada(false);
                        }
                    }

                    if (objPregunta.isNull("TipoPreguntaId")) {
                        pregunta.setIdTipoPregunta(0);
                        String miTipo = String.valueOf(pregunta.getIdTipoPregunta());
                        pregunta.setMiTipoPregunta(miTipo);
                    } else {
                        pregunta.setIdTipoPregunta(objPregunta.getInt("TipoPreguntaId"));
                        pregunta.setMiTipoPregunta(objPregunta.getString("TipoPreguntaId"));
                    }
                    
                    if (objPregunta.isNull("Orden")) {
                        pregunta.setOrden(0);
                    } else {
                        pregunta.setOrden(objPregunta.getInt("Orden"));
                    }
                    
                    if (objPregunta.isNull("ArchivoImagen")) {
                        pregunta.setArchivoImagen("");
                    } else {
                        pregunta.setArchivoImagen(objPregunta.getString("ArchivoImagen"));
                    }
                    
                    try {
                        if (objPregunta.isNull("Opciones")) {
                            setListaOpciones(null);
                            pregunta.setMisOpciones(null);
                        } else {

                            setListaOpciones(objPregunta.getJSONArray("Opciones"));

                            pregunta.setMisOpciones(this.unaListaOpciones(listaOpciones));

                        }

                    } catch (NullPointerException npe) {

                    }

                    miListaPreguntas.add(pregunta);

                }

            }

        } catch (NullPointerException npe) {
        }

        return miListaPreguntas;

    }

    public List<Opciones> unaListaOpciones(JSONArray lista) throws JSONException {

        List<Opciones> miListaOpciones = new ArrayList<>();

        Opciones opciones;

        try {

            if (getListaOpciones().length() > 0) {

                for (int lo = 0; lo < getListaOpciones().length(); lo++) {
                    opciones = new Opciones();

                    JSONObject objOpcion = getListaOpciones().getJSONObject(lo);

                    if (objOpcion.isNull("OpcionesOID")) {
                        opciones.setOpcionesOID("");
                    } else {
                        opciones.setOpcionesOID(objOpcion.getString("OpcionesOID"));
                    }

                    if (objOpcion.isNull("ListasOID")) {
                        opciones.setListasOID("");
                    } else {
                        opciones.setListasOID(objOpcion.getString("ListasOID"));
                    }

                    if (objOpcion.isNull("Texto")) {
                        opciones.setTextoOpcion("");
                    } else {
                        opciones.setTextoOpcion(objOpcion.getString("Texto"));
                    }

                    if (objOpcion.isNull("Orden")) {
                        opciones.setOrden(0);
                    } else {
                        opciones.setOrden(objOpcion.getInt("Orden"));
                    }
                    
                    if (objOpcion.isNull("Condiciones")) {
                        opciones.setCondicionada(false);
                    } else {
                        opciones.setCondicionada(true);
                        setConditions(opciones, objOpcion.getJSONArray("Condiciones"));
                    }

                    miListaOpciones.add(opciones);

                }

            }

        } catch (NullPointerException npe) {
        }

        return miListaOpciones;
    }

    private void setConditions(Opciones op, JSONArray jArray) throws JSONException {
        JSONObject json = jArray.getJSONObject(0);
        op.setSeccionAccion(json.getString("SeccionAccion"));
        op.setPreguntaAccion(json.getString("PreguntaAccion"));
        op.setTipoAccion(json.getString("TipoAccion"));
    }

    /**
     * @return the spListaSecciones
     */
    public DatabaseStoredProc getSpListaSecciones() {
        return spListaSecciones;
    }

    /**
     * @param spListaSecciones the spListaSecciones to set
     */
    public void setSpListaSecciones(DatabaseStoredProc spListaSecciones) {
        this.spListaSecciones = spListaSecciones;
    }

    /**
     * @return the listaSecciones
     */
    public JSONArray getListaSecciones() {
        return listaSecciones;
    }

    /**
     * @param listaSecciones the listaSecciones to set
     */
    public void setListaSecciones(JSONArray listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

    /**
     * @return the listaPreguntas
     */
    public JSONArray getListaPreguntas() {
        return listaPreguntas;
    }

    /**
     * @param listaPreguntas the listaPreguntas to set
     */
    public void setListaPreguntas(JSONArray listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    /**
     * @return the listaOpciones
     */
    public JSONArray getListaOpciones() {
        return listaOpciones;
    }

    /**
     * @param listaOpciones the listaOpciones to set
     */
    public void setListaOpciones(JSONArray listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    /**
     * @return the miEncuesta
     */
    public Encuestas getMiEncuesta() {
        return miEncuesta;
    }

    /**
     * @param miEncuesta the miEncuesta to set
     */
    public void setMiEncuesta(Encuestas miEncuesta) {
        this.miEncuesta = miEncuesta;
    }

    @Override
    public Encuestas traerEncuesta(Integer idEncuestaPlantilla) {
        Encuestas laEncuesta = new Encuestas();
        //Secciones seccion;
        Map<String, Object> inputs = new TreeMap<>();
        
        inputs.put("EncuestasId", idEncuestaPlantilla);
        Map out;

        try {
            out = spTraeEncuesta.execute(inputs);
            if (out != null) {
                int tamanio = out.size();

                if (tamanio == 0 || tamanio == -1) {

                } else {
                    
                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    key = (String) it.next();
                    val = out.get("#result-set-1");

                    if (val.equals("")) {

                    } else {
                        
                        List arrValues = (List) val;
                        int tamanioLista = arrValues.size();
                        if (tamanioLista == 0) {

                        } else {
                            if (arrValues != null) {

                                List<Secciones> listaPlantilla = new ArrayList<>();

                                for (Object arrValue : arrValues) {
                                    Map record = (Map) arrValue;
                                    if (record != null) {
                                        String[] secciones;
                                        Secciones seccion;
                                        String objEncuestas = (String) record.get("EncuestaJson");

                                        JSONObject jsonEncuesta = new JSONObject(objEncuestas);
                                        
                                        laEncuesta.setEncuestasId(jsonEncuesta.has("Encuestas") ? 0 : jsonEncuesta.getInt("Encuestas"));
                                        String nombreEncuesta = jsonEncuesta.getString("Nombre");
                                        laEncuesta.setNombreEncuesta(nombreEncuesta);
                                        String observacionEncuesta = jsonEncuesta.getString("Observaciones");
                                        laEncuesta.setObservaciones(observacionEncuesta);
                                        //String esActiva = jsonEncuesta.getString("Activa");
                                        String esActiva = "1";
                                        switch (esActiva) {
                                            case "1":
                                                laEncuesta.setActiva(true);
                                                break;
                                            case "0":
                                                laEncuesta.setActiva(false);
                                                break;
                                        }
                                        
                                        if (jsonEncuesta.isNull("Operacion")) {
                                            laEncuesta.setOperacion(false);
                                        } else {
                                            int miOperacion = jsonEncuesta.getInt("Operacion");
                                            if (miOperacion == 1) {
                                                laEncuesta.setOperacion(true);
                                            } else {
                                                laEncuesta.setOperacion(false);
                                            }
                                        }
                                        
                                        if (jsonEncuesta.isNull("Plantilla")) {
                                            laEncuesta.setPlantilla(false);
                                        } else {
                                            int plantilla = jsonEncuesta.getInt("Plantilla");
                                            if (plantilla == 1) {
                                                laEncuesta.setPlantilla(true);
                                            } else {
                                                laEncuesta.setPlantilla(false);
                                            }
                                        }
                                        
                                        try {
                                            if (jsonEncuesta.isNull("Secciones")) {
                                                laEncuesta.setListaSecciones(null);
                                            } else {
                                                setListaSecciones(jsonEncuesta.getJSONArray("Secciones"));
                                                secciones = new String[getListaSecciones().length()];

                                                for (int is = 0; is < getListaSecciones().length(); is++) {
                                                    JSONObject objEncuesta = getListaSecciones().getJSONObject(is);
                                                    if(objEncuesta.getString("SeccionesPadreOID").equals("null"))
                                                    {
                                                        seccion = generarSecciones(getListaSecciones(), objEncuesta);
                                                        listaPlantilla.add(seccion);
                                                    }
                                                }
                                            }
                                            laEncuesta.setListaSecciones(listaPlantilla);
                                        } catch (NullPointerException npe) {
                                            Utileria.logger(this.getClass()).info("Ha ocurrido un error... favor de verificar...");
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
        return laEncuesta;
    }

    @Override
    public Secciones generarSecciones(JSONArray jsonSecciones, JSONObject objEncuesta) throws JSONException {
        Secciones seccion = new Secciones();
        List<Secciones> listSecciones= new ArrayList<>();
                                                    //Preguntas pregunta;
                                                    //Opciones opciones;
                                                    if (objEncuesta.isNull("Encuestas")) {
                                                        seccion.setIdEncuesta(0);
                                                    } else {
                                                        seccion.setIdEncuesta(objEncuesta.getInt("Encuestas"));
                                                    }
                                                    if (objEncuesta.isNull("Nombre")) {
                                                        seccion.setNombreSeccion("");
                                                    } else {
                                                        seccion.setNombreSeccion(objEncuesta.getString("Nombre"));
                                                    }
                                                    if (objEncuesta.isNull("SeccionesOID")) {
                                                        seccion.setSeccionOID("");
                                                    } else {
                                                        seccion.setSeccionOID(objEncuesta.getString("SeccionesOID"));
                                                    }

                                                    //plantilla.setNombreSeccionPlantilla((String) record.get("Seccion"));
                                                    //plantilla.setEnabledSeccionPlantilla((boolean) record.get("Enabled"));
                                                    if (objEncuesta.isNull("Ciclica")) {
                                                        seccion.setCiclica(false);
                                                    } else {
                                                        int miCiclica = objEncuesta.getInt("Ciclica");
                                                        if (miCiclica == 1) {
                                                            seccion.setCiclica(true);
                                                        } else {
                                                            seccion.setCiclica(false);
                                                        }
                                                    }
        if (objEncuesta.isNull("ControlUsuario")) {
                                                        seccion.setControlUsuario(false);
        } else {
                                                        int mcu = objEncuesta.getInt("ControlUsuario");
            if (mcu == 1) {
                                                            seccion.setControlUsuario(true);
            } else {
                                                            seccion.setControlUsuario(false);
                                                        }
                                                    }
        if (objEncuesta.isNull("Filtro")) {
                                                        seccion.setFiltrada(false);
        } else {
            try {
                                                            int f = objEncuesta.getInt("Filtro");
                if (f == 1) {
                                                                seccion.setFiltrada(true);
                } else {
                                                                seccion.setFiltrada(false);
                                                            } 
            } catch (JSONException je) {
                                                            seccion.setFiltrada(false);
                                                        }                                                          
                                                    }
                                                    //plantilla.setSeccionVisiblePlantilla((boolean) record.get("Visible"));
                                                    if (objEncuesta.isNull("Orden")) {
                                                        seccion.setOrden(0);
                                                    } else {
                                                        seccion.setOrden(objEncuesta.getInt("Orden"));
                                                    }

                                                    if (objEncuesta.isNull("PreguntaDominante")) {
                                                        seccion.setPreguntaDominante("");
                                                    } else {
                                                        seccion.setPreguntaDominante(objEncuesta.getString("PreguntaDominante"));
                                                    }
                                                    if (objEncuesta.isNull("Preguntas")) {
                                                        setListaPreguntas(null);
                                                        seccion.setMiListaPreguntas(null);
                                                    } else {
                                                        setListaPreguntas(objEncuesta.getJSONArray("Preguntas"));
                                                        seccion.setMiListaPreguntas(this.unaListaPreguntas(getListaPreguntas()));
                                                    }
        
        for (int is = 0; is < jsonSecciones.length(); is++) {
            JSONObject objEncuestaTmp = jsonSecciones.getJSONObject(is);
            if (objEncuestaTmp.getString("SeccionesPadreOID").equals(objEncuesta.getString("SeccionesOID"))) {
                listSecciones.add(generarSecciones(jsonSecciones,objEncuestaTmp ));
                                                }
                                            }
        
        if (listSecciones.size() > 0)
        {
            seccion.setSecciones(listSecciones);
                                        }
        
        return seccion;
                                    }
    
    @Override
    public Encuestas traerEncuestaSeccionesPregunta(Integer encuestasId, String preguntasOID) {
        Encuestas encuesta = new Encuestas();
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EncuestasId", encuestasId);
        inputs.put("PreguntasOID", preguntasOID);
        
        Map out = spEncuestaCondicionesJsonUps.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object element : list) {
                
                Map salida = (Map) element;
                String objEncuestas = (String) salida.get("EncuestaJson");
                JSONObject jsonEncuesta = new JSONObject(objEncuestas);
                
                String nombreEncuesta = jsonEncuesta.isNull("Nombre") ? "" : jsonEncuesta.getString("Nombre");
                String observacionEncuesta = jsonEncuesta.isNull("Observaciones") ? "" : jsonEncuesta.getString("Observaciones");
                
                encuesta.setNombreEncuesta(nombreEncuesta);
                encuesta.setObservaciones(observacionEncuesta);
                encuesta.setActiva((jsonEncuesta.isNull("Activa") ? "0" : jsonEncuesta.getString("Activa")).equals("0") ? false : true);
                
                if (jsonEncuesta.isNull("Secciones")) {
                    encuesta.setListaSecciones(null);
                } else {
                    List<Secciones> listaSeccionesEnc = new ArrayList<Secciones>();
                    setListaSecciones(jsonEncuesta.getJSONArray("Secciones"));
                    //secciones = new String[getListaSecciones().length()];
                    
                    for (int is = 0; is < getListaSecciones().length(); is++) {
                        Secciones seccion = new Secciones();
                        //{"SeccionesOID":"A6C6E429-031F-43B0-8A52-89E7F4911699","Nombre":"Seccion 1","Orden":"1","Preguntas":[
                        JSONObject objSeccion = getListaSecciones().getJSONObject(is);
                        
                        seccion.setSeccionOID((objSeccion.isNull("SeccionesOID") ? "" : objSeccion.getString("SeccionesOID")));
                        seccion.setNombreSeccion((objSeccion.isNull("Nombre") ? "" : objSeccion.getString("Nombre")));
                        seccion.setOrden(Integer.parseInt(objSeccion.isNull("Orden") ? "0" : objSeccion.getString("Orden")));
                        
                        if (objSeccion.isNull("Preguntas")) {
                            this.setListaPreguntas(null);
                            seccion.setMiListaPreguntas(null);
                        } else {
                            List<Preguntas> listaPreguntasSec = new ArrayList<Preguntas>();
                            this.setListaPreguntas(objSeccion.getJSONArray("Preguntas"));
                            for (int ip = 0; ip < getListaPreguntas().length(); ip++) {
                                //{"PreguntasOID":"AA8A92B7-C131-4005-AE9F-DDD22A359BA5","Descripcion":"pregunta de imagen","Clave":"","Orden":"0"},
                                Preguntas pregunta = new Preguntas();
                                JSONObject objPregunta = getListaPreguntas().getJSONObject(ip);
                                
                                pregunta.setPreguntaOID((objPregunta.isNull("PreguntasOID") ? "PreguntaOID" : objPregunta.getString("PreguntasOID")));
                                pregunta.setDescripcionPregunta((objPregunta.isNull("Descripcion") ? "Descripcion" : objPregunta.getString("Descripcion")));
                                pregunta.setOrden(Integer.parseInt(objPregunta.isNull("Orden") ? "0" : objPregunta.getString("Orden")));
                                listaPreguntasSec.add(pregunta);
                            }
                            seccion.setMiListaPreguntas(listaPreguntasSec);
                        }
                        listaSeccionesEnc.add(seccion);
                    }
                    encuesta.setListaSecciones(listaSeccionesEnc);
                }
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        } catch (JSONException ex) {
            Logger.getLogger(DAOEncuestasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encuesta;
    }
    
    public DatabaseStoredProc getSpTraeEncuesta() {
        return spTraeEncuesta;
    }

    public void setSpTraeEncuesta(DatabaseStoredProc spTraeEncuesta) {
        this.spTraeEncuesta = spTraeEncuesta;
    }

    /**
     * @return the spGuardaEncuestaComoPlantilla
     */
    public DatabaseStoredProc getSpGuardaEncuestaComoPlantilla() {
        return spGuardaEncuestaComoPlantilla;
    }

    /**
     * @param spGuardaEncuestaComoPlantilla the spGuardaEncuestaComoPlantilla to
     * set
     */
    public void setSpGuardaEncuestaComoPlantilla(DatabaseStoredProc spGuardaEncuestaComoPlantilla) {
        this.spGuardaEncuestaComoPlantilla = spGuardaEncuestaComoPlantilla;
    }

    @Override
    public void guardaEncuestaComoPlantilla(Integer idEncuesta, String nombrePlantilla, boolean esPlantilla) {
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", idEncuesta);
        inputs.put("Nombre", nombrePlantilla);
        inputs.put("Clave", null);
        inputs.put("GPS", null);
        inputs.put("StatusId", 1);
        inputs.put("Plantilla", esPlantilla);
        inputs.put("Activa", null);
        inputs.put("FechaCreacion", null);
        inputs.put("Observaciones", null);
        inputs.put("UnidadesNegociosId", null);
        inputs.put("Operacion", true);

        try {

            Map out = spGuardaEncuestaComoPlantilla.execute(inputs);

        } catch (Exception se) {
            se.printStackTrace();

        }
    }

    public DatabaseStoredProc getSpEliminaEncuesta() {
        return spEliminaEncuesta;
    }

    public void setSpEliminaEncuesta(DatabaseStoredProc spEliminaEncuesta) {
        this.spEliminaEncuesta = spEliminaEncuesta;
    }

    @Override
    public String eliminaEncuesta(Integer idEncuesta) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EncuestasId", idEncuesta);
        Map out;
        
        out = spEliminaEncuesta.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
            Utileria.logger(getClass()).info("eliminaProyecto - resultado:" + resultado);
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;

    }

    public DatabaseStoredProc getSpEncuestaCondicionesJsonUps() {
        return spEncuestaCondicionesJsonUps;
    }

    public void setSpEncuestaCondicionesJsonUps(DatabaseStoredProc spEncuestaCondicionesJsonUps) {
        this.spEncuestaCondicionesJsonUps = spEncuestaCondicionesJsonUps;
    }

    public DatabaseStoredProc getSpListasPreguntasControlSel() {
        return spListasPreguntasControlSel;
    }

    public void setSpListasPreguntasControlSel(DatabaseStoredProc spListasPreguntasControlSel) {
        this.spListasPreguntasControlSel = spListasPreguntasControlSel;
    }
    
    @Override
    public List<Preguntas> getListasPreguntasControl(Integer encuestasId, Integer ordenSeccion) {
        List<Preguntas> listado = new ArrayList<>();
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EncuestasId", encuestasId);
        inputs.put("OrdenSeccion", ordenSeccion);
        
        Map out = spListasPreguntasControlSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                listado.add(genericObjectPreguntaControl(object));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }
    
    /*P.PreguntasOID,LP.ListasOID, P.Descripcion, P.SeccionesOID, S.Clave, S.Orden AS SeccionOrden, P.Orden as PreguntaOrden*/
    @Override
    public Preguntas genericObjectPreguntaControl(Object args) {
        Map element = (Map) args;
        Preguntas pregunta = new Preguntas();
        
        pregunta.setPreguntaOID((element.get("PreguntasOID") == null ? "" : element.get("PreguntasOID")).toString());
        pregunta.setListaOID((element.get("ListasOID") == null ? "" : element.get("ListasOID")).toString());
        pregunta.setDescripcionPregunta((element.get("Descripcion") == null ? "" : element.get("Descripcion")).toString());
        pregunta.setSeccionOID((element.get("SeccionesOID") == null ? "" : element.get("SeccionesOID")).toString());
        pregunta.setOrden(Integer.parseInt((element.get("PreguntaOrden") == null ? "0" : element.get("PreguntaOrden").toString())));
        
        return pregunta;
    }
    
}
