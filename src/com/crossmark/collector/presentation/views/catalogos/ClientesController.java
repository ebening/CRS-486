/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import java.util.*;

/**
 * @author Christian
 */
public class ClientesController extends AbstractCatalogs<Cliente> implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
            Utileria.getString("nombre"), Utileria.getString("activo"));
    private ServiceCatalogos serviceCatalogos;
    private List<UnidadesNegocio> listaUnidadesNegocios;
    private List<String> listaUnidadesNegociosSeleccionadas;
    private List<UnidadesNegocio> respaldo;

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public List<UnidadesNegocio> getListaUnidadesNegocios() {
        return listaUnidadesNegocios;
    }

    public void setListaUnidadesNegocios(List<UnidadesNegocio> listaUnidadesNegocios) {
        this.listaUnidadesNegocios = listaUnidadesNegocios;
    }

    public List<String> getListaUnidadesNegociosSeleccionadas() {
        return listaUnidadesNegociosSeleccionadas;
    }

    public void setListaUnidadesNegociosSeleccionadas(List<String> listaUnidadesNegociosSeleccionadas) {
        this.listaUnidadesNegociosSeleccionadas = listaUnidadesNegociosSeleccionadas;
    }


    @Override
    protected void init() {
        setList(serviceCatalogos.getAllActivatedClientes());
        setCurrent(new Cliente());
        setSelected(newArrayList());
        setListaUnidadesNegocios(serviceCatalogos.getAllActivatedUnidadesNegocios());
        setListaUnidadesNegociosSeleccionadas(new ArrayList<String>());

        setClase(Cliente.class);
        setSeleccionCrear("PF('dialog-clientes-crear').show()");
        setSeleccionEditar("PF('dialog-clientes-editar').show()");
        setSeleccionEliminar("PF('dialog-clientes-eliminar').show()");

        //Columnas dinamicas
        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("CrearCliente: " + getCurrent().getNombre());
        try {
            preCrear(false);
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedClientes());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent( new Cliente());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        try {
            preCrear(true);
            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllActivatedClientes());
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
                for (Cliente o : getSelected()) {
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
        setList(serviceCatalogos.getAllActivatedClientes());
    }

    @Override
    public void seleccionar(SelectEvent event) {
        super.seleccionar(event);
        auxEdit();
    }

    @Override
    public void cerrarDialogCrear() {
        listaUnidadesNegociosSeleccionadas.clear();
        super.cerrarDialogCrear();
    }

    @Override
    public void cerrarDialogEditar() {
        listaUnidadesNegociosSeleccionadas.clear();
        super.cerrarDialogEditar();
    }

    @Override
    public void comprobarSeleccionCrear() {
        listaUnidadesNegociosSeleccionadas.clear();
        super.comprobarSeleccionCrear();
    }

    private void preCrear(boolean isUpdate) {
        if(isUpdate){
            getCurrent().getUnidadesNegocio().clear();
        }
        for (String element : listaUnidadesNegociosSeleccionadas) {
            getCurrent().getUnidadesNegocio().add(new UnidadesNegocio(Integer.valueOf(element)));
        }
    }

    private void auxEdit() {
        listaUnidadesNegociosSeleccionadas.clear();
        for (UnidadesNegocio element : getCurrent().getUnidadesNegocio()) {
            listaUnidadesNegociosSeleccionadas.add(element.getIdUnidadNegocio().toString());
        }
    }
}
