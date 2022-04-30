package br.com.fit.investimento.controller;

import br.com.fit.investimento.dto.MensagemDTO;
import br.com.fit.investimento.exception.ContaCorrenteServiceException;
import br.com.fit.investimento.exception.InvestimentoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ContaCorrenteServiceException.class)
    public ResponseEntity<?> contaCorrenteServiceExceptionHandler(ContaCorrenteServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getLocalizedMessage()));
    }

    @ExceptionHandler(InvestimentoNotFoundException.class)
    public ResponseEntity<?> investimentoNotFoundExceptionHandler(InvestimentoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO("Investimento nao encontrado"));
    }
}