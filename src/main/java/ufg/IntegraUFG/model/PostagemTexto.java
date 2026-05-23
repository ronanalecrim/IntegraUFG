package ufg.IntegraUFG.model;

public class PostagemTexto extends Publicacao{
    private String conteudo;

    public PostagemTexto(Long id, Usuario autor, String conteudo) {
        super(id, autor);
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}

