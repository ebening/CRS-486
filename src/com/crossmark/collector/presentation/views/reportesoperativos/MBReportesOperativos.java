package com.crossmark.collector.presentation.views.reportesoperativos;

import com.crossmark.collector.business.domain.Clientes;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServicePreguntas;
import com.crossmark.collector.business.services.ServiceReportesOperativos;
import com.crossmark.collector.business.services.ServiceSecciones;
import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.presentation.views.reportesoperativos.net.AccessReportingService;
import com.crossmark.collector.presentation.views.reportesoperativos.net.LoadProperties;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EquipoTerritorio;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Seccion;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author franciscom
 */

@ManagedBean
public class MBReportesOperativos implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Utileria.logger(MBReportesOperativos.class);

    private ServiceReportesOperativos serviceReportesOperativos;
    private ServiceParametros serviceParametros;
    private ServiceTerritorios serviceTerritorios;
    
    private ServiceSecciones serviceSecciones;
    private ServicePreguntas servicePreguntas;

    private int regionId;
    private int equipoId;
    private int territorioId;
    private int clienteId;
    private Date fechaIni;
    private Date fechaFin;

    //Inician Variables para select de combo proyecto
    private String usuariosOID;
    private int proyectoId;
    private int idEncuesta;
    private String seccionesOID;
    //Terminan Variables para select de combo proyecto

    private String claveProyecto;
    private String nombreProyecto;

    //Inicia Variables para el reporte de resultados
    private int clienteResId;
    private int ciclica;
    Equipo equipo;
    Region region;
    EquipoTerritorio territorio;
    Seccion seccion;
    //Termina Variables para el reporte de resultados
    private String reportDashboard;
    private String reportVisitas;
    private String reportCumplimiento;
    private String reportResultados;
    private String reportSolucionIncidencias;
    List<Proyectos> listaProyectos;
    List<EncuestasProyecto> listaEncuestas;
    List<String> listaEncuestasSeleccionadas;
    List<Usuario> listaPromotores;
    List<Seccion> listaSecciones;
    List<Clientes> listaClientes;
    List<Region> listaRegiones;
    List<Equipo> listaEquipos;
    List<EquipoTerritorio> equiposTerritorios;
    List<Parametros> listaParametros;
    private String message;
    private String formato;
    private String nombreTerritorio;
    private String nombreCliente;
    private StreamedContent file;
    private String reportEncuestas;
    private String stringEncuestas;

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }




    public MBReportesOperativos(){
        //listaRegiones = serviceReportesOperativos.getRegionesByFind(0);
    }

    public List<Parametros> getListaParametros(){
        return serviceParametros.getParametrosReporting(null,null);
    }

    public String navigateToRepOperativos(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();

        renderView = "ReportesOperativos?faces-redirect=true";

        return renderView;
    }


    public String navigateToRepCumplimiento(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();

        renderView = "ReporteCumplimiento?faces-redirect=true";

        return renderView;

    }

    public void getDashboard() throws FileNotFoundException, IOException, InterruptedException{

        FacesContext context = FacesContext.getCurrentInstance();

        //addValuesVariablesContext(context);
        LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

        //boolean validate = validateRepOperativos(context);
        //if(validate == true){
        //Esto esta ok, Descomentar p?ra hacer la peticion al servidor de reportes

        parametros.put("rs:Format", "HTML4.0");
        parametros.put("reportName", "Dashboard");
        parametros.put("ClientesId", String.valueOf(clienteId));
        parametros.put("EquiposId", String.valueOf(equipoId));
        parametros.put("TerritoriosId", String.valueOf(territorioId));
        parametros.put("RegionesId", String.valueOf(regionId));
        parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
        parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));

            listaParametros = serviceParametros.getParametrosReporting(null,null);
        AccessReportingService accrep = new AccessReportingService(listaParametros);
        InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);

            if(rep != null){
            reportDashboard = StringHelper.readInputStreamAsString(rep);
            LoadProperties lp = LoadProperties.loadProperties(listaParametros);

                String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();

                reportDashboard = reportDashboard.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                reportDashboard = reportDashboard.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportDashboard = reportDashboard.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportDashboard = reportDashboard.replaceAll("%3a",":");
                reportDashboard = reportDashboard.replaceAll("&rs:SessionID","&reportName=Dashboard&rs:SessionID");
                reportDashboard = reportDashboard.replaceAll("&rs%3ASessionID","&reportName=Dashboard&rs:SessionID");
            }else{
            reportDashboard = "";
        }
        //}else{
        //    reportDashboard = "";
        //}

    }


    public String getStringHtmlVisitas() throws FileNotFoundException, IOException {
        String renderView = "";

        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);

        if(regionId != 0){

            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
            parametros.put("formato", "HTML4.0");
            parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteVisitas");
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));

            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);

            if(rep != null){
                LoadProperties lp = LoadProperties.loadProperties(listaParametros);
                String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();

                reportVisitas = StringHelper.readInputStreamAsString(rep);
                reportVisitas = reportVisitas.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                reportVisitas = reportVisitas.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportVisitas = reportVisitas.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportVisitas = reportVisitas.replaceAll("%3a",":");
                reportVisitas = reportVisitas.replaceAll("&rs:SessionID","&reportName=ReporteVisitas&rs:SessionID");
                reportVisitas = reportVisitas.replaceAll("&rs%3ASessionID","&reportName=ReporteVisitas&rs:SessionID");

            }else{
                reportVisitas = "";
            }

            renderView = "ReporteVisitas?faces-redirect=true";

            if (rep != null) {
                rep.close();
            }
        } else {
            reportVisitas = "";
        }

        return renderView;

    }

    public void dowloadVisitasExcel() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        if(regionId != 0 && !formato.equals("HTML4.0")){
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteVisitas");
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("formato", formato);

            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            byte[] buf = null;
            if(rep != null){
                buf = StringHelper.getBytesFromInputStream(rep);
            }

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setContentType(StringHelper.getContentType(formato));
            response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            if (rep != null) {
                rep.close();
            }

            context.responseComplete();
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }
    }


    //Inicia codigo para reporte de resultados
    //Metodo para obtener la informacion para los p:outputLabel del reporte de resultados.
    public String getInfoResultados(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        /*
         FacesMessage msg = new FacesMessage("Prueba de mensaje ", this.getNombreProyecto());
         FacesContext.getCurrentInstance().addMessage(null, msg);
         */
        if(regionId != 0){
            setRegion(serviceReportesOperativos.getRegionById(regionId));
        }
        if(equipoId != 0){
            setEquipo(serviceReportesOperativos.getEquipoId(equipoId));
        }

        if(territorioId != 0){
            nombreTerritorio = serviceTerritorios.nombreTerritorio(territorioId);
        }

        renderView = "ReporteResultados?faces-redirect=true";
        //renderView = "ReportesOperativos";
        reportResultados = "";

        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;

        return renderView;
    }



    //Metodo para generar el reporte de resultados en html
    public void getStringHtmlResultados() throws FileNotFoundException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);

        if(regionId != 0){

            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            List<Seccion> listaSeccionesTmp = serviceReportesOperativos.getSeccionesByParams(seccionesOID, null, idEncuesta, 0, 0, 0, null, null, 0);

            if(listaSeccionesTmp != null){
                seccion = listaSeccionesTmp.get(0);
                
                boolean esCiclica = seccion.isCiclica();
                
                List<Secciones> miSeccionEditar = serviceSecciones.listaSecciones(seccionesOID, "", 0, Short.parseShort("0"), true, false, false, null, false);
                miSeccionEditar.get(0).setMiListaPreguntas(servicePreguntas.listaPreguntas("", seccionesOID, false, 0));
                if (esCiclica && miSeccionEditar.get(0).getMiListaPreguntas().size() == 1)
                {
                    esCiclica = false;
                }
                
                parametros.put("formato", "HTML4.0");
                parametros.put("rs:Format", "HTML4.0");
                if(esCiclica){
                    parametros.put("reportName", "ReporteResultadosCiclica");
                }else{
                    parametros.put("reportName", "ReporteResultadosPlana");
                }
                parametros.put("ClientesId", String.valueOf(clienteId));
                parametros.put("EquiposId", String.valueOf(equipoId));
                parametros.put("TerritoriosId", String.valueOf(territorioId));
                parametros.put("RegionesId", String.valueOf(regionId));
                parametros.put("ProyectosId", String.valueOf(proyectoId));
                parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
                parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
                parametros.put("SeccionesOID", seccionesOID);
                parametros.put("UsuariosOID", usuariosOID);

                listaParametros = serviceParametros.getParametrosReporting(null,null);
                AccessReportingService accrep = new AccessReportingService(listaParametros);
                InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);

                if(rep != null){
                    String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();

                    reportResultados = StringHelper.readInputStreamAsString(rep);

                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportResultados = reportResultados.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportResultados = reportResultados.replaceAll("%3a",":");
                    reportResultados = reportResultados.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    reportResultados = reportResultados.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                }else{
                    reportResultados = "";
                }
                if (rep != null) {
                    rep.close();
                }
            } else {
                reportResultados = "";
            }

        }else{
            reportResultados = "";
        }
    }

    //Inicia codigo para reporte de resultados
    //Metodo para obtener la informacion para los p:outputLabel del reporte de resultados.
    public String getInfoEncuestas(){
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        /*
        FacesMessage msg = new FacesMessage("Prueba de mensaje ", this.getNombreProyecto());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        */
        if(regionId != 0){
            setRegion(serviceReportesOperativos.getRegionById(regionId));
        }
        if(equipoId != 0){
            setEquipo(serviceReportesOperativos.getEquipoId(equipoId));
        }

        if(territorioId != 0){
            nombreTerritorio = serviceTerritorios.nombreTerritorio(territorioId);
        }

        renderView = "ReporteEncuesta?faces-redirect=true";
        //renderView = "ReportesOperativos";
        reportEncuestas = "";

        usuariosOID = null;
        proyectoId = 0;
        idEncuesta = 0;

        return renderView;
    }

    //Metodo para generar el reporte de resultados en html
    public void getStringHtmlEncuestas() throws FileNotFoundException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        
        stringEncuestas = "";
        
        for(String ep : listaEncuestasSeleccionadas){
            if(stringEncuestas.equals("")){
                stringEncuestas += ep;
            } else {
                stringEncuestas += "|" + ep;
            }
        }
        
        if(regionId != 0){

            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            parametros.put("formato", "HTML4.0");
            parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteResultadosCompleto");
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("ProyectosId", String.valueOf(proyectoId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("encuestasid", stringEncuestas);
            parametros.put("UsuariosOID", usuariosOID);

                listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);

                if(rep != null){
                    String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();

                reportEncuestas = StringHelper.readInputStreamAsString(rep);

                    reportEncuestas = reportEncuestas.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                    reportEncuestas = reportEncuestas.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportEncuestas = reportEncuestas.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                    reportEncuestas = reportEncuestas.replaceAll("%3a",":");
                    reportEncuestas = reportEncuestas.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                    reportEncuestas = reportEncuestas.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                }else{
                reportEncuestas = "";
            }
                rep.close();
            }else{
            reportEncuestas = "";
        }
    }

    public void dowloadResultadosExcel() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        if(regionId != 0 && !formato.equals("HTML4.0")){

            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            List<Seccion> listaSeccionesTmp = serviceReportesOperativos.getSeccionesByParams(seccionesOID, null, idEncuesta, 0, 0, 0, null, null, 0);
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
                    parametros.put("reportName", "ReporteResultadosCiclica");
                }else{
                    parametros.put("reportName", "ReporteResultadosPlana");
                }
                parametros.put("ClientesId", String.valueOf(clienteId));
                parametros.put("EquiposId", String.valueOf(equipoId));
                parametros.put("TerritoriosId", String.valueOf(territorioId));
                parametros.put("RegionesId", String.valueOf(regionId));
                parametros.put("ProyectosId", String.valueOf(proyectoId));
                parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
                parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
                parametros.put("SeccionesOID", seccionesOID);
                parametros.put("UsuariosOID", usuariosOID);
                parametros.put("formato", formato);

                listaParametros = serviceParametros.getParametrosReporting(null,null);
                AccessReportingService accrep = new AccessReportingService(listaParametros);

                InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
                byte[] buf = null;
                if(rep != null){
                    buf = StringHelper.getBytesFromInputStream(rep);
                }

                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(formato));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
                response.getOutputStream().write(buf);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                rep.close();

                context.responseComplete();
            }
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }

    }
    //Termina reporte de Resultados

    public void dowloadResultadosEncuestaExcel() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        if (regionId != 0 && !formato.equals("HTML4.0")) {

            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            stringEncuestas = "";

            for(String ep : listaEncuestasSeleccionadas){
                if(stringEncuestas.equals("")){
                    stringEncuestas += ep;
                } else {
                    stringEncuestas += "|" + ep;
                }
            }

            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteResultadosCompleto");
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("ProyectosId", String.valueOf(proyectoId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("UsuariosOID", usuariosOID);
            parametros.put("formato", formato);
            parametros.put("encuestasid", stringEncuestas);

            listaParametros = serviceParametros.getParametrosReporting(null, null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);

            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);
            byte[] buf = null;
            if (rep != null) {
                buf = StringHelper.getBytesFromInputStream(rep);
            }

            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setContentType(StringHelper.getContentType(formato));
            response.setHeader("Content-Disposition", "attachment;filename=" + parametros.get("reportName") + StringHelper.getTypeFile(formato));
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            rep.close();

            context.responseComplete();
        }
        if (formato.equals("HTML4.0")) {
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(), Utileria.getString("formato_descarga"));
        }

    }
    //Termina reporte de Resultados


    //Inicia codigo para reporte de Cumplimiento.
    public String getStringHtmlCumplimiento() throws FileNotFoundException, IOException {
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);

        if(regionId != 0){

            //Esto esta OK, Descomentar para que haga la peticion al servidor de reportes
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();

            parametros.put("rs:Format", "HTML4.0");
            parametros.put("reportName", "ReporteCumplimiento");
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("Detalle", "true");

            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);

            InputStream rep = accrep.sendGetReportingServiceInputStream(parametros);

            if(rep != null){
                reportCumplimiento = StringHelper.readInputStreamAsString(rep);

                Utileria.logger(getClass()).info("reportCumplimiento:"+reportCumplimiento);
                String servletImage = LoadProperties.getRpURLProject()+LoadProperties.getRpReportImageServletUrl();

                reportCumplimiento = reportCumplimiento.replaceAll(LoadProperties.getRpReportServerUrl(),servletImage);
                reportCumplimiento = reportCumplimiento.replaceAll(LoadProperties.getRpReportServerHost()+":"+LoadProperties.getRpReportServerPort()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportCumplimiento = reportCumplimiento.replaceAll(LoadProperties.getRpReportServerHost()+LoadProperties.getRpReportServerAdmin(),servletImage);
                reportCumplimiento = reportCumplimiento.replaceAll("%3a",":");
                reportCumplimiento = reportCumplimiento.replaceAll("&rs:SessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
                reportCumplimiento = reportCumplimiento.replaceAll("&rs%3ASessionID","&reportName="+parametros.get("reportName")+"&rs:SessionID");
            }else{
                reportCumplimiento = "";
            }

            renderView = "ReporteCumplimiento?faces-redirect=true";
            rep.close();
        }else{
            reportCumplimiento = "";
        }
        return renderView;
    }

    public void dowloadCumplimiento2(){
        Utileria.logger(getClass()).info("dowloadCumplimiento2");
    }

    public void dowloadCumplimiento(){
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        if(regionId != 0 && !formato.equals("HTML4.0")){
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            //ReporteCumplimientoDetalle.rdl
            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteCumplimiento");
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("formato", formato);
            parametros.put("Detalle", "false");

            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = null;
            try {
                rep = accrep.sendGetReportingServiceInputStream(parametros);

                byte[] buf = null;
                if(rep != null){
                    buf = StringHelper.getBytesFromInputStream(rep);
                }

                //byte[] buf = accrep.sendGetReportingServiceByte(parametros);
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(formato));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
                response.getOutputStream().write(buf);


                //Utileria.logger(getClass()).info("formato:" +  StringHelper.getContentType(formato) + "   reportName:" + parametros.get("reportName")+StringHelper.getTypeFile(formato));
                //DefaultStreamedContent(InputStream stream, String contentType, String name)
                //this.setFile(new DefaultStreamedContent(rep, StringHelper.getContentType(formato), parametros.get("reportName")+StringHelper.getTypeFile(formato)));
                //, StringHelper.getContentType(formato), parametros.get("reportName")+StringHelper.getTypeFile(formato))

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }catch (Exception e) {
                LOG.error(e);
            } finally {
                try {
                    rep.close();
                } catch (IOException ex) {
                    Utileria.logger(MBReportesOperativos.class).error("", ex);
                }
                context.responseComplete();
            }
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }
    }
    //Termina reporte de Cumplimiento.

    //Inicia desarrollo para reporte de incidencias
    //Inicia codigo para reporte de resultados
    //Metodo para obtener la informacion para los p:outputLabel del reporte de resultados.

    public String getStringHtmlsolucionIncidencias() throws FileNotFoundException, IOException {
        String renderView = "";
        FacesContext context = FacesContext.getCurrentInstance();
        if(regionId != 0){
            setRegion(serviceReportesOperativos.getRegionById(regionId));
        }
        if(equipoId != 0){
            setEquipo(serviceReportesOperativos.getEquipoId(equipoId));
        }

        if(territorioId != 0){
            nombreTerritorio = serviceTerritorios.nombreTerritorio(territorioId);
        }

        if(clienteId != 0 && listaClientes != null){
            for(Clientes item : listaClientes){
                if( clienteId == item.getClienteId()){
                    nombreCliente = item.getNombreCliente();
                }
            }
        }

        return "ReporteSolucionIncidencias?faces-redirect=true";
    }

    public void generarReporteSolucionIncidencias() {
        reportSolucionIncidencias = "";
        if (clienteId == 0 || equipoId == 0 || territorioId == 0 || regionId == 0
                || fechaIni == null || fechaFin == null) {
            return;
        }

        LinkedHashMap<String, String> parametros = new LinkedHashMap<>();
        formato = "HTML4.0";
        parametros.put("rs:Format", "HTML4.0");
        parametros.put("reportName", "ReporteIncidencias");
        parametros.put("ClientesId", String.valueOf(clienteId));
        parametros.put("EquiposId", String.valueOf(equipoId));
        parametros.put("TerritoriosId", String.valueOf(territorioId));
        parametros.put("RegionesId", String.valueOf(regionId));
        parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
        parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
        parametros.put("formato", formato);
        //parametros.put("Detalle", "true");

        listaParametros = serviceParametros.getParametrosReporting(null, null);
        AccessReportingService accrep = new AccessReportingService(listaParametros);

        try (InputStream rep = accrep.sendGetReportingServiceInputStream(parametros)) {

            if (rep != null) {
                reportSolucionIncidencias = StringHelper.readInputStreamAsString(rep);

                Utileria.logger(getClass()).info("ReporteIncidencias: " + reportSolucionIncidencias);
                String servletImage = LoadProperties.getRpURLProject() + LoadProperties.getRpReportImageServletUrl();

                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll(LoadProperties.getRpReportServerUrl(), servletImage);
                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll(LoadProperties.getRpReportServerHost() + ":" + LoadProperties.getRpReportServerPort() + LoadProperties.getRpReportServerAdmin(), servletImage);
                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll(LoadProperties.getRpReportServerHost() + LoadProperties.getRpReportServerAdmin(), servletImage);
                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll("%3a", ":");
                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll("&rs:SessionID", "&reportName=" + parametros.get("reportName") + "&rs:SessionID");
                reportSolucionIncidencias = reportSolucionIncidencias.replaceAll("&rs%3ASessionID", "&reportName=" + parametros.get("reportName") + "&rs:SessionID");
            } else {
                reportSolucionIncidencias = "";
            }
        } catch (IOException ex) {
            Utileria.logger(MBReportesOperativos.class).warn("", ex);
        }
    }

    public void dowloadsolucionIncidencias(){
        FacesContext context = FacesContext.getCurrentInstance();
        //addValuesVariablesContext(context);
        if(regionId != 0 && !formato.equals("HTML4.0")){
            LinkedHashMap<String, String> parametros = new LinkedHashMap<String, String>();
            //ReporteCumplimientoDetalle.rdl
            parametros.put("rs:Format", formato);
            parametros.put("reportName", "ReporteIncidencias");
            parametros.put("ClientesId", String.valueOf(clienteId));
            parametros.put("EquiposId", String.valueOf(equipoId));
            parametros.put("TerritoriosId", String.valueOf(territorioId));
            parametros.put("RegionesId", String.valueOf(regionId));
            parametros.put("FechaIni", DateHelper.convertirDateToString(fechaIni));
            parametros.put("FechaFin", DateHelper.convertirDateToString(fechaFin));
            parametros.put("formato", formato);
            //parametros.put("Detalle", "false");

            listaParametros = serviceParametros.getParametrosReporting(null,null);
            AccessReportingService accrep = new AccessReportingService(listaParametros);
            InputStream rep = null;
            try {
                rep = accrep.sendGetReportingServiceInputStream(parametros);

                byte[] buf = null;
                if(rep != null){
                    buf = StringHelper.getBytesFromInputStream(rep);
                }

                //byte[] buf = accrep.sendGetReportingServiceByte(parametros);
                HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
                response.setContentType(StringHelper.getContentType(formato));
                response.setHeader("Content-Disposition", "attachment;filename="+parametros.get("reportName")+StringHelper.getTypeFile(formato));
                response.getOutputStream().write(buf);

                response.getOutputStream().flush();
                response.getOutputStream().close();
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rep != null) {
                        rep.close();
                    }
                } catch (IOException ex) {
                    Utileria.logger(MBReportesOperativos.class).error("", ex);
                }
                context.responseComplete();
            }
        }
        if(formato.equals("HTML4.0")){
            Utileria.mensajeAlerta(FacesContext.getCurrentInstance(),Utileria.getString("formato_descarga"));
        }
    }
    //Termina desarrollo para reporte de incidencias

    public void resetPromotoresListReporte(){
        listaPromotores = new ArrayList();
        usuariosOID = null;
    }
    public void resetProyectosList(){
        listaProyectos = new ArrayList();
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        //listClientes = new ArrayList();

        proyectoId = 0;
        idEncuesta = 0;
        seccionesOID = null;
        //clienteId = 0;
    }

    public void resetEncuestasList(){
        listaEncuestas = new ArrayList();
        listaSecciones = new ArrayList();
        idEncuesta = 0;
        seccionesOID = null;
        listaEncuestasSeleccionadas = new ArrayList();
    }

    public void resetSeccionesList(){
        listaSecciones = new ArrayList();
        seccionesOID = null;
    }

    public void promotoresListReporte(){

        if(usuariosOID == null || usuariosOID.equals("") || usuariosOID.equals("0")){
            usuariosOID = null;
        }

        listaPromotores = serviceReportesOperativos.getUsuariosByParams(null, null, null, null,
                null, null, null, null, null, null, null, territorioId, null, null,
                0, equipoId, 0, 1);

    }

    public void proyectosList(){
        //Integer proyectoId,String claveProyecto, String descripcionProyecto,String nombreProyecto, Integer clienteId, Integer unidadNegocioId,
        //Date fechaInicio, Date fechaFin, Date fechaVisible, int activo
        //, Date fechaIni,Date fechaFin
        listaProyectos = serviceReportesOperativos.getProyectosByParams(0, claveProyecto, null, nombreProyecto, clienteId, 0, fechaIni, fechaFin, null, true);

    }

    public void encuestasList(){
        //int idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId, boolean esPlantilla, boolean activa,
        //Date fechaCreacionEncuesta, String observacionEncuesta, int proyectoId, int unidadesNegocioEncuestaId
        listaEncuestas = serviceReportesOperativos.getEncuestasByParams(null, proyectoId);

    }

    public void seccionesList(){
        if(seccionesOID == null || seccionesOID.equals("") || seccionesOID.equals("0")){
            seccionesOID = null;
        }

        //Integer ciclica,Date fechaCreacion,Integer visible
        listaSecciones = serviceReportesOperativos.getSeccionesByParams(null, null, idEncuesta, null, 1, null, null, null, null);

    }

    public void getListaRegionesFind(){
        listaRegiones = serviceReportesOperativos.getRegionesByFind(0);
    }

    public void getEquiposTerritoriosByEqAndTerr(){

        if(equipoId != 0){//territorioId
            equiposTerritorios = serviceReportesOperativos.getEquiposTerritoriosByFind(equipoId, 0);
        }
    }

    public void equiposByRegion(){
        if(regionId != 0){
            listaEquipos = serviceReportesOperativos.getEquiposByFind(0, regionId, null);
        }
    }


    public void clientesList(){
        //clienteId, unidadesNegociosId, int equiposId, int territoriosId, String usuariosOID);//clienteId, 0, equipoId, territorioId, usuariosOID
        listaClientes = serviceReportesOperativos.listaClientesByParams(null, null, equipoId, territorioId, usuariosOID);//clienteId, 0, equipoId, territorioId, usuariosOID);
    }

    public void resetClientes(){
        listaClientes  = new ArrayList();
        clienteId = 0;
        //equipoId = 0;
        //territorioId = 0;
        //usuariosOID = null;
    }

    public void resetEquipos(){
        listaEquipos = new ArrayList();
        //equipoId = 0;
        equipoId = 0;
    }

    public void resetEquiposTerritorios(){
        equiposTerritorios = new ArrayList();
        //equipoId = 0;
        territorioId = 0;
    }
    /*
     public void addMessage(FacesContext context) {
     //if()
     String summary = proyectoGuardado ? "Se ha guardado el proyecto" : "No se pudo guardar el Proyecto";
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
     }
     */

    public void validaRegion(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione una regi?n.",null) );
    }
    /*
     public Boolean validateRepOperativos(FacesContext context) {
     Boolean valido = true;

     if(regionId == 0){
     valido = false;
     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!",  "Seleccione una regi?n.") );
     }
     if(equipoId == 0){
     valido = false;
     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!",  "Seleccione un equipo.") );
     }
     if(territorioId == 0){
     valido = false;
     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!",  "Seleccione un territorio.") );
     }
     if(clienteId == 0){
     valido = false;
     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error!", "Seleccione un cliente.") );
     }
     System.out.println("Llego a validateRepOperativos y  regionId:"+regionId +"  equipoId:"+equipoId+"  territorioId:"+territorioId+"  clienteId:"+clienteId);
     return valido;
     }
     */
    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public int getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(int territorioId) {
        this.territorioId = territorioId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
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

    public String getReportVisitas() {
        return reportVisitas;
    }

    public void setReportVisitas(String reportVisitas) {
        this.reportVisitas = reportVisitas;
    }

    public String getReportCumplimiento() {
        return reportCumplimiento;
    }

    public void setReportCumplimiento(String reportCumplimiento) {
        this.reportCumplimiento = reportCumplimiento;
    }

    public String getReportResultados() {
        return reportResultados;
    }

    public void setReportResultados(String reportResultados) {
        this.reportResultados = reportResultados;
    }

    public int getClienteResId() {
        return clienteResId;
    }

    public void setClienteResId(int clienteResId) {
        this.clienteResId = clienteResId;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public EquipoTerritorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(EquipoTerritorio territorio) {
        this.territorio = territorio;
    }

    public String getUsuariosOID() {
        return usuariosOID;
    }

    public void setUsuariosOID(String promotorOID) {
        this.usuariosOID = promotorOID;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getSeccionesOID() {
        return seccionesOID;
    }

    public void setSeccionesOID(String seccionesOID) {
        this.seccionesOID = seccionesOID;
    }



    public List<Proyectos> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyectos> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<EncuestasProyecto> getListaEncuestas() {
        return listaEncuestas;
    }

    public void setListaEncuestas(List<EncuestasProyecto> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }

    public String getClaveProyecto() {
        return claveProyecto;
    }

    public void setClaveProyecto(String claveProyecto) {
        this.claveProyecto = claveProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
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

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getCiclica() {
        return ciclica;
    }

    public void setCiclica(int ciclica) {
        this.ciclica = ciclica;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReportDashboard() {
        return reportDashboard;
    }

    public void setReportDashboard(String reportDashboard) {
        this.reportDashboard = reportDashboard;
    }
    /*
     public ServiceProyectos getServiceProyectos() {
     return serviceProyectos;
     }

     public void setServiceProyectos(ServiceProyectos serviceProyectos) {
     this.serviceProyectos = serviceProyectos;
     }

     public ServiceEncuestas getServiceEncuestas() {
     return serviceEncuestas;
     }

     public void setServiceEncuestas(ServiceEncuestas serviceEncuestas) {
     this.serviceEncuestas = serviceEncuestas;
     }

     public ServiceClientes getServiceClientes() {
     return serviceClientes;
     }

     public void setServiceClientes(ServiceClientes serviceClientes) {
     this.serviceClientes = serviceClientes;
     }
     */
    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ServiceReportesOperativos getServiceReportesOperativos() {
        return serviceReportesOperativos;
    }

    public void setServiceReportesOperativos(ServiceReportesOperativos serviceReportesOperativos) {
        this.serviceReportesOperativos = serviceReportesOperativos;
    }

    public List<Region> getListaRegiones() {
        return listaRegiones;
    }

    public void setListaRegiones(List<Region> listaRegiones) {
        this.listaRegiones = listaRegiones;
    }

    public List<Equipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<Equipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public List<EquipoTerritorio> getEquiposTerritorios() {
        return equiposTerritorios;
    }

    public void setEquiposTerritorios(List<EquipoTerritorio> equiposTerritorios) {
        this.equiposTerritorios = equiposTerritorios;
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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public ServiceTerritorios getServiceTerritorios() {
        return serviceTerritorios;
    }

    public void setServiceTerritorios(ServiceTerritorios serviceTerritorios) {
        this.serviceTerritorios = serviceTerritorios;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
    }

    public String getReportSolucionIncidencias() {
        return reportSolucionIncidencias;
    }

    public void setReportSolucionIncidencias(String reportSolucionIncidencias) {
        this.reportSolucionIncidencias = reportSolucionIncidencias;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getReportEncuestas() {
        return reportEncuestas;
    }

    public void setReportEncuestas(String reportEncuestas) {
        this.reportEncuestas = reportEncuestas;
    }

    public List<String> getListaEncuestasSeleccionadas() {
        return listaEncuestasSeleccionadas;
    }

    public void setListaEncuestasSeleccionadas(List<String> listaEncuestasSeleccionadas) {
        this.listaEncuestasSeleccionadas = listaEncuestasSeleccionadas;
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
