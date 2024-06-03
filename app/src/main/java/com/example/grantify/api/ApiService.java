package com.example.grantify.api;

import com.example.grantify.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("programs")
    Call<List<Program>> getPrograms();

    @GET("programs")
    Call<List<Program>> getPrograms(@Query("category") String category);
}
