package com.developerxy.sqli_test.retrofit.models;

import java.util.List;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
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
