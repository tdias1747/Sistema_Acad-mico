package br.com.sistemaac_dao;

import br.com.sistemaac_conexao.Conexao;
import br.com.sistemaac_modelo.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoDAO {

    public void salvarEAtualizar(Curso curso) throws SQLException {
        String sqlVerifica = "SELECT RGM FROM Curso WHERE RGM = ?";
        boolean existe = false;
        
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {
            stmtVerifica.setString(1, curso.getRgm());
            try (ResultSet rs = stmtVerifica.executeQuery()) {
                if (rs.next()) existe = true;
            }
        }

        String sql;
        if (existe) {
            sql = "UPDATE Curso SET Curso = ?, Campus = ?, Periodo = ? WHERE RGM = ?";
        } else {
            sql = "INSERT INTO Curso (Curso, Campus, Periodo, RGM) VALUES (?, ?, ?, ?)";
        }

        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getCurso());
            stmt.setString(2, curso.getCampus());
            stmt.setString(3, curso.getPeriodo());
            stmt.setString(4, curso.getRgm());
            stmt.executeUpdate();
        }
    }

    public Curso consultar(String rgm) throws SQLException {
        String sql = "SELECT * FROM Curso WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                        rs.getString("RGM"),
                        rs.getString("Curso"),
                        rs.getString("Campus"),
                        rs.getString("Periodo")
                    );
                }
            }
        }
        return null;
    }
}
