package br.com.sistema_cadastros.inicio.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema_cadastros.inicio.security.auth_dto.AuthDTO;
import br.com.sistema_cadastros.inicio.security.auth_dto.RegisterDTO;
import br.com.sistema_cadastros.inicio.security.infra.TokkenService;
import br.com.sistema_cadastros.inicio.security.securityRespository.UserRepository;
import br.com.sistema_cadastros.inicio.security.user_security.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired 
    private TokkenService tokkenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data){

        var username_password = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(username_password);
        var tokken = tokkenService.generateTokken((User)auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body("TOKKEN -> : "+ tokken);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){

        if(this.repository.findByLogin(data.login()) != null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já cadastrado");
        }

        String encrypt = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(),encrypt,data.role());
        this.repository.save(newUser);
        return ResponseEntity.ok().body(newUser );
    }
}
