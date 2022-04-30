package br.com.fit.contacorrente.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tb_conta_corrente")
@Data
@NoArgsConstructor
public class ContaCorrente {

    public static final long ID_CONTA_CORRENTE_PADRAO = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    public ContaCorrente(Long id) {
        this.id = id;
    }
}
