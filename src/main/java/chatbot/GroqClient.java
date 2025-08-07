package chatbot;

import io.github.cdimascio.dotenv.Dotenv;

// 같은 동일한 패키지 구조 안에 있다면 import 없어도 가져다 쓸 수 있음
public class GroqClient {

    final private String apiKey;

    public GroqClient() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("GROQ_API_KEY");
        if (apiKey == null) throw new RuntimeException("API KEY 없음");
    }

    public String chat(String prompt, GroqModel model) {

        return "";
    }
}
