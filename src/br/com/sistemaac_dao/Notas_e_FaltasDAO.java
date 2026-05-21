package br.com.sistemaac_dao;

import br.com.sistemaac_conexao.Conexao;
import br.com.sistemaac_modelo.Notas_e_Faltas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Notas_e_FaltasDAO {

    public void salvar(Notas_e_Faltas nf) throws SQLException {
        String sql = "INSERT INTO Notas_e_Faltas (RGM, Disciplina, Semestre, Nota, Faltas) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nf.getRgm());
            stmt.setString(2, nf.getDisciplina());
            stmt.setString(3, nf.getSemestre());
            stmt.setDouble(4, nf.getNota());
            stmt.setInt(5, nf.getFaltas());
            stmt.executeUpdate();
        }
    }

    public List<Notas_e_Faltas> listarpeloRgm(String rgm) throws SQLException {
        List<Notas_e_Faltas> lista = new ArrayList<>();
        String sql = "SELECT * FROM Notas_e_Faltas WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notas_e_Faltas nf = new Notas_e_Faltas(
                        rs.getString("RGM"),
                        rs.getString("Disciplina"),
                        rs.getString("Semestre"),
                        rs.getDouble("Nota"),
                        rs.getInt("Faltas")
                    );
                    nf.setIdNota(rs.getInt("id_nota"));
                    lista.add(nf);
                }
            }
        }
        return lista;
    }
    
    public void excluirpeloRgm(String rgm) throws SQLException {
        String sql = "DELETE FROM Notas_e_Faltas WHERE RGM = ?";
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.executeUpdate();
        }
    }
}