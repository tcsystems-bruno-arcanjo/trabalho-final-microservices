package br.com.fit.cartaocredito.dto;

import br.com.fit.cartaocredito.model.DespesaCartao;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Data
public class DespesaCartaoDTO {

    private String horario;
    private double valor;

    public DespesaCartaoDTO(DespesaCartao despesaCartao) {
        this.horario = despesaCartao.getHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.valor = despesaCartao.getValor();
    }

    public DespesaCartao converter(long idContaCorrente) {
        DespesaCartao despesaCartao = new DespesaCartao();
        despesaCartao.setIdContaCorrente(idContaCorrente);
        despesaCartao.setHorario(LocalDateTime.now());
        despesaCartao.setValor(this.valor);

        return despesaCartao;
    }
}
