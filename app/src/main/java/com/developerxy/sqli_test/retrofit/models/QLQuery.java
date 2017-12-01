package com.developerxy.sqli_test.retrofit.models;

/**
 * <p>
 * A class that wraps the query to be communicated to the GitHub GraphQL API.<br/>
 * Retrofit will handle the conversion of an instance of this class to JSON format,
 * that will be sent within a request through the network.
 * </p>
 *
 * After conversion, The JSON to be sent will be of this format:
 * <h2>
 * {
 *     "query": "the value of the query field of a QLQuery instance"
 * }
 * </h2>
 *
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public class QLQuery {
    private String query;

    public QLQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "QLQuery{" +
                "query='" + query + '\'' +
                '}';
    }
}
