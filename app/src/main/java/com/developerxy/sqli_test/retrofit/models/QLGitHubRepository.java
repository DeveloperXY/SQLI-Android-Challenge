package com.developerxy.sqli_test.retrofit.models;

import java.util.ArrayList;
import java.util.List;

public class QLGitHubRepository {
    private String name;
    private String url;
    private String createdAt;
    private String description;
    private String license;
    private PrimaryLanguage primaryLanguage;
    private boolean isPrivate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public PrimaryLanguage getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(PrimaryLanguage primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    private class PrimaryLanguage {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "PrimaryLanguage{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * @param repositories to be filtered
     * @param query the query's text to be used for filtering
     * @return the list of GitHub repositories whose names contain the given query text.
     */
    public static List<QLGitHubRepository> filter(List<QLGitHubRepository> repositories, String query) {
        List<QLGitHubRepository> toFilterRepos = new ArrayList<>(repositories);
        List<QLGitHubRepository> filteredRepos = new ArrayList<>();
        for (int i = 0; i < toFilterRepos.size(); i++) {
            QLGitHubRepository repository = toFilterRepos.get(i);
            if (repository.getName().toLowerCase().contains(query.toLowerCase()))
                filteredRepos.add(repository);
        }

        return filteredRepos;
    }

    @Override
    public String toString() {
        return "QLGitHubRepository{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", description='" + description + '\'' +
                ", license='" + license + '\'' +
                ", primaryLanguage=" + primaryLanguage +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
