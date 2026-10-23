package br.com.api.produtos.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "lb_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descricao;

    // Construtor vazio (obrigatório para o JPA/Spring)
    public Categoria() {
    }

    // Construtor com argumentos
    public Categoria(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}