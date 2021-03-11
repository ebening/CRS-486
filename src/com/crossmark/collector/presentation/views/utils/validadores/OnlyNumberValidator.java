package com.crossmark.collector.presentation.views.utils.validadores;

import com.crossmark.collector.presentation.views.utils.Utileria;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by christian on 18/12/2014.
 */
public class OnlyNumberValidator implements Validator{

        private static final String CODIGO_POSTAL_PATTERN = "[0-9]{6}";

        private Pattern pattern;
        private Matcher matcher;

        public OnlyNumberValidator() {
        pattern = Pattern.compile(CODIGO_POSTAL_PATTERN);
    }


        @Override
        public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            throw new ValidatorException(Utileria.mensajeErroneo_("No es un Codigo Postal valido"));
        }

    }
    }