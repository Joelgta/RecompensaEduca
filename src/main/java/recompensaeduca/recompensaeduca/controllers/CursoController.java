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
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.models.ColegioModel;
import recompensaeduca.recompensaeduca.models.CursoModel;
import recompensaeduca.recompensaeduca.services.impl.ColegioService;
import recompensaeduca.recompensaeduca.services.impl.CursoService;
import recompensaeduca.recompensaeduca.validations.CursoValidator;

@Controller
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ColegioService colegioService;

    private CursoValidator cursoValidator;

    public CursoController()
    {
        this.cursoValidator = new CursoValidator();
    }
    
    @GetMapping("/listar")
    public String listar(Model model)
    {
        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("cursos", cursos);

        return "custom/curso/listar";
    }

    @GetMapping("/crear")
    private String crear(Model model)
    {
        model.addAttribute("curso", new CursoModel());

        return "custom/curso/crear";
    }

    @PostMapping("/crear")
    private String crear(Model model, @NonNull CursoModel curso, @NonNull BindingResult result)
    {

        this.cursoValidator.validate(curso, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.cursoValidator.fielError(result);

            System.out.println("LOG PRINT");
            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
                System.out.println(errores.get(e));
            }

            model.addAttribute("curso", curso);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/curso/crear";
        }

        model.addAttribute("curso", new CursoModel());

        CursoModel cursoInactivo = this.cursoService.buscarCursoPorTipoYGradoYLetraPorActivo(curso.getTipoEnsenanza(), curso.getGrado(), curso.getLetra(), 0);
        CursoModel cursoActivo = this.cursoService.buscarCursoPorTipoYGradoYLetraPorActivo(curso.getTipoEnsenanza(), curso.getGrado(), curso.getLetra(), 1);
        if(cursoInactivo != null){
            cursoInactivo.setActivo(1);
            cursoService.guardarCurso(cursoInactivo);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "success");
            model.addAttribute("titulo", "¡Excelente!");
            model.addAttribute("mensaje", "Curso creado.");

            return "custom/curso/crear";
        }
        if(cursoActivo != null){
            
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "El curso está actualmente creado, consulta la lista.");

            return "custom/curso/crear";
        }
        ColegioModel colegio = this.colegioService.buscarColegioPorId(1);
        curso.setActivo(1);
        curso.setColegio(colegio);
        
        String nombreCurso = curso.getGrado() + " Básico " + curso.getLetra(); 
        curso.setNombreCurso(nombreCurso);
        cursoService.guardarCurso(curso);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Curso creado.");

        return "custom/curso/crear";
    }

    @DeleteMapping("/{id}")
    private String eliminar(Model model, @PathVariable("id") int id)
    {
        CursoModel curso  = this.cursoService.buscarCurso(id);
        curso.setActivo(0);
        cursoService.guardarCurso(curso);

        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("cursos", cursos);
        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Curso eliminado");

        return "custom/curso/listar";
    }
}
