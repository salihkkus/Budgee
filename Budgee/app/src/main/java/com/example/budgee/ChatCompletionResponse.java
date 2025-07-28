package com.example.budgee;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChatCompletionResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object; // Changed from `object` to `objectType` to avoid keyword conflict

    @SerializedName("created")
    private long created;

    @SerializedName("model")
    private String model;

    @SerializedName("choices")
    private List<Choice> choices;

    @SerializedName("usage")
    private Usage usage;

    // Getters and Setters for all fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getObjectType() { return object; }
    public void setObjectType(String object) { this.object = object; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }
}

