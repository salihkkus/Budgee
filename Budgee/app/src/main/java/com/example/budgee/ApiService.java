package com.example.budgee;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("v1/chat/completions")
    Call<ChatCompletionResponse> sendMessage(@Header("Authorization") String auth, @Body JsonObject body);
}
