package fatec.es3.livraria.dao;

import fatec.es3.livraria.model.DomainEntity;

import java.util.List;

interface IDAO {
    int insert(DomainEntity entidade);

    String update(DomainEntity entidade);

    String delete(int id);

    List<DomainEntity> select();

    DomainEntity select(int id);
}