package br.com.alura.screensound.service;

import okhttp3.*;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsultaGemini {

    private static final String MODEL = "models/gemini-2.5-flash";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/" + MODEL + ":generateContent";

    private static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(Duration.ofSeconds(60))
            .readTimeout(Duration.ofSeconds(60))
            .callTimeout(Duration.ofSeconds(120))
            .retryOnConnectionFailure(true)
            .build();
    private static ObjectMapper mapper = new ObjectMapper();
    private final String apiKey;

    public ConsultaGemini() {
        this.apiKey = System.getenv("GEMINI_APIKEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("A variável de ambiente GEMINI_APIKEY não foi definida.");
        }
    }

    public String obterInformacao(String artista) {
        try {
            String json = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        { \"text\": \"Me fale sobre o artista: " + artista + "\" }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(URL + "?key=" + apiKey)
                    .post(body)
                    .build();

            Response response = client
                    .newCall(request)
                    .execute();

            String respBody = response.body() != null ? response.body().string() : "";

            if (!response.isSuccessful()) {
                return new IOException("Erro: " + response.code() + " - " + respBody).toString();
            }

            JsonNode root = mapper.readTree(respBody);

            return root
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao buscar dados do Artista.";
        }
    }
}
