/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.*;

/**
 * @author christian
 */
public class RegionesController extends AbstractCatalogs<Region> implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
           Utileria.getString("nombre"), Utileria.getString("activo"));

    private ServiceCatalogos serviceCatalogos;
    private List<Usuario> listaCoordinadores;


    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public List<Usuario> getListaCoordinadores() {
        return listaCoordinadores;
    }

    @Override
    protected void init() {
        super.setList(serviceCatalogos.getAllActivatedRegions());
        super.setCurrent(new Region());
        listaCoordinadores = serviceCatalogos.getAllUsuariosCoordinador();
        super.setSelected(newArrayList());

        setClase(Region.class);
        setSeleccionCrear("PF('dialog-regiones-crear').show()");
        setSeleccionEditar("PF('dialog-regiones-editar').show()");
        setSeleccionEliminar("PF('dialog-regiones-eliminar').show()");

        //Columnas dinamicas
        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("CrearRegion: " + super.getCurrent().getNombre());
        Utileria.logger(getClass()).info("CrearRegion: " + super.getCurrent().getUserCoordinador().getUsuariosOID());
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedRegions());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new Region());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("EditarRegion: Nombre Region" + super.getCurrent().getNombre());
        Utileria.logger(getClass()).info("EditarRegion: Tamaño de selectedRegiones" + super.getSelected().size());
        try {
            serviceCatalogos.editar(super.getCurrent());
            super.setList(serviceCatalogos.getAllActivatedRegions());
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
        Utileria.logger(getClass()).info("Eliminar: " + getCurrent().getNombre());
        try {
            String resultado = null;
            if (getSelected().size() > 1) {
                for (Region o : getSelected()) {
                    resultado = serviceCatalogos.eliminar(o);
                    if (!resultado.equals("0")) {
                        noEliminados.put(o.getNombre(), resultado);
                        Utileria.mensajeAlerta(Utileria.getString(resultado));
                    } else {
                        eliminados.add(o.getNombre());
                        Utileria.mensajeSatisfactorio(Utileria.getString("remove_success_p", o.getNombre()));
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
        setList(serviceCatalogos.getAllActivatedRegions());
    }

    public String nombreCompleto(Usuario o){
        return o.getNombre()+ " " +o.getApellidoPaterno() + " "+ o.getApellidoMaterno();

    }

}
