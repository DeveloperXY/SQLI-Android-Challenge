package com.developerxy.sqli_test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.GraphQLClient;
import com.developerxy.sqli_test.retrofit.ServiceGenerator;
import com.developerxy.sqli_test.retrofit.models.QLGithubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.retrofit.models.QLPageInfo;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int NUMBER_OF_REPOSITORIES_PER_REQUEST = 30;

    private String accessToken;
    private List<QLGithubRepository> repositories;
    private GraphQLClient graphQLClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repositories = new ArrayList<>();
        accessToken = getResources().getString(R.string.github_token);
        graphQLClient = ServiceGenerator.createService(GraphQLClient.class, accessToken);
        Log.e("GitHub Personal Token", accessToken);

        String query = "{viewer {username: login repositories(first: " + NUMBER_OF_REPOSITORIES_PER_REQUEST +
                ") { pageInfo { hasNextPage endCursor } items: edges { " +
                "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";

        Call<QLGithubResponse> initialCall = callForGithubRepositories(query);
        // Send an asynchronous request for the first bulk of repositories
        // If there are more repositories to be fetched, this initial call will subsequently setup & execute
        // other calls to fetch the rest
        initialCall.enqueue(new GetRepositoriesCallback());
    }

    /**
     * @param query to be used when making the call.
     * @return a Call object resulting from the call to the GraphQLClient.
     */
    private Call<QLGithubResponse> callForGithubRepositories(String query) {
        QLQuery qlQuery = new QLQuery(query);
        return graphQLClient.getAllRepositories(qlQuery);
    }

    class GetRepositoriesCallback implements Callback<QLGithubResponse> {

        private Call<QLGithubResponse> callBackup;

        public GetRepositoriesCallback() {
        }

        public GetRepositoriesCallback(Call<QLGithubResponse> callBackup) {
            this.callBackup = callBackup;
        }

        @Override
        public void onResponse(Call<QLGithubResponse> call, Response<QLGithubResponse> res) {
            QLGithubResponse response = res.body();
            if (response != null) {
                // Get a bulk of repositories from the current response
                repositories.addAll(response.extractRepositories());

                // Get pagination information about the current response
                QLPageInfo pageInfo = response.getPaginationInfo();
                if (pageInfo.hasNextPage()) {
                    // Request the next page of data from the API using the provided end cursor
                    String endCursor = pageInfo.getEndCursor();
                    Call<QLGithubResponse> newCall;

                    if (callBackup == null) {
                        // The first bulk of repositories was fetched, but we need to send a new request
                        // using a new Call object & a new query that specifies the end cursor

                        String query = "{viewer {username: login repositories(first: " + NUMBER_OF_REPOSITORIES_PER_REQUEST +
                                ", after: \"" + endCursor + "\") { pageInfo { hasNextPage endCursor } items: edges { " +
                                "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";
                        newCall = callForGithubRepositories(query);
                        // Create a backup of this call for future calls, if needed
                        callBackup = newCall.clone();
                    } else {
                        // A call backup was already passed to this Callback instance, re-use it to create a new
                        // Call object instead of calling callForGithubRepositories()
                        newCall = callBackup.clone();
                    }

                    // Send a request using a new Call object & keep passing a call backup for future calls
                    newCall.enqueue(new GetRepositoriesCallback(callBackup));
                }
                else {
                    // There is no more data to fetch, this is the last call to be sent
                    Log.i("GraphQLClient", String.format("Retrieved %d repositories.", repositories.size()));
                }

            } else {
                Log.i("GraphQLClient", "GitHub response is null.");
            }
        }

        @Override
        public void onFailure(Call<QLGithubResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Cannot connect to server.", Toast.LENGTH_SHORT).show();
        }
    }
}
