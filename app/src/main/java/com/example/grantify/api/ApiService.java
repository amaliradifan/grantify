package com.example.grantify.api;

import com.example.grantify.Program;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("programs")
    Call<List<Program>> getPrograms();
}
