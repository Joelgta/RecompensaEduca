package recompensaeduca.recompensaeduca.validations;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import recompensaeduca.recompensaeduca.models.RecompensaModel;

public class RecompensaValidator extends FieldErrorValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return RecompensaModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "nombre", "required.nombre","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "descripcion", "required.descripcion","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "puntaje", "required.puntaje","Este campo es obligatorio");
    }
    
}
