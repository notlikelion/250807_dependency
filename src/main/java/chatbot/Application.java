package chatbot;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // https://github.com/googleapis/java-genai
//        String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");
//        if (GEMINI_API_KEY == null) {
//            throw new RuntimeException("API_KEY 없음");
//        }
//        Client client = Client.builder().apiKey(GEMINI_API_KEY).build();
        // https://ai.google.dev/gemini-api/docs/models?hl=ko
        GeminiClient client = new GeminiClient(GeminiModel.gemini_2_0_flash,
                "200자 이내로, 중학생도 이해할 수 있게, 꾸미는 문법 없이 평문으로.");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("질문 : ");
            String input = sc.nextLine();
            if (input.equals("종료")) return; // 한줄은 괄호 없어도 된다
//            GenerateContentConfig config = GenerateContentConfig.builder()
//                    .systemInstruction(Content.builder()
//                            .parts(List.of(
//                                Part.builder()
//                                .text("100자 이내로 핵심만, 꾸미는 표현 없이.")
//                                .build(),
//                                Part.builder()
//                                .text("프랑스어를 써서 답변해줘.")
//                                .build()))
//                            .build())
//                    .build();
//            GenerateContentResponse response = client.models.generateContent(
////                    GeminiModel.gemini_2_0_flash.toString(),
//                    GeminiModel.gemini_2_5_flash.toString(),
//                    input, config);
//            String text = response
//                    .candidates().get() // optional
//                    .get(0)
//                    .content().get()
//                    .text();
            String text = client.chat(input);
            System.out.println(text);
        }
    }
}
