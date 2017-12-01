package com.developerxy.sqli_test.retrofit.models;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGithubRepository {
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

    @Override
    public String toString() {
        return "QLGithubRepository{" +
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
