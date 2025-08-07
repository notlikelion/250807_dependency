package chatbot;

import chatbot.api.GeminiClient;
import chatbot.api.GroqClient;
import chatbot.api.NaverBlogSearchClient;
import chatbot.api.NaverNewsSearchClient;
import chatbot.data.GeminiModel;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;
import java.util.Scanner;
// import?

public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String systemInstruction = dotenv.get("SYSTEM_INSTRUCTION");
        GeminiClient geminiClient = new GeminiClient(GeminiModel.gemini_2_0_flash,
                systemInstruction);
        GroqClient groqClient = new GroqClient("아랍어로 대답해줘");
        NaverBlogSearchClient blogClient = new NaverBlogSearchClient();
        NaverNewsSearchClient newsClient = new NaverNewsSearchClient();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("질문 : ");
            String input = sc.nextLine();
            if (input.equals("종료")) return; // 한줄은 괄호 없어도 된다

            // 질문이 들어오면 -> 뉴스와 블로그에 검색.
            // 그걸 Gemini, Groq(GPT)에서 요약하도록 하고 결과를 받는.

            List<String> blogResult = blogClient.search(input)
                    .stream().map(item -> "제목 : %s\n설명: %s\n링크: %s\n작성일 : %s"
                                    .formatted(item.title(), item.description(),
                                            item.link(), item.postdate()))
                    .toList();
            List<String> newsResult = newsClient.search(input)
                    .stream().map(item -> "제목 : %s\n설명: %s\n링크: %s\n작성일 : %s"
                            .formatted(item.title(), item.description(),
                                    item.link(), item.pubDate()))
                    .toList();
            System.out.println(blogResult);
            System.out.println(newsResult);
        }
    }
}
