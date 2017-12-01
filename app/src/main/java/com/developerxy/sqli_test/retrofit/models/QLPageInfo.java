package com.developerxy.sqli_test.retrofit.models;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLPageInfo {
    private boolean hasNextPage;
    private String endCursor;

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public String getEndCursor() {
        return endCursor;
    }
}
