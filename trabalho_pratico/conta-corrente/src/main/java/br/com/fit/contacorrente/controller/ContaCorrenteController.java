package br.com.fit.contacorrente.controller;

import br.com.fit.contacorrente.dto.MovimentacaoDTO;
import br.com.fit.contacorrente.dto.SaldoDTO;
import br.com.fit.contacorrente.exception.SaldoInsuficienteException;
import br.com.fit.contacorrente.model.ContaCorrente;
import br.com.fit.contacorrente.enums.TipoMovimentacao;
import br.com.fit.contacorrente.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaCorrenteController {

    private final MovimentacaoService movimentacaoService;
    private final ContaCorrente contaCorrente = new ContaCorrente(ContaCorrente.ID_CONTA_CORRENTE_PADRAO);

    @PostMapping("/debito")
    public ResponseEntity<?> salvar(@RequestBody MovimentacaoDTO movimentacao) throws SaldoInsuficienteException {
        movimentacaoService.salvar(movimentacao.converter(contaCorrente, TipoMovimentacao.DEBITO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/credito")
    public ResponseEntity<?> credito(@RequestBody MovimentacaoDTO movimentacao) throws SaldoInsuficienteException {
        movimentacaoService.salvar(movimentacao.converter(contaCorrente, TipoMovimentacao.CREDITO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/saldo")
    public ResponseEntity<?> saldo() {
        return ResponseEntity.ok(new SaldoDTO(movimentacaoService.getSaldo(contaCorrente.getId())));
    }
}
