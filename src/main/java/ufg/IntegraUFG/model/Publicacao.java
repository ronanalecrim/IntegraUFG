package ufg.IntegraUFG.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="publicacoes")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publicacao implements Interagivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;

    @Column(name = "curtidas")
    private int curtidas;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    public Publicacao(Long id, Usuario autor) {
        this.id = id;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
        this.curtidas = 0;
    }

    public Publicacao() {

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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
