package com.developerxy.sqli_test.retrofit.models;

import java.util.List;

/**
 * A class among the hierarchy of classes required by Retrofit to automatically convert a JSON response to a QLGitHubResponse object.
 */
public class QLGitHubRepositories {

    private QLPageInfo pageInfo;
    private List<QLGitHubItem> items;

    public QLPageInfo getPageInfo() {
        return pageInfo;
    }

    public List<QLGitHubItem> getItems() {
        return items;
    }
}
