package com.crossmark.collector.presentation.views.utils;

import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.extensions.model.fluidgrid.FluidGridItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by christian on 22/12/2014.
 */
public class SeccionParametro implements Serializable, Comparable<SeccionParametro> {
    private int id;
    private String nombre;
    private DynaFormModel parametros;

    public SeccionParametro() {
        this.parametros = new DynaFormModel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DynaFormModel getParametros() {
        return parametros;
    }

    public void setParametros(DynaFormModel parametros) {
        this.parametros = parametros;
    }

    @Override
    public int compareTo(SeccionParametro t) {
        if (t.getId() == getId()) {
            return 0;
        } else {
            return Integer.compare(getId(), t.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SeccionParametro) {
            SeccionParametro menu = (SeccionParametro) o;
            return menu == this || menu.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
