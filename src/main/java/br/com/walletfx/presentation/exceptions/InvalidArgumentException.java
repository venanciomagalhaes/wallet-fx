package br.com.walletfx.presentation.exceptions;

import br.com.walletfx.domain.exceptions.BusinessException;

public class InvalidArgumentException extends BusinessException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
