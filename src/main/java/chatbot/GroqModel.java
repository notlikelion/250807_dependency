package chatbot;

public enum GroqModel {
    gpt_oss_120b("openai/gpt-oss-120b");

    private final String modelName;
    GroqModel(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
