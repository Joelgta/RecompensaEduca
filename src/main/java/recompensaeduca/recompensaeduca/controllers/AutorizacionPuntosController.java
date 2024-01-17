package recompensaeduca.recompensaeduca.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.configurations.GlobalControllerAdvice;
import recompensaeduca.recompensaeduca.models.ConceptoModel;
import recompensaeduca.recompensaeduca.models.PuntajeTransaccionesModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.services.impl.ConceptoService;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;
import recompensaeduca.recompensaeduca.services.impl.PersonalService;
import recompensaeduca.recompensaeduca.services.impl.PuntajeTransaccionesService;
import recompensaeduca.recompensaeduca.validations.PuntajeAutorizacionValidator;

@Controller
@RequestMapping("/autorizacionpuntos")
public class AutorizacionPuntosController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PuntajeTransaccionesService puntajeTransaccionesService;

    @Autowired
    private GlobalControllerAdvice globalControllerAdvice;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private ConceptoService conceptoService;

    private PuntajeAutorizacionValidator puntajeAutorizacionValidator;

    public AutorizacionPuntosController()
    {
        this.puntajeAutorizacionValidator = new PuntajeAutorizacionValidator();
    }
    
    @GetMapping("/listar")
    private String listar(Model model)
    {
        String nombreUsuario = this.globalControllerAdvice.nombreUsuario();
        String nombrePerfil = this.globalControllerAdvice.nombrePerfil();
        PersonalEntity personal = this.personalService.buscarPorNombreUsuarioYActivo(nombreUsuario, 1);
        Iterable<ConceptoModel> conceptos = this.conceptoService.buscarTodos();

        List<Object[]> results = new ArrayList<Object[]>();

        if(nombrePerfil.equals("docente")){
            results = this.puntajeTransaccionesService.buscarTodosPorPersonalOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc(personal);
        }
        if(nombrePerfil.equals("administrador")){
            results = this.puntajeTransaccionesService.buscarTodosPorOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc();
        }

        List<PuntajeTransaccionesModel> transacciones = new ArrayList<>();
        List<EstudianteEntity> estudiantes = new ArrayList<>();

        for (Object[] result : results) {
            transacciones.add((PuntajeTransaccionesModel) result[0]);
            estudiantes.add((EstudianteEntity) result[1]);
        }

        model.addAttribute("conceptos", conceptos);
        model.addAttribute("transacciones", transacciones);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/autorizacionpuntos/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id)
    {

        PuntajeTransaccionesModel transaccion = this.puntajeTransaccionesService.buscarPorId(id);
        EstudianteEntity estudiante = this.estudianteService.buscarPorIdYActivo(transaccion.getEstudianteId(), 1);

        String nombreEstudiante = estudiante.getNombre() + ' ' + estudiante.getApellidoPaterno() + ' ' + estudiante.getApellidoMaterno();

        model.addAttribute("nombreEstudiante", nombreEstudiante);
        model.addAttribute("transaccion", transaccion);

        return "custom/autorizacionpuntos/editar";
    }

    @PutMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id, @NonNull PuntajeTransaccionesModel transaccion, @NonNull BindingResult resultBinding)
    {
        this.puntajeAutorizacionValidator.validate(transaccion, resultBinding);
        if(resultBinding.hasErrors()){

            Map<String, String> errores = this.puntajeAutorizacionValidator.fielError(resultBinding);

            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            PuntajeTransaccionesModel nuevaTransaccion = this.puntajeTransaccionesService.buscarPorId(id);
            EstudianteEntity estudiante = this.estudianteService.buscarPorIdYActivo(nuevaTransaccion.getEstudianteId(), 1);

            String nombreEstudiante = estudiante.getNombre() + ' ' + estudiante.getApellidoPaterno() + ' ' + estudiante.getApellidoMaterno();

            model.addAttribute("nombreEstudiante", nombreEstudiante);
            model.addAttribute("transaccion", nuevaTransaccion);


            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/autorizacionpuntos/editar";
        }

        PuntajeTransaccionesModel puntajeEditar = this.puntajeTransaccionesService.buscarPorId(id);
        EstudianteEntity estudiante = this.estudianteService.buscarPorIdYActivo(puntajeEditar.getEstudianteId(), 1);
        Iterable<ConceptoModel> conceptos = this.conceptoService.buscarTodos();

        puntajeEditar.setDetalle(transaccion.getDetalle());
        puntajeEditar.setValidado(transaccion.getValidado());

        puntajeTransaccionesService.guardar(puntajeEditar);

        if(transaccion.getValidado() == 1){
            estudiante.setPuntaje(puntajeEditar.getPuntajeTraspaso() + estudiante.getPuntaje());
            estudianteService.guardar(estudiante);
        }

        List<Object[]> results = this.puntajeTransaccionesService.buscarTodosPorOrdenadoPorValidadoAscPersonal_ApellidoPaternoAsc();
        List<PuntajeTransaccionesModel> transacciones = new ArrayList<>();
        List<EstudianteEntity> estudiantes = new ArrayList<>();

        for (Object[] result : results) {
            transacciones.add((PuntajeTransaccionesModel) result[0]);
            estudiantes.add((EstudianteEntity) result[1]);
        }

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Haz actualizado el estado y el detalle de la solicitud");

        model.addAttribute("conceptos", conceptos);
        model.addAttribute("transacciones", transacciones);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/autorizacionpuntos/listar";
    }
}
