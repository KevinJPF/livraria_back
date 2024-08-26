package fatec.es3.livraria.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Log extends DomainEntity {
    private Timestamp data_alteracao;
    private String tipo_alteracao;
    private String motivo;
    private String cat_inativacao;
    private int estoque_ant;
    private int estoque_novo;
    private boolean estado_ant;
    private boolean estado_novo;
    private Livro livro;
    private Usuario usuario;

    public Log(int id, Timestamp data_alteracao, String tipo_alteracao, String motivo, String cat_inativacao, int estoque_ant,
               int estoque_novo, boolean estado_ant, boolean estado_novo, Livro livro, Usuario usuario) {
        super(id);
        setData_alteracao(data_alteracao);
        setTipo_alteracao(tipo_alteracao);
        setMotivo(motivo);
        setCat_inativacao(cat_inativacao);
        setEstoque_ant(estoque_ant);
        setEstoque_novo(estoque_novo);
        setEstado_ant(estado_ant);
        setEstado_novo(estado_novo);
        setLivro(livro);
        setUsuario(usuario);
    }

    public Log(String tipo_alteracao, String motivo, String cat_inativacao,
               boolean estado_ant, boolean estado_novo, int livro_id, int usuario_id) {
        setData_alteracao(Timestamp.valueOf(LocalDateTime.now()));
        setTipo_alteracao(tipo_alteracao);
        setMotivo(motivo);
        setCat_inativacao(cat_inativacao);
        setEstado_ant(estado_ant);
        setEstado_novo(estado_novo);
        setLivro(new Livro(livro_id));
        setUsuario(new Usuario(usuario_id));
    }

    public Log(String tipo_alteracao, String motivo, int estoque_ant,
               int estoque_novo, int livro_id, int usuario_id) {
        setData_alteracao(Timestamp.valueOf(LocalDateTime.now()));
        setTipo_alteracao(tipo_alteracao);
        setMotivo(motivo);
        setEstoque_ant(estoque_ant);
        setEstoque_novo(estoque_novo);
        setLivro(new Livro(livro_id));
        setUsuario(new Usuario(usuario_id));
    }

    public Log(String tipo_alteracao, String motivo, int livro_id, int usuario_id) {
        setData_alteracao(Timestamp.valueOf(LocalDateTime.now()));
        setTipo_alteracao(tipo_alteracao);
        setMotivo(motivo);
        setLivro(new Livro(livro_id));
        setUsuario(new Usuario(usuario_id));
    }

    public Timestamp getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(Timestamp data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public String getTipo_alteracao() {
        return tipo_alteracao;
    }

    public void setTipo_alteracao(String tipo_alteracao) {
        this.tipo_alteracao = tipo_alteracao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCat_inativacao() {
        return cat_inativacao;
    }

    public void setCat_inativacao(String cat_inativacao) {
        this.cat_inativacao = cat_inativacao;
    }

    public int getEstoque_ant() {
        return estoque_ant;
    }

    public void setEstoque_ant(int estoque_ant) {
        this.estoque_ant = estoque_ant;
    }

    public int getEstoque_novo() {
        return estoque_novo;
    }

    public void setEstoque_novo(int estoque_novo) {
        this.estoque_novo = estoque_novo;
    }

    public boolean isEstado_ant() {
        return estado_ant;
    }

    public void setEstado_ant(boolean estado_ant) {
        this.estado_ant = estado_ant;
    }

    public boolean isEstado_novo() {
        return estado_novo;
    }

    public void setEstado_novo(boolean estado_novo) {
        this.estado_novo = estado_novo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}