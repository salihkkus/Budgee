package com.example.budgee;

import com.google.gson.annotations.SerializedName;

public class Choice {
    @SerializedName("index")
    private int index;

    @SerializedName("message")
    private Message message; // Reusing the Message POJO

    @SerializedName("logprobs")
    private Object logprobs; // Can be null or a complex object

    @SerializedName("finish_reason")
    private String finishReason;

    // Getters and Setters
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Object logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
