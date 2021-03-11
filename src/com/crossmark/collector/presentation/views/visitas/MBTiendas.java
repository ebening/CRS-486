package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceCiudades;
import com.crossmark.collector.business.services.ServiceGruposTiendas;
import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.business.services.ServiceTiendas;
import com.crossmark.collector.business.services.ServiceVisitasxTiendas;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Cadena;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;
import com.crossmark.collector.presentation.views.visitas.objects.GrupoTiendas;
import com.crossmark.collector.presentation.views.visitas.objects.LazyTiendasDataModel;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import com.crossmark.collector.presentation.views.visitas.objects.Visita;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jdominguez
 */

@ManagedBean
public class MBTiendas{
    
    private ServiceTiendas serviceTiendas; 
    private ServiceVisitasxTiendas serviceVisitasxTiendas;
    private ServiceGruposTiendas serviceGruposTiendas;
    private ServiceTerritorios serviceTerritorios;
    private ServiceCatalogos serviceCatalogos;
    private ServiceCiudades serviceCiudades;
    
    private int territorioId;
    private int territorioNatId;
    private int tiendaId;
    private int ciudadId;
    private int estadoId;
    private int unidadesNegociosId;
    private int cadenaId;
    private int regionesId;
    private int equipoId;
    private int clienteId;
    
    private String tiendaNombre;
    private String territorioNombre;
    private String equipoNombre;
    private Cadena cadena;
    private String storeName;
    private String urlQuery;
    
    private List<GrupoTiendas> grupos;
    private List<Tienda> mainTiendaSelected;
    private List<Tienda> tiendaListSearch;
    private List<Tienda> selectedTiendas;
    private List<Tienda> tiendasMainTable;
    private List<Tienda> tiendasFiltered;
    private List<Visita> visitas;
    private List<Ciudad> ciudades;
    
    private int grupoSelected;
    
    private LazyTiendasDataModel lazyModel;
    private LazyTiendasDataModel lazyModelSearch;
    
    public MBTiendas(){
        //String t = Utileria.getSessionAttribute("Tiendas");
        //territorioId = Integer.valueOf(t);
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
                        this.setTerritorioId(Integer.parseInt(element.getValue()));
                        break;
                    case "EquiposId":
                        this.setEquipoId(Integer.parseInt(element.getValue()));
                        break;
                    case "UnidadesNegocios":
                        this.setUnidadesNegociosId(Integer.parseInt(element.getValue()));
                        break;
                    case "ClienteId":
                        this.setClienteId(Integer.parseInt(element.getValue()));
                        break;
                    default:
                        this.setRegionesId(0);
                        this.setTerritorioId(0);
                        this.setEquipoId(0);
                        this.setUnidadesNegociosId(0);
                        this.setClienteId(0);
                        break;
                }
            }
        }
    }

    public List<Tienda> tiendaById(int idTienda){
        return parseTiendaObject(getServiceTiendas().getTiendaById(idTienda));
    }
    
    public void listaGruposTiendas(){
        grupos = MBGruposTiendas.parseGrupoObject(getServiceGruposTiendas().listaGrupos(0, null, 0, 0));
    }
    
    public void ciudadeByEstadoId(){
        if(estadoId == 0){
            ciudades = new ArrayList<>();
        }else{
            ciudades = MBCiudades.parseCiudadObject(getServiceCiudades().getCiudadesByEstado(0, estadoId, null, true));
        }
    }
    
    public void fillTableByGrupo(){
        System.out.println("Grupo Selected: -> " + grupoSelected);
        if(grupoSelected != 0){
            for(GrupoTiendas gt : grupos){
                if(gt.getId() == grupoSelected){
                    if(gt.getTiendas().isEmpty()){
                        gt.setTiendas(parseTiendaObject(getServiceGruposTiendas().listaTiendasByGrupoId(gt.getId())));
                    }
                    tiendaListSearch = gt.getTiendas();
                    break;
                }
            }  
        }else{
            tiendaListSearch = new ArrayList<>();
        }
    }
    
    public void tiendasListByTerritorio(){
        HashMap<String, Object> filtros = getFiltros();
        filtros.put("TerritoriosId", territorioId);
        filtros.put("UnidadesNegociosId", unidadesNegociosId);
        filtros.put("EquiposId", equipoId);
        
        territorioNombre = getServiceTerritorios().nombreTerritorio(territorioId);
        
        lazyModel = new LazyTiendasDataModel(serviceTiendas, filtros, serviceTiendas.tiendasNumRegSel(filtros), false);
        
//        tiendasMainTable = parseTiendaObject(getServiceTiendas().getTiendasList(filtros));
        Equipo eq = getServiceCatalogos().getEquipoById(equipoId);
        equipoNombre = eq.getNombre();
    }
    
    public void tiendaNombreForVisitasView(){
        tiendaId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiendaId"));
       // tiendaNombre = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiendaNombreSel");
        storeName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tiendaNombreSel");
        visitas = parseVisitaObject(getServiceVisitasxTiendas().getVisitasxTiendas(tiendaId, equipoId, territorioId));
    }
    
    public void tiendasBusqueda(){
        grupoSelected = 0;
        HashMap<String, Object> filtros = getFiltros();
        filtros.put("Activo", true);
        filtros.put("TerritoriosId", territorioNatId != 0 ? null : territorioId);
        filtros.put("TerritoriosNativoId", territorioNatId == 0 ? null : territorioNatId);
        filtros.put("CadenasId", cadenaId);
        filtros.put("CiudadesId", getCiudadId());
        filtros.put("EstadosId", getEstadoId());
        filtros.put("Nombre", getTiendaNombre());
        filtros.put("UnidadesNegociosId", unidadesNegociosId);
        filtros.put("EquiposId", equipoId);
        lazyModelSearch = new LazyTiendasDataModel(serviceTiendas, filtros, serviceTiendas.tiendasTerritoriosNumRegSel(filtros), true);
        //tiendaListSearch = parseTiendaObject(getServiceTiendas().tiendasListBySearch(filtros));
    }
    
    public void resetTiendaSearchList(){
//        tiendaListSearch = new ArrayList();
        lazyModelSearch = new LazyTiendasDataModel(new ArrayList());
        tiendaNombre = "";
        cadenaId = 0;
        estadoId = 0;
        ciudadId = 0;
    }
    
    public void resetView(){
        this.visitas = new ArrayList<>();
        this.tiendaId = 0;
    }
    
    public void addTiendasToMainTable(){
        HashMap<String, Object> ins;
        boolean exito = true;
        RequestContext.getCurrentInstance().execute("PF('busDialog').hide()");
        for(Tienda t : selectedTiendas){
            ins = getServiceTiendas().insertTienda(t.getId(), territorioId, equipoId);
            if((boolean)ins.get("Exito")){
                //tiendasMainTable.add(t);
                lazyModel.addTiendaDatasource(t);
            }else{  
                exito = false;
            }
        }
        if(exito){
            tiendasListByTerritorio();
            Utileria.mensajeSatisfactorio("Las tiendas han sido agregadas al territorio.");
        }else{
            Utileria.mensajeErroneo("Error al agregar tiendas al territorio");
        }
    }
    
    public void confirmDeleteTienda(){
        RequestContext.getCurrentInstance().execute("PF('confirmDelTienda').hide();");
        boolean exito = true;
        String msjFallo = "";
        for(Tienda t : mainTiendaSelected){
            HashMap<String, Object> r = getServiceTiendas().borrarTienda(t.getId(), territorioId, equipoId);
            if((boolean)r.get("Exito")){
                tiendasMainTable.remove(t);
            }else{
                exito = false;
                msjFallo = msjFallo + "Tienda no eliminada:" + t.getNombre() + "\n";
            } 
        }
        if(exito){
            Utileria.mensajeSatisfactorio("Las tiendas han sido eliminadas del territorio.");
        }else{
            Utileria.mensajeErroneo("Error al eliminar", msjFallo);
        }   
    }
    
    public void delTiendasFromMainTable(){
        if(mainTiendaSelected != null && !mainTiendaSelected.isEmpty()){
            RequestContext.getCurrentInstance().execute("PF('confirmDelTienda').show();");
        }else{
            Utileria.mensajeAlerta("Para eliminar debe seleccionar al menos una tienda.");
        }   
    }
    
    public void postProcessXLS(Object doc){
        
    }

    public static List<Tienda> parseTiendaObject(List<Map<String, Object>> l){
        List<Tienda> lt = new ArrayList<>();
        
        for(Map m : l){
            Tienda td = new Tienda(
                    Integer.valueOf(String.valueOf(m.get("TiendasId") == null ? "0" : m.get("TiendasId"))),
                    String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre")), 
                    String.valueOf(m.get("Clave") == null ? "" : m.get("Clave")),
                    Boolean.valueOf(String.valueOf(m.get("Activo") == null ? "false" : m.get("Activo"))), 
                    Integer.valueOf(String.valueOf(m.get("CadenasId") == null ? "0" : m.get("CadenasId"))), 
                    Integer.valueOf(String.valueOf(m.get("EstadosId") == null ? "0" : m.get("EstadosId"))),
                    Integer.valueOf(String.valueOf(m.get("CiudadesId") == null ? "0" : m.get("CiudadesId"))));
            if(m.get("Pertenece") != null){
                td.setPertenece((0 != (int) m.get("Pertenece")));
            }
            if(m.get("Enabled") != null){
                td.setEnable(0 != (int) m.get("Enabled"));
            }
            td.setEstados(String.valueOf(m.get("Estados") == null ? "" : m.get("Estados")));
            td.setTerritoriosNativoId((int)(m.get("TerritoriosNativoId") == null ? 0 : m.get("TerritoriosNativoId")));
            td.setCadena(m.get("Cadena") == null ? "" : (String)m.get("Cadena"));
            td.setCiudades(m.get("Ciudades") == null ? "" : (String)m.get("Ciudades"));
            td.setFormato(m.get("Formato") == null ? "" : (String)m.get("Formato"));
            td.setFormatosId(m.get("FormatosId") == null ? 0 : (short)m.get("FormatosId"));
            lt.add(td);
        }  
        return lt;
    }
    
    public static List<Visita> parseVisitaObject(List<Map<String, Object>> l){
        List<Visita> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
           for(Map m: l){
                Visita v = new Visita(
                        String.valueOf(m.get("Visita")), 
                        String.valueOf(m.get("Rutas")),
                        String.valueOf(m.get("Proyectos")),
                        String.valueOf(m.get("Encuestas")), 
                        String.valueOf(m.get("UserName")), 
                        null, 
                        null, 
                        Date.valueOf(String.valueOf(m.get("FechaVisita"))));
                v.setPromotor(String.valueOf(m.get("Promotor")));
                list.add(v);
            } 
        }
        return list;
    }
    
    public static HashMap<String, Object> getFiltros(){
        HashMap<String, Object> filtros;
        filtros = new HashMap<>();
        filtros.put("TiendasId", null);
        filtros.put("Nombre", null);
        filtros.put("Activo", null);
        filtros.put("TerritoriosNativoId", null);
        filtros.put("CiudadesId", null);
        filtros.put("FormatosId", null);
        filtros.put("CadenasId", null);
        filtros.put("EstadosId", null);
        filtros.put("TerritoriosId", null);
        filtros.put("UnidadesNegociosId", null);
        filtros.put("EquiposId", null);
        filtros.put("ClientesId", null);
        filtros.put("PageIndex", null);
        filtros.put("PageSize", null);
        filtros.put("TotalCount", null);
        return filtros;
    }
    
// *********************** Getters & Setters ******************************** //
    /**
     * @return the serviceTiendas
     */
    public ServiceTiendas getServiceTiendas() {
        return serviceTiendas;
    }

    /**
     * @param serviceTiendas the serviceTiendas to set
     */
    public void setServiceTiendas(ServiceTiendas serviceTiendas) {
        this.serviceTiendas = serviceTiendas;
    }

    public int getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(int territorioId) {
        this.territorioId = territorioId;
    }

    

    /**
     * @return the tiendaNombre
     */
    public String getTiendaNombre() {
        return tiendaNombre;
    }

    /**
     * @param tiendaNombre the tiendaNombre to set
     */
    public void setTiendaNombre(String tiendaNombre) {
        this.tiendaNombre = tiendaNombre;
    }

    /**
     * @return the cadena
     */
    public Cadena getCadena() {
        return cadena;
    }

    /**
     * @param cadena the cadena to set
     */
    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    /**
     * @return the cadenaId
     */
    public int getCadenaId() {
        return cadenaId;
    }

    /**
     * @param cadenaId the cadenaId to set
     */
    public void setCadenaId(int cadenaId) {
        this.cadenaId = cadenaId;
    }

    /**
     * @return the estadoId
     */
    public int getEstadoId() {
        return estadoId;
    }

    /**
     * @param estadoId the estadoId to set
     */
    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    /**
     * @return the ciudadId
     */
    public int getCiudadId() {
        return ciudadId;
    }

    /**
     * @param ciudadId the ciudadId to set
     */
    public void setCiudadId(int ciudadId) {
        this.ciudadId = ciudadId;
    }

    /**
     * @return the tiendaId
     */
    public int getTiendaId() {
        return tiendaId;
    }

    /**
     * @param tiendaId the tiendaId to set
     */
    public void setTiendaId(int tiendaId) {
        this.tiendaId = tiendaId;
    }
    
    

    /**
     * @return the selectedTiendas
     */
    public List<Tienda> getSelectedTiendas() {
        return selectedTiendas;
    }

    /**
     * @param selectedTiendas the selectedTiendas to set
     */
    public void setSelectedTiendas(List<Tienda> selectedTiendas) {
        this.selectedTiendas = selectedTiendas;
    }

    /**
     * @return the tiendasMainTable
     */
    public List<Tienda> getTiendasMainTable() {
        return tiendasMainTable;
    }

    /**
     * @param tiendasMainTable the tiendasMainTable to set
     */
    public void setTiendasMainTable(List<Tienda> tiendasMainTable) {
        this.tiendasMainTable = tiendasMainTable;
    }

    /**
     * @return the serviceVisitasxTiendas
     */
    public ServiceVisitasxTiendas getServiceVisitasxTiendas() {
        return serviceVisitasxTiendas;
    }

    /**
     * @param serviceVisitasxTiendas the serviceVisitasxTiendas to set
     */
    public void setServiceVisitasxTiendas(ServiceVisitasxTiendas serviceVisitasxTiendas) {
        this.serviceVisitasxTiendas = serviceVisitasxTiendas;
    }

    /**
     * @return the visitas
     */
    public List<Visita> getVisitas() {
        return visitas;
    }

    /**
     * @param visitas the visitas to set
     */
    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }

    public List<Tienda> getTiendaListSearch() {
        return tiendaListSearch;
    }

    public void setTiendaListSearch(List<Tienda> tiendaListSearch) {
        this.tiendaListSearch = tiendaListSearch;
    }

    public int getTerritorioNatId() {
        return territorioNatId;
    }

    public void setTerritorioNatId(int territorioNatId) {
        this.territorioNatId = territorioNatId;
    }

    public ServiceGruposTiendas getServiceGruposTiendas() {
        return serviceGruposTiendas;
    }

    public void setServiceGruposTiendas(ServiceGruposTiendas serviceGruposTiendas) {
        this.serviceGruposTiendas = serviceGruposTiendas;
    }

    public List<GrupoTiendas> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoTiendas> grupos) {
        this.grupos = grupos;
    }

    public int getGrupoSelected() {
        return grupoSelected;
    }

    public void setGrupoSelected(int grupoSelected) {
        this.grupoSelected = grupoSelected;
    }

    public int getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(int unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public int getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(int regionesId) {
        this.regionesId = regionesId;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public ServiceTerritorios getServiceTerritorios() {
        return serviceTerritorios;
    }

    public void setServiceTerritorios(ServiceTerritorios serviceTerritorios) {
        this.serviceTerritorios = serviceTerritorios;
    }

    public String getTerritorioNombre() {
        return territorioNombre;
    }

    public void setTerritorioNombre(String territorioNombre) {
        this.territorioNombre = territorioNombre;
    }

    public List<Tienda> getTiendasFiltered() {
        return tiendasFiltered;
    }

    public void setTiendasFiltered(List<Tienda> tiendasFiltered) {
        this.tiendasFiltered = tiendasFiltered;
    }

    public List<Tienda> getMainTiendaSelected() {
        return mainTiendaSelected;
    }

    public void setMainTiendaSelected(List<Tienda> mainTiendaSelected) {
        this.mainTiendaSelected = mainTiendaSelected;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LazyTiendasDataModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyTiendasDataModel lazyModel) {
        this.lazyModel = lazyModel;
    }

    public LazyTiendasDataModel getLazyModelSearch() {
        return lazyModelSearch;
    }

    public void setLazyModelSearch(LazyTiendasDataModel lazyModelSearch) {
        this.lazyModelSearch = lazyModelSearch;
    }

    public String getUrlQuery() {
        return urlQuery;
    }

    public void setUrlQuery(String urlQuery) {
        this.urlQuery = urlQuery;
    }
    
    
}
