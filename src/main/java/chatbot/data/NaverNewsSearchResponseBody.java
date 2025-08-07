package chatbot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverNewsSearchResponseBody(List<Item> items) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(String title, String link, String description, String pubDate) {}
}
