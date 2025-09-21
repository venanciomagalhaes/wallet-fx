package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.List;

public class GetAllTransactionsUseCase {
    private final TransactionRepositoryInterface repository;

    public GetAllTransactionsUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Transaction> handle() throws SQLException {
        return this.repository.getAllInMonthPeriod();
    }

}
