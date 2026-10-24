package br.com.api.produtos.controle;

import br.com.api.produtos.modelo.Produto;
import br.com.api.produtos.servico.ProdutoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoControle {

    @Autowired
    private ProdutoServico servico;

    @GetMapping
    public List<Produto> listar() {
        return servico.listar();
    }

    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return servico.cadastrar(produto);
    }
}