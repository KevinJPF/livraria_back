package fatec.es3.livraria.controller;

import fatec.es3.livraria.fachada.LivroFachada;
import fatec.es3.livraria.fachada.LogFachada;
import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livraria")
public class LogController {

    @GetMapping(value="/logs")
    public ResponseEntity<?> selectEntities() {
        List<DomainEntity> logs = LogFachada.getInstance().selectEntities();
        if (logs == null) {
            return new ResponseEntity<>("Erro.", HttpStatus.NOT_FOUND);
        } else
        if (logs.size() == 0) {
            return new ResponseEntity<>("Não há logs cadastrados ainda.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
