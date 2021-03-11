package com.crossmark.collector.presentation.views.utils.validadores;


import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.utils.validadores.*;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.springframework.util.StringUtils;

/**
 * Created by Francisco Mora on 11/12/2014.
 */

@FacesValidator("descripcionCadena")
public class DescripcionCadena implements Validator {
    
    private static final String CADENA_PATTERN = "^[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ]+.*";
    
    private Pattern pattern;
    private Matcher matcher;
    
    public DescripcionCadena(){
        pattern = Pattern.compile(CADENA_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if(value == null){//Valida, valor del componente
            return;
        }
        
        matcher = pattern.matcher(value.toString());
        if(!matcher.matches()){
            throw new ValidatorException(Utileria.mensajeErroneo_(Utileria.getString("titulo_fail"),Utileria.getString("titulo_fail")));
        }
    }
}
