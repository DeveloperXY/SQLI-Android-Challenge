package com.developerxy.sqli_test.retrofit.listeners;

import android.util.Log;

import com.developerxy.sqli_test.retrofit.RetrofitCallBuilder;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGitHubResponse;
import com.developerxy.sqli_test.retrofit.models.QLPageInfo;
import com.developerxy.sqli_test.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <p>A custom Retrofit callback class.</p>
 *
 * <p>
 *      Its methods will be invoked when as a result of an incoming response from the GitHub GraphQL API.<br/>
 *      It makes use of a List internally to accumulate the repositories that were fetched due to subsequent calls to the API
 *      (in case the total number of user repositories was higher than the default number of repositories retrieved per response).
 * </p>
 *
 * When this callback's is first invoked, it fetches the first chunk of repositories. If there are
 * more callbacks to be fetched, it creates a new Call by cloning the original Call (request) that was used to initiate
 * the first request in the first place, in order to initiate a new request.
 */
public class FetchRepositoriesCallback implements Callback<QLGitHubResponse> {

    /**
     * A list of repositories that accumulates the repositories retrieved from the GitHub GraphQL API
     * through subsequent Calls.
     */
    private List<QLGitHubRepository> accumulatedRepositories;
    /**
     * A reference to the very original Call object that was used to initiate the first request.
     * This object will be cloned in the future (if needed) to serve new requests to be made.
     */
    private Call<QLGitHubResponse> callBackup;
    /**
     * A listener that will be notified whenever the repositories' loading operation finishes.
     */
    private OnRepositoriesLoadedListener onRepositoriesLoadedListener;

    public FetchRepositoriesCallback() {
        accumulatedRepositories = new ArrayList<>();
    }

    public FetchRepositoriesCallback(OnRepositoriesLoadedListener onRepositoriesLoadedListener) {
        this();
        this.onRepositoriesLoadedListener = onRepositoriesLoadedListener;
    }

    public FetchRepositoriesCallback(Call<QLGitHubResponse> callBackup,
                                     OnRepositoriesLoadedListener onRepositoriesLoadedListener,
                                     List<QLGitHubRepository> repositories) {
        this.onRepositoriesLoadedListener = onRepositoriesLoadedListener;
        this.callBackup = callBackup;
        this.accumulatedRepositories = repositories;
    }

    @Override
    public void onResponse(Call<QLGitHubResponse> call, Response<QLGitHubResponse> res) {
        QLGitHubResponse response = res.body();
        if (response != null) {
            // Get the repositories contained within the current response
            accumulatedRepositories.addAll(response.extractRepositories());

            // Get pagination information about the current response
            QLPageInfo pageInfo = response.getPaginationInfo();
            if (pageInfo.hasNextPage()) {
                // There are still more repositories to fetch
                // Request the next page of data from the API using the provided end cursor
                String endCursor = pageInfo.getEndCursor();
                Call<QLGitHubResponse> newCall; // the Call object to be used for the next request

                if (callBackup == null) {
                    // No backup Call object was passed to this callback instance
                    // so, create a new Call object using a new query that specifies the end cursor

                    String query = "{viewer {username: login repositories(first: " + Constants.NUMBER_OF_REPOSITORIES_PER_REQUEST +
                            ", after: \"" + endCursor + "\") { pageInfo { hasNextPage endCursor } items: edges { " +
                            "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";
                    newCall = RetrofitCallBuilder.callForGithubRepositories(query);
                    // Create a backup of this call for future calls
                    callBackup = newCall.clone();
                } else {
                    // A call backup was already passed to this Callback instance, clone it to get a fresh Call instance
                    // instead of calling callForGithubRepositories()
                    newCall = callBackup.clone();
                }

                // Send a request using the new Call object & keep passing the original call backup in addition
                // to the concerned listener for future calls
                newCall.enqueue(new FetchRepositoriesCallback(callBackup, onRepositoriesLoadedListener, accumulatedRepositories));
            } else {
                // There is no more data to fetch, this is the last page of data to be retrieved from the API
                // Notify the activity that the loading operation was successful
                onRepositoriesLoadedListener.onLoadSucceeded(accumulatedRepositories);
            }

        } else {
            Log.i("GraphQLClient", "GitHub response is null.");
        }
    }

    @Override
    public void onFailure(Call<QLGitHubResponse> call, Throwable t) {
        // Notify the activity that the loading failed
        onRepositoriesLoadedListener.onLoadFailed("Cannot connect to server.");
    }
}