package com.developerxy.sqli_test.retrofit.listeners;

import com.developerxy.sqli_test.retrofit.models.QLGithubRepository;

import java.util.List;

/**
 * Created by Mohammed Aouf ZOUAG on 12/1/2017.
 */

public interface OnRepositoriesLoadedListener {
    void onLoadSucceeded(List<QLGithubRepository> repositories);
    void onLoadFailed(String errorMessage);
}
