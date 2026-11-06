package br.com.api.cadastro.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.api.cadastro.modelo.Cadastro;
import br.com.api.cadastro.repositorio.CadastroRepositorio;

@Service
public class CadastroServico {

    @Autowired
    private CadastroRepositorio acao;

    // Listar todos os cadastros
    public Iterable<Cadastro> listar() {
        return acao.findAll();
    }

    // Cadastrar ou alterar
    public Cadastro cadastrar(Cadastro obj) {
        return acao.save(obj);
    }
    
    // Remover
    public void remover(Long id) {
        acao.deleteById(id);
    }
}