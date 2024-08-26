package fatec.es3.livraria.fachada;

import fatec.es3.livraria.model.DomainEntity;

import java.util.List;

public interface IFachada {
    public List<DomainEntity> selectEntities();
    public DomainEntity selectEntity(Integer id);
    public String insertEntity(DomainEntity entity);
    public String updateEntity(DomainEntity entity);
    public String deleteEntity(Integer id);
}
