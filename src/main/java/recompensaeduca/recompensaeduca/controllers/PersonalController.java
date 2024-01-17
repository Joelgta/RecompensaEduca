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

import recompensaeduca.recompensaeduca.models.ColegioModel;
import recompensaeduca.recompensaeduca.models.ComunaModel;
import recompensaeduca.recompensaeduca.models.PerfilModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.services.impl.ColegioService;
import recompensaeduca.recompensaeduca.services.impl.ComunaService;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;
import recompensaeduca.recompensaeduca.services.impl.PerfilService;
import recompensaeduca.recompensaeduca.services.impl.PersonalService;
import recompensaeduca.recompensaeduca.validations.PersonalValidator;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ColegioService colegioService;

    @Autowired
    private EstudianteService estudianteService;

    private PersonalValidator personalValidator;

    public PersonalController()
    {
        this.personalValidator = new PersonalValidator();
    }

    @GetMapping("/listar")
    public String listar(Model model)
    {   
        List<PersonalEntity> personales = this.personalService.buscarTodosPorActivo(1);

        model.addAttribute("personales", personales);

        return "custom/personal/listar";
    }

    @GetMapping("/crear")
    public String crear(Model model)
    {
        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

        model.addAttribute("personal", new PersonalEntity());
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("colegios", colegios);

        return "custom/personal/crear";
    }

    @PostMapping("crear")
    public String crear(Model model, @NonNull PersonalEntity personal, @NonNull BindingResult result)
    {
        this.personalValidator.validate(personal, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.personalValidator.fielError(result);
            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

            model.addAttribute("personal", personal);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", colegios);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/personal/crear";
        }

        if(personal.getContrasena().equals("")){

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

            model.addAttribute("personal", personal);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", colegios);
            model.addAttribute("error_contrasena", "Este campo es obligatorio");

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/personal/crear";
        }

        PersonalEntity personalInactivo = this.personalService.buscarPorRutYActivo(personal.getRut(), 0);
        PersonalEntity personalActivo = this.personalService.buscarPorRutYActivo(personal.getRut(), 1);
        PersonalEntity personalUsuarioActivo = this.personalService.buscarPorNombreUsuarioYActivo(personal.getNombreUsuario(), 1);
        EstudianteEntity estudianteUsuarioActivo = this.estudianteService.buscarPorNombreUsuarioYActivo(personal.getNombreUsuario(), 1);

        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

        model.addAttribute("personal", personal);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("colegios", colegios);

        if(personalInactivo != null){
            personalInactivo.setActivo(1);
            personalInactivo.setContrasena(this.passwordEncoder.encode(personal.getContrasena()));
            personalInactivo.setPerfilId(personal.getPerfilId());
            personalInactivo.setPuntaje(0);
            PerfilModel perfil = this.perfilService.buscarPorId(personal.getPerfilId());
            if(perfil != null && perfil.getNombre().equals("docente")){
                personalInactivo.setPuntaje(30000);
            }
            
            personalService.guardar(personalInactivo);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "success");
            model.addAttribute("titulo", "¡Excelente!");
            model.addAttribute("mensaje", "Usuario creado.");

            return "custom/personal/crear";
        }

        if(personalActivo != null){
            
            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "El usuario está actualmente creado, consulta la lista.");

            return "custom/personal/crear";
        }

        if(personalUsuarioActivo != null || estudianteUsuarioActivo != null){

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Debes elegir otro nombre de usuario, debido a que está ocupado.");

            return "custom/personal/crear";
        }

        personal.setPuntaje(0);
        PerfilModel perfil = this.perfilService.buscarPorId(personal.getPerfilId());
        if(perfil != null && perfil.getNombre().equals("docente")){
            personal.setPuntaje(30000);
        }
        personal.setActivo(1);
        
        this.personalService.guardar(personal);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Usuario creado.");
        return "custom/personal/crear";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") int id)
    {
        PersonalEntity personal = this.personalService.buscarPorIdYActivo(id, 1);
        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

        model.addAttribute("personal", personal);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("colegios", colegios);

        return "custom/personal/editar";
    }

    @PutMapping("editar/{id}")
    public String editar(Model model, @PathVariable("id") int id, @NonNull PersonalEntity personal, @NonNull BindingResult result)
    { 
        this.personalValidator.validate(personal, result);
        if(result.hasErrors()){

            Map<String, String> errores = this.personalValidator.fielError(result);
            for (String e : errores.keySet()) {
                model.addAttribute("error_" + e, errores.get(e));
            }

            Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
            Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
            Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

            model.addAttribute("personal", personal);
            model.addAttribute("perfiles", perfiles);
            model.addAttribute("comunas", comunas);
            model.addAttribute("colegios", colegios);

            model.addAttribute("alerta", true);
            model.addAttribute("tipo", "warning");
            model.addAttribute("titulo", "¡Atención!");
            model.addAttribute("mensaje", "Algunos campos están vacios o no son válidos, debes completarlos todos.");

            return "custom/personal/crear";
        }

        PersonalEntity personalBuscado = this.personalService.buscarPorIdYActivo(id, 1);
        
        if(!personal.getContrasena().equals("") && this.passwordEncoder.encode(personal.getContrasena()) != personalBuscado.getContrasena()){
            personal.setContrasena(this.passwordEncoder.encode(personal.getContrasena()));
        }else{
            personal.setContrasena(this.passwordEncoder.encode(personalBuscado.getContrasena()));
        }
        personal.setCreadoEn(personalBuscado.getCreadoEn());
        personal.setPuntaje(personalBuscado.getPuntaje());
        personal.setActivo(1);

        this.personalService.guardar(personal);

        Iterable<PerfilModel> perfiles = this.perfilService.buscarTodos();
        Iterable<ComunaModel> comunas = this.comunaService.buscarComunas();
        Iterable<ColegioModel> colegios = this.colegioService.buscarColegios();

        model.addAttribute("personal", personal);
        model.addAttribute("perfiles", perfiles);
        model.addAttribute("comunas", comunas);
        model.addAttribute("colegios", colegios);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Usuario editado");

        return "custom/personal/editar";
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable("id") int id)
    {
        PersonalEntity personal = this.personalService.buscarPorIdYActivo(id, 1);
        personal.setActivo(0);
        this.personalService.guardar(personal);

        List<PersonalEntity> personales = this.personalService.buscarTodosPorActivo(1);
        model.addAttribute("personales", personales);

        model.addAttribute("alerta", true);
        model.addAttribute("tipo", "success");
        model.addAttribute("titulo", "¡Excelente!");
        model.addAttribute("mensaje", "Usuario eliminado");

        return "custom/personal/listar";
    }

}
