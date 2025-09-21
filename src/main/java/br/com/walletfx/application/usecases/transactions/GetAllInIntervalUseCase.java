package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.dtos.transactions.GetAllInIntervalDto;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.List;

public class GetAllInIntervalUseCase {
    private final TransactionRepositoryInterface repository;

    public GetAllInIntervalUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Transaction> handle(GetAllInIntervalDto dto) throws SQLException {
        return this.repository.getAllInInterval(dto);
    }

}
