/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceCiudades;
import com.crossmark.collector.business.services.ServiceFormatos;
import com.crossmark.collector.business.services.ServiceProyectosTiendas;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.MBCiudades;
import com.crossmark.collector.presentation.views.visitas.MBGruposTiendas;
import com.crossmark.collector.presentation.views.visitas.MBTiendas;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;
import com.crossmark.collector.presentation.views.visitas.objects.Formato;
import com.crossmark.collector.presentation.views.visitas.objects.GrupoTiendas;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jdominguez
 */
@ManagedBean
public class MBProyectosTiendas {
    
    // **************** Services ***************** //
    private ServiceProyectosTiendas serviceProyectosTiendas;
    private ServiceFormatos serviceFormatos;
    private ServiceCatalogos serviceCatalogos;
    private ServiceCiudades serviceCiudades;
    // **************** Parametros *************** //
    private int unidadesNegociosId;
    private int regionesId;
    private int territorioId;
    private int equipoId;
    private int clienteId;
    private int proyectoId;
    private String nombreProyecto;
    private String query;
    // **************** Filtros Busqueda ********* //
    private int estadosId;
    private int ciudadesId;
    private int formatoId;
    private int cadenasId;
    private int grupoSelected;
    private String nombreTienda;
    private List<Ciudad> ciudades;
    
    // *************** Grupos Tiendas ****************** //
    private String nuevoGrupo;
    private List<Tienda> tiendasGruposAll;
    private List<Tienda> tiendasGruposSelected;
    
    // **************** Otras Variables **************** //
    private List<Tienda> tiendasAll;
    private List<Tienda> tiendaListSearch;
    private List<Tienda> tiendaSearchSelected;
    private List<Tienda> mainTableFiltered;
    private List<GrupoTiendas>grupos;
    private List<Formato> formatos;
    private List<Tienda> tiendaSelected;
    private String nombreCliente;
      
    public void procesaQuery(){
        
        Codificacion cs = new Codificacion();
        
        if(query != null){
            cs.proceso(query.replaceAll("%2", ","),true);
            Map<String,String> urlParameters = cs.obtenerParametros2(cs.getResultado());
            
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
                    this.setUnidadesNegociosId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("ClienteId")){
                    this.setClienteId(Integer.parseInt(element.getValue()));
                }
                
                if(element.getKey().equals("ProyectoId")){
                    this.setProyectoId(Integer.parseInt(element.getValue()));
                }
                
                if(element.getKey().equals("ProyectoN")){
                    this.setNombreProyecto(element.getValue());
                }
                
            }
            
        }
    }
    
    public void initListaTiendas(){
        Cliente cl = getServiceCatalogos().getClienteById(clienteId);
        nombreCliente = cl.getNombre();
        tiendasAll = parseTiendaObject(getServiceProyectosTiendas().listaTiendasProyecto(
                proyectoId, 
                unidadesNegociosId, 
                equipoId, 
                clienteId, 
                null, 
                cadenasId, 
                formatoId, 
                estadosId, 
                ciudadesId));
    }
    
    public void initListaFormatos(){
        List<Map<String, Object>> r = getServiceFormatos().listaFormatos(0);
        formatos = new ArrayList<>();
        for(Map m : r){
            Formato f = new Formato();
            f.setId(Integer.valueOf(String.valueOf(m.get("FormatosId"))));
            f.setNombre(String.valueOf(m.get("Nombre")));
            formatos.add(f);
        }
    }

// *************************** Search Functions *********************************** //
    
    public void openSearchDialog(){
        grupoSelected = 0;
        tiendaListSearch = new ArrayList<>();
        tiendaSearchSelected = new ArrayList<>();
        
        RequestContext.getCurrentInstance().execute("PF('buscarTienda').show()");
    }
    
    public void buscarTiendas(){
        HashMap<String, Object> filtros = MBTiendas.getFiltros();
        filtros.put("UnidadesNegociosId", unidadesNegociosId);
        filtros.put("EquiposId", equipoId);
        filtros.put("ClientesId", clienteId);
        filtros.put("Nombre", nombreTienda);
        filtros.put("CadenasId", cadenasId);
        filtros.put("FormatosId", formatoId);
        filtros.put("EstadosId", estadosId);
        filtros.put("CiudadesId", ciudadesId);
        filtros.put("TerritoriosId", null);
        tiendaListSearch = MBTiendas.parseTiendaObject(getServiceProyectosTiendas().getTiendasList(filtros));
    }
    
    public void ciudadeByEstadoId(){
        if(estadosId == 0){
            ciudades = new ArrayList<>();
        }else{
            ciudades = MBCiudades.parseCiudadObject(getServiceCiudades().getCiudadesByEstado(0, estadosId, null, true));
        }
    }
    
    public void agregarTienda(){
        if(tiendaSearchSelected == null || tiendaSearchSelected.isEmpty()){
            Utileria.mensajeAlerta("Es necesario seleccionar una tienda para agregar");
        }else{
            boolean exito = true;
            String msjFailed = "Las tiendas ";
            for(Tienda t : tiendaSearchSelected){
                HashMap<String, Object> r = getServiceProyectosTiendas().guardaTiendaProyecto(proyectoId, t.getId());
                if(!(boolean)r.get("Exito")){
                    exito = false;
                    msjFailed = msjFailed + t.getNombre() + ", ";
                }else{
                   tiendasAll.add(t); 
                }
            }
            if(exito){
                RequestContext.getCurrentInstance().execute("PF('buscarTienda').hide()");
                Utileria.mensajeSatisfactorio("Las tiendas han sido agregadas al proyecto.");   
            }else{
                Utileria.mensajeAlerta("Error al guardar", msjFailed + " ya existen en el proyecto.");
            }
        }
    }
    
    public void borrarTienda(){
        if(tiendaSelected == null || tiendaSelected.isEmpty()){
            Utileria.mensajeAlerta("Es necesario seleccionar una tienda a eliminar");
        }else{
            RequestContext.getCurrentInstance().execute("PF('confirmDelTienda').show()");
        } 
    }
    
    public void confirmDelete(){
        boolean exito = true;
        String msjFail = "";
        for(Tienda t : tiendaSelected){
            HashMap<String, Object> r = getServiceProyectosTiendas().borraTiendaProyecto(proyectoId, t.getId());
            if((boolean)r.get("Exito")){
                tiendasAll.remove(t);
            }else{
                msjFail = msjFail + "Tienda no eliminada: " + t.getNombre();
                exito = false;
            } 
        }
        if(exito){
            Utileria.mensajeSatisfactorio("Tiendas eliminadas del proyecto");
        }else{
            Utileria.mensajeErroneo(msjFail);
        }
        RequestContext.getCurrentInstance().execute("PF('confirmDelTienda').hide()");
    }
    
// ******************************** Groups Functions ****************************** //

    public void fillTableByGrupo(boolean search){
        if(grupoSelected != 0){
            for(GrupoTiendas gt : grupos){
                if(gt.getId() == grupoSelected){
                    if(gt.getTiendas().isEmpty()){
                        gt.setTiendas(MBTiendas.parseTiendaObject(getServiceProyectosTiendas().listaGruposProyectosTiendas(gt.getId())));
                    }
                    if(search){
                        tiendaListSearch = gt.getTiendas();
                    }else{
                       tiendasGruposSelected = gt.getTiendas(); 
                    }
                    
                    break;
                }
            }  
        }else{
            tiendasGruposSelected = new ArrayList<>();
            tiendaListSearch = new ArrayList<>();
        }
    }
    
    public void listaGruposTiendas(){
        grupos = MBGruposTiendas.parseGrupoObject(getServiceProyectosTiendas().listaGruposProyectos(0, null, clienteId));
    }
    
    public void crearGrupo(){
        if(nuevoGrupo.equals("") || nuevoGrupo == null){
            Utileria.mensajeAlerta("Capture el nombre del grupo.");
        }else{
            HashMap<String, Object> r = getServiceProyectosTiendas().updateGruposProyectos(0, nuevoGrupo, clienteId);
            if((boolean)r.get("Exito")){
                tiendasGruposSelected = new ArrayList<>();
                Utileria.mensajeSatisfactorio("El grupo de tiendas ha sido creado correctamente.");
                GrupoTiendas nuevo = new GrupoTiendas((int)r.get("ID"), nuevoGrupo);
                grupos.add(nuevo);
                grupoSelected = nuevo.getId();
                nuevoGrupo = "";
            }else{
                Utileria.mensajeErroneo(r.get("Msj"));
            }
        }   
    }
    
    public void borrarGrupo(){
        if(grupoSelected != 0){
            HashMap<String, Object> r = getServiceProyectosTiendas().borrarGruposProyectos(grupoSelected);
            if((boolean)r.get("Exito")){
                Utileria.mensajeSatisfactorio("Grupo Eliminado");
            }else{
                Utileria.mensajeErroneo(r.get("Msj"));
            }
        }else{
            Utileria.mensajeAlerta("Debe seleccionar un grupo para eliminar");
        }
    }
    
    public void initTiendasGruposAll(){
        HashMap<String, Object> filtros = MBTiendas.getFiltros();
        tiendasGruposAll = MBTiendas.parseTiendaObject(getServiceProyectosTiendas().getTiendasList(filtros));   
    }
    
    public void guardarEstadoGrupo(){
        if(grupoSelected != 0){
            if(!tiendasGruposSelected.isEmpty()){
                for(GrupoTiendas gt : grupos){
                    if(grupoSelected == gt.getId()){
                        boolean exito = true;
                        String failedMsj = "";
                        List<Tienda> selected = new ArrayList<>(tiendasGruposSelected);
                        List<Tienda> actual = new ArrayList<>(gt.getTiendas());
                        for(Tienda t : gt.getTiendas()){
                            selected.remove(t);
                        }
                        if(!selected.isEmpty()){
                            for(Tienda t : selected){
                                HashMap<String, Object> r = getServiceProyectosTiendas().updateGruposProyectosTiendas(grupoSelected, t.getId());
                                if(!(boolean)r.get("Exito")){
                                    exito = false;
                                    failedMsj = failedMsj + "Tienda no guardada: " + t.getNombre() + "\n" + r.get("Msj") + "\n";
                                }else{
                                    gt.getTiendas().add(t);
                                }
                            }
                        }
                        actual.removeAll(tiendasGruposSelected);
                        if(!actual.isEmpty()){
                            for(Tienda t : actual){
                                HashMap<String, Object> r = getServiceProyectosTiendas().borrarGruposProyectosTiendas(grupoSelected, t.getId());
                                if(!(boolean)r.get("Exito")){
                                    exito = false;
                                    failedMsj = failedMsj + "Tienda no eliminada " + t.getNombre() + "\n" + r.get("Msj") + "\n";
                                }else{
                                    gt.getTiendas().remove(t);
                                }
                            }
                        }
                        tiendasGruposSelected = gt.getTiendas();
                        if(exito){
                            Utileria.mensajeSatisfactorio("El grupo de tiendas ha sido actualizado.");
                        }else{
                            Utileria.mensajeErroneo("Error al actualizar", failedMsj);
                        }
                        break;
                    }
                }
            }
        }else{
            Utileria.mensajeAlerta("Es necesario seleccionar un Grupo");
        }
    }
    
// ******************************************************************************** //
    
    public List<Tienda> parseTiendaObject (List<Map<String, Object>> l){
        List<Tienda> lista = new ArrayList<>();
        
        for(Map m : l){
            Tienda t = new Tienda();
            t.setClave(String.valueOf(String.valueOf(m.get("Clave") == null ? "" : m.get("Clave")) ));
            t.setId(Integer.valueOf(String.valueOf(m.get("TiendasId"))));
            t.setNombre(String.valueOf(m.get("Nombre")));
            t.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId") == null ? 0 : m.get("ProyectosId"))));
            t.setCalle(String.valueOf(m.get("Calle")));
            t.setCiudadesId(Integer.valueOf(String.valueOf(m.get("CiudadesId"))));
            t.setCiudades(String.valueOf(m.get("Ciudades")));
            t.setEstados(String.valueOf(m.get("Estados")));
            t.setEstadosId(Integer.valueOf(String.valueOf(m.get("EstadosId"))));
            t.setCadenasId(Integer.valueOf(String.valueOf(m.get("CadenasId"))));
            t.setCadena(String.valueOf(m.get("Cadenas")));
            t.setFormato(String.valueOf(m.get("Formatos")));
            t.setFormatosId(Integer.valueOf(String.valueOf(m.get("FormatosId"))));
            lista.add(t);
        }
        return lista;
    }
    
// ********************************* Getters & Setters *********************************************** // 
    
    public ServiceProyectosTiendas getServiceProyectosTiendas() {
        return serviceProyectosTiendas;
    }

    public void setServiceProyectosTiendas(ServiceProyectosTiendas serviceProyectosTiendas) {
        this.serviceProyectosTiendas = serviceProyectosTiendas;
    }

    public List<Tienda> getTiendasAll() {
        return tiendasAll;
    }

    public void setTiendasAll(List<Tienda> tiendasAll) {
        this.tiendasAll = tiendasAll;
    }

    public int getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(int unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
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

    public int getCadenasId() {
        return cadenasId;
    }

    public void setCadenasId(int cadenasId) {
        this.cadenasId = cadenasId;
    }

    public int getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(int formatoId) {
        this.formatoId = formatoId;
    }

    public int getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(int estadosId) {
        this.estadosId = estadosId;
    }

    public int getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(int ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

    public int getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(int regionesId) {
        this.regionesId = regionesId;
    }

    public int getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(int territorioId) {
        this.territorioId = territorioId;
    }

    public List<Tienda> getTiendaSelected() {
        return tiendaSelected;
    }

    public void setTiendaSelected(List<Tienda> tiendaSelected) {
        this.tiendaSelected = tiendaSelected;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public int getGrupoSelected() {
        return grupoSelected;
    }

    public void setGrupoSelected(int grupoSelected) {
        this.grupoSelected = grupoSelected;
    }

    public List<GrupoTiendas> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoTiendas> grupos) {
        this.grupos = grupos;
    }

    public ServiceFormatos getServiceFormatos() {
        return serviceFormatos;
    }

    public void setServiceFormatos(ServiceFormatos serviceFormatos) {
        this.serviceFormatos = serviceFormatos;
    }
    
    public List<Tienda> getTiendaListSearch() {
        return tiendaListSearch;
    }

    public void setTiendaListSearch(List<Tienda> tiendaListSearch) {
        this.tiendaListSearch = tiendaListSearch;
    }

    public List<Tienda> getTiendaSearchSelected() {
        return tiendaSearchSelected;
    }

    public void setTiendaSearchSelected(List<Tienda> tiendaSearchSelected) {
        this.tiendaSearchSelected = tiendaSearchSelected;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public List<Formato> getFormatos() {
        return formatos;
    }

    public void setFormatos(List<Formato> formatos) {
        this.formatos = formatos;
    }

    public String getNuevoGrupo() {
        return nuevoGrupo;
    }

    public void setNuevoGrupo(String nuevoGrupo) {
        this.nuevoGrupo = nuevoGrupo;
    }

    public List<Tienda> getTiendasGruposAll() {
        return tiendasGruposAll;
    }

    public void setTiendasGruposAll(List<Tienda> tiendasGruposAll) {
        this.tiendasGruposAll = tiendasGruposAll;
    }

    public List<Tienda> getTiendasGruposSelected() {
        return tiendasGruposSelected;
    }

    public void setTiendasGruposSelected(List<Tienda> tiendasGruposSelected) {
        this.tiendasGruposSelected = tiendasGruposSelected;
    }

    public List<Tienda> getMainTableFiltered() {
        return mainTableFiltered;
    }

    public void setMainTableFiltered(List<Tienda> mainTableFiltered) {
        this.mainTableFiltered = mainTableFiltered;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    
    
}
