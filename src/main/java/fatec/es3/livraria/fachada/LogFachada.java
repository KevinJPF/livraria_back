package fatec.es3.livraria.fachada;

import fatec.es3.livraria.dao.LivroDAO;
import fatec.es3.livraria.dao.LogDAO;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Usuario;
import fatec.es3.livraria.strategy.ValidarQuantidadeEstoque;

import java.util.List;

public class LogFachada implements IFachada {
    private static final LogFachada instance = new LogFachada();

    private LogFachada() {
        // Construtor privado para evitar instanciamento
    }

    public static LogFachada getInstance() {return instance;}

    @Override
    public List<DomainEntity> selectEntities() {
        List<DomainEntity> logs = LogDAO.getInstance().select();

        return logs;
    }

    @Override
    public DomainEntity selectEntity(Integer id) {
        return null;
    }

    @Override
    public String insertEntity(DomainEntity entity) {
        return "";
    }

    @Override
    public String updateEntity(DomainEntity entity) {
        return "";
    }

    @Override
    public String deleteEntity(Integer id) {
        return "";
    }
}
