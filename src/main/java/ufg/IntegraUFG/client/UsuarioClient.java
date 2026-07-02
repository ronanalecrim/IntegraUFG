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
        return response.body();
    }
}