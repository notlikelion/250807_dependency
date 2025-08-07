package chatbot;


/*
{
        "messages": [
            {
                "role": "user",
                "content": "%s"
            }
        ],
        "model": "%s"
}
*/

import java.util.List;

public record GroqRequestBody(List<Message> messages, String model) {
    public record Message(String role, String content) {};
}
