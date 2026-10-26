package br.com.api.produtos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.api.produtos.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    // Adicione esta linha para o Spring reconhecer a busca por e-mail:
    public Cliente findByEmail(String email);
}