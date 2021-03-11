/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.utils.validadores;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Francisco
 * Valida un rango de fechas, que la fecha inicial, sea menor que la fecha final.
 * La validacion, se coloca en la segunda fecha y se le pasa como atributo fechaIni.
 */
@FacesValidator(value="DateRange")
public class DateRange implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        
        if (o == null) {
            return;
        }
        
        UIInput fechaIniConp = (UIInput) uic.getAttributes().get("fechaIni");
        if (fechaIniConp == null && !fechaIniConp.isValid()) {
            return;
        }
        
        Date startDate = (Date) fechaIniConp.getValue();
        if (startDate == null) {
            return;
        }
        
        Date endDate = (Date)o;
        if (startDate.after(endDate)){
            fechaIniConp.setValid(false);
            throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("validaor_rango_fecha"),Utileria.getString("validaor_rango_fecha")));
        }
    }
}
