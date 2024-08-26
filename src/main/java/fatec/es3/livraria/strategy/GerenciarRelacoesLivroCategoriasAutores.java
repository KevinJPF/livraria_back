package fatec.es3.livraria.strategy;

import fatec.es3.livraria.dao.LivroDAO;
import fatec.es3.livraria.model.Autor;
import fatec.es3.livraria.model.Categoria;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GerenciarRelacoesLivroCategoriasAutores implements IStrategy {
    private static final GerenciarRelacoesLivroCategoriasAutores instance = new GerenciarRelacoesLivroCategoriasAutores();

    private GerenciarRelacoesLivroCategoriasAutores() {
        // Construtor privado para evitar instanciamento
    }

    public static GerenciarRelacoesLivroCategoriasAutores getInstance() {return instance;}

    @Override
    public String process(DomainEntity entidade) {
        Livro livroNovo = (Livro) entidade;
        int id = livroNovo.getId();
        Livro livroAtual = (Livro) LivroDAO.getInstance().select(id);
        String returnString = "";

        // Obter as categorias que devem ser removidas
        ArrayList<Categoria> categoriasParaRemover = livroAtual.getCategorias().stream()
                .filter(categoriaAtual -> livroNovo.getCategorias().stream()
                        .noneMatch(categoria -> categoria.getId().equals(categoriaAtual.getId())))
                .collect(Collectors.toCollection(ArrayList::new));

        // Obter as categorias que devem ser adicionadas
        ArrayList<Categoria> categoriasParaAdicionar = livroNovo.getCategorias().stream()
                .filter(categoria -> livroAtual.getCategorias().stream()
                        .noneMatch(categoriaAtual -> categoriaAtual.getId().equals(categoria.getId())))
                .collect(Collectors.toCollection(ArrayList::new));

        // Obter os autores que devem ser removidos
        ArrayList<Autor> autoresParaRemover = livroAtual.getAutores().stream()
                .filter(autorAtual -> livroNovo.getAutores().stream()
                        .noneMatch(autor -> autor.getId().equals(autorAtual.getId())))
                .collect(Collectors.toCollection(ArrayList::new));

        // Obter os autores que devem ser adicionados
        ArrayList<Autor> autoresParaAdicionar = livroNovo.getAutores().stream()
                .filter(autor -> livroAtual.getAutores().stream()
                        .noneMatch(autorAtual -> autorAtual.getId().equals(autor.getId())))
                .collect(Collectors.toCollection(ArrayList::new));

        if (!categoriasParaRemover.isEmpty()) {
            if (!LivroDAO.getInstance().deleteLivroCategorias(id, categoriasParaRemover).equals("sucesso")) {
                returnString +=  "Erro ao deletar categorias.\n";
            }
        }

        if (!categoriasParaAdicionar.isEmpty()) {
            if (!LivroDAO.getInstance().insertLivroCategoria(id, categoriasParaAdicionar).equals("sucesso")) {
                returnString +=  "Erro ao atualizar categorias.\n";
            }
        }

        if (!autoresParaRemover.isEmpty()) {
            if (!LivroDAO.getInstance().deleteLivroAutores(id, autoresParaRemover).equals("sucesso")) {
                returnString +=  "Erro ao deletar autores.\n";
            }
        }

        if (!autoresParaAdicionar.isEmpty()) {
            if (!LivroDAO.getInstance().insertLivroAutor(id, autoresParaAdicionar).equals("sucesso")) {
                returnString += "Erro ao atualizar autores.\n";
            }
        }

        return returnString;
    }
}
