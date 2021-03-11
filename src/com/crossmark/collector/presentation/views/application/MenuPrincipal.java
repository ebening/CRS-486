/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

//import com.crossmark.collector.business.services.ServiceMenu;
import com.crossmark.collector.business.services.ServiceMenu;
import java.io.Serializable;

/**
 *
 * @author christian
 *
 * Si queremos la vista en arbol: 1.-commonMenu.xhtml ; cambiamos por esto
 * <ui:include src="menu/treeMenu.xhtml" />
 * 2.-:commonCenter.xhtml ; cambiamos por esto
 * <ui:include src="#{MBAplicacion.menuPrincipal.treeMenu.navigator.pagina}" />
 *
 * Si queremos la vista en paneles: 1.-commonMenu.xhtml ; cambiamos por esto
 * <ui:include src="menu/panelMenu.xhtml" /-->
 * 2.-:commonCenter.xhtml ; cambiamos por esto
 * <ui:include src="#{MBAplicacion.menuPrincipal.panelMenu.navigator.pagina}" />
 *
 * Descomentamos la intancia que deseemos
 *  PanelMenu o TreeMenu
 *
 */
public class MenuPrincipal implements Serializable {

    private PanelMenu panelMenu;
    private TreeMenu treeMenu;

    public MenuPrincipal(ServiceMenu serviceMenu) {
        panelMenu = new PanelMenu(serviceMenu);
        treeMenu = new TreeMenu(serviceMenu);
    }

    public PanelMenu getPanelMenu() {
        return panelMenu;
                    }


     public TreeMenu getTreeMenu() {
     return treeMenu;
     }

}
