package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.Motivo;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.*;

/**
 * Created by christian on 15/12/2014.
 */
public class MotivosController extends AbstractCatalogs<Motivo> implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
            Utileria.getString("nombre"), Utileria.getString("activo"));

    private ServiceCatalogos serviceCatalogos;

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    @Override
    protected void init() {
        setList(serviceCatalogos.getAllActivatedMotivos());
        setCurrent(new Motivo());
        setSelected(newArrayList());

        setClase(Motivo.class);
        setSeleccionCrear("PF('dialog-motivos-crear').show()");
        setSeleccionEditar("PF('dialog-motivos-editar').show()");
        setSeleccionEliminar("PF('dialog-motivos-eliminar').show()");

        //Columnas dinamicas
        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("Crear Motivo: " + super.getCurrent().getNombre());
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedMotivos());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new Motivo());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("Editar Motivo: Nombre Region" + super.getCurrent().getNombre());
        try {
            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllActivatedMotivos());
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
                for (Motivo o : getSelected()) {
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
        setList(serviceCatalogos.getAllActivatedMotivos());
    }
}
