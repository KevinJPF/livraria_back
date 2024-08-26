package fatec.es3.livraria.dao;

import fatec.es3.livraria.fachada.DominioFachada;
import fatec.es3.livraria.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DominioDAO implements IDAO {
    private static final DominioDAO instance = new DominioDAO();

    private DominioDAO() {
        // Construtor privado para evitar instanciamento
    }

    public static DominioDAO getInstance() {return instance;}

    @Override
    public int insert(DomainEntity entidade) {
        return 0;
    }

    @Override
    public String update(DomainEntity entidade) {
        return "";
    }

    @Override
    public String delete(int id) {
        return "";
    }

    @Override
    public List<DomainEntity> select() {
        List<DomainEntity> listaRetorno = new ArrayList<>();
        List<DomainEntity> grupos = new ArrayList<>();
        List<DomainEntity> dominios = new ArrayList<>();
        List<DomainEntity> autores = new ArrayList<>();
        List<DomainEntity> categorias = new ArrayList<>();

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

        listaRetorno.addAll(grupos);

        sql = "SELECT * FROM dominios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dominio dominio = new Dominio(
                        rs.getInt("id"),
                        rs.getString("editora"),
                        rs.getString("fornecedor")
                );
                dominios.add(dominio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaRetorno.addAll(dominios);

        return listaRetorno;
    }

    @Override
    public DomainEntity select(int id) {
        return null;
    }

    public Map<String, List<DomainEntity>> selectGambiarra() {
        Map<String, List<DomainEntity>> responseMap = new HashMap<>();
        List<DomainEntity> grupos = new ArrayList<>();
        List<DomainEntity> dominios = new ArrayList<>();
        List<DomainEntity> autores = new ArrayList<>();
        List<DomainEntity> categorias = new ArrayList<>();

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

        sql = "SELECT * FROM dominios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Dominio dominio = new Dominio(
                        rs.getInt("id"),
                        rs.getString("editora"),
                        rs.getString("fornecedor")
                );
                dominios.add(dominio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM autores";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Autor autor = new Autor(
                        rs.getInt("id"),
                        rs.getString("nome")
                );
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM categorias";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        responseMap.put("grupos", grupos);
        responseMap.put("dominios", dominios);
        responseMap.put("autores", autores);
        responseMap.put("categorias", categorias);

        return responseMap;
    }
}
