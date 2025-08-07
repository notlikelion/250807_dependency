package chatbot;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class Application {
    public static void main(String[] args) {
        // https://github.com/googleapis/java-genai
        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
        Client client = Client.builder().apiKey(GEMINI_API_KEY).build();
        // https://ai.google.dev/gemini-api/docs/models?hl=ko

        GenerateContentResponse response = client.models.generateContent("gemini-2.0-flash",
                "점심 메뉴 추천해주세요", null);
        String text = response
                        .candidates().get() // optional
                        .get(0)
                        .content().get()
                        .text();
        System.out.println(text);
    }
}
