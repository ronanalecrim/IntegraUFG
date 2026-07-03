package ufg.IntegraUFG.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String emailInstitucional;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String curso;

    @Column(nullable = false)
    private boolean admin = false;

    @JsonIgnore
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Publicacao> publicacoes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();

    public Usuario(Long id, String nome, String emailInstitucional, String senha, String curso) {
        this.id = id;
        this.nome = nome;
        this.emailInstitucional = emailInstitucional;
        this.senha = senha;
        this.curso = curso;
    }

    public Usuario() {

    }

    public void adicionarPublicacao(Publicacao p) {
        this.publicacoes.add(p);
        p.setAutor(this);
    }

    public void adicionarComentario(Comentario c) {
        this.comentarios.add(c);
        c.setAutor(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
