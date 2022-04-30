package br.com.fit.cartaocredito.controller;

import br.com.fit.cartaocredito.dto.MensagemDTO;
import br.com.fit.cartaocredito.exception.ContaCorrenteServiceException;
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
}