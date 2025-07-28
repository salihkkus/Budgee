package com.example.budgee;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenRouterApiService {

    @POST("chat/completions")
    Call<ChatCompletionResponse> createChatCompletion(
            @Header("Authorization") String authorization,
            @Body ChatCompletionRequest request
    );
}