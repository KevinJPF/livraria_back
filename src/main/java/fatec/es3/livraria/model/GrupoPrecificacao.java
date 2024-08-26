package fatec.es3.livraria.model;

public class GrupoPrecificacao extends DomainEntity {
    private String nome;
    private double lucro_min;
    private double lucro_max;

    public GrupoPrecificacao(int id, String nome, double lucro_min, double lucro_max) {
        super(id);
        setNome(nome);
        setLucro_min(lucro_min);
        setLucro_max(lucro_max);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLucro_min() {
        return lucro_min;
    }

    public void setLucro_min(double lucro_min) {
        this.lucro_min = lucro_min;
    }

    public double getLucro_max() {
        return lucro_max;
    }

    public void setLucro_max(double lucro_max) {
        this.lucro_max = lucro_max;
    }
}
