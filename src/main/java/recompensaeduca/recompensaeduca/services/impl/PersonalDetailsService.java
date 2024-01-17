package recompensaeduca.recompensaeduca.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import recompensaeduca.recompensaeduca.models.entities.PersonalEntity;

@Service
public class PersonalDetailsService implements UserDetailsService {

    @Autowired
    PersonalService personalService;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        PersonalEntity miUsuario = this.personalService.buscarPorNombreUsuarioYActivo(usuario,1); // Cambia 'findByUsername' por tu método personalizado
        if (miUsuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        String perfil = miUsuario.getPerfilId().toString();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(perfil));
        return new User(miUsuario.getNombreUsuario(), miUsuario.getContrasena(), authorities);
    }
    
}
