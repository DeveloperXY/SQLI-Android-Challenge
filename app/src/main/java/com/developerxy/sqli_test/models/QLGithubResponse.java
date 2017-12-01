package com.developerxy.sqli_test.models;

/**
 * This model represents the response returned by the Github GraphQL API.
 *
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGithubResponse {
    private QLGIthubData data;

    public QLGIthubData getData() {
        return data;
    }

    public void setData(QLGIthubData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QLGithubResponse{" +
                "data=" + data +
                '}';
    }
}
