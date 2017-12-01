package com.developerxy.sqli_test.retrofit.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This model represents the response returned by the Github GraphQL API.
 * <p>
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGithubResponse {
    private QLGIthubData data;

    public QLGIthubData getData() {
        return data;
    }

    /**
     * @return the list of repositories contained deep within the response hierarchy.
     */
    public List<QLGithubRepository> extractRepositories() {
        List<QLGithubRepository> repositories = new ArrayList<>();
        List<QLGithubItem> repositoryItems = data.getViewer().getRepositories().getItems();

        for (QLGithubItem item : repositoryItems) {
            repositories.add(item.getRepository());
        }

        return repositories;
    }

    /**
     * @return pagination information about the current response data.
     */
    public QLPageInfo getPaginationInfo() {
        return data.getViewer()
                .getRepositories()
                .getPageInfo();
    }
}
