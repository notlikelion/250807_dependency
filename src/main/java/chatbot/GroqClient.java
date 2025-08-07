package chatbot;

import io.github.cdimascio.dotenv.Dotenv;

public class GroqClient {

    final private String apiKey;

    public GroqClient() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null) throw new RuntimeException("API KEY 없음");
    }
}
