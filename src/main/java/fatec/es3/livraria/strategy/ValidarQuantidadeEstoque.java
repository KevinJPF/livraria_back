package fatec.es3.livraria.strategy;

import fatec.es3.livraria.dao.LogDAO;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;
import fatec.es3.livraria.model.Log;
import fatec.es3.livraria.model.Usuario;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ValidarQuantidadeEstoque implements IStrategy {
    private static final ValidarQuantidadeEstoque instance = new ValidarQuantidadeEstoque();

    private ValidarQuantidadeEstoque() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarQuantidadeEstoque getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        Livro livro = (Livro) entidade;

        if (livro.getQuantidade() == 0 && livro.isEstado()) {
            ArrayList<DomainEntity> entidadesLog = LogDAO.getInstance().selectLogsByLivroID(livro.getId()).stream().filter(log ->
                    ((Log) log).getTipo_alteracao().equals("Alteração") || ((Log) log).getTipo_alteracao().equals("Criação")).collect(Collectors.toCollection(ArrayList::new));

            DomainEntity entidadeLog = entidadesLog.size() > 0 ? entidadesLog.get(0) : null;

            Log lastLog = entidadeLog != null ? (Log) entidadeLog : null;

            if (lastLog == null || ChronoUnit.MONTHS.between(lastLog.getData_alteracao().toLocalDateTime(), LocalDateTime.now()) >= 3) {
                return "Desativar";
            }
        }

        return "";
    }
}
