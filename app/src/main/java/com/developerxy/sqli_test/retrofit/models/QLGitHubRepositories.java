package com.developerxy.sqli_test.retrofit.models;

import java.util.List;

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
