package fatec.es3.livraria.model;

import java.sql.Timestamp;

public class Categoria extends DomainEntity {
    private String nome;
    private String descricao;
    public Categoria(int id, String nome, String descricao) {
        super(id);
        setNome(nome);
        setDescricao(descricao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
