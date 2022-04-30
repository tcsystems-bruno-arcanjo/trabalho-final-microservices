package br.com.fit.cartaocredito.controller;

import br.com.fit.cartaocredito.dto.DespesaCartaoDTO;
import br.com.fit.cartaocredito.exception.ContaCorrenteServiceException;
import br.com.fit.cartaocredito.model.ContaCorrente;
import br.com.fit.cartaocredito.service.DespesaCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class DespesaCartaoController {

    private final DespesaCartaoService despesaCartaoService;
    private final ContaCorrente contaCorrente = new ContaCorrente(ContaCorrente.ID_CONTA_CORRENTE_PADRAO);

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DespesaCartaoDTO despesaCartaoDTO) {
        despesaCartaoService.salvar(despesaCartaoDTO.converter(contaCorrente.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/fatura")
    public ResponseEntity<?> getFatura() {
        return ResponseEntity.ok(despesaCartaoService.getFatura(contaCorrente.getId()));
    }

    @GetMapping("/pagar")
    public ResponseEntity<?> pagarFatura() throws ContaCorrenteServiceException {
        despesaCartaoService.pagarFatura(contaCorrente.getId());
        return ResponseEntity.ok().build();
    }
}
