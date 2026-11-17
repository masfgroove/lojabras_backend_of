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

    public List<Produto> listar() {
        return repositorio.findAll();
    }

    public Produto obterPorId(Long id) {
        Optional<Produto> produto = repositorio.findById(id);
        return produto.orElse(null);
    }

    public Produto cadastrar(Produto produto) {
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

    public Produto atualizar(Long id, Produto produtoNovo) {
        Optional<Produto> produtoOpt = repositorio.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produtoExistente = produtoOpt.get();
            produtoExistente.setTitulo(produtoNovo.getTitulo());
            produtoExistente.setDescricao(produtoNovo.getDescricao());
            produtoExistente.setPreco(produtoNovo.getPreco());
            produtoExistente.setQuantidadeEstoque(produtoNovo.getQuantidadeEstoque());
            produtoExistente.setUrlImg(produtoNovo.getUrlImg());
            produtoExistente.setTamanhosDisponiveis(produtoNovo.getTamanhosDisponiveis());
            produtoExistente.setCoresDisponiveis(produtoNovo.getCoresDisponiveis());
            produtoExistente.setBadge(produtoNovo.getBadge());
            
            if (produtoNovo.getCategoria() != null && produtoNovo.getCategoria().getId() != null) {
                Optional<Categoria> categoriaOpt = categoriaRepositorio.findById(produtoNovo.getCategoria().getId());
                categoriaOpt.ifPresent(produtoExistente::setCategoria);
            }

            return repositorio.save(produtoExistente);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return true;
        }
        return false;
    }
}