package br.com.api.produtos.controle;

import br.com.api.produtos.modelo.Cliente;
import br.com.api.produtos.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteControle {

    @Autowired
    private ClienteRepositorio repositorio;

    @GetMapping
    public List<Cliente> listar() {
        return repositorio.findAll();
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repositorio.save(cliente);
    }
}