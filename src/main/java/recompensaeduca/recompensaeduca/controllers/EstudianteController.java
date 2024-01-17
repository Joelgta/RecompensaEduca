package recompensaeduca.recompensaeduca.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import recompensaeduca.recompensaeduca.models.ComunaModel;
import recompensaeduca.recompensaeduca.models.CursoModel;
import recompensaeduca.recompensaeduca.models.PerfilModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.services.impl.ComunaService;
import recompensaeduca.recompensaeduca.services.impl.CursoService;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;
import recompensaeduca.recompensaeduca.services.impl.PerfilService;
import recompensaeduca.recompensaeduca.services.impl.PersonalService;
import recompensaeduca.recompensaeduca.validations.EstudianteValidator;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

     @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private PersonalService personalService;

    private EstudianteValidator estudianteValidator;

    public EstudianteController()
    {
        this.estudianteValidator = new EstudianteValidator();
    }

    @GetMapping("/listar")
    public String listar(Model model)
    {

        List<EstudianteEntity> estudiantes = this.estudianteService.buscarTodosPorActivoOrdenarPorCurso_NombreCurso(1);
        model.addAttribute("estudiantes", estudiantes);

        return "custom/estudiante/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model)
    {
        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("estudiante", new EstudianteEntity());
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("cursos", cursos);

        return "custom/estudiante/crear";
    }

    @PostMapping("crear")
    public String crear(Model model, @NonNull EstudianteEntity estudiante, @NonNull BindingResult result)
    {
        this.estudianteValidator.validate(estudiante, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.estudianteValidator.fielError(result);
            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

            model.addAttribute("estudiante", estudiante);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", cursos);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/estudiante/crear";
        }

        if(estudiante.getContrasena().equals("")){

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

            model.addAttribute("estudiante", estudiante);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", cursos);
            model.addAttribute("error_contrasena", "Este campo es obligatorio");

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/estudiante/crear";
        }

        EstudianteEntity estudianteInactivo = this.estudianteService.buscarPorRutYActivo(estudiante.getRut(), 0);
        EstudianteEntity estudianteActivo = this.estudianteService.buscarPorRutYActivo(estudiante.getRut(), 1);
        EstudianteEntity estudianteUsuarioActivo = this.estudianteService.buscarPorNombreUsuarioYActivo(estudiante.getNombreUsuario(), 1);
        PersonalEntity personalUsuarioActivo = this.personalService.buscarPorNombreUsuarioYActivo(estudiante.getNombreUsuario(), 1);

        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("cursos", cursos);

        if(estudianteInactivo != null){
            estudianteInactivo.setActivo(1);
            estudianteInactivo.setPuntaje(0);
            estudianteInactivo.setContrasena(this.passwordEncoder.encode(estudiante.getContrasena()));
            estudianteInactivo.setDireccion(estudiante.getDireccion());
            estudianteInactivo.setComunaId(estudiante.getComunaId());
            estudianteInactivo.setCurso(estudiante.getCurso());

            this.estudianteService.guardar(estudianteInactivo);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "success");
            model.addAttribute("titulo", "¡Excelente!");
            model.addAttribute("mensaje", "Estudiante creado.");

            return "custom/estudiante/crear";
        }
        if(estudianteActivo != null){
            
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "El estudiante está actualmente creado, consulta la lista.");

            return "custom/estudiante/crear";
        }

        if(personalUsuarioActivo != null || estudianteUsuarioActivo != null){

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Debes elegir otro nombre de usuario, debido a que está ocupado.");

            return "custom/estudiante/crear";
        }

        estudiante.setActivo(1);
        estudiante.setPuntaje(0);
        estudiante.setContrasena(this.passwordEncoder.encode(estudiante.getContrasena()));
        this.estudianteService.guardar(estudiante);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Usuario creado.");
        return "custom/estudiante/crear";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id)
    {
        EstudianteEntity estudiante = this.estudianteService.buscarPorIdYActivo(id, 1);
        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("cursos", cursos);

        return "custom/estudiante/editar";
    }

    @PutMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id, @NonNull EstudianteEntity estudiante, @NonNull BindingResult result)
    {
        this.estudianteValidator.validate(estudiante, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.estudianteValidator.fielError(result);

            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

            model.addAttribute("estudiante", estudiante);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", cursos);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/estudiante/crear";
        }

        EstudianteEntity estudianteBuscado = this.estudianteService.buscarPorIdYActivo(id, 1);

        estudiante.getContrasena().replace(" ","");
        
        if(!estudiante.getContrasena().equals("") && this.passwordEncoder.encode(estudiante.getContrasena()) != estudianteBuscado.getContrasena()){
            estudiante.setContrasena(this.passwordEncoder.encode(estudiante.getContrasena()));
        }else{
            estudiante.setContrasena(estudianteBuscado.getContrasena());
        }

        estudiante.setCreadoEn(estudianteBuscado.getCreadoEn());
        estudiante.setPuntaje(estudianteBuscado.getPuntaje());
        estudiante.setActivo(1);

        this.estudianteService.guardar(estudiante);

        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        List<CursoModel> cursos = this.cursoService.buscarCursosPorActivoOrdenadoPorNombreCurso(1);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("cursos", cursos);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Estudiante editado");

        return "custom/estudiante/editar";
    }

    @DeleteMapping("/{id}")
    public String eliminar(Model model, @PathVariable("id") int id)
    {
        EstudianteEntity estudiante = this.estudianteService.buscarPorIdYActivo(id, 1);
        estudiante.setActivo(0);
        this.estudianteService.guardar(estudiante);

        List<EstudianteEntity> estudiantes = this.estudianteService.buscarTodosPorActivoOrdenarPorCurso_NombreCurso(1);
        model.addAttribute("estudiantes", estudiantes);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Estudiante eliminado");

        return "custom/estudiante/listar";
    }
}
