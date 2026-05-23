package ufg.IntegraUFG.model;

import java.time.LocalDateTime;

public class Comentario implements Interagivel{
    private Long id;
    private String texto;
    private Usuario autor;
    private Publicacao publicacao;
    private LocalDateTime dataComentario;
    private int curtidas;

    public Comentario(Long id, String texto, Usuario autor, Publicacao publicacao) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.publicacao = publicacao;
        this.dataComentario = LocalDateTime.now();
        this.curtidas = 0;
    }

    @Override
    public void curtir() {
        this.curtidas++;
    }

    @Override
    public int getTotalCurtidas() {
        return this.curtidas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public LocalDateTime getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(LocalDateTime dataComentario) {
        this.dataComentario = dataComentario;
    }

    public int getCurtidas() {
        return curtidas;
    }
    
}
