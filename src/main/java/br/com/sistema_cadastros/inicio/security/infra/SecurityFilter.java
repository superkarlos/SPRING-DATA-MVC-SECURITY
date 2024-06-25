package br.com.sistema_cadastros.inicio.security.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.sistema_cadastros.inicio.security.securityRespository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokkenService tokkenService;
    @Autowired
    private UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tokken = this.recorverTokker(request);
        if (tokken != null) {
            var login = this.tokkenService.validTokkne(tokken);
            UserDetails user = this.repository.findByLogin(login);
            var authenticate = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }
        filterChain.doFilter(request, response);
    }

    private String recorverTokker(HttpServletRequest request) {
        var auth = request.getHeader("Authorization");
        if (auth == null) {
            return null;
        }
        return auth.replace("Bearer ", "");
    }

}
