package br.com.api.produtos.controle;

import br.com.api.produtos.modelo.Categoria;
import br.com.api.produtos.servico.CategoriaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaControle {

    @Autowired
    private CategoriaServico servico;

    @GetMapping
    public List<Categoria> listar() {
        return servico.listar();
    }

    @PostMapping
    public Categoria cadastrar(@RequestBody Categoria categoria) {
        return servico.cadastrar(categoria);
    }
}