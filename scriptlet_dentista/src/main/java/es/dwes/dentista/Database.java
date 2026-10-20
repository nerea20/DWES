package es.dwes.dentista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/dentista?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv().getOrDefault("DENTISTA_DB_URL", DEFAULT_URL);
        String user = System.getenv().getOrDefault("DENTISTA_DB_USER", "user");
        String password = System.getenv().getOrDefault("DENTISTA_DB_PASSWORD", "user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("No se encontró el driver JDBC de MySQL", exception);
        }
        return DriverManager.getConnection(url, user, password);
    }
}