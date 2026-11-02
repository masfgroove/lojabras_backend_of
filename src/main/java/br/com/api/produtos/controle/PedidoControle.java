package br.com.api.produtos.controle;

import br.com.api.produtos.modelo.*;
import br.com.api.produtos.repositorio.*;
import br.com.api.produtos.servico.MercadoPagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoControle {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
private MercadoPagoService mercadoPagoService;



    @PostMapping("/pagar")
    public ResponseEntity<?> gerarPagamento(@RequestBody Map<String, Object> payload) {
        try {
            String linkPagamento = mercadoPagoService.criarPreferenciaPagamento(payload);
            return ResponseEntity.ok(Map.of("initPoint", linkPagamento));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity criarPedido(@RequestBody Map<String, Object> payload) {
        try {
            // 1. Extrair ID do cliente
            Long clienteId = Long.valueOf(payload.get("clienteId").toString());
            Cliente cliente = clienteRepositorio.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            // 2. Salvar o Endereço de Entrega
            Map<String, String> endMap = (Map<String, String>) payload.get("enderecoEntrega");
            Endereco endereco = new Endereco();
            endereco.setCep(endMap.get("cep"));
            endereco.setRua(endMap.get("rua"));
            endereco.setNumero(endMap.get("numero"));
            endereco.setBairro(endMap.get("bairro"));
            endereco.setCidade(endMap.get("cidade"));
            endereco.setEstado(endMap.get("estado"));
            Endereco enderecoSalvo = enderecoRepositorio.save(endereco);

            // 3. Criar o Pedido
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setStatus("PENDING");
            pedido.setValorTotal(new BigDecimal(payload.get("valorTotal").toString()));
            pedido.setCriadoEm(LocalDateTime.now());

            // Salva primeiro o pedido para gerar o ID
            Pedido pedidoSalvo = pedidoRepositorio.save(pedido);

            // 4. Processar e salvar os itens do pedido (OrderItem) com conversão segura
            Object itensObj = payload.get("itensPedido");
            List<OrderItem> itensPedido = new ArrayList<>();

            if (itensObj instanceof List<?>) {
                List<?> itensBrutos = (List<?>) itensObj;
                for (Object obj : itensBrutos) {
                    if (obj instanceof Map<?, ?>) {
                        Map<?, ?> itemData = (Map<?, ?>) obj;
                        
                        Long produtoId = Long.valueOf(itemData.get("produtoId").toString());
                        Produto produto = produtoRepositorio.findById(produtoId)
                                .orElseThrow(() -> new RuntimeException("Produto ID " + produtoId + " não encontrado"));

                        OrderItem item = new OrderItem();
                        item.setPedido(pedidoSalvo);
                        item.setProduto(produto);
                        item.setQuantidade(Integer.valueOf(itemData.get("quantidade").toString()));
                        item.setPrecoUnitario(new BigDecimal(itemData.get("preco").toString()));

                        itensPedido.add(item);
                    }
                }
            }

            // Associa os itens ao pedido e salva
            pedidoSalvo.setItens(itensPedido);
            pedidoRepositorio.save(pedidoSalvo);

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("status", "sucesso");
            resposta.put("mensagem", "Pedido gravado com sucesso no banco de dados!");
            resposta.put("pedidoId", pedidoSalvo.getId());

            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> erro = new HashMap<>();
            erro.put("status", "erro");
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}