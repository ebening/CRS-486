/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author franciscom
 */
public class DAOSeccionesImpl implements DAOSecciones {

    DatabaseStoredProc spSecciones;
    private DatabaseStoredProc spListaSecciones;
    private DatabaseStoredProc spGuardaSeccion;
    private DatabaseStoredProc spEliminaSeccion;

    @Override
    public List<Seccion> getSeccionesByParams(String seccionesOID, String nombre, Integer encuestasId, Integer orden, Integer activa, Integer enabled, Integer ciclica,
            Date fechaCreacion, Integer visible) {
        List<Map<String, Object>> lstResult = null;
        List<Seccion> listado = new ArrayList<Seccion>();
        Seccion seccion;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("SeccionesOID", seccionesOID);
        inputs.put("Nombre", nombre);
        inputs.put("EncuestasId", encuestasId);
        inputs.put("Orden", orden);
        inputs.put("Activa", activa);
        inputs.put("Enabled", enabled);
        inputs.put("Ciclica", ciclica);
        inputs.put("FechaCreacion", fechaCreacion);
        inputs.put("Visible", visible);

        lstResult = spSecciones.execSP(inputs);
        if (lstResult != null) {
            for (Map m : lstResult) {
                seccion = new Seccion();

                seccion.setSeccionesOID(String.valueOf(m.get("SeccionesOID")));
                seccion.setNombre(String.valueOf(m.get("Nombre")));
                seccion.setOrden(Integer.valueOf(String.valueOf(m.get("Orden"))));
                seccion.setActiva(Boolean.valueOf(String.valueOf(m.get("Activa"))));
                seccion.setEnabled(Boolean.valueOf(String.valueOf(m.get("Enabled"))));
                seccion.setCiclica(Boolean.valueOf(String.valueOf(m.get("Ciclica"))));
                //null,
                seccion.setFechaCreacion(DateHelper.convertirStringToDate(String.valueOf(m.get("FechaCreacion"))));
                seccion.setSeccionesPadreOID(String.valueOf(m.get("SeccionesPadreOID")));
                seccion.setVisible(Boolean.valueOf(String.valueOf(m.get("Visible"))));
                seccion.setEncuestasId(Integer.valueOf(String.valueOf(m.get("EncuestasId"))));

                listado.add(seccion);
                //int Equiposd, String Nombre, boolean Activo, int RegionesId
            }
        } else {
            //System.out.println("out is null ");
        }
        
        // TODO Auto-generated method stub
        return listado;

    }

    @Override
    public List<Secciones> listaSecciones(String seccionesOID, String nombreSeccion, int idEncuesta, short orden, boolean activa, boolean enabled, boolean ciclica, Date fechaCreacion, boolean visible) {

        List<Secciones> listaSecciones = new ArrayList<Secciones>();

        Secciones secciones;

        Map<String, Object> inputs = new TreeMap<>();
        if (!seccionesOID.equals("")) {
            inputs.put("SeccionesOID", seccionesOID);
        } else {
            inputs.put("SeccionesOID", null);
        }

        if (!nombreSeccion.equals("")) {
            inputs.put("Nombre", nombreSeccion);
        } else {
            inputs.put("Nombre", null);
        }

        if (idEncuesta != 0) {
            inputs.put("EncuestasId", idEncuesta);
        } else {
            inputs.put("EncuestasId", 0);
        }

        if (orden != 0) {
            inputs.put("Orden", orden);
        } else {
            inputs.put("Orden", 0);
        }

        if (activa == true) {
            inputs.put("Activa", activa);
        } else {
            inputs.put("Activa", false);
        }

        if (enabled == true) {
            inputs.put("Enabled", enabled);
        } else {
            inputs.put("Enabled", false);
        }

        if (ciclica == true) {
            inputs.put("Ciclica", ciclica);
        } else {
            inputs.put("Ciclica", false);
        }

        if (fechaCreacion != null) {
            inputs.put("FechaCreacion", fechaCreacion);
        } else {
            inputs.put("FechaCreacion", null);
        }

        if (visible == true) {
            inputs.put("Visible", visible);
        } else {
            inputs.put("Visible", false);
        }
        
        Map out;
        try {

            out = spListaSecciones.execute(inputs);

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

                                            secciones = new Secciones();
                                            
                                            try {
                                                secciones.setNombreSeccion((String) record.get("Nombre"));

                                            } catch (NullPointerException npe) {
                                                secciones.setNombreSeccion("");
                                            }

                                            try {
                                                secciones.setIdEncuesta((int) record.get("EncuestasId"));

                                            } catch (NullPointerException npe) {
                                                secciones.setIdEncuesta(0);
                                            }
                                            try {
                                                short numero = (short) record.get("Orden");
                                                secciones.setOrden(((short) (numero)));

                                            } catch (NullPointerException npe) {
                                                short numero = (short) 0;
                                                secciones.setOrden(((short) (numero)));
                                            }

                                            try {
                                                secciones.setActiva((boolean) record.get("Activa"));

                                            } catch (NullPointerException npe) {
                                                secciones.setActiva(false);
                                            }

                                            try {
                                                secciones.setCiclica((boolean) record.get("Ciclica"));

                                            } catch (NullPointerException npe) {
                                                secciones.setCiclica(false);
                                            }
                                            try {
                                                secciones.setControlUsuario(Boolean.parseBoolean((record.get("ControlUsuario") == null ? "0" : record.get("ControlUsuario")).toString()) );
                                            } catch (NullPointerException npe) {
                                                secciones.setControlUsuario(false);
                                            }
                                            try {
                                                secciones.setFechaCreacion((Date) record.get("FechaCreacion"));

                                            } catch (NullPointerException npe) {
                                                secciones.setFechaCreacion(null);
                                            }

                                            try {
                                                secciones.setSeccionOID((String) record.get("SeccionesOID"));

                                            } catch (NullPointerException npe) {
                                                secciones.setSeccionOID("");
                                            }

                                            try {
                                                secciones.setSeleccionada((boolean) record.get("Seleccionadas"));
                                            } catch (NullPointerException npe) {
                                                secciones.setSeleccionada(false);
                                            }

                                            try {
                                                secciones.setClaveSeccion((String) record.get("Clave"));

                                            } catch (NullPointerException npe) {
                                                secciones.setClaveSeccion("");
                                            }
                                            
                                            try {
                                                secciones.setListasOID((String) record.get("ListasOID"));
                                            } catch (NullPointerException npe) {
                                                secciones.setListasOID("");
                                            }
                                            
                                            try {
                                                secciones.setPreguntaDominante((String) record.get("PreguntasDominanteOID"));
                                            } catch (NullPointerException npe) {
                                                secciones.setPreguntaDominante("");
                                            }
                                            
                                            try {
                                                secciones.setIdTipoLista(Integer.parseInt((record.get("TipoListasOpcionesId") == null ? 0 : record.get("TipoListasOpcionesId")).toString()));
                                            } catch (NullPointerException npe) {
                                                secciones.setIdTipoLista(0);
                                            }
                                            
                                            listaSecciones.add(secciones);

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

        return listaSecciones;

    }

    public DatabaseStoredProc getSpSecciones() {
        return spSecciones;
    }

    public void setSpSecciones(DatabaseStoredProc spSecciones) {
        this.spSecciones = spSecciones;
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

    @Override
    public String guardaSeccion(String seccionesOID,
            String nombreSeccion,
            int idEncuesta,
            short orden,
            boolean activa,
            boolean enabled,
            boolean ciclica,
            Date fechaCreacion,
            boolean seleccionada,
            String preguntaDominanteOID,
            Integer tipoListasOpciones,
            String listasFiltroOID,
            int variable,
            boolean controlUsuario,
            boolean codigoBarras,
            String listasOID,
            String claveSeccion
            , String seccionPadreOID) {

        String seccionInsertada = "";

        Map<String, Object> inputs = new TreeMap<>();

        if (!seccionesOID.equals("")) {
            inputs.put("SeccionesOID", seccionesOID);
        } else {
            inputs.put("SeccionesOID", null);
        }
        
        inputs.put("Nombre", nombreSeccion);
        inputs.put("EncuestasId", idEncuesta);
        inputs.put("Orden", orden);
        inputs.put("Activa", activa);
        inputs.put("Enabled", enabled);
        inputs.put("Ciclica", ciclica);
        inputs.put("FechaCreacion", fechaCreacion);
        inputs.put("Seleccionadas", seleccionada);
        inputs.put("PreguntasDominanteOID", preguntaDominanteOID);
        inputs.put("TipoListasOpcionesId", tipoListasOpciones);
        inputs.put("ListasFiltroOID", null);
        inputs.put("Variable", variable);
        inputs.put("ControlUsuario", controlUsuario);
        inputs.put("CodigoBarra", codigoBarras);
        inputs.put("ListasOID", listasOID);
        inputs.put("Clave", claveSeccion);
        inputs.put("SeccionesPadreOID", seccionPadreOID);
        Map out;

        try {

            out = spGuardaSeccion.execute(inputs);

            if (out.size() <= 0) {
                // insertado = false;
            } else {

                Set keySet = out.keySet();
                Iterator it = keySet.iterator();
                String key;
                Object val;
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
                                    seccionInsertada = (String) record.get("SeccionesOID");
                                }
                            }
                        }
                    }
                }
                // insertado = true;
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
        return seccionInsertada;
    }

    /**
     * @return the spGuardaSeccion
     */
    public DatabaseStoredProc getSpGuardaSeccion() {
        return spGuardaSeccion;
    }

    /**
     * @param spGuardaSeccion the spGuardaSeccion to set
     */
    public void setSpGuardaSeccion(DatabaseStoredProc spGuardaSeccion) {
        this.spGuardaSeccion = spGuardaSeccion;
    }

    @Override
    public void eliminaSeccion(String seccionOID) {
        
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("SeccionesOID", seccionOID);

        Map out;

        try {

            out = spEliminaSeccion.execute(inputs);

        } catch (Exception se) {
            se.printStackTrace();

        }
    }

    /**
     * @return the spEliminaSeccion
     */
    public DatabaseStoredProc getSpEliminaSeccion() {
        return spEliminaSeccion;
    }

    /**
     * @param spEliminaSeccion the spEliminaSeccion to set
     */
    public void setSpEliminaSeccion(DatabaseStoredProc spEliminaSeccion) {
        this.spEliminaSeccion = spEliminaSeccion;
    }

}
