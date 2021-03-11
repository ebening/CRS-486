/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.catalogos.seguridad;

import com.crossmark.collector.business.domain.ModulosPerfiles;
import com.crossmark.collector.business.domain.Perfiles;
import com.crossmark.collector.business.services.ServicePerfiles;
import com.crossmark.collector.presentation.views.utils.ColumnModel;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Francisco Mora
 */
@ManagedBean
public class MBPerfiles implements Serializable {
    
    private ServicePerfiles servicePerfiles;
    private Perfiles selectedPerfil = new Perfiles();
    private List<Perfiles> listaPerfiles;
    private List<Perfiles> selectedPerfiles;
    private List<Perfiles> filteredPerfiles;
    
    private List<ModulosPerfiles> listaModulosPerfil;
    private TreeNode treeModulosPerfil;
    private TreeNode[] selectedModulosPerfil;
    private int contador = 0;
    HashMap<String, Integer> modPadresParSel;//Lista de modulos padre seleccionados de forma parcial

    public MBPerfiles() {
        
    }
    
    @PostConstruct
    public void init(){
        listaPerfiles();
        treeModulosPerfil = new DefaultTreeNode("Root", null);
    }
    
    public void listaPerfiles(){
        Utileria.logger(getClass()).info("llego listaPerfiles:");
        //Integer perfilesId, Boolean activo
        listaPerfiles = servicePerfiles.listaPerfiles(null, true);
    }
    
    public void eventEditarPerfil() {
        Utileria.logger(getClass()).info("llego eventEditarPerfil:");
        
        if(selectedPerfil != null && selectedPerfiles != null && !selectedPerfiles.isEmpty() && selectedPerfiles.size() == 1 ){
            RequestContext.getCurrentInstance().execute("PF('editarPerfil').show()");
        } else {
            Utileria.mensajeAlerta(Utileria.getString("fail_selection"));
        }
    }
    
    public void editarPerfil() {
        Utileria.logger(getClass()).info("llego editarPerfil: getPerfilesId:"+selectedPerfil.getPerfilesId()+"    getNombre:"+selectedPerfil.getNombre()+"    getDescripcion:"+selectedPerfil.getDescripcion()+"      isAccesoMovil:"+selectedPerfil.isAccesoMovil());
        try {
            
            servicePerfiles.actualizarPerfil(selectedPerfil);
            Utileria.mensajeSatisfactorio("El perfil "+selectedPerfil.getNombre()+" fue actualizado!");
            selectedPerfil = new Perfiles();
            selectedPerfiles = new ArrayList();
            listaPerfiles();
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    public void eventCrearPerfil() {
        Utileria.logger(getClass()).info("llego eventCrearPerfil:");
        selectedPerfil = new Perfiles();
        selectedPerfiles = new ArrayList();
        RequestContext.getCurrentInstance().execute("PF('crearPerfil').show()");
    }
    
    public void crearPerfil() {
        Utileria.logger(getClass()).info("llego crearPerfil: getPerfilesId:"+selectedPerfil.getPerfilesId()+"    getNombre:"+selectedPerfil.getNombre()+"    getDescripcion:"+selectedPerfil.getDescripcion()+"      isAccesoMovil:"+selectedPerfil.isAccesoMovil());
        try {
            servicePerfiles.crearPerfil(selectedPerfil);
            Utileria.mensajeSatisfactorio("El perfil "+selectedPerfil.getNombre()+" fue dado de alta!");
            selectedPerfil = new Perfiles();
            selectedPerfiles = new ArrayList();
            listaPerfiles();
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    public void eventConfigurarPerfil() {
        //Utileria.logger(getClass()).info("llego eventConfigurarPerfil:"+selectedPerfil.getNombre());
        
        if(selectedPerfil != null && selectedPerfiles != null && !selectedPerfiles.isEmpty() && selectedPerfiles.size() == 1  ){
            
            Utileria.logger(getClass()).info("llego listaModulosPorPerfil getNombre:"+selectedPerfil.getNombre()+"      getPerfilesId:"+selectedPerfil.getPerfilesId());
            treeModulosPerfil = new DefaultTreeNode("Root", null);
            listaModulosPerfil =servicePerfiles.listaModulosPorPerfil(selectedPerfil.getPerfilesId());
            contador = 0;
            if(listaModulosPerfil != null && !listaModulosPerfil.isEmpty()){
                treeModulosPerfil = procesalistModulosPerfil(listaModulosPerfil);
            }
            
            RequestContext.getCurrentInstance().execute("PF('configurarPerfil').show()");
        } else {
            Utileria.mensajeAlerta(Utileria.getString("fail_selection"));
        }
    }
    
    
    //No lo utilizo, solo para verificar lo lo que seleccionaba
    public void verifTreeNode(TreeNode rootNode){
        for (TreeNode childNode : rootNode.getChildren()) {
            if (childNode.getChildCount() > 0) {
                if(childNode.isSelected()){
                    ModulosPerfiles item = (ModulosPerfiles) childNode.getData();
                    Utileria.logger(getClass()).info("Tiene hijos y seleccionado:"+item.getTitulos());
                    childNode.setPartialSelected(true);
                    childNode.getParent().setPartialSelected(true);
                }
                verifTreeNode(childNode);
            }else{
                if(childNode.isSelected()){
                    ModulosPerfiles item = (ModulosPerfiles) childNode.getData();
                    Utileria.logger(getClass()).info("Ultimo seleccionado:"+item.getTitulos());
                    childNode.getParent().setPartialSelected(true);
                }
            }
        }
    }
    
    
    public void configurarPerfil() {
        Utileria.logger(getClass()).info("llego configPerfil:"+selectedPerfil.getPerfilesId());
        
            Utileria.logger(getClass()).info("llego selectedModulosPerfil:"+selectedModulosPerfil.length);
            if(selectedModulosPerfil != null ) {
                servicePerfiles.eliminarModulosPerfiles(selectedPerfil.getPerfilesId());
                
                for(TreeNode node : selectedModulosPerfil) {
                    if(node != null){
                        ModulosPerfiles item = new ModulosPerfiles();
                        item = (ModulosPerfiles) node.getData();
                        insertPerfilModulo(item.getModulosId(),selectedPerfil.getPerfilesId());
                        //servicePerfiles.crearModuloPerfiles(item.getModulosId(),selectedPerfil.getPerfilesId());
                        //padres.put(item.getModulosPadreId().toString(), item.getModulosPadreId());
                        //Utileria.logger(getClass()).info("llego configurarPerfil guardado:"+item.getTitulos()+"    configurarPerfil:"+item.getModulosId());
                    }
                }
                
                //Iterando hashmap que contienen los ids de lospadres, para poder obtener los padres de esos registros e inserarlos.
                modPadresParSel = new HashMap<String, Integer>();
                getIdParentsPartialSelected(treeModulosPerfil);
                if(!modPadresParSel.isEmpty()){
                    Iterator it = modPadresParSel.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry e = (Map.Entry) it.next();
                        if(e.getValue() != null && Integer.parseInt(e.getValue().toString()) != 0){
                            insertPerfilModulo(Integer.parseInt(e.getValue().toString()),selectedPerfil.getPerfilesId());
                        }
                    }
                }
                
                Utileria.mensajeSatisfactorio("El perfil "+selectedPerfil.getNombre()+" fue actualizado!");
            }
            
            treeModulosPerfil = new DefaultTreeNode("Root", null);
            selectedModulosPerfil = new TreeNode[0];
            
            listaPerfiles();
            
        
    }
    
    public void insertPerfilModulo(Integer moduloId, Integer perfilId){
        try {
            servicePerfiles.crearModuloPerfiles(moduloId,perfilId);
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    //Metodo para obtener los ids de los elementos padres
    public void getIdParentsPartialSelected(TreeNode rootNode){
        for (TreeNode childNode : rootNode.getChildren()) {
            if (childNode.getChildCount() > 0) {
                if(childNode.isPartialSelected()){
                    ModulosPerfiles item = (ModulosPerfiles) childNode.getData();
                    
                    if(!modPadresParSel.containsKey(item.getModulosId().toString())){
                        modPadresParSel.put(item.getModulosId().toString(), item.getModulosId());
                        Utileria.logger(getClass()).info("Esta seleccionado de forma parcial:getTitulos"+item.getTitulos()+"       getModulosId:"+item.getModulosId());
                    }
                }
                getIdParentsPartialSelected(childNode);
            }else{
                if(childNode.isPartialSelected()){
                    ModulosPerfiles item = (ModulosPerfiles) childNode.getData();
                    Utileria.logger(getClass()).info("Ultimo seleccionado:"+item.getTitulos());
                    modPadresParSel.put(item.getModulosId().toString(), item.getModulosId());
                    //childNode.getParent().setPartialSelected(true);
                }
            }
        }
    }
    
    public TreeNode procesalistModulosPerfil(List<ModulosPerfiles> lista){
        TreeNode listaOrdenada = new CheckboxTreeNode(new ModulosPerfiles(), null);
        
        if(lista != null && !lista.isEmpty()){
            for(ModulosPerfiles item : lista){
                if(item.getModulosPadreId() == null || item.getModulosPadreId() == 0){
                    Boolean haschild = false;
                    
                    for(ModulosPerfiles item2 : lista){
                        if((item.getModulosId() == item2.getModulosPadreId()) && item.getModulosId() != item.getModulosPadreId()){
                            haschild = true;
                        }
                    }
                    
                    //Utileria.logger(getClass()).info("procesalistModulosPerfil getModulosId:"+item.getModulosId()+"   getTitulos"+item.getTitulos());
                    TreeNode node1 = new CheckboxTreeNode(item.getModulosId().toString(),item, listaOrdenada);
                    if(haschild){
                        node1.setSelected(false);
                        node1.setExpanded(true);
                        procesaListSegundaIteracion(item.getModulosId(),lista, node1);
                    }else{
                        if(item.isSeleccionado() == true && haschild == false){// && haschild == false
                            node1.setSelected(true);
                            //Utileria.logger(getClass()).info("procesalistModulosPerfil Apico selected:"+item.getTitulos()+"    configurarPerfil:"+item.getModulosId());
                        }
                    }
                }
            }
        }
        Utileria.logger(getClass()).info("Saliendo procesalistModulosPerfil:  contador:"+contador);
        return listaOrdenada;
    }
    
    public TreeNode procesaListSegundaIteracion(Integer modulosId, List<ModulosPerfiles> lista, TreeNode nodo){
        //TreeNode nodoHijo = null;
        
        for(ModulosPerfiles item : lista){
            if(modulosId == item.getModulosPadreId()){
                Boolean haschild = false;
                for(ModulosPerfiles item2 : lista){
                    if((item.getModulosId() == item2.getModulosPadreId()) && item.getModulosId() != item.getModulosPadreId()){
                        haschild = true;
                    }
                }
                
                TreeNode nodoHijo = new CheckboxTreeNode(item, nodo);
                if(haschild){
                    nodoHijo.setSelected(false);
                    nodoHijo.setExpanded(true);
                    procesaListTerceraIteracion(item.getModulosId(),lista, nodoHijo);
                }else{
                    if(item.isSeleccionado() == true && haschild == false){
                        nodoHijo.setSelected(true);
                        //Utileria.logger(getClass()).info("procesaListSegundaIteracion Apico selected:"+item.getTitulos()+"    configurarPerfil:"+item.getModulosId());
                    }
                }
            }
        }
        return nodo;
    }
    
    public TreeNode procesaListTerceraIteracion(Integer modulosId, List<ModulosPerfiles> lista, TreeNode nodo){
        //TreeNode nodoHijo = null;
        
        for(ModulosPerfiles item : lista){
            if(modulosId == item.getModulosPadreId()){
                Boolean haschild = false;
                for(ModulosPerfiles item2 : lista){
                    if((item.getModulosId() == item2.getModulosPadreId()) && item.getModulosId() != item.getModulosPadreId()){
                        haschild = true;
                    }
                }
                
                TreeNode nodoHijo = new CheckboxTreeNode(item, nodo);
                //Utileria.logger(getClass()).info("isSeleccionado:"+item.isSeleccionado());
                if(haschild){
                    nodoHijo.setSelected(false);
                    nodoHijo.setExpanded(true);
                    procesaListSegundaIteracion(item.getModulosId(),lista, nodoHijo);
                }else{
                    if(item.isSeleccionado() == true && haschild == false){
                        nodoHijo.setSelected(true);
                        //Utileria.logger(getClass()).info("procesaListTerceraIteracion Apico selected:"+item.getTitulos()+"    configurarPerfil:"+item.getModulosId());
                    }
                }
            }
        }
        return nodo;
    }
    
    
    public void eliminarPerfiles() {
        
        Utileria.logger(getClass()).info("llego eventEliminarPerfiles:");
        FacesContext fc = FacesContext.getCurrentInstance();
        try{
            if(selectedPerfiles != null && !selectedPerfiles.isEmpty() ){
                String listaEliminar = "\n";
                for(Perfiles p : selectedPerfiles){
                    servicePerfiles.eliminarModulosPerfiles(p.getPerfilesId());
                    servicePerfiles.eliminarPerfil(p);
                    listaEliminar += "\n"+p.getNombre();
                }
                Utileria.mensajeAlerta(fc,Utileria.getString("delete_message",listaEliminar));

                selectedPerfil = new Perfiles();
                selectedPerfiles = new ArrayList();
                listaPerfiles();
            }else{
                Utileria.mensajeAlerta(fc,Utileria.getString("noselected_perfil"));
            }
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    public void eliminarPerfil() {
        Utileria.logger(getClass()).info("llego eliminarPerfil:");
        FacesContext fc = FacesContext.getCurrentInstance();
        try{
            if(selectedPerfil != null ){
                servicePerfiles.eliminarModulosPerfiles(selectedPerfil.getPerfilesId());
                servicePerfiles.eliminarPerfil(selectedPerfil);
                Utileria.mensajeAlerta(fc,Utileria.getString("delete_message",selectedPerfil.getNombre()));
                selectedPerfil = new Perfiles();
                selectedPerfiles = new ArrayList();
                listaPerfiles();
            }else{
                Utileria.mensajeAlerta(fc,Utileria.getString("noselected_perfil"));
            }
            
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }
    
    public void seleccionarPerfil(SelectEvent event) {
        seleccionPerfil();
    }
    
    public void quitarSeleccion(UnselectEvent event) {
        seleccionPerfil();
    }
    
    void seleccionPerfil(){
        if(this.selectedPerfiles != null && this.selectedPerfiles.size() > 0){
            setSelectedPerfil(this.selectedPerfiles.get(this.selectedPerfiles.size() - 1));
        }else{
            setSelectedPerfil(new Perfiles());
        }
    }
    
    public ServicePerfiles getServicePerfiles() {
        return servicePerfiles;
    }

    public void setServicePerfiles(ServicePerfiles servicePerfiles) {
        this.servicePerfiles = servicePerfiles;
    }
    
    public List<Perfiles> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfiles> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public List<Perfiles> getFilteredPerfiles() {
        return filteredPerfiles;
    }

    public void setFilteredPerfiles(List<Perfiles> filteredPerfiles) {
        this.filteredPerfiles = filteredPerfiles;
    }

    public Perfiles getSelectedPerfil() {
        return selectedPerfil;
    }

    public void setSelectedPerfil(Perfiles selectedPerfil) {
        this.selectedPerfil = selectedPerfil;
    }

    public List<Perfiles> getSelectedPerfiles() {
        return selectedPerfiles;
    }

    public void setSelectedPerfiles(List<Perfiles> selectedPerfiles) {
        this.selectedPerfiles = selectedPerfiles;
    }
    
    public TreeNode getTreeModulosPerfil() {
        return treeModulosPerfil;
    }

    public void setTreeModulosPerfil(TreeNode treeModulosPerfil) {
        this.treeModulosPerfil = treeModulosPerfil;
    }

    public TreeNode[] getSelectedModulosPerfil() {
        return selectedModulosPerfil;
    }

    public void setSelectedModulosPerfil(TreeNode[] selectedModulosPerfil) {
        this.selectedModulosPerfil = selectedModulosPerfil;
    }

    public List<ModulosPerfiles> getListaModulosPerfil() {
        return listaModulosPerfil;
    }

    public void setListaModulosPerfil(List<ModulosPerfiles> listaModulosPerfil) {
        this.listaModulosPerfil = listaModulosPerfil;
    }

    public HashMap<String, Integer> getModPadresParSel() {
        return modPadresParSel;
    }

    public void setModPadresParSel(HashMap<String, Integer> modPadresParSel) {
        this.modPadresParSel = modPadresParSel;
    }
    
    
}
