package fatec.es3.livraria.strategy;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;

public class ValidarCategoriasLivro implements IStrategy {
    private static final ValidarCategoriasLivro instance = new ValidarCategoriasLivro();

    private ValidarCategoriasLivro() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarCategoriasLivro getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        try {
            Livro livro = (Livro) entidade;

            if (livro.getCategorias() == null || livro.getCategorias().isEmpty()) {
                return "O livro deve ter ao menos uma categoria.\n";
            }

            return "";
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }
}
