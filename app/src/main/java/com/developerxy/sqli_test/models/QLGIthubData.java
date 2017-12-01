package com.developerxy.sqli_test.models;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLGIthubData {
    private QLGithubViewer viewer;

    public QLGithubViewer getViewer() {
        return viewer;
    }

    public void setViewer(QLGithubViewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public String toString() {
        return "QLGIthubData{" +
                "viewer=" + viewer +
                '}';
    }
}
