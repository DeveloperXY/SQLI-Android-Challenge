package com.developerxy.sqli_test.retrofit.models;

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
