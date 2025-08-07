package chatbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/*
{"id":"chatcmpl-fae249ce-9813-4280-9ac7-ec7078b84f47",
 "object":"chat.completion",
 "created":1754545742,
 "model":"openai/gpt-oss-120b",
 "choices":[
    {"index":0,"message":
        {"role":"assistant","content":"...", "reasoning": "..."}
    }],
 "logprobs":null,"finish_reason":"stop"}],
 "usage":{"queue_time":0.196781554,"prompt_tokens":80,"prompt_time":0.004677395,"completion_tokens":2591,"completion_time":4.814634258,"total_tokens":2671,"total_time":4.819311653},
 "usage_breakdown":null,
 "system_fingerprint":"fp_82669fd91d",
 "x_groq":{"id":"req_01k21h6k8vecdbs4r21ns5jgyg"},
 "service_tier":"on_demand"}
*/

// UnrecognizedPropertyException (Unrecognized field) 해결
@JsonIgnoreProperties(ignoreUnknown = true)
public record GroqResponseBody(List<Choice> choices) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Choice(Message message) {};
//    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Message(String role, String content, String reasoning) {};
}
