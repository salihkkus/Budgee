package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends Activity {
    private EditText inputMessage;
    private TextView chatOutput;

    private String apiKey = "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";

    private ImageView mascotImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMessage = findViewById(R.id.inputMessage);
        chatOutput = findViewById(R.id.chatOutput);
        mascotImageView = findViewById(R.id.maskotIcon);

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            String message = inputMessage.getText().toString();
            if (!message.isEmpty()) {
                makeApiRequest(message);
            }
        });
    }

    private void makeApiRequest(String message) {
        Message userMessage = new Message("user", message);
        ChatCompletionRequest request = new ChatCompletionRequest(
                "deepseek/deepseek-chat-v3-0324:free",
                Arrays.asList(userMessage)
        );

        // The "Bearer " prefix is crucial for the Authorization header
        Call<ChatCompletionResponse> call = RetrofitClient.getApiService().createChatCompletion(
                "Bearer " + apiKey,
                request
        );

        call.enqueue(new Callback<ChatCompletionResponse>() {
            @Override
            public void onResponse(Call<ChatCompletionResponse> call, Response<ChatCompletionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatCompletionResponse chatResponse = response.body();
                    if (!chatResponse.getChoices().isEmpty()) {
                        String content = chatResponse.getChoices().get(0).getMessage().getContent();
                        Log.d("Chat", "API Response: " + content);
                    } else {
                        Log.d("Chat", "API Response: No choices found.");
                    }
                } else {
                    Log.e("Chat", "API Request Failed: " + response.code() + " - " + response.message());
                    try {
                        Log.e("Chat", "Error Body: " + response.errorBody().string());
                    } catch (Exception e) {
                        Log.e("Chat", "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatCompletionResponse> call, Throwable t) {
                Log.e("Chat", "API Request Failed: " + t.getMessage(), t);
            }
        });
    }

    private void setGifToImageView(){
        Glide.with(this)
                .asGif()
                .load(R.drawable.watchapp_maskot)  // drawable klasörüne eklediğin gif'in adı
                .into(mascotImageView);
    }

}