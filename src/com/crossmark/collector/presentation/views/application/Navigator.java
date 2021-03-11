/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

import com.crossmark.collector.presentation.views.utils.UrlParameter;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author christian
 */
public class Navigator implements Serializable {

    private List<UrlParameter> parametros;
    private String pagina;

    public Navigator() {
        parametros = new ArrayList<>();
    }

    public List<UrlParameter> getParametros() {
        return parametros;
    }

    public void setParametros(List<UrlParameter> parametros) {
        this.parametros = parametros;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

}
