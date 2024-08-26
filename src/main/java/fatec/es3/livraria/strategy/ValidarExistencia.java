package fatec.es3.livraria.strategy;

import fatec.es3.livraria.dao.LivroDAO;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;

import java.util.List;

public class ValidarExistencia implements IStrategy {
    private static final ValidarExistencia instance = new ValidarExistencia();

    private ValidarExistencia() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarExistencia getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        try {
            String isbnLivro = ((Livro) entidade).getIsbn();

            List<DomainEntity> livros = LivroDAO.getInstance().select();

            for (DomainEntity entidadeBanco : livros) {
                Livro livro = (Livro) entidadeBanco;

                if (livro.getIsbn().equals(isbnLivro)) {
                    return "Já existe um livro com esse ISBN cadastrado.\n";
                }
            }

            return "";
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }
}
