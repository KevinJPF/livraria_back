package fatec.es3.livraria.strategy;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;

public class ValidarAutoresLivro implements IStrategy {
    private static final ValidarAutoresLivro instance = new ValidarAutoresLivro();

    private ValidarAutoresLivro() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarAutoresLivro getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        try {
            Livro livro = (Livro) entidade;

            if (livro.getAutores() == null || livro.getAutores().isEmpty()) {
                return "O livro deve ter ao menos um autor.\n";
            }

            return "";
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }
}
