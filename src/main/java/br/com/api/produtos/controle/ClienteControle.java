package br.com.api.produtos.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.api.produtos.modelo.Cliente;
import br.com.api.produtos.repositorio.ClienteRepositorio;

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

    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody Cliente clienteLogin) {
        try {
            // Busca o cliente pelo e-mail usando o repositório correto
            Cliente clienteExistente = repositorio.findByEmail(clienteLogin.getEmail());
            
            if (clienteExistente == null) {
                return ResponseEntity.status(401).body("E-mail não encontrado.");
            }

            // Valida a senha
            if (!clienteExistente.getSenha().equals(clienteLogin.getSenha())) {
                return ResponseEntity.status(401).body("Senha incorreta.");
            }

            // Remove a senha antes de retornar os dados por segurança
            clienteExistente.setSenha(null);
            return ResponseEntity.ok(clienteExistente);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro no servidor ao realizar login: " + e.getMessage());
        }
    }
}