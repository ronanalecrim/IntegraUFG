package ufg.IntegraUFG.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class UsuarioClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "http://localhost:8080/api/usuarios";

    public UsuarioClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Método para o POST /login
    public String fazerLogin(String email, String senha) throws Exception {
        // Cria o JSON rapidamente
        String json = objectMapper.writeValueAsString(Map.of(
                "email", email,
                "senha", senha
        ));

        // Monta a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .header("Content-Type", "application/json") // Avisa a API que estamos mandando JSON
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Envia e pega a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body(); // Retorna os dados do usuário
        } else {
            throw new RuntimeException("Falha no login: Credenciais incorretas.");
        }
    }

    // Método para o POST /cadastrar
    public String cadastrarUsuario(String nome, String email, String senha, String curso) throws Exception {
        String json = objectMapper.writeValueAsString(Map.of(
                "nome", nome,
                "emailInstitucional", email,
                "senha", senha,
                "curso", curso
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/cadastrar"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201 || response.statusCode() == 200) {
            return response.body(); // Sucesso! Retorna os dados normalmente.
        } else {
            // Se der erro (ex: e-mail sem @ufg), lança a exceção com a mensagem do backend.
            throw new RuntimeException(response.body());
        }
    }

    // Método para buscar Perfil (GET)
    public String buscarPerfil(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao buscar perfil: " + response.body());
        }
        return response.body();
    }

    // Método para atualizar Perfil (PUT)
    public String atualizarPerfil(Long id, String nome, String curso, String senha) throws Exception {
        String json = objectMapper.writeValueAsString(Map.of(
                "nome", nome,
                "curso", curso,
                "senha", senha
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao atualizar perfil: " + response.body());
        }
        return response.body();
    }

    // Método para excluir Conta (DELETE)
    public void excluirConta(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao excluir conta: " + response.body());
        }
    }
}