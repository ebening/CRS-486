/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServiceRepCliCumplProyecto;
import com.crossmark.collector.presentation.views.reportescliente.objects.Cadenas;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.net.AccessReportingService;
import com.crossmark.collector.presentation.views.reportesoperativos.net.LoadProperties;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.presentation.views.utils.StringHelper;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Francisco Mora
 */
public class MBRepCliCumplProyecto  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ServiceRepCliCumplProyecto serviceRepCliCumplProyecto;
    private ServiceParametros serviceParametros;
    
    //private String sessionOID = "adecf5e8-ae1a-40c8-899d-905e02281fc9";
    private String sessionOID;// = "6529A115-3B4C-444B-83B2-0F94E5DC9FE3";
    //                          "6529a115-3b4c-444b-83b2-0f94e5dc9fe3"
    private Integer regionId;
    private Integer equipoId;
    private Integer territorioId;
    private Integer cadenaId;
    private Integer clienteId;
    private Integer proyectoId;
    private Date fechaIni;
    private Date fechaFin;
    
    //Inician Variables para select de combo proyecto
    
    private String repCumplProyecto;
    String formato;
    
    List<RegionesUsuarios> listaRegiones;
    List<EquiposRegion> listaEquipos;
    List<TerritoriosUsuarios> listaTerritorios;
    List<Cadenas> listaCadenas;
    List<Clientes> listaClientes;
    List<ProyectosUsuarios> listaProyectos;
    List<Parametros> listaParametros;
    private StreamedContent file;
    
    @PostConstruct
    public void init(){
        
        UsuarioSession usSistema = null;
        
        usSistema = Utileria.getSessionAttribute("userLoged");
        if(usSistema != null){
            setSessionOID(usSistema.getOID());
            Utileria.logger(getClass()).info("usSistema.getOID():"+usSistema.getOID());
        }
    }
    
    public void inicializaListas(){
        getListaRegionesFind();
        equiposByRegion();
        getEquiposTerritoriosByEqAndTerr();
        getlistaCadenasCombo();
    }
    
    public void cleanFilters(){
        Utileria.logger(getClass()).info("Reseteando los valores.");
        regionId = null;
        equipoId = null;
        territorioId = null;
        clienteId = null;
        cadenaId = null;
        proyectoId = null;
        fechaIni = null;
        fechaFin = null;
        
        listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        listaTerritorios = new ArrayList();
        listaCadenas = new ArrayList();
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
    }
    
    public List<Parametros> getListaParametros(){
        listaParametros = serviceParametros.getParametrosReporting(null,null);
        return listaParametros;
    };
    
    
    //Metodo para generar el reporte de resultados en html
    //Inicia codigo para reporte de Cumplimiento.
    public void getStringHtmlCumplimiento() throws FileNotFoundException, IOException {
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        
        if(regionId != 0){
            
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            
            parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteCliCumplimientoProyecto");
            
            
            parametros.put("UsuariosOID", sessionOID);
            parametros.put("RegionesId", String.valueOf(regionId));
            
            parametros.put("ClientesId",String.valueOf(clienteId == null ? 0 : clienteId));
            parametros.put("TerritoriosId",String.valueOf(territorioId == null ? 0 : territorioId));
            parametros.put("EquiposId",String.valueOf(equipoId == null ? 0 : equipoId));
            parametros.put("CadenasId",String.valueOf(cadenaId == null ? 0 : cadenaId));
            parametros.put("ProyectosId",String.valueOf(proyectoId == null ? 0 : proyectoId));
            
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("Detalle", "true");
            parametros.put("formato", "HTML4.0");
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            
            if(rep != null){
                repCumplProyecto = StringHelper.readInputStreamAsString(rep);
                //http://192.168.10.204:9492/CRS-486/
                String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();
                //String servletImage = "http://localhost:8081/CRS-486/"+LoadProperties.getRpReportImageServletUrl();
                repCumplProyecto = repCumplProyecto.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                    repCumplProyecto = repCumplProyecto.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    repCumplProyecto = repCumplProyecto.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    repCumplProyecto = repCumplProyecto.replaceAll("%3a",":");
                    repCumplProyecto = repCumplProyecto.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    repCumplProyecto = repCumplProyecto.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
            }else{
                repCumplProyecto = "";
            }
            
            //renderView = "ReporteCumplimiento";
            rep.close();
        }else{
            repCumplProyecto = "";
        }
        
        //return renderView;
    }
    
    public void dowloadReporte() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        file = null;
        if(regionId != 0 && formato != null && !formato.equals("HTML4.0") ){
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            //ReporteCumplimientoDetalle.rdl
            
            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteCliCumplimientoProyecto");
            
            parametros.put("UsuariosOID", sessionOID);
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("ClientesId",String.valueOf(clienteId == null ? 0 : clienteId));
            parametros.put("TerritoriosId",String.valueOf(territorioId == null ? 0 : territorioId));
            parametros.put("EquiposId",String.valueOf(equipoId == null ? 0 : equipoId));
            parametros.put("CadenasId",String.valueOf(cadenaId == null ? 0 : cadenaId));
            parametros.put("ProyectosId",String.valueOf(proyectoId == null ? 0 : proyectoId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("Detalle", "false");
            parametros.put("formato", formato);
            
            Utileria.logger(getClass()).info("equipoId:"+equipoId+"     territorioId:"+territorioId+"        regionId:"+regionId+"      fechaIni:"+fechaIni+"      fechaFin:"+fechaFin);
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = null;
            try {
                rep = accrep.sendGetReportingServiceInputStream(parametros);
                //byte[] buf = null;
                if(rep != null){
                    file = new DefaultStreamedContent(rep, StringHelper.getContentType(formato), parametros.get("reportName")+StringHelper.getTypeFile(formato));
                    //buf = StringHelper.getBytesFromInputStream(rep);
                }else{
                    Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),"No hay informaci&oacute;n.");
                    file = null;
                }
                /*
                //byte[] buf = accrep.sendGetReportingServiceByte(parametros);
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(formato));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
                response.getOutputStream().write(buf);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                */
            }catch (Exception e) {
                Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),e.getMessage());
                e.printStackTrace();
            } finally {
                //rep.close();
                context.responseComplete();
            }
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }
    }
    //Termina reporte de Cumplimiento.
    
    public void proyectosList(){
        //String usuariosOID,Integer territoriosId,Integer unidadesNegociosId, Integer equiposId, String nombreTienda, String nombreProyecto
        Utileria.logger(getClass()).info("Llego a proyectosList sessionOID:"+sessionOID+"  territorioId:"+territorioId+"  equipoId:"+equipoId);
        //String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId
        listaProyectos = serviceRepCliCumplProyecto.getListProyectosPorUsuario(sessionOID, 
                (clienteId == null || clienteId == 0) ? null : clienteId, 
                (regionId == null || regionId == 0) ? null : regionId , 
                (territorioId == null || territorioId == 0) ? null : territorioId, 
                (equipoId == null || equipoId == 0) ? null : equipoId );
        //sessionOID, territorioId, null, equipoId, null, null);
    }
    
    public void getListaRegionesFind(){
        listaRegiones = serviceRepCliCumplProyecto.getListRegione(sessionOID);
    }
    
    public void getEquiposTerritoriosByEqAndTerr(){
        //String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece
        Utileria.logger(getClass()).info("Llego a proyectosList sessionOID:"+sessionOID+"  equipoId:"+equipoId+"  territorioId:"+territorioId);
        byte pertenece = 1;
        //if(equipoId != 0){
        listaTerritorios = serviceRepCliCumplProyecto.getListaTerritoriosPorUsuarioYEq(sessionOID, 
                (equipoId == null || equipoId == 0) ? null : equipoId, 
                (territorioId == null || territorioId == 0) ? null : territorioId, pertenece);
        //}
    }
    
    public void equiposByRegion(){
        Utileria.logger(getClass()).info("Llego a equiposByRegion equipoId:"+regionId);
        //if(regionId != 0){
        listaEquipos = serviceRepCliCumplProyecto.getListEquiposPorRegion(sessionOID, 
                (regionId == null || regionId == 0) ? null : regionId );
        //}
    }
    
    public void getlistaCadenasCombo(){
        Utileria.logger(getClass()).info("Llego a getlistaCadenasCombo equipoId:");
        //cadenaId, nombre
        listaCadenas = serviceRepCliCumplProyecto.getListCadenas(0, null);
    }
    
    public void clientesList(){
        Utileria.logger(getClass()).info("Llego a equiposByRegion equipoId:"+equipoId+"  territorioId"+territorioId);
        //int clientesId, int unidadesNegociosId, int equiposId, int territoriosId, String usuariosOID
        listaClientes = serviceRepCliCumplProyecto.getListaClientes(null, null, 
                (regionId == null || regionId == 0) ? null : equipoId , 
                (territorioId == null || territorioId == 0) ? null : territorioId , sessionOID);
    }
    
    
    
    public void resetChangeRegiones(){
        
        //listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        listaTerritorios = new ArrayList();
        //listaCadenas = new ArrayList();
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
        
        //regionId = 0;
        equipoId = 0;
        territorioId = 0;
        //cadenaId = 0;
        clienteId = 0;
        proyectoId = 0;
    }
    
    public void resetChangeEquipos(){
        
        listaTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
        
        territorioId = 0;
        clienteId = 0;
        proyectoId = 0;
    }
    
    public void resetChangeTerritorio(){
        
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
        
        clienteId = 0;
        proyectoId = 0;
    }
    
    public void resetChangeCliente(){
        
        listaProyectos = new ArrayList();
        
        proyectoId = 0;
    }
    
    public ServiceRepCliCumplProyecto getServiceRepCliCumplProyecto() {
        return serviceRepCliCumplProyecto;
    }

    public void setServiceRepCliCumplProyecto(ServiceRepCliCumplProyecto serviceRepCliCumplProyecto) {
        this.serviceRepCliCumplProyecto = serviceRepCliCumplProyecto;
    }

    public String getSessionOID() {
        return sessionOID;
    }

    public void setSessionOID(String sessionOID) {
        this.sessionOID = sessionOID;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    public Integer getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(Integer territorioId) {
        this.territorioId = territorioId;
    }

    public Integer getCadenaId() {
        return cadenaId;
    }

    public void setCadenaId(Integer cadenaId) {
        this.cadenaId = cadenaId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }
    
    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getRepCumplProyecto() {
        return repCumplProyecto;
    }

    public void setRepCumplProyecto(String repCumplProyecto) {
        this.repCumplProyecto = repCumplProyecto;
    }

    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<RegionesUsuarios> getListaRegiones() {
        return listaRegiones;
    }

    public void setListaRegiones(List<RegionesUsuarios> listaRegiones) {
        this.listaRegiones = listaRegiones;
    }

    public List<EquiposRegion> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<EquiposRegion> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }
    
    public List<TerritoriosUsuarios> getListaTerritorios() {
        return listaTerritorios;
    }

    public void setListaTerritorios(List<TerritoriosUsuarios> listaTerritorios) {
        this.listaTerritorios = listaTerritorios;
    }

    public List<Cadenas> getListaCadenas() {
        return listaCadenas;
    }

    public void setListaCadenas(List<Cadenas> listaCadenas) {
        this.listaCadenas = listaCadenas;
    }

    public List<ProyectosUsuarios> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<ProyectosUsuarios> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }
    
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
    
    
}
