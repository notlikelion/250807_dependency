package chatbot;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Scanner;
// import?

public class Application {
    public static void main(String[] args) {
        // https://github.com/googleapis/java-genai
        // https://ai.google.dev/gemini-api/docs/models?hl=ko
        Dotenv dotenv = Dotenv.load();
        String systemInstruction = dotenv.get("SYSTEM_INSTRUCTION");
        GeminiClient client = new GeminiClient(GeminiModel.gemini_2_0_flash,
                systemInstruction);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("질문 : ");
            String input = sc.nextLine();
            if (input.equals("종료")) return; // 한줄은 괄호 없어도 된다
            String text = client.chat(input);
            System.out.println(text);
        }
    }
}
