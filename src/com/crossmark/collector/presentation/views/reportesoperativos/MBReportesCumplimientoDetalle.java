package com.crossmark.collector.presentation.views.reportesoperativos;

//import com.crossmark.collector.presentation.views.reportevisitas.*;
//import com.crossmark.collector.business.services.ServiceTiendas;
////import com.crossmark.collector.business.services.ServiceReportesOperativos;
//import com.crossmark.collector.business.services.ServiceTiendas;
////import com.crossmark.collector.presentation.views.visitas.objects.Cadena;
////import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
//import com.crossmark.collector.presentation.views.reportesoperativos.MBReportesOperativos;



import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServiceReportesOperativos;
import com.crossmark.collector.presentation.views.reportesoperativos.net.AccessReportingService;
import com.crossmark.collector.presentation.views.reportesoperativos.net.LoadProperties;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.StringHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;




/**
 *
 * @author franciscom
 */

@ManagedBean
public class MBReportesCumplimientoDetalle implements Serializable {
    
    private ServiceReportesOperativos serviceReportesOperativos;
    private ServiceParametros serviceParametros;
    
    private Integer region;
    private Integer equipo;
    private Integer territorio;
    private Integer cliente;
    private Date fechai;
    private Date fechaf;
    private String format;
    private String reportCumplDet;
    List<Parametros> listaParametros;
    
    private String message;
    
    private String query;
    private StreamedContent file;
    
    void procesaQuery(){
        
        Codificacion cs = new Codificacion();
        
        if(this.query != null){
            cs.proceso(this.query,true);
            
            Map<String,String> urlParameters = cs.obtenerParametros2(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                
                if(element.getKey().equals("region")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setRegion(Integer.valueOf(element.getValue()));
                    }
                }
                if(element.getKey().equals("territorio")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setTerritorio(Integer.valueOf(element.getValue()));
                    }
                }
                if(element.getKey().equals("equipo")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setEquipo(Integer.parseInt(element.getValue()));
                    }
                }
                
                if(element.getKey().equals("cliente")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setCliente(Integer.parseInt(element.getValue()));
                    }
                }
                
                if(element.getKey().equals("fechai")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setFechai(DateHelper.convertirStringToDate(element.getValue()));
                    }
                }
                if(element.getKey().equals("fechaf")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setFechaf(DateHelper.convertirStringToDate(element.getValue()));
                    }
                }
                if(element.getKey().equals("format")){
                    this.setFormat(element.getValue());
                }
                //region=1&territorio=2&equipo=1&cliente=2&cadena=&proyecto=72&encuesta=0&fechai=2006-02-02&fechaf=2024-02-22&format=HTML4.0
            }
        }
    }
    
    //Integer region, Integer territorio,Integer equipo,String fechai,String fechaf,String format
    public void getStringHtmlCumplimientoDetalle() throws FileNotFoundException, IOException{
    //public void getStringHtmlCumplimientoDetalle() throws FileNotFoundException, IOException{
        procesaQuery();
        FacesContext context = FacesContext.getCurrentInstance();
        
        /*setRegion(region);
        setEquipo(equipo);
        setTerritorio(territorio);
        setFechai(DateHelper.convertirStringToDate(fechai));
        setFechaf(DateHelper.convertirStringToDate(fechaf));
        setFormat(format);
        */
        
        if(this.region != null){
            
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
            if(format.toUpperCase().equals("EXCEL")){
                parametros.put("rs:Format", "EXCEL");
                parametros.put("formato", "EXCEL");
            }
            if(format.toUpperCase().equals("HTML")){
                parametros.put("rs:Format", "HTML4.0");
                parametros.put("formato", "HTML4.0");
            }
            //parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteCumplimientoDetalle");
            parametros.put("EquiposId", String.valueOf(this.equipo));
            parametros.put("TerritoriosId", String.valueOf(this.territorio));
            parametros.put("RegionesId", String.valueOf(this.region));
            parametros.put("ClientesId", String.valueOf(this.cliente));
            parametros.put("FechaIni", DateHelper.convertirDateToString(this.fechai));
            parametros.put("FechaFin", DateHelper.convertirDateToString(this.fechaf));
            
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            //String urlFile = accrep.sendGetReportingService(parametros);
            ////byte[] buf = accrep.sendGetReportingServiceByte(parametros);
                
                if(format.toUpperCase().equals("EXCEL")){
                    
                    byte[] buf = null;
                    if(rep != null){
                        buf = StringHelper.getBytesFromInputStream(rep);
                    }
                    
                    HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=ReporteCumplimientoDetalle.xls");
                    response.getOutputStream().write(buf);
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    
                    context.responseComplete();
                }
                if(format.toUpperCase().equals("HTML")){
                    if(rep != null){
                        reportCumplDet = StringHelper.readInputStreamAsString(rep);
                        
                        String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();
                        
                        reportCumplDet = reportCumplDet.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                        reportCumplDet = reportCumplDet.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                        reportCumplDet = reportCumplDet.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                        reportCumplDet = reportCumplDet.replaceAll("%3a",":");
                        reportCumplDet = reportCumplDet.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                        reportCumplDet = reportCumplDet.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    }else{
                        reportCumplDet = "";
                    }
                    ////setReportCumplDet(new String(buf));
                    
                }
            rep.close();
            //renderView = "ReporteCumplimiento";
        }else{
            reportCumplDet = null;
        }
        
    }
    
    public String navigateToRepOperativos(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        
        renderView = "ReportesOperativos?faces-redirect=true";
        
        return renderView;
    }
    
    public void navigateToRepRepCumplDetalleExcel() throws IOException{
        
        //String renderView = "ReporteCumplimientoDetalle.xhtml?faces-redirect=true&region="+region+"&territorio="+territorio+"&equipo="+equipo+"&fechai="+fechai+"&fechaf="+fechaf+"&format=excel";
        FacesContext context = FacesContext.getCurrentInstance();
        
        //return "ReporteCumplimientoDetalle.xhtml?faces-redirect=true&includeViewParams=true&region="+region+"&territorio="+territorio+"&equipo="+equipo+"&fechai="+fechai+"&fechaf="+fechaf+"&format=excel";
        this.file = null;
        if(this.region != null){
            
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
            parametros.put("rs:Format", "EXCEL");
            parametros.put("formato", "EXCEL");
            //parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteCumplimientoDetalle");
            parametros.put("EquiposId", String.valueOf(this.equipo));
            parametros.put("TerritoriosId", String.valueOf(this.territorio));
            parametros.put("RegionesId", String.valueOf(this.region));
            parametros.put("ClientesId", String.valueOf(this.cliente));
            parametros.put("FechaIni", DateHelper.convertirDateToString(this.fechai));
            parametros.put("FechaFin", DateHelper.convertirDateToString(this.fechaf));
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            //http://localhost:8081/CRS-486/faces/pages/reportesoperativos/ReporteCumplimientoDetalle.xhtml?query=114,101,103,105,111,110,61,49,38,116,101,114,114,105,116,111,114,105,111,61,50,38,101,113,117,105,112,111,61,49,38,102,101,99,104,97,105,61,50,48,48,54,45,48,50,45,48,50,38,102,101,99,104,97,102,61,50,48,50,51,45,48,50,45,50,51,38,102,111,114,109,97,116,61,104,116,109,108
            
            //InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/demo/images/optimus.jpg");
            this.file = new DefaultStreamedContent(rep, "application/vnd.ms-excel", "ReporteCumplimientoDetalle.xls");
            //String urlFile = accrep.sendGetReportingService(parametros);
            ////byte[] buf = accrep.sendGetReportingServiceByte(parametros);
                /*
                //if(format.toUpperCase().equals("EXCEL")){
                    
                    byte[] buf = null;
                    if(rep != null){
                        buf = StringHelper.getBytesFromInputStream(rep);
                    }
                    
                    HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=ReporteCumplimientoDetalle.xls");
                    response.getOutputStream().write(buf);
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    
                    context.responseComplete();
                //}
                
                rep.close();
                */
            //renderView = "ReporteCumplimiento";
        }else{
            reportCumplDet = null;
        }
        
    }
    
    
    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getEquipo() {
        return equipo;
    }

    public void setEquipo(Integer equipo) {
        this.equipo = equipo;
    }

    public Integer getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Integer territorio) {
        this.territorio = territorio;
    }

    public Date getFechai() {
        return fechai;
    }

    public void setFechai(Date fechai) {
        this.fechai = fechai;
    }

    public Date getFechaf() {
        return fechaf;
    }

    public void setFechaf(Date fechaf) {
        this.fechaf = fechaf;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReportCumplDet() {
        return reportCumplDet;
    }

    public void setReportCumplDet(String reportCumplDet) {
        this.reportCumplDet = reportCumplDet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceReportesOperativos getServiceReportesOperativos() {
        return serviceReportesOperativos;
    }

    public void setServiceReportesOperativos(ServiceReportesOperativos serviceReportesOperativos) {
        this.serviceReportesOperativos = serviceReportesOperativos;
    }

    public List<Parametros> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }
    
    
}
