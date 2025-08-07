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
//        GeminiClient client = new GeminiClient(GeminiModel.gemini_2_0_flash,
//                systemInstruction);
        // AIClient 추상클래스, 인터페이스
//        GroqClient client = new GroqClient("아랍어로 대답해줘");
        NaverBlogSearchClient client = new NaverBlogSearchClient();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("질문 : ");
            String input = sc.nextLine();
            if (input.equals("종료")) return; // 한줄은 괄호 없어도 된다
//            String text = client.chat(input, GroqModel.gpt_oss_120b);
            client.search(input).stream().forEach(
                    item -> System.out.println(
                            "제목 : %s\n설명: %s\n링크: %s\n작성일 : %s"
                                    .formatted(item.title(), item.description(),
                                            item.link(), item.postdate())
                    )
            );
//            System.out.println(text);
        }
    }
}
