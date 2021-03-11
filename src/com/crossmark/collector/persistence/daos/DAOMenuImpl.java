/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.application.MenuElement;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static  com.crossmark.collector.presentation.views.utils.Utileria.logger;

/**
 * @author christian
 */
public class DAOMenuImpl implements DAOMenu, Serializable {

    private DatabaseStoredProc spMenu;

    /*
     *  
     * Metodo para traer los elementos que formaran el menu la aplicacion
     * 
     * @param usuariosOID
     * @return
     */
    @Override
    public List<MenuElement> getMenuElemets() {
        List<MenuElement> listado = new ArrayList<>();
        MenuElement menuElement;
        Map<String, Object> inputs = new TreeMap<>();
        UsuarioSession usSistema = Utileria.getSessionAttribute("userLoged");
        logger(getClass()).info("Equipo:"+usSistema.getEquipoId());
        inputs.put("UsuariosOID", usSistema.getOID());
        //inputs.put("UsuariosOID", "6529A115-3B4C-444B-83B2-0F94E5DC9FE3");
        Map out = getSpMenu().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            Integer idPadre = null;
            Integer orden = null;
            String url;
            for (Object object : list) {
                Map element = (Map) object;
                menuElement = new MenuElement();

                idPadre = (element.get("ModulosPadreId") == null || element.get("ModulosPadreId").toString() == null) ? 0 : Integer.valueOf(element.get("ModulosPadreId").toString());
                orden = (element.get("Orden") == null || element.get("Orden").toString() == null) ? 0 : Integer.valueOf(element.get("Orden").toString());
                url = ((element.get("Url") == null) || (element.get("Url").toString() == null)) ? "" : element.get("Url").toString();

                menuElement.setModulosId(Integer.valueOf(element.get("ModulosId").toString()));
                menuElement.setModulosPadreId(idPadre);
                menuElement.setNombre(element.get("Nombre").toString());
                menuElement.setOrden(orden);
                menuElement.setUrl(url);
               // menuElement.setEsNavDirecta(Boolean.valueOf(element.get("esNavDirecta").toString()));
                listado.add(menuElement);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    public DatabaseStoredProc getSpMenu() {
        return spMenu;
    }

    public void setSpMenu(DatabaseStoredProc spMenu) {
        this.spMenu = spMenu;
    }

}
