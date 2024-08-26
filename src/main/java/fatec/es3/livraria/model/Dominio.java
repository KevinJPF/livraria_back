package fatec.es3.livraria.model;

import java.sql.Timestamp;

public class Dominio extends DomainEntity {
    private String editora;
    private String fornecedor;

    public Dominio(int id, String editora, String fornecedor) {
        super(id);
        setEditora(editora);
        setFornecedor(fornecedor);
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}
