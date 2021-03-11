/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;

import java.io.Serializable;
import java.util.*;

/**
 * @author christian
 */
public class TerritoriosController extends AbstractCatalogs<Territorio> implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
            Utileria.getString("nombre"), Utileria.getString("activo"));

    private ServiceCatalogos serviceCatalogos;

    private String filter;

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    protected void init() {
        setList(serviceCatalogos.getAllActivatedTerritories());
        setSelected(new ArrayList<Territorio>());
        setCurrent(new Territorio());
        setClase(Territorio.class);
        setSeleccionCrear("PF('dialog-territorios-crear').show()");
        setSeleccionEditar("PF('dialog-territorios-editar').show()");
        setSeleccionEliminar("PF('dialog-territorios-eliminar').show()");

        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("CrearTerritorio: " + getCurrent().getNombre());
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedTerritories());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new Territorio());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("EditarTerritorio: " + getCurrent().getNombre());
        try {
            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllActivatedTerritories());
            Utileria.mensajeSatisfactorio(Utileria.getString("update_success"));
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void eliminar() {
        Map<String, String> noEliminados = new HashMap<>();
        List<String> eliminados = new ArrayList<>();
        StringBuilder mensaje = null;
        Utileria.logger(getClass()).info("RemoveTerritorio: " + getCurrent().getNombre());
        try {
            String resultado = null;
            if (getSelected().size() > 1) {
                for (Territorio territorio : getSelected()) {
                    resultado = serviceCatalogos.eliminar(territorio);
                    if (!resultado.equals("0")) {
                        noEliminados.put(territorio.getNombre(), resultado);
                        Utileria.mensajeAlerta(Utileria.getString(resultado));
                    } else {
                        eliminados.add(territorio.getNombre());
                        Utileria.mensajeSatisfactorio(Utileria.getString("remove_success_p", territorio.getNombre()));
                    }

                    if (eliminados.size() == getSelected().size() && noEliminados.size() == 0) {
                        Utileria.mensajeSatisfactorio(Utileria.getString("removes_success"));
                    } else if ((eliminados.size() == 1) && (noEliminados.size() == 1)) {
                        mensaje = new StringBuilder();
                        mensaje.append(Utileria.getString("registros_eliminados"));
                        for (String e : eliminados) {
                            mensaje.append("\t" + e + "\n");
                        }
                        mensaje.append(Utileria.getString("registros_no_eliminados"));
                        for (Map.Entry<String, String> entry : noEliminados.entrySet()) {
                            mensaje.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
                        }
                        Utileria.mensajeAlerta(mensaje.toString());
                    }

                }
            } else if (getSelected().size() == 1) {
                resultado = serviceCatalogos.eliminar(getCurrent());
                if (!resultado.equals("0")) {
                    Utileria.mensajeAlerta(Utileria.getString(resultado));
                } else {
                    Utileria.mensajeSatisfactorio(Utileria.getString("remove_success"));
                }
            }
        } catch (Exception e) {
            Utileria.mensajeErroneo(Utileria.getString("fail_operation"));
            Utileria.logger(getClass()).error(e.getMessage());
            Utileria.logger(getClass()).error(e.getCause());
        }
        getSelected().clear();
        setList(serviceCatalogos.getAllActivatedTerritories());
    }

    public boolean filterText(Object value, Object filter, Locale locale) {

        String filterText = Utileria.validarStringNull(filter).trim();
        String str = Utileria.validarStringNull(value).trim();
        String string = Utileria.validarStringNull(this.filter).trim();
        Utileria.logger(getClass()).info("Chido: "+filterText);
        Utileria.logger(getClass()).info("Contra: "+str);
        Utileria.logger(getClass()).info("Resultado:"+str.equals(filterText));
        Utileria.logger(getClass()).info("Filter:"+this.filter);

        if(filterText == null||filterText.equals("")) {
            return true;
        }

        if(value == null) {
            return false;
        }



        return  str.equals(filterText);
    }


}
