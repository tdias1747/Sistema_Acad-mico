package br.com.sistemaac_conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// LINHA QUE MOSTRA A CONEXÃO AO USUARIO MYSQL PARA TER ACESSO AS TABELAS

public class Conexao {
    
    private static final String URL = "jdbc:mysql://localhost:3206/sistemaacademico";
    private static final String USUARIO = "root";
    private static final String SENHA = ""; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do mysql não descoberto", e);
        }
    }
}
