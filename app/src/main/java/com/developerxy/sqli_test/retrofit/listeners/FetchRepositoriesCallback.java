package com.developerxy.sqli_test.retrofit.listeners;

import android.util.Log;

import com.developerxy.sqli_test.retrofit.RetrofitCallBuilder;
import com.developerxy.sqli_test.retrofit.models.QLGithubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.retrofit.models.QLPageInfo;
import com.developerxy.sqli_test.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class FetchRepositoriesCallback implements Callback<QLGithubResponse> {

    private List<QLGithubRepository> accumulatedRepositories;
    private Call<QLGithubResponse> callBackup;
    private OnRepositoriesLoadedListener onRepositoriesLoadedListener;

    public FetchRepositoriesCallback() {
        accumulatedRepositories = new ArrayList<>();
    }

    public FetchRepositoriesCallback(OnRepositoriesLoadedListener onRepositoriesLoadedListener) {
        this();
        this.onRepositoriesLoadedListener = onRepositoriesLoadedListener;
    }

    public FetchRepositoriesCallback(Call<QLGithubResponse> callBackup,
                                     OnRepositoriesLoadedListener onRepositoriesLoadedListener,
                                     List<QLGithubRepository> repositories) {
        this.onRepositoriesLoadedListener = onRepositoriesLoadedListener;
        this.callBackup = callBackup;
        this.accumulatedRepositories = repositories;
    }

    @Override
    public void onResponse(Call<QLGithubResponse> call, Response<QLGithubResponse> res) {
        QLGithubResponse response = res.body();
        if (response != null) {
            // Get a bulk of repositories from the current response
            accumulatedRepositories.addAll(response.extractRepositories());

            // Get pagination information about the current response
            QLPageInfo pageInfo = response.getPaginationInfo();
            if (pageInfo.hasNextPage()) {
                // Request the next page of data from the API using the provided end cursor
                String endCursor = pageInfo.getEndCursor();
                Call<QLGithubResponse> newCall;

                if (callBackup == null) {
                    // The first bulk of repositories was fetched, but we need to send a new request
                    // using a new Call object & a new query that specifies the end cursor

                    String query = "{viewer {username: login repositories(first: " + Constants.NUMBER_OF_REPOSITORIES_PER_REQUEST +
                            ", after: \"" + endCursor + "\") { pageInfo { hasNextPage endCursor } items: edges { " +
                            "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";
                    newCall = RetrofitCallBuilder.callForGithubRepositories(query);
                    // Create a backup of this call for future calls, if needed
                    callBackup = newCall.clone();
                } else {
                    // A call backup was already passed to this Callback instance, re-use it to create a new
                    // Call object instead of calling callForGithubRepositories()
                    newCall = callBackup.clone();
                }

                // Send a request using a new Call object & keep passing a call backup for future calls
                newCall.enqueue(new FetchRepositoriesCallback(callBackup, onRepositoriesLoadedListener, accumulatedRepositories));
            } else {
                // There is no more data to fetch, this is the last call to be sent
                // Notify the activity that the loading operation was successful
                onRepositoriesLoadedListener.onLoadSucceeded(accumulatedRepositories);
            }

        } else {
            Log.i("GraphQLClient", "GitHub response is null.");
        }
    }

    @Override
    public void onFailure(Call<QLGithubResponse> call, Throwable t) {
        // Notify the activity that the loading failed
        onRepositoriesLoadedListener.onLoadFailed("Cannot connect to server.");
    }
}