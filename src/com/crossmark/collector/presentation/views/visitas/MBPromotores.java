/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceCiudades;
import com.crossmark.collector.business.services.ServiceEncuestas;
import com.crossmark.collector.business.services.ServicePromotores;
import com.crossmark.collector.business.services.ServicePromotoresBus;
import com.crossmark.collector.business.services.ServiceProyectos;
import com.crossmark.collector.business.services.ServiceSecciones;
import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.EncuestasProyecto;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;
import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import com.crossmark.collector.presentation.views.visitas.objects.Proyecto;
import com.crossmark.collector.presentation.views.visitas.objects.ProyectoViewer;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jdominguez
 */
@ManagedBean
public class MBPromotores{
    
/* *************** Atributos Busqueda ******************* */    
    private String nombre = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    private String nroEmpleado = "";
    private int ciudadesId;
    private int estadosId;
    private final int perfilesId = 2;
    private final byte pertenece = 0;
    
/* ****************************************************** */    
    private ServicePromotores servicePromotores;
    private ServicePromotoresBus servicePromotoresBus;
    private ServiceTerritorios serviceTerritorios;
    private ServiceCatalogos serviceCatalogos;
    private ServiceCiudades serviceCiudades;
    private ServiceProyectos serviceProyectos;
    private ServiceEncuestas serviceEncuestas;
    private ServiceSecciones serviceSecciones;
    
/* ****************************************************** */
    private int unidadesNegociosId;
    private int equiposId;
    private int territorioNativoId;
    private int territoriosId;
    private int clienteId;
    private int regionesId;
    
    private List<Promotor> promotores;
    private List<Promotor> promotoresToDelete;
    private List<Promotor> busPromotores;
    private List<Promotor> busPromotoresSelection;
    private List<Proyecto> proyectosByPromotores;
    private List<Ciudad> ciudades;
    
    private Promotor pSelected;
    private String nombreTerritorio;
    private String nombrePromo;
    private String nombreEquipo;
    private String urlQuery;
    
    public MBPromotores(){

    }
    
    public void processUrl(){
        Codificacion cs = new Codificacion();
        
        if(urlQuery != null){
            cs.proceso(urlQuery, true);
            Map<String,String> urlParameters = cs.obtenerParametros(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                switch(element.getKey()){
                    case "RegionesId":
                        this.setRegionesId(Integer.parseInt(element.getValue()));
                        break;
                    case "TerritorioId":
                        this.setTerritoriosId(Integer.parseInt(element.getValue()));
                        break;
                    case "EquiposId":
                        this.setEquiposId(Integer.parseInt(element.getValue()));
                        break;
                    case "UnidadesNegocios":
                        this.setUnidadesNegociosId(Integer.parseInt(element.getValue()));
                        break;
                    case "ClienteId":
                        this.setClienteId(Integer.parseInt(element.getValue()));
                        break;
                    default:
                        this.setRegionesId(0);
                        this.setTerritoriosId(0);
                        this.setEquiposId(0);
                        this.setUnidadesNegociosId(0);
                        this.setClienteId(0);
                        break;
                }
            }
        }
    }
    
    public void buscarPromotores(){
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("UsuariosOID", null);
        inputs.put("UserName", null);
        inputs.put("Nombre", nombre.equals("") ? null : nombre);
        inputs.put("NroEmpleado", nroEmpleado.equals("") ? null : nroEmpleado);
        inputs.put("ApellidoMaterno", apellidoMaterno.equals("") ? null : apellidoMaterno);
        inputs.put("ApellidoPaterno", apellidoPaterno.equals("") ? null : apellidoPaterno);
        inputs.put("CiudadesId", ciudadesId == 0 ? null : ciudadesId);
        inputs.put("TerritorioNativoId", territorioNativoId == 0 ? null : territorioNativoId);
        inputs.put("PerfilesId", perfilesId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("EstadosId", estadosId);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
     //   inputs.put("Pertenece", pertenece);
        setBusPromotores(parsePromotorObject(getServicePromotoresBus().buscarPromotores(inputs)));
    }
    
    public void isSelectable(){
        for(Promotor p : busPromotoresSelection){
            if(p.isUnselect()){
                Utileria.mensajeAlerta("El promotor pertenece a otro territorio");
                busPromotoresSelection.remove(p);
                RequestContext.getCurrentInstance().update("center-form:tableBusquedaProm");
                break;
            }
        }
    }
    
    public void addPromotoresToMainTable(){
        String msj = "Los promotores han sido agregados al territorio";
        for(Promotor x : busPromotoresSelection){
            HashMap<String, Object> r = guardaPromotor(x);
            if((boolean)r.get("Exito")){
                promotores.add(x);
            }else{
                msj = "Error al guardar promotor\n" + x.getNombre() + " " + x.getApellidoPaterno();
            }
        }
        resetBusqueda();
        RequestContext.getCurrentInstance().execute("PF('busPromotor').hide()");
        Utileria.mensajeSatisfactorio(msj);
    }
    
    public HashMap<String, Object> guardaPromotor(Promotor p){
        return getServicePromotoresBus().guardarPromotoresTerritorios(p.getOID(), territoriosId, equiposId);
    }
    
    public void deletePromotores(){
        
        if(promotoresToDelete != null && !promotoresToDelete.isEmpty()){
           RequestContext.getCurrentInstance().execute("PF('confirmDelete').show()");
        }else{
            Utileria.mensajeAlerta("Para eliminar debe seleccionar al menos un proveedor.");
        }
    }
    
    public void confirmDelete(){
        int r = 0;
        RequestContext.getCurrentInstance().execute("PF('confirmDelete').hide()");
        for(Promotor p : promotoresToDelete){
            Iterator<Promotor> iter = promotores.iterator();
            for(; iter.hasNext();){
                Promotor x = iter.next();
                if(p.getOID().equals(x.getOID())){
                    iter.remove();
                }
            }
            r = getServicePromotores().deletePromotorTerritorio(p.getOID(), equiposId);
        }
        Utileria.mensajeSatisfactorio("Los promotores han sido eliminados del territorio.");
    }
    
    public void recuperarPromotoresByIdTerritorio(){
        nombreTerritorio = getServiceTerritorios().nombreTerritorio(territoriosId);
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("UsuariosOID", null);
        inputs.put("UserName", null);
        inputs.put("Nombre", null);
        inputs.put("ApellidoMaterno", null);
        inputs.put("ApellidoPaterno", null);
      /*  inputs.put("Calle", null);
        inputs.put("EntreCalle", null);
        inputs.put("Numero", null); */
        inputs.put("Direccion", null);
        inputs.put("Colonia", null);
        inputs.put("CP", null);
        inputs.put("CiudadesId", null);
        inputs.put("EstadosId", null);
        inputs.put("Activo", null);
     //   inputs.put("Password", null); 
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("TerritorioNativoId", territorioNativoId);
        inputs.put("NroEmpleado", null);
        inputs.put("PerfilesId", null);
        inputs.put("UnidadesNegociosId", unidadesNegociosId);
        inputs.put("EquiposId", equiposId);
        inputs.put("Pertenece", null);
        
        setPromotores(parsePromotorObject(getServicePromotores().getPromotoresByTerritorioId(inputs)));
        Equipo eq = getServiceCatalogos().getEquipoById(equiposId);
        nombreEquipo = eq.getNombre();
    }
    
    public void ciudadeByEstadoId(){
        if(estadosId == 0){
            ciudades = new ArrayList<>();
        }else{
            ciudades = MBCiudades.parseCiudadObject(getServiceCiudades().getCiudadesByEstado(0, estadosId, null, true));
        }
    }
    
    public void showProjectDialog(){
        nombrePromo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("promoName");
        nombrePromo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("promoNro") + " - " + nombrePromo;
        nombrePromo = nombrePromo + " " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("promoAp");
        String oid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("promoOID");
        proyectosByPromotores = parseProyectoObject(getServicePromotores().listaProyectosByPromotor(
                oid, territoriosId, unidadesNegociosId, equiposId, null, null));
    }
    
    public void resetProjectDialog(){
        proyectosByPromotores = new ArrayList<>();
        nombre = "";
    }
    
    public void proyectoPDF(int pyId){
        List<Proyectos> pys = getServiceProyectos().listaProyectos(pyId, null, null, null, null, null, null, null, null, true);
        if(pys.isEmpty()){
            Utileria.mensajeAlerta("No fue posible recuperar informacion del proyecto");
        }else{
            Proyectos pr = pys.get(0);
            List<EncuestasProyecto> encuestas = getServicePromotores().getListaEncuestasProyecto(unidadesNegociosId, pyId);
            try {
                List<Encuestas> temp = new ArrayList<>();
                for(EncuestasProyecto ep : encuestas){
                    Encuestas ec = getServiceEncuestas().traerEncuesta(ep.getEncuestasId());
                    temp.add(ec);
                }
                pr.setListaEncuestasProyecto(temp);
                Cliente cl = getServiceCatalogos().getClienteById(pr.getClienteId());
                pr.setNombreCliente(cl.getNombre());
                ProyectoViewer pv = new ProyectoViewer();
                pv.createPreview(pr);
            } catch (JRException | FileNotFoundException ex) {
                Logger.getLogger(MBPromotores.class.getName()).log(Level.SEVERE, null, ex);
                Utileria.mensajeErroneo(ex);
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(MBPromotores.class.getName()).log(Level.SEVERE, null, ex);
                Utileria.mensajeErroneo(ex);
            }
        }
        
    }
    
    public static List<Proyecto> parseProyectoObject(List<Map<String, Object>> l){
        List<Proyecto> pr = new ArrayList<>();
        SimpleDateFormat sfd1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        if(l != null && !l.isEmpty()){
            for(Map m : l){
                Proyecto p = new Proyecto(String.valueOf(m.get("Clave")), String.valueOf(m.get("Nombre")));
                p.setTienda(String.valueOf(m.get("Tienda")));
                p.setId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                if(m.get("VisitaProgramadas") != null){
                    p.setVisitaProgramada(String.valueOf(m.get("VisitaProgramadas")));
                }
                try {
                    Date datefi = sfd1.parse(String.valueOf(m.get("FechaIni")));
                    Date datefin = sfd1.parse(String.valueOf(m.get("FechaFin")));
                    String datefist = sfd.format(datefi);
                    String datefinst = sfd.format(datefin);
                    p.setFechaInicio(sfd.parse(datefist));
                    p.setFechaFin(sfd.parse(datefinst));
                } catch (ParseException ex) {
                    Logger.getLogger(MBPromotores.class.getName()).log(Level.SEVERE, null, ex);
                }
                pr.add(p);
            }
        }
        return pr;
    }
    
    public static List<Promotor> parsePromotorObject(List<Map<String, Object>> l){
        List<Promotor> pr = new ArrayList<>();
        if(l != null && !l.isEmpty()){
           for(Map m : l){
                Promotor p = new Promotor(String.valueOf(m.get("Nombre")), 
                                          String.valueOf(m.get("UsuariosOID")),
                                          String.valueOf(m.get("ApellidoPaterno")),
                                          String.valueOf(m.get("ApellidoMaterno")));
                p.setCiudad(String.valueOf(m.get("NombreCiudades")));
                p.setEstado(String.valueOf(m.get("NombreEstados")));
                p.setNroEmpleado(String.valueOf(m.get("NroEmpleado")));
                
                if(m.get("Enabled") != null){
                    p.setUnselect(0 == (int) m.get("Enabled"));
                }else{
                    p.setUnselect(true);
                }
                
                if(m.get("Pertenece") != null){
                    p.setPertenece(0 != (int) m.get("Pertenece"));
                }
                
                if(m.get("RutaAsignada") != null){
                    p.setRuta(String.valueOf(m.get("RutaAsignada")));
                }
                
                if(m.get("EquipoAsignado") != null){
                    p.setEquipo(String.valueOf(m.get("EquipoAsignado")));
                }
                
                pr.add(p);
            } 
        }
        return pr;
    }

    public void resetBusqueda(){
        busPromotores = new ArrayList<>();
        nombre = "";
        apellidoPaterno = "";
        apellidoMaterno = "";
        nroEmpleado = "";
        ciudadesId = 0;
        territorioNativoId = 0;
        estadosId = 0;
    }

// ******************************* Getters & Setters *************************** //
    public ServicePromotores getServicePromotores() {
        return servicePromotores;
    }

    /**
     * @param servicePromotores the servicePromotores to set
     */
    public void setServicePromotores(ServicePromotores servicePromotores) {
        this.servicePromotores = servicePromotores;
    }

    /**
     * @return the promotores
     */
    public List<Promotor> getPromotores() {
        return promotores;
    }

    /**
     * @param promotores the promotores to set
     */
    public void setPromotores(List<Promotor> promotores) {
        this.promotores = promotores;
    }

    /**
     * @return the busPromotores
     */
    public List<Promotor> getBusPromotores() {
        return busPromotores;
    }

    /**
     * @param busPromotores the busPromotores to set
     */
    public void setBusPromotores(List<Promotor> busPromotores) {
        this.busPromotores = busPromotores;
    }

    /**
     * @return the servicePromotoresBus
     */
    public ServicePromotoresBus getServicePromotoresBus() {
        return servicePromotoresBus;
    }

    /**
     * @param servicePromotoresBus the servicePromotoresBus to set
     */
    public void setServicePromotoresBus(ServicePromotoresBus servicePromotoresBus) {
        this.servicePromotoresBus = servicePromotoresBus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(int ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

    public int getTerritorioNativoId() {
        return territorioNativoId;
    }

    public void setTerritorioNativoId(int territorioNativoId) {
        this.territorioNativoId = territorioNativoId;
    }

    public int getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(int territoriosId) {
        this.territoriosId = territoriosId;
    }

    public int getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(int estadosId) {
        this.estadosId = estadosId;
    }

    public int getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(int unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public int getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(int equiposId) {
        this.equiposId = equiposId;
    }

    public String getNroEmpleado() {
        return nroEmpleado;
    }

    public void setNroEmpleado(String nroEmpleado) {
        this.nroEmpleado = nroEmpleado;
    }

    /**
     * @return the promotoresToDelete
     */
    public List<Promotor> getPromotoresToDelete() {
        return promotoresToDelete;
    }

    /**
     * @param promotoresToDelete the promotoresToDelete to set
     */
    public void setPromotoresToDelete(List<Promotor> promotoresToDelete) {
        this.promotoresToDelete = promotoresToDelete;
    }

    /**
     * @return the busPromotoresSelection
     */
    public List<Promotor> getBusPromotoresSelection() {
        return busPromotoresSelection;
    }

    /**
     * @param busPromotoresSelection the busPromotoresSelection to set
     */
    public void setBusPromotoresSelection(List<Promotor> busPromotoresSelection) {
        this.busPromotoresSelection = busPromotoresSelection;
    }

    public Promotor getpSelected() {
        return pSelected;
    }

    public void setpSelected(Promotor pSelected) {
        this.pSelected = pSelected;
    }

    public List<Proyecto> getProyectosByPromotores() {
        return proyectosByPromotores;
    }

    public void setProyectosByPromotores(List<Proyecto> proyectosByPromotores) {
        this.proyectosByPromotores = proyectosByPromotores;
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

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(int regionesId) {
        this.regionesId = regionesId;
    }

    public String getNombrePromo() {
        return nombrePromo;
    }

    public void setNombrePromo(String nombrePromo) {
        this.nombrePromo = nombrePromo;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public ServiceCiudades getServiceCiudades() {
        return serviceCiudades;
    }

    public void setServiceCiudades(ServiceCiudades serviceCiudades) {
        this.serviceCiudades = serviceCiudades;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

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

    public ServiceSecciones getServiceSecciones() {
        return serviceSecciones;
    }

    public void setServiceSecciones(ServiceSecciones serviceSecciones) {
        this.serviceSecciones = serviceSecciones;
    }

    public String getUrlQuery() {
        return urlQuery;
    }

    public void setUrlQuery(String urlQuery) {
        this.urlQuery = urlQuery;
    }
    
    
}
