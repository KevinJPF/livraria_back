package fatec.es3.livraria.strategy;

import fatec.es3.livraria.model.DomainEntity;

public interface IStrategy {
    public String process(DomainEntity entidade);
}
