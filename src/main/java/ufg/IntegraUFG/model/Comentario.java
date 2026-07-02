package ufg.IntegraUFG.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="comentarios")
public class Comentario implements Interagivel{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "texto", nullable = false, length = 255)
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacao_id", nullable = false)
    private Publicacao publicacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataComentario = LocalDateTime.now();

    @Column(name = "curtidas")
    private int curtidas = 0;

    public Comentario(Long id, String texto, Usuario autor, Publicacao publicacao) {
        this.id = id;
        this.texto = texto;
        this.autor = autor;
        this.publicacao = publicacao;
        this.dataComentario = LocalDateTime.now();
        this.curtidas = 0;
    }

    public Comentario() {

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
