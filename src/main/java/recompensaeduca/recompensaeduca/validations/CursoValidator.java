package recompensaeduca.recompensaeduca.validations;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import recompensaeduca.recompensaeduca.models.CursoModel;

public class CursoValidator extends FieldErrorValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CursoModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        
        ValidationUtils.rejectIfEmpty(errors, "tipoEnsenanza", "required.tipoEnsenanza","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "grado", "required.grado","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "letra", "required.letra","Este campo es obligatorio");
    }
}
