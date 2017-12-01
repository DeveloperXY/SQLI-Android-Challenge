package com.developerxy.sqli_test.models;

import java.util.List;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGithubRepositories {

    private QLGithubItems items;

    public QLGithubItems getItems() {
        return items;
    }

    public void setItems(QLGithubItems items) {
        this.items = items;
    }

    private class QLGithubItems {
        private List<QLGithubRepository> repositories;

        public List<QLGithubRepository> getRepositories() {
            return repositories;
        }

        public void setRepositories(List<QLGithubRepository> repositories) {
            this.repositories = repositories;
        }

        @Override
        public String toString() {
            return "QLGithubItems{" +
                    "repositories=" + repositories +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QLGithubRepositories{" +
                "items=" + items +
                '}';
    }
}
