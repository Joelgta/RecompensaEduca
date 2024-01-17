package recompensaeduca.recompensaeduca.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.models.RecompensaModel;
import recompensaeduca.recompensaeduca.services.impl.RecompensaService;
import recompensaeduca.recompensaeduca.validations.RecompensaValidator;

@Controller
@RequestMapping("/recompensa")
public class RecompensaController {
    
    @Autowired
    private RecompensaService recompensaService;

    private RecompensaValidator recompensaValidator;

    public RecompensaController()
    {
        this.recompensaValidator = new RecompensaValidator();
    }

    @GetMapping("/listar")
    public String listar(Model model)
    {
        List<RecompensaModel> recompensas = recompensaService.buscarTodosPorActivo(1);

        model.addAttribute("recompensas", recompensas);

        return "custom/recompensa/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model)
    {
        model.addAttribute("recompensa", new RecompensaModel());

        return "custom/recompensa/crear";
    }

    @PostMapping("/crear")
    public String crear(Model model, @NonNull RecompensaModel recompensa, @NonNull BindingResult result)
    {
        this.recompensaValidator.validate(recompensa, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.recompensaValidator.fielError(result);

            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            model.addAttribute("recompensa", recompensa);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/recompensa/crear";
        }

        RecompensaModel recompensaEncontrada = recompensaService.buscarPorNombreYActivo(recompensa.getNombre(), 1);
        RecompensaModel recompensaInactiva = recompensaService.buscarPorNombreYActivo(recompensa.getNombre(), 0);
        
        model.addAttribute("recompensa", recompensa);

        if(recompensaEncontrada != null){
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Hay una coincidencia en el nombre de la recompensa");

            return "custom/recompensa/crear";
        }

        if(recompensaInactiva != null){

            recompensa.setId(recompensaInactiva.getId());
            recompensa.setActivo(1);

            this.recompensaService.guardar(recompensa);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "success");
            model.addAttribute("titulo", "¡Excelente!");
            model.addAttribute("mensaje", "Recompensa creada");
        }

        recompensa.setActivo(1);
        this.recompensaService.guardar(recompensa);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Recompensa creada");
        
        return "custom/recompensa/crear";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id)
    {
        RecompensaModel recompensa = this.recompensaService.buscarPorIdYActivo(id, 1);

        model.addAttribute("recompensa", recompensa);

        return "custom/recompensa/editar";
    }

    @PutMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id, @NonNull RecompensaModel recompensa, @NonNull BindingResult result)
    {
        this.recompensaValidator.validate(recompensa, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.recompensaValidator.fielError(result);

            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            model.addAttribute("recompensa", recompensa);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/recompensa/crear";
        }

        RecompensaModel recompensaEncontrada = this.recompensaService.buscarPorNombreYActivo(recompensa.getNombre(), 1);
        RecompensaModel recompensaAnterior = this.recompensaService.buscarPorIdYActivo(id, 1);

        model.addAttribute("recompensa", recompensa);

        if(recompensaEncontrada != null && !recompensa.getNombre().equals(recompensaAnterior.getNombre())){
            
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Debes cambiar el nombre de la recompensa hay una coincidencia");

            return "custom/recompensa/editar";
        }

        recompensa.setActivo(1);
        recompensa.setId(recompensaAnterior.getId());
        recompensa.setCreadoEn(recompensaAnterior.getCreadoEn());
        this.recompensaService.guardar(recompensa);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "La recompensa se ha actualizado");

        return "custom/recompensa/editar";
    }

    @DeleteMapping("/{id}")
    public String eliminar(Model model, @PathVariable("id") int id)
    {
        RecompensaModel recompensaEliminada = this.recompensaService.buscarPorIdYActivo(id, 1);
        recompensaEliminada.setActivo(0);
        this.recompensaService.guardar(recompensaEliminada);

        List<RecompensaModel> recompensas = recompensaService.buscarTodosPorActivo(1);

        model.addAttribute("recompensas", recompensas);
        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Recompensa eliminada");
        
        return "custom/recompensa/listar";
    }
}
