package fatec.es3.livraria.model;

import java.util.ArrayList;

public class Livro extends DomainEntity {
    private String titulo;
    private String sinopse;
    private String dimensoes;
    private String cod_barras;
    private String isbn;
    private int ano;
    private int paginas;
    private String edicao;
    private Double valor_aquisicao;
    private Double valor_venda;
    private int quantidade;
    private boolean estado;
    private ArrayList<Autor> autores;
    private ArrayList<Categoria> categorias;
    private GrupoPrecificacao grupoPrecificacao;
    private Dominio dominio;

    public Livro(int id, String titulo, int ano, ArrayList<Autor> autores, ArrayList<Categoria> categorias,
                 GrupoPrecificacao grupo_precificacao, Dominio dominio, String isbn, int paginas, double valor_aquisicao,
                 double valor_venda, int quantidade, boolean estado, String edicao, String sinopse, String dimensoes, String cod_barras) {
        super(id);
        setTitulo(titulo);
        setAno(ano);
        setAutores(autores);
        setCategorias(categorias);
        setGrupoPrecificacao(grupo_precificacao);
        setDominio(dominio);
        setIsbn(isbn);
        setPaginas(paginas);
        setValor_aquisicao(valor_aquisicao);
        setValor_venda(valor_venda);
        setEdicao(edicao);
        setSinopse(sinopse);
        setDimensoes(dimensoes);
        setCod_barras(cod_barras);
        setQuantidade(quantidade);
        setEstado(estado);
    }

    public Livro(int id) {
        super(id);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<Autor> autor) {
        this.autores = autor;
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<Categoria> categoria) {
        this.categorias = categoria;
    }

    public Dominio getDominio() {
        return dominio;
    }

    public void setDominio(Dominio dominio) {
        this.dominio = dominio;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public Double getValor_aquisicao() {
        return valor_aquisicao;
    }

    public void setValor_aquisicao(double valor_aquisicao) {
        this.valor_aquisicao = valor_aquisicao;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(String dimensoes) {
        this.dimensoes = dimensoes;
    }

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public GrupoPrecificacao getGrupoPrecificacao() {
        return grupoPrecificacao;
    }

    public void setGrupoPrecificacao(GrupoPrecificacao grupoPrecificacao) {
        this.grupoPrecificacao = grupoPrecificacao;
    }

    public Double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
