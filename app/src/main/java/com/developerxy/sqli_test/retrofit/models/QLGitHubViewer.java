package com.developerxy.sqli_test.retrofit.models;

/**
 * A class among the hierarchy of classes required by Retrofit to automatically convert a JSON response to a QLGitHubResponse object.
 */
public class QLGitHubViewer {
    /**
     * The GitHub username.
     */
    private String username;
    private QLGitHubRepositories repositories;

    public String getUsername() {
        return username;
    }

    public QLGitHubRepositories getRepositories() {
        return repositories;
    }
}
