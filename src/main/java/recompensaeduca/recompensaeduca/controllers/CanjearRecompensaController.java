package recompensaeduca.recompensaeduca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.models.CanjearRecompensaModel;
import recompensaeduca.recompensaeduca.models.RecompensaModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.services.impl.CanjearRecompensaService;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;
import recompensaeduca.recompensaeduca.services.impl.RecompensaService;

@Controller
@RequestMapping("/canjearrecompensa")
public class CanjearRecompensaController {
    

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private RecompensaService recompensaService;

    @Autowired
    private CanjearRecompensaService canjearRecompensaService;

    
    @PostMapping("/{idRecompensa}/{nombreUsuarioEstudiante}")
    public String postularRecompensa(Model model, @PathVariable("idRecompensa") int idRecompensa, @PathVariable("nombreUsuarioEstudiante") String nombreUsuarioEstudiante)
    {
        EstudianteEntity estudiante = this.estudianteService.buscarPorNombreUsuarioYActivo(nombreUsuarioEstudiante, 1);
        RecompensaModel recompensa = this.recompensaService.buscarPorIdYActivo(idRecompensa, 1);

        List<RecompensaModel> recompensas = this.recompensaService.buscarTodosPorActivo(1);

        model.addAttribute("recompensas", recompensas);

        if(estudiante.getPuntaje() < recompensa.getPuntaje()){
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "No puedes canjear la recompensa, por falta de puntos");

            return "custom/recompensa/listar";
        }

        CanjearRecompensaModel canjearRecompensa = new CanjearRecompensaModel();

        canjearRecompensa.setRecompensa(recompensa);
        canjearRecompensa.setEstudianteId(estudiante.getId());
        canjearRecompensa.setCanjeado(0);

        this.canjearRecompensaService.guardar(canjearRecompensa);

        estudiante.setPuntaje(estudiante.getPuntaje() - recompensa.getPuntaje());
        this.estudianteService.guardar(estudiante);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Haz canjeado la recompensa");

        return "custom/recompensa/listar";
    }

    @GetMapping("/pendiente/listar")
    public String pendientes(Model model)
    {
        List<Object[]> results = this.canjearRecompensaService.buscarTodosPorOrdenadoPorCanjeadoDescRecompensa_IdAscActualizadoEnAsc();
        List<CanjearRecompensaModel> canjearRecompensas = new ArrayList<>();
        List<EstudianteEntity> estudiantes = new ArrayList<>();

        for (Object[] result : results) {
            canjearRecompensas.add((CanjearRecompensaModel) result[0]);
            estudiantes.add((EstudianteEntity) result[1]);
        }

        model.addAttribute("recompensas", canjearRecompensas);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/recompensa/pendiente";
    }

    @PutMapping("/pendiente/{id}")
    public String pendientes(Model model, @PathVariable("id") int id)
    {
        CanjearRecompensaModel recompensa = this.canjearRecompensaService.buscarPorId(id);
        
        recompensa.setCanjeado(1);
        this.canjearRecompensaService.guardar(recompensa);

        List<Object[]> results = this.canjearRecompensaService.buscarTodosPorOrdenadoPorCanjeadoDescRecompensa_IdAscActualizadoEnAsc();
        List<CanjearRecompensaModel> canjearRecompensas = new ArrayList<>();
        List<EstudianteEntity> estudiantes = new ArrayList<>();

        for (Object[] result : results) {
            canjearRecompensas.add((CanjearRecompensaModel) result[0]);
            estudiantes.add((EstudianteEntity) result[1]);
        }

        model.addAttribute("recompensas", canjearRecompensas);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/recompensa/pendiente";
    }
}
