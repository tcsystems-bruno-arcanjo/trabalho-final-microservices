package br.com.fit.gateway.service;

import br.com.fit.gateway.model.Usuario;
import br.com.fit.gateway.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

  public Mono<Usuario> findByUsername(String usuario) {
    return Mono.justOrEmpty(usuarioRepository.findByUsuarioAndAtivoTrue(usuario));
  }
}
