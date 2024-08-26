package fatec.es3.livraria.strategy;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;

public class ValidarCamposObrigatorios implements IStrategy {
    private static final ValidarCamposObrigatorios instance = new ValidarCamposObrigatorios();

    private ValidarCamposObrigatorios() {
        // Construtor privado para evitar instanciamento
    }

    public static ValidarCamposObrigatorios getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        StringBuilder message = new StringBuilder();

        try {
            Livro livro = (Livro) entidade;

            if (!validarTitulo(livro)) {
                message.append("Título inválido.\n");
            }

            if (!validarSinopse(livro)) {
                message.append("Sinopse inválida.\n");
            }

            if (!validarDimensoes(livro)) {
                message.append("Dimensões inválidas.\n");
            }

            if (!validarCodBarras(livro)) {
                message.append("Código de barras inválido.\n");
            }

            if (!validarISBN(livro)) {
                message.append("ISBN inválido.\n");
            }

            if (!validarAno(livro)) {
                message.append("Ano inválido.\n");
            }

            if (!validarPaginas(livro)) {
                message.append("Número de páginas inválido.\n");
            }

            if (!validarEdicao(livro)) {
                message.append("Edição inválida.\n");
            }

            if (!validarValorAquisicao(livro)) {
                message.append("Valor de aquisição inválido.\n");
            }

            if (!validarValorVenda(livro)) {
                message.append("Valor de venda inválido.\n");
            }

            if (!validarQuantidade(livro)) {
                message.append("Quantidade inválida.\n");
            }

        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }

        return message.toString();
    }

    private boolean validarTitulo(Livro livro) {
        return livro.getTitulo() != null && !livro.getTitulo().trim().isEmpty();
    }

    private boolean validarSinopse(Livro livro) {
        return livro.getSinopse() != null && !livro.getSinopse().trim().isEmpty();
    }

    private boolean validarDimensoes(Livro livro) {
        return livro.getDimensoes() != null && !livro.getDimensoes().trim().isEmpty();
    }

    private boolean validarCodBarras(Livro livro) {
        return livro.getCod_barras() != null && !livro.getCod_barras().trim().isEmpty();
    }

    private boolean validarISBN(Livro livro) {
        return livro.getIsbn() != null && !livro.getIsbn().trim().isEmpty();
    }

    private boolean validarAno(Livro livro) {
        return livro.getAno() > 0;
    }

    private boolean validarPaginas(Livro livro) {
        return livro.getPaginas() > 0;
    }

    private boolean validarEdicao(Livro livro) {
        return livro.getEdicao() != null && !livro.getEdicao().trim().isEmpty();
    }

    private boolean validarValorAquisicao(Livro livro) {
        return livro.getValor_aquisicao() != null && livro.getValor_aquisicao() > 0;
    }

    private boolean validarValorVenda(Livro livro) {
        return livro.getValor_venda() != null && livro.getValor_venda() > 0;
    }

    private boolean validarQuantidade(Livro livro) {
        return livro.getQuantidade() >= 0;
    }
}
