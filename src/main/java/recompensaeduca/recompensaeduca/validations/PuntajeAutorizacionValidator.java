package recompensaeduca.recompensaeduca.validations;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;

public class PuntajeAutorizacionValidator extends FieldErrorValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PuntajeTransaccionesModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "detalle", "required.detalle","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "validado", "required.validado","Este campo es obligatorio");
    }
    
}
