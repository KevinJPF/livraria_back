package fatec.es3.livraria.strategy;

import fatec.es3.livraria.dao.GrupoPrecificacaoDAO;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.GrupoPrecificacao;
import fatec.es3.livraria.model.Livro;

public class ValidarMargemLucro implements IStrategy {
    private static final ValidarMargemLucro instance = new ValidarMargemLucro();

    private ValidarMargemLucro() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarMargemLucro getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        try {
            Livro livro = (Livro) entidade;

            if (livro.getGrupoPrecificacao() == null) return "Um livro deve ter um grupo de precificação selecionado.\n";

            GrupoPrecificacao grupo = (GrupoPrecificacao) GrupoPrecificacaoDAO.getInstance().select(livro.getGrupoPrecificacao().getId());

            if (grupo == null) {
                return "Grupo no encontrado.\n";
            }

            double margemLucro = ((livro.getValor_venda() - livro.getValor_aquisicao()) / livro.getValor_aquisicao()) * 100;

            if (margemLucro > grupo.getLucro_max()) {
                return "A margem de lucro do livro está acima da margem definida pelo grupo de precificação escolhido.\n";
            } else if (margemLucro < grupo.getLucro_min()) {
                return "A margem de lucro do livro está abaixo da margem definida pelo grupo de precificação escolhido.\n";
            }

            return "";
        } catch (Exception e) {
            return "Erro: " + e.getMessage() + "\n";
        }
    }
}
