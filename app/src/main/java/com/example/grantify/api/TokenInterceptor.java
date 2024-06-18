package com.example.grantify.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.grantify.ui.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private TokenManager tokenManager;
    private Context context;

    public TokenInterceptor(Context context) {
        this.tokenManager = new TokenManager(context);
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Bypass token handling for the registration endpoint
        if (originalRequest.url().toString().contains("/register")) {
            return chain.proceed(originalRequest);
        }

        String token = tokenManager.getToken();
        if (token != null) {
            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token);
            Request newRequest = builder.build();
            Response response = chain.proceed(newRequest);

            if (response.code() == 401) {
                // Token expired, redirect to login activity
                redirectToLoginActivity();
            }

            return response;
        } else {
            // Redirect to login activity if token is null (user is not logged in) or expired
            redirectToLoginActivity();
            return chain.proceed(originalRequest);
        }
    }

        private void redirectToLoginActivity() {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
}
