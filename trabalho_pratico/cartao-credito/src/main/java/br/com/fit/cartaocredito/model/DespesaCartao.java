package br.com.fit.cartaocredito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_despesa_cartao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class DespesaCartao {

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

    private boolean paga;
}
