package br.com.gestorfinanceiro.application.repositories.transactions;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.dtos.transactions.GetAllInIntervalDto;
import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryInterface {

     Optional<Transaction> getByUuid(UUID uuid) throws SQLException;
     List<Transaction> getAllInMonthPeriod() throws SQLException;
     List<Transaction> getAllInInterval(GetAllInIntervalDto dto) throws SQLException;
     void create(CreateTransactionDto dto) throws SQLException;
     void update(UpdateTransactionDto dto) throws SQLException;
     void delete(UUID uuid) throws SQLException;

}
