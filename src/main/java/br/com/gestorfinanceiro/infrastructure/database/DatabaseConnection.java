package br.com.gestorfinanceiro.infrastructure.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static  String URL;
    private static Connection instance;

    private DatabaseConnection() {}

    private static void setCredentials() throws IOException {
        InputStream input = DatabaseConnection.class.getResourceAsStream("/br/com/gestorfinanceiro/db.properties") ;
        Properties prop = new Properties();
        prop.load(input);
        URL = prop.getProperty("db.url");
    }

    public static Connection getInstance() throws SQLException, IOException {
        if (instance == null){
            setCredentials();
            instance = DriverManager.getConnection(URL);
        }
        return instance;
    }

}
