package br.com.sistema_cadastros.inicio.security.securityRespository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.sistema_cadastros.inicio.security.user_security.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    UserDetails findByLogin(String login);
    
}
