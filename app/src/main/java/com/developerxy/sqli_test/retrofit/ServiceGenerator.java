package com.developerxy.sqli_test.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A utility class to configure & generate Retrofit clients.
 */
public class ServiceGenerator {

    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /**
     * @param serviceClass the class type of the client to be generated
     * @param githubToken the Personal Access Token required to access the GitHub GraphQL API.
     * @param <S> the type of client to be generated
     * @return an instance of the requested client that is ready for use.
     */
    public static <S> S createService(Class<S> serviceClass, final String githubToken) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);

            // Intercept requests on the network layer & add the Authorization header required
            // for the GitHub API
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Authorization", "token " + githubToken)
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
