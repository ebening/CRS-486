package com.crossmark.collector.presentation.views.reportescliente;


import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServiceRepCliCumplProyecto;
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
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;




/**
 *
 * @author franciscom
 */

@ManagedBean
public class MBRepCliCumplDetalleProyecto  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ServiceRepCliCumplProyecto serviceRepCliCumplProyecto;
    private ServiceParametros serviceParametros;
    
    private String region;
    private Integer equipo;
    private Integer territorio;
    private Integer cliente;
    private Integer cadena;
    private Integer proyecto;
    //private Integer encuesta;
    private Date fechai;
    private Date fechaf;
    private String format;
    private String formato;
    private Integer unidadNegocio;
    private String reportCumplDet;
    private List<Parametros> listaParametros;
    
    private String message;
    private String query;
    private StreamedContent file;
    
    public List<Parametros> getListaParametros(){
        listaParametros = serviceParametros.getParametrosReporting(null,null);
        return listaParametros;
    }
    
    
    public String navigateToRepCumplProyecto(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        
        renderView = "RepCliCumplProyecto?faces-redirect=true";
        
        return renderView;
    }
    
    //param.region, param.territorio,param.equipo, param.cliente,param.cadena,param.proyecto,param.encuesta, param.fechai,param.fechaf
    //String region, Integer territorio,Integer equipo, Integer cliente,Integer cadena,Integer proyecto,Integer encuesta, String fechai,String fechaf,String format
    public void getStringHtmlCumplimientoDetalle() throws FileNotFoundException, IOException{
    //public void getStringHtmlCumplimientoDetalle() throws FileNotFoundException, IOException{
        
        procesaQuery();
        //getQueryDecode();
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(region != null && !region.equals("0")){
            /*
            setRegion(region);
            setEquipo(equipo);
            setTerritorio(territorio);
            setCliente(cliente);
            setCadena(cadena);
            setProyecto(proyecto);
            setEncuesta(encuesta);
            setFechai(DateHelper.convertirStringToDate(fechai));
            setFechaf(DateHelper.convertirStringToDate(fechaf));
            setFormat(format);
            */
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
            
            if(format.toUpperCase().equals("HTML")){
                parametros.put("rs:Format", "HTML4.0");
            }else{
                parametros.put("rs:Format",format);
            }
            
            parametros.put("reportName", "ReporteCliCumplimientoProyectoDetalle");
            //parametros.put("rs:Format", "HTML4.0");
            parametros.put("EquiposId", String.valueOf(this.equipo));
            parametros.put("TerritoriosId", String.valueOf(this.territorio));
            parametros.put("RegionesId", String.valueOf(this.region));
            parametros.put("ClientesId", String.valueOf(this.cliente));
            parametros.put("CadenasId", String.valueOf(this.cadena));
            parametros.put("ProyectosId", String.valueOf(this.proyecto));
            parametros.put("UnidadesNegociosID", String.valueOf(this.unidadNegocio));
            parametros.put("FechaIni", DateHelper.convertirDateToString(this.fechai));
            parametros.put("FechaFin", DateHelper.convertirDateToString(this.fechaf));
            parametros.put("formato", this.format);
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            //String urlFile = accrep.sendGetReportingService(parametros);
            ////byte[] buf = accrep.sendGetReportingServiceByte(parametros);
            
            if(!this.format.toUpperCase().equals("HTML") && !this.format.toUpperCase().equals("HTML4.0")){
                
                byte[] buf = null;
                if(rep != null){
                    buf = StringHelper.getBytesFromInputStream(rep);
                }
                
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(format));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(this.format));
                response.getOutputStream().write(buf);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                
                context.responseComplete();
            }else{
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
            }
            if(rep != null ){
                rep.close();
            }
        }else{
            reportCumplDet = null;
        }
        
    }
    
    //Integer region, Integer territorio,Integer equipo, Integer cliente,Integer cadena,Integer proyecto,Integer encuesta, String fechai,String fechaf,String format
    public void navigateToRepRepCumplDetalleProyecto() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        
        this.file = null;
        if(region != null && !region.equals("0")){
            /*
            setRegion(region);
            setEquipo(equipo);
            setTerritorio(territorio);
            setCliente(cliente);
            setCadena(cadena);
            setProyecto(proyecto);
            setEncuesta(encuesta);
            setFechai(DateHelper.convertirStringToDate(fechai));
            setFechaf(DateHelper.convertirStringToDate(fechaf));
            setFormat(format);
            */
            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
            
            parametros.put("rs:Format", "EXCEL");
            parametros.put("reportName", "ReporteCliCumplimientoProyectoDetalle");
            //parametros.put("rs:Format", "HTML4.0");
            parametros.put("EquiposId", String.valueOf(this.equipo));
            parametros.put("TerritoriosId", String.valueOf(this.territorio));
            parametros.put("RegionesId", String.valueOf(this.region));
            parametros.put("ClientesId", String.valueOf(this.cliente));
            parametros.put("CadenasId", String.valueOf(this.cadena));
            parametros.put("ProyectosId", String.valueOf(this.proyecto));
            parametros.put("UnidadesNegociosID", String.valueOf(this.unidadNegocio));
            parametros.put("FechaIni", DateHelper.convertirDateToString(this.fechai));
            parametros.put("FechaFin", DateHelper.convertirDateToString(this.fechaf));
            parametros.put("formato", "EXCEL");
            
            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            //String urlFile = accrep.sendGetReportingService(parametros);
            ////byte[] buf = accrep.sendGetReportingServiceByte(parametros);
            
            if(rep != null){
                this.file = new DefaultStreamedContent(rep, "application/vnd.ms-excel", parametros.get("reportName")+StringHelper.getTypeFile("EXCEL"));
                /*
                byte[] buf = null;
                if(rep != null){
                    buf = StringHelper.getBytesFromInputStream(rep);
                }
                
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(format));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(this.format));
                response.getOutputStream().write(buf);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                
                context.responseComplete();
                */
            }
        }else{
            reportCumplDet = null;
        }
        //String query = "RepCliCumplDetalleProyecto.xhtml?faces-redirect=true&includeViewParams=true&region="+this.region+"&territorio="+this.territorio+"&equipo="+this.equipo+"&cliente="+this.cliente+"&cadena="+this.cadena+"&proyecto="+this.proyecto+"&encuesta="+this.encuesta+"&fechai="+this.fechai+"&fechaf="+this.fechaf+"&format="+this.format+"&formato="+this.format;
        //return query;
    }
    
    void procesaQuery(){
        
        Codificacion cs = new Codificacion();
        
        if(this.query != null){
            cs.proceso(this.query,true);
            
            Map<String,String> urlParameters = cs.obtenerParametros2(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                
                if(element.getKey().equals("region")){
                    this.setRegion(element.getValue());
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
                    this.setCliente(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("cadena")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setCadena(Integer.parseInt(element.getValue()));
                    }
                }
                
                if(element.getKey().equals("proyecto")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setProyecto(Integer.parseInt(element.getValue()));
                    }
                }
                if(element.getKey().equals("unidadNegocio")){
                    if(element.getValue() != null && !element.getValue().equals("")){
                        this.setUnidadNegocio(Integer.parseInt(element.getValue()));
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
    
    /*
    public static void main(String... arg){
        Codificacion cs = new Codificacion();
        String urlTmp = "114,101,103,105,111,110,61,49,38,116,101,114,114,105,116,111,114,105,111,61,50,38,101,113,117,105,112,111,61,49,38,99,108,105,101,110,116,101,61,50,38,99,97,100,101,110,97,61,38,112,114,111,121,101,99,116,111,61,55,50,38,101,110,99,117,101,115,116,97,61,48,38,102,101,99,104,97,105,61,50,48,48,54,45,48,50,45,48,50,38,102,101,99,104,97,102,61,50,48,50,52,45,48,50,45,50,50,38,102,111,114,109,97,116,61,72,84,77,76,52,46,48";
        cs.proceso(urlTmp,true);
    }
    */
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
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

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public Integer getCadena() {
        return cadena;
    }

    public void setCadena(Integer cadena) {
        this.cadena = cadena;
    }

    public Integer getProyecto() {
        return proyecto;
    }

    public void setProyecto(Integer proyecto) {
        this.proyecto = proyecto;
    }
/*
    public Integer getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Integer encuesta) {
        this.encuesta = encuesta;
    }
*/
    public ServiceRepCliCumplProyecto getServiceRepCliCumplProyecto() {
        return serviceRepCliCumplProyecto;
    }

    public void setServiceRepCliCumplProyecto(ServiceRepCliCumplProyecto serviceRepCliCumplProyecto) {
        this.serviceRepCliCumplProyecto = serviceRepCliCumplProyecto;
    }

    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }

    public String getFormato() {
        //Utileria.logger(getClass()).info("getFormato  RepCliCumplDetalleProyecto.xhtml?faces-redirect=true&includeViewParams=true&region="+region+"&territorio="+territorio+"&equipo="+equipo+"&cliente="+cliente+"&cadena="+cadena+"&proyecto="+proyecto+"&encuesta="+encuesta+"&fechai="+fechai+"&fechaf="+fechaf+"&format="+format+"&formato="+formato);
        return formato;
    }

    public void setFormato(String formato) {
        //Utileria.logger(getClass()).info("formato:"+formato);
        this.formato = formato;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
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

    public Integer getUnidadNegocio() {
        return unidadNegocio;
    }

    public void setUnidadNegocio(Integer unidadNegocio) {
        this.unidadNegocio = unidadNegocio;
    }
    
    
    
}
