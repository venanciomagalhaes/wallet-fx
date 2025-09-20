package br.com.gestorfinanceiro.application.usecases.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.sql.SQLException;

public class CreateTransactionUseCase {

    private final TransactionRepositoryInterface repository;

    public CreateTransactionUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public void handle(CreateTransactionDto dto) throws SQLException {
        this.repository.create(dto);
    }

}
