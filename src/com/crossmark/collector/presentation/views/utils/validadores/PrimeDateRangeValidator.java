package com.crossmark.collector.presentation.views.utils.validadores;


import com.crossmark.collector.presentation.views.utils.Utileria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;

/**
 * Created by christian on 11/12/2014.
 */

@FacesValidator("primeDateRangeValidator")
public class PrimeDateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
        if (startDateValue == null) {
            return;
        }

        Date startDate = (Date) startDateValue;
        Date endDate = (Date) value;
        if (endDate.before(startDate)) {
            throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("fallo_rango_horas")));

        }
    }
}