/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

import com.crossmark.collector.business.services.ServiceMenu;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author christian
 */
public class TreeMenu implements Serializable {

    private TreeNode root;
    private MenuElement raiz;
    private Navigator navigator;
    private TreeNode selectedNode;


    public TreeMenu(ServiceMenu serviceMenu) {
        init(serviceMenu.getMenuElemets());
    }

    public TreeNode getRoot() {
        return root;
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    private void init(List<MenuElement> lista) {
        this.raiz = new MenuElement(0, -1, 0, "raiz", "");
        this.root = new DefaultTreeNode("Root", null);

        lista = new ArrayList<>(lista);
        lista.add(this.raiz);
        for (MenuElement item : lista) {
            for (MenuElement padre : lista) {
                if (item.getModulosPadreId().intValue() == padre.getModulosId().intValue()) {
                    padre.getSubmenu().add(item);
                    break;
                }
            }
        }

        initMenu();
    }

    private void initMenu() {
        if (this.raiz.isHasChildren()) {
            for (MenuElement menu1 : this.raiz.getSubmenu()) {
                armarMenu(menu1, root);
            }
        }

    }


    /**
     * Metodo  que sirve para armar el menu.
     */
    private void armarMenu(MenuElement elemento, TreeNode nodo) {
        TreeNode ele = new DefaultTreeNode(elemento, nodo);
        if (elemento.isHasChildren()) {
            for (MenuElement item : elemento.getSubmenu()) {
                armarMenu(item, ele);
            }
        }
    }

    /**
     * Metodo  que sirve para activar un evento en el que un nodo del
     * menu es seleccionado, refresca la parte del centro de la pagina
     * principal.
     */
    public void onNodeSelectV1(NodeSelectEvent event) {
        MenuElement elemento = (MenuElement) selectedNode.getData();
        navigator = new Navigator();
        if (elemento == null || elemento.getUrl().equals("")) {
            if (selectedNode.isSelected() && selectedNode.isExpanded()) {
                selectedNode.setExpanded(false);
                selectedNode.setSelected(false);
                RequestContext.getCurrentInstance().update("menu-form:treemenu");
            } else {
                selectedNode.setExpanded(true);
                selectedNode.setSelected(false);
                RequestContext.getCurrentInstance().update("menu-form:treemenu");
            }
        } else {

            Utileria.mensajeSatisfactorio(Utileria.getUrlSinParametros(elemento.getUrl()));
            navigator.setPagina(Utileria.getUrlSinParametros(elemento.getUrl()));
        }
    }

    /**
     * Metodo  que sirve para activar un evento en el que un nodo del
     * menu es seleccionado, cambia de pagina segun la opcion del menu
     * seleccionada.
     */
    public void onNodeSelectV2(NodeSelectEvent event) {
        MenuElement elemento = (MenuElement) selectedNode.getData();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        navigator = new Navigator();
        if (elemento == null || elemento.getUrl().equals("")) {
            if (elemento == null || elemento.getUrl().equals("")) {
                if (selectedNode.isSelected() && selectedNode.isExpanded()) {
                    selectedNode.setExpanded(false);
                    selectedNode.setSelected(false);
                    RequestContext.getCurrentInstance().update("menu-form:treemenu");
                } else {
                    selectedNode.setExpanded(true);
                    selectedNode.setSelected(false);
                    RequestContext.getCurrentInstance().update("menu-form:treemenu");
                }
            }
        } else {
            try {


                Codificacion cs = new Codificacion();
                cs.proceso(cs.cadenaParametros(elemento.getUrl()), false);

                //   Codificacion cs = new Codificacion();
                //   cs.proceso(cs.cadenaParametros(elemento.getUrl()),true);


                StringBuilder url = new StringBuilder();
                url.append(ec.getRequestContextPath()).append(Utileria.getUrlSinParametros(elemento.getUrl()))
                        .append("?query=").append(cs.getResultado());



/*
                System.out.println(url.toString());
                List<UrlParameter> urlnueva = Utileria.obtenerParamtrosDeURL(url.toString());
                System.out.println(urlnueva.get(0).getKey());
                System.out.println(urlnueva.get(0).getValue());
                cs.proceso(urlnueva.get(0).getValue(),true);


                System.out.println(cs.getResultado());
                Map<String,String> urlParameters = cs.obtenerParametros(cs.getResultado());
                for(Map.Entry<String,String> element : urlParameters.entrySet()){
                    System.out.println(element.getKey());
                    System.out.println(element.getValue());
                }
                */
                ec.redirect(url.toString());
            } catch (Exception e) {
                Utileria.logger(getClass()).info(e);
            }
        }
    }

    /*
     <p:ajax event="expand" update=":center-form:mensajes"
     listener="#{MBAplicacion.menuPrincipal.onNodeExpand}" />
     <p:ajax event="collapse"  update=":center-form:mensajes"
     listener="#{MBAplicacion.menuPrincipal.onNodeCollapse}" />
     <p:ajax event="unselect"  update=":center-form:mensajes"
     listener="#{MBAplicacion.menuPrincipal.onNodeUnselect}" />
     public void onNodeExpand(NodeExpandEvent event) {
     Utileria.logger(getClass()).info(event.getTreeNode().toString());
     Utileria.mensajeSatisfactorio(event.getTreeNode().toString());
     }
     public void onNodeCollapse(NodeCollapseEvent event) {
     Utileria.logger(getClass()).info(event.getTreeNode().toString());
     Utileria.mensajeSatisfactorio(event.getTreeNode().toString());
     }
     public void onNodeUnselect(NodeUnselectEvent event) {
     Utileria.logger(getClass()).info(event.getTreeNode().toString());
     Utileria.mensajeSatisfactorio(event.getTreeNode().toString());
     }
     */

}
