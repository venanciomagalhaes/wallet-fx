package br.com.gestorfinanceiro.application.repositories.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryInterface {

    public Optional<Transaction> getByUuid(UUID uuid);
    public List<Transaction> getAll();
    public void create(CreateTransactionDto dto);
    public void update(UpdateTransactionDto dto);
    public void delete(UUID uuid);

}
