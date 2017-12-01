package com.developerxy.sqli_test.application;

import android.app.Application;
import android.util.Log;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.GraphQLClient;
import com.developerxy.sqli_test.retrofit.ServiceGenerator;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class GraphQLApplication extends Application {

    private String accessToken;
    private GraphQLClient graphQLClient;

    @Override
    public void onCreate() {
        super.onCreate();

        accessToken = getResources().getString(R.string.github_token);
        Log.e("GitHub Personal Token", accessToken);
        graphQLClient = ServiceGenerator.createService(GraphQLClient.class, accessToken);
    }

    public GraphQLClient getGraphQLClient() {
        return graphQLClient;
    }
}
