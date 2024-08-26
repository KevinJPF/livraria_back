package fatec.es3.livraria.fachada;

import fatec.es3.livraria.dao.LivroDAO;
import fatec.es3.livraria.dao.LogDAO;
import fatec.es3.livraria.model.*;
import fatec.es3.livraria.strategy.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroFachada implements IFachada {
    private static final LivroFachada instance = new LivroFachada();

    private LivroFachada() {
        // Construtor privado para evitar instanciamento
    }

    public static LivroFachada getInstance() {return instance;}

    @Override
    public List<DomainEntity> selectEntities() {
        List<DomainEntity> livros = LivroDAO.getInstance().select();

        for (DomainEntity entidade : livros) {
            if (ValidarQuantidadeEstoque.getInstance().process(entidade).equals("Desativar")) {
                Usuario usuario = new Usuario(1);
                inactivateLivro(entidade.getId(), usuario, "Fora de mercado.", "Inativação Automática");
            }
        }

        return livros;
    }

    @Override
    public DomainEntity selectEntity(Integer id) {
        return LivroDAO.getInstance().select(id);
    }

    @Override
    public String insertEntity(DomainEntity entidadeLivro) {
        if (entidadeLivro instanceof Livro) {
            Livro livro = (Livro) entidadeLivro;
            String returnString = "";

            returnString += ValidarCamposObrigatorios.getInstance().process(entidadeLivro);
            returnString += ValidarAutoresLivro.getInstance().process(entidadeLivro);
            returnString += ValidarCategoriasLivro.getInstance().process(entidadeLivro);
            returnString += ValidarMargemLucro.getInstance().process(entidadeLivro);
            returnString += ValidarExistencia.getInstance().process(entidadeLivro);

            if (returnString.isEmpty()) {
                int id = LivroDAO.getInstance().insert(livro);

                if (id > 0) {
                    if (!LivroDAO.getInstance().insertLivroCategoria(id, livro.getCategorias()).equals("sucesso")) {
                        LivroDAO.getInstance().delete(id);
                        returnString +=  "Erro ao inserir categorias.\n";
                    }

                    if (!LivroDAO.getInstance().insertLivroAutor(id, livro.getAutores()).equals("sucesso")) {
                        LivroDAO.getInstance().delete(id);
                        returnString +=  "Erro ao inserir autores.\n";
                    }

                    returnString += GerarLog.getInstance().process(id, 1);
                } else {
                    returnString += "O livro não foi adicionado no banco de dados.\n";
                }
            }
            return returnString.isEmpty() ? "Livro inserido com sucesso." : returnString;
        } else {
            return "A entidadeLivro fornecida não é um objeto Livro válido.";
        }
    }

    @Override
    public String updateEntity(DomainEntity entidadeLivro) {
        if (entidadeLivro instanceof Livro) {
            Livro livro = (Livro) entidadeLivro;
            String returnString = "";

            returnString += ValidarCamposObrigatorios.getInstance().process(entidadeLivro);
            returnString += ValidarAutoresLivro.getInstance().process(entidadeLivro);
            returnString += ValidarCategoriasLivro.getInstance().process(entidadeLivro);
            returnString += ValidarMargemLucro.getInstance().process(entidadeLivro);

            if (returnString.isEmpty()) {
                returnString += GerarLog.getInstance().process(livro);

                returnString += !LivroDAO.getInstance().update(livro).equals("sucesso") ? "Erro ao atualizar o livro.\n" : "";

                returnString += GerenciarRelacoesLivroCategoriasAutores.getInstance().process(livro);
            }
            return returnString.isEmpty() ? "Livro atualizado com sucesso." : returnString;
        } else {
            return "A entidadeLivro fornecida não é um objeto Livro válido.";
        }
    }

    public String activateLivro(int livro_id, DomainEntity entidadeUsuario, String motivo) {
        String returnString = "";

        returnString += LivroDAO.getInstance().updateEstadoLivro(true, livro_id).equals("sucesso") ? "" : "Erro ao atualizar o Livro.";

        returnString += GerarLog.getInstance().process(livro_id, entidadeUsuario.getId(), motivo, null, true);

        return returnString.isEmpty() ? "sucesso" : "Livro não foi ativado.";
    }

    public String inactivateLivro(int livro_id, DomainEntity entidadeUsuario, String motivo, String cat_inativacao) {
        String returnString = "";

        returnString += LivroDAO.getInstance().updateEstadoLivro(false, livro_id).equals("sucesso") ? "" : "Erro ao atualizar o Livro.";

        returnString += GerarLog.getInstance().process(livro_id, entidadeUsuario.getId(), motivo, cat_inativacao, false);

        return returnString.isEmpty() ? "sucesso" : "Livro não foi inativado.";
    }

    @Override
    public String deleteEntity(Integer id) {
        return LivroDAO.getInstance().delete(id);
    }
}
