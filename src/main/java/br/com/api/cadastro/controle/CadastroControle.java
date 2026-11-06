package br.com.api.cadastro.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.api.cadastro.modelo.Cadastro;
import br.com.api.cadastro.servico.CadastroServico;

@RestController
@CrossOrigin(origins = "*")
public class CadastroControle {

    @Autowired
    private CadastroServico servico;

    @GetMapping("/cadastros")
    public Iterable<Cadastro> listar() {
        return servico.listar();
    }

    @PostMapping("/cadastros")
    public Cadastro cadastrar(@RequestBody Cadastro obj) {
        return servico.cadastrar(obj);
    }

    @PutMapping("/cadastros")
    public Cadastro alterar(@RequestBody Cadastro obj) {
        return servico.cadastrar(obj);
    }

    @DeleteMapping("/cadastros/{id}")
    public void remover(@PathVariable Long id) {
        servico.remover(id);
    }
}