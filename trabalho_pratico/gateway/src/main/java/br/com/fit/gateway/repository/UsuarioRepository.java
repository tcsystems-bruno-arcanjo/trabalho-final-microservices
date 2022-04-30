package br.com.fit.gateway.repository;

import br.com.fit.gateway.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuarioAndAtivoTrue(String usuario);
}
