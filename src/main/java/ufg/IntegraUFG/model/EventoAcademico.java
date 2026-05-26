package ufg.IntegraUFG.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_academicos")
public class EventoAcademico extends Publicacao{
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataEvento;

    private String local;

    public EventoAcademico(Long id, Usuario autor, String titulo, String descricao, LocalDateTime dataEvento, String local) {
        super(id, autor);
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
        this.local = local;
    }

    public EventoAcademico() {

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

