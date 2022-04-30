package br.com.fit.contacorrente.dto;

import br.com.fit.contacorrente.model.ContaCorrente;
import br.com.fit.contacorrente.model.Movimentacao;
import br.com.fit.contacorrente.enums.TipoMovimentacao;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MovimentacaoDTO {

    private double valor;

    public Movimentacao converter(ContaCorrente contaCorrente, TipoMovimentacao tipo) {
        return new Movimentacao(contaCorrente.getId(), LocalDateTime.now(), tipo, this.valor);
    }
}
