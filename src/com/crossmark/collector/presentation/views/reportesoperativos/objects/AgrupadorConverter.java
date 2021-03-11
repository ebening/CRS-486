package com.crossmark.collector.presentation.views.reportesoperativos.objects;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 */
public class AgrupadorConverter implements Converter {

    private List<Agrupador> agrupadores = new ArrayList<>();

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        Agrupador respuesta = null;
        for (Agrupador agrupador : agrupadores) {
            if (agrupador.getIdInterno().equals(string)) {
                return agrupador;
            }
        }
        return respuesta;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || "".equals(o)) {
            return "";
        } else if (o instanceof Agrupador) {
            Agrupador agrupador = (Agrupador) o;
            return agrupador.getIdInterno();
        }
        return "";
    }

    public List<Agrupador> getAgrupadores() {
        return agrupadores;
    }

    public void setAgrupadores(List<Agrupador> agrupadores) {
        this.agrupadores = agrupadores;
    }

}
