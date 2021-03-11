package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Archivos;
import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceEncuestas;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServicePromotores;
import com.crossmark.collector.business.services.ServiceProyectos;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.FileHelper;
import com.crossmark.collector.presentation.views.utils.StringHelper;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.MBPromotores;
import com.crossmark.collector.presentation.views.visitas.objects.ProyectoViewer;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


public class MBProyectos implements Serializable {
    
    private ServiceProyectos serviceProyectos;
    private ServiceEncuestas serviceEncuestas;
    private ServiceParametros serviceParametros;
    private ServiceCatalogos serviceCatalogos;
    private ServicePromotores servicePromotores;
    
    private static long serialVersionUID = -1L;
    //private int proyectoId;
    private String sessionOID;

    private Integer cantidadTiendas;
    private List<Proyectos> listaProyectos;
    private List<Proyectos> proyectosSeleccionados; //add by Paco
    private List<Proyectos> proyectosFiltrados;     //add by Paco
    private Proyectos proyectoSeleccionado;//Guarda los datos del proyecto que se esta editando, asi como los cambios que se le realizan.
    private Proyectos proyectoSeleccionadoCopia;//Guarda de forma temporal los datos del proyecto que ese esta editando, sin que se le hagan cambios.
    
    private List<Encuestas> listaEncuestas = new ArrayList<>();
    private List<Encuestas> listaEncuestasMostrar = new ArrayList<>();
    private List<Encuestas> listaEncuestasProyecto = new ArrayList<>();
    private Encuestas encuestaSeleccionada;
    private Integer idEncuestaSeleccionada;
    //private Encuestas encuestas;
    
    private List<Archivos> listaArchivosProyecto = new ArrayList<>();
    private Archivos archivoSelecccionado;
    
    //private Integer idProyectoGuardado;
    private UploadedFile archivo;
    StreamedContent archivoDescargar;
    private String inputFile;
    private String urlImage;
    private String pathImgProyectos;
    private String protocoloLeerImg;
    private String protocoloGuardarImg;
    private String nombreCliente;
    
    //parametros desde url
    private Integer regionesId;
    private Integer territorioId;
    private Integer equipoId;
    private Integer unidadesNegocioId;
    private Integer clienteId;
    StreamedContent logo;
    
    //variables para habilitar y deshabilitar componentes
    boolean fechaInicioHabilitada;
    boolean fechaFinHabilitada;
    boolean fechaVisibleHabilitada;
    boolean botonEditaHabilitado;
    
    private String query;
    
    public MBProyectos(){}
    
    
    public void procesaQuery(){
        
        Codificacion cs = new Codificacion();
        
        if(query != null){
            cs.proceso(query,true);
            Map<String,String> urlParameters = cs.obtenerParametros(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                if(element.getKey().equals("RegionesId")){
                    this.setRegionesId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("TerritorioId")){
                    this.setTerritorioId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("EquiposId")){
                    this.setEquipoId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("UnidadesNegocios")){
                    this.setUnidadesNegocioId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("ClienteId")){
                    this.setClienteId(Integer.parseInt(element.getValue()));
                }
            }
        }
    }
    
    
    @PostConstruct
    public void init(){
        
        UsuarioSession usSistema = null;
        
        usSistema = Utileria.getSessionAttribute("userLoged");
        if(usSistema != null){
            setSessionOID(usSistema.getOID());
            Utileria.logger(getClass()).info("usSistema.getOID():"+usSistema.getOID());
        }
        
        List<Parametros> listaParametros = serviceParametros.getParametrosReporting(null,null);
        if(listaParametros != null && !listaParametros.isEmpty())
        for(Parametros item : listaParametros){
            //ImageServeletProjectReporting
            //11	URLImageProdReporting	file://192.168.10.105/FranciscoMora/	STRING
            //PathImgProyectos
            //17	PathImgProyectos	proyectos/	String
          /*  
            private String urlImage;
        private String pathImgProyectos;
        private String protocoloLeerImg;
        */
            if(item.getNombre().equalsIgnoreCase("ProtocoloGuardarImg")){
                protocoloGuardarImg = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("ProtocoloLeerImg")){
                protocoloLeerImg = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("URLImageProdReporting")){
                urlImage = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("PathImgProyectos")){
                pathImgProyectos = item.getValor();
            }
        }
        
        Utileria.logger(getClass()).info("urlImage:"+urlImage+"     urlImage:"+pathImgProyectos);
    }
    
    public void showPreview(){
        if(proyectosSeleccionados.size()>1){
            Utileria.mensajeAlerta("Seleccionar solo un proyecto");
        }else{
            Proyectos p = proyectosSeleccionados.get(0);
            List<EncuestasProyecto> encuestas = getServicePromotores().getListaEncuestasProyecto(unidadesNegocioId, p.getProyectoId());
            try{
                List<Encuestas> temp = new ArrayList<>();
                for(EncuestasProyecto ep : encuestas){
                    Encuestas ec = getServiceEncuestas().traerEncuesta(ep.getEncuestasId());
                    temp.add(ec);
                }
                p.setListaEncuestasProyecto(temp);
                Cliente cl = getServiceCatalogos().getClienteById(clienteId);
                p.setNombreCliente(cl.getNombre());
                ProyectoViewer pv = new ProyectoViewer();
                pv.createPreview(p);
//                String window = "window.open('../../resources/preview/VistaPreviaProyectos.pdf')";
//                RequestContext.getCurrentInstance().execute(window);
            }catch (JRException | FileNotFoundException ex) {
                Logger.getLogger(MBPromotores.class.getName()).log(Level.SEVERE, null, ex);
                Utileria.mensajeErroneo(ex);
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(MBPromotores.class.getName()).log(Level.SEVERE, null, ex);
                Utileria.mensajeErroneo(ex);
            }
        }
    }
    
    //Obtiene la lista de proyectos activos.
    public void listaProyectos(){
    
// ************** Add by JDA - Para Mostrar el nombre del cliente **************//
        Cliente cl = getServiceCatalogos().getClienteById(clienteId);
        nombreCliente = cl.getNombre();
        RequestContext.getCurrentInstance().update("center-form:nombreCliente");
// *****************************************************************************//
        listaProyectos = getServiceProyectos().listaProyectos(null,
                        null, null, null,
                        this.getClienteId(), this.getUnidadesNegocioId(), null,
                        null, null, true);
        
        if(listaProyectos != null && !listaProyectos.isEmpty()){
            for(Proyectos item : listaProyectos){
                DateFormat dateFormat = null;
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                item.setFechaInicioFilter(dateFormat.format(item.getFechaInicio()));
                item.setFechaFinFilter(dateFormat.format(item.getFechaFin()));
                item.setFechaVisibleFilter(dateFormat.format(item.getFechaVisible()));
            }
        }
    }
    
    //Trae la lista de encuestas que se pueden agregar al proyecto
    public void listaEncuestasUNegocio(){
        listaEncuestas = listaEncuestasParams(null,//Proyecto
            unidadesNegocioId);
    }
    
    //Obtiene las encuestas, pasandole como parametro, el id del proyecto y la unidad de negocio.
    public List<Encuestas> listaEncuestasParams(Integer idProyecto, Integer uNegocioId){
        
        return getServiceProyectos().listaEncuestas(
            null,null,null,null,
            1,
            false,
            true,
            null,null,
            idProyecto,//Proyecto
            uNegocioId);
    }
    
    //add by Paco, para eliminar uno o varios proyectos
    public void eliminaProyectos() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean elimnado = false;
        String listaEliminar = "";
        
        if(proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty() ){

            String toDir = protocoloGuardarImg+urlImage+pathImgProyectos;//+proyectoSeleccionado.getArchivoLogo();
            for(Proyectos p : proyectosSeleccionados){
                Utileria.logger(getClass()).info("llego p:"+p.getProyectoId());
                String delete =  serviceProyectos.eliminaProyecto(p.getProyectoId());
                Utileria.logger(getClass()).info("llego p delete:"+delete);
                if(delete.equals("0")){

                    elimnado = true;
                    //listaEliminar += "\n"+p.getNombreProyecto()+": Eliminado.";
                    //Elimina el logo del proyecto
                    deleteFile(toDir+p.getArchivoLogo());

                    listaEncuestasProyecto = serviceProyectos.listaEncuestasProyecto(unidadesNegocioId, p.getProyectoId());
                    listaArchivosProyecto = serviceProyectos.getListaArchivos(null, p.getProyectoId());
                    //Elimina las encuestas del proyecto
                    if(listaEncuestasProyecto != null){
                        for(Encuestas item : listaEncuestasProyecto){
                            serviceProyectos.eliminaEncuestaProyecto(p.getProyectoId(), item.getEncuestasId());
                        }
                    }
                    //Elimina los archivos del proyecto
                    if(listaArchivosProyecto != null){
                        String fileProyecto = toDir+p.getProyectoId()+"_"+p.getClaveProyecto();//Nombre de la carpeta para el proyecto seleccionado
                        deleteFile(fileProyecto+"/"+p.getArchivoLogo());

                        for(Archivos item : listaArchivosProyecto){
                            deleteFile(fileProyecto+"/"+item.getNombre());
                            serviceProyectos.eliminaArchivoProyecto(item.getArchivosOID());
                        }
                    }
                }else{
                    listaEliminar += "\n"+p.getNombreProyecto()+": "+delete;
                }
                //serviceProyectos.eliminaProyecto(p.getProyectoId());
            }
        }else{
            mensajeAlerta(Utileria.getString("selection_del_proyectos"));
        }
            
            proyectoSeleccionado = new Proyectos();
            proyectoSeleccionadoCopia = new Proyectos();
            proyectosSeleccionados = new ArrayList();
            
            listaEncuestasProyecto = new ArrayList();
            listaArchivosProyecto = new ArrayList();
            listaProyectos();
            if(elimnado){
                if(listaEliminar.equals("")){
                    Utileria.mensajeSatisfactorio(Utileria.getString("eliminados_proyectos", ""),Utileria.getString("eliminados_proyectos", ""));
                }else{
                    Utileria.mensajeSatisfactorio(Utileria.getString("eliminados_proyectos", ". Proyectos no eliminados:"+listaEliminar),Utileria.getString("eliminados_proyectos", ". Proyectos no eliminados:"+listaEliminar));
                }
            //Utileria.mensajeAlerta(context,listaEliminar);
            }else if(!listaEliminar.equals("")){
                mensajeAlerta(listaEliminar);
            }
    }
    
    boolean deleteFile(String dirFile){
        boolean deleted = false;
        try {
            if(FileHelper.existFile(dirFile)){
                deleted = FileHelper.delete(dirFile);
            }
        } catch (Exception ex) {
            Logger.getLogger(MBProyectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleted;
    }
    //Metodo que se ejecuta al dar click enel boton nuevo
    public void limpiaCamposProyecto(){
        
        reloadGrid();
        RequestContext.getCurrentInstance().execute("PF('agregarProyecto').show()");
    }
    
    //Limpia y recarga los datos del grid.
    public void reloadGrid(){
        listaProyectos = new ArrayList();
        proyectosSeleccionados = new ArrayList();
        proyectoSeleccionado = new Proyectos();
        proyectoSeleccionado.setFechaInicio(DateHelper.getNow());
        proyectoSeleccionado.setFechaFin(DateHelper.getNow());
        proyectoSeleccionado.setFechaVisible(DateHelper.getNow());
        proyectoSeleccionado.setDiasVigencias(0);
        proyectoSeleccionadoCopia = new Proyectos();
        listaProyectos();
    }
    
    //Evento que se ejecuta al cerrar el dialog de nuevo, con el boton de regresar
    public void cerrarNuevo() throws Exception{
        String toFile = protocoloGuardarImg+urlImage+pathImgProyectos;
        if(FileHelper.existFile(toFile+this.getInputFile()) ){
            FileHelper.delete(toFile+proyectoSeleccionado.getArchivoLogo());
        }
        reloadGrid();
    }
    
    //Evento que se ejecuta al cerrar el dialog de nuevo
    public void handleCloseNew(CloseEvent event) throws Exception{
        String toFile = protocoloGuardarImg+urlImage+pathImgProyectos+proyectoSeleccionado.getArchivoLogo();
        if(FileHelper.existFile(toFile) ){
            FileHelper.delete(toFile);
        }
        reloadGrid();
    }
    
    //Evento que se ejecuta al cerrar el dialog de copiar
    public void handleCloseCopy(CloseEvent event){
        reloadGrid();
    }
    
    //Evento de seleccionado de proyecto
    public void seleccionarProyecto(SelectEvent event) {
        event.getObject();
        proyectoSeleccionado = (Proyectos) event.getObject();
        proyectoSeleccionadoCopia = proyectoSeleccionado;
    }
    
    //Evento quitar seleccion de proyecto
    public void quitarSeleccion(UnselectEvent event) {
        //Si ha seleccionado mas de un elemento, proyectoSeleccionado siempre se mantendra en el ultimo.
        if(proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty()){
            proyectoSeleccionado = proyectosSeleccionados.get((proyectosSeleccionados.size() - 1));
            proyectoSeleccionadoCopia = proyectosSeleccionados.get((proyectosSeleccionados.size() - 1));
        }else{
            proyectoSeleccionado = new Proyectos();
            proyectoSeleccionadoCopia = new Proyectos();
        }
    }
    
    /*Alta de un proyecto*/
    public void altaProyectos() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();    
        
        Integer proyectoGuarda = 0;
        if (validaCampos()) {
            try {
                proyectoSeleccionado.setProyectoId(0);
                
                proyectoGuarda = guardaProyecto();
                
                //@DiasVigencias
                if (proyectoGuarda > 0) {
                    proyectoSeleccionado.setProyectoId(proyectoGuarda);
                    //logo = new DefaultStreamedContent(event.getFile().getInputstream());
                    if(logo != null ){
                        String type_tmp = "jpeg";
                        
                        if(logo.getContentType() != null && logo.getContentType().contains("/")){
                            type_tmp = logo.getContentType().split("/")[1];
                        }
                        
                        String toFile = urlImage+pathImgProyectos;
                        String nameLogo = proyectoSeleccionado.getClaveProyecto()+"_"+proyectoSeleccionado.getProyectoId()+"."+type_tmp;
                        String nameDir = proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto();//Carpeta del proyecto.
                        
                        FileHelper.createDir(protocoloGuardarImg+toFile+nameDir);
                        
                        //Copiar el archivo, del temporal a la carpeta correspondiente del proyecto
                        if(FileHelper.existFile(protocoloGuardarImg+toFile+proyectoSeleccionado.getArchivoLogo()) && 
                                FileHelper.copy(protocoloGuardarImg+toFile+proyectoSeleccionado.getArchivoLogo(), protocoloGuardarImg+toFile+nameDir+"/"+nameLogo)){
                            FileHelper.delete(protocoloGuardarImg+toFile+proyectoSeleccionado.getArchivoLogo());
                            proyectoSeleccionado.setArchivoLogo(nameLogo);
                            proyectoGuarda = guardaProyecto();
                            
                        }
                    }
                    
                    Utileria.mensajeSatisfactorio(Utileria.getString("guardado_proyectos"),Utileria.getString("guardado_proyectos"));
                    reloadGrid();
                } else {
                    mensajeAlerta("No se pudo guardar el proyecto "+proyectoSeleccionado.getNombreProyecto());
                }
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
        } else {
            mensajeAlerta("Faltan algunos datos, verifique! ");
        }
    }
    
    /*Copiar proyecto*/
    public void buscaProyectoCopiar(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            
            if(proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty() && proyectosSeleccionados.size() == 1 && proyectoSeleccionado != null){
                //this.proyectoSeleccionado.getProyectoId();
                this.setProyectoSeleccionado(proyectosSeleccionados.get(0));
                this.setProyectoSeleccionadoCopia((Proyectos)proyectoSeleccionado.clone());
                
                proyectoSeleccionado.setClaveProyecto(null);
                proyectoSeleccionado.setNombreProyecto("COPIA-"+proyectoSeleccionado.getNombreProyecto());
                proyectoSeleccionado.setDescripcionProyecto("COPIA-"+proyectoSeleccionado.getDescripcionProyecto());
                
                this.setListaEncuestasProyecto(serviceProyectos.listaEncuestasProyecto(unidadesNegocioId,proyectoSeleccionado.getProyectoId() ));
                setInputFile(proyectoSeleccionado.getArchivoLogo());
                listaArchivosProyecto = serviceProyectos.getListaArchivos(null, proyectoSeleccionado.getProyectoId());
                
                RequestContext.getCurrentInstance().execute("PF('copiarProyecto').show()");
                
            }else{
                //Utileria.mensajeAlerta(Utileria.getString("Para editar, solo debe haber un proyecto seleccionado."));
                mensajeAlerta(Utileria.getString("selection_copy_proyectos"));
            }
        } catch (NullPointerException npe) {
            mensajeAlerta("No se puede copiar proyecto si no ha seleccionado un cliente");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MBProyectos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void proyectoCopiar(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            
            proyectoSeleccionado.setArchivoLogo(null);
            proyectoSeleccionado.setProyectoId(0);
            Integer proyectoGuarda = guardaProyecto();
            if (proyectoGuarda > 0) {
                proyectoSeleccionado.setProyectoId(proyectoGuarda);
                //Copiado de encuestas del proyecto
                if(listaEncuestasProyecto != null){
                    for(Encuestas item : listaEncuestasProyecto){
                        String agregada = serviceProyectos.agregaEncuesta(proyectoSeleccionado.getProyectoId(), item.getEncuestasId());
                        if (agregada.equals("0")) {
                            //System.out.println("Encuesta guardada getIdProyectoGuardado:"+proyectoSeleccionado.getProyectoId()+"            getEncuestasId:"+item.getEncuestasId());
                        }else{
                            //System.out.println("Encuesta no guardada getIdProyectoGuardado:"+proyectoSeleccionado.getProyectoId()+"            getEncuestasId:"+item.getEncuestasId());
                        }
                    }
                }
                
                //Copiado de los archivos del proyecto
                String fromFile = protocoloGuardarImg+urlImage+pathImgProyectos+proyectoSeleccionadoCopia.getProyectoId()+"_"+proyectoSeleccionadoCopia.getClaveProyecto()+"/";
                String toFile = protocoloGuardarImg+urlImage+pathImgProyectos+proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto();
                
                FileHelper.createDir(toFile);
                if(listaArchivosProyecto != null){
                    
                    for(Archivos item : listaArchivosProyecto){
                        item.setArchivosOID(null);
                        item.setProyectosId(proyectoSeleccionado.getProyectoId());
                        String archivoOID = serviceProyectos.agregaArchivoProyecto(item);
                        
                        if(item.getNombre() != null && !item.getNombre().equals("") && FileHelper.existFile(fromFile+item.getNombre())){
                            FileHelper.copy(fromFile+item.getNombre(), toFile+"/"+item.getNombre());
                        }
                    }
                }
                
                if(logo != null && proyectoSeleccionadoCopia.getArchivoLogo() != null && !proyectoSeleccionadoCopia.getArchivoLogo().equals("") && proyectoSeleccionadoCopia.getArchivoLogo().contains(".")){
                    String nameLogoTmp[] = proyectoSeleccionadoCopia.getArchivoLogo().split("\\.");
                    String type_tmp = nameLogoTmp[(nameLogoTmp.length - 1)];
                    
                    String nameLogo = proyectoSeleccionado.getClaveProyecto()+"_"+proyectoSeleccionado.getProyectoId()+"."+type_tmp;
                    
                    if(FileHelper.existFile(fromFile+proyectoSeleccionadoCopia.getArchivoLogo()) && FileHelper.copy(fromFile+proyectoSeleccionadoCopia.getArchivoLogo(), toFile+"/"+nameLogo)){
                        proyectoSeleccionado.setArchivoLogo(nameLogo);//this.setInputFile(nameLogo);
                        proyectoGuarda = guardaProyecto();
                    }else{
                        //mensajeAlerta("El logo, no fue copiado "+proyectoSeleccionado.getNombreProyecto());
                    }
                }
                
                mensajeAlerta("Se ha guardado el proyecto "+proyectoSeleccionado.getNombreProyecto());
                Utileria.mensajeSatisfactorio("Se ha guardado el proyecto "+proyectoSeleccionado.getNombreProyecto(),"Se ha guardado el proyecto "+proyectoSeleccionado.getNombreProyecto());
                reloadGrid();
            } else {
                mensajeAlerta("No se pudo guardar el proyecto "+proyectoSeleccionado.getNombreProyecto());
            }
        } catch (Exception ex) {
            mensajeAlerta("No se pudo guardar el proyecto "+proyectoSeleccionado.getNombreProyecto()+"   "+ex.getMessage());
            ex.printStackTrace();
        }
            
    }
    
    public void agregarEncuesta(){
        String agregada = "";
        FacesContext context = FacesContext.getCurrentInstance();
        
        agregada = serviceProyectos.agregaEncuesta(proyectoSeleccionado.getProyectoId(), this.idEncuestaSeleccionada);
        if (agregada.equals("0")) {
            //muestraEncuestasProyecto = true;
            this.idEncuestaSeleccionada = 0;
            this.setListaEncuestasProyecto(serviceProyectos.listaEncuestasProyecto(unidadesNegocioId,proyectoSeleccionado.getProyectoId() ));
            Utileria.mensajeSatisfactorio("Encuesta agregada.","Encuesta agregada.");
        }else{
            Utileria.mensajeAlerta(agregada);
        }
    }
    
    /*Valida que los campos no esten en null ###### Mejoirar y quitar este metodo#######*/
    public boolean validaCampos() {
        boolean camposLlenos = false;
        
        if (unidadesNegocioId == null || clienteId == null
                            || proyectoSeleccionado.getClaveProyecto() == null
                            || proyectoSeleccionado.getDescripcionProyecto() == null
                            || proyectoSeleccionado.getNombreProyecto()== null
                            || proyectoSeleccionado.getFechaInicio() == null || proyectoSeleccionado.getFechaFin() == null
                            || proyectoSeleccionado.getFechaVisible() == null) {
            camposLlenos = false;
        } else {
            camposLlenos = true;
        }
        return camposLlenos;
    }
    
    
    public void buscaProyectoEditar(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            
            if(proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty() && proyectosSeleccionados.size() == 1 && proyectoSeleccionado != null ){
                
                proyectoSeleccionado = proyectosSeleccionados.get(0);
                try {
                    this.setProyectoSeleccionadoCopia((Proyectos)proyectoSeleccionado.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(MBProyectos.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                this.setListaEncuestasProyecto(serviceProyectos.listaEncuestasProyecto(unidadesNegocioId,proyectoSeleccionado.getProyectoId()));
                Date fechaActualSistema = Utileria.fechaActual();//formateador.parse(fechaSistema);
                
                if (fechaActualSistema.after(proyectoSeleccionado.getFechaInicio()) && fechaActualSistema.before(proyectoSeleccionado.getFechaFin())) {
                    this.fechaInicioHabilitada = true;
                    this.fechaFinHabilitada = true;
                    this.fechaVisibleHabilitada = true;
                    this.botonEditaHabilitado = false;
                } else {
                    this.fechaInicioHabilitada = false;
                    this.fechaFinHabilitada = false;
                    this.fechaVisibleHabilitada = false;
                    this.botonEditaHabilitado = true;
                }
                RequestContext.getCurrentInstance().execute("PF('editarProyecto').show()");
            }else{
                mensajeAlerta(Utileria.getString("selection_edit_proyectos"));
            }
        } catch (NullPointerException npe) {
                mensajeAlerta("No se puede editar proyecto si no ha seleccionado un cliente");
        }
    }
    
    
    /*Subir archivos, para nuevo y para edicion*/
    public boolean upload(FileUploadEvent event){
        
        boolean render=false;
        FacesContext context = FacesContext.getCurrentInstance();
        
        String toFile = protocoloGuardarImg+urlImage+pathImgProyectos;//Ruta en la que se guardaran los logos de los proyectos
        
        try {
            String type_tmp = event.getFile().getContentType().split("/")[1];//Obteniendo el tipo de archivo que se selecciono
            if(proyectoSeleccionado != null && proyectoSeleccionado.getProyectoId() != null && proyectoSeleccionado.getProyectoId() != 0 ){
                String nameLogo = proyectoSeleccionado.getArchivoLogo();//Nombre del logo del proyecto seleccionado
                
                String dirProyectoTmp = toFile+proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto();//Nombre de la carpeta para el proyecto seleccionado
                if(!FileHelper.createDir(dirProyectoTmp)){
                    mensajeAlerta("No se pudo crear el directorio para el proyecto.");
                }
                dirProyectoTmp += "/";
                //Si ya existe el archivo, se elimina.
                if(FileHelper.existFile(dirProyectoTmp+nameLogo)){
                    FileHelper.delete(dirProyectoTmp+nameLogo);
                }
                nameLogo = proyectoSeleccionado.getClaveProyecto()+"_"+proyectoSeleccionado.getProyectoId()+"."+type_tmp;
                logo = new DefaultStreamedContent(event.getFile().getInputstream(),event.getFile().getContentType(), event.getFile().getFileName());//Carga el logo en la variable logo, para poder mostrarlo en pantalla
                if(guadaImagenDirectorio(dirProyectoTmp , nameLogo)){
                    proyectoSeleccionado.setArchivoLogo(nameLogo);//this.setInputFile(nameLogo);
                    //proyectoEditar("imagen");
                    guardaProyecto();
                    render = true;
                }
                if(!render){
                    mensajeAlerta("No se pudo guardar la imagen.");
                }
            }else{
                String nameLogo = DateHelper.nowNumber()+"."+type_tmp;
                
                if(FileHelper.existFile(toFile+nameLogo )){
                    FileHelper.delete(toFile+nameLogo);
                }
                //Para cuando sea nuevo proyecto, solo guarda en memoria
                logo = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType(), nameLogo);
                if(guadaImagenDirectorio(toFile , nameLogo)){
                    this.proyectoSeleccionado.setArchivoLogo(nameLogo);//setInputFile(nameLogo);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MBProyectos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return render;
    }
    
    //enviadoDe, guarda info de donde fue realizada la llamada al metodo.
    public boolean proyectoEditar(String enviadoDe){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean seEdita = true;
        Integer proyectoEdita = -1;
        
        try {
            proyectoEdita = guardaProyecto();
            
            //this.setIdProyectoGuardado(proyectoEdita);
            
            if (proyectoEdita == 0 && !enviadoDe.equals("imagen")) {
                Utileria.mensajeSatisfactorio(Utileria.getString("guardado_proyectos"),Utileria.getString("guardado_proyectos"));
                seEdita = true;
            } else if(!enviadoDe.equals("imagen")){
                mensajeAlerta("No se editó el proyecto");
            }
        } catch (Exception ex) {
            mensajeAlerta(ex.getMessage());
                ex.printStackTrace();
        }finally{
            reloadGrid();
        }
        return seEdita;
    }
    
    Integer guardaProyecto(){
        Integer guardo = -1;
        //proyectoSeleccionado
        Date fechaActualSistema = Utileria.fechaActual();
        
        //Validar que las fechas no esten dentro del reanfo a la fecha actual, si es asi, regrtesa a las fechas originales
        if (proyectoSeleccionadoCopia != null && proyectoSeleccionadoCopia.getProyectoId() != null  && proyectoSeleccionadoCopia.getProyectoId() != 0 
                && fechaActualSistema.after(proyectoSeleccionado.getFechaInicio()) && fechaActualSistema.before(proyectoSeleccionado.getFechaFin())) {
            proyectoSeleccionado.setClaveProyecto(proyectoSeleccionadoCopia.getClaveProyecto());
            proyectoSeleccionado.setFechaFin(proyectoSeleccionadoCopia.getFechaFin());
            proyectoSeleccionado.setFechaInicio(proyectoSeleccionadoCopia.getFechaInicio());
            proyectoSeleccionado.setFechaVisible(proyectoSeleccionadoCopia.getFechaVisible());
        }
        /*
        idProyecto,claveProyecto, nombreProyecto,
                 descripcionProyecto,  fechaVisible,  fechaInicio,
                 fechaFin,  status,  unidadNegocioId,  visitaAutomatica, clienteId, imagen, diasVigencias
        */
        guardo = getServiceProyectos().guardaProyecto(
                proyectoSeleccionado.getProyectoId(), proyectoSeleccionado.getClaveProyecto(),
                proyectoSeleccionado.getNombreProyecto(),
                proyectoSeleccionado.getDescripcionProyecto(), proyectoSeleccionado.getFechaVisible(),
                proyectoSeleccionado.getFechaInicio(), proyectoSeleccionado.getFechaFin(),
                1, unidadesNegocioId,
                proyectoSeleccionado.isVisitaAutomatica() == false ? 0 : 1,
                clienteId,
                proyectoSeleccionado.getArchivoLogo(), proyectoSeleccionado.getDiasVigencias());
        
        return guardo;
    }
    
    
    //Obtiene las encuestas del proyecto seleccionado
    public void obtieneEncuestas() {
        idEncuestaSeleccionada = 0;
        listaEncuestas = new ArrayList();
        listaEncuestasProyecto = new ArrayList();
        
        listaEncuestas = listaEncuestasParams(null,unidadesNegocioId);
        //listaEncuestasProyecto = listaEncuestasParams(proyectoSeleccionado.getProyectoId(),unidadesNegocioId);
        listaEncuestasProyecto = serviceProyectos.listaEncuestasProyecto(unidadesNegocioId,proyectoSeleccionado.getProyectoId() );
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(listaEncuestas != null && !listaEncuestas.isEmpty() ){
            
            if( listaEncuestasProyecto != null && !listaEncuestasProyecto.isEmpty()){
                listaEncuestasMostrar = new ArrayList<Encuestas>();
                for(Encuestas item : listaEncuestas){
                    boolean seleccionado = false;
                    for(Encuestas item2 : listaEncuestasProyecto){
                        if(item.getEncuestasId().longValue() == item2.getEncuestasId().longValue()){
                            seleccionado = true;
                        }
                    }
                    if(!seleccionado){
                        listaEncuestasMostrar.add(item);
                    }
                }
            }else{
                listaEncuestasMostrar = listaEncuestas;
            }
            RequestContext.getCurrentInstance().execute("PF('agregadoDialog').show()");
        }else{
            mensajeAlerta("No hay encustas para seleccionar.");
        }
    }
    
    //Metodo para eliminar encuestas del proyecto seleccionado
    public void eliminaEncuestaProyecto() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            if(encuestaSeleccionada != null){
                String eliminado = serviceProyectos.eliminaEncuestaProyecto(this.proyectoSeleccionado.getProyectoId(),this.encuestaSeleccionada.getEncuestasId());
                this.setListaEncuestasProyecto(serviceProyectos.listaEncuestasProyecto(unidadesNegocioId,proyectoSeleccionado.getProyectoId() ));
                if(eliminado.equals("0")){
                    Utileria.mensajeSatisfactorio("Encuesta Eliminada.","Encuesta Eliminada.");
                }else{
                    mensajeAlerta(eliminado);
                }
            }else{
                mensajeAlerta("Para eliminar debe seleccionar una encuesta.");
            }
        } catch (Exception ex) {
            mensajeAlerta("No se elimin&oacute; la encuesta");
            ex.printStackTrace();
        }
    }
    
    //Obtiene los oarchivos de la encuesta seleccinoada
    public void buscaArchivosProyecto(){
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if(proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty() && proyectosSeleccionados.size() == 1 && proyectoSeleccionado != null ){
                proyectoSeleccionado = proyectosSeleccionados.get(0);
                proyectoSeleccionadoCopia = proyectosSeleccionados.get(0);
                
                listaArchivosProyecto = serviceProyectos.getListaArchivos(null, proyectoSeleccionado.getProyectoId());
                RequestContext.getCurrentInstance().execute("PF('archivosProyectoDialog').show()");
            }else{
                mensajeAlerta(Utileria.getString("selection_upload_proyectos"));
            }
        } catch (NullPointerException npe) {
            mensajeAlerta("No se puede editar proyecto si no ha seleccionado un cliente");
        }
    }
    
    /*Subir archivos, para nuevo y para edicion FileUploadEvent event*/
    public boolean subirArchivosProyecto(FileUploadEvent event) throws IOException{
        
        boolean validaciones=true;
        FacesContext context = FacesContext.getCurrentInstance();
        
        
        String mensaje = "";
        //urlImage = "C:\\Users\\Francisco Mora\\Documents\\proyectos\\";
        //pathImgProyectos = "";
        String toFile = protocoloGuardarImg+urlImage+pathImgProyectos;//Ruta en la que se almacenaran las imagenes de los proyectos.
        String dirProyectoTmp = proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto();//Nombre de la carpeta para el proyecto seleccionado
        
        //Valida que haya un proyecto seleccionado
        if((proyectoSeleccionado.getProyectoId() == null || proyectoSeleccionado.getProyectoId() == 0) && validaciones == true ){
            validaciones = false;
            //Utileria.mensajeAlerta(context,"Debe de seleccionar un proyecto.");
            mensaje = "Debe de seleccionar un proyecto.";
        }
        //Valida que no haya mas de 5 archivos en el proyecto
        if((listaArchivosProyecto != null && listaArchivosProyecto.size() >= 5) && validaciones == true){
            validaciones = false;
            //Utileria.mensajeAlerta(context,"Solo puede haber un maximo de 5 archivos.");
            mensaje = "Solo puede haber un maximo de 5 archivos.";
        }
        //Valida que el total de archivos incluyendo el que se cargo, no sobrepasen los 2 megas
        if(listaArchivosProyecto != null  && validaciones == true){
            long sumatoriaMb = 0;
            for(Archivos item : listaArchivosProyecto){
                if(FileHelper.existFile(toFile+dirProyectoTmp+"/"+item.getNombre())){
                    sumatoriaMb += FileHelper.getSizeFile(toFile+dirProyectoTmp+"/"+item.getNombre());
                }
            }
            
            sumatoriaMb += event.getFile().getSize();
            //1048576=1MB;
            if(sumatoriaMb > (1048576 * 2)){
                validaciones = false;
                //Utileria.mensajeAlerta(context,"La suma del tamaño de los archivos, sobrepasa los 2 MB.");
                mensaje = "La suma del tamaño de los archivos, sobrepasa los 2 MB.";
            }
        }
        
        if(proyectoSeleccionado.getProyectoId() != null && proyectoSeleccionado.getProyectoId() != 0 && validaciones == true ){
            
            if(!FileHelper.createDir(toFile+dirProyectoTmp) && validaciones == true){
                //Utileria.mensajeAlerta(context,"No se pudo crear el directorio para el proyecto.");
                validaciones = false;
                mensaje = "No se pudo crear el directorio para el proyecto.";
            }
            if(FileHelper.existFile(toFile+dirProyectoTmp+"/"+event.getFile().getFileName()) && validaciones == true){
                //Utileria.mensajeAlerta(context,"Ya existe un archivo con el mismo nombre.");
                validaciones = false;
                mensaje = "Ya existe un archivo con el mismo nombre.";
            }
            
            if(validaciones){
                //Copia el archivo a la carpeta del proyecto
                boolean guardarArchivo = FileHelper.saveInputStreamFile(event.getFile().getInputstream(), toFile+dirProyectoTmp+"/", event.getFile().getFileName());
                
                if(guardarArchivo){
                    //Crea un nuevo registro en la tabla de archivos, para el proyecto seleccionado
                    Archivos archivo = new Archivos();
                    archivo.setActivo(true);
                    archivo.setNombre(event.getFile().getFileName());
                    archivo.setFecha(DateHelper.getNow());
                    archivo.setProyectosId(proyectoSeleccionado.getProyectoId());
                    String archivoOID = serviceProyectos.agregaArchivoProyecto(archivo);
                    //Utileria.mensajeAlerta(Utileria.getString("cargado_umploadfile_proyectos"));
                    mensaje = Utileria.getString("cargado_umploadfile_proyectos");
                }
                listaArchivosProyecto = serviceProyectos.getListaArchivos(null, proyectoSeleccionado.getProyectoId());
            }
        }
        
        Utileria.mensajeAlerta(mensaje);
        //mensajeAlerta(mensaje);
        return validaciones;
    }
    
    //Metodo para descargar los archivos del proyecto seleccionado
    public void descargarArchivoProyecto(String nombre){
        FacesContext context = FacesContext.getCurrentInstance();
        String archivoTmp = protocoloGuardarImg+urlImage+pathImgProyectos;
        archivoDescargar = null;
        
        String nombreArchivo = nombre;//params.get("nombreArchivo") != null ? params.get("nombreArchivo") : "";
        
        if(nombreArchivo != null && !nombreArchivo.equals("")){
            
            archivoTmp += proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto()+"/"+nombreArchivo;
            
            if(FileHelper.existFile(archivoTmp) ){
                String contentType = StringHelper.getContentTypeByFileName(nombreArchivo);
                if(contentType.equals("")){
                    mensajeAlerta("El archivo, no tienen extension.");
                    archivoDescargar = null;
                }else{
                    archivoDescargar = new DefaultStreamedContent(FileHelper.getFileInputStream(archivoTmp), contentType ,nombreArchivo );
                }
            }else{
                mensajeAlerta("No se encontro el archivo.");
            }
        }else{
            mensajeAlerta("No se encontro el archivo.");
        }
    }
    
    //Metodo para eliminar archivos del proyecto seleccionado
    public void eliminaArchivoProyecto() {
        FacesContext context = FacesContext.getCurrentInstance();
        String archivoTmp = protocoloGuardarImg+urlImage+pathImgProyectos;
        archivoTmp += proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto()+"/";
        
        try {
            if(archivoSelecccionado != null && archivoSelecccionado.getNombre() != null && !archivoSelecccionado.getNombre().equals("")){
                serviceProyectos.eliminaArchivoProyecto(archivoSelecccionado.getArchivosOID());
                if(FileHelper.existFile(archivoTmp+archivoSelecccionado.getNombre())){
                    FileHelper.delete(archivoTmp+archivoSelecccionado.getNombre());
                }
                listaArchivosProyecto = serviceProyectos.getListaArchivos(null, proyectoSeleccionado.getProyectoId());
                //Utileria.mensajeSatisfactorio(Utileria.getString("eliminado_umploadfile_proyectos"));
                Utileria.mensajeSatisfactorio(Utileria.getString("eliminado_umploadfile_proyectos"),Utileria.getString("eliminado_umploadfile_proyectos"));
            }else{
                if(archivoSelecccionado == null){
                    mensajeAlerta("Para eliminar debe seleccionar un archivo.");
                }
            }
        } catch (Exception ex) {
             mensajeAlerta("No se elimin&oacute; el archivo.");
            ex.printStackTrace();
        }
    }
    
    
    //Cargar el logo del proyecto seleccionado en la pagina.
    public void getInputStreamLogo(String edit) {
        String fileLogo = protocoloGuardarImg+urlImage+pathImgProyectos;
        
        if(edit.equals("edit") && proyectoSeleccionado != null){
            fileLogo += proyectoSeleccionado.getProyectoId()+"_"+proyectoSeleccionado.getClaveProyecto()+"/";
        }else if(edit.equals("copy") && proyectoSeleccionado != null && proyectoSeleccionadoCopia != null){
            
            fileLogo += proyectoSeleccionadoCopia.getProyectoId()+"_"+proyectoSeleccionadoCopia.getClaveProyecto()+"/";
        }
        String type = "jpeg";
        if(proyectoSeleccionado != null && proyectoSeleccionado.getArchivoLogo() != null){
            fileLogo += proyectoSeleccionado.getArchivoLogo();
            //logo = new DefaultStreamedContent(event.getFile().getInputstream(),event.getFile().getContentType(), event.getFile().getFileName());
            if(proyectoSeleccionado.getArchivoLogo().contains(".")){
                type = proyectoSeleccionado.getArchivoLogo().split("\\.")[proyectoSeleccionado.getArchivoLogo().split("\\.").length - 1];
            }
        }
        
        logo = null;
        if(proyectoSeleccionado != null && (edit.equals("edit") || edit.equals("copy")) && proyectoSeleccionado.getArchivoLogo() != null){
            
            if(FileHelper.existFile(fileLogo)){
                setLogo(new DefaultStreamedContent(FileHelper.getFileInputStream(fileLogo), type ,proyectoSeleccionado.getArchivoLogo() ));
            }else{
                //System.out.println( "No existe:"+fileLogo);
            }
        }else{
            
            if(proyectoSeleccionado != null && proyectoSeleccionado.getArchivoLogo() != null && proyectoSeleccionado.getArchivoLogo() != null && FileHelper.existFile(fileLogo) && FileHelper.isFile(fileLogo)){
                setLogo(new DefaultStreamedContent(FileHelper.getFileInputStream(fileLogo), type ,proyectoSeleccionado.getArchivoLogo() ));
            }
        }
    }
    
    /*Guarda la imagen en la carpeta configfurada*/
    public boolean guadaImagenDirectorio(String to, String nameFile) throws Exception{
        return FileHelper.saveInputStreamFile(logo.getStream(), to, nameFile);
    }
    
    //Forma la url para el catalogo de tierndas.
    public String getStringUrlTiendas(){
        FacesContext context = FacesContext.getCurrentInstance();
        Codificacion cs = new Codificacion();
        
        String cadena = "";
        Map<String,String> params = null;
        params = context.getExternalContext().getRequestParameterMap();
        
        Integer attrProyectoId = Integer.parseInt(params.get("ProyectoId") == null ? "0" : params.get("ProyectoId"));
        String attrProyectoN = (params.get("ProyectoN")  == null ? "" : (String)params.get("ProyectoN"));
        
        cs.proceso("ProyectoId="+attrProyectoId+"&ProyectoN="+attrProyectoN+"&RegionesId="+regionesId+
                "&UnidadesNegocios="+unidadesNegocioId+"&TerritorioId="+territorioId+"&EquiposId="+equipoId+"&ClienteId="+clienteId, false);
        
        return "Tiendas?faces-redirect=true&includeViewParams=true&query="+cs.getResultado();
    }
    
    void mensajeAlerta(String mensaje){
        Utileria.mensajeAlerta(mensaje, mensaje);
    }
    
    public Date nowDate(){
        return DateHelper.getNow();
    }

    public boolean isFechaInicioHabilitada() {
        return fechaInicioHabilitada;
    }

    public void setFechaInicioHabilitada(boolean fechaInicioHabilitada) {
        this.fechaInicioHabilitada = fechaInicioHabilitada;
    }

    public boolean isFechaFinHabilitada() {
        return fechaFinHabilitada;
    }

    public void setFechaFinHabilitada(boolean fechaFinHabilitada) {
        this.fechaFinHabilitada = fechaFinHabilitada;
    }

    public boolean isFechaVisibleHabilitada() {
        return fechaVisibleHabilitada;
    }

    public void setFechaVisibleHabilitada(boolean fechaVisibleHabilitada) {
        this.fechaVisibleHabilitada = fechaVisibleHabilitada;
    }

    public boolean isBotonEditaHabilitado() {
        return botonEditaHabilitado;
    }

    public void setBotonEditaHabilitado(boolean botonEditaHabilitado) {
        this.botonEditaHabilitado = botonEditaHabilitado;
    }
    
    public String getSessionOID() {
        return sessionOID;
    }

    public void setSessionOID(String sessionOID) {
        this.sessionOID = sessionOID;
    }
    
    public ServiceProyectos getServiceProyectos() {
        return serviceProyectos;
    }

    public void setServiceProyectos(ServiceProyectos serviceProyectos) {
        this.serviceProyectos = serviceProyectos;
    }

    public ServiceParametros getServiceParametros() {
        return serviceParametros;
    }

    public void setServiceParametros(ServiceParametros serviceParametros) {
        this.serviceParametros = serviceParametros;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        MBProyectos.serialVersionUID = serialVersionUID;
    }

    public Integer getCantidadTiendas() {
        return cantidadTiendas;
    }

    public void setCantidadTiendas(Integer cantidadTiendas) {
        this.cantidadTiendas = cantidadTiendas;
    }

    public List<Proyectos> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyectos> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<Proyectos> getProyectosSeleccionados() {
        return proyectosSeleccionados;
    }

    public void setProyectosSeleccionados(List<Proyectos> proyectosSeleccionados) {
        this.proyectosSeleccionados = proyectosSeleccionados;
    }

    public List<Proyectos> getProyectosFiltrados() {
        return proyectosFiltrados;
    }

    public void setProyectosFiltrados(List<Proyectos> proyectosFiltrados) {
        this.proyectosFiltrados = proyectosFiltrados;
    }

    public Proyectos getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyectos proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public List<Encuestas> getListaEncuestas() {
        return listaEncuestas;
    }

    public void setListaEncuestas(List<Encuestas> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }

    public List<Encuestas> getListaEncuestasMostrar() {
        return listaEncuestasMostrar;
    }

    public void setListaEncuestasMostrar(List<Encuestas> listaEncuestasMostrar) {
        this.listaEncuestasMostrar = listaEncuestasMostrar;
    }

    public List<Encuestas> getListaEncuestasProyecto() {
        return listaEncuestasProyecto;
    }

    public void setListaEncuestasProyecto(List<Encuestas> listaEncuestasProyecto) {
        this.listaEncuestasProyecto = listaEncuestasProyecto;
    }

    public Encuestas getEncuestaSeleccionada() {
        return encuestaSeleccionada;
    }

    public void setEncuestaSeleccionada(Encuestas encuestaSeleccionada) {
        this.encuestaSeleccionada = encuestaSeleccionada;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPathImgProyectos() {
        return pathImgProyectos;
    }

    public void setPathImgProyectos(String pathImgProyectos) {
        this.pathImgProyectos = pathImgProyectos;
    }

    public String getProtocoloLeerImg() {
        return protocoloLeerImg;
    }

    public void setProtocoloLeerImg(String protocoloLeerImg) {
        this.protocoloLeerImg = protocoloLeerImg;
    }

    public String getProtocoloGuardarImg() {
        return protocoloGuardarImg;
    }

    public void setProtocoloGuardarImg(String protocoloGuardarImg) {
        this.protocoloGuardarImg = protocoloGuardarImg;
    }

    public Integer getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(Integer regionesId) {
        this.regionesId = regionesId;
    }

    public Integer getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(Integer territorioId) {
        this.territorioId = territorioId;
    }

    public Integer getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    public Integer getUnidadesNegocioId() {
        return unidadesNegocioId;
    }

    public void setUnidadesNegocioId(Integer unidadesNegocioId) {
        this.unidadesNegocioId = unidadesNegocioId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public StreamedContent getLogo() {
        return logo;
    }

    public void setLogo(StreamedContent logo) {
        this.logo = logo;
    }
    
    public Integer getIdEncuestaSeleccionada() {
        return idEncuestaSeleccionada;
    }

    public void setIdEncuestaSeleccionada(Integer idEncuestaSeleccionada) {
        this.idEncuestaSeleccionada = idEncuestaSeleccionada;
    }

    public List<Archivos> getListaArchivosProyecto() {
        return listaArchivosProyecto;
    }

    public void setListaArchivosProyecto(List<Archivos> listaArchivosProyecto) {
        this.listaArchivosProyecto = listaArchivosProyecto;
    }

    public Archivos getArchivoSelecccionado() {
        return archivoSelecccionado;
    }

    public void setArchivoSelecccionado(Archivos archivoSelecccionado) {
        this.archivoSelecccionado = archivoSelecccionado;
    }

    public StreamedContent getArchivoDescargar() {
        return archivoDescargar;
    }

    public void setArchivoDescargar(StreamedContent archivoDescargar) {
        this.archivoDescargar = archivoDescargar;
    }

    public Proyectos getProyectoSeleccionadoCopia() {
        return proyectoSeleccionadoCopia;
    }

    public void setProyectoSeleccionadoCopia(Proyectos proyectoSeleccionadoCopia) {
        this.proyectoSeleccionadoCopia = proyectoSeleccionadoCopia;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public ServicePromotores getServicePromotores() {
        return servicePromotores;
    }

    public void setServicePromotores(ServicePromotores servicePromotores) {
        this.servicePromotores = servicePromotores;
    }

    public ServiceEncuestas getServiceEncuestas() {
        return serviceEncuestas;
    }

    public void setServiceEncuestas(ServiceEncuestas serviceEncuestas) {
        this.serviceEncuestas = serviceEncuestas;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    
}
