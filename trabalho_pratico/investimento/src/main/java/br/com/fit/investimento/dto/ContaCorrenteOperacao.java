package br.com.fit.investimento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@AllArgsConstructor
@Getter
public class ContaCorrenteOperacao<T> {

    private String url;
    private String fallbackUrl;
    private Object body;
    private Consumer<T> callback;
}
