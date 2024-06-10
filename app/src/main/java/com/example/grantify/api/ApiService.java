package com.example.grantify.api;

import com.example.grantify.model.LoginRequest;
import com.example.grantify.model.LoginResponse;
import com.example.grantify.model.Program;
import com.example.grantify.model.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("programs")
    Call<List<Program>> getPrograms(@Query("category") String category, @Query("q") String query);

    @GET("programs")
    Call<List<Program>> searchPrograms(@Query("q") String query);

    @POST("register")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);

    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
