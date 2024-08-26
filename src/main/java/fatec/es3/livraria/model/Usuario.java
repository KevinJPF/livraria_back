package fatec.es3.livraria.model;

import java.sql.Timestamp;

public class Usuario extends DomainEntity {
    private String nome;
    private String senha;

    public Usuario(int id, String nome, String senha) {
        super(id);
        setNome(nome);
        setSenha(senha);
    }

    public Usuario(int id) {
        super(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
