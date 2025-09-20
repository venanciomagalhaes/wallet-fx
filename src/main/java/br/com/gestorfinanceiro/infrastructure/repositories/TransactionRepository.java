package br.com.gestorfinanceiro.infrastructure.repositories;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;

import java.sql.*;
import java.util.*;

public class TransactionRepository implements TransactionRepositoryInterface {

    private final Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Transaction> getByUuid(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE uuid = ? LIMIT 1";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, uuid.toString());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return Optional.of(mapResultSetToTransaction(rs));
        }

        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            transactions.add(mapResultSetToTransaction(rs));
        }
        return transactions;
    }

    @Override
    public void create(CreateTransactionDto dto) throws SQLException {
        String sql = "INSERT INTO transactions (uuid, name, value, is_installments, remaining_installments) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, UUID.randomUUID().toString());
        stmt.setString(2, dto.getName());
        stmt.setBigDecimal(3, dto.getValue());
        stmt.setInt(4, dto.isInstallments() ? 1 : 0);
        stmt.setInt(5, dto.getRemainingInstallments());
        stmt.executeUpdate();
    }

    @Override
    public void update(UpdateTransactionDto dto) throws SQLException {
        String sql = "UPDATE transactions SET name = ?, value = ?, is_installments = ?, remaining_installments = ?, updated_at = CURRENT_TIMESTAMP WHERE uuid = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, dto.getName());
        stmt.setBigDecimal(2, dto.getValue());
        stmt.setInt(3, dto.isInstallments() ? 1 : 0);
        stmt.setInt(4, dto.getRemainingInstallments());
        stmt.setString(5, dto.getUuid().toString());
        stmt.executeUpdate();
    }

    @Override
    public void delete(UUID uuid) throws SQLException {
        String sql = "DELETE FROM transactions WHERE uuid = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, uuid.toString());
        stmt.executeUpdate();
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getLong("id"),
                UUID.fromString(rs.getString("uuid")),
                rs.getString("name"),
                rs.getBigDecimal("value"),
                rs.getInt("is_installments") == 1,
                rs.getInt("remaining_installments"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
