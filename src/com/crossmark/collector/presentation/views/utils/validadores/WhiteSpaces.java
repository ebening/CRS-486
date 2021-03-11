package com.crossmark.collector.presentation.views.utils.validadores;

import com.crossmark.collector.presentation.views.utils.Utileria;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by christian on 07/01/2015.
 */
@FacesValidator("whiteSpaces")
public class WhiteSpaces implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        String str = Utileria.validarStringNull(value);
        String label = uiComponent.getAttributes().get("label").toString();
        str = str.trim();
        Utileria.withoutWhiteSpace(str);
        Utileria.logger(getClass()).info("whiteSpaces: "+"'"+str+"'");
        if (str.equals("")) {
            throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("javax.faces.component.UIInput.REQUIRED",label)));
        }


    }
}
