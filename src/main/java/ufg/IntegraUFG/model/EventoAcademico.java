package ufg.IntegraUFG.model;

import java.time.LocalDateTime;

public class EventoAcademico extends Publicacao{
    private String titulo;
    private String descricao;
    private LocalDateTime dataEvento;
    private String local;

    public EventoAcademico(Long id, Usuario autor, String titulo, String descricao, LocalDateTime dataEvento, String local) {
        super(id, autor);
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.local = local;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}

