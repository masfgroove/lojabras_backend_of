package br.com.api.produtos.servico;

import br.com.api.produtos.modelo.Categoria;
import br.com.api.produtos.modelo.Produto;
import br.com.api.produtos.repositorio.CategoriaRepositorio;
import br.com.api.produtos.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio repositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    // Listar todos os produtos
    public List<Produto> listar() {
        return repositorio.findAll();
    }

    // Cadastrar ou Alterar produto
    public Produto cadastrar(Produto produto) {
        // Se a categoria foi informada com ID, garantimos que ela existe/é gerenciada pelo JPA
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepositorio.findById(produto.getCategoria().getId()).orElse(null);
            produto.setCategoria(categoria);
        }
        return repositorio.save(produto);
    }
}