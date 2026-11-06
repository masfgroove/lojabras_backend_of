package br.com.api.cadastro.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.api.cadastro.modelo.Cadastro;

@Repository
public interface CadastroRepositorio extends CrudRepository<Cadastro, Long> {
    // Aqui já tens todos os métodos prontos (CRUD: save, findAll, findById, delete, etc.)
}