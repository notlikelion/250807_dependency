package chatbot;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // https://github.com/googleapis/java-genai
        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
        if (GEMINI_API_KEY == null) {
            throw new RuntimeException("API_KEY 없음");
        }
        Client client = Client.builder().apiKey(GEMINI_API_KEY).build();
        // https://ai.google.dev/gemini-api/docs/models?hl=ko

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("종료")) return; // 한줄은 괄호 없어도 된다
            GenerateContentResponse response = client.models.generateContent(
                    GeminiModel.gemini_2_0_flash.toString(),
                    input, null);
            String text = response
                    .candidates().get() // optional
                    .get(0)
                    .content().get()
                    .text();
            System.out.println(text);
        }
    }
}
