package com.developerxy.sqli_test.retrofit;

import com.developerxy.sqli_test.application.GraphQLApplication;
import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import retrofit2.Call;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class RetrofitCallBuilder {

    private static GraphQLClient mGraphQLClient;
    private static GraphQLApplication mApplication;

    private RetrofitCallBuilder() {
        // Impose the Singleton pattern
    }

    public static void init(GraphQLApplication application) {
        mApplication = application;
        mGraphQLClient = mApplication.getGraphQLClient();
    }

    public static Call<QLGithubResponse> callForGithubRepositories(String query) {
        if (mApplication == null)
            throw new IllegalStateException("You need to call RetrofitCallBuilder.init() before using the rest of the API.");

        QLQuery qlQuery = new QLQuery(query);
        return mGraphQLClient.getAllRepositories(qlQuery);
    }
}
