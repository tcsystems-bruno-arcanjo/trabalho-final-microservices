package br.com.fit.investimento.repository;

import br.com.fit.investimento.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {

    List<Investimento> findAllByIdContaCorrenteAndResgatadoFalse(long idContaCorrente);
}
