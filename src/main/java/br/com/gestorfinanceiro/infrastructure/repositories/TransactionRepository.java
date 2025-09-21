package br.com.gestorfinanceiro.infrastructure.repositories;

import br.com.gestorfinanceiro.application.dtos.transactions.CreateTransactionDto;
import br.com.gestorfinanceiro.application.dtos.transactions.GetAllInIntervalDto;
import br.com.gestorfinanceiro.application.dtos.transactions.UpdateTransactionDto;
import br.com.gestorfinanceiro.application.repositories.transactions.TransactionRepositoryInterface;
import br.com.gestorfinanceiro.domain.entities.Transaction;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public List<Transaction> getAllInMonthPeriod() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions " +
                "WHERE date BETWEEN (strftime('%s', 'now', 'start of month') * 1000) AND " +
                "(strftime('%s', 'now', 'start of month', '+1 month') * 1000 - 1);";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            transactions.add(mapResultSetToTransaction(rs));
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllInInterval(GetAllInIntervalDto dto) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE date BETWEEN ? AND ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        long startMillis = dto.getInitialDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long endMillis = dto.getFinallDate()
                .plusDays(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli() - 1;

            stmt.setLong(1, startMillis);
            stmt.setLong(2, endMillis);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        return transactions;
    }


    @Override
    public void create(CreateTransactionDto dto) throws SQLException {
        String sql = "INSERT INTO transactions (uuid, name, value, date, is_installments, remaining_installments) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, dto.getName());
            stmt.setBigDecimal(3, dto.getValue());

            LocalDate localDate = dto.getDate();
            Date sqlDate = Date.valueOf(localDate);
            stmt.setDate(4, sqlDate);

            stmt.setInt(5, dto.isInstallments() ? 1 : 0);
            stmt.setInt(6, dto.getRemainingInstallments());

            stmt.executeUpdate();
        }
    }


    @Override
    public void update(UpdateTransactionDto dto) throws SQLException {
        String sql = "UPDATE transactions SET name = ?, value = ?, date = ?, is_installments = ?, remaining_installments = ?, updated_at = CURRENT_TIMESTAMP WHERE uuid = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, dto.getName());
        stmt.setBigDecimal(2, dto.getValue());

        LocalDate localDate = dto.getDate();
        Date sqlDate = Date.valueOf(localDate);
        stmt.setDate(3, sqlDate);

        stmt.setInt(4, dto.isInstallments() ? 1 : 0);
        stmt.setInt(5, dto.getRemainingInstallments());
        stmt.setString(6, dto.getUuid().toString());
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
                rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
                rs.getInt("is_installments") == 1,
                rs.getInt("remaining_installments"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
