package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.exceptions.transactions.TransactionNotFoundException;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class DeleteTransactionUseCase {

    private final TransactionRepositoryInterface repository;

    public DeleteTransactionUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public void handle(UUID uuid) throws SQLException {
        Optional<Transaction> transaction = this.repository.getByUuid(uuid);
        if (transaction.isEmpty()){
            throw new TransactionNotFoundException(uuid.toString());
        }
        this.repository.delete(uuid);
    }
}
