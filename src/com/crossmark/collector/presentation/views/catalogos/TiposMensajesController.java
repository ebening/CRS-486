package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.TipoMensaje;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.*;

/**
 * Created by christian on 05/12/2014.
 */
public class TiposMensajesController extends AbstractCatalogs<TipoMensaje> implements Serializable {

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
        setList(serviceCatalogos.getAllActivatedTipoMensaje());
        setSelected(new ArrayList<TipoMensaje>());
        setCurrent(new TipoMensaje());

        setClase(TipoMensaje.class);
        setSeleccionCrear("PF('dialog-tiposMensajes-crear').show()");
        setSeleccionEditar("PF('dialog-tiposMensajes-editar').show()");
        setSeleccionEliminar("PF('dialog-tiposMensajes-eliminar').show()");

        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("Crear Tipo Mensaje: Nombre " + getCurrent().getNombre());
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllActivatedTipoMensaje());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new TipoMensaje());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("Editar Tipo Mensaje: Nombre:" + getCurrent().getNombre());
        try {
            serviceCatalogos.editar(getCurrent());
            super.setList(serviceCatalogos.getAllActivatedTipoMensaje());
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
                for (TipoMensaje territorio : getSelected()) {
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
        setList(serviceCatalogos.getAllActivatedTipoMensaje());
    }


}
