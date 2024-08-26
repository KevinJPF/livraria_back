package fatec.es3.livraria.controller;

import fatec.es3.livraria.fachada.DominioFachada;
import fatec.es3.livraria.fachada.LogFachada;
import fatec.es3.livraria.model.DomainEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/livraria")
public class DominioController {

    @GetMapping(value="/dominios")
    public ResponseEntity<?> selectEntities() {
        Map<String, List<DomainEntity>> dominios = DominioFachada.getInstance().selectGambiarra();
        if (dominios == null) {
            return new ResponseEntity<>("Erro.", HttpStatus.NOT_FOUND);
        } else
        if (dominios.size() == 0) {
            return new ResponseEntity<>("Não há dominios cadastrados ainda.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dominios, HttpStatus.OK);
    }
}
