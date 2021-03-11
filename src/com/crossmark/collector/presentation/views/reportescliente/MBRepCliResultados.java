
package com.crossmark.collector.presentation.views.reportescliente;


import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServicePreguntas;
import com.crossmark.collector.business.services.ServiceRepCliResultados;
import com.crossmark.collector.business.services.ServiceSecciones;
import com.crossmark.collector.presentation.views.reportescliente.objects.EncuestasUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.EquiposRegion;
import com.crossmark.collector.presentation.views.reportescliente.objects.ProyectosUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.net.AccessReportingService;
import com.crossmark.collector.presentation.views.reportesoperativos.net.LoadProperties;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.StringHelper;
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
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 *
 * @author franciscom
 */
@ManagedBean
public class MBRepCliResultados implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ServiceRepCliResultados serviceRepCliResultados;
    private ServiceParametros serviceParametros;
    
    private ServiceSecciones serviceSecciones;
    private ServicePreguntas servicePreguntas;
    
    //private String sessionOID = "adecf5e8-ae1a-40c8-899d-905e02281fc9";
    private String sessionOID;// = "6529A115-3B4C-444B-83B2-0F94E5DC9FE3";
    //                          "6529a115-3b4c-444b-83b2-0f94e5dc9fe3"
    
    private Integer regionId;
    private Integer equipoId;
    private Integer territorioId;
    private Integer clienteId;
    
    private String usuariosOID;
    private Integer proyectoId;
    private Integer idEncuesta;
    private String seccionesOID;
    
    private Date fechaIni;
    private Date fechaFin;
    //Inician Variables para select de combo proyecto
    
    private Integer ciclica;
    Seccion seccion;
    //Termina Variables para el reporte de resultados
    private String reportResultados;
    List<RegionesUsuarios> listaRegiones;
    List<EquiposRegion> listaEquipos;
    List<TerritoriosUsuarios> equiposTerritorios;
    List<Clientes> listaClientes;
    List<Usuario> listaPromotores;
    List<ProyectosUsuarios> listaProyectos;
    List<EncuestasUsuarios> listaEncuestas;
    List<Seccion> listaSecciones;
    List<Parametros> listaParametros;
    
    private String formato;
    private StreamedContent file;
    
    public List<Parametros> getListaParametros(){
        listaParametros = serviceParametros.getParametrosReporting(null,null);
        return listaParametros;
    };
    
    @PostConstruct
    public void init(){
        UsuarioSession usSistema = null;
        
        usSistema = Utileria.getSessionAttribute("userLoged");
        if(usSistema != null){
            setSessionOID(usSistema.getOID());
            Utileria.logger(getClass()).info("usSistema.getOID():"+usSistema.getOID());
        }
    }
    
    public void cleanFilters(){
        Utileria.logger(getClass()).info("Reseteando los valores.");
        regionId = null;
        equipoId = null;
        territorioId = null;
        clienteId = null;
        proyectoId = null;
        idEncuesta = null;
        seccionesOID = null;
        fechaIni = null;
        fechaFin = null;
        
        listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        equiposTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaPromotores = new ArrayList();
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
    }
    
    
    public void inicializaListas(){
        
        getListaRegionesFind();
        equiposByRegion();
        getEquiposTerritoriosByEqAndTerr();
        promotoresListReporte();
        clientesList();
        proyectosList();
        encuestasList();
    }
    
    //Metodo para generar el reporte de resultados en html
    public void getStringHtmlResultados() throws FileNotFoundException, IOException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        
        if(regionId != 0){
            
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            
            List<Seccion> listaSeccionesTmp = serviceRepCliResultados.getSeccionesByParams(seccionesOID, null, idEncuesta, 0, 0, 0, null, null, 0);
            
            if(listaSeccionesTmp != null){
                seccion = listaSeccionesTmp.get(0);
                
                boolean esCiclica = seccion.isCiclica();
                
                List<Secciones> miSeccionEditar = serviceSecciones.listaSecciones(seccionesOID, "", 0, Short.parseShort("0"), true, false, false, null, false);
                miSeccionEditar.get(0).setMiListaPreguntas(servicePreguntas.listaPreguntas("", seccionesOID, false, 0));
                if (esCiclica && miSeccionEditar.get(0).getMiListaPreguntas().size() == 1)
                {
                    esCiclica = false;
                }
                
                parametros.put("rs:Format", "HTML4.0");
                if(esCiclica){
                    parametros.put("reportName", "ReporteResultadosClienteCiclica");
                }else{
                    parametros.put("reportName", "ReporteResultadosClientePlanos");
                }
                parametros.put("ClientesId", String.valueOf(clienteId));
                parametros.put("CadenasId",  String.valueOf(equipoId == null ? 0 : equipoId));
                parametros.put("TerritoriosId", String.valueOf(territorioId == null ? 0 : territorioId));
                parametros.put("RegionesId", String.valueOf(regionId));
                parametros.put("ProyectosId", String.valueOf(proyectoId));
                parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
                parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
                parametros.put("SeccionesOID", seccionesOID);
                parametros.put("UsuariosOID", sessionOID);
                parametros.put("UsuariosPromotorOID", usuariosOID);
                parametros.put("formato", "HTML4.0");
                
                listaParametros = serviceParametros.getParametrosReporting(null,null);
                AccessReportingService accrep = new AccessReportingService(listaParametros);
                InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
                
                if(rep != null){
                    reportResultados = StringHelper.readInputStreamAsString(rep);
                    String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();
                    //String servletImage = "http://localhost:8081/CRS-486/ReportingService";
                    //http://192.168.10.204:9492/CRS-486/
                    //ImageServeletProjectReporting
                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportResultados = reportResultados.replaceAll("%3a",":");
                    reportResultados = reportResultados.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    reportResultados = reportResultados.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    //RequestContext.getCurrentInstance().execute("PF('busReporteResultados').show()");
                    //RequestContext.getCurrentInstance().openDialog("CliResultados");
                }else{
                    reportResultados = "";
                }
                rep.close();
            }else{
                reportResultados = "";
            }
            
        }else{
            reportResultados = "";
        }
    }
    
    public void dowloadReporte() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        file = null;
        if(regionId != 0 && !formato.equals("HTML4.0")){
            
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            
            List<Seccion> listaSeccionesTmp = serviceRepCliResultados.getSeccionesByParams(seccionesOID, null, idEncuesta, 0, 0, 0, null, null, 0);
            if(listaSeccionesTmp != null){
                
                seccion = listaSeccionesTmp.get(0);
                
                boolean esCiclica = seccion.isCiclica();
                
                List<Secciones> miSeccionEditar = serviceSecciones.listaSecciones(seccionesOID, "", 0, Short.parseShort("0"), true, false, false, null, false);
                miSeccionEditar.get(0).setMiListaPreguntas(servicePreguntas.listaPreguntas("", seccionesOID, false, 0));
                if (esCiclica && miSeccionEditar.get(0).getMiListaPreguntas().size() == 1)
                {
                    esCiclica = false;
                }
                
                parametros.put("rs:Format", formato);
                if(esCiclica){
                    parametros.put("reportName", "ReporteResultadosClienteCiclica");
                }else{
                    parametros.put("reportName", "ReporteResultadosClientePlanos");
                }
                parametros.put("ClientesId", String.valueOf(clienteId));
                parametros.put("CadenasId",  String.valueOf(equipoId == null ? 0 : equipoId));
                parametros.put("TerritoriosId", String.valueOf(territorioId == null ? 0 : territorioId));
                parametros.put("RegionesId", String.valueOf(regionId));
                parametros.put("ProyectosId", String.valueOf(proyectoId));
                parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
                parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
                parametros.put("SeccionesOID", seccionesOID);
                parametros.put("UsuariosOID", sessionOID);
                parametros.put("UsuariosPromotorOID", usuariosOID);
                parametros.put("formato", formato);
                
                listaParametros = serviceParametros.getParametrosReporting(null,null);
                AccessReportingService accrep = new AccessReportingService(listaParametros);

                InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
                //byte[] buf = null;
                if(rep != null){
                    file = new DefaultStreamedContent(rep, StringHelper.getContentType(formato), parametros.get("reportName")+StringHelper.getTypeFile(formato));
                    //buf = StringHelper.getBytesFromInputStream(rep);
                }else{
                    Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),"No hay informaci&oacute;n.");
                    file = null;
                }
                /*
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(formato));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
                response.getOutputStream().write(buf);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                rep.close();
                context.responseComplete();
                */
            }
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }
        
    }
    //Termina reporte de Resultados
    
    public void promotoresListReporte(){
        
        if(usuariosOID == null || usuariosOID.equals("") || usuariosOID.equals("0")){
            usuariosOID = null;
        }
        /*
        String usuariosOID,String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String calle,
            String entreCalle,String numero,String colonia,String cP,String nroEmpleado,String password,Integer ciudadesId, 
            Integer estadosId,Integer territoriosId,Integer territorioNativoId,Integer perfilesId,Integer unidadesNegociosId,Integer equiposId, 
            Integer pertenece,Integer activo
                    */
        listaPromotores = serviceRepCliResultados.getUsuariosByParams(null, null, null, null,
                null, null, null, null, null, null, null, (territorioId == null || territorioId == 0) ? null : territorioId, 0, null, 
                0, 
                (equipoId == null || equipoId == 0) ? null : equipoId, 0, 1);
        
        //serviceRepCliResultados.getProyectosByParams(proyectoId, claveProyecto, null, nombreProyecto, clienteId, regionId, fechaIni, fechaFin, fechaIni, 0);
        
    }
    
    public void proyectosList(){
        /*
        private Date fechaIni;
        private Date fechaFin;
        */
        if(fechaIni != null || fechaFin != null ){
            //Alewrta por que las fechas estan en null
        }
        Utileria.logger(getClass()).info("proyectosList:");
        listaProyectos = serviceRepCliResultados.getListProyectosPorUsuario(sessionOID, 
                (clienteId == null || clienteId == 0) ? null : clienteId, 
                (regionId == null || regionId == 0) ? null : regionId, 
                (territorioId == null || territorioId == 0) ? null : territorioId, 
                (equipoId == null || equipoId == 0) ? null : equipoId);
        
    }
    
    public void encuestasList(){
        //int idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId, boolean esPlantilla, boolean activa, 
        //Date fechaCreacionEncuesta, String observacionEncuesta, int proyectoId, int unidadesNegocioEncuestaId
        //String usuariosOID,Integer clientesId,Integer regionesId, Integer territoriosId, Integer equiposId, Integer proyectosId
        listaEncuestas = serviceRepCliResultados.getEncuestasByParams(
                (sessionOID == null || sessionOID.equals("0")) ? null : sessionOID, 
               (clienteId == null || clienteId == 0) ? null : clienteId, 
                (regionId == null || regionId == 0) ? null : regionId,
                (territorioId == null || territorioId == 0) ? null : territorioId,
                (equipoId == null || equipoId == 0) ? null : equipoId,
                (proyectoId == null || proyectoId == 0) ? null : proyectoId);
        
        //.getEncuestasByParams(0, null, null, null, 1, false, true, null, null, proyectoId, 0);
        
    }
    
    public void seccionesList(){
        if(seccionesOID == null || seccionesOID.equals("") || seccionesOID.equals("0")){
            seccionesOID = null;
        }
        //String seccionesOID,String nombre,Integer encuestasId,Integer orden, Integer activa, Integer enabled,
        //Integer ciclica,Date fechaCreacion,Integer visible
        listaSecciones = serviceRepCliResultados.getSeccionesByParams(null, null, idEncuesta, null, 1, null, null, null, null);
        
    }
    
    public void getListaRegionesFind(){
        listaRegiones = serviceRepCliResultados.getListRegiones(sessionOID);
    }
    
    public void getEquiposTerritoriosByEqAndTerr(){
        
        byte pertenece = 1;
        equiposTerritorios = serviceRepCliResultados.getListaTerritoriosPorUsuarioYEq((sessionOID == null || sessionOID.equals("0")) ? null : sessionOID,
                (equipoId == null || equipoId == 0) ? null : equipoId,
                (territorioId == null || territorioId == 0) ? null : territorioId,
                pertenece);
    }
    
    public void equiposByRegion(){
        
        if(regionId == null || regionId != 0){
            listaEquipos = serviceRepCliResultados.getListEquiposPorRegion(sessionOID, 
                    regionId == null || regionId == 0 ? null : regionId);
        }
    }
    
    
    public void clientesList(){
        //clienteId, unidadesNegociosId, int equiposId, int territoriosId, String usuariosOID);//clienteId, 0, equipoId, territorioId, usuariosOID
        //listaClientes = serviceRepCliResultados.listaClientesByParams(0, 0, equipoId, territorioId, usuariosOID);//clienteId, 0, equipoId, territorioId, usuariosOID);
        //clientesId, unidadesNegociosId, equiposId, territoriosId, usuariosOID
        listaClientes = serviceRepCliResultados.listaClientesByParams(null, 
                null, 
                (equipoId == null || equipoId == 0) ? null : equipoId,
                (territorioId == null || territorioId == 0) ? null : territorioId, usuariosOID);//clienteId, 0, equipoId, territorioId, usuariosOID);
    }
    public void resetReportResultados(){
        reportResultados = "";
    }
    
    public void resetCampos(){
        /*
        listaRegiones = new ArrayList();
        listaEquipos = new ArrayList();
        equiposTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaPromotores = new ArrayList();
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        
        
        regionId = 0;
        equipoId = 0;
        territorioId = 0;
        clienteId = 0;
        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
        reportResultados = "";
        */
    }
    
    public void resetChangeProyectos(){
        listaEquipos = new ArrayList();
        equiposTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaPromotores = new ArrayList();
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        //equipoId = 0;
        equipoId = 0;
        territorioId = 0;
        clienteId = 0;
        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
    }
    
    public void resetChangeEquipos(){
        equiposTerritorios = new ArrayList();
        listaClientes = new ArrayList();
        listaPromotores = new ArrayList();
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        
        territorioId = 0;
        clienteId = 0;
        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
    }
    
    public void resetChangeEquiposTerritorios(){
        listaClientes = new ArrayList();
        listaPromotores = new ArrayList();
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        
        clienteId = 0;
        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
    }
    
    public void resetChangePromotor(){
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        
        idEncuesta = 0;
        seccionesOID = null;
    }
    
    public void resetChangeClientes(){
        
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
    }
    
    public void resetChangueProyectos(){
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        //listClientes = new ArrayList();
        
        idEncuesta = 0;
        seccionesOID = null;
        //clienteId = 0;
    }
    
    public void resetChangueEncuestas(){
        listaSecciones = new ArrayList();
        seccionesOID = null;
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
    
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
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
    
    public String getReportResultados() {
        return reportResultados;
    }

    public void setReportResultados(String reportResultados) {
        this.reportResultados = reportResultados;
    }
    
    public String getUsuariosOID() {
        return usuariosOID;
    }

    public void setUsuariosOID(String promotorOID) {
        this.usuariosOID = promotorOID;
    }

    public Integer getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Integer proyectoId) {
        this.proyectoId = proyectoId;
    }
    
    public String getSeccionesOID() {
        return seccionesOID;
    }

    public void setSeccionesOID(String seccionesOID) {
        this.seccionesOID = seccionesOID;
    }
    
    public List<Usuario> getListaPromotores() {
        return listaPromotores;
    }

    public void setListaPromotores(List<Usuario> listaPromotores) {
        this.listaPromotores = listaPromotores;
    }

    public List<Seccion> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaSecciones(List<Seccion> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }
    
    public Integer getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }
    
    public Integer getCiclica() {
        return ciclica;
    }

    public void setCiclica(Integer ciclica) {
        this.ciclica = ciclica;
    }
    
    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
    
    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public ServiceRepCliResultados getServiceRepCliResultados() {
        return serviceRepCliResultados;
    }

    public void setServiceRepCliResultados(ServiceRepCliResultados serviceRepCliResultados) {
        this.serviceRepCliResultados = serviceRepCliResultados;
    }

    public String getSessionOID() {
        return sessionOID;
    }

    public void setSessionOID(String sessionOID) {
        this.sessionOID = sessionOID;
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

    public List<TerritoriosUsuarios> getEquiposTerritorios() {
        return equiposTerritorios;
    }

    public void setEquiposTerritorios(List<TerritoriosUsuarios> equiposTerritorios) {
        this.equiposTerritorios = equiposTerritorios;
    }

    public List<ProyectosUsuarios> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<ProyectosUsuarios> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<EncuestasUsuarios> getListaEncuestas() {
        return listaEncuestas;
    }

    public void setListaEncuestas(List<EncuestasUsuarios> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }
    
    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public ServiceSecciones getServiceSecciones() {
        return serviceSecciones;
    }

    public void setServiceSecciones(ServiceSecciones serviceSecciones) {
        this.serviceSecciones = serviceSecciones;
    }

    public ServicePreguntas getServicePreguntas() {
        return servicePreguntas;
    }

    public void setServicePreguntas(ServicePreguntas servicePreguntas) {
        this.servicePreguntas = servicePreguntas;
    }
    
    
    
}
