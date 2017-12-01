package com.developerxy.sqli_test.ui.fragments;


import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.adapters.RepositoryAdapter;
import com.developerxy.sqli_test.retrofit.RetrofitCallBuilder;
import com.developerxy.sqli_test.retrofit.listeners.FetchRepositoriesCallback;
import com.developerxy.sqli_test.retrofit.listeners.OnRepositoriesLoadedListener;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGitHubResponse;
import com.developerxy.sqli_test.utils.Constants;
import com.developerxy.sqli_test.utils.GridSpacingItemDecoration;

import java.util.List;

import retrofit2.Call;

public class RepositoriesListFragment extends Fragment implements OnRepositoriesLoadedListener {

    private List<QLGitHubRepository> repositories;
    private RepositoryAdapter mRepositoryAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Snackbar mSnackbar;
    /**
     * Switches between the RecyclerView & an empty view to be displayed in case the repositories' list was empty.
     */
    private ViewSwitcher viewSwitcher;

    public RepositoriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repositories_list, container, false);

        initializeUI(view); // required UI setup
        setupRecyclerView(); // setup the recyclerview
        setupRefreshListener(); // configure the behavior of the SwipeRefreshLayout

        // start fetching repositories asynchronously using the Retrofit GraphQLClient that was setup
        // and notify this fragment when finished
        fetchRepostories(this);

        return view;
    }

    @Override
    public void onLoadSucceeded(List<QLGitHubRepository> repositories) {
        int nbrOfRepositories = repositories.size();
        if (nbrOfRepositories == 0) {
            switch (viewSwitcher.getNextView().getId()) {
                case R.id.empty_view:
                    viewSwitcher.showNext();
                    break;
            }
        } else {
            switch (viewSwitcher.getNextView().getId()) {
                case R.id.recyclerView:
                    viewSwitcher.showNext();
                    break;
            }
        }

        this.repositories = repositories;
        mSwipeRefreshLayout.setRefreshing(false);
        populateRecyclerView();

        if (nbrOfRepositories != 0) {
            Snackbar.make(getActivity().getWindow().getDecorView(),
                    String.format("Displaying %d repositories.", nbrOfRepositories), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onLoadFailed(String errorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        mSnackbar = Snackbar.make(getActivity().getWindow().getDecorView(), "Loading failed: " + errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchRepostories(RepositoriesListFragment.this);
                    }
                });

        mSnackbar.show();
    }

    private void initializeUI(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        viewSwitcher = rootView.findViewById(R.id.switcher);
    }

    private void setupRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mSnackbar != null && mSnackbar.isShown())
                    mSnackbar.dismiss();
                fetchRepostories(RepositoriesListFragment.this);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void populateRecyclerView() {
        if (mRepositoryAdapter == null) {
            mRepositoryAdapter = new RepositoryAdapter(getActivity(), repositories);
            mRecyclerView.setAdapter(mRepositoryAdapter);
        } else
            mRepositoryAdapter.animateTo(repositories);
    }

    /**
     * Convert dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void fetchRepostories(OnRepositoriesLoadedListener listener) {
        String query = "{viewer {username: login repositories(first: " + Constants.NUMBER_OF_REPOSITORIES_PER_REQUEST +
                ") { pageInfo { hasNextPage endCursor } items: edges { " +
                "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";

        Call<QLGitHubResponse> initialCall = RetrofitCallBuilder.callForGithubRepositories(query);
        // Send an asynchronous request for the first bulk of repositories
        // If there are more repositories to be fetched, this initial call will subsequently setup & execute
        // other calls to fetch the rest
        initialCall.enqueue(new FetchRepositoriesCallback(listener));
    }

    public static Fragment newInstance() {
        return new RepositoriesListFragment();
    }

    /**
     * This method is invoked when the content of the host activity's SearchView changes.
     *
     * @param query the query, the content of the SearchView
     * @return boolean
     */
    public boolean onSearchQueryChanged(String query) {
        final List<QLGitHubRepository> filteredRepos = QLGitHubRepository.filter(repositories, query);
        mRepositoryAdapter.animateTo(filteredRepos);
        mRecyclerView.scrollToPosition(0);
        return true;
    }
}
