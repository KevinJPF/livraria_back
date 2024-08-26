package fatec.es3.livraria.strategy;

import fatec.es3.livraria.dao.LivroDAO;
import fatec.es3.livraria.dao.LogDAO;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;
import fatec.es3.livraria.model.Log;

public class GerarLog implements IStrategy {
    private static final GerarLog instance = new GerarLog();

    private GerarLog() {
        // Construtor privado para evitar instanciamento
    }

    public static GerarLog getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        Livro livro = (Livro) entidade;
        try {
            Log log = new Log("Alteração", "Livro editado.", livro.getId(), 1);
            Livro livroAtual = (Livro) LivroDAO.getInstance().select(livro.getId());

            log.setEstoque_ant(livroAtual.getQuantidade());
            log.setEstoque_novo(livro.getQuantidade());

            return salvarLog(log);
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }

    public String process(int livro_id, int usuario_id) {
        try {
            Log log = new Log("Criação", "Livro criado.", livro_id, usuario_id);

            return salvarLog(log);
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }

    public String process(int livro_id, int usuario_id, String motivo, String cat_inativacao, boolean estado_novo) {
        try {
            Log log = new Log("Estado", motivo,
                    cat_inativacao, !estado_novo, estado_novo, livro_id, usuario_id);

            return salvarLog(log);
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }

    public String process(int livro_id, int usuario_id, int estoque_ant, int estoque_novo) {
        try {
            Log log = new Log("Estoque", "Alteração de estoque.",
                    estoque_ant, estoque_novo, livro_id, usuario_id);

            return salvarLog(log);
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }

    private String salvarLog(Log log) {
        String returnString = "";
        try {
            int id = LogDAO.getInstance().insert(log);
            returnString += id > 0 ? "" : "Não foi possível inserir o Log.";
        } catch (Exception e) {
            returnString += e.getMessage();
        }

        return returnString;
    }
}
