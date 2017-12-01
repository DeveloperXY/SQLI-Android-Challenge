package com.developerxy.sqli_test.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class among the hierarchy of classes required by Retrofit to automatically convert a JSON response to a QLGitHubResponse object.
 */
public class QLGitHubRepository implements Parcelable {
    /**
     * The name of the repository.
     */
    private String name;
    /**
     * The URL of the repository.
     */
    private String url;
    /**
     * The creation date of this repository.
     */
    private String createdAt;
    /**
     * A description of this repository.
     */
    private String description;
    /**
     * The license attributed to this repository.
     */
    private String license;
    /**
     * The primary language that was used in this repository.
     */
    private PrimaryLanguage primaryLanguage;
    /**
     * a boolean flag indicating if this repository is a private one.
     */
    private boolean isPrivate;

    private QLGitHubRepository(Parcel in) {
        name = in.readString();
        url = in.readString();
        createdAt = in.readString();
        description = in.readString();
        primaryLanguage = new PrimaryLanguage(in.readString());
        isPrivate = in.readInt() == 1;
    }

    public QLGitHubRepository() {

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(createdAt);
        parcel.writeString(description);
        parcel.writeString(license);
        parcel.writeString(primaryLanguage.getName());
        parcel.writeInt(isPrivate ? 1 : 0);
    }

    public static final Creator<QLGitHubRepository> CREATOR = new Creator<QLGitHubRepository>() {

        @Override
        public QLGitHubRepository createFromParcel(Parcel source) {
            return new QLGitHubRepository(source);
        }

        @Override
        public QLGitHubRepository[] newArray(int size) {
            return new QLGitHubRepository[size];
        }
    };

    public class PrimaryLanguage {
        private String name;

        public PrimaryLanguage() {
        }

        public PrimaryLanguage(String name) {
            this.name = name;
        }

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
     * @param query        the query's text to be used for filtering
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

    /**
     * This method removes the private repositories among those that were passed in to the method.
     *
     * @param repositories to filter
     * @return a list of the public repositories among the {@code repositories} list.
     */
    public static List<QLGitHubRepository> eliminatePrivateRepositories(List<QLGitHubRepository> repositories) {
        for (Iterator<QLGitHubRepository> iterator = repositories.iterator();
                iterator.hasNext();) {
            QLGitHubRepository repository = iterator.next();
            if (repository.isPrivate())
                iterator.remove();
        }

        return repositories;
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
