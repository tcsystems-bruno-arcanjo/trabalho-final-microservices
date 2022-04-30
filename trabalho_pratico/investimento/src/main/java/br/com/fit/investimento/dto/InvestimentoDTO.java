package br.com.fit.investimento.dto;

import br.com.fit.investimento.model.ContaCorrente;
import br.com.fit.investimento.model.Investimento;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InvestimentoDTO {

    private long id;
    private double valor;

    public InvestimentoDTO(Investimento investimento) {
        this.id = investimento.getId();
        this.valor = investimento.getValor();
    }

    public Investimento converter(ContaCorrente contaCorrente) {
        return new Investimento(contaCorrente.getId(), LocalDateTime.now(), this.valor);
    }
}
