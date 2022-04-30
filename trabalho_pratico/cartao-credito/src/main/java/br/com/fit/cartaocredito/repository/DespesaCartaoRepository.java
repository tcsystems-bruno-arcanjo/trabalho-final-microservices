package br.com.fit.cartaocredito.repository;

import br.com.fit.cartaocredito.model.DespesaCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DespesaCartaoRepository extends JpaRepository<DespesaCartao, Long> {

    List<DespesaCartao> findByIdContaCorrenteAndPagaFalse(Long idContaCorrente);
}
