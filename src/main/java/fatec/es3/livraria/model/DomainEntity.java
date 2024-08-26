package fatec.es3.livraria.model;

import java.sql.Timestamp;

public abstract class DomainEntity {
    private Integer id;

    protected DomainEntity(Integer id) {
        this.id = id;
    }

    protected DomainEntity() {
    }

    public Integer getId() {
        return id;
    }
}
