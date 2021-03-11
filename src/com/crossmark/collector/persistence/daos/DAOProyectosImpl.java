package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Archivos;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DAOProyectosImpl implements DAOProyectos, Serializable {

    private DatabaseStoredProc spProyectos;
    private DatabaseStoredProc spGuardaProyecto;
    private DatabaseStoredProc spCreaEncuesta;
    private DatabaseStoredProc spTraeProyecto;
    //private DatabaseStoredProc spListaEncuestasProyecto;
    private DatabaseStoredProc spAgregaEncuestaProyecto;
    private DatabaseStoredProc spEliminaProyecto;
    private DatabaseStoredProc spEliminaEncuestaProyecto;
    private DatabaseStoredProc spTraeListaEncuestasProyecto;
    private DatabaseStoredProc spEncuestas;
    
    //SPs para archivos
    private DatabaseStoredProc spArchivosDel;
    private DatabaseStoredProc spArchivosSel;
    private DatabaseStoredProc spArchivosUps;
    
    //Sps para reportes
    private DatabaseStoredProc spProyectosReporte;
    
    
    /**
     * @return the spGuardaProyecto
     */
    public DatabaseStoredProc getSpGuardaProyecto() {
        return spGuardaProyecto;
    }
    
    /**
     * @param spGuardaProyecto the spGuardaProyecto to set
     */
    public void setSpGuardaProyecto(DatabaseStoredProc spGuardaProyecto) {
        this.spGuardaProyecto = spGuardaProyecto;
    }

    public DatabaseStoredProc getSpProyectosReporte() {
        return spProyectosReporte;
    }

    public void setSpProyectosReporte(DatabaseStoredProc spProyectosReporte) {
        this.spProyectosReporte = spProyectosReporte;
    }
    
    @Override
    public String getProyecto(Integer clienteId, String claveProyecto,
            String nombreProyecto, Date fechaInicio, Date fechaFin) {
        
        // TODO Auto-generated method stub
        String perfilName = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", clienteId);
        inputs.put("Clave", claveProyecto);
        inputs.put("Nombre", nombreProyecto);
        inputs.put("FechaIni", fechaInicio);
        inputs.put("FechaFin", fechaFin);

        Map out = spProyectos.execute(inputs);

        out.get("#result-set-1");
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
                                Map record = (Map) arrValues.get(0);
                                if (record != null) {
                                    perfilName = (String) record.get("Nombre");
                                }
                            }

                        }

                    }

                }

            }

        } else {
            //System.out.println("out is null ");
        }
        return perfilName;
    }
    
    @Override
    public List<Proyectos> listaProyectos(Integer proyectoId, String claveProyecto, String descripcionProyecto,
            String nombreProyecto, Integer clienteId, Integer unidadNegocioId,
            Date fechaInicio, Date fechaFin, Date fechaVisible, boolean activo) {
        
        List<Proyectos> listado = new ArrayList<Proyectos>();
        Proyectos proyecto;
        
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("ProyectosId", proyectoId);
        inputs.put("Clave", claveProyecto);
        inputs.put("Descripcion", descripcionProyecto);
        inputs.put("Nombre", nombreProyecto);
        inputs.put("ClienteId", clienteId);
        inputs.put("UnidadesNegociosId", unidadNegocioId);
        inputs.put("FechaIni", fechaInicio);
        inputs.put("FechaFin", fechaFin);
        inputs.put("FechaVisible", fechaVisible);
        
        if(activo == true){
            inputs.put("Activo", 1);
        }else if(activo == false){
            inputs.put("Activo", 0);
        }else{
            inputs.put("Activo", activo);
        }
        Map out;
        
        try {
            out = spProyectos.execute(inputs);
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
                                            
                                            proyecto = new Proyectos();
                                            
                                            proyecto.setProyectoId((Integer) (record.get("ProyectosId") == null ? 0 : record.get("ProyectosId") ));
                                            proyecto.setClaveProyecto((String) (record.get("Clave") == null ? "" : record.get("Clave")));
                                            proyecto.setDescripcionProyecto((String) (record.get("Descripcion") == null ? "" : record.get("Descripcion")));
                                            proyecto.setNombreProyecto((String) (record.get("Nombre") == null ? "" : record.get("Nombre")));
                                            proyecto.setFechaInicio((Date) record.get("FechaIni"));
                                            proyecto.setFechaFin((Date) record.get("FechaFin"));
                                            proyecto.setFechaVisible((Date) record.get("FechaVisible"));
                                            Integer cantTiendas = (Integer) (record.get("Tiendas") == null ? 0 : record.get("Tiendas"));
                                            proyecto.setCantidadTiendas((new Long(cantTiendas)));
                                            proyecto.setClienteId(Integer.parseInt((record.get("ClientesId") == null ? 0 : record.get("ClientesId")).toString() ));
                                            proyecto.setArchivoLogo((String) (record.get("ArchivoLogo") == null ? "" : record.get("ArchivoLogo")));
                                            proyecto.setUnidadesNegociosId((Integer) (record.get("UnidadesNegociosId") == null ? 0 : record.get("UnidadesNegociosId")));
                                            
                                            proyecto.setStatus( ( record.get("Activo") == null || record.get("Activo").toString().equals("0") ) ? false : true );
                                            proyecto.setVisitaAutomatica(( record.get("VisitaAutomatica") == null || record.get("VisitaAutomatica").toString().equals("0") || record.get("VisitaAutomatica").toString().equals("false") ) ? false : true );
                                            //proyecto.setVisitaAutomatica((Boolean) record.get("VisitaAutomatica"));
                                            proyecto.setDiasVigencias(
                                                    Integer.parseInt(record.get("DiasVigencias") == null ? "0" : record.get("DiasVigencias").toString()
                                                    ) );
                                            
                                            
                                            listado.add(proyecto);
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

        // TODO Auto-generated method stub
        return listado;
    }
    
    @Override
    public List<Proyectos> listaProyectosReportes(Integer proyectoId, String claveProyecto, String descripcionProyecto,
            String nombreProyecto, Integer clienteId, Integer unidadNegocioId,
            Date fechaInicio, Date fechaFin, Date fechaVisible, boolean activo) {
        
        List<Proyectos> listado = new ArrayList<Proyectos>();
        Proyectos proyecto;
        
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("ProyectosId", proyectoId);
        inputs.put("Clave", claveProyecto);
        inputs.put("Descripcion", descripcionProyecto);
        inputs.put("Nombre", nombreProyecto);
        inputs.put("ClienteId", clienteId);
        inputs.put("UnidadesNegociosId", unidadNegocioId);
        inputs.put("FechaIni", fechaInicio);
        inputs.put("FechaFin", fechaFin);
        inputs.put("FechaVisible", fechaVisible);
        
        if(activo == true){
            inputs.put("Activo", 1);
        }else if(activo == false){
            inputs.put("Activo", 0);
        }else{
            inputs.put("Activo", activo);
        }
        
        Map out = spProyectosReporte.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                proyecto = new Proyectos();
                
                proyecto.setProyectoId((Integer) (element.get("ProyectosId") == null ? 0 : element.get("ProyectosId") ));
                proyecto.setClaveProyecto((String) (element.get("Clave") == null ? "" : element.get("Clave")));
                proyecto.setDescripcionProyecto((String) (element.get("Descripcion") == null ? "" : element.get("Descripcion")));
                proyecto.setNombreProyecto((String) (element.get("Nombre") == null ? "" : element.get("Nombre")));
                
                proyecto.setFechaInicio((Date) element.get("FechaIni"));
                proyecto.setFechaFin((Date) element.get("FechaFin"));
                proyecto.setFechaVisible((Date) element.get("FechaVisible"));
                Integer cantTiendas = (Integer) (element.get("Tiendas") == null ? 0 : element.get("Tiendas"));
                proyecto.setCantidadTiendas((new Long(cantTiendas)));
                proyecto.setClienteId(Integer.parseInt((element.get("ClientesId") == null ? 0 : element.get("ClientesId")).toString() ));
                proyecto.setArchivoLogo((String) (element.get("ArchivoLogo") == null ? "" : element.get("ArchivoLogo")));
                proyecto.setUnidadesNegociosId((Integer) (element.get("UnidadesNegociosId") == null ? 0 : element.get("UnidadesNegociosId")));
                
                proyecto.setStatus( ( element.get("Activo") == null || element.get("Activo").toString().equals("0") ) ? false : true );
                proyecto.setVisitaAutomatica(( element.get("VisitaAutomatica") == null || element.get("VisitaAutomatica").toString().equals("0") || element.get("VisitaAutomatica").toString().equals("false") ) ? false : true );
                //proyecto.setVisitaAutomatica((Boolean) record.get("VisitaAutomatica"));
                proyecto.setDiasVigencias(
                        Integer.parseInt(element.get("DiasVigencias") == null ? "0" : element.get("DiasVigencias").toString()
                        ) );
                listado.add(proyecto);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        
        return listado;
    }
    
    @Override
    public Integer guardaProyecto(int idProyecto, String claveProyecto, String nombreProyecto,
            String descripcionProyecto, Date fechaVisible, Date fechaInicio,
            Date fechaFin, int status, Integer unidadNegocioId, int visitaAutomatica, Integer clienteId, String imagen,Integer diasVigencias) {

        Integer insertado = 0;
        //FileInputStream fis = null;
        //File miImagen = new File(imagen);

        //String ruta = miImagen.getPath();
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ProyectosId", idProyecto);
        inputs.put("Clave", claveProyecto);
        inputs.put("Nombre", nombreProyecto);
        inputs.put("Descripcion", descripcionProyecto);
        inputs.put("FechaVisible", fechaVisible);
        inputs.put("FechaIni", fechaInicio);
        inputs.put("FechaFin", fechaFin);
        inputs.put("Activo", status);
        inputs.put("UnidadesNegociosId", unidadNegocioId);
        inputs.put("VisitaAutomatica", visitaAutomatica);
        inputs.put("ClienteId", clienteId);
        inputs.put("ArchivoLogo",imagen);
        inputs.put("DiasVigencias",diasVigencias);
        Map out;
        try {
            
            out = spGuardaProyecto.execute(inputs);
            
            if (out.size() < 0) {
                // insertado = false;
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

                                        BigDecimal miNum = (BigDecimal) record.get("InsertedID");
                                        if(miNum == null){
                                            insertado = 0;
                                        }else{
                                            insertado = miNum.intValue();
                                        }
                                    }

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

        return insertado;
    }

    @Override
    public Proyectos proyectoEditar(Integer proyectoId) {

        Proyectos proyecto = new Proyectos();

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("ProyectosId", proyectoId);

        DateFormat formatter = null;
        Date convertedDate = null;

        Map out;
        try {
            out = spTraeProyecto.execute(inputs);
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
                                            try {
                                                proyecto.setProyectoId((Integer) record.get("ProyectosId"));
                                            } catch (NullPointerException npe) {
                                                proyecto.setProyectoId(0);
                                            }
                                            try {
                                                proyecto.setClaveProyecto((String) record.get("Clave"));
                                            } catch (NullPointerException npe) {
                                                proyecto.setClaveProyecto("");
                                            }
                                            try {
                                                proyecto.setDescripcionProyecto((String) record.get("Descripcion"));
                                            } catch (NullPointerException npe) {
                                                proyecto.setDescripcionProyecto("");
                                            }
                                            try {
                                                proyecto.setNombreProyecto((String) record.get("Nombre"));
                                            } catch (NullPointerException npe) {
                                                proyecto.setNombreProyecto("");
                                            }
                                            try {
                                                proyecto.setArchivoLogo((String) record.get("ArchivoLogo"));
                                            } catch (NullPointerException npe) {
                                                proyecto.setArchivoLogo("");
                                            }
                                            try {
                                                proyecto.setFechaInicio((Date) record.get("FechaIni"));
                                            } catch (NullPointerException npe) {
                                                String ddMMyyyy = "00000000";
                                                formatter = new SimpleDateFormat("ddMMyyyy");
                                                convertedDate = (Date) formatter.parse(ddMMyyyy);

                                                proyecto.setFechaInicio(convertedDate);
                                            }
                                            try {
                                                proyecto.setFechaFin((Date) record.get("FechaFin"));
                                            } catch (NullPointerException npe) {
                                                String ddMMyyyy = "00000000";
                                                formatter = new SimpleDateFormat("ddMMyyyy");
                                                convertedDate = (Date) formatter.parse(ddMMyyyy);

                                                proyecto.setFechaInicio(convertedDate);
                                            }
                                            try {
                                                proyecto.setFechaVisible((Date) record.get("FechaVisible"));
                                            } catch (NullPointerException npe) {
                                                String ddMMyyyy = "00000000";
                                                formatter = new SimpleDateFormat("ddMMyyyy");
                                                convertedDate = (Date) formatter.parse(ddMMyyyy);

                                                proyecto.setFechaInicio(convertedDate);
                                            }
                                            try {
                                                Integer miCantidad = (Integer) record.get("Tiendas");
                                                proyecto.setCantidadTiendas(new Long(miCantidad));
                                            } catch (NullPointerException npe) {
                                                proyecto.setCantidadTiendas(new Long(0));
                                            }
                                            try {
                                                Integer miCantidad = (Integer) record.get("DiasVigencias");
                                                proyecto.setDiasVigencias(miCantidad);
                                            } catch (NullPointerException npe) {
                                                proyecto.setDiasVigencias(0);
                                            }
                                            //proyecto.setListaEncuestasProyecto(this.listaEncuestasProyecto(proyecto.getProyectoId()));
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
        return proyecto;
    }
    
    @Override
    public List<Encuestas> listaEncuestasProyecto(Integer inidadesNegociosID, Integer proyectoId) {

        List<Encuestas> listado = new ArrayList<Encuestas>();
        Encuestas encuesta;

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("UnidadesNegociosID", inidadesNegociosID);//cambiar unidad de negocio
        inputs.put("ProyectosId", proyectoId);

        Map out;
        try {
            out = spTraeListaEncuestasProyecto.execute(inputs);
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

                                            encuesta = new Encuestas();

                                            encuesta.setEncuestasId((Integer) record.get("EncuestasId"));
                                            encuesta.setNombreEncuesta((String) record.get("Nombre"));
                                            listado.add(encuesta);
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

        // TODO Auto-generated method stub
        return listado;

    }
    
    @Override
    public String agregaEncuesta(int proyectoId, int encuestaId) {
        //boolean insertado = false;
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", encuestaId);
        inputs.put("ProyectosId", proyectoId);
        
        System.out.println("agregaEncuesta inputs:"+inputs);
        Map out;
        out = spAgregaEncuestaProyecto.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
            Utileria.logger(getClass()).info("agregaEncuesta - resultado:"+resultado);
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;

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

    /**
     * @return the spTraeProyecto
     */
    public DatabaseStoredProc getSpTraeProyecto() {
        return spTraeProyecto;
    }

    /**
     * @param spTraeProyecto the spTraeProyecto to set
     */
    public void setSpTraeProyecto(DatabaseStoredProc spTraeProyecto) {
        this.spTraeProyecto = spTraeProyecto;
    }
    
    /**
     * @return the spAgregaEncuestaProyecto
     */
    public DatabaseStoredProc getSpAgregaEncuestaProyecto() {
        return spAgregaEncuestaProyecto;
    }

    /**
     * @param spAgregaEncuestaProyecto the spAgregaEncuestaProyecto to set
     */
    public void setSpAgregaEncuestaProyecto(DatabaseStoredProc spAgregaEncuestaProyecto) {
        this.spAgregaEncuestaProyecto = spAgregaEncuestaProyecto;
    }

    @Override
    public String eliminaProyecto(Integer proyectoId) {
        String resultado = "";
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ProyectosId", proyectoId);
        Map out;
        
        System.out.println("eliminaProyecto inputs:"+inputs);
        out = spEliminaProyecto.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
            Utileria.logger(getClass()).info("eliminaProyecto - resultado:"+resultado);
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }

    /**
     * @return the spEliminaProyecto
     */
    public DatabaseStoredProc getSpEliminaProyecto() {
        return spEliminaProyecto;
    }

    /**
     * @param spEliminaProyecto the spEliminaProyecto to set
     */
    public void setSpEliminaProyecto(DatabaseStoredProc spEliminaProyecto) {
        this.spEliminaProyecto = spEliminaProyecto;
    }

    @Override
    public String eliminaEncuestaProyecto(int proyectoId, int encuestaId) {
        
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("EncuestasId", encuestaId);
        inputs.put("ProyectosId", proyectoId);

        Map out;
        System.out.println("eliminaEncuestaProyecto inputs:"+inputs);
        out = spEliminaEncuestaProyecto.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
            Utileria.logger(getClass()).info("eliminaEncuestaProyecto - resultado:"+resultado);
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public List<Encuestas> listaEncuestas(Integer idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, Integer statusId, boolean esPlantilla, boolean activa, Date fechaCreacionEncuesta, String observacionEncuesta, Integer proyectoId, Integer unidadesNegocioEncuestaId){

        List<Encuestas> listaEncuestas = new ArrayList<Encuestas>();

        Encuestas encuestas;

        Map<String, Object> inputs = new TreeMap<>();

        inputs.put("EncuestasId", idEncuesta);
        inputs.put("Nombre", nombreEncuesta);
        inputs.put("Clave", claveEncuesta);
        inputs.put("GPS", gpsEncuesta);
        inputs.put("StatusId", null);
        inputs.put("StatusId", statusId);
        inputs.put("Plantilla", esPlantilla);
        inputs.put("Activa", activa);
        inputs.put("FechaCreacion", fechaCreacionEncuesta);
        inputs.put("Observaciones", observacionEncuesta);
        inputs.put("ProyectosID", proyectoId);
        inputs.put("UnidadesNegociosId", unidadesNegocioEncuestaId);
        System.out.println("listaEncuestas inputs:"+inputs);
        Map out;

        try {
            out = spEncuestas.execute(inputs);
            if (out != null) {
                System.out.println("out size :" + out.size());
                
                int tamanio = out.size();
                if (tamanio == 0 || tamanio == -1) {
                } else {

                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    while (it.hasNext()) {
                        key = (String) it.next();
                        System.out.println(key);
                        val = out.get(key);
                        
                        if (val.equals("")) {
                        } else {
                            System.out.println(val);
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
                                            try{
                                                encuestas.setEstatus((String)record.get("Estatus"));
                                            }catch(NullPointerException npe){
                                                encuestas.setEstatus("");
                                            }
                                            try{
                                                encuestas.setTipo((String)record.get("Tipo"));
                                            }catch(NullPointerException npe){
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
                System.out.println("out is null ");
            }
        } catch (Exception se) {
            se.printStackTrace();
            System.out.println(se.getMessage());
        }
        return listaEncuestas;
    }
    
    //private DatabaseStoredProc spArchivosDel;
    //private DatabaseStoredProc spArchivosSel;
    //private DatabaseStoredProc spArchivosUps;
    @Override
    public List<Archivos> getListaArchivos(String archivosOID, Integer proyectosId){
        List<Map<String, Object>> lstResult=null;
        List<Archivos> listado = new ArrayList<Archivos>();
        Archivos archivo;
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ArchivosOID", archivosOID);
        inputs.put("ProyectosId", proyectosId);
        
        System.out.println("getListaArchivos inputs:"+inputs);
        lstResult=spArchivosSel.execSP(inputs);
        if(lstResult!=null){
            for(Map m : lstResult){
                
                archivo = new Archivos();
                archivo.setArchivosOID(m.get("ArchivosOID").toString());
                archivo.setNombre(m.get("Nombre").toString());
                archivo.setFecha(DateHelper.convertirStringToDate(m.get("Fecha").toString()));
                archivo.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                archivo.setActivo((m.get("Activo") != null || !m.get("Activo").toString().endsWith("") || !m.get("Activo").toString().equalsIgnoreCase("false") ? false : true ));
                
                listado.add(archivo);
            }
            
            System.out.println("out size :"+lstResult.size());
            //spCadena.execSP(inputs)
        }else{
            System.out.println("out is null ");
        }
        
        return listado;
    }
    
    @Override
    public void eliminaArchivoProyecto(String archivosOID){
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ArchivosOID", archivosOID);
        Map out;
        System.out.println("eliminaArchivoProyecto inputs:"+inputs);
        try {
            out = spArchivosDel.execute(inputs);
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }
    
    @Override
    public String agregaArchivoProyecto(Archivos archivo) {
        //boolean insertado = false;
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        
        inputs.put("ArchivosOID", archivo.getArchivosOID());
        inputs.put("Nombre", archivo.getNombre());
        inputs.put("ProyectosId", archivo.getProyectosId());
        inputs.put("Fecha", archivo.getFecha());
        inputs.put("Activo", archivo.isActivo() == false ? 0 : 1 );
        
        System.out.println("agregaArchivoProyecto inputs:"+inputs);
        Map out;
        out = spArchivosUps.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("InsertedID").toString();
            }
            Utileria.logger(getClass()).info("agregaArchivoProyecto - resultado:"+resultado);
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }
    
    /**
     * @return the spEliminaEncuestaProyecto
     */
    public DatabaseStoredProc getSpEliminaEncuestaProyecto() {
        return spEliminaEncuestaProyecto;
    }

    /**
     * @param spEliminaEncuestaProyecto the spEliminaEncuestaProyecto to set
     */
    public void setSpEliminaEncuestaProyecto(DatabaseStoredProc spEliminaEncuestaProyecto) {
        this.spEliminaEncuestaProyecto = spEliminaEncuestaProyecto;
    }

    public DatabaseStoredProc getSpTraeListaEncuestasProyecto() {
        return spTraeListaEncuestasProyecto;
    }

    public void setSpTraeListaEncuestasProyecto(DatabaseStoredProc spTraeListaEncuestasProyecto) {
        this.spTraeListaEncuestasProyecto = spTraeListaEncuestasProyecto;
    }

    public DatabaseStoredProc getSpEncuestas() {
        return spEncuestas;
    }

    public void setSpEncuestas(DatabaseStoredProc spEncuestas) {
        this.spEncuestas = spEncuestas;
    }
    
    public DatabaseStoredProc getSpProyectos() {
        return spProyectos;
    }

    public void setSpProyectos(DatabaseStoredProc spProyectos) {
        this.spProyectos = spProyectos;
    }

    public DatabaseStoredProc getSpArchivosDel() {
        return spArchivosDel;
    }

    public void setSpArchivosDel(DatabaseStoredProc spArchivosDel) {
        this.spArchivosDel = spArchivosDel;
    }

    public DatabaseStoredProc getSpArchivosSel() {
        return spArchivosSel;
    }

    public void setSpArchivosSel(DatabaseStoredProc spArchivosSel) {
        this.spArchivosSel = spArchivosSel;
    }

    public DatabaseStoredProc getSpArchivosUps() {
        return spArchivosUps;
    }

    public void setSpArchivosUps(DatabaseStoredProc spArchivosUps) {
        this.spArchivosUps = spArchivosUps;
    }
    
    
    
}
