package com.developerxy.sqli_test.models;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGithubViewer {
    private String username;
    private QLGithubRepositories repositories;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public QLGithubRepositories getRepositories() {
        return repositories;
    }

    public void setRepositories(QLGithubRepositories repositories) {
        this.repositories = repositories;
    }

    @Override
    public String toString() {
        return "QLGithubViewer{" +
                "username='" + username + '\'' +
                ", repositories=" + repositories +
                '}';
    }
}
