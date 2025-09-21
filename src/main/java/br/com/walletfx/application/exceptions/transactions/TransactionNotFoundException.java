package br.com.walletfx.application.exceptions.transactions;

import br.com.walletfx.domain.exceptions.BusinessException;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(String uuid) {
        super("Não foi possível encontrar a transação: " + uuid);
    }
}
