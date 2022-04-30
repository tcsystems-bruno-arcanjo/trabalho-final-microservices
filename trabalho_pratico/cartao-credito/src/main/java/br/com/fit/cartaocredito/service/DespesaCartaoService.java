package br.com.fit.cartaocredito.service;

import br.com.fit.cartaocredito.dto.ContaCorrenteOperacao;
import br.com.fit.cartaocredito.dto.DespesaCartaoDTO;
import br.com.fit.cartaocredito.dto.MovimentacaoDTO;
import br.com.fit.cartaocredito.exception.ContaCorrenteServiceException;
import br.com.fit.cartaocredito.model.DespesaCartao;
import br.com.fit.cartaocredito.proxy.ContaCorrenteProxy;
import br.com.fit.cartaocredito.repository.DespesaCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespesaCartaoService {

    @Value("${CONTA_SERVICE_URL:http://localhost:8000}")
    private String contaServiceUrl;

    private final DespesaCartaoRepository despesaCartaoRepository;
    private final ContaCorrenteProxy contaCorrenteProxy;

    public void salvar(DespesaCartao entidade) {
        despesaCartaoRepository.save(entidade);
    }

    public List<DespesaCartaoDTO> getFatura(Long idContaCorrente) {
        return despesaCartaoRepository.findByIdContaCorrenteAndPagaFalse(idContaCorrente)
                .stream()
                .map(DespesaCartaoDTO::new)
                .collect(Collectors.toList());
    }

    public void pagarFatura(Long idContaCorrente) throws ContaCorrenteServiceException {
        List<DespesaCartao> despesasNaoPagas = despesaCartaoRepository.findByIdContaCorrenteAndPagaFalse(idContaCorrente);
        Double valorTotalDespesas = despesasNaoPagas.stream().collect(Collectors.summingDouble(DespesaCartao::getValor));

        MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO(valorTotalDespesas);
        ContaCorrenteOperacao contaCorrenteOperacao = new ContaCorrenteOperacao(
                contaServiceUrl.concat("/contas/debito"),
                contaServiceUrl.concat("/contas/credito"),
                movimentacaoDTO,
                (response) -> {
                    despesasNaoPagas.forEach((despesaCartao -> {
                        despesaCartao.setPaga(true);
                        despesaCartaoRepository.save(despesaCartao);
                    }));
                });

        contaCorrenteProxy.post(contaCorrenteOperacao);
    }
}
