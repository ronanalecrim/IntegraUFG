package ufg.IntegraUFG.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Publicacao implements Interagivel {
    private Long id;
    private Usuario autor;
    private LocalDateTime dataCriacao;
    private int curtidas;

    private ArrayList<Comentario> comentarios = new ArrayList<>();

    public Publicacao(Long id, Usuario autor) {
        this.id = id;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
