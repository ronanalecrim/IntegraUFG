package ufg.IntegraUFG.view;

public class SessaoUsuario {
    public static Long usuarioLogadoId;
    public static String usuarioLogadoNome;
    public static String usuarioLogadoCurso;
    
    public static void limparSessao() {
        usuarioLogadoId = null;
        usuarioLogadoNome = null;
        usuarioLogadoCurso = null;
    }
}
