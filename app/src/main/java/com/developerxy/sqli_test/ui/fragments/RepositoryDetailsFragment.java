package com.developerxy.sqli_test.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;
import com.developerxy.sqli_test.utils.DateUtils;

public class RepositoryDetailsFragment extends Fragment {

    private TextView repoNameText;
    private TextView urlText;
    private TextView createdAtText;
    private TextView descriptionText;
    private TextView licenseText;
    private TextView languageText;

    public RepositoryDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repository_details, container, false);
        initializeUI(view);

        Bundle args = getArguments();
        if (args != null) {
            repoNameText.setText(args.getString("name"));
            urlText.setText(args.getString("url"));

            String dateString = args.getString("createdAt");
            createdAtText.setText(DateUtils.parseTZ(dateString));

            String description = args.getString("description");
            descriptionText.setText(description == null ? "No description available" : description);

            String license = args.getString("license");
            licenseText.setText(license == null ? "No license" : license);

            String primaryLanguage = args.getString("primaryLang");
            languageText.setText(primaryLanguage == null ? "Primary language info not available" : primaryLanguage);
        }

        return view;
    }

    private void initializeUI(View view) {
        repoNameText = view.findViewById(R.id.repoText);
        urlText = view.findViewById(R.id.urlText);
        createdAtText = view.findViewById(R.id.createAtText);
        descriptionText = view.findViewById(R.id.descriptionText);
        licenseText = view.findViewById(R.id.licenseText);
        languageText = view.findViewById(R.id.languageText);
    }

    public static Fragment newInstance(QLGitHubRepository repository) {
        RepositoryDetailsFragment fragment = new RepositoryDetailsFragment();

        if (repository != null) {
            Bundle args = new Bundle();
            args.putString("name", repository.getName());
            args.putString("url", repository.getUrl());
            args.putString("createdAt", repository.getCreatedAt());
            args.putString("description", repository.getDescription());
            args.putString("license", repository.getLicense());
            args.putString("primaryLang", repository.getPrimaryLanguage().getName());
            fragment.setArguments(args);
        }

        return fragment;
    }
}
