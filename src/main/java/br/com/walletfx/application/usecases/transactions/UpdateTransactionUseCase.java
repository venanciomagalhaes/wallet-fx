package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.dtos.transactions.UpdateTransactionDto;
import br.com.walletfx.application.exceptions.transactions.TransactionNotFoundException;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.domain.entities.Transaction;

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
