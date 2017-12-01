package com.developerxy.sqli_test.retrofit.models;

/**
 * A class among the hierarchy of classes required by Retrofit to automatically convert a JSON response to a QLGitHubResponse object.
 */
public class QLGitHubItem {
    private QLGitHubRepository repository;

    public QLGitHubRepository getRepository() {
        return repository;
    }
}
