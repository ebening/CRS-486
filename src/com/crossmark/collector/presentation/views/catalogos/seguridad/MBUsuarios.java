/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.catalogos.seguridad;

import com.crossmark.collector.business.domain.EquiposUsuarios;
import com.crossmark.collector.business.domain.Perfiles;
import com.crossmark.collector.business.domain.Puestos;
import com.crossmark.collector.business.domain.TerritoriosUsuariosIn;
import com.crossmark.collector.business.services.ServiceCiudades;
import com.crossmark.collector.business.services.ServiceEstados;
import com.crossmark.collector.business.services.ServicePerfiles;
import com.crossmark.collector.business.services.ServicePuestos;
import com.crossmark.collector.business.services.ServiceUsuarios;
import com.crossmark.collector.presentation.views.reportescliente.objects.TerritoriosUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.MBCiudades;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;
import com.crossmark.collector.presentation.views.visitas.objects.Estado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Francisco Mora
 */
@ManagedBean
public class MBUsuarios implements Serializable {
    
    private ServiceUsuarios serviceUsuarios;
    private ServiceEstados serviceEstados;
    private ServiceCiudades serviceCiudades;
    private ServicePuestos servicePuestos;
    private ServicePerfiles servicePerfiles;
    
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
    private List<Usuario> usuariosSeleccionados;
    private List<Usuario> usuariosFiltrados;
    
    private List<Estado> listEstados;
    private List<Ciudad> listCiudades;
    private List<Puestos> listPuestos;
    private List<Perfiles> listPerfiles;
    private List<Region> listRegiones;
    
    private List<EquiposUsuarios> listEquipos;
    private List<String> listEsquiposSeleccionados;
    private List<TerritoriosUsuariosIn> listTerritorios;
    private List<String> listTerritoriosSeleccionados;
    //private List<TerritoriosUsuarios> listUsuariosTerritorios;
    
// ******************* Variables JDA para seleccion de Equipos y Territorios ******** //
    
    private EquiposUsuarios equipoSelected;
    private EquiposUsuarios equipoAnterior;
    private List<TerritoriosUsuarios> terrSelected;
    private HashMap<EquiposUsuarios, List<TerritoriosUsuarios>> equipoTerritorios;
    private List<TerritoriosUsuarios> territoriosAllByEquipo;
    private String query;
    //PArametros por get
    Integer unidadesNegocios;
    Integer territorioId;
    Integer equiposId;
    Integer regionesId;
    Integer clienteId;
    
    public MBUsuarios() {
        
    }

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
                    this.setEquiposId(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("UnidadesNegocios")){
                    this.setUnidadesNegocios(Integer.parseInt(element.getValue()));
                }
                if(element.getKey().equals("ClienteId")){
                    this.setClienteId(Integer.parseInt(element.getValue()));
                }
            }
        }
    }


    public void loadRefresh(){
        setListaUsuarios(new ArrayList());
        setUsuarioSeleccionado(new Usuario());
        setUsuariosSeleccionados(new ArrayList());
        setUsuariosFiltrados(new ArrayList());
        regionesList();
        listUsuarios();
    }
    
    
    public void listUsuarios(){
        Utileria.logger(getClass()).info("llego listaUsuarios:");
        /*String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String calle,
            String entreCalle, String numero, String colonia, String cP, String nroEmpleado, String password, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo*/
        setListaUsuarios(serviceUsuarios.getListUsuarios( null, null, null,
            null, null,null, null, null,null, null,
            null, territorioId == 0 ? null : territorioId, null, null, 
            unidadesNegocios == 0 ? null : unidadesNegocios, 
            equiposId == 0 ? null : equiposId,
            null, null));
    }
    
    public void eventEditarUsuario() {
        
        if(usuarioSeleccionado != null && usuariosSeleccionados != null && !usuariosSeleccionados.isEmpty() && usuariosSeleccionados.size() == 1 ){
            this.equipoTerritorios = new HashMap<>();
            this.terrSelected = new ArrayList<>();
            this.territoriosAllByEquipo = new ArrayList<>();
            this.listEsquiposSeleccionados = new ArrayList();
            this.listTerritoriosSeleccionados = new ArrayList();
            perfilesList();
            regionesList();
            equiposList();
           // territoriosList();
            RequestContext.getCurrentInstance().execute("PF('editarUsuario').show()");
            
        } else {
            Utileria.mensajeAlerta(Utileria.getString("fail_selection_edit_usuario"));
        }
    }
    
    public void changeRegion(){
        equiposList();
    }
    
    public void changeEquipo(){
        
        if(equipoTerritorios == null){
            equipoTerritorios = new HashMap<>();
        }   
        if(equipoTerritorios.containsKey(equipoSelected) && equipoTerritorios.get(equipoSelected).size() > 0){
            updateTerritoriosByEquipo();
            territoriosAllByEquipo = equipoTerritorios.get(equipoSelected);
            territoriosPertenece();
        }else if(equipoAnterior != null){
            updateTerritoriosByEquipo();
            territoriosList();
        }else{
            territoriosList();
        }
        try {
            equipoAnterior = (EquiposUsuarios) equipoSelected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().update("center-form:tableTerr");
    }
    
    public void guardarSelectedTerr(){
        for(TerritoriosUsuarios tu : terrSelected){
            tu.setPertenece(1);
        }
        equipoTerritorios.put(equipoSelected, terrSelected);
    }
    
    private void updateTerritoriosByEquipo(){
        List<TerritoriosUsuarios> temp;
        List<TerritoriosUsuarios> tempSelected = new ArrayList<>(terrSelected);
        for(Map.Entry<EquiposUsuarios, List<TerritoriosUsuarios>> e : equipoTerritorios.entrySet()){
            if(Objects.equals(e.getKey().getEquiposId(), equipoAnterior == null ? 0 : equipoAnterior.getEquiposId())){
                temp = e.getValue();
                boolean pert;
                int x = 0;
                for(TerritoriosUsuarios tu : temp){
                    pert = false;
                    if(terrSelected == null){
                        terrSelected = new ArrayList<>();
                    }
                    for(TerritoriosUsuarios tusel : tempSelected){
                        if(tusel.getTerritoriosId() == tu.getTerritoriosId()){
                            pert = true;
                            break;
                        }
                    }
                    temp.get(x++).setPertenece(pert ? 1: 0);
                }
                e.setValue(temp);
                terrSelected = new ArrayList<>();
            }
            if(Objects.equals(e.getKey().getEquiposId(), equipoSelected.getEquiposId())){
                territoriosAllByEquipo = e.getValue();
                territoriosPertenece();
            }
        }
    }
    
    public void editarUsuario() {
        Utileria.logger(getClass()).info("llego editarPerfil: getUsuariosOID:"+usuarioSeleccionado.getUsuariosOID()+"    getNombre:"+usuarioSeleccionado.getNombre()
                +"    getApellidoPaterno:"+usuarioSeleccionado.getApellidoPaterno()+"      getUserName:"+usuarioSeleccionado.getUserName()+"      isActivo:"+usuarioSeleccionado.isActivo());
        
        try {
            serviceUsuarios.actualizarUsuario(usuarioSeleccionado);
            
            if(equipoTerritorios != null && !equipoTerritorios.isEmpty()){
                
                for(Map.Entry<EquiposUsuarios, List<TerritoriosUsuarios>> e : equipoTerritorios.entrySet()){
                    serviceUsuarios.eliminarEquiposPorUsuario(usuarioSeleccionado.getUsuariosOID(), e.getKey().getEquiposId());
                    if(!e.getValue().isEmpty()){
                        for(TerritoriosUsuarios tu : e.getValue()){
                            if(tu.getPertenece() == 1){
                                serviceUsuarios.insertarUsuarioTerritorio(usuarioSeleccionado.getUsuariosOID(), tu.getTerritoriosId(), e.getKey().getEquiposId());
                            }   
                        } 
                    }
                }
            }
            
            loadRefresh();
            equipoSelected = null;
            Utileria.mensajeSatisfactorio(Utileria.getString("update_usuario"));
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
        
    }
    
    public void eventEliminarUsuarios() {
        
        FacesContext fc = FacesContext.getCurrentInstance();
        try{
            //if(usuarioSeleccionado != null && usuariosSeleccionados != null && !usuariosSeleccionados.isEmpty() && usuariosSeleccionados.size() == 1 ){
            if(usuariosSeleccionados != null && !usuariosSeleccionados.isEmpty() ){
                String listaEliminar = "\n";
                for(Usuario item : usuariosSeleccionados){
                    //servicePerfiles.eliminarModulosPerfiles(p.getPerfilesId());
                    serviceUsuarios.eliminarUsuario(item.getUsuariosOID());
                    listaEliminar += "\n"+item.getNombre();
                }
                Utileria.mensajeSatisfactorio(Utileria.getString("delete_message_usuario"));
                loadRefresh();
            }else{
                Utileria.mensajeAlerta(fc,Utileria.getString("fail_selection_del_usuario"));
            }
            loadRefresh();
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    
    //Metodos para llenar las listas
    //LLena lista de estados
    public void estadosList(){
        List<Map<String, Object>> l = serviceEstados.getEstados();
        listEstados = new ArrayList<>();
        if(l != null){
            for(Map m : l){
                listEstados.add(new Estado(Integer.valueOf(String.valueOf(m.get("EstadosId"))), String.valueOf(m.get("Nombre")),
                        String.valueOf(m.get("Clave")), Boolean.valueOf(String.valueOf(m.get("Activo")))));
            }
        }
    }
    
    //Llena lista de ciudades
    public void ciudadeByEstadoId(){
        if(usuarioSeleccionado != null && usuarioSeleccionado.getEstadosId() > 0){
            listCiudades = MBCiudades.parseCiudadObject(getServiceCiudades().getCiudadesByEstado(0, usuarioSeleccionado.getEstadosId(), null, true));
        }else{
            listCiudades = new ArrayList<>();
        }
    }
    
    //Lista de puestos
    public void puestosList(){
        listPuestos = servicePuestos.getListpuestos(null);
    }
    
    //Lista de perfiles
    public void perfilesList(){
        listPerfiles = servicePerfiles.listaPerfiles(null, true);
    }
    
    //Lista de Equipos
    public void equiposList(){
        listEsquiposSeleccionados = new ArrayList();
        //String usuariosOID,Integer equiposId,Integer territoriosId,byte pertenece
        listEquipos = serviceUsuarios.getEquiposByUserFind(usuarioSeleccionado.getUsuariosOID(), usuarioSeleccionado.getEquiposId(), null);
        
        //
        if(listEquipos != null && !this.listEquipos.isEmpty()){
            for(EquiposUsuarios item : listEquipos){
                if(item.getPertenece() == 1){
                    //this.listEsquiposSeleccionados.add(String.valueOf(item.getEquiposId()));
                    if(equipoTerritorios == null){
                        equipoTerritorios = new HashMap<>();
                    }
                    equipoTerritorios.put(item, territoriosList(item));
                }
            }
        }
    }
    
    //Lista de territorios
    public void territoriosList(){
        listTerritoriosSeleccionados = new ArrayList();
        territoriosAllByEquipo = serviceUsuarios.getTerritoriosByEquipoId(usuarioSeleccionado.getUsuariosOID(), equipoSelected.getEquiposId());
        equipoTerritorios.put(equipoSelected, territoriosAllByEquipo); 
        territoriosPertenece(); 
    }
    
    private List<TerritoriosUsuarios> territoriosList(EquiposUsuarios equipo){
        return serviceUsuarios.getTerritoriosByEquipoId(usuarioSeleccionado.getUsuariosOID(), equipo.getEquiposId());
    }
    
    public void territoriosPertenece(){
        if(territoriosAllByEquipo != null && !this.territoriosAllByEquipo.isEmpty()){
            terrSelected = new ArrayList<>();
            for(TerritoriosUsuarios item : territoriosAllByEquipo){
                if(item.getPertenece() == 1){
                    terrSelected.add(item);
                }
            }
        }
    }
    
    public void regionesList(){
        listRegiones = serviceUsuarios.getRegionesByFind(regionesId);
    }
    
    public void eventSeleccionarUsuario(SelectEvent event) {
        seleccionUsuario();
    }
    
    public void eventQuitarSeleccion(UnselectEvent event) {
        seleccionUsuario();
    }
    
    void seleccionUsuario(){
        listEquipos = new ArrayList<>();
        if(this.usuariosSeleccionados != null && this.usuariosSeleccionados.size() > 0){
            setUsuarioSeleccionado(this.usuariosSeleccionados.get(this.usuariosSeleccionados.size() - 1));
        }else{
            setUsuarioSeleccionado(new Usuario());
        }
    }
    
// ****************************** Getters & Setters ****************************//

    public ServiceUsuarios getServiceUsuarios() {
        return serviceUsuarios;
    }

    public void setServiceUsuarios(ServiceUsuarios serviceUsuarios) {
        this.serviceUsuarios = serviceUsuarios;
    }

    public ServiceEstados getServiceEstados() {
        return serviceEstados;
    }

    public void setServiceEstados(ServiceEstados serviceEstados) {
        this.serviceEstados = serviceEstados;
    }

    public ServiceCiudades getServiceCiudades() {
        return serviceCiudades;
    }

    public void setServiceCiudades(ServiceCiudades serviceCiudades) {
        this.serviceCiudades = serviceCiudades;
    }

    public ServicePuestos getServicePuestos() {
        return servicePuestos;
    }

    public void setServicePuestos(ServicePuestos servicePuestos) {
        this.servicePuestos = servicePuestos;
    }

    public ServicePerfiles getServicePerfiles() {
        return servicePerfiles;
    }

    public void setServicePerfiles(ServicePerfiles servicePerfiles) {
        this.servicePerfiles = servicePerfiles;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Usuario> getUsuariosSeleccionados() {
        return usuariosSeleccionados;
    }

    public void setUsuariosSeleccionados(List<Usuario> usuariosSeleccionados) {
        this.usuariosSeleccionados = usuariosSeleccionados;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public List<Estado> getListEstados() {
        return listEstados;
    }

    public void setListEstados(List<Estado> listEstados) {
        this.listEstados = listEstados;
    }

    public List<Ciudad> getListCiudades() {
        return listCiudades;
    }

    public void setListCiudades(List<Ciudad> listCiudades) {
        this.listCiudades = listCiudades;
    }

    public List<Puestos> getListPuestos() {
        return listPuestos;
    }

    public void setListPuestos(List<Puestos> listPuestos) {
        this.listPuestos = listPuestos;
    }

    public List<Perfiles> getListPerfiles() {
        return listPerfiles;
    }

    public void setListPerfiles(List<Perfiles> listPerfiles) {
        this.listPerfiles = listPerfiles;
    }

    public List<EquiposUsuarios> getListEquipos() {
        return listEquipos;
    }

    public void setListEquipos(List<EquiposUsuarios> listEquipos) {
        this.listEquipos = listEquipos;
    }

    public Integer getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(Integer territorioId) {
        this.territorioId = territorioId;
    }

    public Integer getEquiposId() {
        return equiposId;
    }

    public void setEquiposId(Integer equiposId) {
        this.equiposId = equiposId;
    }

    public Integer getUnidadesNegocios() {
        return unidadesNegocios;
    }

    public void setUnidadesNegocios(Integer unidadesNegocios) {
        this.unidadesNegocios = unidadesNegocios;
    }

    public Integer getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(Integer regionesId) {
        this.regionesId = regionesId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<String> getListTerritoriosSeleccionados() {
        return listTerritoriosSeleccionados;
    }

    public void setListTerritoriosSeleccionados(List<String> listTerritoriosSeleccionados) {
        this.listTerritoriosSeleccionados = listTerritoriosSeleccionados;
    }

    public List<String> getListEsquiposSeleccionados() {
        return listEsquiposSeleccionados;
    }

    public void setListEsquiposSeleccionados(List<String> listEsquiposSeleccionados) {
        this.listEsquiposSeleccionados = listEsquiposSeleccionados;
    }

    public List<Region> getListRegiones() {
        return listRegiones;
    }

    public void setListRegiones(List<Region> listRegiones) {
        this.listRegiones = listRegiones;
    }

    public List<TerritoriosUsuariosIn> getListTerritorios() {
        return listTerritorios;
    }

    public void setListTerritorios(List<TerritoriosUsuariosIn> listTerritorios) {
        this.listTerritorios = listTerritorios;
    }

    public EquiposUsuarios getEquipoSelected() {
        return equipoSelected;
    }

    public void setEquipoSelected(EquiposUsuarios equipoSelected) {
        this.equipoSelected = equipoSelected;
    }

    public List<TerritoriosUsuarios> getTerritoriosAllByEquipo() {
        return territoriosAllByEquipo;
    }

    public void setTerritoriosAllByEquipo(List<TerritoriosUsuarios> territoriosAllByEquipo) {
        this.territoriosAllByEquipo = territoriosAllByEquipo;
    }

    public List<TerritoriosUsuarios> getTerrSelected() {
        return terrSelected;
    }

    public void setTerrSelected(List<TerritoriosUsuarios> terrSelected) {
        this.terrSelected = terrSelected;
    }



    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
