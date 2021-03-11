package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.Parametros;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.presentation.views.utils.DynamicField;
import com.crossmark.collector.presentation.views.utils.SeccionParametro;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christian on 22/12/2014.
 */


public class ParametrosController implements Serializable {


    // private List<FluidGridItem> list;


    private DynaFormModel model;

    private List<SeccionParametro> list;

    private ServiceCatalogos serviceCatalogos;

    private Map<Integer, String> parametros;

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    @PostConstruct
    protected void init() {
        armarSecciones();
    }


    public void guardar() {
        try {
            for (Map.Entry<Integer, String> entry : parametros.entrySet()) {
                Utileria.logger(getClass()).info("Id: " + entry.getKey() + "Valor: " + entry.getValue());
            }
            serviceCatalogos.guardar(parametros);
            Utileria.mensajeSatisfactorio(Utileria.getString("save_success"));
            armarSecciones();
        } catch (Exception e) {
            Utileria.mensajeErroneo(Utileria.getString("fail_operation"));
            Utileria.logger(getClass()).error(e.getMessage());
            Utileria.logger(getClass()).error(e.getCause());
        } finally {
            parametros.clear();
        }


    }


    private void armarSecciones() {
        model = new DynaFormModel();
        parametros = new HashMap<>();
        List<SeccionParametro> seccionParametros = serviceCatalogos.getAllSeccionParametro();
        List<Parametros> parametrosList = serviceCatalogos.getAllParametros();

        Collections.sort(seccionParametros);
        Collections.sort(parametrosList);

        for (Parametros item : parametrosList) {
            for (SeccionParametro padre : seccionParametros) {
                if (item.getSeccionParametro().getId() == padre.getId()) {
                    crearRegistro(item, padre.getParametros());
                }
            }
        }


        list = seccionParametros;

    }

    private void crearRegistro(Parametros o, DynaFormModel model) {
        DynaFormRow row = model.createRegularRow();
        DynamicField<Parametros> dynamicField = new DynamicField();
        DynaFormLabel label;
        DynaFormControl control;

        dynamicField.setValue(o);
        label = row.addLabel(dynamicField.getValue().getTitulo());
        dynamicField.setRequired(true);
        dynamicField.setSelectItems(null);

        switch (o.getTipoValor()) {

            case "STRING":
                control = row.addControl(dynamicField, "input");
                label.setForControl(control);
                break;
            case "BOOLEAN":
                control = row.addControl(dynamicField, "radio");
                label.setForControl(control);
                break;
            case "LONG":

            case "NUMBER":
                control = row.addControl(dynamicField, "num");
                label.setForControl(control);
                break;
            case "LARGESTR":
                control = row.addControl(dynamicField, "textarea");
                label.setForControl(control);
                break;

        }

    }

    public void nvosValores(Parametros o) {
        Utileria.logger(getClass()).info("Id: " + o.getParametrosId());
        Utileria.logger(getClass()).info("Valor: " + o.getValor());
        parametros.put(o.getParametrosId(), o.getValor());
    }

    public List<SeccionParametro> getList() {
        return list;
    }

    public DynaFormModel getModel() {
        return model;
    }
}
