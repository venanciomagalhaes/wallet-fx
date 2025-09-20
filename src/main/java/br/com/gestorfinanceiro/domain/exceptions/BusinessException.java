package br.com.gestorfinanceiro.domain.exceptions;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
