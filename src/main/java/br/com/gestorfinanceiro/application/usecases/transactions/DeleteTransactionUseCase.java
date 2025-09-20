package br.com.gestorfinanceiro.application.usecases.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.application.exceptions.transactions.TransactionNotFoundException;
import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.util.Optional;
import java.util.UUID;

public class DeleteTransactionUseCase {

    private final TransactionRepositoryInterface repository;

    public DeleteTransactionUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public void handle(UUID uuid){
        Optional<Transaction> transaction = this.repository.getByUuid(uuid);
        if (transaction.isEmpty()){
            throw new TransactionNotFoundException(uuid.toString());
        }
        this.repository.delete(uuid);
    }
}
