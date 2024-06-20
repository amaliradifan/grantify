package com.example.grantify.api;

import com.example.grantify.model.Bookmark;
import com.example.grantify.model.BookmarkRequest;
import com.example.grantify.model.BookmarkResponse;
import com.example.grantify.model.EditUserRequest;
import com.example.grantify.model.LoginRequest;
import com.example.grantify.model.LoginResponse;
import com.example.grantify.model.Program;
import com.example.grantify.model.RegisterRequest;
import com.example.grantify.model.UserProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @GET("profile")
    Call<UserProfile> getUserProfile();

    @GET("bookmarks")
    Call<List<Bookmark>> getBookmarks();

    @POST("bookmark")
    Call<BookmarkResponse> createBookmark(@Body BookmarkRequest bookmarkRequest);

    @DELETE("bookmark/{id}")
    Call<Void> deleteBookmark(@Path("id") String bookmarkId);

    @PUT("user/edit")
    Call<UserProfile> editUser(@Body EditUserRequest request);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadProfileImage(@Part MultipartBody.Part file);
}
