package br.com.fit.contacorrente.repository;

import br.com.fit.contacorrente.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findAllByIdContaCorrente(long idContaCorrente);
}
