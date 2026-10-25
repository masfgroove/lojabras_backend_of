package br.com.api.produtos.servico;

import br.com.api.produtos.modelo.Cliente;
import br.com.api.produtos.modelo.OrderItem;
import br.com.api.produtos.modelo.Pedido;
import br.com.api.produtos.modelo.Produto;
import br.com.api.produtos.repositorio.ClienteRepositorio;
import br.com.api.produtos.repositorio.PedidoRepositorio;
import br.com.api.produtos.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoServico {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Pedido> listar() {
        return pedidoRepositorio.findAll();
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // 1. Valida e vincula o Cliente pelo ID
        if (pedido.getCliente() != null && pedido.getCliente().getId() != null) {
            Cliente cliente = clienteRepositorio.findById(pedido.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + pedido.getCliente().getId()));
            pedido.setCliente(cliente);
        } else {
            throw new RuntimeException("O pedido precisa estar associado a um cliente válido!");
        }

        BigDecimal valorTotal = BigDecimal.ZERO;

        // 2. Processa os itens do pedido, calcula o total e dá baixa no estoque
        if (pedido.getItens() != null) {
            for (OrderItem item : pedido.getItens()) {
                Produto produto = produtoRepositorio.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado ID: " + item.getProduto().getId()));

                // Verifica estoque
                if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                    throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getTitulo() + 
                                               " (Disponível: " + produto.getQuantidadeEstoque() + ")");
                }

                // Dá baixa no estoque do produto
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
                produtoRepositorio.save(produto);

                // Configura o item do pedido
                item.setPrecoUnitario(produto.getPreco());
                item.setItemPedido(pedido); // Ajuste se o método no OrderItem for setPedido

                // Se o seu método no OrderItem.java se chama setPedido, use:
                item.setPedido(pedido);

                // Calcula subtotal e acumula no valor total
                BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                valorTotal = valorTotal.add(subtotal);
            }
        }

        pedido.setValorTotal(valorTotal);
        if (pedido.getStatus() == null || pedido.getStatus().isEmpty()) {
            pedido.setStatus("PENDING");
        }

        return pedidoRepositorio.save(pedido);
    }
}