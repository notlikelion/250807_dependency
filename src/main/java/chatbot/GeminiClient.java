package chatbot;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import java.util.List;

public class GeminiClient {
    private final Client client;
    private final String model;
    private final GenerateContentConfig config;

    GeminiClient(GeminiModel model, String systemInstruction) {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null) throw new RuntimeException("API KEY 없음");
        client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.model = model.toString();
        this.config = GenerateContentConfig.builder()
                .systemInstruction(
                        Content.builder().parts(
                                List.of(Part.builder().text(systemInstruction).build())
                        ).build()
                )
                .build();
    }
    public String chat(String prompt) {
        GenerateContentResponse response = client.models.generateContent(this.model, prompt, config);
        return response.candidates().get()
                .get(0)
                .content().get()
                .text();
    }
}
