package com.developerxy.sqli_test.retrofit;

import com.developerxy.sqli_test.application.GraphQLApplication;
import com.developerxy.sqli_test.retrofit.models.QLGitHubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import retrofit2.Call;

/**
 * A helper class that builds Retrofit Call objects based on different GraphQL queries.
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

    /**
     * This method constructs a QLQuery based on the passed in String query, & uses it to pass the query data
     * to the request body before sending it.
     *
     * @param query to be executed
     * @return a Retrofit Call object wrapping the GraphQL API's response after executing the passed in query.
     */
    public static Call<QLGitHubResponse> callForGithubRepositories(String query) {
        if (mApplication == null)
            throw new IllegalStateException("You need to call RetrofitCallBuilder.init() before using the rest of the API.");

        QLQuery qlQuery = new QLQuery(query);
        return mGraphQLClient.getAllRepositories(qlQuery);
    }
}
