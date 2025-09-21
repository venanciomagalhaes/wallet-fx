package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.dtos.transactions.CreateTransactionDto;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;

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
