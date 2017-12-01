package com.developerxy.sqli_test.application;

import android.app.Application;
import android.util.Log;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.GraphQLClient;
import com.developerxy.sqli_test.retrofit.ServiceGenerator;

public class GraphQLApplication extends Application {

    /**
     * The GitHub Personal Access Token used to communicate with the GitHub GraphQL API.
     * An entry with the name 'github_token' is required in the local.properties file, from which
     * the access token will be loaded.
     */
    private String accessToken;
    private GraphQLClient graphQLClient;

    @Override
    public void onCreate() {
        super.onCreate();

        accessToken = getResources().getString(R.string.github_token);
        if (accessToken.startsWith("Error"))
            throw new IllegalStateException(accessToken); // Throw an exception if the access token wasn't specified
        Log.e("GitHub Personal Token", accessToken);

        graphQLClient = ServiceGenerator.createService(GraphQLClient.class, accessToken);
    }

    public GraphQLClient getGraphQLClient() {
        return graphQLClient;
    }
}
