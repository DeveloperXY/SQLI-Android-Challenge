package com.developerxy.sqli_test.retrofit;

import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mohammed Aouf ZOUAG on 01/12/2017.
 */
public interface GraphQLClient {
    @POST("graphql")
    Call<QLGithubResponse> getAllRepositories(@Body QLQuery query);
}
