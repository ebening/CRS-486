/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

import com.crossmark.collector.business.services.ServiceMenu;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author christian
 */
public class PanelMenu implements Serializable {

    private List<MenuElement> listaAuxiliar;
    private MenuModel model;
    private MenuElement raiz;
    private Navigator navigator;

    public PanelMenu(ServiceMenu serviceMenu) {
        // imprimirElementos(serviceMenu.getMenuElemets());
        init(serviceMenu.getMenuElemets());
    }

    public MenuModel getModel() {
        return model;
    }


    /**
     * Este metodo inicializa el menu:
     * 1.-armarMenuV1: Cada item envia a una pagina nueva dependiendo el item
     * 2.-armarMenuV2: Cada item refresca el centro de la pagina alfitrion dependiendo el item
     */
    private void initMenu() {
        if (this.raiz.isHasChildren()) {
            for (MenuElement menu1 : this.raiz.getSubmenu()) {
                armarMenuV1(menu1, model);
                //armarMenuV2(menu1, model);
            }
        }
    }

    private void init(List<MenuElement> lista) {
        this.navigator = new Navigator();
        this.raiz = new MenuElement(0, -1, 0, "raiz", "");
        this.model = new DefaultMenuModel();
        lista = new ArrayList<>(lista);
        Collections.sort(lista);
        lista.add(this.raiz);
        listaAuxiliar = new ArrayList<>(lista);
        Collections.sort(listaAuxiliar);
        for (MenuElement item : lista) {
            for (MenuElement padre : lista) {
                if (item.getModulosPadreId().intValue() == padre.getModulosId().intValue()) {
                    if (padre.getSubmenu() == null) {
                        padre.setSubmenu(new ArrayList<MenuElement>());
                    }
                    padre.getSubmenu().add(item);

                }
            }
        }
        initMenu();
    }


/**Este metodo sirve para armar el menu de manera que al hacer click en los hijos
 * el menu nos redireccione a una nueva pagina con todo el template implementado.
 */

    private void armarMenuV1(MenuElement elemento, Object nodo) {

        if (nodo instanceof DefaultMenuModel) {
            //  DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
            //  model.addElement(subMenu);
            if (elemento.isHasChildren()) {
                DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
                model.addElement(subMenu);
                for (MenuElement elem : elemento.getSubmenu()) {
                    armarMenuV1(elem, subMenu);
                }
            }
        } else if (nodo instanceof DefaultSubMenu) {
            if (elemento.isHasChildren()) {
                DefaultSubMenu subMenuPadre = (DefaultSubMenu) nodo;
                DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
                subMenuPadre.addElement(subMenu);
                for (MenuElement elem : elemento.getSubmenu()) {
                    armarMenuV1(elem, subMenu);
                }
            } else {
                DefaultSubMenu subMenuPadre = (DefaultSubMenu) nodo;
                // DefaultMenuItem item = item(elemento);
                DefaultMenuItem item = null;
                if (elemento.getUrl() == null || elemento.getUrl().equals("")) {
                    item = new DefaultMenuItem(elemento, null, "/pages/Blank.xhtml");

                } else {
                    item = new DefaultMenuItem(elemento, null, elemento.getUrl());
                }

                subMenuPadre.addElement(item);
            }
        }
    }


    /**Este metodo sirve para armar el menu de manera que al hacer click en los hijos
     * el menu nos refresque la parte del centro de la pagina Host.
     */

    private void armarMenuV2(MenuElement elemento, Object nodo) {

        if (nodo instanceof DefaultMenuModel) {
            //  DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
            //  model.addElement(subMenu);
            if (elemento.isHasChildren()) {
                DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
                model.addElement(subMenu);
                for (MenuElement elem : elemento.getSubmenu()) {
                    armarMenuV2(elem, subMenu);
                }
            }
        } else if (nodo instanceof DefaultSubMenu) {
            if (elemento.isHasChildren()) {
                DefaultSubMenu subMenuPadre = (DefaultSubMenu) nodo;
                DefaultSubMenu subMenu = new DefaultSubMenu(elemento.getNombre());
                subMenuPadre.addElement(subMenu);
                for (MenuElement elem : elemento.getSubmenu()) {
                    armarMenuV2(elem, subMenu);
                }
            } else {
                DefaultSubMenu subMenuPadre = (DefaultSubMenu) nodo;
                DefaultMenuItem item = new DefaultMenuItem(elemento, null, null);
                item.setCommand("#{MBAplicacion.menuPrincipal.panelMenu.iniNavigator('" + elemento.getModulosId() + "')}");
                item.setUpdate(":center-form");
                Utileria.logger(getClass()).info(navigator.getPagina());
                subMenuPadre.addElement(item);
            }
        }

    }


    /**Este metodo sirve para armar cada elemento para que ejecute un metodo
     * complemente del metodo armarMenuV2.
     *
     */

    private DefaultMenuItem item(MenuElement elemento) {
        if (elemento.isEsNavDirecta()) {
            DefaultMenuItem item = new DefaultMenuItem(elemento, null, Utileria.getUrlSinParametros(elemento.getUrl()));
            Utileria.logger(getClass()).info(Utileria.getUrlSinParametros(elemento.getUrl()) + "  ---  " + elemento.getUrl());
            return item;
        } else {
            DefaultMenuItem item = new DefaultMenuItem(elemento, null, null);
            item.setCommand("#{MBAplicacion.menuPrincipal.panelMenu.iniNavigator('" + elemento.getModulosId() + "')}");
            item.setUpdate(":center-form");
            return item;
        }
    }


    /**Este metodo sirve como auxiliar al metodo DefaultMenuItem el cual es el metodo
     * que ejecuta cada elemento hijo del menu.
     */

    public void iniNavigator(String id) {
        MenuElement menuElement = null;
        Integer id_ = Integer.valueOf(id);
        for (MenuElement item : listaAuxiliar) {
            if (item.getModulosId() == id_) {
                menuElement = item;
                Utileria.logger(getClass()).info("Si encontró la opcion");
                break;
            }
        }

        if (menuElement.getUrl() == null || menuElement.getUrl().equals("")) {
            navigator.setPagina("../Blank.xhtml");
            Utileria.mensajeSatisfactorio("URL nulo");
        } else {
            Utileria.logger(getClass()).info("Nombre:" + menuElement.getNombre());
            Utileria.logger(getClass()).info("URL:" + menuElement.getUrl());

            //Utileria.mensajeSatisfactorio(Utileria.getUrlSinParametros(menuElement.getNombre()));
            navigator.setPagina(Utileria.getUrlSinParametros(menuElement.getUrl()));
            // navigator.setParametros(Utileria.obtenerParamtrosDeURL(menuElement.getUrl()));
            // Utileria.setSessionAttribute("attr_" + menuElement.getNombre(), navigator.getParametros());
        }

    }

    /**Este get nos sirve para llevar el control de las pagina que se seleccionó
     * en el menu.
     */

    public Navigator getNavigator() {
        return navigator;
    }

    private void imprimirElementos(List<MenuElement> listaAuxiliar) {
        for (MenuElement menuElement : listaAuxiliar) {
            Utileria.logger(getClass()).info("-----------------------------------------------");
            Utileria.logger(getClass()).info("Id: " + menuElement.getModulosId());
            Utileria.logger(getClass()).info("Nombre: " + menuElement.getNombre());
            Utileria.logger(getClass()).info("Id Padre: " + menuElement.getModulosPadreId());
        }
        Utileria.logger(getClass()).info("-----------------------------------------------");
    }

}
