package com.developerxy.sqli_test.retrofit;

import com.developerxy.sqli_test.retrofit.models.QLGitHubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * A Retrofit client interface that specifies the endpoints to be used when communicating
 * with the GitHub GraphQL API.
 */
public interface GraphQLClient {
    /**
     * @param query to be used to retrieve the repositories.
     * @return a Call wrapping the response of the GraphQL API.
     */
    @POST("graphql")
    Call<QLGitHubResponse> getAllRepositories(@Body QLQuery query);
}
