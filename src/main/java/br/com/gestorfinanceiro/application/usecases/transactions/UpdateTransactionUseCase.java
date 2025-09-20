package br.com.gestorfinanceiro.application.usecases.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.application.exceptions.transactions.TransactionNotFoundException;
import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.Optional;


public class UpdateTransactionUseCase {
    private final TransactionRepositoryInterface repository;

    public UpdateTransactionUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }


    public void handle(UpdateTransactionDto dto) throws SQLException {
        Optional<Transaction> transaction = this.repository.getByUuid(dto.getUuid());
        if (transaction.isEmpty()){
            throw new TransactionNotFoundException(dto.getUuid().toString());
        }
        this.repository.update(dto);
    }
}
