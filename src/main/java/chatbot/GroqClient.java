package chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

// 같은 동일한 패키지 구조 안에 있다면 import 없어도 가져다 쓸 수 있음
public class GroqClient {

    final private String apiKey;
    final private HttpClient httpClient = HttpClient.newHttpClient();

    public GroqClient() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null) throw new RuntimeException("API KEY 없음");
    }

    final private ObjectMapper mapper = new ObjectMapper();

    public String chat(String prompt, GroqModel model) {
//        https://console.groq.com/playground?model=openai/gpt-oss-120b
        String groqURL = "https://api.groq.com/openai/v1/chat/completions";
        GroqRequestBody requestBody = new GroqRequestBody(
                List.of(new GroqRequestBody.Message("user", prompt)),
                model.toString()
        );
        String bodyString = null;
        try {
            bodyString = mapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(groqURL))
                .headers(
                        "Content-Type", "application/json", // json
                        // 인증/인가 헤더 - Bearer Token
                        "Authorization", "Bearer %s".formatted(apiKey)
                )
                .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );
            return httpResponse.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
