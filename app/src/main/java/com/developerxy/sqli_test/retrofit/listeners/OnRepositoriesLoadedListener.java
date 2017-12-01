package com.developerxy.sqli_test.retrofit.listeners;

import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;

import java.util.List;

/**
 * A listener that notifies the implementer class of the result of loading GitHub repositories.
 */
public interface OnRepositoriesLoadedListener {
    /**
     * This method is invoked if the loading of repositories was successful.
     *
     * @param repositories that were loaded
     */
    void onLoadSucceeded(List<QLGitHubRepository> repositories);

    /**
     * This method is invoked if the loading of repositories failed.
     *
     * @param errorMessage describing the error that happened.
     */
    void onLoadFailed(String errorMessage);
}
