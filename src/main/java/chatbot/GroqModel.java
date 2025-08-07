package chatbot;

public enum GroqModel {
    gemini_2_0_flash("gemini-2.0-flash"),
    gemini_2_5_flash("gemini-2.5-flash");

    private final String modelName;
    GroqModel(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
