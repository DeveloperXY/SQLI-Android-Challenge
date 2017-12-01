package com.developerxy.sqli_test.retrofit;

import com.developerxy.sqli_test.retrofit.models.QLGitHubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GraphQLClient {
    @POST("graphql")
    Call<QLGitHubResponse> getAllRepositories(@Body QLQuery query);
}
