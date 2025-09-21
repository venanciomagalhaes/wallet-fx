package br.com.gestorfinanceiro.application.repositories.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.dtos.transactions.GetAllInIntervalDto;
import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryInterface {

    public Optional<Transaction> getByUuid(UUID uuid) throws SQLException;
    public List<Transaction> getAllInMonthPeriod() throws SQLException;
    public List<Transaction> getAllInInterval(GetAllInIntervalDto dto) throws SQLException;
    public void create(CreateTransactionDto dto) throws SQLException;
    public void update(UpdateTransactionDto dto) throws SQLException;
    public void delete(UUID uuid) throws SQLException;

}
