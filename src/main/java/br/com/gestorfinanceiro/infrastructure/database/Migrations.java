package br.com.gestorfinanceiro.infrastructure.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Migrations {

    private final Connection connection;

    public Migrations(Connection connection) {
        this.connection = connection;
    }

    public void run() throws SQLException {

        String transactionTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "uuid TEXT NOT NULL UNIQUE, " +
                "name TEXT NOT NULL, " +
                "value REAL NOT NULL, " +
                "is_installments INTEGER NOT NULL, " +
                "remaining_installments INTEGER DEFAULT 0, " +
                "created_at TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TEXT DEFAULT CURRENT_TIMESTAMP" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(transactionTable);
        }
    }
}
