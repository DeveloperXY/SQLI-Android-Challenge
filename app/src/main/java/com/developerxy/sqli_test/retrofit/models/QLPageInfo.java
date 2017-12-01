package com.developerxy.sqli_test.retrofit.models;

/**
 * A class among the hierarchy of classes required by Retrofit to automatically convert a JSON response to a QLGitHubResponse object.
 */
public class QLPageInfo {
    /**
     * A flag indicating if there still more data to be fetched from the GitHub GraphQL API.
     */
    private boolean hasNextPage;
    /**
     * A cursor used for pagination in the context of the GraphQL API.
     */
    private String endCursor;

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public String getEndCursor() {
        return endCursor;
    }
}
