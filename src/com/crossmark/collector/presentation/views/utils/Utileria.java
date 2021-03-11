package com.crossmark.collector.presentation.views.utils;

import org.apache.log4j.Logger;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

public final class Utileria implements Serializable {

    public static final String ACCION = "accion";
    public static final String EDITAR = "editar";
    public static final String CREAR = "crear";
    public static final String ELIMINAR = "eliminar";
    public static final String SALIR = "salir";
    public static final SimpleDateFormat DATA_FORMAT_1 = new SimpleDateFormat("dd/MM/yy");
    public static final SimpleDateFormat DATA_FORMAT_2 = new SimpleDateFormat();
    public static final SimpleDateFormat DATA_FORMAT_3 = new SimpleDateFormat();
    public static final SimpleDateFormat DATA_FORMAT_4 = new SimpleDateFormat();
    public static final SimpleDateFormat DATA_FORMAT_5 = new SimpleDateFormat();

    // property file is: package/name/messages.properties
    private static final String BUNDLE_NAME = "com.crossmark.collector.presentation.views.utils.messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);
    private static final String LDAP = "com.crossmark.collector.presentation.views.utils.ldap";
    private static final ResourceBundle LDAP_PROPERTIES = ResourceBundle
            .getBundle(LDAP);
    public static String LOGIN = "Login.xhtml";

    public static Logger logger(Class<?> clase) {
        return Logger.getLogger(clase);
    }

    public static String getProperties(String key) {
        try {
            return LDAP_PROPERTIES.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, Object... params) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static void mensajeSatisfactorio(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensajeAlerta(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensajeAlerta(FacesContext fc, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                msj.toString());
        fc.addMessage(null, message);
    }

    public static void mensajeErroneo(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "", msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensajeSatisfactorio(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                title.toString(), msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensajeAlerta(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                title.toString(), msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensajeErroneo(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                title.toString(), msj.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static FacesMessage mensajeSatisfactorio_(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                msj.toString());
        return message;
    }

    public static FacesMessage mensajeAlerta_(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                msj.toString());
        return message;
    }

    public static FacesMessage mensajeErroneo_(Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "", msj.toString());
        return message;
    }

    public static FacesMessage mensajeSatisfactorio_(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                title.toString(), msj.toString());
        return message;
    }

    public static FacesMessage mensajeAlerta_(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                title.toString(), msj.toString());
        return message;
    }

    public static FacesMessage mensajeErroneo_(Object title, Object msj) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                title.toString(), msj.toString());
        return message;
    }

    /**
     * Busca un componente en la vista según si id.
     *
     * @param id
     * @return
     */
    public static UIComponent findComponent(String id) {
        UIViewRoot iViewRoot = getFacesContext().getViewRoot();
        return iViewRoot.findComponent(id);
    }

    /**
     * Refresca un componente visual por su id.
     *
     * @param id
     */
    public static void refreshComponent(String id) {
        PartialViewContext partialViewContext = getFacesContext()
                .getPartialViewContext();
        Collection<String> renderIds = partialViewContext.getRenderIds();
        renderIds.add(id);
    }

    /**
     * Obtiene el ManagedBean del contexto.
     *
     * @param <T>
     * @param managedBeanName
     * @return
     */
    @SuppressWarnings({"unchecked", "el-syntax"})
    public static <T> T getManagedBean(String managedBeanName) {
        return (T) getFacesContext().getApplication().evaluateExpressionGet(
                getFacesContext(), "#{" + managedBeanName + "}", Object.class);
    }

    public static FacesContext getFacesContext() {
        setFacesLocaleMXN();
        return FacesContext.getCurrentInstance();
    }

    public static void setFacesLocaleMXN() {
        FacesContext.getCurrentInstance().getViewRoot()
                .setLocale(new Locale("es_MX"));
    }

    /**
     * Obtiene el domimio del server
     *
     * @return
     */
    public static String getLocalName() {
        return getRequest().getLocalName();
    }

    /**
     * Obtiene la IP del server.
     *
     * @return
     */
    public static String getLocalAddress() {
        return getRequest().getLocalAddr();
    }

    /**
     * Obtiene el request del contexto del servlet.
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext()
                .getRequest();
    }

    /**
     * Obtiene un parametro del request.
     *
     * @return
     */
    public static String getRequestParameter(String name) {
        return getRequest().getParameter(name);
    }

    public static void redirect(String uri) {
        try {

            getFacesContext().responseComplete();
            getFacesContext().getExternalContext().dispatch(uri);
        } catch (IOException e) {
            logger(Utileria.class).error(e);
        }
    }

    public static void irA(String url){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            logger(Utileria.class).error(e);
        }
    }

    /**
     * @return
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext()
                .getResponse();
    }

    /**
     * Muestra mensaje de jsf.
     */
    public static void sendMessage(String message) {
        getFacesContext().addMessage(null, new FacesMessage(message));
    }

    /**
     * Muestra mensaje de jsf.
     *
     * @param id
     * @param severity
     * @param summary
     * @param detail
     */
    public static void sendMessage(String id, FacesMessage.Severity severity,
                                   String summary, String detail) {
        getFacesContext().addMessage(id,
                new FacesMessage(severity, summary, detail));
    }

    /**
     * Pone un attributo en session.
     *
     * @param name
     * @param value
     */
    public static void setSessionAttribute(String name, Object value) {
        getHttpSession().setAttribute(name, value);
    }

    /**
     * Obtiene un attributo de session.
     *
     * @param name
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String name) {
        return (T) getHttpSession().getAttribute(name);
    }

    private static HttpSession getHttpSession() {
        return getRequest().getSession();
    }

    /**
     * Redirecciona a un navigation case.
     *
     * @param outcome
     */
    public static void handleNavigation(String outcome) {

        ConfigurableNavigationHandler cnh = (ConfigurableNavigationHandler) getFacesContext()
                .getApplication().getNavigationHandler();
        cnh.performNavigation(outcome);
    }

    /**
     * Obtiene el contextpath de la app
     *
     * @return
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * Hace un redirect mediante el ExternalContext de JSF.
     *
     * @param uri
     */
    public static void externalRedirect(String uri) {
        try {

            getFacesContext().responseComplete();
            getFacesContext().getExternalContext().redirect(uri);
        } catch (IOException e) {
            logger(Utileria.class).error(e);
        }
    }

    public static DecimalFormat formatoDinero() {
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        return formateador;
    }

    public static Date fechaFormat2(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date newFecha = null;
        try {
            newFecha = formatoDelTexto.parse(fecha);
        } catch (Exception ex) {
            logger(Utileria.class).error(ex);
        }
        return newFecha;
    }

    public static Date fechaFormat3(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yy HH:MM:SS");
        Date newFecha = null;
        try {
            newFecha = formatoDelTexto.parse(fecha);
        } catch (Exception ex) {
            logger(Utileria.class).error(ex);
        }
        return newFecha;
    }

    public static Date horaFormat(String hora) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm");
        Date newHora = null;
        try {
            newHora = formatoDelTexto.parse(hora);
        } catch (Exception ex) {
            logger(Utileria.class).error(ex);
        }
        return newHora;
    }



    public static String fechaFormat1(Date fecha) {
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return formatDate.format(fecha);
    }

    public static String fechaFormat(Date fecha) {
        SimpleDateFormat formatDate = new SimpleDateFormat("MM'-'yyyy");
        return formatDate.format(fecha);
    }

    public static String dateToString(Date fecha,SimpleDateFormat formatDate)throws
            ParseException {
        return  formatDate.format(fecha);
    }

    public static Date stringToDate(String fecha,SimpleDateFormat formatDate)throws
            ParseException {
        return formatDate.parse(fecha);
    }

    public static String fechaFormat3(Date fecha) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(fecha);
    }

    public static Date fechaActual() {
        return new Date();
    }

    private static List<UrlParameter> parseParam(String parameters) {
        List<UrlParameter> param = new ArrayList<>();
        StringTokenizer paramGroup = new StringTokenizer(parameters, "&");

        while (paramGroup.hasMoreTokens()) {
            StringTokenizer value = new StringTokenizer(paramGroup.nextToken(), "=");
            param.add(new UrlParameter(value.nextToken(), value.nextToken()));

        }
        return param;
    }

    public static List<UrlParameter> obtenerParamtrosDeURL(String url) {
        List<UrlParameter> param = null;

        if (url.contains("?")) {
            String paramaters = url.substring(url.indexOf("?") + 1);
            return param = parseParam(paramaters);
        } else {
            return param;
        }
    }

    public static String getUrlSinParametros(String url) {

        if (url.contains("?")) {
            return url.substring(0, url.lastIndexOf("?"));
        } else {
            return url;
        }
    }

    public static List<Map<Integer, Object>> getInsertResponse(Map out) {
        List<Map<Integer, Object>> r = new ArrayList<>();
        if (out != null) {
            System.out.println("out size :" + out.size());
            Set keySet = out.keySet();
            Iterator it = keySet.iterator();
            String key;
            Object val;

            if (it.hasNext()) {
                key = (String) it.next();
                System.out.println(key);
                val = out.get(key);
                System.out.println(val);
                //List<Map<String,Object>> arrValues=(List)val;
                r = (List<Map<Integer, Object>>) val;
            }
        }
        return r;
    }

    public static Map<Integer, String> paseoVallenato(String cadena) {
        String[] objects = cadena.split("@");
        Map<Integer, String> resultado = new HashMap<>();
        if (cadena.equals("")) {
            return resultado;
        } else {
            for (String element : objects) {
                String[] objects_ = element.split(",");
                resultado.put(Integer.valueOf(objects_[0]), objects_[1]);
            }
            return resultado;
        }
    }

    public static Map<String, String> paseoVallenato2(String cadena) {
        String[] objects = cadena.split("@");
        Map<String, String> resultado = new HashMap<>();
        if (cadena.equals("")) {
            return resultado;
        } else {
            for (String element : objects) {
                String[] objects_ = element.split(",");
                if(element.contains(",")){
                    resultado.put((objects_[0]), objects_[1]);
                }else{
                    resultado.put((objects_[0]), "");
                }

            }
            return resultado;
        }
    }
    public static Map<String, String> paseoVallenato3(String cadena) {
        String[] objects = cadena.split("&");
        Map<String, String> resultado = new HashMap<>();
        if (cadena.equals("")) {
            return resultado;
        } else {
            for (String element : objects) {
                String[] objects_ = element.split("=");
                if(element.contains("=") && objects_.length > 1){
                    resultado.put((objects_[0]), objects_[1]);
                }else{
                    resultado.put((objects_[0]), "");
                }

            }
            return resultado;
        }
    }

    public static String nameColumnUpperCase(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);

    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        //  if (selectOne) {
        //    items[0] = new SelectItem("", "---");
        //  i++;
        // }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static <T> T conDefault(T valor, T def) {
        return valor == null ? def : valor;
    }

    public static String validarStringNull(Object arg) {
        return (arg == null) ? " " : String.valueOf(arg);
    }

    public static Integer validarIntegerNull(Object arg) {
        return (arg == null) ? 0 : Integer.valueOf(arg.toString());
    }

    public static Boolean validarBooleanNull(Object arg) {
        return (arg == null) ? false : Boolean.valueOf(arg.toString());
    }


    public static Long validarLongNull(Object arg) {
        return (arg == null) ? (long) 0 : Long.valueOf(arg.toString());
    }

    public static Float validarFloatNull(Object arg) {
        return (arg == null || arg.equals("")) ? (float) 0.0 : Float.valueOf(arg.toString());
    }

    public static final Object nuevaInstancia(Class clase) {
        Object object = null;
        try {
            Class classDefinition = Class.forName(clase.getName());
            object = classDefinition.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger(clase).error(e);
        }
        return object;

    }

    public static String decodeUrl(String url){
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("URL:\t" + ex.getMessage());
        }
        return url;
    }

    public static String encodeUrl(String url){
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("URL:\t" + ex.getMessage());
        }
        return url;
    }

    /**Funcion que quita espacios en blancos
     *
     */
    public static String withoutWhiteSpace(String cadena) {
        return cadena.replaceAll("^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2")
                .replace("\t"," ");
    }


    public static String getIP() throws NullPointerException{
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger(Utileria.class).error(e);
            return null;
        }
    }

    public static Timestamp getTimeStamp(Object arg){
        return (arg == null) ? null : (Timestamp)arg;
    }
    //Prueba
    public static void main(String[] args) {
      /*  String url = "http://domain.com/page.xhtml?parameter1=value1&parameter2=value2";
        String url2 = "catalogos/regiones.xhtml&ClienteId=0,&RegionesId=0,&UnidadesNegocios=0,&TerritorioId=0,&EquiposId = 0";
        List<UrlParameter> paramValue = null;
        paramValue = obtenerParamtrosDeURL(url);

        // for (Map.Entry<String, String> entry : paramValue.entrySet()) {
        //     System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
        // }
        for (UrlParameter urlParameter : paramValue) {
            System.out.println(urlParameter.getKey() + ":" + urlParameter.getValue());
        }
        System.out.println("URL:\t" + getUrlSinParametros(url));*/


        try {
            out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }




}
