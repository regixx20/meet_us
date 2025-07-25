package myapp.security;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import myapp.model.User;
import myapp.repository.UserRepository;
import myapp.service.AuthenticationSucessService;
import myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SpringSecurity {

    @Autowired
    UserService userService;

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers("/webjars/**");
            web.ignoring().requestMatchers("/images/**");
            web.ignoring().requestMatchers("/css/**");
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] anonymousRequests = {"/",
                "/webjars/**",
                "/homepage",
                "/meeting/**",
                "/login",
                "/register",
                "/contact"
        };
        http.authorizeHttpRequests(config -> {//
            config.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll();
            // Pour tous
            config.requestMatchers(anonymousRequests).permitAll();
            // Pour tous les utilisateurs authentifiés
            config.anyRequest().authenticated();
        });
        // Nous autorisons un formulaire de login
        http.formLogin(formLoginConfigurer -> {
            formLoginConfigurer
                    .successHandler(authenticationSucess())  // Ajouter le gestionnaire de succès
                    .permitAll();  // Permettre l'accès à tous pour les pages de login
        });
        // Nous autorisons un formulaire de logout
        http.logout(config -> {
            config.permitAll();
            config.logoutSuccessUrl("/");
            config.invalidateHttpSession(true);
            config.deleteCookies("JSESSIONID");
        });
        // Nous activons CSRF pour les actions protégées
        http.csrf(config -> {
            config.ignoringRequestMatchers(anonymousRequests);
        });
        return http.build();
    }

    /*
     * Définition des utilisateurs en BD.
     */
    @PostConstruct
    public void init() {
        System.out.println("--- INIT SPRING SECURITY");
    }

    @Bean
    public AuthenticationProvider myAuthenticationProvider(//
                                                           @Autowired PasswordEncoder encoder, //
                                                           @Autowired UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    /*
     * Définir la politique par défaut pour le cryptage des mots de passe.
     */
    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSucess() {
        return new AuthenticationSucessService();
    }

}
