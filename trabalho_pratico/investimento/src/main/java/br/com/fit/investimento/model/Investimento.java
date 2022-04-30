package br.com.fit.investimento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_investimento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class Investimento {

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
    private double valor;

    private boolean resgatado;

    public Investimento(long idContaCorrente, LocalDateTime horario, double valor) {
        this.idContaCorrente = idContaCorrente;
        this.horario = horario;
        this.valor = valor;
    }
}
