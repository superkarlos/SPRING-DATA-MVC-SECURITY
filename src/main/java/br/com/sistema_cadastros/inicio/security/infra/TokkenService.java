package br.com.sistema_cadastros.inicio.security.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.sistema_cadastros.inicio.security.user_security.User;

@Service
public class TokkenService {
    @Value("${api.security.token.secret}")
    private String secrete;

    public String generateTokken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secrete);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(tempExpInstant())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro tokker");
        }
    }

    private Instant tempExpInstant() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validTokkne(String tokken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secrete);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(tokken)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
}
