package br.com.sistemaac_dao;

import br.com.sistemaac_conexao.Conexao;
import br.com.sistemaac_modelo.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO RESPONSÁVEL PELAS OPERAÇÕES CRUD E FAZER A PONTE ENTRE A INTERFACE JAVA E O MYSQL

public class AlunoDAO {

    // SALVA OS DADOS DO DEVIDO ALUNO NO BANCO DE DADOS
    
    public void salvar(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO Aluno (RGM, Nome, Data_de_Nascimento, CPF, Email, Endereco, Municipio, UF, Celular) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getRgm());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getDataDeNascimento());
            stmt.setString(4, aluno.getCpf());
            stmt.setString(5, aluno.getEmail());
            stmt.setString(6, aluno.getEndereco());
            stmt.setString(7, aluno.getMunicipio());
            stmt.setString(8, aluno.getUf());
            stmt.setString(9, aluno.getCelular());
            stmt.executeUpdate();
        }
    }
// CONSULTA DADOS DO ALUNO NO BANCO DE DADOS
    
    public Aluno consultar(String rgm) throws SQLException {
        String sql = "SELECT * FROM Aluno WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                        rs.getString("RGM"),
                        rs.getString("Nome"),
                        rs.getString("Data_de_Nascimento"),
                        rs.getString("CPF"),
                        rs.getString("Email"),
                        rs.getString("Endereco"),
                        rs.getString("Municipio"),
                        rs.getString("UF"),
                        rs.getString("Celular")
                    );
                }
            }
        }
        return null;
    }

    //ALTERA DADOS DOS ALUNOS NO BANCO DADOS
    
    public void alterar(Aluno aluno) throws SQLException {
        String sql = "UPDATE Aluno SET Nome = ?, Data_de_Nascimento = ?, CPF = ?, Email = ?, Endereco = ?, Municipio = ?, UF = ?, Celular = ? WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataDeNascimento());
            stmt.setString(3, aluno.getCpf());
            stmt.setString(4, aluno.getEmail());
            stmt.setString(5, aluno.getEndereco());
            stmt.setString(6, aluno.getMunicipio());
            stmt.setString(7, aluno.getUf());
            stmt.setString(8, aluno.getCelular());
            stmt.setString(9, aluno.getRgm());
            stmt.executeUpdate();
        }
    }
// EXCLUSÃO DE DADOS DO ALUNO NO BANCO DE DADOS
    
    public void excluir(String rgm) throws SQLException {
        String sql = "DELETE FROM Aluno WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.executeUpdate();
        }
    }
}
