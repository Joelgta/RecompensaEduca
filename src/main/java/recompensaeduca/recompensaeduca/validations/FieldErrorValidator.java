package recompensaeduca.recompensaeduca.validations;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class FieldErrorValidator {
    
    public Map<String, String> fielError(BindingResult result)
    {
        Map<String, String> errores = new LinkedHashMap<>();
        for (Object object : result.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errores.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return errores;
    }
}
