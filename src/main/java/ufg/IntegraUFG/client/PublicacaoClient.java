package ufg.IntegraUFG.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class PublicacaoClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String BASE_URL = "http://localhost:8080/api";

    public PublicacaoClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Método para o GET /publicacoes/texto
    public String buscarFeed() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/texto"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body(); // Retorna a lista em JSON das postagens
    }

    // Método para o POST /publicacoes/{id}/curtir
    public void curtirPublicacao(Long idPublicacao) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/" + idPublicacao + "/curtir"))
                .POST(HttpRequest.BodyPublishers.noBody()) // Botão de like geralmente não envia corpo (body)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao curtir publicação: " + response.body());
        }
    }

    // Método para o POST /publicacoes/texto
    public void criarPostagem(Long idAutor, String conteudo) throws Exception {
        String json = objectMapper.writeValueAsString(Map.of(
                "idAutor", idAutor,
                "conteudo", conteudo
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/texto"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao criar postagem: " + response.body());
        }
    }

    // Método para o GET /publicacoes/evento
    public String buscarEventos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/evento"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao buscar eventos: " + response.body());
        }
        return response.body();
    }

    // Método para o POST /publicacoes/evento
    public void criarEvento(Long idAutor, String titulo, String descricao, String dataEvento, String local) throws Exception {
        // Garantir formato LocalDateTime adicionando T00:00:00 se não estiver presente
        String formattedDate = dataEvento;
        if (dataEvento != null && !dataEvento.contains("T")) {
            formattedDate = dataEvento + "T00:00:00";
        }
        
        String json = objectMapper.writeValueAsString(Map.of(
                "idAutor", idAutor,
                "titulo", titulo,
                "descricao", descricao,
                "dataEvento", formattedDate,
                "local", local
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/publicacoes/evento"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new RuntimeException("Erro ao criar evento: " + response.body());
        }
    }
}