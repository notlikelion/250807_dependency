package chatbot.api;

import chatbot.data.GroqModel;
import chatbot.data.GroqRequestBody;
import chatbot.data.GroqResponseBody;
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
    final private String systemInstruction;
    public GroqClient(String systemInstruction) {
        Dotenv dotenv = Dotenv.load();
        this.systemInstruction = systemInstruction;
        apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null) throw new RuntimeException("API KEY 없음");
    }

    final private ObjectMapper mapper = new ObjectMapper();

    public String chat(String prompt, GroqModel model) {
//        https://console.groq.com/playground?model=openai/gpt-oss-120b
        String groqURL = "https://api.groq.com/openai/v1/chat/completions";
        GroqRequestBody requestBody = new GroqRequestBody(
                List.of(
                        new GroqRequestBody.Message("system", systemInstruction),
                        new GroqRequestBody.Message("user", prompt)),
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
//                .header(
//                        "Content-Type", "application/json"
//                )
//                .header(
//                        "Authorization", "Bearer %s".formatted(apiKey)
//                )
                .headers("Content-Type", "application/json",
                        "Authorization", "Bearer %s".formatted(apiKey))
                .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );
            String json = httpResponse.body();
            GroqResponseBody data = mapper.readValue(json, GroqResponseBody.class);
            return data.choices().get(0).message().content();
//            return data.choices().get(0).message().reasoning();

            // ObjectMapper
            // 1. writeValueAsString -> Record, Class => JSON String
            // 3. 무시해야할 필드가 있다면 -> @JsonIgnoreProperties(ignoreUnknown = true)
            // 2. readValue(JSONText, 레코드/클래스.class) -> Record, Class Object
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
