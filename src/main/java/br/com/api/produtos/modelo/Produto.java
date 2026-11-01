package br.com.api.produtos.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "lb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    private Double preco;
    
    private Integer quantidadeEstoque;
    
    // URL da imagem principal (mantendo a compatibilidade com o que já tem)
    private String urlImg;
    
    // Novos campos inspirados no Mercado Livre
    private String tamanhosDisponiveis; // Ex: "36,38,40,42,44"
    private String coresDisponiveis;     // Ex: "Azul-claro, Azul-escuro, Preto"
    private String badge;                // Ex: "NOVIDADE", "MAIS VENDIDO", "20% OFF"

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // Construtores, Getters e Setters
    public Produto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }

    public String getTamanhosDisponiveis() { return tamanhosDisponiveis; }
    public void setTamanhosDisponiveis(String tamanhosDisponiveis) { this.tamanhosDisponiveis = tamanhosDisponiveis; }

    public String getCoresDisponiveis() { return coresDisponiveis; }
    public void setCoresDisponiveis(String coresDisponiveis) { this.coresDisponiveis = coresDisponiveis; }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}