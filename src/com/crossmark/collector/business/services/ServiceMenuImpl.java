/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOMenu;
import com.crossmark.collector.presentation.views.application.MenuElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author christian
 */
public class ServiceMenuImpl implements ServiceMenu,Serializable {

    private DAOMenu daoMenu;

    public void setDaoMenu(DAOMenu daoMenu) {
        this.daoMenu = daoMenu;
    }

    public DAOMenu getDaoMenu() {
        return daoMenu;
    }

    @Override
    public List<MenuElement> getMenuElemets() {
        return getDaoMenu().getMenuElemets();
    }

}
