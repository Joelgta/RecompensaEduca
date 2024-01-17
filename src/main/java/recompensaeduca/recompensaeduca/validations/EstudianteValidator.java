package recompensaeduca.recompensaeduca.validations;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;

public class EstudianteValidator extends FieldErrorValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return EstudianteEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "rut", "required.rut","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "nombre", "required.nombre","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "apellidoPaterno", "required.apellidoPaterno","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "apellidoMaterno", "required.apellidoMaterno","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "direccion", "required.direccion","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "comunaId", "required.comunaId","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "nombreUsuario", "required.nombreUsuario","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "perfilId", "required.perfilId","Este campo es obligatorio");
        ValidationUtils.rejectIfEmpty(errors, "curso", "required.curso","Este campo es obligatorio");
    }    
}
