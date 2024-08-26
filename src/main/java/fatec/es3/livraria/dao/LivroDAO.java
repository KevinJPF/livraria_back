package fatec.es3.livraria.dao;

import fatec.es3.livraria.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements IDAO {
    private static final LivroDAO instance = new LivroDAO();

    private LivroDAO() {
        // Construtor privado para evitar instanciamento
    }

    public static LivroDAO getInstance() {return instance;}

    @Override
    public int insert(DomainEntity entidade) {
        Livro livro = (Livro) entidade;
        String sql = "INSERT INTO livros (titulo, ano, grupo_precificacao_id, dominio_id, isbn, paginas, valor_aquisicao, " +
                "edicao, sinopse, dimensoes, cod_barras, valor_venda, quantidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAno());
            stmt.setInt(3, livro.getGrupoPrecificacao().getId());
            stmt.setInt(4, livro.getDominio().getId());
            stmt.setString(5, livro.getIsbn());
            stmt.setInt(6, livro.getPaginas());
            stmt.setDouble(7, livro.getValor_aquisicao());
            stmt.setString(8, livro.getEdicao());
            stmt.setString(9, livro.getSinopse());
            stmt.setString(10, livro.getDimensoes());
            stmt.setString(11, livro.getCod_barras());
            stmt.setDouble(12, livro.getValor_venda());
            stmt.setInt(13, livro.getQuantidade());
            stmt.setBoolean(14, true);

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
        Livro livro = (Livro) entidade;
        String sql = "UPDATE livros SET titulo = ?, ano = ?, grupo_precificacao_id = ?, dominio_id = ?, isbn = ?, paginas = ?, " +
                "valor_aquisicao = ?, edicao = ?, sinopse = ?, dimensoes = ?, cod_barras = ?, valor_venda = ?, quantidade = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAno());
            stmt.setInt(3, livro.getGrupoPrecificacao().getId());
            stmt.setInt(4, livro.getDominio().getId());
            stmt.setString(5, livro.getIsbn());
            stmt.setInt(6, livro.getPaginas());
            stmt.setDouble(7, livro.getValor_aquisicao());
            stmt.setString(8, livro.getEdicao());
            stmt.setString(9, livro.getSinopse());
            stmt.setString(10, livro.getDimensoes());
            stmt.setString(11, livro.getCod_barras());
            stmt.setDouble(12, livro.getValor_venda());
            stmt.setInt(13, livro.getQuantidade());
            stmt.setInt(14, livro.getId());
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

    public String updateEstadoLivro(boolean estado_novo, int id) {
        String sql = "UPDATE livros SET estado = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, estado_novo);
            stmt.setInt(2, id);
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
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return "Não foi possível encontrar um livro com o ID fornecido.";
            } else {
                return "sucesso";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public List<DomainEntity> select() {
        List<DomainEntity> livros = new ArrayList<>();
        String sql = "SELECT l.*, g.nome AS grupo_precificacao_nome, g.lucro_min, g.lucro_max, d.editora, d.fornecedor " +
                "FROM livros l " +
                "LEFT JOIN gruposprecificacao g ON g.id = l.grupo_precificacao_id " +
                "LEFT JOIN dominios d ON d.id = l.dominio_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int livroId = rs.getInt("id");

                // Buscar autores
                ArrayList<Autor> autores = getAutoresByLivroId(conn, livroId);

                // Buscar categorias
                ArrayList<Categoria> categorias = getCategoriasByLivroId(conn, livroId);

                Livro livro = new Livro(
                        livroId,
                        rs.getString("titulo"),
                        rs.getInt("ano"),
                        autores,
                        categorias,
                        new GrupoPrecificacao(rs.getInt("grupo_precificacao_id"), rs.getString("grupo_precificacao_nome"), rs.getDouble("lucro_min"), rs.getDouble("lucro_max")),
                        new Dominio(rs.getInt("dominio_id"), rs.getString("editora"), rs.getString("fornecedor")),
                        rs.getString("isbn"),
                        rs.getInt("paginas"),
                        rs.getDouble("valor_aquisicao"),
                        rs.getDouble("valor_venda"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("estado"),
                        rs.getString("edicao"),
                        rs.getString("sinopse"),
                        rs.getString("dimensoes"),
                        rs.getString("cod_barras")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    @Override
    public DomainEntity select(int id) {
        String sql = "SELECT l.*, g.nome AS grupo_precificacao_nome, g.lucro_min, g.lucro_max, d.editora, d.fornecedor " +
                "FROM livros l " +
                "LEFT JOIN gruposprecificacao g ON g.id = l.grupo_precificacao_id " +
                "LEFT JOIN dominios d ON d.id = l.dominio_id " +
                "WHERE l.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, id);
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Buscar autores
                ArrayList<Autor> autores = getAutoresByLivroId(conn, rs.getInt("id"));

                // Buscar categorias
                ArrayList<Categoria> categorias = getCategoriasByLivroId(conn, rs.getInt("id"));

                return new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("ano"),
                        autores,
                        categorias,
                        new GrupoPrecificacao(rs.getInt("grupo_precificacao_id"), rs.getString("grupo_precificacao_nome"), rs.getDouble("lucro_min"), rs.getDouble("lucro_max")),
                        new Dominio(rs.getInt("dominio_id"), rs.getString("editora"), rs.getString("fornecedor")),
                        rs.getString("isbn"),
                        rs.getInt("paginas"),
                        rs.getDouble("valor_aquisicao"),
                        rs.getDouble("valor_venda"),
                        rs.getInt("quantidade"),
                        rs.getBoolean("estado"),
                        rs.getString("edicao"),
                        rs.getString("sinopse"),
                        rs.getString("dimensoes"),
                        rs.getString("cod_barras")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String insertLivroAutor(int livro_id, ArrayList<Autor> autores) {
        for (Autor autor : autores) {
            String sql = "INSERT INTO livros_autores (livro_id, autor_id) VALUES (?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, livro_id);
                stmt.setInt(2, autor.getId());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        return "erro";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "erro";
            }
        }
        return "sucesso";
    }

    public String insertLivroCategoria(int livro_id, ArrayList<Categoria> categorias) {
        for (Categoria categoria : categorias) {
            String sql = "INSERT INTO livros_categorias (livro_id, categoria_id) VALUES (?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, livro_id);
                stmt.setInt(2, categoria.getId());

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        return "erro";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "erro";
            }
        }
        return "sucesso";
    }

    public String deleteLivroAutores(int livro_id, ArrayList<Autor> autores_id) {
            String sql = "DELETE FROM livros_autores WHERE livro_id = ? AND autor_id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                int affectedRows = 0;

                for (Autor autor : autores_id) {
                    stmt.setInt(1, livro_id);
                    stmt.setInt(2, autor.getId());
                    affectedRows += stmt.executeUpdate();
                }

                if (affectedRows == 0) {
                    return "erro";
                } else {
                    return "sucesso";
                }
            } catch (SQLException e) {
                return "erro";
            }
    }

    public String deleteLivroCategorias(int livro_id, ArrayList<Categoria> categorias_id) {
        String sql = "DELETE FROM livros_categorias WHERE livro_id = ? AND categoria_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int affectedRows = 0;

            for (Categoria categoria : categorias_id) {
                stmt.setInt(1, livro_id);
                stmt.setInt(2, categoria.getId());
                affectedRows += stmt.executeUpdate();
            }

            if (affectedRows == 0) {
                return "erro";
            } else {
                return "sucesso";
            }
        } catch (SQLException e) {
            return "erro";
        }
    }

    public ArrayList<Autor> getAutoresByLivroId(Connection conn, int livroId) throws SQLException {
        ArrayList<Autor> autores = new ArrayList<>();
        String sql = "SELECT a.id, a.nome FROM autores a " +
                "JOIN livros_autores la ON a.id = la.autor_id " +
                "WHERE la.livro_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    autores.add(new Autor(rs.getInt("id"), rs.getString("nome")));
                }
            }
        }
        return autores;
    }

    public ArrayList<Categoria> getCategoriasByLivroId(Connection conn, int livroId) throws SQLException {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT c.id, c.nome, c.descricao FROM categorias c " +
                "JOIN livros_categorias lc ON c.id = lc.categoria_id " +
                "WHERE lc.livro_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categorias.add(new Categoria(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao")));
                }
            }
        }
        return categorias;
    }

}
