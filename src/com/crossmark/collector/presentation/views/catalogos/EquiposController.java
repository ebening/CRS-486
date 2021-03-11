/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christian on 27/11/2014.
 */
public class EquiposController extends AbstractCatalogs<Equipo> implements Serializable {

    private List<Region> listaRegiones;
    private List<String> listaTerrioriosSeleccionadas;
    private List<Territorio> listaTerritorios;
    private List<UnidadesNegocio> listaUnidadesNegocios;
    private ServiceCatalogos serviceCatalogos;


    public List<Region> getListaRegiones() {
        return listaRegiones;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public List<String> getListaTerrioriosSeleccionadas() {
        return listaTerrioriosSeleccionadas;
    }

    public void setListaTerrioriosSeleccionadas(List<String> listaTerrioriosSeleccionadas) {
        this.listaTerrioriosSeleccionadas = listaTerrioriosSeleccionadas;
    }

    public List<Territorio> getListaTerritorios() {
        return listaTerritorios;
    }

    public List<UnidadesNegocio> getListaUnidadesNegocios() {
        return listaUnidadesNegocios;
    }


    @Override
    protected void init() {
        setList(serviceCatalogos.getAllActivatedEquipos());
        listaRegiones = serviceCatalogos.getAllActivatedRegions();
        listaTerritorios = serviceCatalogos.getAllActivatedTerritories();
        listaUnidadesNegocios = serviceCatalogos.getAllActivatedUnidadesNegocios();
        setSelected(newArrayList());
        listaTerrioriosSeleccionadas = new ArrayList<>();
        setCurrent( new Equipo());

        setClase(Equipo.class);
        setSeleccionCrear("PF('dialog-equipos-crear').show()");
        setSeleccionEditar("PF('dialog-equipos-editar').show()");
        setSeleccionEliminar("PF('dialog-equipos-eliminar').show()");
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("Crear Equipo" + getCurrent().getNombre());
        try {
            preCrear(false);
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedEquipos());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent( new Equipo());
        } catch (Exception e) {
            Utileria.mensajeErroneo(Utileria.getString("fail_operation"));
            Utileria.logger(getClass()).error(e.getMessage());
            Utileria.logger(getClass()).error(e.getCause());
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("EditarEquipo: " + getCurrent().getNombre());
        Utileria.logger(getClass()).info("EditarRegion: Tamaño de selectedEquipos" + getSelected().size());
        try {
            preCrear(true);
            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllActivatedEquipos());
            Utileria.mensajeSatisfactorio(Utileria.getString("update_success"));
        } catch (Exception e) {
            Utileria.mensajeErroneo(Utileria.getString("fail_operation"));
            Utileria.logger(getClass()).error(e.getMessage());
            Utileria.logger(getClass()).error(e.getCause());
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
                for (Equipo o : getSelected()) {
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
        setList(serviceCatalogos.getAllActivatedEquipos());
    }

    @Override
    public void seleccionar(SelectEvent event) {
        super.seleccionar(event);
        auxEdit();
    }

    @Override
    public void cerrarDialogCrear() {
        listaTerrioriosSeleccionadas.clear();
        super.cerrarDialogCrear();
    }

    @Override
    public void cerrarDialogEditar() {
        listaTerrioriosSeleccionadas.clear();
        super.cerrarDialogEditar();
    }

    @Override
    public void comprobarSeleccionCrear() {
        listaTerrioriosSeleccionadas.clear();
        super.comprobarSeleccionCrear();
    }

    private void preCrear(boolean isUpdate) {
        if (isUpdate) {
            getCurrent().getListaTerritorios().clear();
        }
        for (String element : listaTerrioriosSeleccionadas) {
            getCurrent().getListaTerritorios().add(new Territorio(Integer.valueOf(element)));
        }
    }

    private void auxEdit() {
        listaTerrioriosSeleccionadas.clear();
        for (Territorio element : getCurrent().getListaTerritorios()) {
            listaTerrioriosSeleccionadas.add(element.getId().toString());
        }
    }



    public void prueba() {
        preCrear(false);
        Utileria.logger(getClass()).info("Nombre Equipo: " + getCurrent().getNombre());
        Utileria.logger(getClass()).info("idRegion de Equipo: " + getCurrent().getRegion().getId());
        Utileria.logger(getClass()).info("idUnidadNegocio de Equipo: " + getCurrent().getUnidadesNegocio().getIdUnidadNegocio());
        Utileria.logger(getClass()).info("Territorios de Equipo: " + getCurrent().getListaTerritorios().size());
        for (Territorio t : getCurrent().getListaTerritorios()) {
            Utileria.logger(getClass()).info("id Territorio: " + t.getId());
        }
    }


    public void prueba2() {
        preCrear(true);
        Utileria.logger(getClass()).info("Nombre Equipo: " + getCurrent().getNombre());
        Utileria.logger(getClass()).info("idRegion: " + getCurrent().getRegion().getId());
        Utileria.logger(getClass()).info("idUnidadNegocio: " + getCurrent().getUnidadesNegocio().getIdUnidadNegocio());
        Utileria.logger(getClass()).info("Territorios de Equipo: " + getCurrent().getListaTerritorios().size());
        for (Territorio t : getCurrent().getListaTerritorios()) {
            Utileria.logger(getClass()).info("id Territorio: " + t.getId());
        }
    }


}
