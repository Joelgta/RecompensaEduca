package recompensaeduca.recompensaeduca.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import recompensaeduca.recompensaeduca.services.impl.CustomUserDetailsService;

@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Si estás trabajando con formularios y peticiones desde el cliente, puede ser necesario deshabilitar CSRF. Utilizar con precaución.
            .userDetailsService(customUserDetailsService)
            .authorizeRequests(authorize -> authorize
            .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()  // Permite el acceso sin autenticar
            .anyRequest().authenticated()  // Requiere autenticación para cualquier otra ruta
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login") // Endpoint que procesará el formulario de inicio de sesión
                .defaultSuccessUrl("/portada", true) // Página a la que se redirigirá tras un inicio de sesión exitoso
                .failureUrl("/login?error=true") // Página a la que se redirigirá tras un inicio de sesión fallido
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/perform_logout") // Endpoint que procesará el cierre de sesión
                .logoutSuccessUrl("/login?logout=true") // Página a la que se redirigirá tras cerrar sesión
                .deleteCookies("JSESSIONID") // Elimina la cookie de sesión
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
