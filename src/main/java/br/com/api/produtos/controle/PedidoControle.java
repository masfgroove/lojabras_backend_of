package br.com.api.produtos.controle;

import br.com.api.produtos.modelo.Pedido;
import br.com.api.produtos.servico.PedidoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoControle {

    @Autowired
    private PedidoServico servico;

    @GetMapping
    public List<Pedido> listar() {
        return servico.listar();
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return servico.criarPedido(pedido);
    }
}