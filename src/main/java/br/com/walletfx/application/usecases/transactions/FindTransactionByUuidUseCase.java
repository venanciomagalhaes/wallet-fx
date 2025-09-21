package br.com.walletfx.application.usecases.transactions;

import br.com.walletfx.application.exceptions.transactions.TransactionNotFoundException;
import br.com.walletfx.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.walletfx.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class FindTransactionByUuidUseCase {

    private final TransactionRepositoryInterface repository;

    public FindTransactionByUuidUseCase(TransactionRepositoryInterface repository) {
        this.repository = repository;
    }

    public  Optional<Transaction> handle(UUID uuid) throws SQLException {
        Optional<Transaction> transaction = this.repository.getByUuid(uuid);
        if (transaction.isEmpty()){
            throw new TransactionNotFoundException(uuid.toString());
        }
        return  transaction;
    }

}
