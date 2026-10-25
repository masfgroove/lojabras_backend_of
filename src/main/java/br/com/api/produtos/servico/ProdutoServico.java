package br.com.api.produtos.servico;

import br.com.api.produtos.modelo.Categoria;
import br.com.api.produtos.modelo.Produto;
import br.com.api.produtos.repositorio.CategoriaRepositorio;
import br.com.api.produtos.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        // Valida se a categoria foi informada e se existe no banco
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Optional<Categoria> categoriaOpt = categoriaRepositorio.findById(produto.getCategoria().getId());
            if (categoriaOpt.isPresent()) {
                produto.setCategoria(categoriaOpt.get());
            } else {
                throw new RuntimeException("Categoria com ID " + produto.getCategoria().getId() + " não encontrada!");
            }
        } else {
            produto.setCategoria(null);
        }
        return repositorio.save(produto);
    }
}