package br.com.fit.investimento.service;

import br.com.fit.investimento.dto.ContaCorrenteOperacao;
import br.com.fit.investimento.dto.MovimentacaoDTO;
import br.com.fit.investimento.exception.ContaCorrenteServiceException;
import br.com.fit.investimento.exception.InvestimentoNotFoundException;
import br.com.fit.investimento.model.Investimento;
import br.com.fit.investimento.proxy.ContaCorrenteProxy;
import br.com.fit.investimento.repository.InvestimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class InvestimentoService {

    @Value("${CONTA_SERVICE_URL:http://localhost:8000}")
    private String contaServiceUrl;

    private final InvestimentoRepository investimentoRepository;
    private final ContaCorrenteProxy contaCorrenteProxy;

    public List<Investimento> findAllByIdContaCorrente(long idContaCorrente) {
        return investimentoRepository.findAllByIdContaCorrenteAndResgatadoFalse(idContaCorrente);
    }

    public synchronized Investimento salvar(Investimento entidade) throws ContaCorrenteServiceException {
        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(entidade.getValor());
        ContaCorrenteOperacao contaCorrenteOperacao = new ContaCorrenteOperacao(
                contaServiceUrl.concat("/contas/debito"),
                contaServiceUrl.concat("/contas/credito"),
                movimentacaoDTO,
                (response) -> investimentoRepository.save(entidade));

        contaCorrenteProxy.post(contaCorrenteOperacao);

        return entidade;
    }

    public double getSaldo(long idContaCorrente) {
        List<Investimento> investimentos = investimentoRepository.findAllByIdContaCorrenteAndResgatadoFalse(idContaCorrente);

        if (CollectionUtils.isEmpty(investimentos)) {
            return 0;
        }

        return investimentos.stream().collect(Collectors.summingDouble(Investimento::getValor));
    }

    public void resgatar(long id) throws InvestimentoNotFoundException, ContaCorrenteServiceException {
        Investimento investimento = investimentoRepository.findById(id).orElseThrow(InvestimentoNotFoundException::new);

        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(investimento.getValor());
        ContaCorrenteOperacao contaCorrenteOperacao = new ContaCorrenteOperacao(
                contaServiceUrl.concat("/contas/credito"),
                contaServiceUrl.concat("/contas/debito"),
                movimentacaoDTO,
                (response) -> investimentoRepository.save(investimento.withResgatado(true)));

        contaCorrenteProxy.post(contaCorrenteOperacao);
    }
}
