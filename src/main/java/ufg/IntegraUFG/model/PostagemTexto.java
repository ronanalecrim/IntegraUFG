package ufg.IntegraUFG.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="postagem_texto")
public class PostagemTexto extends Publicacao{
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    public PostagemTexto(Long id, Usuario autor, String conteudo) {
        super(id, autor);
        this.conteudo = conteudo;
    }

    public PostagemTexto() {

    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}

