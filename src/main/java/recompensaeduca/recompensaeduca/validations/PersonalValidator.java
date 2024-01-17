package recompensaeduca.recompensaeduca.validations;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

public class PersonalValidator extends FieldErrorValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PersonalEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "rut", "required.rut","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "nombre", "required.nombre","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "apellidoPaterno", "required.apellidoPaterno","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "apellidoMaterno", "required.apellidoMaterno","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "nombreUsuario", "required.nombreUsuario","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "perfilId", "required.perfilId","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "colegio", "required.colegio","Este campo es obligatorio");
    }    
}
