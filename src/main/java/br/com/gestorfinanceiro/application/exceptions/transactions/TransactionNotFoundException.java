package br.com.gestorfinanceiro.application.exceptions.transactions;

import br.com.gestorfinanceiro.domain.exceptions.BusinessException;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(String uuid) {
        super("Não foi possível encontrar a transação: " + uuid);
    }
}
