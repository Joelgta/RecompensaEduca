package recompensaeduca.recompensaeduca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@Controller
@RequestMapping("/solicitarpuntos")
public class SolicitarPuntos {
    
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

    @GetMapping("/listar")
    public String listar(Model model)
    {
        List<EstudianteEntity> estudiantes = this.estudianteService.buscarTodosPorActivoOrdenarPorCurso_NombreCurso(1);
        Iterable<ConceptoModel> conceptos = this.conceptoService.buscarTodos();

        model.addAttribute("concepto", new ConceptoModel());
        model.addAttribute("conceptos", conceptos);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/solicitarpuntos/listar";
    }

    @PostMapping("/{idEstudiante}")
    public String solicitar(Model model, @PathVariable("idEstudiante") int idEstudiante, ConceptoModel concepto)
    {
        String nombreUsuario = this.globalControllerAdvice.nombreUsuario();
        PersonalEntity personal = this.personalService.buscarPorNombreUsuarioYActivo(nombreUsuario, 1);
        ConceptoModel conceptoEncontrado = this.conceptoService.buscarPorId(concepto.getId());
        Iterable<ConceptoModel> conceptos = this.conceptoService.buscarTodos();

        List<EstudianteEntity> estudiantes = this.estudianteService.buscarTodosPorActivoOrdenarPorCurso_NombreCurso(1);

        model.addAttribute("concepto", new ConceptoModel());
        model.addAttribute("conceptos", conceptos);
        model.addAttribute("estudiantes", estudiantes);

        PuntajeTransaccionesModel puntajePendiente = this.puntajeTransaccionesService.buscarPorEstudianteIdYValidado(idEstudiante, 0);

        if(concepto.getId() == 0){
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Debes elegir un concepto para la solicitud");
            
            return "custom/solicitarpuntos/listar";
        }

        if(puntajePendiente != null){
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Lo sentimos hay puntaje pendiente por validar, no puedes solicitar hasta que pueda ser validado");

            return "custom/solicitarpuntos/listar";
        }

        PuntajeTransaccionesModel puntajeSolicitado = new PuntajeTransaccionesModel();

        puntajeSolicitado.setEstudianteId(idEstudiante);
        puntajeSolicitado.setPuntajeTraspaso(1000);
        puntajeSolicitado.setPersonal(personal);
        puntajeSolicitado.setValidado(0);
        puntajeSolicitado.setDetalle("");
        puntajeSolicitado.setConcepto(conceptoEncontrado);

        this.puntajeTransaccionesService.guardar(puntajeSolicitado);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Puntaje solicitado");

        return "custom/solicitarpuntos/listar";
    }
}
