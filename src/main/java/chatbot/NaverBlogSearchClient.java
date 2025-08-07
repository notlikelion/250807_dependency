package chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class NaverBlogSearchClient {
    static final String url = "https://openapi.naver.com/v1/search/blog.json";

    static final HttpClient httpClient = HttpClient.newHttpClient();
    static final ObjectMapper objectMapper = new ObjectMapper();

    private final String naverClientId;
    private final String naverClientSecret;

    public NaverBlogSearchClient () {
        Dotenv dotenv = Dotenv.load();
        this.naverClientId = dotenv.get("NAVER_CLIENT_ID");
        this.naverClientSecret = dotenv.get("NAVER_CLIENT_SECRET");
    }

    public String search(String keyword) {
        try {
            // keyword -> 복잡한 문자 -> 이 경우에는 요청에서 이슈...
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("%s?query=%s"
                            .formatted(url, URLEncoder.encode(keyword, StandardCharsets.UTF_8))))
                    .headers(
                            "X-Naver-Client-Id", naverClientId,
                            "X-Naver-Client-Secret", naverClientSecret
                    )
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );
//            System.out.println(httpResponse.statusCode());
            String body = httpResponse.body();
            return body;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
