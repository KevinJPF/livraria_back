package fatec.es3.livraria.dao;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IDAO {
    private static final UsuarioDAO instance = new UsuarioDAO();

    private UsuarioDAO() {
        // Construtor privado para evitar instanciamento
    }

    public static UsuarioDAO getInstance() {return instance;}

    @Override
    public int insert(DomainEntity entidade) {
        Usuario usuario = (Usuario) entidade;
        String sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());

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
        Usuario usuario = (Usuario) entidade;
        String sql = "UPDATE usuarios SET nome = ?, senha = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(3, usuario.getId());
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
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return "Não foi possível encontrar um usuario com o ID fornecido.";
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
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("senha")
                );
                grupos.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public DomainEntity select(int id) {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("senha")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
