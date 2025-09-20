package br.com.gestorfinanceiro.presentation.exceptions;

import br.com.gestorfinanceiro.domain.exceptions.BusinessException;

public class InvalidArgumentException extends BusinessException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
