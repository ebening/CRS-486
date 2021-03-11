package com.crossmark.collector.presentation.views.application;

import com.crossmark.collector.business.services.ServiceMenu;

import javax.annotation.PostConstruct;
import java.io.Serializable;

public class MBAplicacion implements Serializable {

    private Login login;
    private MenuPrincipal menuPrincipal;
    private ServiceMenu serviceMenu;

    @PostConstruct
    public void initMBAplicacion() {
        login = new Login();
        menuPrincipal = new MenuPrincipal(serviceMenu);
    }

    public Login getLogin() {
        return login;
    }

    public MenuPrincipal getMenuPrincipal() {
        return menuPrincipal;
    }

    public ServiceMenu getServiceMenu() {
        return serviceMenu;
    }

    public void setServiceMenu(ServiceMenu serviceMenu) {
        this.serviceMenu = serviceMenu;
    }

}
