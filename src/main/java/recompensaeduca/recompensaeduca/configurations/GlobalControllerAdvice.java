package recompensaeduca.recompensaeduca.configurations;


import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


import recompensaeduca.recompensaeduca.models.PerfilModel;
import recompensaeduca.recompensaeduca.models.entities.EstudianteEntity;
import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;
import recompensaeduca.recompensaeduca.services.impl.EstudianteService;
import recompensaeduca.recompensaeduca.services.impl.PerfilService;
import recompensaeduca.recompensaeduca.services.impl.PersonalService;


@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private EstudianteService estudianteService;

    @ModelAttribute("usuarioAutenticado")
    public boolean isUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Revisa si el usuario está autenticado y no es un usuario anónimo
        return authentication != null && authentication.isAuthenticated() 
               && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("nombreUsuario")
    public String nombreUsuario() 
    {
        if (!this.isUsuarioAutenticado()) {
            return "";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @ModelAttribute("nombrePerfil")
    public String nombrePerfil() 
    {
        if (!this.isUsuarioAutenticado()) {
            return "";
        }
        return perfil().getNombre();       
    }

    @ModelAttribute("puntaje")
    private Integer puntaje()
    {
        Integer puntaje = 0;
        if (!this.isUsuarioAutenticado()) {
            return puntaje;
        }

        if(this.nombrePerfil().equals("administrador") || this.nombrePerfil().equals("docente")){
            
            PersonalEntity personal = this.personalService.buscarPorNombreUsuarioYActivo(this.nombreUsuario(), 1);
            puntaje = personal.getPuntaje();
        
        }else if(this.nombrePerfil().equals("estudiante")){
            
            EstudianteEntity estudiante = this.estudianteService.buscarPorNombreUsuarioYActivo(this.nombreUsuario(), 1);
            puntaje = estudiante.getPuntaje();
        
        }
        
        return puntaje;
    }

    @ModelAttribute("nombre")
    private String nombre()
    {
        String nombre = "";
        if (!this.isUsuarioAutenticado()) {
            return nombre;
        }

        if(this.nombrePerfil().equals("administrador") || this.nombrePerfil().equals("docente")){
            
            PersonalEntity personal = this.personalService.buscarPorNombreUsuarioYActivo(this.nombreUsuario(), 1);
            nombre = personal.getNombre();
        
        }else if(this.nombrePerfil().equals("estudiante")){
            
            EstudianteEntity estudiante = this.estudianteService.buscarPorNombreUsuarioYActivo(this.nombreUsuario(), 1);
            nombre = estudiante.getNombre();
        
        }
        
        return nombre;
    }

    @ModelAttribute("listaAcceso")
    public Map<String, String> listaAcceso() {
        Map<String, String> resultMap = new LinkedHashMap<>();
        if (!this.isUsuarioAutenticado()) {
                resultMap.put("Acceso no encontrado", "No encontrado");
                return resultMap;
        }
        PerfilModel perfil = perfil();
        JSONArray jsonArray = new JSONArray(perfil.getListaAcceso());
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (String key : jsonObject.keySet()) {
                resultMap.put(key, jsonObject.getString(key));
            }
        }
        return resultMap;
    }

    private StringBuilder authorities() 
    {
        if (!this.isUsuarioAutenticado()) {
            return new StringBuilder();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder authBuilder = new StringBuilder();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authBuilder.length() > 0) {
                authBuilder.append(", ");
            }
            authBuilder.append(authority.getAuthority());
        }
        return authBuilder;
    }

    private PerfilModel perfil()
    {
        StringBuilder authBuilder = authorities();
        String numeroStringperfil = authBuilder.toString();
        Integer numeroPerfil = Integer.parseInt(numeroStringperfil);

        return perfilService.buscarPorId(numeroPerfil);
    } 
}
