package com.developerxy.sqli_test.retrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohammed Aouf ZOUAG on 01/12/2017.
 */
public class ServiceGenerator {

    private static final String BASE_URL = "https://api.github.com/";
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, final String token) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);

            // Intercept requests on the network layer & add the Authorization header required
            // for the GitHub API
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Authorization", "token " + token)
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}
