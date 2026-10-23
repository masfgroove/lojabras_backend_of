package br.com.api.produtos.servico;

import br.com.api.produtos.modelo.Categoria;
import br.com.api.produtos.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServico {

    @Autowired
    private CategoriaRepositorio repositorio;

    // Listar todas as categorias
    public List<Categoria> listar() {
        return repositorio.findAll();
    }

    // Cadastrar ou Alterar categoria
    public Categoria cadastrar(Categoria categoria) {
        return repositorio.save(categoria);
    }
}