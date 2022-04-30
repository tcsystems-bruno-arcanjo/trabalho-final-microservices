package br.com.fit.contacorrente.model;

import br.com.fit.contacorrente.enums.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_movimentacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_conta_corrente")
    private long idContaCorrente;

    @JoinColumn(name = "id_conta_corrente", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ContaCorrente contaCorrente;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @Column(nullable = false)
    private double valor;

    public Movimentacao(long idContaCorrente, LocalDateTime horario, TipoMovimentacao tipo, double valor) {
        this.idContaCorrente = idContaCorrente;
        this.horario = horario;
        this.tipo = tipo;
        this.valor = valor;
    }
}
