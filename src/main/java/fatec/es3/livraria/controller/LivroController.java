package fatec.es3.livraria.controller;

import fatec.es3.livraria.model.DomainEntity;
import fatec.es3.livraria.model.Livro;
import fatec.es3.livraria.fachada.LivroFachada;
import fatec.es3.livraria.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livraria")
public class LivroController {
    Usuario usuario = new Usuario(1, "ADMIN", "123");

    @GetMapping(value="/livros")
    public ResponseEntity<?> selectEntities() {
        List<DomainEntity> livros = LivroFachada.getInstance().selectEntities();
        if (livros == null) {
            return new ResponseEntity<>("Erro.", HttpStatus.NOT_FOUND);
        } else
        if (livros.size() == 0) {
            return new ResponseEntity<>("Não há livros cadastrados ainda.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping(value="/livro")
    public ResponseEntity<?> selectEntity(@RequestParam Integer id) {
        try {
            DomainEntity livro = LivroFachada.getInstance().selectEntity(id);
            if (livro == null) {
                return new ResponseEntity<>("Livro não encontrado com o ID fornecido.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(livro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Livro não encontrado com o ID fornecido.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/livro")
    public ResponseEntity<String> insertEntity(@RequestBody Livro livro) {
        String response = LivroFachada.getInstance().insertEntity(livro);
        if (response.contains("sucesso")) {
            return new ResponseEntity<>("Livro inserido com sucesso!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Erro ao inserir livro: \n" + response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/livro")
    public ResponseEntity<String> updateEntity(@RequestBody Livro livro) {
        String response = LivroFachada.getInstance().updateEntity(livro);
        if (response.contains("sucesso")) {
            return new ResponseEntity<>("Livro atualizado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erro ao atualizar livro:  \n" + response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<String> deleteEntity(@PathVariable Integer id) {
        String response = LivroFachada.getInstance().deleteEntity(id);
        if (response.contains("sucesso")) {
            return new ResponseEntity<>("Livro excluído com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erro ao excluir livro:  \n" + response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/livro/ativar/{id}")
    public ResponseEntity<String> activateLivro(@PathVariable Integer id, @RequestBody String motivo) {
        String response = LivroFachada.getInstance().activateLivro(id, usuario, motivo);
        if (response.contains("sucesso")) {
            return new ResponseEntity<>("Livro ativado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erro ao ativar livro:  \n" + response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/livro/inativar/{id}")
    public ResponseEntity<String> inactivateLivro(@PathVariable Integer id, @RequestBody String motivo) {
        String response = LivroFachada.getInstance().inactivateLivro(id, usuario, motivo, "Inativação Manual");
        if (response.contains("sucesso")) {
            return new ResponseEntity<>("Livro inativado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Erro ao inativar livro:  \n" + response, HttpStatus.BAD_REQUEST);
        }
    }
}
