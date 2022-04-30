package br.com.fit.investimento.controller;

import br.com.fit.investimento.dto.InvestimentoDTO;
import br.com.fit.investimento.dto.SaldoDTO;
import br.com.fit.investimento.exception.ContaCorrenteServiceException;
import br.com.fit.investimento.exception.InvestimentoNotFoundException;
import br.com.fit.investimento.model.ContaCorrente;
import br.com.fit.investimento.service.InvestimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/investimentos")
@RequiredArgsConstructor
public class InvestimentoController {

    private final InvestimentoService investimentoService;
    private final ContaCorrente contaCorrente = new ContaCorrente(ContaCorrente.ID_CONTA_CORRENTE_PADRAO);

    @PostMapping
    public ResponseEntity<?> credito(@RequestBody InvestimentoDTO investimentoDTO) throws ContaCorrenteServiceException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new InvestimentoDTO(investimentoService.salvar(investimentoDTO.converter(contaCorrente))));
    }

    @GetMapping
    public ResponseEntity<?> getInvestimentos() {
        return ResponseEntity.ok(investimentoService.findAllByIdContaCorrente(contaCorrente.getId())
                .stream()
                .map(InvestimentoDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/saldo")
    public ResponseEntity<SaldoDTO> getSaldo() {
        return ResponseEntity.ok(new SaldoDTO(investimentoService.getSaldo(contaCorrente.getId())));
    }

    @GetMapping("/{id}/resgatar")
    public ResponseEntity<?> resgatar(@PathVariable long id) throws InvestimentoNotFoundException, ContaCorrenteServiceException {
        investimentoService.resgatar(id);
        return ResponseEntity.ok().build();
    }
}
