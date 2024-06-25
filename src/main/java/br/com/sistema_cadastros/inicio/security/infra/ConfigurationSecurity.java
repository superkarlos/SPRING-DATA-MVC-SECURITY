package br.com.sistema_cadastros.inicio.security.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class ConfigurationSecurity {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "auth/register").permitAll()

                                .requestMatchers(HttpMethod.GET, "Alunos/lista").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Alunos/lista_logica").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Alunos/lista/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "Alunos/post").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "Alunos/post/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "Alunos/deletar/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "Professor/lista").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Professor/lista_logica").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Professor/lista/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "Professor/post").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "Professor/post/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "Professor/deletar/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "Professor/Turma/entrar/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "Turma/listar").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Turma/professor/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Turma/aluno/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Turma/listar_logica").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "Turma/listar/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "Turma/post").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "Turma/post/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "Turma/deletar/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "Turma/{idturma}/AssociarProfessor/{idprofessor}")
                                .hasRole("ADMIN")

                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
