package fatec.es3.livraria.dao;

import fatec.es3.livraria.model.*;
import fatec.es3.livraria.model.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO implements IDAO {
    private static final LogDAO instance = new LogDAO();

    private LogDAO() {
        // Construtor privado para evitar instanciamento
    }

    public static LogDAO getInstance() {return instance;}

    @Override
    public int insert(DomainEntity entidade) {
        Log log = (Log) entidade;
        String sql = "INSERT INTO logs (data_alteracao, tipo_alteracao, motivo, cat_inativacao, estoque_ant, estoque_novo, estado_ant, estado_novo, livro_id, usuario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, log.getData_alteracao());
            stmt.setString(2, log.getTipo_alteracao());
            stmt.setString(3, log.getMotivo());
            stmt.setString(4, log.getCat_inativacao());
            stmt.setInt(5, log.getEstoque_ant());
            stmt.setInt(6, log.getEstoque_novo());
            stmt.setBoolean(7, log.isEstado_ant());
            stmt.setBoolean(8, log.isEstado_novo());
            stmt.setInt(9, log.getLivro().getId());
            stmt.setInt(10, log.getUsuario().getId());

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
        Log log = (Log) entidade;
        String sql = "UPDATE logs SET data_alteracao = ?, tipo_alteracao = ?, motivo = ?, cat_inativacao = ?, " +
                "estoque_ant = ?, estoque_novo = ?, estado_ant = ?, estado_novo = ?, livro_id = ?, usuario_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, log.getData_alteracao());
            stmt.setString(2, log.getTipo_alteracao());
            stmt.setString(3, log.getMotivo());
            stmt.setString(4, log.getCat_inativacao());
            stmt.setInt(5, log.getEstoque_ant());
            stmt.setInt(6, log.getEstoque_novo());
            stmt.setBoolean(7, log.isEstado_ant());
            stmt.setBoolean(8, log.isEstado_novo());
            stmt.setInt(9, log.getLivro().getId());
            stmt.setInt(10, log.getUsuario().getId());
            stmt.setInt(11, log.getId());

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
        String sql = "DELETE FROM logs WHERE id = ?";
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
        String sql = "SELECT * FROM logs";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                // Buscar livro
                Livro livro = (Livro) LivroDAO.getInstance().select(rs.getInt("livro_id"));

                // Buscar livro
                Usuario usuario = (Usuario) UsuarioDAO.getInstance().select(rs.getInt("usuario_id"));

                Log log = new Log(
                        rs.getInt("id"),
                        rs.getTimestamp("data_alteracao"),
                        rs.getString("tipo_alteracao"),
                        rs.getString("motivo"),
                        rs.getString("cat_inativacao"),
                        rs.getInt("estoque_ant"),
                        rs.getInt("estoque_novo"),
                        rs.getBoolean("estado_ant"),
                        rs.getBoolean("estado_novo"),
                        livro,
                        usuario
                );
                grupos.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public DomainEntity select(int id) {
        String sql = "SELECT * FROM logs WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                // Buscar livro
                Livro livro = (Livro) LivroDAO.getInstance().select(rs.getInt("livro_id"));

                // Buscar livro
                Usuario usuario = (Usuario) UsuarioDAO.getInstance().select(rs.getInt("usuario_id"));

                return new Log(
                        rs.getInt("id"),
                        rs.getTimestamp("data_alteracao"),
                        rs.getString("tipo_alteracao"),
                        rs.getString("motivo"),
                        rs.getString("cat_inativacao"),
                        rs.getInt("estoque_ant"),
                        rs.getInt("estoque_novo"),
                        rs.getBoolean("estado_ant"),
                        rs.getBoolean("estado_novo"),
                        livro,
                        usuario
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DomainEntity> selectLogsByLivroID(int livro_id) {
        List<DomainEntity> grupos = new ArrayList<>();
        String sql = "SELECT * FROM logs WHERE livro_id = ? ORDER BY data_alteracao DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, livro_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                // Buscar livro
                Livro livro = (Livro) LivroDAO.getInstance().select(livro_id);

                // Buscar livro
                Usuario usuario = (Usuario) UsuarioDAO.getInstance().select(rs.getInt("usuario_id"));

                Log log = new Log(
                        rs.getInt("id"),
                        rs.getTimestamp("data_alteracao"),
                        rs.getString("tipo_alteracao"),
                        rs.getString("motivo"),
                        rs.getString("cat_inativacao"),
                        rs.getInt("estoque_ant"),
                        rs.getInt("estoque_novo"),
                        rs.getBoolean("estado_ant"),
                        rs.getBoolean("estado_novo"),
                        livro,
                        usuario
                );
                grupos.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }
}
