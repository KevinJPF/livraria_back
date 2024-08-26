package fatec.es3.livraria.dao;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.GrupoPrecificacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoPrecificacaoDAO implements IDAO {
    private static final GrupoPrecificacaoDAO instance = new GrupoPrecificacaoDAO();

    private GrupoPrecificacaoDAO() {
        // Construtor privado para evitar instanciamento
    }

    public static GrupoPrecificacaoDAO getInstance() {return instance;}

    @Override
    public int insert(DomainEntity entidade) {
        GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) entidade;
        String sql = "INSERT INTO gruposprecificacao (nome, lucro_min, lucro_max) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, grupoPrecificacao.getNome());
            stmt.setDouble(2, grupoPrecificacao.getLucro_min());
            stmt.setDouble(3, grupoPrecificacao.getLucro_max());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return (int) id;
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String update(DomainEntity entidade) {
        GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) entidade;
        String sql = "UPDATE gruposprecificacao SET nome = ?, lucro_min = ?, lucro_max = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, grupoPrecificacao.getNome());
            stmt.setDouble(2, grupoPrecificacao.getLucro_min());
            stmt.setDouble(3, grupoPrecificacao.getLucro_max());
            stmt.setInt(4, grupoPrecificacao.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return "erro";
            } else {
                return "sucesso";
            }
        } catch (SQLException e) {
            return "erro";
        }
    }

    @Override
    public String delete(int id) {
        String sql = "DELETE FROM gruposprecificacao WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return "Não foi possível encontrar um grupo de precificação com o ID fornecido.";
            } else {
                return "sucesso";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<DomainEntity> select() {
        List<DomainEntity> grupos = new ArrayList<>();
        String sql = "SELECT * FROM gruposprecificacao";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("lucro_min"),
                        rs.getDouble("lucro_max")
                );
                grupos.add(grupoPrecificacao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public DomainEntity select(int id) {
            String sql = "SELECT * FROM gruposprecificacao WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new GrupoPrecificacao(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("lucro_min"),
                        rs.getDouble("lucro_max")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
