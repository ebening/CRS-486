/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.utils.validadores;

import com.crossmark.collector.presentation.views.utils.Utileria;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@FacesValidator(value="dateGtNow")
public class DateGtNow implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        
        try {
            if (o == null) {
                return;
            }
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechasalida = sdf.format(new Date());
            
            Date date = (Date)o;
            if (date.before(sdf.parse(fechasalida) )){
                throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("validador_gt_datenow"),Utileria.getString("validador_gt_datenow")));
            }
        } catch (ParseException ex) {
            throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("validador_gt_datenow"),Utileria.getString("validador_gt_datenow")));
        }
    }
}
