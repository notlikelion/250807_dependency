package chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NaverNewsSearchClient {
    static final String url = "https://openapi.naver.com/v1/search/news.json";

    static final HttpClient httpClient = HttpClient.newHttpClient();
    static final ObjectMapper objectMapper = new ObjectMapper();

    private final String naverClientId;
    private final String naverClientSecret;

    public NaverNewsSearchClient() {
        Dotenv dotenv = Dotenv.load();
        this.naverClientId = dotenv.get("NAVER_CLIENT_ID");
        this.naverClientSecret = dotenv.get("NAVER_CLIENT_SECRET");
    }

    public List<NaverNewsSearchResponseBody.Item> search(String keyword) {
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
            String json = httpResponse.body();
            NaverNewsSearchResponseBody body = objectMapper.readValue(json, NaverNewsSearchResponseBody.class);
            return body.items();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
