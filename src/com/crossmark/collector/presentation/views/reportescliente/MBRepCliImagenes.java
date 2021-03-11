/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportescliente;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServiceRepCliImagenes;
import com.crossmark.collector.presentation.views.reportescliente.objects.Cadenas;
import com.crossmark.collector.presentation.views.reportescliente.objects.EncuestasUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.UsuariosTiendas;
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
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Francisco Mora
 */
public class MBRepCliImagenes  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ServiceRepCliImagenes serviceRepCliImagenes;
    private ServiceParametros serviceParametros;
    
    //private String sessionOID = "adecf5e8-ae1a-40c8-899d-905e02281fc9";
    //private String sessionOID = "9FA558B2-A47C-4532-B064-29BFD812B99D";
    private String sessionOID;// = "6529a115-3b4c-444b-83b2-0f94e5dc9fe3";
    private Integer regionId;
    private Integer equipoId;
    private Integer territorioId;
    private Integer clienteId;
    private Integer cadenaId;
    private Integer tiendaId;
    private Integer proyectoId;
    //private String categoriaOId;
    private Date fechaIni;
    private Date fechaFin;
    //Inician Variables para select de combo proyecto
    private String ordenarpor;
    private String promotorOID;
    private String repImagenes;
    String formato;
    
    List<RegionesUsuarios> listaRegiones;
    List<EquiposRegion> listaEquipos;
    List<TerritoriosUsuarios> listaTerritorios;
    List<Cadenas> listaCadenas;
    List<Clientes> listaClientes;
    List<UsuariosTiendas> listaTiendas;
    List<ProyectosUsuarios> listaProyectos;
    //List<ImagenesCategoria> listaCategorias;
    List<Parametros> listaParametros;
    List<EncuestasUsuarios> listaOrdenarpor;
    private StreamedContent file;
    
    public List<Parametros> getListaParametros(){
        listaParametros = serviceParametros.getParametrosReporting(null,null);
        return listaParametros;
    }
    
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
        clientesList();
        getlistaCadenasCombo();
        tiendasList();
        proyectosList();
    }
    
    public void cleanFilters(){
        Utileria.logger(getClass()).info("Reseteando los valores.");
        regionId = null;
        equipoId = null;
        territorioId = null;
        clienteId = null;
        cadenaId = null;
        tiendaId = null;
        proyectoId = null;
        fechaIni = null;
        fechaFin = null;
        
        listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        listaTerritorios = new ArrayList();
        listaCadenas = new ArrayList();
        listaClientes = new ArrayList();
        listaTiendas = new ArrayList();
        listaProyectos = new ArrayList();
    }
    
    //Metodo para generar el reporte de resultados en html
    //Inicia codigo para reporte de Cumplimiento.
    public void getStringHtmlReporte() throws FileNotFoundException, IOException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        
        if(regionId != 0){
            
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            
            parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteCliImagenes");
            //Equipo, territorio, opcion
            
            parametros.put("UsuariosOID", sessionOID);
            parametros.put("UsuariosPromotorOID", null);
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            
            parametros.put("ClientesId",  String.valueOf(clienteId == null ? 0 : clienteId));
            parametros.put("RegionesId",  String.valueOf(regionId == null ? 0 : regionId));
            parametros.put("TerritoriosId",  String.valueOf(territorioId == null ? 0 : territorioId));
            parametros.put("EquiposId",  String.valueOf(equipoId == null ? 0 : equipoId));
            parametros.put("TiendasId",  String.valueOf(tiendaId == null ? 0 : tiendaId));
            parametros.put("CadenasId",  String.valueOf(cadenaId == null ? 0 : cadenaId));
            parametros.put("ProyectosId",  String.valueOf(proyectoId == null ? 0 : proyectoId));
            //parametros.put("CategoriasOId", String.valueOf(categoriaOId));
            parametros.put("SortBy", String.valueOf(ordenarpor));
            parametros.put("formato", "HTML4.0");
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            
            if(rep != null){
                repImagenes = StringHelper.readInputStreamAsString(rep);
                
                String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();
                repImagenes = repImagenes.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                    repImagenes = repImagenes.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    repImagenes = repImagenes.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    repImagenes = repImagenes.replaceAll("%3a",":");
                    repImagenes = repImagenes.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    repImagenes = repImagenes.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    //RequestContext.getCurrentInstance().execute("PF('busReporteImagenes').show()");
            }else{
                repImagenes = "";
            }
            rep.close();
        }else{
            repImagenes = "";
        }
        
    }
    
    public void dowloadReporte() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        file = null;
        if(regionId != 0 && formato != null && !formato.equals("HTML4.0") ){
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            //ReporteCumplimientoDetalle.rdl
            
            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteCliImagenes");
            //Equipo, territorio, opcion
            
            parametros.put("UsuariosOID", sessionOID);
            parametros.put("UsuariosPromotorOID", null);
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("ClientesId", clienteId == 0 ? null : String.valueOf(clienteId));
            parametros.put("RegionesId", regionId == 0 ? null : String.valueOf(regionId));
            parametros.put("TerritoriosId", territorioId == 0 ? null : String.valueOf(territorioId));
            parametros.put("EquiposId", equipoId == 0 ? null : String.valueOf(equipoId));
            parametros.put("TiendasId", tiendaId == 0 ? null : String.valueOf(tiendaId));
            parametros.put("CadenasId", cadenaId == 0 ? null : String.valueOf(cadenaId));
            parametros.put("ProyectosId", proyectoId == 0 ? null : String.valueOf(proyectoId));
            //parametros.put("CategoriasOId", String.valueOf(categoriaOId));
            parametros.put("SortBy", String.valueOf(ordenarpor));
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
        listaProyectos = serviceRepCliImagenes.getListProyectosPorUsuario(sessionOID, 
                (clienteId == null || clienteId == 0) ? null : clienteId, 
                (regionId == null || regionId == 0) ? null : regionId , 
                (territorioId == null || territorioId == 0) ? null : territorioId, 
                (equipoId == null || equipoId == 0) ? null : equipoId );
    }
    
    public void getListaRegionesFind(){
        listaRegiones = serviceRepCliImagenes.getListRegione(sessionOID);
    }
    
    public void getEquiposTerritoriosByEqAndTerr(){
        //String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece
        Utileria.logger(getClass()).info("Llego a proyectosList sessionOID:"+sessionOID+"  equipoId:"+equipoId+"  territorioId:"+territorioId);
        byte pertenece = 1;
        //if(equipoId != 0){
        listaTerritorios = serviceRepCliImagenes.getListaTerritoriosPorUsuarioYEq(sessionOID, 
                (equipoId == null || equipoId == 0) ? null : equipoId, 
                (territorioId == null || territorioId == 0) ? null : territorioId ,
                pertenece);
        //}
    }
    
    public void equiposByRegion(){
        Utileria.logger(getClass()).info("Llego a equiposByRegion equipoId:"+regionId);
        //if(regionId != 0){
        listaEquipos = serviceRepCliImagenes.getListEquiposPorRegion(sessionOID, 
                (regionId == null || regionId == 0) ? null : regionId );
        //}
    }
    
    public void getlistaCadenasCombo(){
        Utileria.logger(getClass()).info("Llego a getlistaCadenasCombo equipoId:");
        //cadenaId, nombre
        listaCadenas = serviceRepCliImagenes.getListCadenas(0, null);
    }
    
    public void clientesList(){
        Utileria.logger(getClass()).info("Llego a clientesList equipoId:"+equipoId+"  territorioId"+territorioId);
        //int clientesId, int unidadesNegociosId, int equiposId, int territoriosId, String usuariosOID
        listaClientes = serviceRepCliImagenes.getListaClientes(null, null, 
                (equipoId == null || equipoId == 0) ? null : equipoId, 
                (territorioId == null || territorioId == 0) ? null : territorioId , 
                sessionOID);
    }
    
    public void tiendasList(){
        Utileria.logger(getClass()).info("Llego a tiendasList equipoId:"+equipoId+"  territorioId"+territorioId);
        //String usuariosOID,Integer territoriosId,Integer unidadesNegociosId, Integer equiposId, Integer clientesId, Integer regionesId, Integer cadenasId
        listaTiendas = serviceRepCliImagenes.getListaTiendas(sessionOID, 
                (territorioId == null || territorioId == 0) ? null : territorioId , null, 
                (equipoId == null || equipoId == 0) ? null : equipoId, 
                (clienteId == null || clienteId == 0) ? null : clienteId, 
                (regionId == null || regionId == 0) ? null : regionId, 
                (cadenaId == null || cadenaId == 0) ? null : cadenaId );
    }
    /*
    public void categoriaList(){
        Utileria.logger(getClass()).info("Llego a encuestaList equipoId:"+equipoId+"  territorioId"+territorioId);
        //String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId, Integer proyectosId
        listaCategorias = serviceRepCliImagenes.getListImagenesCategoria();
    }
    */
    
    public void resetReportImagenes(){
        repImagenes = "";
    }
    
    public void resetCampos(){
        /*
        listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        listaTerritorios = new ArrayList();
        listaCadenas = new ArrayList();
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
        listaTiendas = new ArrayList();
        
        regionId = 0;
        equipoId = 0;
        territorioId = 0;
        cadenaId = 0;
        clienteId = 0;
        proyectoId = 0;
        tiendaId = 0;
        */
    }
    
    public void resetChangeRegiones(){
        
        //listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        listaTerritorios = new ArrayList();
        //listaCadenas = new ArrayList();
        listaClientes = new ArrayList();
        listaProyectos = new ArrayList();
        //listaCategorias = new ArrayList();
        
        //regionId = 0;
        equipoId = 0;
        territorioId = 0;
        //cadenaId = 0;
        clienteId = 0;
        proyectoId = 0;
        //categoriaOId = null;
    }
    
    public void resetChangeEquipos(){
        
        listaTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaTiendas = new ArrayList();
        listaProyectos = new ArrayList();
        //listaCategorias = new ArrayList();
        
        territorioId = 0;
        clienteId = 0;
        tiendaId = 0;
        proyectoId = 0;
        //categoriaOId = null;
    }
    
    public void resetChangeTerritorio(){
        
        listaClientes = new ArrayList();
        listaTiendas = new ArrayList();
        listaProyectos = new ArrayList();
        //listaCategorias = new ArrayList();
        
        clienteId = 0;
        tiendaId = 0;
        proyectoId = 0;
        //categoriaOId = null;
    }
    
    public void resetChangeCliente(){
        
        listaProyectos = new ArrayList();
        //listaCategorias = new ArrayList();
        
        proyectoId = 0;
        //categoriaOId = null;
    }
    
    public void resetChangeCadena(){
        
        listaTiendas = new ArrayList();
        
        tiendaId = 0;
    }
    /*
    public void resetChangeProyecto(){
        
        listaCategorias = new ArrayList();
        
        categoriaOId = null;
    }
    */
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getCadenaId() {
        return cadenaId;
    }

    public void setCadenaId(Integer cadenaId) {
        this.cadenaId = cadenaId;
    }

    public Integer getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Integer tiendaId) {
        this.tiendaId = tiendaId;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }
    
    public String getSessionOID() {
        return sessionOID;
    }

    public void setSessionOID(String sessionOID) {
        this.sessionOID = sessionOID;
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

    public List<UsuariosTiendas> getListaTiendas() {
        return listaTiendas;
    }

    public void setListaTiendas(List<UsuariosTiendas> listaTiendas) {
        this.listaTiendas = listaTiendas;
    }
    
    public List<ProyectosUsuarios> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<ProyectosUsuarios> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public String getOrdenarpor() {
        return ordenarpor;
    }

    public void setOrdenarpor(String ordenarpor) {
        this.ordenarpor = ordenarpor;
    }

    public String getRepImagenes() {
        return repImagenes;
    }

    public void setRepImagenes(String repImagenes) {
        this.repImagenes = repImagenes;
    }
/*
    public List<ImagenesCategoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<ImagenesCategoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    */
    public ServiceRepCliImagenes getServiceRepCliImagenes() {
        return serviceRepCliImagenes;
    }

    public void setServiceRepCliImagenes(ServiceRepCliImagenes serviceRepCliImagenes) {
        this.serviceRepCliImagenes = serviceRepCliImagenes;
    }
/*
    public String getCategoriaOId() {
        return categoriaOId;
    }

    public void setCategoriaOId(String categoriaOId) {
        this.categoriaOId = categoriaOId;
    }
*/
    public String getPromotorOID() {
        return promotorOID;
    }

    public void setPromotorOID(String promotorOID) {
        this.promotorOID = promotorOID;
    }

    public List<EncuestasUsuarios> getListaOrdenarpor() {
        return listaOrdenarpor;
    }

    public void setListaOrdenarpor(List<EncuestasUsuarios> listaOrdenarpor) {
        this.listaOrdenarpor = listaOrdenarpor;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
    
    
    
}
