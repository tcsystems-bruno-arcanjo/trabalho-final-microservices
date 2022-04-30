package br.com.fit.contacorrente.service;

import br.com.fit.contacorrente.exception.SaldoInsuficienteException;
import br.com.fit.contacorrente.model.Movimentacao;
import br.com.fit.contacorrente.enums.TipoMovimentacao;
import br.com.fit.contacorrente.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    public double getSaldo(long idContaCorrente) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAllByIdContaCorrente(idContaCorrente);

        if (CollectionUtils.isEmpty(movimentacoes)) {
            return 0;
        }

        double saldo = 0.0;

        for (Movimentacao movimentacao : movimentacoes) {
            if (movimentacao.getTipo().equals(TipoMovimentacao.DEBITO)) {
                saldo -= movimentacao.getValor();
            } else if (movimentacao.getTipo().equals(TipoMovimentacao.CREDITO)) {
                saldo += movimentacao.getValor();
            } else {
                if (log.isErrorEnabled()) {
                    log.error("Movimentacao invalida: " + movimentacao.getTipo());
                }
            }
        }

        return saldo;
    }

    public synchronized void salvar(Movimentacao entidade) throws SaldoInsuficienteException {
        double saldo = getSaldo(entidade.getIdContaCorrente());

        if (entidade.getTipo().equals(TipoMovimentacao.DEBITO) && saldo < entidade.getValor()) {
            throw new SaldoInsuficienteException();
        }

        movimentacaoRepository.save(entidade);
    }
}
