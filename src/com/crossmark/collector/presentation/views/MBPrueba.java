package com.crossmark.collector.presentation.views;

import com.crossmark.collector.business.services.ServiceEjemplo;

public class MBPrueba {

    private Integer perfilId;
    private String perfilName;
    ServiceEjemplo serviceEjemplo;

    public ServiceEjemplo getServiceEjemplo() {
        return serviceEjemplo;
    }

    public void setServiceEjemplo(ServiceEjemplo serviceEjemplo) {
        this.serviceEjemplo = serviceEjemplo;
    }

    public String test() {
        this.perfilName = serviceEjemplo.getPerfil(perfilId);
        return null;
    }

    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    public String getPerfilName() {
        return perfilName;
    }

    public void setPerfilName(String perfilName) {
        this.perfilName = perfilName;
    }

}
