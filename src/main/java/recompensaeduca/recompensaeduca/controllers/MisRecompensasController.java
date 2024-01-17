package recompensaeduca.recompensaeduca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.configurations.GlobalControllerAdvice;
import recompensaeduca.recompensaeduca.models.CanjearRecompensaModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.services.impl.CanjearRecompensaService;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;

@Controller
@RequestMapping("/misrecompensas")
public class MisRecompensasController {
    
    @Autowired
    private CanjearRecompensaService canjearRecompensaService;

    @Autowired
    private GlobalControllerAdvice globalControllerAdvice;

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("listar")
    public String listar(Model model)
    {
        String nombreUsuario = this.globalControllerAdvice.nombreUsuario();
        EstudianteEntity estudiante = this.estudianteService.buscarPorNombreUsuarioYActivo(nombreUsuario, 1);

        List<CanjearRecompensaModel> misRecompensas = this.canjearRecompensaService.buscarTodosPorEstudianteIdOrdenadoPorCanjeadoAscRecompensa_IdAsc(estudiante.getId());

        model.addAttribute("misRecompensas", misRecompensas);

        return "custom/mirecompensa/listar";
    }
}
