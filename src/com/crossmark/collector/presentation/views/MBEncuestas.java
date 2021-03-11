/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Catalogos;
import com.crossmark.collector.business.domain.Condiciones;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.domain.Plantillas;
import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Respuestas;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.business.domain.SubCategorias;
import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.business.services.ServiceCategorias;
import com.crossmark.collector.business.services.ServiceEncuestas;
import com.crossmark.collector.business.services.ServiceOpciones;
import com.crossmark.collector.business.services.ServiceParametros;
import com.crossmark.collector.business.services.ServicePreguntas;
import com.crossmark.collector.business.services.ServiceSecciones;
import com.crossmark.collector.business.services.ServiceVariables;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.DynamicField;
import com.crossmark.collector.presentation.views.utils.FileHelper;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.extensions.event.ImageAreaSelectEvent;
import org.primefaces.extensions.event.ResizeEvent;
import org.primefaces.extensions.event.RotateEvent;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;


/**
 *
 * @author RIGG
 */
@ManagedBean
public class MBEncuestas implements Serializable {
    
    private static final long serialVersionUID = -1L;
    private Integer idEncuesta = 0;
    private String encuestaId = "0";
    List<Encuestas> listaEncuestas = new ArrayList<Encuestas>();
    List<Encuestas> listaEncuestasFiltradas = new ArrayList<Encuestas>();
    private ServiceEncuestas serviceEncuestas;
    private ServiceVariables serviceVariables;
    private Encuestas encuestaSeleccionada;
    private List<Encuestas> encuestaSeleccionadas;//add by paco
    private int tipoEncuesta;
    private int idPlantilla;
    private boolean muestraCombo = false;
    private boolean mostrarConfiguracionEncuesta = false;
    private boolean mostrarPlantilla = false;
    // ------CAMPOS PARA UNA NUEVA ENCUESTA-----
    
    private String claveEncuesta;
    private String gpsEncuesta;
    private int statusId;
    private boolean status;
    private boolean operacion;//Para el campo operacion de la encuesta
    private boolean esPlantilla;
    private Date fechaCreacionEncuesta;
    
    private Plantillas plantilla = new Plantillas();
    // UIComponent etiqueta = new CommandButton();
    private List<Plantillas> listaElementosPlantilla = new ArrayList<Plantillas>();
    private HtmlForm formulario;
    private HtmlPanelGrid painel;
    private String filtroTeste;
    private HtmlInputText itxOperador;
    private String nombreSeccion;
    DefaultMenuItem item = new DefaultMenuItem("External");
    private List<Secciones> miListaSecciones = new ArrayList<Secciones>();
    private Secciones secciones;
    private int idTipoLista;
    
    private String catalogosOID;
    private String preguntaOID;
    private List<Preguntas> listaPreguntaOrigen = new ArrayList<Preguntas>();
    private int proyectoId;
    private List<Secciones> listaSeccion = new ArrayList<Secciones>();
    private Secciones seccionSeleccionada;
    
    private ServiceSecciones serviceSecciones;
    
    private int idSubCategoria;
    
    private ServiceCategorias serviceCategorias;
    private List<SubCategorias> listaSubCategorias = new ArrayList<SubCategorias>();
    Preguntas misPreguntas;
    private String laSeccionOIDPregunta;
    private String laPreguntaOID;
    private List<Secciones> miListaS = new ArrayList<Secciones>();
    private List<Preguntas> miListaP = new ArrayList<Preguntas>();
    private List<Opciones> miListaO = new ArrayList<Opciones>();
    private Opciones misOpciones;
    private Secciones miSeccionPadre;
    
    private DataTable dataTable;
    private int idSeccionEncuesta;
    private short ordenSeccion;
    private boolean activaSeccion;
    private Date fechaCreacionSeccion;
    private String preguntaDominanteOID;
    private boolean tipoListaOpciones;
    private String listasFiltroOID;
    private short variable;
    private boolean controlUsuario;
    
    private String listasOID;
    private boolean ciclica;
    private boolean seleccionada;
    private boolean enabled;
    private int seleccionadaInt;
    private Integer miEncuesta;
    private TreeTable tablaArbol;
    private boolean muestraBoton = false;
    // valores para las preguntas
    private ServicePreguntas servicePreguntas;
    private String seccionOID;
    
    private int categoriaPregunta;
    
    private List<Respuestas> listaTipoRespuesta = new ArrayList<Respuestas>();
    private String nombreEncuestaPlantilla;
    private String observacionesEncuesta;
    private boolean esActivaEncuesta;
    private TabView tabView = new TabView();
    private TabView tabEdicion = new TabView();
    
    private String nombreBusquedaEncuesta;
    private String tipoBusquedaEncuesta;
    
    private String opcionAgregada = "";
    
    private String nombreEncuestaEditar;
    private String observacionEncuestaEditar;
    private boolean activaEncuestaEditar;
    
    private CommandButton botonEditaSeccion;
    private CommandLink link;
    private CommandButton botonEditaPregunta;
    private CommandButton botonEditaOpcion;
    
    private String miPreguntaOID = "";
    
    private Tab miTab;
    private PanelGrid miPanel;
    
    private List<Boolean> listaRenders = new ArrayList<Boolean>();
    
    private List<Opciones> listaOpciones = new ArrayList<Opciones>();
    
    //variables para configuracion de opciones
    private List<Opciones> listaOpcionesConf = new ArrayList<Opciones>();
    private Opciones opcionSelectedConf;
    private Encuestas encuestaConf;
    private Integer seleccionadaOpcConf;//Variable para Seleccionada y no seleccionada en Configuiracion de opciones
    //private Encuestas encuestaConfTmp;//Se guardara la la encuesta con las preguntas para no ir por esa info cada que se cambie de opcoin.
    
    private ServiceOpciones serviceOpciones;

    /* Campos para ConfigurarSeccion.xhtml */
    private String nombreSeccionConfigurar;
    private short ordenSeccionConfigurar;
    private int idTipoListaSeccionConfigurar;
    private String catalogosOIDSeccionConfigurar;
    private String preguntaOIDSeccionConfigurar;
    private int seleccionadaIntSeccionConfigurar;
    private String catalogoOIDSC;
    private String claveSeccionConfigurar;
    
    private boolean disableTipoLista = false;
    private boolean disableContorlUsuario = false;
    private boolean disableCatalogoOrigen = false;
    private boolean disablePreguntaOrigen = false;
    private boolean ciclicaSeccionConfigurar = false;
    private boolean controlUsuarioSeccionConfigurar = false;
    private boolean apareceCatalogoOrigen = false;
    private boolean aparecePreguntaOrigen = false;
    
    /* Campos para BancoPreguntas.xhtml */
    private int idCategoriaBanco;
    private int idSubCategoriaBanco;
    private String miSeccionOIDBanco;
    private List<Preguntas> preguntasSeleccionada;
    private List<Preguntas> listaPreguntasBanco = new ArrayList<Preguntas>();

    /* Campos para PlantillaDinamica.xhtml */
    private String miSeccionOID = "";
    private String nombreEncuesta;
    private String observacionEncuesta;
    private boolean activa;
    public Encuestas laEncuesta;
    private boolean apareceCajaTexto = false;
    private boolean apareceRadio = false;
    private boolean apareceAreaTexto = false;
    private boolean apareceCalendario = false;
    private boolean apareceCheckBox = false;
    private boolean apareceUpload = false;
    private boolean apareceImagen = false;
    private String opcion;
    private String[] opcionCheck;
    private DynaFormModel model;

    /* Campos para ConfigurarPregunta.xhtml */
    private String miSeccionOIDPregunta = "";
    private List<Secciones> seccionesPorEncuesta = new ArrayList<Secciones>();
    private int ordenPregunta;
    private String textoPregunta;
    private int idCategoria;
    private int subCategoriaPregunta;
    private String mensajePregunta;
    private String variablePregunta;
    private String alertaPregunta;
    private int idTipoRespuesta;
    private boolean requeridaPregunta;
    private boolean bancoPreguntas;
    private boolean verTexto = false;
    private boolean verEntero = false;
    private boolean verDecimal = false;
    private boolean verInstruccion = false;
    private boolean verCheck = false;
    private boolean verLosMultiples = false;
    private boolean verOpcionMultiple = false;
    private boolean verSeleccionMultiple = false;
    private boolean verCodigoBarras = false;
    private boolean verAgregarOpcionOtro = false;
    private boolean opcionOtros = false;
    private boolean condicionada = false;
    private int longitudMaxima = 0;
    private int valorMaximo = 0;
    private int valorMinimo = 0;
    private int numeroDecimales = 0;
    private String archivoImagen;
    private UploadedFile archivoPreguntas;
    private boolean activaPregunta;
    private boolean enabledPregunta;
    private boolean visible;
    private String valorAlertaPregunta;
    private int idTipoListaConfiguraPregunta;
    private String catalogosOIDOpciones;
    private List<Catalogos> listaCatalogos = new ArrayList<Catalogos>();
    private List<Catalogos> listaFiltraPor = new ArrayList<Catalogos>();
    private List<Preguntas> listaPreguntas = new ArrayList<Preguntas>();
    private List<Variable> listaVariablesFiltro = new ArrayList<Variable>();
    private String listasOIDFiltro = "";
    private Integer variablePreguntaOpcionMultiple;
    private InputTextarea inputTextArea;
    private InputText inputText;
    private SelectOneRadio radio;
    private SelectManyCheckbox muchosCheck;
    private boolean seleccionFiltro;
    private boolean mostrarCampos = true;
    // ----esto se camibara a false despues
    private boolean mostrarCheckFiltro = true;
    private boolean codigoBarras = false;
    private boolean deshabilitaInputOpcion = true;
    private boolean deshabilitaBotonOpcion = true;
    private boolean deshabilitaSeleccion = true;
    private boolean deshabilitaCatalogos = true;
    private boolean sePuedeSeleccionar = false;
    private boolean sePuedeEditar = false;
    private boolean deshabilitaSeleccionFiltro = true;
    private boolean deshabilitaPreguntas = true;
    private boolean deshabilitaCondicionada = true;
    private boolean deshabilitaCodigoBarras = true;
    private boolean deshabilitaOpcionOtros = true;
    private boolean deshabilitaConfigOpciones = true;
    private boolean deshabilitaSeleccionMinima = true;
    
    private String preguntasOIDOpciones = "";
    private List<Preguntas> listaPreguntasCU = new ArrayList<Preguntas>();//Pregunta inciala para el select control usuario
    private String imageUrl;

    private int idTipoListaSeleccionMultiple = 0;
    private String catalogosOIDSeleccionMultiple = "";
    private boolean seleccionFiltroSM;
    private String listasOIDFiltroSM = "";
    private String opcionAgregadaSM = "";
    private int valorMinimoSM = 0;
    private boolean deshabilitaSeleccionFiltroSM = true;
    private String protocoloLeerImg;
    private String protocoloGuardarImg;
    private String urlImage;
    private String pathImgEncuestas;
    StreamedContent logo;
    
    private DefaultStreamedContent imgInstruccion;
    
    private List<String> listaAgregaOpciones = new ArrayList<String>();
    
    /* Campos para GuardaComoPlantilla.xhtml */
    private String nombrePlantilla;
    
    private int tipoRespuestaPregunta;
    private ServiceParametros serviceParametros;
    private String pathImagePreguntas;
    
    private Integer unidadesNegocioId;
    private boolean deshabilitaPreguntaInicioCU;
    
    
    private boolean deshabilitaComboPlantilla;
    
    private String query;
    
    /**
     * Inicializa los datos necesarios.
     **/
    @PostConstruct
    public void init(){
        
        UsuarioSession usSistema = null;
        
        usSistema = Utileria.getSessionAttribute("userLoged");
        if(usSistema != null){
            Utileria.logger(getClass()).info("usSistema.getOID():"+usSistema.getOID());
        }
        
        List<Parametros> listaParametros = serviceParametros.getParametrosReporting(null,null);
        if(listaParametros != null && !listaParametros.isEmpty())
        for(Parametros item : listaParametros){
            
            if(item.getNombre().equalsIgnoreCase("PathImagePreguntas")){
            	pathImagePreguntas = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("ProtocoloLeerImg")){
                protocoloLeerImg = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("ProtocoloGuardarImg")){
                protocoloGuardarImg = item.getValor();
            }
            if(item.getNombre().equalsIgnoreCase("URLImageProdReporting")){
                urlImage = item.getValor();
            }
        }
        
        Utileria.logger(getClass()).info("urlImage:"+urlImage+"     urlImage:"+pathImagePreguntas);
        
    }
    
    /**
     * Procesa los datos de la url.
     **/
    public void procesaQuery(){
        
        Codificacion cs = new Codificacion();
        
        if(query != null){
            cs.proceso(query,true);
            Map<String,String> urlParameters = cs.obtenerParametros(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                if(element.getKey().equals("UnidadesNegocios")){
                    this.setUnidadesNegocioId(Integer.parseInt(element.getValue()));
                }
            }
        }
    }
    
    
    
    public List<Encuestas> obtenerEncuestas() {

        return listaEncuestas;
    }
    
    /* Campos para EligeTipoEncuesta.xhtml */
    List<Encuestas> listaPlantillas = new ArrayList<Encuestas>();

    /**
     * Busca las encuestas y las guarda en la variable listaEncuestas.
     **/
    public void buscaEncuesta() {
        
        boolean plantilla = false;
        claveEncuesta = "";
        gpsEncuesta = "";
        activa = true;
        fechaCreacionEncuesta = null;
        observacionEncuesta = null;
        proyectoId = 0;
        
        if (tipoBusquedaEncuesta.equals("")) {
            Utileria.mensajeAlerta("Debe seleccionar un tipo de búsqueda");
        } else {
            if (tipoBusquedaEncuesta.equals("2")) {
                plantilla = true;
            }
            this.setListaEncuestas(serviceEncuestas.listaEncuestas(idEncuesta,
                    nombreBusquedaEncuesta, claveEncuesta, gpsEncuesta,
                    statusId, plantilla, activa, fechaCreacionEncuesta,
                    observacionEncuesta, proyectoId, this.unidadesNegocioId));
        }
    }

    /**
     * Obtiene las secciones de una encuesta.
     * @param  Identificador de la encuesta.
     * @return List<Secciones>  Lista de secciones de la encuesta.
     **/
    private List<Secciones> obtenerSeccionesEncuesta(int miIdEncuesta) {

        short peq = 0;

        return serviceSecciones.listaSecciones("", "", miIdEncuesta, peq, true,
                false, false, null, false);

    }
    
    public void onTabChange(TabChangeEvent event) {
        //Codigo al hacer el change de la seccion en la encuesta.
    }
    
    /**
     * Obtiene los datos para el tipo de lista seleccionada en una seccion o en una pregunta.
     * las listas son de 2 tipos, en base catalogos (Catalogos de opciones) y preguntas.
     * Dependiendo del tipo de lista se activan y desactivan campos en la vista.
     **/
    public void tipoDeLista() {
        
        if (this.getIdTipoListaSeccionConfigurar() == 1) {

            this.disableCatalogoOrigen = false;
            this.disablePreguntaOrigen = true;
            this.apareceCatalogoOrigen=false;
            this.aparecePreguntaOrigen = true;
            
            String misListasOID = "";
            boolean catalogo = false;
            
            this.setListaPreguntaOrigen(new ArrayList());
            this.preguntaOIDSeccionConfigurar = null;
            
            obtieneListaCatalogos(misListasOID,catalogo, idTipoListaSeccionConfigurar);
        } else if (this.getIdTipoListaSeccionConfigurar() == 2) {
            
            this.disableCatalogoOrigen = true;
            this.disablePreguntaOrigen = false;
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = false;
            
            this.setListaCatalogos(new ArrayList());
            this.catalogoOIDSC = null;
            
            obtieneListaPreguntasOrigen(miSeccionPadre != null ? miSeccionPadre.getSeccionOID() : null, this.getIdEncuesta(), this.ordenSeccionConfigurar,0);
        }
        
    }
    
    
    public void obtieneListaCatalogos(String misListasOID,boolean catalogo, Integer idTipoListaSeccionConf){
        this.setListaCatalogos(serviceEncuestas.listaCatalogos(misListasOID, catalogo, idTipoListaSeccionConf));
    }
    
    public void obtieneListaPreguntasOrigen(String seccionPadreOID, Integer encuestaId,int ordenSeccion, int ordenPregunta){
        this.setListaPreguntaOrigen(serviceEncuestas.listaPreguntaOrigen(seccionPadreOID, encuestaId, ordenSeccion,ordenPregunta));
    }
    
    public void traeSubCategorias() {
        if (idCategoria == 0) {
            Utileria.mensajeAlerta("Debe elegir una categoría");
        } else {
            subCategoriasList(this.idCategoria, null);
        }
    }
    
    public void subCategoriasList(Integer categoriaId, Integer subCategoriaId){
        this.setListaSubCategorias(serviceCategorias.listaSubCategorias(categoriaId, subCategoriaId));
    }
    
    /*@SubCategoriasId tinyint=null,
	@CategoriasId tinyint = null*/
    
    
    /**
     * Elimina las encuestas que se encuentran en la variable encuestaSeleccionadas, son las que se seleccionaron en la vista.
     * Muestra un mensaje de alerta de las en cuestas eliminadas.
     * Recarga el listado de encuestas activas.
     **/
    public void eliminaEncuesta() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        if(encuestaSeleccionadas != null && !encuestaSeleccionadas.isEmpty() && encuestaSeleccionadas.size() > 0){
            String mensaje = "";
            for(Encuestas item : encuestaSeleccionadas){
                String delete = delEncuesta(item.getEncuestasId());
                if(!delete.equals("0")){
                    mensaje += "\n"+item.getClaveEncuesta()+": "+delete;
                }
            }
            Utileria.mensajeAlerta(context, "Se han eliminado las encuestas.");
            this.traemosEncuestas();
        }else{
            Utileria.mensajeAlerta(context, "Para eliminar debe seleccionar almenos una encuesta.");
        }
    }
    
    /**
     * Elimina una encuesta.
     * @param encuestaId Identificador de la encuesta a eliminar.
     * @return retorno String con valor 0 si la encuesta fue eliminada.Y un mensaje de error en caso de no ser eliminada.
     **/
    String delEncuesta(Integer encuestaId){
        String retorno = "";
        try {
            retorno = serviceEncuestas.eliminaEncuesta(encuestaId);
        } catch (Exception ex) {
            Utileria.logger(getClass()).info("No se elimina " + ex.getMessage());
        }
        return retorno;
    }
    
    /**
     * Inicializa las variables para ser llenados por los buevos valores al recargar el grid.
     **/
    public void loadRefresh(){
       listaEncuestas = new ArrayList();
       listaEncuestasFiltradas = new ArrayList();
       encuestaSeleccionada = new Encuestas();
       encuestaSeleccionadas = new ArrayList();
        traemosEncuestas();
    }
    
    /**
     * Obtiene la lista de las encuestas activas de acuerdo a los criterios de busqueda.
     **/
    public String traemosEncuestas() {
        
        nombreEncuesta = "";
        claveEncuesta = "";
        gpsEncuesta = "";
        statusId = 0;
        esPlantilla = false;
        activa = true;
        fechaCreacionEncuesta = null;
        observacionEncuesta = null;
        proyectoId = 0;
        
        this.setListaEncuestas(serviceEncuestas.listaEncuestas(0,
                nombreEncuesta, claveEncuesta, gpsEncuesta, statusId,
                esPlantilla, activa, fechaCreacionEncuesta,
                observacionEncuesta, proyectoId, unidadesNegocioId));

        return "";

    }
    
    /**
     * Obtiene la lista de las encuestas tipo plantilla de acuerdo a los criterios de busqueda.
     **/
    public String traemosPlantillas() {
        
        this.setListaPlantillas(serviceEncuestas.listaEncuestas(0, "", "", "",
                0, true, true, null, null, 0, this.unidadesNegocioId));

        return "";

    }
    
    /**
     * Limpia las variables para crear una nueva encuesta.
     **/
    public void limpiarNuevo(){
        
        this.encuestaSeleccionadas = new ArrayList<>();
        this.setIdEncuesta(null);
        this.setNombreEncuesta("");
        this.setObservacionEncuesta("");
        eventCrearTipoEncuesta();
    }
    
    
    public void encuestaVistaPrevia() {
        encuestaSeleccionada = null;
        if(encuestaSeleccionadas != null && !encuestaSeleccionadas.isEmpty() && encuestaSeleccionadas.size() == 1){
            
            this.encuestaSeleccionada = encuestaSeleccionadas.get(0);
                laEncuesta = serviceEncuestas.traerEncuesta(this.encuestaSeleccionada
                .getEncuestasId());
            List<Secciones> laListaS = laEncuesta.getListaSecciones();
            this.setIdEncuesta(this.encuestaSeleccionada.getEncuestasId());
            this.setNombreEncuesta(laEncuesta.getNombreEncuesta());
            this.setObservacionEncuesta(laEncuesta.getObservaciones());
            //this.setActiva(laEncuesta.isActiva());
            
            this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
            this.setStatusId(laEncuesta.getStatusEncuesta());
            this.setStatus((laEncuesta.getStatusEncuesta() == 1 ? true : false));//StatusId=1->Activo, StatusId=2->Inactivo
            
            this.setNombrePlantilla("Plantilla-" + this.getNombreEncuesta());
            
            this.seccionesPorEncuesta = this
                .obtenerSeccionesEncuesta(this.encuestaSeleccionada
                        .getEncuestasId());
            
            //RequestContext.getCurrentInstance().execute("PF('plantillaDinamica').show()");
            
        }else{
            Utileria.mensajeAlerta("Para vista previa debe seleccionar una encuesta.");
            //mensajeAlerta("Para editar debe seleccionar una encuesta.");
        }
        // laEncuesta.getListaSecciones().clear();
    }
    
    /**
     * Obtiene los datos de la encuesta seleccionada en el grid de la vista.
     * Este metodo se llama al darle editar encuesta.
     **/
    public void encuestaEditar() {
        
        encuestaSeleccionada = null;
        this.seccionesPorEncuesta = new ArrayList();
        if(encuestaSeleccionadas != null && !encuestaSeleccionadas.isEmpty() && encuestaSeleccionadas.size() == 1){
            
            this.encuestaSeleccionada = encuestaSeleccionadas.get(0);
            laEncuesta = serviceEncuestas.traerEncuesta(this.encuestaSeleccionada.getEncuestasId());
            List<Secciones> laListaS = laEncuesta.getListaSecciones();
            this.setIdEncuesta(this.encuestaSeleccionada.getEncuestasId());
            this.setNombreEncuesta(laEncuesta.getNombreEncuesta());
            this.setObservacionEncuesta(laEncuesta.getObservaciones());
            this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
            this.setOperacion(laEncuesta.isOperacion());
            //this.setStatusId(laEncuesta.getStatusEncuesta());
            //this.setStatus((laEncuesta.getStatusEncuesta() == 1 ? true : false));//StatusId=1->Activo, StatusId=2->Inactivo
            //private int statusId;
            this.setNombrePlantilla("Plantilla-" + this.getNombreEncuesta());
            
            this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(this.encuestaSeleccionada.getEncuestasId());
            
            RequestContext.getCurrentInstance().execute("PF('plantillaDinamica').show()");
            
        }else{
            Utileria.mensajeAlerta("Para editar debe seleccionar una encuesta.");
            //mensajeAlerta("Para editar debe seleccionar una encuesta.");
        }
        // laEncuesta.getListaSecciones().clear();
    }
    
    /**
     *  Obtiene la lista de subcategorias de la categoria seleccionada en la seccion de preguntas.
     **/
    public void traeSubCategoriasBanco() {
        if (idCategoriaBanco == 0) {
            Utileria.mensajeAlerta("Debe elegir una categoria");
        } else {
            this.setListaSubCategorias(serviceCategorias.listaSubCategorias(idCategoriaBanco, null));
        }
    }
    
    /**
     *  Obtiene la lista de preguntas que pertenecen al banco de preguntas.
     **/
    public void listaBancoPreguntas() {
        preguntasSeleccionada = new ArrayList();//Para que se quede valio la lista de preguntas seleccinoadas.
        this.setListaPreguntasBanco(servicePreguntas.listaPreguntas("", "",true, idSubCategoriaBanco));
    }
    
    public void eventBancoPreguntas() {
        String validacion = validaEncuestaGuardada();
        this.idCategoriaBanco = 0;
        this.idSubCategoriaBanco = 0;
        this.miSeccionOIDBanco = null;
        this.preguntasSeleccionada = new ArrayList();
        if (validacion.equals("")) {
            listaBancoPreguntas();
            RequestContext.getCurrentInstance().execute("PF('bancoPreguntas').show()");
        }else{
            Utileria.mensajeAlerta(validacion);
        }
    }
    
    public void agregarPreguntaBanco() {
        String validaSeleccion = "";
        
        if (this.preguntasSeleccionada != null && !this.preguntasSeleccionada.isEmpty() && this.preguntasSeleccionada.size() > 0) {
            
            for(Preguntas item : preguntasSeleccionada){
                if(item.getSeccionOIDSelect() == null || item.getSeccionOIDSelect().equals("") || item.getSeccionOIDSelect().equals("0")){
                    validaSeleccion += "\nLa pregunta "+item.getDescripcionPregunta() + " no le selecciono una sección.";
                }
            }
            if(validaSeleccion.equals("")){
                for(Preguntas item : preguntasSeleccionada){
                    String unaPreguntaOID = agregarPreguntaDeBanco(item);
                }
                
                laEncuesta = serviceEncuestas.traerEncuesta(this.getIdEncuesta());
                
                this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(this.encuestaSeleccionada.getEncuestasId());
                
                //Utileria.mensajeAlerta("Preguntas agregadas.");
                RequestContext.getCurrentInstance().execute("PF('bancoPreguntas').hide()");
            }else{
                Utileria.mensajeAlerta("Seleccione una sección a todas las preguntas seleccionadas.");
            }
        }else{
            Utileria.mensajeAlerta("Debe seleccionar una pregunta.");
        }
    }
    
    public String agregarPreguntaDeBanco(Preguntas item){
        String unaPreguntaOID = "";
        try {
            //idTipoListaConfiguraPregunta
            unaPreguntaOID = getServicePreguntas().preguntaAgregada("",
                item.getSeccionOIDSelect(),
                item.getDescripcionPregunta(),
                item.getMensajesPregunta(),
                item.getOrden(),
                item.isActiva(),
                item.isEnabled(),
                item.isVisible(),
                item.getVariable(),
                item.getIdTipoPregunta(),
                item.getAlerta(),
                item.getIdSubCategoria(), false,
                0, true, 0, item.getValorMinimo(),
                item.getValorMaximo(),
                item.getNumeroDecimales(), item.getArchivoImagen(), item.getValorAlerta());
            
            
            if ((item.getIdTipoPregunta() == 6 || item.getIdTipoPregunta() == 7 || item.getIdTipoPregunta() == 9) && !unaPreguntaOID.equals("") ) {
                //Inicializa los valores de la pregunta null
                this.catalogosOIDOpciones = "";
                this.preguntasOIDOpciones = "";
                this.idTipoListaConfiguraPregunta = 0;
                this.seleccionadaInt = 0;
                this.opcionOtros = false;
                this.listasOIDFiltro = "";
                this.variablePreguntaOpcionMultiple = 0;
                this.codigoBarras = false;
                this.seleccionadaInt = 0;
                
                //Asigna los nuevo valores
                this.catalogosOIDOpciones = item.getListaOID();
                this.preguntasOIDOpciones = item.getPreguntasDominanteOID();
                this.idTipoListaConfiguraPregunta = item.getTipoListasOpcionesId();
                this.seleccionadaInt = 0;
                this.opcionOtros = item.isOtros();
                this.listasOIDFiltro = item.getListasFiltroOID();
                this.variablePreguntaOpcionMultiple = item.getVariableFiltro();
                
                //Utileria.logger(getClass()).info("Asignando valores nueva preunta");
                codigoBarras = item.isCodigoBarra();
                if(this.catalogosOIDOpciones != null && !"".equals(this.catalogosOIDOpciones)){
                    //Utileria.logger(getClass()).info("Asignando catalogosOIDOpciones:");
                    traerListaOpciones();
                }
                if(this.preguntasOIDOpciones != null && !item.getPreguntasDominanteOID().equals("")){
                    //Utileria.logger(getClass()).info("Asignando preguntasOIDOpciones:");
                    traerListaPreguntas();
                    if(listaPreguntas != null && !listaPreguntas.isEmpty() ){
                        this.seleccionadaInt = Integer.parseInt(String.valueOf( (listaPreguntas.get(0).isSeleccionadas() ? 1 : listaPreguntas.get(0).isSeleccionadas() ? 1 : 0 ) ) );
                    }
                }
                duplicarOpciones();
                guardarOpciones(unaPreguntaOID);
            }
            
        } catch (Exception ex) {
            unaPreguntaOID = "";
        }
        return unaPreguntaOID;
    }
    
    public List<Opciones> duplicarOpciones(){
        
        for(int i = 0; i < listaOpciones.size(); i++)
        {
            listaOpciones.get(i).setOpcionesOID(null);
        }
        return listaOpciones;
    }
    
    public void eventLinkConfigurarOpciones() {
        String validaciones = "";
        
        String opcionesOID = "";
        this.listaOpcionesConf = new ArrayList();
        Opciones item = new Opciones();
        this.opcionSelectedConf = item;
        this.seleccionadaOpcConf = null;
        
        if(this.miPreguntaOID == null || miPreguntaOID.equals("")){
            validaciones = "Para poder configurar primero debe guardar la pregunta.";
        }
        if(listaOpciones != null && !listaOpciones.isEmpty() && listaOpciones.size() > 0 && validaciones.equals("")){
            //Traer las opciones guardadas
            this.listaOpcionesConf = serviceOpciones.traerOpciones(this.catalogosOIDOpciones, opcionesOID);//TRae la lista de opciones de la pregunta.
            
            this.encuestaConf = serviceEncuestas.traerEncuestaSeccionesPregunta(this.encuestaSeleccionada.getEncuestasId(), this.miPreguntaOID );//TRae la lista de preguntas de la encuesta.
            
            recorreEncuestaSeccionPreguntasJsonFalse();
            RequestContext.getCurrentInstance().execute("PF('configOpciones').show()");
            
        }else{
            if(validaciones.equals("")){
                Utileria.mensajeAlerta("No hay opciónes guardadas.");
            }else{
                Utileria.mensajeAlerta(validaciones);
            }
        }
    }
    
    public void guardaOrdenPregunta() {
        ordenPregunta = ordenPregunta;
    }
    
    public void guardaTextoPregunta() {
        textoPregunta = textoPregunta;
    }
    
    public void condicionadaChecked() {
        String summary = condicionada ? "Checked" : "Unchecked";
        
        if (summary.equals("Checked")) {
            deshabilitaConfigOpciones = false;
        } else {
            deshabilitaConfigOpciones = true;
        }
    }
    
    public void addMessage() {
        String summary = seleccionFiltro ? "Checked" : "Unchecked";
        
        if (summary.equals("Checked")) {
            mostrarCampos = false;
        } else {
            mostrarCampos = true;
        }
    }
    
    public void eventCheckedPregSeccionSel(AjaxBehaviorEvent e){
        
        Secciones itemSeccion = (Secciones) e.getComponent().getAttributes().get("seccion");
        
        if (itemSeccion.isHabilitadoOk()) {
            itemSeccion.setDeshabilitadoOk(false);
            
            //Selecciona todas las preguntas de la seccion.
            if(itemSeccion.getMiListaPreguntas() != null && !itemSeccion.getMiListaPreguntas().isEmpty()){
                for(Preguntas item : itemSeccion.getMiListaPreguntas()){
                    
                    item.setHabilitadoOk(true);
                    item.setDeshabilitadoOk(false);
                }
            }
        } else {
            //Quita selecciona a todas las preguntas de la seccion.
            if(itemSeccion.getMiListaPreguntas() != null && !itemSeccion.getMiListaPreguntas().isEmpty()){
                for(Preguntas item : itemSeccion.getMiListaPreguntas()){
                    
                    item.setHabilitadoOk(false);
                }
            }
        }
    }
    
    public void eventCheckedPregSeccionNoSel(AjaxBehaviorEvent e){
        Secciones itemSeccion = (Secciones) e.getComponent().getAttributes().get("seccion");
        
        if (itemSeccion.isDeshabilitadoOk()) {
            itemSeccion.setHabilitadoOk(false);
            //Selecciona todas las preguntas de la seccion.
            if(itemSeccion.getMiListaPreguntas() != null && !itemSeccion.getMiListaPreguntas().isEmpty()){
                for(Preguntas item : itemSeccion.getMiListaPreguntas()){
                    item.setDeshabilitadoOk(true);
                    item.setHabilitadoOk(false);
                }
            }
        } else {
            //Quita selecciona a todas las preguntas de la seccion.
            if(itemSeccion.getMiListaPreguntas() != null && !itemSeccion.getMiListaPreguntas().isEmpty()){
                for(Preguntas item : itemSeccion.getMiListaPreguntas()){
                    item.setDeshabilitadoOk(false);
                }
            }
        }
    }
    
    public void eventCheckedPregSel(AjaxBehaviorEvent e){
        Preguntas itemPreg = (Preguntas) e.getComponent().getAttributes().get("pregunta");
        
        if (itemPreg.isHabilitadoOk()) {
            itemPreg.setDeshabilitadoOk(false);
        }
    }
    
    public void eventCheckedPregNoSel(AjaxBehaviorEvent e){
        Preguntas itemPreg = (Preguntas) e.getComponent().getAttributes().get("pregunta");
        
        if (itemPreg.isDeshabilitadoOk()) {
            itemPreg.setHabilitadoOk(false);
        }
    }
    
    public void eventChangeOption(ActionEvent e){
        
        Opciones itemSeccion = (Opciones) e.getComponent().getAttributes().get("opcion");
        opcionSelectedConf = itemSeccion;
        seleccionadaOpcConf = null;
        recorreEncuestaSeccionPreguntasJsonFalse();
        
    }
    
    public void eventSelectRowOpcion(SelectEvent event){
        
        Opciones item = (Opciones) event.getObject();
        this.opcionSelectedConf = item;
        this.seleccionadaOpcConf = null;
        recorreEncuestaSeccionPreguntasJsonFalse();
        
    }
    
    public void eventCancelSel(ActionEvent actionEvent){
        
        if(seleccionadaOpcConf != null){
            if(seleccionadaOpcConf == 0){
                seleccionadaOpcConf = 1;
            }else{
                seleccionadaOpcConf = 0;
            }
        }
    }
    
    public void eventCrearTipoEncuesta(){
        
        if(tipoEncuesta == 0 || tipoEncuesta == 1){
            this.deshabilitaComboPlantilla = true;
        }else{
            this.deshabilitaComboPlantilla = false;
        }
    }
    
    public void eventChangeRadioSel(){
        //RequestContext.getCurrentInstance().execute("PF('confirmDialogSel').show()");
    }
    
    public void traeaListaPreguntasSecciones(){
        String validaciones = "";
        if(opcionSelectedConf == null){
            validaciones = "Debe de seleccionar una opción.";
        }
        
        recorreEncuestaSeccionPreguntasJsonFalse();
        boolean seleccionadaConf = (this.seleccionadaOpcConf == 0 ? false : true);
        //seleccionadaOpcConf
        List<Condiciones> condiciones = new ArrayList();
        condiciones = serviceEncuestas.getListCondiciones(this.miSeccionOIDPregunta, this.miPreguntaOID, this.opcionSelectedConf.getOpcionesOID(), seleccionadaConf);
        
        if(condiciones != null && !condiciones.isEmpty()){
            for(Condiciones item1 : condiciones){
                
                //PAra seleccionar las secciones
                if(item1.getSeccionesOID() != null && (item1.getPreguntasOID() == null || item1.getPreguntasOID().equals("") )){
                    if( this.encuestaConf != null && this.encuestaConf.getListaSecciones() != null && 
                            !this.encuestaConf.getListaSecciones().isEmpty() && validaciones.equals("")){
                        for(Secciones seccion : this.encuestaConf.getListaSecciones()){
                            if(seccion.getSeccionOID().equals(item1.getSeccionesOID())){
                                if(item1.getHabilitar() != null && item1.getHabilitar() == 1){
                                    seccion.setHabilitadoOk(true);
                                    seccion.setDeshabilitadoOk(false);
                                }else{
                                    seccion.setHabilitadoOk(false);
                                    seccion.setDeshabilitadoOk(true);
                                }
                            }
                        }
                    }
                }
                
                //PAra seleccionar las preguntas
                if(item1.getPreguntasOID() != null && !item1.getPreguntasOID().equals("")){
                    if( this.encuestaConf != null && this.encuestaConf.getListaSecciones() != null && !this.encuestaConf.getListaSecciones().isEmpty() && validaciones.equals("")){
                        for(Secciones seccion : this.encuestaConf.getListaSecciones()){
                            
                            if(seccion.getMiListaPreguntas() != null && !seccion.getMiListaPreguntas().isEmpty()){
                                for(Preguntas pregunta : seccion.getMiListaPreguntas()){
                                    
                                    if(pregunta.getPreguntaOID().equals(item1.getPreguntasOID())){
                                        if(item1.getHabilitar() != null && item1.getHabilitar() == 1){
                                            pregunta.setHabilitadoOk(true);
                                            pregunta.setDeshabilitadoOk(false);
                                        }else{
                                            pregunta.setHabilitadoOk(false);
                                            pregunta.setDeshabilitadoOk(true);
                                        }
                                    }
                                    
                                }
                            }
                            
                            
                        }
                    }
                }
                
            }
        }else{
            if(!validaciones.equals("")){
                Utileria.mensajeAlerta(validaciones);
            }
        }
        
    }
    
    //PAra poner todos los checks del arregloen false
    public void recorreEncuestaSeccionPreguntasJsonFalse(){
        if(this.encuestaConf != null && this.encuestaConf.getListaSecciones() != null && !this.encuestaConf.getListaSecciones().isEmpty()){
            for(Secciones seccion : this.encuestaConf.getListaSecciones()){
                seccion.setHabilitadoOk(false);
                seccion.setDeshabilitadoOk(false);
                if(seccion.getMiListaPreguntas() != null && !seccion.getMiListaPreguntas().isEmpty()){
                    for(Preguntas pregunta : seccion.getMiListaPreguntas()){
                        pregunta.setHabilitadoOk(false);
                        pregunta.setDeshabilitadoOk(false);
                    }
                }
            }
        }
    }
    
    //Metodo para guardar
    public void guardarConfOpciones(){
        
        String validaciones = validaGuardarConfOpciones();
        if(validaciones.equals("")){
            boolean seleccionadaConf = (this.seleccionadaOpcConf == 0 ? false : true);
            
            Condiciones condicionPregunta = new Condiciones();
            condicionPregunta.setPreguntasOID(this.miPreguntaOID);
            condicionPregunta.setOpcionesOID(opcionSelectedConf.getOpcionesOID());
            condicionPregunta.setSeccionesOID(this.miSeccionOIDPregunta);
            condicionPregunta.setSeleccionadas(seleccionadaConf);
            eliminarCondiciones(condicionPregunta);
            
            //Inserta Condiciones
            if(this.encuestaConf != null && this.encuestaConf.getListaSecciones() != null && !this.encuestaConf.getListaSecciones().isEmpty()){
                for(Secciones seccion : this.encuestaConf.getListaSecciones()){
                    
                    Condiciones condicionSeccion = new Condiciones();
                    condicionSeccion.setSeccionesOID(this.miSeccionOIDPregunta);
                    condicionSeccion.setPreguntasOID(this.miPreguntaOID);
                    condicionSeccion.setOpcionesOID(opcionSelectedConf.getOpcionesOID());
                    condicionSeccion.setSeccionesAccionesOID(seccion.getSeccionOID());
                    condicionSeccion.setPreguntasAccionesOID(null);
                    condicionSeccion.setSeleccionadas(seleccionadaConf);
                    
                    if(seccion.isHabilitadoOk() ){
                        condicionSeccion.setHabilitar(1);//PAra habilitar seleccionado y no seleccionado
                        creaCondiciones(condicionSeccion);
                    }
                    if(seccion.isDeshabilitadoOk() ){
                        
                        condicionSeccion.setHabilitar(0);//PAra habilitar seleccionado y no seleccionado
                        creaCondiciones(condicionSeccion);
                    }
                    
                    if(seccion.getMiListaPreguntas() != null && !seccion.getMiListaPreguntas().isEmpty()){
                        for(Preguntas pregunta : seccion.getMiListaPreguntas()){
                            
                            Condiciones condicionPreg = new Condiciones();
                            condicionPreg.setSeccionesOID(this.miSeccionOIDPregunta);
                            condicionPreg.setPreguntasOID(this.miPreguntaOID);
                            condicionPreg.setOpcionesOID(opcionSelectedConf.getOpcionesOID());
                            condicionPreg.setSeccionesAccionesOID(pregunta.getSeccionOID());
                            condicionPreg.setPreguntasAccionesOID(pregunta.getPreguntaOID());
                            condicionPreg.setSeleccionadas(seleccionadaConf);
                            if(pregunta.isHabilitadoOk() ){
                                
                                condicionPreg.setHabilitar(1);//PAra habilitar seleccionado y no seleccionado
                                creaCondiciones(condicionPreg);
                            }
                            if(pregunta.isDeshabilitadoOk() ){
                                
                                condicionPreg.setHabilitar(0);//PAra habilitar seleccionado y no seleccionado
                                creaCondiciones(condicionPreg);
                            }
                            
                        }
                    }
                }
            }
            
            //RequestContext.getCurrentInstance().execute("PF('configOpciones').hide()");
            Utileria.mensajeSatisfactorio("La configuración ha sido guardada.");
        }else{
            Utileria.mensajeAlerta(validaciones);
        }
    }
    
    public void eliminarCondiciones(Condiciones condicion){
        serviceEncuestas.eliminarCondicion(condicion);
    }
    
    public void creaCondiciones(Condiciones condicion){
        serviceEncuestas.crearCondicion(condicion);
    }
    
    public String validaGuardarConfOpciones(){
        
        String retorno = "";
        boolean opcionChecked = false;
        
         
        boolean seleccionadaConf = (this.seleccionadaOpcConf == 0 ? false : true);
        if(this.seleccionadaOpcConf == null || (this.seleccionadaOpcConf != 0 && this.seleccionadaOpcConf != 1)){
            retorno = "Seleccionadas o no seleccionadas es requerido.";
        }
        if(this.opcionSelectedConf != null && retorno.equals("")){
            if(encuestaConf != null && encuestaConf.getListaSecciones() != null && !encuestaConf.getListaSecciones().isEmpty()){
                for(Secciones seccion : this.encuestaConf.getListaSecciones()){
                    if(seccion.getMiListaPreguntas() != null && !seccion.getMiListaPreguntas().isEmpty()){
                        for(Preguntas pregunta : seccion.getMiListaPreguntas()){
                            if(pregunta.isHabilitadoOk()){
                                opcionChecked = true;
                            }
                            if(pregunta.isDeshabilitadoOk()){
                                opcionChecked = true;
                            }
                        }
                    }
                }
            }else{
                retorno = "No hay secciones en la encuesta.";
            }
        }else{
            retorno = "Seleccione una opción.";
        }
        if(!opcionChecked){
            retorno = "No hay preguntas seleccionadas.";
        }
        return retorno;
    }
    
    public void eventUnSelectRowOpcion(UnselectEvent event){
        //this.encuestaConf = new Encuestas();
    }
    
    public String guardarOpciones(String laPreguntaAgregada) {
        
        String opcionAgregada = "";
        
        this.getIdTipoListaConfiguraPregunta();
        this.getCatalogosOIDOpciones();
        this.getListasOIDFiltro();
        this.getVariablePreguntaOpcionMultiple();
        this.getListaOpciones();
        String valorOpciones = "";
        //variablePregunta = this.getVariablePreguntaOpcionMultiple();
        //Utileria.logger(getClass()).info("guardarOpciones Guardando las opciones de la pregunta:"+laPreguntaAgregada+ "  idTipoListaConfiguraPregunta:"+idTipoListaConfiguraPregunta);
        if (idTipoListaConfiguraPregunta == 0) {
            // *****mensaje para que deban seleccionar algo
            FacesMessage msg = new FacesMessage("Seleccione un tipo de lista.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (idTipoListaConfiguraPregunta == 1) {
            
            boolean seleccionadaTmp = ((this.seleccionadaInt == 0) ? false : true);
            //SEle pasan los parametros, de acuerdo al tipo de lista que se selecciono
            opcionAgregada = servicePreguntas.opcionesAgregadas(catalogosOIDOpciones,
                    laPreguntaAgregada, 0, seleccionadaTmp, this.opcionOtros, "",
                    idTipoListaConfiguraPregunta, listasOIDFiltro, variablePreguntaOpcionMultiple, false, false,
                    codigoBarras, false, "");
            
        } else if (idTipoListaConfiguraPregunta == 2) {
            boolean seleccionadaTmp = ((this.seleccionadaInt == 0) ? false : true);
            
            opcionAgregada = servicePreguntas.opcionesAgregadas("",
                    laPreguntaAgregada, 0, seleccionadaTmp, this.opcionOtros, preguntasOIDOpciones,
                    idTipoListaConfiguraPregunta, "", 0, false, false,
                    codigoBarras, false, valorOpciones);
            // ***** preguntas
        } else if (idTipoListaConfiguraPregunta == 3) {
            
            if (!this.listaOpciones.isEmpty()) {
                for (Opciones misO : this.getListaOpciones()) {
                    String idOpcionesTmp = "";
                    if(misO.getOpcionesOID() == null || misO.getOpcionesOID().equals("") || misO.getOpcionesOID().toUpperCase().equals("null")){
                        idOpcionesTmp = "null";
                    }else{
                        idOpcionesTmp = misO.getOpcionesOID();
                    }
                    valorOpciones += idOpcionesTmp+"#" + misO.getTextoOpcion() + "@";
                }
            }
            
            boolean seleccionadaTmp = ((this.seleccionadaInt == 0) ? false : true);
            // String miPreguntaAgregada = this.preguntaAgregada();
            opcionAgregada = servicePreguntas.opcionesAgregadas(catalogosOIDOpciones,
                    laPreguntaAgregada, 0, seleccionadaTmp, this.opcionOtros, "",
                    idTipoListaConfiguraPregunta, "", 0, false, false,
                    codigoBarras, false, valorOpciones);

            // **** opciones
        }
        return opcionAgregada;
    }
    
    public List<Catalogos> filtrarPor() {
        String misListasOID = "";
        boolean catalogo = false;

        this.setListaFiltraPor(serviceEncuestas.listaCatalogos(misListasOID,
                catalogo, 2));
        return this.getListaFiltraPor();
    }
    
    public void changeTipoListaPregunta(){
        if(this.miSeccionOIDPregunta != null && !miSeccionOIDPregunta.equals("")){
            tipoDeListaPregunta();
        }else{
            Utileria.mensajeAlerta("Debe de seleccionar una seccion de la encuesta.");
        }
    }
    
    public void tipoDeListaPregunta() {
        
        apareceCatalogoOrigen = false;
        aparecePreguntaOrigen = false;
        setDeshabilitaCatalogos(true);
        deshabilitaSeleccionFiltro = true;
        deshabilitaBotonOpcion = true;
        deshabilitaInputOpcion = true;
        deshabilitaPreguntas = true;
        deshabilitaCondicionada = true;
        deshabilitaCodigoBarras = true;
        deshabilitaOpcionOtros = true;
        deshabilitaConfigOpciones = true;
        deshabilitaSeleccionMinima = true;
        sePuedeEditar = true;
        
        if (idTipoListaConfiguraPregunta == 1) {
            //catalogo
            apareceCatalogoOrigen = true;
            aparecePreguntaOrigen = false;
            setDeshabilitaCatalogos(false);
            deshabilitaSeleccionFiltro = false;
            deshabilitaBotonOpcion = true;
            deshabilitaInputOpcion = true;
            deshabilitaPreguntas = true;
            
            String misListasOID = "";
            boolean catalogo = false;
            
            deshabilitaCondicionada = false;
            deshabilitaCodigoBarras = false;
            deshabilitaOpcionOtros = false;
            deshabilitaConfigOpciones = false;
            deshabilitaSeleccionMinima = false;
            sePuedeEditar = false;
            this.setListaCatalogos(serviceEncuestas.listaCatalogos(misListasOID, catalogo, idTipoListaConfiguraPregunta));
            
        } else if (idTipoListaConfiguraPregunta == 2) {
            //pregunta
            apareceCatalogoOrigen = false;
            aparecePreguntaOrigen = true;
            setDeshabilitaCatalogos(true);
            deshabilitaSeleccionFiltro = true;
            mostrarCampos = true;
            deshabilitaBotonOpcion = true;
            deshabilitaInputOpcion = true;
            this.setListaCatalogos(null);
            deshabilitaPreguntas = false;
            
            deshabilitaCondicionada = true;
            deshabilitaCodigoBarras = true;
            deshabilitaOpcionOtros = true;
            deshabilitaConfigOpciones = true;
            deshabilitaSeleccionMinima = true;
            sePuedeEditar = false;
            //this.setListaPreguntaOrigen(serviceEncuestas.listaPreguntaOrigen(this.idEncuesta, ordenSeccionConfigurar, ordenPregunta));
            obtieneListaPreguntasOrigen(miSeccionPadre != null ? miSeccionPadre.getSeccionOID() : null, this.idEncuesta, ordenSeccionConfigurar, ordenPregunta);
            
        } else if (idTipoListaConfiguraPregunta == 3) {
            //opciones
            apareceCatalogoOrigen = false;
            aparecePreguntaOrigen = true;
            setDeshabilitaCatalogos(true);
            this.setListaCatalogos(null);
            this.setListaOpciones(new ArrayList<Opciones>());
            deshabilitaSeleccionFiltro = true;
            deshabilitaBotonOpcion = false;
            deshabilitaInputOpcion = false;
            deshabilitaPreguntas = true;
            
            deshabilitaCondicionada = false;
            deshabilitaCodigoBarras = false;
            deshabilitaOpcionOtros = false;
            deshabilitaConfigOpciones = false;
            deshabilitaSeleccionMinima = false;
            sePuedeEditar = true;
            //this.setListaPreguntaOrigen(serviceEncuestas.listaPreguntaOrigen(this.idEncuesta, ordenSeccionConfigurar, ordenPregunta));
        }

    }

    public void agregarOpciones() {
        
        Opciones opcionAgregar = new Opciones();
        if (!opcionAgregada.trim().equals("")) {
            opcionAgregar.setTextoOpcion(this.opcionAgregada.trim());
            opcionAgregada = "";
            listaOpciones.add(opcionAgregar);
        } else if (!opcionAgregadaSM.trim().equals("")) {
            opcionAgregar.setTextoOpcion(this.opcionAgregadaSM.trim());
            opcionAgregadaSM = "";
            listaOpciones.add(opcionAgregar);
        }else{
            Utileria.mensajeAlerta("Debe ingresar la opción.");
        }
        
    }

    public List<String> getList() {
        return this.listaAgregaOpciones;
    }

    public void onRowReorder(ReorderEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Row Moved", "From: " + event.getFromIndex() + ", To:"
                + event.getToIndex());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowDelete(Object obj) {
        listaOpciones.remove(obj);
        Utileria.mensajeAlerta("Se ha eliminado solo en memoria.");
    }
    
    public void onRowEdit(RowEditEvent event) {
        
        Opciones newValue = (Opciones) event.getObject();
        
        if (!newValue.getTextoOpcion().trim().equals("")){
            Utileria.mensajeAlerta("Se ha editado solo en memoria.");
        } else {
            Utileria.mensajeAlerta("La opcion debe contener al menos una palabra.");
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Utileria.mensajeAlerta("Edición cancelada.");
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void configuraPregunta() {
        verTexto = false;
        verEntero = false;
        verDecimal = false;
        verInstruccion = false;
        verCheck = false;
        verOpcionMultiple = false;
        verLosMultiples = false;
        verSeleccionMultiple = false;
        verCodigoBarras = false;
        verAgregarOpcionOtro = false;
        
        if (this.idTipoRespuesta == 0) {
            
        } else if (this.idTipoRespuesta == 1) {
            
            verTexto = true;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = false;
            verSeleccionMultiple = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;
            
        } else if (this.idTipoRespuesta == 2) {

            verTexto = false;
            verEntero = true;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = false;
            verSeleccionMultiple = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 3) {

            verTexto = false;
            verEntero = false;
            verDecimal = true;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = false;
            verSeleccionMultiple = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 4) {
            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = false;
            verSeleccionMultiple = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 5) {
            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = false;
            verSeleccionMultiple = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 6) {

            verTexto = false;
            
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = true;
            verLosMultiples = true;
            verSeleccionMultiple = false;
            verCodigoBarras = true;
            verAgregarOpcionOtro = true;
            
        } else if (this.idTipoRespuesta == 7) {

            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = true;
            verSeleccionMultiple = true;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 8) {

            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;
            verLosMultiples = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;

        } else if (this.idTipoRespuesta == 9) {
            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = false;
            verCheck = false;
            verOpcionMultiple = false;
            verLosMultiples = true;
            verSeleccionMultiple = true;
            verCodigoBarras = true;
            verAgregarOpcionOtro = true;
            
        } else if (this.idTipoRespuesta == 10) {
            
            verTexto = false;
            verEntero = false;
            verDecimal = false;
            verInstruccion = true;
            verCheck = false;
            verOpcionMultiple = false;
            verSeleccionMultiple = false;
            verLosMultiples = false;
            verCodigoBarras = false;
            verAgregarOpcionOtro = false;
        }

    }
    
    public void preguntaAgregada() {
        String unaPreguntaOID = "";
        String opcionesAgregadas = "";
        if((idTipoRespuesta == 6 || idTipoRespuesta == 7 || idTipoRespuesta == 9) && 
                (idTipoListaConfiguraPregunta == 1 || idTipoListaConfiguraPregunta == 3) &&  
                ((listaOpciones == null || listaOpciones.isEmpty() || listaOpciones.size() == 0) && opcionOtros == false ) ){
            //opcionOtros == false
            Utileria.mensajeAlerta("No hay opcoines para la pregunta.");
        }else if (miSeccionOIDPregunta.equals("0") || miSeccionOIDPregunta.equals("")) {
            Utileria.mensajeAlerta("Debes seleccionar una sección.");
        } else if (idTipoRespuesta == 2 &&  (valorMinimo >= valorMaximo) ) {
            Utileria.mensajeAlerta("El valor mínimo debe ser menor al valor máximo.");
        } else {
            
            if(this.idTipoRespuesta == 1) {
                this.valorMaximo = longitudMaxima;
            }
            // laEncuesta = new Encuestas();
            if (miPreguntaOID.equals("")) {
                
                unaPreguntaOID = getServicePreguntas().preguntaAgregada("",
                        miSeccionOIDPregunta, textoPregunta, mensajePregunta,
                        ordenPregunta, true, true, true, variablePregunta,
                        idTipoRespuesta, alertaPregunta, subCategoriaPregunta,
                        bancoPreguntas, categoriaPregunta, requeridaPregunta,
                        longitudMaxima, valorMinimo, valorMaximo, numeroDecimales, archivoImagen, valorAlertaPregunta);
                //valorAlertaPregunta
                
                if (!unaPreguntaOID.equals("")) {
                    Utileria.mensajeSatisfactorio("Se ha agregado la pregunta " + textoPregunta);
                    RequestContext.getCurrentInstance().execute("PF('configurarPreguntas').hide()");
                } else {
                    Utileria.mensajeAlerta("No se pudo agregar la pregunta " + textoPregunta);
                }
            } else {
                
                unaPreguntaOID = getServicePreguntas().preguntaAgregada(
                        miPreguntaOID, miSeccionOIDPregunta, textoPregunta,
                        mensajePregunta, ordenPregunta, true, true, true,
                        variablePregunta, idTipoRespuesta, alertaPregunta,
                        subCategoriaPregunta, bancoPreguntas, categoriaPregunta,
                        requeridaPregunta, longitudMaxima, valorMinimo,
                        valorMaximo, numeroDecimales, archivoImagen,valorAlertaPregunta);
                
                if (!unaPreguntaOID.equals("")) {
                    Utileria.mensajeSatisfactorio("Se ha editado la pregunta " + textoPregunta);
                    RequestContext.getCurrentInstance().execute("PF('configurarPreguntas').hide()");
                } else {
                    Utileria.mensajeAlerta("No se pudo editar la pregunta " + textoPregunta);
                }
            }
            
            if ((idTipoRespuesta == 6 || idTipoRespuesta == 7 || idTipoRespuesta == 9) && !unaPreguntaOID.equals("")) {
                opcionesAgregadas = this.guardarOpciones(unaPreguntaOID);
            }
            if (idTipoRespuesta == 10 && !unaPreguntaOID.equals("") && !archivoImagen.equals("") ) {
                String toFile = protocoloGuardarImg+urlImage+pathImagePreguntas;//Ruta en la que se guardaran las imagenes de las preguntas tipo instruccion.
                String dirEncuestaTmp = toFile+encuestaSeleccionada.getEncuestasId()+"/";//Nombre de la carpeta para el proyecto seleccionado
                
                String newName = "";
                if(archivoImagen.contains(".")){
                    String[] fileTml = archivoImagen.split("\\.");
                    newName = unaPreguntaOID + "." +fileTml[fileTml.length - 1];
                }
                try {
                    if(!newName.equals("")){
                        if(FileHelper.existFile( dirEncuestaTmp + newName)){
                            FileHelper.delete(toFile + newName);
                        }
                        if(FileHelper.existFile(toFile+archivoImagen) &&  FileHelper.copy(toFile + archivoImagen, dirEncuestaTmp+ newName)){
                            FileHelper.delete(toFile + archivoImagen);

                            archivoImagen = newName;
                            unaPreguntaOID = getServicePreguntas().preguntaAgregada(unaPreguntaOID,
                                    miSeccionOIDPregunta, textoPregunta, mensajePregunta,
                                    ordenPregunta, true, true, true, variablePregunta,
                                    idTipoRespuesta, alertaPregunta, subCategoriaPregunta,
                                    bancoPreguntas, categoriaPregunta, requeridaPregunta,
                                    longitudMaxima, valorMinimo, valorMaximo, numeroDecimales, archivoImagen, valorAlertaPregunta);
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MBEncuestas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            laEncuesta = serviceEncuestas.traerEncuesta(this.getIdEncuesta());
            
        }
        
        //return unaPreguntaOID;
    }

    public List<Respuestas> traerListaTipoRespuesta() {

        this.setListaTipoRespuesta(servicePreguntas.listaTipoRespuestas());

        return getListaTipoRespuesta();
    }

    public List<Opciones> traerListaOpciones(){
        String opcionesOID = "";
        listaOpciones = serviceOpciones.traerOpciones(this.catalogosOIDOpciones, opcionesOID);
        return listaOpciones;
    }

    public List<Preguntas> traerListaPreguntas() {
        listaPreguntas = serviceEncuestas.listaPreguntaOrigen(miSeccionPadre.getSeccionOID(), idEncuesta, ordenSeccionConfigurar, ordenPregunta);
        return listaPreguntas;
    }

    public void limpiarDatosPregunta() {

        this.miPreguntaOID = "";
        this.miSeccionOIDPregunta = "";
        //this.ordenPregunta = 0;
        this.textoPregunta = "";
        this.subCategoriaPregunta = 0;
        this.mensajePregunta = "";
        this.variablePregunta = "";
        this.alertaPregunta = "";
        this.idTipoRespuesta = 0;
        this.bancoPreguntas = false;
        this.valorMinimo = 0;
        this.valorMaximo = 0;
        this.numeroDecimales = 0;
        this.listaOpciones = new ArrayList<Opciones>();
                
                
        this.idTipoListaConfiguraPregunta  = 0;
        this.catalogosOIDOpciones = "";
        this.preguntasOIDOpciones = "";

        this.valorAlertaPregunta = "";//La alerta de que depende del tipo de respuesta, no se arroja en [udp_Preguntas_sel]
        this.longitudMaxima = 0;
        this.archivoImagen = "";
        this.listasOIDFiltro = "";
        this.variablePreguntaOpcionMultiple = 0;
        this.opcionOtros  = false;
        this.condicionada = false;
        this.codigoBarras  = false;
        this.seleccionadaInt  = 1;//No se guarda en base de datos aun
                
        verTexto = false;
        verEntero = false;
        verDecimal = false;
        verInstruccion = false;
        verCheck = false;
        verOpcionMultiple = false;
        verLosMultiples = false;
        verSeleccionMultiple = false;
        verCodigoBarras = false;
        verAgregarOpcionOtro = false;
        
        this.opcionAgregada = "";
        this.listaSubCategorias = new ArrayList();
        this.idCategoria = 0;
        
        this.requeridaPregunta = false;
        
    }

    /* Metodos para ConfigurarSeccion.xhtml */
    /* Metodos para ConfigurarPregunta.xhtml */
    
    public void getISInstDinamico(String imgPregunta) {
        FacesContext context = FacesContext.getCurrentInstance();
        //String imgPregunta = context.getExternalContext().getRequestParameterMap().get("imgPregunta");
        
        String toFile = protocoloGuardarImg+urlImage+pathImagePreguntas;
        
       if(imgPregunta != null && !imgPregunta.equals("") && 
                this.encuestaSeleccionada != null && 
                encuestaSeleccionada.getEncuestasId() != null ){
           
            //String dirEncuestaTmp = toFile+encuestaSeleccionada.getEncuestasId();
            toFile += encuestaSeleccionada.getEncuestasId()+"/"+imgPregunta;
            
            if(FileHelper.existFile(toFile)){
                imgInstruccion = new DefaultStreamedContent(FileHelper.getFileInputStream(toFile)  );
            }
        }
       
       if(imgInstruccion == null){
           imgInstruccion = new DefaultStreamedContent();
       }
    }
    
    public void getInputStreamInstr() {
        String toFile = protocoloGuardarImg+urlImage+pathImagePreguntas;
        
        if(this.archivoImagen != null && !this.archivoImagen.equals("")){
            //Arma la url
            logo = null;
            if(this.miPreguntaOID != null && !this.miPreguntaOID.equals("") ){
                //String dirEncuestaTmp = toFile+encuestaSeleccionada.getEncuestasId();
                toFile += encuestaSeleccionada.getEncuestasId()+"/"+this.archivoImagen;
            }else{
                toFile += this.archivoImagen;
            }
            
            if(FileHelper.existFile(toFile)){
                setLogo(new DefaultStreamedContent(FileHelper.getFileInputStream(toFile)  ));
            }else{
                logo = null;
            }
            
        }else{
            logo = null;
        }
    }
    
    
    
    public boolean upload(FileUploadEvent event){
        
        boolean render=false;
        FacesContext context = FacesContext.getCurrentInstance();
        //urlImage = "C:\\Users\\Francisco Mora\\Documents\\proyectos\\";
        //pathImgProyectos = "";
        //protocoloGuardarImg+// Guarda el protocolo con el que se guardaran las imagenes (C://, ftp://, file://, etc.)
        String toFile = protocoloGuardarImg+urlImage+pathImagePreguntas;//Ruta en la que se guardaran las imagenes de las preguntas tipo instruccion.
        try {
            
            String dirEncuestaTmp = toFile+encuestaSeleccionada.getEncuestasId();//Nombre de la carpeta para el proyecto seleccionado
            if(FileHelper.createDir(dirEncuestaTmp)){
               
                dirEncuestaTmp += "/";
            
        	//String miTextoPregunta = this.textoPregunta;
                String type_tmp = event.getFile().getContentType().split("/")[1];//Obteniendo el tipo de archivo que se selecciono
                if(this.miPreguntaOID != null && !this.miPreguntaOID.equals("") ){
                    
                    String nameImg = this.miPreguntaOID+"."+type_tmp;
                    logo = new DefaultStreamedContent(event.getFile().getInputstream(),event.getFile().getContentType(), event.getFile().getFileName());//Carga el logo en la variable logo, para poder mostrarlo en pantalla
                    
                    if(guadaImagenDirectorio(dirEncuestaTmp , nameImg)){
                        this.archivoImagen = nameImg;
                        render = true;
                    }
                    
                    if(!render){
                        mensajeAlerta("No se pudo guardar la imagen.");
                    }
                }else{
                    String nameImg = DateHelper.nowNumber()+"."+type_tmp;
                    
                    if(FileHelper.existFile(toFile+nameImg )){
                        FileHelper.delete(toFile+nameImg);
                    }
                    
                    //Para cuando sea nuevo proyecto, solo guarda en memoria
                    logo = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType(), nameImg);
                    if(guadaImagenDirectorio(toFile , nameImg)){
                        this.archivoImagen = nameImg;//setInputFile(nameLogo);
                    }
                }
                
            }else{
                Utileria.mensajeAlerta("No se pudo crear el directorio para la encuesta.");
            }
        } catch (Exception ex) {
            Logger.getLogger(MBEncuestas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return render;
    }
    
    
    /*Guarda la imagen en la carpeta configfurada*/
    public boolean guadaImagenDirectorio(String to, String nameFile) throws Exception{
        return FileHelper.saveInputStreamFile(logo.getStream(), to, nameFile);
    }
    
    
    public void selectEndListener(final ImageAreaSelectEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Area Seleccionada",
                "X1: " + e.getX1()
                + ", X2: " + e.getX2()
                + ", Y1: " + e.getY1()
                + ", Y2: " + e.getY2()
                + ", Ancho: " + e.getImgWidth()
                + ", Alto: " + e.getImgHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void rotateListener(final RotateEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagen rodada",
                "Grados:" + e.getDegree());
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void resizeListener(final ResizeEvent e) {
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagen ajustada",
                "Ancho:" + e.getWidth() + ", Alcho: " + e.getHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void busquedaRecursiva() {
        String summary = this.ciclicaSeccionConfigurar ? "Checked" : "Unchecked";
        
        if (summary.equals("Checked")) {
            
            //FacesMessage msg = new FacesMessage("Seccion Recursiva");
            //FacesContext.getCurrentInstance().addMessage(null, msg);
            this.disableContorlUsuario = false;
            this.disableTipoLista = false;
            this.disableCatalogoOrigen = true;
            this.disablePreguntaOrigen = true;
            
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = true;
            this.controlUsuarioSeccionConfigurar=false;
            this.idTipoListaSeccionConfigurar=0;
            this.catalogoOIDSC = null;
            this.preguntaOIDSeccionConfigurar=null;
            this.deshabilitaPreguntaInicioCU = true;
        } else {
            //FacesMessage msg = new FacesMessage("Seccion NO Recursiva");
            //FacesContext.getCurrentInstance().addMessage(null, msg);
            this.disableContorlUsuario = true;
            this.disableTipoLista = true;
            this.disableCatalogoOrigen = true;
            this.disablePreguntaOrigen = true;
            
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = true;
            
            this.controlUsuarioSeccionConfigurar = false;
            this.deshabilitaPreguntaInicioCU = true;
        }
    }
    
    public void checkedControlUsuario(){
        
        if (this.controlUsuarioSeccionConfigurar) {
            
            this.disableTipoLista = true;
            this.disableCatalogoOrigen = true;
            this.disablePreguntaOrigen = true;
            
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = true;
            this.deshabilitaPreguntaInicioCU = false;
            //Aqui para buscar la pregunta iniciañd
            if(this.ordenSeccionConfigurar > 0){
                this.listaPreguntasCU = serviceEncuestas.getListasPreguntasControl(this.idEncuesta,(int) this.ordenSeccionConfigurar);
            }else{
                Utileria.mensajeAlerta("Ingrese una sección.");
            }
        } else {
            this.disableTipoLista = false;
            this.disableCatalogoOrigen = false;
            this.disablePreguntaOrigen = true;
            
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = true;
            this.deshabilitaPreguntaInicioCU = true;
        }
        
    }
    
    public void guardaOrden() {

        ordenSeccionConfigurar = ordenSeccionConfigurar;

    }

    public void guardaSeccion() {
        
        String miNuevaSeccion = "";
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        String miSeccionPadreOID = null;
        
        if (miSeccionPadre != null){
            miSeccionPadreOID = miSeccionPadre.getSeccionOID();
        }
        
        try {
            this.fechaCreacionSeccion = formateador.parse(fechaSistema);
        } catch (ParseException ex) {
            Logger.getLogger(MBEncuestas.class.getName()).log(Level.SEVERE, null, ex);
        }

        // laEncuesta = new Encuestas();
        // tabView = null;
        try {
            String validar = validaSeccion();
            if (this.getIdEncuesta() != null && this.getIdEncuesta() != 0 && validar.equals("")) {
                
                if (this.seleccionadaIntSeccionConfigurar == 1) {
                    this.setSeleccionada(true);
                } else {
                    this.setSeleccionada(false);
                }
                
                int variable = 0;
                
                if(this.preguntaOIDSeccionConfigurar != null && this.preguntaOIDSeccionConfigurar.equals("")){
                    this.preguntaOIDSeccionConfigurar = null;
                }
                if(this.catalogoOIDSC != null && this.catalogoOIDSC.equals("")){
                    this.catalogoOIDSC = null;
                }
                
                if(this.controlUsuarioSeccionConfigurar == true){
                    this.idTipoListaSeccionConfigurar = 2;
                }
                
                if (this.miSeccionOID == null || this.miSeccionOID.equals("") || this.miSeccionOID.equals("0")) {
                    
                    miNuevaSeccion = serviceSecciones.guardaSeccion("",
                            this.nombreSeccionConfigurar, this.getIdEncuesta(),
                            this.ordenSeccionConfigurar, true, true,
                            this.ciclicaSeccionConfigurar,
                            this.fechaCreacionSeccion, this.isSeleccionada(),
                            // pregDominante origen ?
                            this.preguntaOIDSeccionConfigurar,
                            // tipo lista opciones
                            this.idTipoListaSeccionConfigurar,
                            // listas filtro oid
                            "", variable, this.controlUsuarioSeccionConfigurar, false, this.catalogoOIDSC, this.claveSeccionConfigurar, miSeccionPadreOID);
                    
                    if (!miNuevaSeccion.equals("")) {
                        Utileria.mensajeSatisfactorio("La sección se ha guardado correctamente.");
                    } else {
                        Utileria.mensajeAlerta("No se pudo editar la seccion.");
                    }
                    
                } else {
                    
                    miNuevaSeccion = serviceSecciones.guardaSeccion(
                            miSeccionOID, this.nombreSeccionConfigurar,
                            this.getIdEncuesta(), this.ordenSeccionConfigurar,
                            true, true, this.ciclicaSeccionConfigurar,
                            this.fechaCreacionSeccion, this.isSeleccionada(),
                            // pregDominante origen ?
                            this.preguntaOIDSeccionConfigurar,
                            // tipo lista opciones
                            this.idTipoListaSeccionConfigurar,
                            // listas filtro oid
                            "", variable, controlUsuarioSeccionConfigurar, false, this.catalogoOIDSC, this.claveSeccionConfigurar, miSeccionPadreOID);
                    
                    if (!miNuevaSeccion.equals("")) {
                        Utileria.mensajeSatisfactorio("La sección ha sido actualizada.");
                    } else {
                        Utileria.mensajeAlerta("No se pudo editar la seccion.");
                    }
                    
                }

                this.setSeccionesPorEncuesta(this.obtenerSeccionesEncuesta(this.getIdEncuesta()));
                try {
                    laEncuesta.getListaSecciones().clear();
                } catch (NullPointerException npe) {
                    Utileria.logger(getClass()).info(npe.getMessage());
                }
                laEncuesta = serviceEncuestas.traerEncuesta(this.getIdEncuesta());
                
                RequestContext.getCurrentInstance().execute("PF('configuraSeccion').hide()");
            } else {
                if(!validar.equals("")){
                    Utileria.mensajeAlerta(validar);
                }
            }

        } catch (Exception ex) {
            Utileria.logger(getClass()).info(ex.getMessage());
        }

    }
    
    String validaSeccion(){
        String cadenaValidar = "";
        if(this.ciclicaSeccionConfigurar && this.controlUsuarioSeccionConfigurar == false){
            if(this.idTipoListaSeccionConfigurar == 0){
                cadenaValidar = "Seleccione un tipo de lista.";
            }
            if(this.idTipoListaSeccionConfigurar == 1 && (this.catalogoOIDSC == null || this.catalogoOIDSC.equals("")) ){
                cadenaValidar = "Seleccione un catalogo de origen.";
            }
            if(this.idTipoListaSeccionConfigurar == 2 && (this.preguntaOIDSeccionConfigurar == null || this.preguntaOIDSeccionConfigurar.equals("")) ){
                cadenaValidar = "Seleccione una pregunta de origen.";
            }
        }
        return cadenaValidar;
    }
    
    public void limpiarDatosSeccion() {

        this.nombreSeccionConfigurar = "";
        this.ordenSeccionConfigurar = 0;
        this.ciclicaSeccionConfigurar = false;
        this.idTipoListaSeccionConfigurar = 0;
        this.catalogosOIDSeccionConfigurar = "";
        this.preguntaOIDSeccionConfigurar = "";
        this.seleccionadaIntSeccionConfigurar = 1;
        miSeccionOID = "";
        this.claveSeccionConfigurar = "";
        disableTipoLista = false;
        disableContorlUsuario = false;
    }
    
    /* Metodos para EligeTipoEncuesta.xhtml */
    public void continuar() {
        
        laEncuesta = new Encuestas();
        
        String pagina = "";
        
        // AQUI GENERAREMOS UNA ENCUESTA NUEVA********
        if(this.tipoEncuesta == 0){
            Utileria.mensajeAlerta("Debe seleccionar un tipo de encuesta");
        }else if (this.tipoEncuesta == 2 && this.getIdPlantilla() == 0) {
            Utileria.mensajeAlerta("Debe seleccionar una plantilla");
        } else if (this.tipoEncuesta == 2 && this.getIdPlantilla() != 0) {
            boolean esPlantilla = false;
            Integer idEncuestaPlantilla = null;
            
            setMostrarConfiguracionEncuesta(false);
            this.setMuestraCombo(false);
            setMostrarPlantilla(true);
            
            idEncuestaPlantilla = serviceEncuestas.guardaComoPlantilla(
                    this.getIdPlantilla(), esPlantilla,"");
            
            listaSeccion.clear();
            
            laEncuesta = serviceEncuestas.traerEncuesta(idEncuestaPlantilla);
            
            this.setListaSeccion(laEncuesta.getListaSecciones());
            this.setIdEncuesta(idEncuestaPlantilla);
            this.setNombreEncuesta(laEncuesta.getNombreEncuesta());
            this.setObservacionEncuesta(laEncuesta.getObservaciones());
            
            this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
            this.setStatusId(laEncuesta.getStatusEncuesta());
            this.setStatus((laEncuesta.getStatusEncuesta() == 1 ? true : false));//StatusId=1->Activo, StatusId=2->Inactivo
            
            this.setNombrePlantilla("Plantilla-" + this.getNombreEncuesta());
            
            this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(idEncuestaPlantilla);
            RequestContext.getCurrentInstance().execute("PF('plantillaDinamica').show()");
        } else if (this.tipoEncuesta == 1) {
            this.setIdEncuesta(0);
            this.setNombreEncuesta("");
            this.setObservacionEncuesta("");
            RequestContext.getCurrentInstance().execute("PF('plantillaDinamica').show()");
        }

    }

    /* Metodos para GuardaComoPlantilla.xhtml */
    public void guardaEncuestaComoPlantilla() {

        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            //serviceEncuestas.guardaEncuestaComoPlantilla(this.idEncuesta, this.nombrePlantilla, true);
            Integer idEncuestaPlantilla = serviceEncuestas.guardaComoPlantilla(this.idEncuesta, true, this.nombrePlantilla);
            this.traemosEncuestas();

            Utileria.mensajeAlerta(context, "Se ha guardado como Plantilla");

        } catch (Exception ex) {
            Utileria.mensajeAlerta(context, "No se pudo guardar como Plantilla");
        }

        //----y se actualiza la tabla de encuestas y de plantillas
    }
    
    /**
     * Elimina una pregunta de una seccion de la encuesta y carga nuevamente los datos de la encuesta para ser redibujada.
     * @param preguntaEliminar  Identificador de la pregunta a eliminar.
     **/
    public void eliminaPregunta(String preguntaEliminar) {
        
        int unaEncuesta = this.encuestaSeleccionada.getEncuestasId();
        
        try {
            
            servicePreguntas.eliminaPregunta(preguntaEliminar);
            
            laEncuesta = serviceEncuestas.traerEncuesta(unaEncuesta);
            //this.setIdEncuesta(laEncuesta.getEncuestasId());
            this.setNombreEncuesta(laEncuesta.getNombreEncuesta());
            this.setObservacionEncuesta(laEncuesta.getObservaciones());
            //this.setActiva(laEncuesta.isActiva());
            
            this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
            this.setStatusId(laEncuesta.getStatusEncuesta());
            this.setStatus((laEncuesta.getStatusEncuesta() == 1 ? true : false));//StatusId=1->Activo, StatusId=2->Inactivo
            
            this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(miEncuesta);
            
        } catch (Exception ex) {
            Utileria.logger(getClass()).info(ex.getMessage());
        }

    }

    public void guardaComoPlantilla() throws ParseException {

        MBEncuestas miNuevaEncuesta = new MBEncuestas();

        this.claveEncuesta = null;
        this.gpsEncuesta = "1";
        this.statusId = 1;
        this.esPlantilla = true;

        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        this.fechaCreacionEncuesta = formateador.parse(fechaSistema);
        
        setMiEncuesta(serviceEncuestas.guardaEncuesta(null,this.nombreEncuesta,
                this.claveEncuesta, this.gpsEncuesta, this.statusId,
                this.esPlantilla, this.activa, this.fechaCreacionEncuesta,
                this.observacionEncuesta, this.unidadesNegocioId, this.isOperacion()));
        miNuevaEncuesta.setIdEncuesta(getMiEncuesta());

        this.setIdEncuesta(miNuevaEncuesta.getIdEncuesta());

        this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(miNuevaEncuesta.getIdEncuesta());
        if (this.getIdEncuesta() != null || this.getIdEncuesta() != 0) {
            
            FacesMessage msg = new FacesMessage("Se ha guardado la encuesta "
                    + this.nombreEncuesta);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {

            FacesMessage msg = new FacesMessage("No se guardo la encuesta "
                    + this.nombreEncuesta + " revise su informacion!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

    }
    
    
    
    public void changeSeccionPregunta(){
        this.ordenSeccionConfigurar = 0;
        if(this.miSeccionOIDPregunta != null && !this.miSeccionOIDPregunta.equals("")){
            List<Secciones> miSeccionseleccionada = new ArrayList<Secciones>();
            short orden = 0;
            miSeccionseleccionada = serviceSecciones.listaSecciones(this.miSeccionOIDPregunta, "", 0,
                    orden, true, false, false, null, false);
            if(miSeccionseleccionada != null && !miSeccionseleccionada.isEmpty()){
                for (Secciones seccionEditar : miSeccionseleccionada) {
                    this.ordenSeccionConfigurar = (short) seccionEditar.getOrden();
                }
            }
        }
    }
    
    /**
     * Obtiene los datos de una seccion.
     * @param seccionOID Identificador de la seccion.
     **/
    public Secciones traeSeccion(Secciones seccionPadre, String seccionOID){
        this.miSeccionPadre = seccionPadre;
        String validacion = validaEncuestaGuardada();
        if(validacion.equals("")){
            if(!seccionOID.equals("")){
                List<Secciones> miSeccionEditar = new ArrayList<Secciones>();
                short orden = 0;
                
                miSeccionEditar = serviceSecciones.listaSecciones(seccionOID, "", 0, orden, true, false, false, null, false);

                for (Secciones seccionEditar : miSeccionEditar) {
                    /*this.listaPreguntasCU = serviceEncuestas.getListasPreguntasControl(this.idEncuesta,(int) this.ordenSeccion);*/
                    // *
                    this.setMiSeccionOID(seccionEditar.getSeccionOID());
                    // = seccionEditar.getSeccionOID();

                    this.nombreSeccionConfigurar = seccionEditar.getNombreSeccion();
                    this.ordenSeccionConfigurar = (short) seccionEditar.getOrden();
                    this.ciclicaSeccionConfigurar = seccionEditar.isCiclica();
                    this.controlUsuarioSeccionConfigurar = seccionEditar.isControlUsuario();

                    this.idTipoListaSeccionConfigurar = seccionEditar.getIdTipoLista();
                    this.claveSeccionConfigurar = seccionEditar.getClaveSeccion();
                    this.ordenSeccion = (short)seccionEditar.getOrden();
                    this.seleccionadaIntSeccionConfigurar = seccionEditar.isSeleccionada() == true ? 1 : 0;
                    //this.catalogosOIDSeccionConfigurar = seccionEditar.get;
                    // this.preguntaOIDSeccionConfigurar = "";
                    // this.seleccionadaIntSeccionConfigurar = 0;
                    
                    this.catalogoOIDSC = seccionEditar.getListasOID();
                    this.preguntaOIDSeccionConfigurar = seccionEditar.getPreguntaDominante();
                    
                    String misListasOID = "";
                    boolean catalogo = false;
                    
                    if(this.preguntaOIDSeccionConfigurar != null && !this.preguntaOIDSeccionConfigurar.equals("")){
                        obtieneListaPreguntasOrigen(miSeccionPadre != null ? miSeccionPadre.getSeccionOID() : null, this.getIdEncuesta(), ordenSeccionConfigurar,0);
                    }
                    if(this.catalogoOIDSC != null && !this.catalogoOIDSC.equals("")){
                        obtieneListaCatalogos(misListasOID,catalogo, idTipoListaSeccionConfigurar);
                    }
                    activaComponentesEdit();
                    //busquedaRecursiva();
                    //tipoDeLista();
                }
            }else{
                this.setMiSeccionOID(null);
                // = seccionEditar.getSeccionOID();

                this.nombreSeccionConfigurar = "";
                this.ordenSeccionConfigurar = 0;
                this.ciclicaSeccionConfigurar = false;
                this.controlUsuarioSeccionConfigurar = false;

                this.idTipoListaSeccionConfigurar = 0;
                this.claveSeccionConfigurar = "";
                this.ordenSeccion = 0;

                this.catalogoOIDSC = "";
                this.preguntaOIDSeccionConfigurar = "";
                this.seleccionadaIntSeccionConfigurar = 1;

                activaComponentesEdit();
            }
            RequestContext.getCurrentInstance().execute("PF('configuraSeccion').show()");
        }else{
            Utileria.mensajeAlerta(validacion);
        }
        
        // this.setCatalogosOID(miSeccionEditar.get(0).getCatalogoOrigen());
        return secciones;
    }
    
    
    public void validaGuardaComoPlantilla(){
        String validacion = validaEncuestaGuardada();
        if(validacion.equals("")){
            RequestContext.getCurrentInstance().execute("PF('guardaComoPlantilla').show()");
        }else{
            Utileria.mensajeAlerta(validacion);
        }
    }
    
    String validaEncuestaGuardada(){
        String valida = "";
        if (this.getIdEncuesta() == null || this.getIdEncuesta() == 0) {
            valida = "Primero debe guardar la encuesta.";
        }
        return valida;
    }
    
    /**
     * Asigna valores para habilitar y deshabilitar componentes en una seccion.
     **/
    void activaComponentesEdit(){
        
        if(this.ciclicaSeccionConfigurar){
            if(this.controlUsuarioSeccionConfigurar){
                this.disableContorlUsuario = true;
                this.disableTipoLista = true;
                this.deshabilitaPreguntaInicioCU = false;
                checkedControlUsuario();
            }else{
                this.disableContorlUsuario = false;
                this.disableTipoLista = false;
                this.deshabilitaPreguntaInicioCU = true;
                
                tipoDeLista();
            }
        }else{
            this.disableContorlUsuario = true;
            this.disableTipoLista = true;
            this.disableCatalogoOrigen = true;
            this.disablePreguntaOrigen = true;
            this.apareceCatalogoOrigen=true;
            this.aparecePreguntaOrigen = true;
            deshabilitaPreguntaInicioCU = true;
        }
    }
    
    
    
    /**
     * Obtiene los datos de una pregunta.
     * @param ordenSec Orden de la seccion en la encuesta.
     * @param miSe Identificador de la seccion.
     * @param miPre Identificador de la pregunta.
     **/
    public Preguntas traePregunta(int ordenSec,String miSe, String miPre) {
        //limpiarDatosPregunta();
        String validacion = validaEncuestaGuardada();
        
        this.ordenSeccionConfigurar = (short) ordenSec;
        variablePreguntaOpcionMultiple = null;
        listaVariablesFiltro = new ArrayList();
        listaSubCategorias = new ArrayList();
        this.idCategoria = 0;
        this.subCategoriaPregunta = 0;
        
        //this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(this.encuestaSeleccionada.getEncuestasId());
        if(validacion.equals("") && ( this.seccionesPorEncuesta !=null && this.seccionesPorEncuesta.size() > 0 )){
            
            if(miPre != null && !miPre.equals("")){
                
                List<Preguntas> miPreguntaEditar = new ArrayList<Preguntas>();
                //String preguntaOID, String seccionOID, boolean banco, int idSubCategoria
                miPreguntaEditar = servicePreguntas.listaPreguntas(miPre,miSe, false, 0);
                if(miPreguntaEditar == null || miPreguntaEditar.isEmpty()){
                    miPreguntaEditar = servicePreguntas.listaPreguntas(miPre,miSe, true, 0);
                }
                
                for (Preguntas preguntaEditar : miPreguntaEditar) {
                    limpiarDatosPregunta();
                    this.miPreguntaOID = preguntaEditar.getPreguntaOID();
                    this.miSeccionOIDPregunta = preguntaEditar.getSeccionOID();
                    this.ordenPregunta = preguntaEditar.getOrden();
                    this.textoPregunta = preguntaEditar.getDescripcionPregunta();
                    this.subCategoriaPregunta = preguntaEditar.getIdSubCategoria();
                    this.mensajePregunta = preguntaEditar.getMensajesPregunta();
                    this.variablePregunta = preguntaEditar.getVariable();
                    this.alertaPregunta = preguntaEditar.getAlerta();
                    this.idTipoRespuesta = preguntaEditar.getIdTipoPregunta();
                    this.bancoPreguntas = preguntaEditar.isBanco();
                    this.valorMinimo = preguntaEditar.getValorMinimo();
                    this.valorMaximo = preguntaEditar.getValorMaximo();
                    this.numeroDecimales = preguntaEditar.getNumeroDecimales();
                    
                    this.idTipoListaConfiguraPregunta  = preguntaEditar.getTipoListasOpcionesId();
                    this.catalogosOIDOpciones = preguntaEditar.getListaOID();
                    this.preguntasOIDOpciones = preguntaEditar.getPreguntasDominanteOID();
                    
                    this.valorAlertaPregunta = preguntaEditar.getValorAlerta();//La alerta de que depende del tipo de respuesta, no se arroja en [udp_Preguntas_sel]
                    this.requeridaPregunta = preguntaEditar.isRequerida();
                    this.longitudMaxima = preguntaEditar.getValorMaximo();
                    this.archivoImagen = preguntaEditar.getArchivoImagen();
                    this.listasOIDFiltro = preguntaEditar.getListasFiltroOID();
                    this.variablePreguntaOpcionMultiple = preguntaEditar.getVariableFiltro();
                    this.opcionOtros  = preguntaEditar.isOtros();
                    this.condicionada = preguntaEditar.isCondicionada();
                    this.codigoBarras  = preguntaEditar.isCodigoBarra();
                    this.seleccionadaInt  = preguntaEditar.isSeleccionadas() == true ? 1 : 0 ;//No se guarda en base de datos aun
                    if(this.listasOIDFiltro != null && !this.listasOIDFiltro.equals("")){
                        this.seleccionFiltro = true;
                    }
                    configuraPregunta();
                    tipoDeListaPregunta();
                    
                    this.opcionAgregada = "";//esta variable, solo se utiliza cuando se tiene que agregar una opcion al grid
                    this.listaOpciones = new ArrayList();//lista de opciones que apareceran en el grid.
                    //seleccionFiltro

                    subCategoriasList(null, this.subCategoriaPregunta);
                    if(this.listaSubCategorias != null && !this.listaSubCategorias.isEmpty()){
                        this.idCategoria = listaSubCategorias.get(0).getIdCategoria();
                        subCategoriasList(this.idCategoria, null);
                    }

                    if(this.catalogosOIDOpciones != null && !this.catalogosOIDOpciones.equals("")){
                        traerListaOpciones();
                    }
                    if(this.preguntasOIDOpciones != null && !this.preguntasOIDOpciones.equals("")){
                        traerListaPreguntas();
                    }

                    condicionadaChecked();
                }
            }else{
                limpiarDatosPregunta();
                configuraPregunta();
                tipoDeListaPregunta();
                this.miSeccionOIDPregunta = miSe;
            }
            this.listaVariablesFiltro = getServiceVariables().getAllVariables();
            
            RequestContext.getCurrentInstance().execute("PF('configurarPreguntas').show()");
        }else{
            if(validacion.equals("")){
                validacion = "La encuesta no tiene secciones.";
            }
            Utileria.mensajeAlerta(validacion);
        }
        
        return misPreguntas;

    }

    public void guardaEncuesta() throws ParseException {
        
        this.claveEncuesta = getLaEncuesta().getClaveEncuesta();
        this.gpsEncuesta = getLaEncuesta().getGpsEncuesta();
        this.statusId = getLaEncuesta().getStatusEncuesta();
        this.esPlantilla = getLaEncuesta().isPlantilla();
        
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);
        this.fechaCreacionEncuesta = formateador.parse(fechaSistema);
        
        if (this.getIdEncuesta() != null && this.getIdEncuesta() != 0) {
            
            setMiEncuesta(serviceEncuestas.guardaEncuesta(this.getIdEncuesta(),this.nombreEncuesta,
                this.claveEncuesta, this.gpsEncuesta, this.statusId,
                this.esPlantilla, this.activa, this.fechaCreacionEncuesta,
                this.observacionEncuesta, this.unidadesNegocioId, this.isOperacion()));
            
            //this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(miNuevaEncuesta.getIdEncuesta());
            
            //this.setIdEncuesta(this.getMiEncuesta());
            if (this.getMiEncuesta() != null || this.getMiEncuesta() != 0) {
                this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(this.getMiEncuesta());
                this.setIdEncuesta(this.getMiEncuesta());
                
                this.setNombrePlantilla("Plantilla-" + this.nombreEncuesta);
                encuestaSeleccionada.setEncuestasId(this.getIdEncuesta());
                //traemosEncuestas();
                Utileria.mensajeSatisfactorio("Se ha editado la encuesta "+ this.nombreEncuesta);
            } else {
                Utileria.mensajeAlerta("No se edito la encuesta "+ this.nombreEncuesta + " revise su información!");
            }
        }else{
            
            setMiEncuesta(serviceEncuestas.guardaEncuesta(null,this.nombreEncuesta,
                this.claveEncuesta, this.gpsEncuesta, this.statusId,
                this.esPlantilla, this.activa, this.fechaCreacionEncuesta,
                this.observacionEncuesta, this.unidadesNegocioId, this.isOperacion()));
            
            //this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(miNuevaEncuesta.getIdEncuesta());
            
            //this.setIdEncuesta(miNuevaEncuesta.getIdEncuesta());
            if (this.getMiEncuesta() != null || this.getMiEncuesta() != 0) {
                this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(this.getMiEncuesta());
                this.setIdEncuesta(this.getMiEncuesta());
                encuestaSeleccionada.setEncuestasId(this.getIdEncuesta());
                this.setNombrePlantilla("Plantilla-" + this.nombreEncuesta);
                //traemosEncuestas();
                Utileria.mensajeSatisfactorio("La encuesta se ha guardado correctamente.");
            } else {
                Utileria.mensajeAlerta("No se guardó la encuesta "+ this.nombreEncuesta + " revise su información!");
            }
            
        }
    }

    public void limpiarDatosEncuesta() {
        laEncuesta = null;
        tabView = null;
        tabEdicion = null;
        this.nombreEncuesta = null;
        this.observacionEncuesta = null;
        //this.activa = false;
        this.idPlantilla = 0;
        this.tipoEncuesta = 0;
        
        this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
        this.setStatusId(2);
        this.setStatus(false);//StatusId=1->Activo, StatusId=2->Inactivo
        // miNuevaEncuesta.setIdEncuesta(getMiEncuesta());
    }
    
    /**
     * Elimina una una seccion de la encuesta y carga nuevamente los datos de la encuesta para ser redibujada.
     * @param seccionEliminar  Identificador de la pregunta a eliminar.
     **/
    public void eliminaSeccion(String seccionEliminar) {
        
        int unaEncuesta = this.getIdEncuesta();
        
        try {
            serviceSecciones.eliminaSeccion(seccionEliminar);
            laEncuesta = serviceEncuestas.traerEncuesta(unaEncuesta);
            //this.setIdEncuesta(unaEncuesta);
            this.setNombreEncuesta(laEncuesta.getNombreEncuesta());
            this.setObservacionEncuesta(laEncuesta.getObservaciones());
            //this.setActiva(laEncuesta.isActiva());
            
            this.setActiva(true);//Por que si no esta activa, no la puedo editar, esto se quito del String que arroja el sp.
            this.setStatusId(laEncuesta.getStatusEncuesta());
            this.setStatus((laEncuesta.getStatusEncuesta() == 1 ? true : false));//StatusId=1->Activo, StatusId=2->Inactivo
            
            this.seccionesPorEncuesta = this.obtenerSeccionesEncuesta(unaEncuesta);
        } catch (Exception ex) {
            Utileria.mensajeAlerta(ex.getMessage());
        }
    }
    
    /**
     * Crea componente primefaces desde codigo java y devuelve al componente
     * primefaces xhtml en su propiedad binding
     *
     * @param tabView
     */
    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    public TabView getTabView() {

        FacesContext fc = FacesContext.getCurrentInstance();
        tabView = (TabView) fc.getApplication().createComponent(
                "org.primefaces.component.TabView");

        return tabView;
    }

    public void validarRangoNP(FacesContext ctx, UIComponent component, Object value)throws ValidatorException {
        String validar = value.toString();
        Pattern regEx = Pattern.compile("^-?[\\d]{0,4}$");
        
        if(!regEx.matcher(validar).matches()){
            
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El valor debe estar entre -9999 y 9999"));
        }
        
    }

    //Metodo para el mensaje de alerta, poniendo el mismo mensaje en el titulo y en el detalle.
    void mensajeAlerta(String mensaje){
        Utileria.mensajeAlerta(mensaje, mensaje);
    }
    
    /**
     * @return the listaEncuestas
     */
    public List<Encuestas> getListaEncuestas() {
        return listaEncuestas;
    }

    /**
     * @param listaEncuestas the listaEncuestas to set
     */
    public void setListaEncuestas(List<Encuestas> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }

    /**
     * @return the serviceEncuestas
     */
    public ServiceEncuestas getServiceEncuestas() {
        return serviceEncuestas;
    }

    /**
     * @param serviceEncuestas the serviceEncuestas to set
     */
    public void setServiceEncuestas(ServiceEncuestas serviceEncuestas) {
        this.serviceEncuestas = serviceEncuestas;
    }

    /**
     * @return the idEncuesta
     */
    public Integer getIdEncuesta() {
        return idEncuesta;
    }

    /**
     * @param idEncuesta the idEncuesta to set
     */
    public void setIdEncuesta(Integer idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    /**
     * @return the encuestaSeleccionada
     */
    public Encuestas getEncuestaSeleccionada() {
        return encuestaSeleccionada;
    }

    /**
     * @param encuestaSeleccionada the encuestaSeleccionada to set
     */
    public void setEncuestaSeleccionada(Encuestas encuestaSeleccionada) {
        this.encuestaSeleccionada = encuestaSeleccionada;
    }

    /**
     * @return the tipoEncuesta
     */
    public int getTipoEncuesta() {
        return tipoEncuesta;
    }

    /**
     * @param tipoEncuesta the tipoEncuesta to set
     */
    public void setTipoEncuesta(int tipoEncuesta) {
        this.tipoEncuesta = tipoEncuesta;
    }

    /**
     * @return the idPlantilla
     */
    public int getIdPlantilla() {
        return idPlantilla;
    }

    /**
     * @param idPlantilla the idPlantilla to set
     */
    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    /**
     * @return the muestraCombo
     */
    public boolean isMuestraCombo() {
        return muestraCombo;
    }

    /**
     * @param muestraCombo the muestraCombo to set
     */
    public void setMuestraCombo(boolean muestraCombo) {
        this.muestraCombo = muestraCombo;
    }

    /**
     * @return the mostrarConfiguracionEncuesta
     */
    public boolean isMostrarConfiguracionEncuesta() {
        return mostrarConfiguracionEncuesta;
    }

    /**
     * @param mostrarConfiguracionEncuesta the mostrarConfiguracionEncuesta to
     * set
     */
    public void setMostrarConfiguracionEncuesta(
            boolean mostrarConfiguracionEncuesta) {
        this.mostrarConfiguracionEncuesta = mostrarConfiguracionEncuesta;
    }

    /**
     * @return the encuestaId
     */
    public String getEncuestaId() {
        return encuestaId;
    }

    /**
     * @param encuestaId the encuestaId to set
     */
    public void setEncuestaId(String encuestaId) {
        this.encuestaId = encuestaId;
    }

    /**
     * @return the nombreEncuesta
     */
    public String getNombreEncuesta() {
        return nombreEncuesta;
    }

    /**
     * @param nombreEncuesta the nombreEncuesta to set
     */
    public void setNombreEncuesta(String nombreEncuesta) {
        this.nombreEncuesta = nombreEncuesta;
    }

    /**
     * @return the claveEncuesta
     */
    public String getClaveEncuesta() {
        return claveEncuesta;
    }

    /**
     * @param claveEncuesta the claveEncuesta to set
     */
    public void setClaveEncuesta(String claveEncuesta) {
        this.claveEncuesta = claveEncuesta;
    }

    /**
     * @return the gpsEncuesta
     */
    public String getGpsEncuesta() {
        return gpsEncuesta;
    }

    /**
     * @param gpsEncuesta the gpsEncuesta to set
     */
    public void setGpsEncuesta(String gpsEncuesta) {
        this.gpsEncuesta = gpsEncuesta;
    }

    /**
     * @return the fechaCreacionEncuesta
     */
    public Date getFechaCreacionEncuesta() {
        return fechaCreacionEncuesta;
    }

    /**
     * @param fechaCreacionEncuesta the fechaCreacionEncuesta to set
     */
    public void setFechaCreacionEncuesta(Date fechaCreacionEncuesta) {
        this.fechaCreacionEncuesta = fechaCreacionEncuesta;
    }

    /**
     * @return the observacionEncuesta
     */
    public String getObservacionEncuesta() {
        return observacionEncuesta;
    }

    /**
     * @param observacionEncuesta the observacionEncuesta to set
     */
    public void setObservacionEncuesta(String observacionEncuesta) {
        this.observacionEncuesta = observacionEncuesta;
    }

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the statusId
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the esPlantilla
     */
    public boolean isEsPlantilla() {
        return esPlantilla;
    }

    /**
     * @param esPlantilla the esPlantilla to set
     */
    public void setEsPlantilla(boolean esPlantilla) {
        this.esPlantilla = esPlantilla;
    }

    /**
     * @return the mostrarPlantilla
     */
    public boolean isMostrarPlantilla() {
        return mostrarPlantilla;
    }

    /**
     * @param mostrarPlantilla the mostrarPlantilla to set
     */
    public void setMostrarPlantilla(boolean mostrarPlantilla) {
        this.mostrarPlantilla = mostrarPlantilla;
    }

    /**
     * @return the plantilla
     */
    public Plantillas getPlantilla() {
        return plantilla;
    }

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(Plantillas plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * @return the listaElementosPlantilla
     */
    public List<Plantillas> getListaElementosPlantilla() {
        return listaElementosPlantilla;
    }

    /**
     * @param listaElementosPlantilla the listaElementosPlantilla to set
     */
    public void setListaElementosPlantilla(
            List<Plantillas> listaElementosPlantilla) {
        this.listaElementosPlantilla = listaElementosPlantilla;
    }

    /**
     * @return the formulario
     */
    public HtmlForm getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(HtmlForm formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the painel
     */
    public HtmlPanelGrid getPainel() {
        return painel;
    }

    /**
     * @param painel the painel to set
     */
    public void setPainel(HtmlPanelGrid painel) {
        this.painel = painel;
    }

    /**
     * @return the filtroTeste
     */
    public String getFiltroTeste() {
        return filtroTeste;
    }

    /**
     * @param filtroTeste the filtroTeste to set
     */
    public void setFiltroTeste(String filtroTeste) {
        this.filtroTeste = filtroTeste;
    }

    /**
     * @return the itxOperador
     */
    public HtmlInputText getItxOperador() {
        return itxOperador;
    }

    /**
     * @param itxOperador the itxOperador to set
     */
    public void setItxOperador(HtmlInputText itxOperador) {
        this.itxOperador = itxOperador;
    }

    /**
     * @return the nombreSeccion
     */
    public String getNombreSeccion() {
        return nombreSeccion;
    }

    /**
     * @param nombreSeccion the nombreSeccion to set
     */
    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    /**
     * @return the miListaSecciones
     */
    public List<Secciones> getMiListaSecciones() {
        return miListaSecciones;
    }

    /**
     * @param miListaSecciones the miListaSecciones to set
     */
    public void setMiListaSecciones(List<Secciones> miListaSecciones) {
        this.miListaSecciones = miListaSecciones;
    }

    /**
     * @return the secciones
     */
    public Secciones getSecciones() {
        return secciones;
    }

    /**
     * @param secciones the secciones to set
     */
    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    /**
     * @return the idTipoLista
     */
    public int getIdTipoLista() {
        return idTipoLista;
    }

    /**
     * @param idTipoLista the idTipoLista to set
     */
    public void setIdTipoLista(int idTipoLista) {
        this.idTipoLista = idTipoLista;
    }

    /**
     * @return the apareceCatalogoOrigen
     */
    public boolean isApareceCatalogoOrigen() {
        return apareceCatalogoOrigen;
    }

    /**
     * @param apareceCatalogoOrigen the apareceCatalogoOrigen to set
     */
    public void setApareceCatalogoOrigen(boolean apareceCatalogoOrigen) {
        this.apareceCatalogoOrigen = apareceCatalogoOrigen;
    }

    /**
     * @return the aparecePreguntaOrigen
     */
    public boolean isAparecePreguntaOrigen() {
        return aparecePreguntaOrigen;
    }

    /**
     * @param aparecePreguntaOrigen the aparecePreguntaOrigen to set
     */
    public void setAparecePreguntaOrigen(boolean aparecePreguntaOrigen) {
        this.aparecePreguntaOrigen = aparecePreguntaOrigen;
    }

    /**
     * @return the listaCatalogos
     */
    public List<Catalogos> getListaCatalogos() {
        return listaCatalogos;
    }

    /**
     * @param listaCatalogos the listaCatalogos to set
     */
    public void setListaCatalogos(List<Catalogos> listaCatalogos) {
        this.listaCatalogos = listaCatalogos;
    }

    /**
     * @return the catalogosOID
     */
    public String getCatalogosOID() {
        return catalogosOID;
    }

    /**
     * @param catalogosOID the catalogosOID to set
     */
    public void setCatalogosOID(String catalogosOID) {
        this.catalogosOID = catalogosOID;
    }

    /**
     * @return the listaPreguntaOrigen
     */
    public List<Preguntas> getListaPreguntaOrigen() {
        return listaPreguntaOrigen;
    }

    /**
     * @param listaPreguntaOrigen the listaPreguntaOrigen to set
     */
    public void setListaPreguntaOrigen(List<Preguntas> listaPreguntaOrigen) {
        this.listaPreguntaOrigen = listaPreguntaOrigen;
    }

    /**
     * @return the preguntaOID
     */
    public String getPreguntaOID() {
        return preguntaOID;
    }

    /**
     * @param preguntaOID the preguntaOID to set
     */
    public void setPreguntaOID(String preguntaOID) {
        this.preguntaOID = preguntaOID;
    }

    /**
     * @return the proyectoId
     */
    public int getProyectoId() {
        return proyectoId;
    }

    /**
     * @param proyectoId the proyectoId to set
     */
    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    /**
     * @return the listaSeccion
     */
    public List<Secciones> getListaSeccion() {
        return listaSeccion;
    }

    /**
     * @param listaSeccion the listaSeccion to set
     */
    public void setListaSeccion(List<Secciones> listaSeccion) {
        this.listaSeccion = listaSeccion;
    }

    /**
     * @return the serviceSecciones
     */
    public ServiceSecciones getServiceSecciones() {
        return serviceSecciones;
    }

    /**
     * @param serviceSecciones the serviceSecciones to set
     */
    public void setServiceSecciones(ServiceSecciones serviceSecciones) {
        this.serviceSecciones = serviceSecciones;
    }

    /**
     * @return the idCategoria
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * @return the idSubCategoria
     */
    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    /**
     * @param idSubCategoria the idSubCategoria to set
     */
    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    /**
     * @return the serviceCategorias
     */
    public ServiceCategorias getServiceCategorias() {
        return serviceCategorias;
    }

    /**
     * @param serviceCategorias the serviceCategorias to set
     */
    public void setServiceCategorias(ServiceCategorias serviceCategorias) {
        this.serviceCategorias = serviceCategorias;
    }

    /**
     * @return the listaSubCategorias
     */
    public List<SubCategorias> getListaSubCategorias() {
        return listaSubCategorias;
    }

    /**
     * @param listaSubCategorias the listaSubCategorias to set
     */
    public void setListaSubCategorias(List<SubCategorias> listaSubCategorias) {
        this.listaSubCategorias = listaSubCategorias;
    }

    /**
     * @return the idTipoRespuesta
     */
    public int getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    /**
     * @param idTipoRespuesta the idTipoRespuesta to set
     */
    public void setIdTipoRespuesta(int idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
    }

    /**
     * @return the misPreguntas
     */
    public Preguntas getMisPreguntas() {
        return misPreguntas;
    }

    /**
     * @param misPreguntas the misPreguntas to set
     */
    public void setMisPreguntas(Preguntas misPreguntas) {
        this.misPreguntas = misPreguntas;
    }

    /**
     * @return the miListaP
     */
    public List<Preguntas> getMiListaP() {
        return miListaP;
    }

    /**
     * @param miListaP the miListaP to set
     */
    public void setMiListaP(List<Preguntas> miListaP) {
        this.miListaP = miListaP;
    }

    /**
     * @return the miListaO
     */
    public List<Opciones> getMiListaO() {
        return miListaO;
    }

    /**
     * @param miListaO the miListaO to set
     */
    public void setMiListaO(List<Opciones> miListaO) {
        this.miListaO = miListaO;
    }

    /**
     * @return the misOpciones
     */
    public Opciones getMisOpciones() {
        return misOpciones;
    }

    /**
     * @param misOpciones the misOpciones to set
     */
    public void setMisOpciones(Opciones misOpciones) {
        this.misOpciones = misOpciones;
    }

    /**
     * @return the root
     */
/*    public TreeNode getRoot() {
        return root;
    }
*/
    /**
     * @param root the root to set
     */
/*    public void setRoot(TreeNode root) {
        this.root = root;
    }
*/
    /**
     * @return the miListaS
     */
    public List<Secciones> getMiListaS() {
        return miListaS;
    }

    /**
     * @param miListaS the miListaS to set
     */
    public void setMiListaS(List<Secciones> miListaS) {
        this.miListaS = miListaS;
    }

    /**
     * @return the inputTextArea
     */
    public InputTextarea getInputTextArea() {
        return inputTextArea;
    }

    /**
     * @param inputTextArea the inputTextArea to set
     */
    public void setInputTextArea(InputTextarea inputTextArea) {
        this.inputTextArea = inputTextArea;
    }

    /**
     * @return the dataTable
     */
    public DataTable getDataTable() {

        FacesContext fc = FacesContext.getCurrentInstance();
        dataTable = (DataTable) fc.getApplication().createComponent(
                "org.primefaces.component.DataTable");

        return dataTable;
    }

    /**
     * @param dataTable the dataTable to set
     */
    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    /**
     * @return the idSeccionEncuesta
     */
    public int getIdSeccionEncuesta() {
        return idSeccionEncuesta;
    }

    /**
     * @param idSeccionEncuesta the idSeccionEncuesta to set
     */
    public void setIdSeccionEncuesta(int idSeccionEncuesta) {
        this.idSeccionEncuesta = idSeccionEncuesta;
    }

    /**
     * @return the ordenSeccion
     */
    public short getOrdenSeccion() {
        return ordenSeccion;
    }

    /**
     * @param ordenSeccion the ordenSeccion to set
     */
    public void setOrdenSeccion(short ordenSeccion) {
        this.ordenSeccion = ordenSeccion;
    }

    /**
     * @return the activaSeccion
     */
    public boolean isActivaSeccion() {
        return activaSeccion;
    }

    /**
     * @param activaSeccion the activaSeccion to set
     */
    public void setActivaSeccion(boolean activaSeccion) {
        this.activaSeccion = activaSeccion;
    }

    /**
     * @return the fechaCreacionSeccion
     */
    public Date getFechaCreacionSeccion() {
        return fechaCreacionSeccion;
    }

    /**
     * @param fechaCreacionSeccion the fechaCreacionSeccion to set
     */
    public void setFechaCreacionSeccion(Date fechaCreacionSeccion) {
        this.fechaCreacionSeccion = fechaCreacionSeccion;
    }

    /**
     * @return the preguntaDominanteOID
     */
    public String getPreguntaDominanteOID() {
        return preguntaDominanteOID;
    }

    /**
     * @param preguntaDominanteOID the preguntaDominanteOID to set
     */
    public void setPreguntaDominanteOID(String preguntaDominanteOID) {
        this.preguntaDominanteOID = preguntaDominanteOID;
    }

    /**
     * @return the tipoListaOpciones
     */
    public boolean isTipoListaOpciones() {
        return tipoListaOpciones;
    }

    /**
     * @param tipoListaOpciones the tipoListaOpciones to set
     */
    public void setTipoListaOpciones(boolean tipoListaOpciones) {
        this.tipoListaOpciones = tipoListaOpciones;
    }

    /**
     * @return the listasFiltroOID
     */
    public String getListasFiltroOID() {
        return listasFiltroOID;
    }

    /**
     * @param listasFiltroOID the listasFiltroOID to set
     */
    public void setListasFiltroOID(String listasFiltroOID) {
        this.listasFiltroOID = listasFiltroOID;
    }

    /**
     * @return the variable
     */
    public short getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(short variable) {
        this.variable = variable;
    }

    /**
     * @return the controlUsuario
     */
    public boolean isControlUsuario() {
        return controlUsuario;
    }

    /**
     * @param controlUsuario the controlUsuario to set
     */
    public void setControlUsuario(boolean controlUsuario) {
        this.controlUsuario = controlUsuario;
    }

    /**
     * @return the codigoBarras
     */
    public boolean isCodigoBarras() {
        return codigoBarras;
    }

    /**
     * @param codigoBarras the codigoBarras to set
     */
    public void setCodigoBarras(boolean codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    /**
     * @return the listasOID
     */
    public String getListasOID() {
        return listasOID;
    }

    /**
     * @param listasOID the listasOID to set
     */
    public void setListasOID(String listasOID) {
        this.listasOID = listasOID;
    }

    /**
     * @return the ciclica
     */
    public boolean isCiclica() {
        return ciclica;
    }

    /**
     * @param ciclica the ciclica to set
     */
    public void setCiclica(boolean ciclica) {
        this.ciclica = ciclica;
    }

    /**
     * @return the seleccionada
     */
    public boolean isSeleccionada() {
        return seleccionada;
    }

    /**
     * @param seleccionada the seleccionada to set
     */
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the seleccionadaInt
     */
    public int getSeleccionadaInt() {
        return seleccionadaInt;
    }

    /**
     * @param seleccionadaInt the seleccionadaInt to set
     */
    public void setSeleccionadaInt(int seleccionadaInt) {
        this.seleccionadaInt = seleccionadaInt;
    }

    /**
     * @return the miEncuesta
     */
    public Integer getMiEncuesta() {
        return miEncuesta;
    }

    /**
     * @param miEncuesta the miEncuesta to set
     */
    public void setMiEncuesta(Integer miEncuesta) {
        this.miEncuesta = miEncuesta;
    }

    /**
     * @return the seccionesPorEncuesta
     */
    public List<Secciones> getSeccionesPorEncuesta() {
        return seccionesPorEncuesta;
    }

    /**
     * @param seccionesPorEncuesta the seccionesPorEncuesta to set
     */
    public void setSeccionesPorEncuesta(List<Secciones> seccionesPorEncuesta) {
        this.seccionesPorEncuesta = seccionesPorEncuesta;
    }

    /**
     * @return the seccionSeleccionada
     */
    public Secciones getSeccionSeleccionada() {
        return seccionSeleccionada;
    }

    /**
     * @param seccionSeleccionada the seccionSeleccionada to set
     */
    public void setSeccionSeleccionada(Secciones seccionSeleccionada) {
        this.seccionSeleccionada = seccionSeleccionada;
    }

    /**
     * @return the tablaArbol
     */
    public TreeTable getTablaArbol() {
        return tablaArbol;
    }

    /**
     * @param tablaArbol the tablaArbol to set
     */
    public void setTablaArbol(TreeTable tablaArbol) {
        this.tablaArbol = tablaArbol;
    }

    /**
     * @return the muestraBoton
     */
    public boolean isMuestraBoton() {
        return muestraBoton;
    }

    /**
     * @param muestraBoton the muestraBoton to set
     */
    public void setMuestraBoton(boolean muestraBoton) {
        this.muestraBoton = muestraBoton;
    }

    /**
     * @return the servicePreguntas
     */
    public ServicePreguntas getServicePreguntas() {
        return servicePreguntas;
    }

    /**
     * @param servicePreguntas the servicePreguntas to set
     */
    public void setServicePreguntas(ServicePreguntas servicePreguntas) {
        this.servicePreguntas = servicePreguntas;
    }

    /**
     * @return the seccionOID
     */
    public String getSeccionOID() {
        return seccionOID;
    }

    /**
     * @param seccionOID the seccionOID to set
     */
    public void setSeccionOID(String seccionOID) {
        this.seccionOID = seccionOID;
    }

    /**
     * @return the textoPregunta
     */
    public String getTextoPregunta() {
        return textoPregunta;
    }

    /**
     * @param textoPregunta the textoPregunta to set
     */
    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    /**
     * @return the mensajePregunta
     */
    public String getMensajePregunta() {
        return mensajePregunta;
    }

    /**
     * @param mensajePregunta the mensajePregunta to set
     */
    public void setMensajePregunta(String mensajePregunta) {
        this.mensajePregunta = mensajePregunta;
    }

    /**
     * @return the ordenPregunta
     */
    public int getOrdenPregunta() {
        return ordenPregunta;
    }

    /**
     * @param ordenPregunta the ordenPregunta to set
     */
    public void setOrdenPregunta(int ordenPregunta) {
        this.ordenPregunta = ordenPregunta;
    }

    /**
     * @return the activaPregunta
     */
    public boolean isActivaPregunta() {
        return activaPregunta;
    }

    /**
     * @param activaPregunta the activaPregunta to set
     */
    public void setActivaPregunta(boolean activaPregunta) {
        this.activaPregunta = activaPregunta;
    }

    /**
     * @return the enabledPregunta
     */
    public boolean isEnabledPregunta() {
        return enabledPregunta;
    }

    /**
     * @param enabledPregunta the enabledPregunta to set
     */
    public void setEnabledPregunta(boolean enabledPregunta) {
        this.enabledPregunta = enabledPregunta;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the variablePregunta
     */
    public String getVariablePregunta() {
        return variablePregunta;
    }

    /**
     * @param variablePregunta the variablePregunta to set
     */
    public void setVariablePregunta(String variablePregunta) {
        this.variablePregunta = variablePregunta;
    }

    /**
     * @return the tipoRespuestaPregunta
     */
    public int getTipoRespuestaPregunta() {
        return tipoRespuestaPregunta;
    }

    /**
     * @param tipoRespuestaPregunta the tipoRespuestaPregunta to set
     */
    public void setTipoRespuestaPregunta(int tipoRespuestaPregunta) {
        this.tipoRespuestaPregunta = tipoRespuestaPregunta;
    }

    /**
     * @return the alertaPregunta
     */
    public String getAlertaPregunta() {
        return alertaPregunta;
    }

    /**
     * @param alertaPregunta the alertaPregunta to set
     */
    public void setAlertaPregunta(String alertaPregunta) {
        this.alertaPregunta = alertaPregunta;
    }

    /**
     * @return the subCategoriaPregunta
     */
    public int getSubCategoriaPregunta() {
        return subCategoriaPregunta;
    }

    /**
     * @param subCategoriaPregunta the subCategoriaPregunta to set
     */
    public void setSubCategoriaPregunta(int subCategoriaPregunta) {
        this.subCategoriaPregunta = subCategoriaPregunta;
    }

    /**
     * @return the bancoPreguntas
     */
    public boolean isBancoPreguntas() {
        return bancoPreguntas;
    }

    /**
     * @param bancoPreguntas the bancoPreguntas to set
     */
    public void setBancoPreguntas(boolean bancoPreguntas) {
        this.bancoPreguntas = bancoPreguntas;
    }

    /**
     * @return the categoriaPregunta
     */
    public int getCategoriaPregunta() {
        return categoriaPregunta;
    }

    /**
     * @param categoriaPregunta the categoriaPregunta to set
     */
    public void setCategoriaPregunta(int categoriaPregunta) {
        this.categoriaPregunta = categoriaPregunta;
    }

    /**
     * @return the requeridaPregunta
     */
    public boolean isRequeridaPregunta() {
        return requeridaPregunta;
    }

    /**
     * @param requeridaPregunta the requeridaPregunta to set
     */
    public void setRequeridaPregunta(boolean requeridaPregunta) {
        this.requeridaPregunta = requeridaPregunta;
    }

    /**
     * @return the verTexto
     */
    public boolean isVerTexto() {
        return verTexto;
    }

    /**
     * @param verTexto the verTexto to set
     */
    public void setVerTexto(boolean verTexto) {
        this.verTexto = verTexto;
    }

    /**
     * @return the verEntero
     */
    public boolean isVerEntero() {
        return verEntero;
    }

    /**
     * @param verEntero the verEntero to set
     */
    public void setVerEntero(boolean verEntero) {
        this.verEntero = verEntero;
    }

    /**
     * @return the verDecimal
     */
    public boolean isVerDecimal() {
        return verDecimal;
    }

    /**
     * @param verDecimal the verDecimal to set
     */
    public void setVerDecimal(boolean verDecimal) {
        this.verDecimal = verDecimal;
    }

    /**
     * @return the verInstruccion
     */
    public boolean isVerInstruccion() {
        return verInstruccion;
    }

    /**
     * @param verInstruccion the verInstruccion to set
     */
    public void setVerInstruccion(boolean verInstruccion) {
        this.verInstruccion = verInstruccion;
    }

    /**
     * @return the verCheck
     */
    public boolean isVerCheck() {
        return verCheck;
    }

    /**
     * @param verCheck the verCheck to set
     */
    public void setVerCheck(boolean verCheck) {
        this.verCheck = verCheck;
    }

    /**
     * @return the verOpcionMultiple
     */
    public boolean isVerOpcionMultiple() {
        return verOpcionMultiple;
    }

    /**
     * @param verOpcionMultiple the verOpcionMultiple to set
     */
    public void setVerOpcionMultiple(boolean verOpcionMultiple) {
        this.verOpcionMultiple = verOpcionMultiple;
    }

    /**
     * @return the verSeleccionMultiple
     */
    public boolean isVerSeleccionMultiple() {
        return verSeleccionMultiple;
    }

    /**
     * @param verSeleccionMultiple the verSeleccionMultiple to set
     */
    public void setVerSeleccionMultiple(boolean verSeleccionMultiple) {
        this.verSeleccionMultiple = verSeleccionMultiple;
    }

    /**
     * @return the listaTipoRespuesta
     */
    public List<Respuestas> getListaTipoRespuesta() {
        return listaTipoRespuesta;
    }

    /**
     * @param listaTipoRespuesta the listaTipoRespuesta to set
     */
    public void setListaTipoRespuesta(List<Respuestas> listaTipoRespuesta) {
        this.listaTipoRespuesta = listaTipoRespuesta;
    }

    /**
     * @return the nombreEncuestaPlantilla
     */
    public String getNombreEncuestaPlantilla() {
        return nombreEncuestaPlantilla;
    }

    /**
     * @param nombreEncuestaPlantilla the nombreEncuestaPlantilla to set
     */
    public void setNombreEncuestaPlantilla(String nombreEncuestaPlantilla) {
        this.nombreEncuestaPlantilla = nombreEncuestaPlantilla;
    }

    /**
     * @return the observacionesEncuesta
     */
    public String getObservacionesEncuesta() {
        return observacionesEncuesta;
    }

    /**
     * @param observacionesEncuesta the observacionesEncuesta to set
     */
    public void setObservacionesEncuesta(String observacionesEncuesta) {
        this.observacionesEncuesta = observacionesEncuesta;
    }

    /**
     * @return the esActivaEncuesta
     */
    public boolean isEsActivaEncuesta() {
        return esActivaEncuesta;
    }

    /**
     * @param esActivaEncuesta the esActivaEncuesta to set
     */
    public void setEsActivaEncuesta(boolean esActivaEncuesta) {
        this.esActivaEncuesta = esActivaEncuesta;
    }

    /**
     * @return the nombreBusquedaEncuesta
     */
    public String getNombreBusquedaEncuesta() {
        return nombreBusquedaEncuesta;
    }

    /**
     * @param nombreBusquedaEncuesta the nombreBusquedaEncuesta to set
     */
    public void setNombreBusquedaEncuesta(String nombreBusquedaEncuesta) {
        this.nombreBusquedaEncuesta = nombreBusquedaEncuesta;
    }

    /**
     * @return the tipoBusquedaEncuesta
     */
    public String getTipoBusquedaEncuesta() {
        return tipoBusquedaEncuesta;
    }

    /**
     * @param tipoBusquedaEncuesta the tipoBusquedaEncuesta to set
     */
    public void setTipoBusquedaEncuesta(String tipoBusquedaEncuesta) {
        this.tipoBusquedaEncuesta = tipoBusquedaEncuesta;
    }

    public List<String> getListaAgregaOpciones() {
        return listaAgregaOpciones;
    }

    public void setListaAgregaOpciones(List<String> listaAgregaOpciones) {
        this.listaAgregaOpciones = listaAgregaOpciones;
    }

    public String getOpcionAgregada() {
        return opcionAgregada;
    }

    public void setOpcionAgregada(String opcionAgregada) {
        this.opcionAgregada = opcionAgregada;
    }

    public List<Encuestas> getListaEncuestasFiltradas() {
        return listaEncuestasFiltradas;
    }

    public void setListaEncuestasFiltradas(
            List<Encuestas> listaEncuestasFiltradas) {
        this.listaEncuestasFiltradas = listaEncuestasFiltradas;
    }

    public Encuestas getLaEncuesta() {
        return laEncuesta;
    }

    public void setLaEncuesta(Encuestas laEncuesta) {
        this.laEncuesta = laEncuesta;
    }

    public String getNombreEncuestaEditar() {
        return nombreEncuestaEditar;
    }

    public void setNombreEncuestaEditar(String nombreEncuestaEditar) {
        this.nombreEncuestaEditar = nombreEncuestaEditar;
    }

    public String getObservacionEncuestaEditar() {
        return observacionEncuestaEditar;
    }

    public void setObservacionEncuestaEditar(String observacionEncuestaEditar) {
        this.observacionEncuestaEditar = observacionEncuestaEditar;
    }

    public boolean isActivaEncuestaEditar() {
        return activaEncuestaEditar;
    }

    public void setActivaEncuestaEditar(boolean activaEncuestaEditar) {
        this.activaEncuestaEditar = activaEncuestaEditar;
    }

    /**
     * @return the nombreSeccionConfigurar
     */
    public String getNombreSeccionConfigurar() {
        return nombreSeccionConfigurar;
    }

    /**
     * @param nombreSeccionConfigurar the nombreSeccionConfigurar to set
     */
    public void setNombreSeccionConfigurar(String nombreSeccionConfigurar) {
        this.nombreSeccionConfigurar = nombreSeccionConfigurar;
    }

    /**
     * @return the ordenSeccionConfigurar
     */
    public short getOrdenSeccionConfigurar() {
        return ordenSeccionConfigurar;
    }

    /**
     * @param ordenSeccionConfigurar the ordenSeccionConfigurar to set
     */
    public void setOrdenSeccionConfigurar(short ordenSeccionConfigurar) {
        this.ordenSeccionConfigurar = ordenSeccionConfigurar;
    }

    /**
     * @return the ciclicaSeccionConfigurar
     */
    public boolean isCiclicaSeccionConfigurar() {
        return ciclicaSeccionConfigurar;
    }

    /**
     * @param ciclicaSeccionConfigurar the ciclicaSeccionConfigurar to set
     */
    public void setCiclicaSeccionConfigurar(boolean ciclicaSeccionConfigurar) {
        this.ciclicaSeccionConfigurar = ciclicaSeccionConfigurar;
    }

    /**
     * @return the idTipoListaSeccionConfigurar
     */
    public int getIdTipoListaSeccionConfigurar() {
        return idTipoListaSeccionConfigurar;
    }

    /**
     * @param idTipoListaSeccionConfigurar the idTipoListaSeccionConfigurar to
     * set
     */
    public void setIdTipoListaSeccionConfigurar(int idTipoListaSeccionConfigurar) {
        this.idTipoListaSeccionConfigurar = idTipoListaSeccionConfigurar;
    }

    /**
     * @return the catalogosOIDSeccionConfigurar
     */
    public String getCatalogosOIDSeccionConfigurar() {
        return catalogosOIDSeccionConfigurar;
    }

    /**
     * @param catalogosOIDSeccionConfigurar the catalogosOIDSeccionConfigurar to
     * set
     */
    public void setCatalogosOIDSeccionConfigurar(
            String catalogosOIDSeccionConfigurar) {
        this.catalogosOIDSeccionConfigurar = catalogosOIDSeccionConfigurar;
    }

    /**
     * @return the preguntaOIDSeccionConfigurar
     */
    public String getPreguntaOIDSeccionConfigurar() {
        return preguntaOIDSeccionConfigurar;
    }

    /**
     * @param preguntaOIDSeccionConfigurar the preguntaOIDSeccionConfigurar to
     * set
     */
    public void setPreguntaOIDSeccionConfigurar(
            String preguntaOIDSeccionConfigurar) {
        this.preguntaOIDSeccionConfigurar = preguntaOIDSeccionConfigurar;
    }

    /**
     * @return the seleccionadaIntSeccionConfigurar
     */
    public int getSeleccionadaIntSeccionConfigurar() {
        return seleccionadaIntSeccionConfigurar;
    }

    /**
     * @param seleccionadaIntSeccionConfigurar the
     * seleccionadaIntSeccionConfigurar to set
     */
    public void setSeleccionadaIntSeccionConfigurar(
            int seleccionadaIntSeccionConfigurar) {
        this.seleccionadaIntSeccionConfigurar = seleccionadaIntSeccionConfigurar;
    }

    /**
     * @return the botonEditaSeccion
     */
    public CommandButton getBotonEditaSeccion() {
        return botonEditaSeccion;
    }

    /**
     * @param botonEditaSeccion the botonEditaSeccion to set
     */
    public void setBotonEditaSeccion(CommandButton botonEditaSeccion) {
        this.botonEditaSeccion = botonEditaSeccion;
    }

    /**
     * @return the link
     */
    public CommandLink getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(CommandLink link) {
        this.link = link;
    }

    /**
     * @return the botonEditaPregunta
     */
    public CommandButton getBotonEditaPregunta() {
        return botonEditaPregunta;
    }

    /**
     * @param botonEditaPregunta the botonEditaPregunta to set
     */
    public void setBotonEditaPregunta(CommandButton botonEditaPregunta) {
        this.botonEditaPregunta = botonEditaPregunta;
    }

    /**
     * @return the botonEditaOpcion
     */
    public CommandButton getBotonEditaOpcion() {
        return botonEditaOpcion;
    }

    /**
     * @param botonEditaOpcion the botonEditaOpcion to set
     */
    public void setBotonEditaOpcion(CommandButton botonEditaOpcion) {
        this.botonEditaOpcion = botonEditaOpcion;
    }

    /**
     * @return the tabEdicion
     */
    public TabView getTabEdicion() {
        return tabEdicion;
    }

    /**
     * @param tabEdicion the tabEdicion to set
     */
    public void setTabEdicion(TabView tabEdicion) {
        this.tabEdicion = tabEdicion;
    }

    /**
     * @return the miSeccionOID
     */
    public String getMiSeccionOID() {
        return miSeccionOID;
    }

    /**
     * @param miSeccionOID the miSeccionOID to set
     */
    public void setMiSeccionOID(String miSeccionOID) {
        this.miSeccionOID = miSeccionOID;
    }

    /**
     * @return the catalogoOIDSC
     */
    public String getCatalogoOIDSC() {
        return catalogoOIDSC;
    }

    /**
     * @param catalogoOIDSC the catalogoOIDSC to set
     */
    public void setCatalogoOIDSC(String catalogoOIDSC) {
        this.catalogoOIDSC = catalogoOIDSC;
    }

    /**
     * @return the miPreguntaOID
     */
    public String getMiPreguntaOID() {
        return miPreguntaOID;
    }

    /**
     * @param miPreguntaOID the miPreguntaOID to set
     */
    public void setMiPreguntaOID(String miPreguntaOID) {
        this.miPreguntaOID = miPreguntaOID;
    }

    /**
     * @return the longitudMaxima
     */
    public int getLongitudMaxima() {
        return longitudMaxima;
    }

    /**
     * @param longitudMaxima the longitudMaxima to set
     */
    public void setLongitudMaxima(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    /**
     * @return the valorMaximo
     */
    public int getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param valorMaximo the valorMaximo to set
     */
    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * @return the valorMinimo
     */
    public int getValorMinimo() {
        return valorMinimo;
    }

    /**
     * @param valorMinimo the valorMinimo to set
     */
    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    /**
     * @return the numeroDecimales
     */
    public int getNumeroDecimales() {
        return numeroDecimales;
    }

    /**
     * @param numeroDecimales the numeroDecimales to set
     */
    public void setNumeroDecimales(int numeroDecimales) {
        this.numeroDecimales = numeroDecimales;
    }

    /**
     * @return the miTab
     */
    public Tab getMiTab() {
        return miTab;
    }

    /**
     * @param miTab the miTab to set
     */
    public void setMiTab(Tab miTab) {
        this.miTab = miTab;
    }

    /**
     * @return the miPanel
     */
    public PanelGrid getMiPanel() {
        return miPanel;
    }

    /**
     * @param miPanel the miPanel to set
     */
    public void setMiPanel(PanelGrid miPanel) {
        this.miPanel = miPanel;
    }

    /**
     * @return the apareceCajaTexto
     */
    public boolean isApareceCajaTexto() {
        return apareceCajaTexto;
    }

    /**
     * @param apareceCajaTexto the apareceCajaTexto to set
     */
    public void setApareceCajaTexto(boolean apareceCajaTexto) {
        this.apareceCajaTexto = apareceCajaTexto;
    }

    /**
     * @return the opcion
     */
    public String getOpcion() {
        return opcion;
    }

    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    /**
     * @return the apareceRadio
     */
    public boolean isApareceRadio() {
        return apareceRadio;
    }

    /**
     * @param apareceRadio the apareceRadio to set
     */
    public void setApareceRadio(boolean apareceRadio) {
        this.apareceRadio = apareceRadio;
    }

    /**
     * @return the apareceAreaTexto
     */
    public boolean isApareceAreaTexto() {
        return apareceAreaTexto;
    }

    /**
     * @param apareceAreaTexto the apareceAreaTexto to set
     */
    public void setApareceAreaTexto(boolean apareceAreaTexto) {
        this.apareceAreaTexto = apareceAreaTexto;
    }

    /**
     * @return the apareceCalendario
     */
    public boolean isApareceCalendario() {
        return apareceCalendario;
    }

    /**
     * @param apareceCalendario the apareceCalendario to set
     */
    public void setApareceCalendario(boolean apareceCalendario) {
        this.apareceCalendario = apareceCalendario;
    }

    /**
     * @return the apareceCheckBox
     */
    public boolean isApareceCheckBox() {
        return apareceCheckBox;
    }

    /**
     * @param apareceCheckBox the apareceCheckBox to set
     */
    public void setApareceCheckBox(boolean apareceCheckBox) {
        this.apareceCheckBox = apareceCheckBox;
    }

    /**
     * @return the apareceUpload
     */
    public boolean isApareceUpload() {
        return apareceUpload;
    }

    /**
     * @param apareceUpload the apareceUpload to set
     */
    public void setApareceUpload(boolean apareceUpload) {
        this.apareceUpload = apareceUpload;
    }

    /**
     * @return the apareceImagen
     */
    public boolean isApareceImagen() {
        return apareceImagen;
    }

    /**
     * @param apareceImagen the apareceImagen to set
     */
    public void setApareceImagen(boolean apareceImagen) {
        this.apareceImagen = apareceImagen;
    }

    /**
     * @return the listaRenders
     */
    public List<Boolean> getListaRenders() {
        return listaRenders;
    }

    /**
     * @param listaRenders the listaRenders to set
     */
    public void setListaRenders(List<Boolean> listaRenders) {
        this.listaRenders = listaRenders;
    }

    /**
     * @return the listaOpciones
     */
    public List<Opciones> getListaOpciones() {
        return listaOpciones;
    }

    /**
     * @param listaOpciones the listaOpciones to set
     */
    public void setListaOpciones(List<Opciones> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    /**
     * @return the serviceOpciones
     */
    public ServiceOpciones getServiceOpciones() {
        return serviceOpciones;
    }

    /**
     * @param serviceOpciones the serviceOpciones to set
     */
    public void setServiceOpciones(ServiceOpciones serviceOpciones) {
        this.serviceOpciones = serviceOpciones;
    }

    /**
     * @return the catalogosOIDOpciones
     */
    public String getCatalogosOIDOpciones() {
        return catalogosOIDOpciones;
    }

    /**
     * @param catalogosOIDOpciones the catalogosOIDOpciones to set
     */
    public void setCatalogosOIDOpciones(String catalogosOIDOpciones) {
        this.catalogosOIDOpciones = catalogosOIDOpciones;
    }

    /**
     * @return the idCategoriaBanco
     */
    public int getIdCategoriaBanco() {
        return idCategoriaBanco;
    }

    /**
     * @param idCategoriaBanco the idCategoriaBanco to set
     */
    public void setIdCategoriaBanco(int idCategoriaBanco) {
        this.idCategoriaBanco = idCategoriaBanco;
    }

    /**
     * @return the idSubCategoriaBanco
     */
    public int getIdSubCategoriaBanco() {
        return idSubCategoriaBanco;
    }

    /**
     * @param idSubCategoriaBanco the idSubCategoriaBanco to set
     */
    public void setIdSubCategoriaBanco(int idSubCategoriaBanco) {
        this.idSubCategoriaBanco = idSubCategoriaBanco;
    }

    /**
     * @return the listaPreguntasBanco
     */
    public List<Preguntas> getListaPreguntasBanco() {
        return listaPreguntasBanco;
    }

    /**
     * @param listaPreguntasBanco the listaPreguntasBanco to set
     */
    public void setListaPreguntasBanco(List<Preguntas> listaPreguntasBanco) {
        this.listaPreguntasBanco = listaPreguntasBanco;
    }

    public List<Preguntas> getPreguntasSeleccionada() {
        return preguntasSeleccionada;
    }

    public void setPreguntasSeleccionada(List<Preguntas> preguntasSeleccionada) {
        this.preguntasSeleccionada = preguntasSeleccionada;
    }

    

    /**
     * @return the miSeccionOIDBanco
     */
    public String getMiSeccionOIDBanco() {
        return miSeccionOIDBanco;
    }

    /**
     * @param miSeccionOIDBanco the miSeccionOIDBanco to set
     */
    public void setMiSeccionOIDBanco(String miSeccionOIDBanco) {
        this.miSeccionOIDBanco = miSeccionOIDBanco;
    }

    /**
     * @return the nombrePlantilla
     */
    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    /**
     * @param nombrePlantilla the nombrePlantilla to set
     */
    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    /**
     * @return the opcionCheck
     */
    public String[] getOpcionCheck() {
        return opcionCheck;
    }

    /**
     * @param opcionCheck the opcionCheck to set
     */
    public void setOpcionCheck(String[] opcionCheck) {
        this.opcionCheck = opcionCheck;
    }

    /**
     * @return the miSeccionOIDPregunta
     */
    public String getMiSeccionOIDPregunta() {
        return miSeccionOIDPregunta;
    }

    /**
     * @param miSeccionOIDPregunta the miSeccionOIDPregunta to set
     */
    public void setMiSeccionOIDPregunta(String miSeccionOIDPregunta) {
        this.miSeccionOIDPregunta = miSeccionOIDPregunta;
    }

    /**
     * @return the valorAlertaPregunta
     */
    public String getValorAlertaPregunta() {
        return valorAlertaPregunta;
    }

    /**
     * @param valorAlertaPregunta the valorAlertaPregunta to set
     */
    public void setValorAlertaPregunta(String valorAlertaPregunta) {
        this.valorAlertaPregunta = valorAlertaPregunta;
    }

    public UploadedFile getArchivoPreguntas() {
        return archivoPreguntas;
    }

    public void setArchivoPreguntas(UploadedFile archivoPreguntas) {
        this.archivoPreguntas = archivoPreguntas;
    }

    public int getIdTipoListaConfiguraPregunta() {
        return idTipoListaConfiguraPregunta;
    }

    public void setIdTipoListaConfiguraPregunta(int idTipoListaConfiguraPregunta) {
        this.idTipoListaConfiguraPregunta = idTipoListaConfiguraPregunta;
    }

    public List<Catalogos> getListaFiltraPor() {
        return listaFiltraPor;
    }

    public void setListaFiltraPor(List<Catalogos> listaFiltraPor) {
        this.listaFiltraPor = listaFiltraPor;
    }

    public String getListasOIDFiltro() {
        return listasOIDFiltro;
    }

    public void setListasOIDFiltro(String listasOIDFiltro) {
        this.listasOIDFiltro = listasOIDFiltro;
    }

    public Integer getVariablePreguntaOpcionMultiple() {
        return variablePreguntaOpcionMultiple;
    }

    public void setVariablePreguntaOpcionMultiple(Integer variablePreguntaOpcionMultiple) {
        this.variablePreguntaOpcionMultiple = variablePreguntaOpcionMultiple;
    }

    public InputText getInputText() {
        return inputText;
    }

    public void setInputText(InputText inputText) {
        this.inputText = inputText;
    }

    public SelectOneRadio getRadio() {
        return radio;
    }

    public void setRadio(SelectOneRadio radio) {
        this.radio = radio;
    }

    public SelectManyCheckbox getMuchosCheck() {
        return muchosCheck;
    }

    public void setMuchosCheck(SelectManyCheckbox muchosCheck) {
        this.muchosCheck = muchosCheck;
    }

    public boolean isSeleccionFiltro() {
        return seleccionFiltro;
    }

    public void setSeleccionFiltro(boolean seleccionFiltro) {
        this.seleccionFiltro = seleccionFiltro;
    }

    public boolean isMostrarCampos() {
        return mostrarCampos;
    }

    public void setMostrarCampos(boolean mostrarCampos) {
        this.mostrarCampos = mostrarCampos;
    }

    public List<Encuestas> getListaPlantillas() {
        return listaPlantillas;
    }

    public void setListaPlantillas(List<Encuestas> listaPlantillas) {
        this.listaPlantillas = listaPlantillas;
    }

    public boolean isMostrarCheckFiltro() {
        return mostrarCheckFiltro;
    }

    public void setMostrarCheckFiltro(boolean mostrarCheckFiltro) {
        this.mostrarCheckFiltro = mostrarCheckFiltro;
    }

    /**
     * @return the claveSeccionConfigurar
     */
    public String getClaveSeccionConfigurar() {
        return claveSeccionConfigurar;
    }

    /**
     * @param claveSeccionConfigurar the claveSeccionConfigurar to set
     */
    public void setClaveSeccionConfigurar(String claveSeccionConfigurar) {
        this.claveSeccionConfigurar = claveSeccionConfigurar;
    }

    /**
     * @return the controlUsuarioSeccionConfigurar
     */
    public boolean isControlUsuarioSeccionConfigurar() {
        return controlUsuarioSeccionConfigurar;
    }

    /**
     * @param controlUsuarioSeccionConfigurar the
     * controlUsuarioSeccionConfigurar to set
     */
    public void setControlUsuarioSeccionConfigurar(boolean controlUsuarioSeccionConfigurar) {
        this.controlUsuarioSeccionConfigurar = controlUsuarioSeccionConfigurar;
    }

    /**
     * @return the disableTipoLista
     */
    public boolean isDisableTipoLista() {
        return disableTipoLista;
    }

    /**
     * @param disableTipoLista the disableTipoLista to set
     */
    public void setDisableTipoLista(boolean disableTipoLista) {
        this.disableTipoLista = disableTipoLista;
    }

    public boolean isDisableContorlUsuario() {
        return disableContorlUsuario;
    }

    public void setDisableContorlUsuario(boolean disableContorlUsuario) {
        this.disableContorlUsuario = disableContorlUsuario;
    }
    
    

    /**
     * @return the sePuedeSeleccionar
     */
    public boolean isSePuedeSeleccionar() {
        return sePuedeSeleccionar;
    }

    /**
     * @param sePuedeSeleccionar the sePuedeSeleccionar to set
     */
    public void setSePuedeSeleccionar(boolean sePuedeSeleccionar) {
        this.sePuedeSeleccionar = sePuedeSeleccionar;
    }

    /**
     * @return the sePuedeEditar
     */
    public boolean isSePuedeEditar() {
        return sePuedeEditar;
    }

    /**
     * @param sePuedeEditar the sePuedeEditar to set
     */
    public void setSePuedeEditar(boolean sePuedeEditar) {
        this.sePuedeEditar = sePuedeEditar;
    }

    /**
     * @return the deshabilitaInputOpcion
     */
    public boolean isDeshabilitaInputOpcion() {
        return deshabilitaInputOpcion;
    }

    /**
     * @param deshabilitaInputOpcion the deshabilitaInputOpcion to set
     */
    public void setDeshabilitaInputOpcion(boolean deshabilitaInputOpcion) {
        this.deshabilitaInputOpcion = deshabilitaInputOpcion;
    }

    /**
     * @return the deshabilitaBotonOpcion
     */
    public boolean isDeshabilitaBotonOpcion() {
        return deshabilitaBotonOpcion;
    }

    /**
     * @param deshabilitaBotonOpcion the deshabilitaBotonOpcion to set
     */
    public void setDeshabilitaBotonOpcion(boolean deshabilitaBotonOpcion) {
        this.deshabilitaBotonOpcion = deshabilitaBotonOpcion;
    }

    /**
     * @return the deshabilitaSeleccion
     */
    public boolean isDeshabilitaSeleccion() {
        return deshabilitaSeleccion;
    }

    /**
     * @param deshabilitaSeleccion the deshabilitaSeleccion to set
     */
    public void setDeshabilitaSeleccion(boolean deshabilitaSeleccion) {
        this.deshabilitaSeleccion = deshabilitaSeleccion;
    }

    /**
     * @return the deshabilitaCatalogos
     */
    public boolean isDeshabilitaCatalogos() {
        return deshabilitaCatalogos;
    }

    /**
     * @param deshabilitaCatalogos the deshabilitaCatalogos to set
     */
    public void setDeshabilitaCatalogos(boolean deshabilitaCatalogos) {
        this.deshabilitaCatalogos = deshabilitaCatalogos;
    }

    /**
     * @return the deshabilitaSeleccionFiltro
     */
    public boolean isDeshabilitaSeleccionFiltro() {
        return deshabilitaSeleccionFiltro;
    }

    /**
     * @param deshabilitaSeleccionFiltro the deshabilitaSeleccionFiltro to set
     */
    public void setDeshabilitaSeleccionFiltro(boolean deshabilitaSeleccionFiltro) {
        this.deshabilitaSeleccionFiltro = deshabilitaSeleccionFiltro;
    }

    /**
     * @return the deshabilitaPreguntas
     */
    public boolean isDeshabilitaPreguntas() {
        return deshabilitaPreguntas;
    }

    /**
     * @param deshabilitaPreguntas the deshabilitaPreguntas to set
     */
    public void setDeshabilitaPreguntas(boolean deshabilitaPreguntas) {
        this.deshabilitaPreguntas = deshabilitaPreguntas;
    }

    /**
     * @return the preguntasOIDOpciones
     */
    public String getPreguntasOIDOpciones() {
        return preguntasOIDOpciones;
    }

    /**
     * @param preguntasOIDOpciones the preguntasOIDOpciones to set
     */
    public void setPreguntasOIDOpciones(String preguntasOIDOpciones) {
        this.preguntasOIDOpciones = preguntasOIDOpciones;
    }

    /**
     * @return the listaPreguntas
     */
    public List<Preguntas> getListaPreguntas() {
        return listaPreguntas;
    }

    /**
     * @param listaPreguntas the listaPreguntas to set
     */
    public void setListaPreguntas(List<Preguntas> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    /**
     * @return the model
     */
    public DynaFormModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(DynaFormModel model) {
        this.model = model;
    }

    /**
     * @return the laSeccionOIDPregunta
     */
    public String getLaSeccionOIDPregunta() {
        return laSeccionOIDPregunta;
    }

    /**
     * @param laSeccionOIDPregunta the laSeccionOIDPregunta to set
     */
    public void setLaSeccionOIDPregunta(String laSeccionOIDPregunta) {
        this.laSeccionOIDPregunta = laSeccionOIDPregunta;
    }

    /**
     * @return the laPreguntaOID
     */
    public String getLaPreguntaOID() {
        return laPreguntaOID;
    }

    /**
     * @param laPreguntaOID the laPreguntaOID to set
     */
    public void setLaPreguntaOID(String laPreguntaOID) {
        this.laPreguntaOID = laPreguntaOID;
    }
    
    public List<Encuestas> getEncuestaSeleccionadas() {
        return encuestaSeleccionadas;
    }

    public void setEncuestaSeleccionadas(List<Encuestas> encuestaSeleccionadas) {
        this.encuestaSeleccionadas = encuestaSeleccionadas;
    }
    
    
    
    /**
     * @return the idTipoListaSeleccionMultiple
     */
    public int getIdTipoListaSeleccionMultiple() {
        return idTipoListaSeleccionMultiple;
    }

    /**
     * @param idTipoListaSeleccionMultiple the idTipoListaSeleccionMultiple to
     * set
     */
    public void setIdTipoListaSeleccionMultiple(int idTipoListaSeleccionMultiple) {
        this.idTipoListaSeleccionMultiple = idTipoListaSeleccionMultiple;
    }

    /**
     * @return the catalogosOIDSeleccionMultiple
     */
    public String getCatalogosOIDSeleccionMultiple() {
        return catalogosOIDSeleccionMultiple;
    }

    /**
     * @param catalogosOIDSeleccionMultiple the catalogosOIDSeleccionMultiple to
     * set
     */
    public void setCatalogosOIDSeleccionMultiple(String catalogosOIDSeleccionMultiple) {
        this.catalogosOIDSeleccionMultiple = catalogosOIDSeleccionMultiple;
    }

    /**
     * @return the seleccionFiltroSM
     */
    public boolean isSeleccionFiltroSM() {
        return seleccionFiltroSM;
    }

    /**
     * @param seleccionFiltroSM the seleccionFiltroSM to set
     */
    public void setSeleccionFiltroSM(boolean seleccionFiltroSM) {
        this.seleccionFiltroSM = seleccionFiltroSM;
    }

    /**
     * @return the listasOIDFiltroSM
     */
    public String getListasOIDFiltroSM() {
        return listasOIDFiltroSM;
    }

    /**
     * @param listasOIDFiltroSM the listasOIDFiltroSM to set
     */
    public void setListasOIDFiltroSM(String listasOIDFiltroSM) {
        this.listasOIDFiltroSM = listasOIDFiltroSM;
    }

    /**
     * @return the opcionAgregadaSM
     */
    public String getOpcionAgregadaSM() {
        return opcionAgregadaSM;
    }

    /**
     * @param opcionAgregadaSM the opcionAgregadaSM to set
     */
    public void setOpcionAgregadaSM(String opcionAgregadaSM) {
        this.opcionAgregadaSM = opcionAgregadaSM;
    }

    /**
     * @return the valorMinimoSM
     */
    public int getValorMinimoSM() {
        return valorMinimoSM;
    }

    /**
     * @param valorMinimoSM the valorMinimoSM to set
     */
    public void setValorMinimoSM(int valorMinimoSM) {
        this.valorMinimoSM = valorMinimoSM;
    }

    /**
     * @return the deshabilitaSeleccionFiltroSM
     */
    public boolean isDeshabilitaSeleccionFiltroSM() {
        return deshabilitaSeleccionFiltroSM;
    }

    /**
     * @param deshabilitaSeleccionFiltroSM the deshabilitaSeleccionFiltroSM to
     * set
     */
    public void setDeshabilitaSeleccionFiltroSM(boolean deshabilitaSeleccionFiltroSM) {
        this.deshabilitaSeleccionFiltroSM = deshabilitaSeleccionFiltroSM;
    }

    /**
     * @return the verLosMultiples
     */
    public boolean isVerLosMultiples() {
        return verLosMultiples;
    }

    /**
     * @param verLosMultiples the verLosMultiples to set
     */
    public void setVerLosMultiples(boolean verLosMultiples) {
        this.verLosMultiples = verLosMultiples;
    }

	public String getProtocoloLeerImg() {
		return protocoloLeerImg;
	}

	public void setProtocoloLeerImg(String protocoloLeerImg) {
		this.protocoloLeerImg = protocoloLeerImg;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getPathImgEncuestas() {
		return pathImgEncuestas;
	}

	public void setPathImgEncuestas(String pathImgEncuestas) {
		this.pathImgEncuestas = pathImgEncuestas;
	}

	public StreamedContent getLogo() {
		return logo;
	}

	public void setLogo(StreamedContent logo) {
		this.logo = logo;
	}
        
	public ServiceParametros getServiceParametros() {
		return serviceParametros;
	}
        
	public void setServiceParametros(ServiceParametros serviceParametros) {
		this.serviceParametros = serviceParametros;
	}
        
	public String getPathImagePreguntas() {
		return pathImagePreguntas;
	}
        
	public void setPathImagePreguntas(String pathImagePreguntas) {
		this.pathImagePreguntas = pathImagePreguntas;
	}

    public boolean isDisableCatalogoOrigen() {
        return disableCatalogoOrigen;
    }

    public void setDisableCatalogoOrigen(boolean disableCatalogoOrigen) {
        this.disableCatalogoOrigen = disableCatalogoOrigen;
    }

    public boolean isDisablePreguntaOrigen() {
        return disablePreguntaOrigen;
    }

    public void setDisablePreguntaOrigen(boolean disablePreguntaOrigen) {
        this.disablePreguntaOrigen = disablePreguntaOrigen;
    }

    public Integer getUnidadesNegocioId() {
        return unidadesNegocioId;
    }

    public void setUnidadesNegocioId(Integer unidadesNegocioId) {
        this.unidadesNegocioId = unidadesNegocioId;
    }

    public boolean isOpcionOtros() {
        return opcionOtros;
    }

    public void setOpcionOtros(boolean opcionOtros) {
        this.opcionOtros = opcionOtros;
    }

    public boolean isCondicionada() {
        return condicionada;
    }

    public void setCondicionada(boolean condicionada) {
        this.condicionada = condicionada;
    }

    public String getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(String archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public boolean isDeshabilitaCondicionada() {
        return deshabilitaCondicionada;
    }

    public void setDeshabilitaCondicionada(boolean deshabilitaCondicionada) {
        this.deshabilitaCondicionada = deshabilitaCondicionada;
    }

    public boolean isDeshabilitaCodigoBarras() {
        return deshabilitaCodigoBarras;
    }

    public void setDeshabilitaCodigoBarras(boolean deshabilitaCodigoBarras) {
        this.deshabilitaCodigoBarras = deshabilitaCodigoBarras;
    }

    public boolean isDeshabilitaOpcionOtros() {
        return deshabilitaOpcionOtros;
    }

    public void setDeshabilitaOpcionOtros(boolean deshabilitaOpcionOtros) {
        this.deshabilitaOpcionOtros = deshabilitaOpcionOtros;
    }

    public boolean isDeshabilitaConfigOpciones() {
        return deshabilitaConfigOpciones;
    }

    public void setDeshabilitaConfigOpciones(boolean deshabilitaConfigOpciones) {
        this.deshabilitaConfigOpciones = deshabilitaConfigOpciones;
    }

    public boolean isDeshabilitaSeleccionMinima() {
        return deshabilitaSeleccionMinima;
    }

    public void setDeshabilitaSeleccionMinima(boolean deshabilitaSeleccionMinima) {
        this.deshabilitaSeleccionMinima = deshabilitaSeleccionMinima;
    }

    public List<Opciones> getListaOpcionesConf() {
        return listaOpcionesConf;
    }

    public void setListaOpcionesConf(List<Opciones> listaOpcionesConf) {
        this.listaOpcionesConf = listaOpcionesConf;
    }

    public Opciones getOpcionSelectedConf() {
        return opcionSelectedConf;
    }

    public void setOpcionSelectedConf(Opciones opcionSelectedConf) {
        this.opcionSelectedConf = opcionSelectedConf;
    }

    public Encuestas getEncuestaConf() {
        return encuestaConf;
    }

    public void setEncuestaConf(Encuestas encuestaConf) {
        this.encuestaConf = encuestaConf;
    }

    public boolean isDeshabilitaPreguntaInicioCU() {
        return deshabilitaPreguntaInicioCU;
    }

    public void setDeshabilitaPreguntaInicioCU(boolean deshabilitaPreguntaInicioCU) {
        this.deshabilitaPreguntaInicioCU = deshabilitaPreguntaInicioCU;
    }

    public List<Preguntas> getListaPreguntasCU() {
        return listaPreguntasCU;
    }

    public void setListaPreguntasCU(List<Preguntas> listaPreguntasCU) {
        this.listaPreguntasCU = listaPreguntasCU;
    }

    public Integer getSeleccionadaOpcConf() {
        return seleccionadaOpcConf;
    }

    public void setSeleccionadaOpcConf(Integer seleccionadaOpcConf) {
        this.seleccionadaOpcConf = seleccionadaOpcConf;
    }

    public DefaultStreamedContent getImgInstruccion() {
        return imgInstruccion;
    }

    public void setImgInstruccion(DefaultStreamedContent imgInstruccion) {
        this.imgInstruccion = imgInstruccion;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isOperacion() {
        return operacion;
    }

    public void setOperacion(boolean operacion) {
        this.operacion = operacion;
    }

    public boolean isDeshabilitaComboPlantilla() {
        return deshabilitaComboPlantilla;
    }

    public void setDeshabilitaComboPlantilla(boolean deshabilitaComboPlantilla) {
        this.deshabilitaComboPlantilla = deshabilitaComboPlantilla;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getProtocoloGuardarImg() {
        return protocoloGuardarImg;
    }

    public void setProtocoloGuardarImg(String protocoloGuardarImg) {
        this.protocoloGuardarImg = protocoloGuardarImg;
    }

    public ServiceVariables getServiceVariables() {
        return serviceVariables;
    }

    public void setServiceVariables(ServiceVariables serviceVariables) {
        this.serviceVariables = serviceVariables;
    }

    public List<Variable> getListaVariablesFiltro() {
        return listaVariablesFiltro;
    }

    public void setListaVariablesFiltro(List<Variable> listaVariablesFiltro) {
        this.listaVariablesFiltro = listaVariablesFiltro;
    }

    public boolean isVerCodigoBarras() {
        return verCodigoBarras;
    }

    public void setVerCodigoBarras(boolean verCodigoBarras) {
        this.verCodigoBarras = verCodigoBarras;
    }

    public boolean isVerAgregarOpcionOtro() {
        return verAgregarOpcionOtro;
    }

    public void setVerAgregarOpcionOtro(boolean verAgregarOpcionOtro) {
        this.verAgregarOpcionOtro = verAgregarOpcionOtro;
    }

    public Secciones getMiSeccionPadre() {
        return miSeccionPadre;
    }

    public void setMiSeccionPadre(Secciones miSeccionPadre) {
        this.miSeccionPadre = miSeccionPadre;
    }
}
