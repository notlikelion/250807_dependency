package chatbot;

public enum GeminiModel {
    gemini_2_0_flash("gemini-2.0-flash"),
    gemini_2_5_flash("gemini-2.5-flash");

    private final String modelName;
    GeminiModel(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
