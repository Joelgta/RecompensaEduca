package recompensaeduca.recompensaeduca.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Autowired
    private EstudianteDetailsService estudianteDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Intenta autenticar con el servicio de personal
            return this.personalDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            // Si no se encuentra en personal, intenta con estudiantes
            return this.estudianteDetailsService.loadUserByUsername(username);
        }
    }
}
