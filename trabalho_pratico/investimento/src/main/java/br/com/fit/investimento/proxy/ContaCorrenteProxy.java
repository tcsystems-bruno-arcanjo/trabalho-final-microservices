package br.com.fit.investimento.proxy;

import br.com.fit.investimento.dto.ContaCorrenteOperacao;
import br.com.fit.investimento.dto.MensagemDTO;
import br.com.fit.investimento.exception.ContaCorrenteServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Log4j2
public class ContaCorrenteProxy {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void post(ContaCorrenteOperacao operacao) throws ContaCorrenteServiceException {
        try {
            ResponseEntity<Object> response = restTemplate.postForEntity(operacao.getUrl(), operacao.getBody(), Object.class);

            if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST) && response.hasBody()) {
                try {
                    MensagemDTO mensagemDTO = (MensagemDTO) response.getBody();

                    if (Objects.isNull(mensagemDTO)) {
                        throw new ContaCorrenteServiceException("Nao foi possivel concluir a operacao. Refaca em alguns minutos!");
                    }

                    throw new ContaCorrenteServiceException(mensagemDTO.getMensagem());
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error(e.getLocalizedMessage(), e.getCause());
                    }

                    throw new ContaCorrenteServiceException("Nao foi possivel concluir a operacao. Refaca em alguns minutos!");
                }
            }

            try {
                operacao.getCallback().accept(null);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getLocalizedMessage(), e.getCause());
                }

                restTemplate.postForEntity(operacao.getFallbackUrl(), operacao.getBody(), Object.class);
            }
        } catch (RestClientException e1) {
            if (log.isErrorEnabled()) {
                log.error(e1.getLocalizedMessage(), e1.getCause());
            }

            if (e1 instanceof HttpClientErrorException) {
                HttpClientErrorException exception = (HttpClientErrorException) e1;

                if (exception.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                    try {
                        MensagemDTO mensagemDTO = objectMapper.readValue(exception.getResponseBodyAsString(), MensagemDTO.class);
                        throw new ContaCorrenteServiceException(mensagemDTO.getMensagem());
                    } catch (Exception e2) {
                        if (log.isErrorEnabled()) {
                            log.error(e2.getLocalizedMessage(), e2.getCause());
                        }

                        throw new ContaCorrenteServiceException("Nao foi possivel concluir a operacao. Refaca em alguns minutos!");
                    }
                }
            }

            throw new ContaCorrenteServiceException("Nao foi possivel concluir a operacao. Refaca em alguns minutos!");
        }
    }
}
