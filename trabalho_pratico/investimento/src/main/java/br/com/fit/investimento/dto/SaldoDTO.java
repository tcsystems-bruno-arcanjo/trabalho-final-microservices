package br.com.fit.investimento.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class SaldoDTO {

    private final double valor;
}
