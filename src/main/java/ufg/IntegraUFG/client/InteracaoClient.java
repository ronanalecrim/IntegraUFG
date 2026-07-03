package ufg.IntegraUFG.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class InteracaoClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "http://localhost:8080/api";

    public InteracaoClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String listarComentarios(Long idPublicacao) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/" + idPublicacao + "/comentarios"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao buscar comentários: " + response.body());
        }
        return response.body();
    }

    public void adicionarComentario(Long idPublicacao, Long idAutor, String texto) throws Exception {
        String json = objectMapper.writeValueAsString(Map.of(
                "idAutor", idAutor,
                "texto", texto
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/" + idPublicacao + "/comentarios"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao adicionar comentário: " + response.body());
        }
    }

    public void curtirComentario(Long idComentario) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/comentarios/" + idComentario + "/curtir"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao curtir comentário: " + response.body());
        }
    }
}
