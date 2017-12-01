package com.developerxy.sqli_test.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;

public class RepositoryDetailsFragment extends Fragment {

    public RepositoryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repository_details, container, false);

        return view;
    }

    public static Fragment newInstance(QLGitHubRepository repository) {
        RepositoryDetailsFragment fragment = new RepositoryDetailsFragment();

        if (repository != null) {
            Bundle args = new Bundle();
            args.putString("path", repository.getName());
            args.putString("url", repository.getUrl());
            args.putString("createdAt", repository.getCreatedAt());
            args.putString("description", repository.getDescription());
            args.putString("license", repository.getLicense());
            args.putString("primaryLang", repository.getPrimaryLanguage().getName());
            args.putBoolean("isPrivate", repository.isPrivate());
            fragment.setArguments(args);
        }

        return fragment;
    }
}
