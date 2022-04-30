package br.com.fit.contacorrente.controller;

import br.com.fit.contacorrente.dto.MensagemDTO;
import br.com.fit.contacorrente.exception.SaldoInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> saldoInsuficienteExceptionHandler(SaldoInsuficienteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO("Saldo insuficiente para a operacao"));
    }
}
