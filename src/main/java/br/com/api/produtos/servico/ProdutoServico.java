package br.com.api.produtos.servico;

import br.com.api.produtos.modelo.Produto;
import br.com.api.produtos.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio repositorio;

    // Listar todos os produtos
    public List<Produto> listar() {
        return repositorio.findAll();
    }

    // Cadastrar ou Alterar produto
    public Produto cadastrar(Produto produto) {
        return repositorio.save(produto);
    }
}