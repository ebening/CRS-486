package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christian on 27/11/2014.
 */
public class UnidadesNegociosController extends AbstractCatalogs<UnidadesNegocio> implements Serializable {

    private ServiceCatalogos serviceCatalogos;

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    @Override
    protected void init() {
        setList(serviceCatalogos.getAllActivatedUnidadesNegocios());
        setSelected(new ArrayList<UnidadesNegocio>());

        setClase(UnidadesNegocio.class);
        setSeleccionCrear("PF('dialog-unidadesNegocios-crear').show()");
        setSeleccionEditar("PF('dialog-unidadesNegocios-editar').show()");
        setSeleccionEliminar("PF('dialog-unidadesNegocios-eliminar').show()");


    }

    @Override
    public void crear() {
        Utileria.logger(getClass()).info("CrearUnidadNegocio: " + getCurrent().getNombreUnidad());
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllUnidadesNegocios());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new UnidadesNegocio());
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {
        Utileria.logger(getClass()).info("EditarUnidadNegocio: " + getCurrent().getNombreUnidad());
        Utileria.logger(getClass()).info("EditarRegion: Tamaño de selectedUnidadesNegocio" + getSelected().size());
        try {
            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllUnidadesNegocios());
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
        Utileria.logger(getClass()).info("RemoveTerritorio: " + getCurrent().getNombreUnidad());
        try {
            String resultado = null;
            if (getSelected().size() > 1) {
                for (UnidadesNegocio territorio : getSelected()) {
                    resultado = serviceCatalogos.eliminar(territorio);
                    if (!resultado.equals("0")) {
                        noEliminados.put(territorio.getNombreUnidad(), resultado);
                        Utileria.mensajeAlerta(Utileria.getString(resultado));
                    } else {
                        eliminados.add(territorio.getNombreUnidad());
                        Utileria.mensajeSatisfactorio(Utileria.getString("remove_success_p", territorio.getNombreUnidad()));
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
        setList(serviceCatalogos.getAllUnidadesNegocios());
    }

    public void prueba() {
        Utileria.logger(getClass()).info("Nombre Unidad: " + getCurrent().getNombreUnidad());
        Utileria.logger(getClass()).info("horaIni: " + getCurrent().getdHoraIni());
        Utileria.logger(getClass()).info("horaFin: " + getCurrent().getdHoraFin());
    }


}



