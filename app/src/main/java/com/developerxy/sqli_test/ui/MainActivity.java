package com.developerxy.sqli_test.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Toast;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.application.GraphQLApplication;
import com.developerxy.sqli_test.retrofit.RetrofitCallBuilder;
import com.developerxy.sqli_test.retrofit.listeners.FetchRepositoriesCallback;
import com.developerxy.sqli_test.retrofit.listeners.OnRepositoriesLoadedListener;
import com.developerxy.sqli_test.retrofit.models.QLGithubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.utils.Constants;
import com.developerxy.sqli_test.utils.GridSpacingItemDecoration;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private List<QLGithubRepository> repositories;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitCallBuilder.init(((GraphQLApplication) getApplication()));

        initializeUI(); // required UI setup
        setupRecyclerView(); // setup the activity's recyclerview
        // start fetching repositories asynchronously using the Retrofit GraphQLClient that was setup
        fetchRepostories(new OnRepositoriesLoadedListener() {
            @Override
            public void onLoadSucceeded(List<QLGithubRepository> repositories) {

            }

            @Override
            public void onLoadFailed(String errorMessage) {
                Toast.makeText(MainActivity.this, "Loading failed: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchRepostories(OnRepositoriesLoadedListener listener) {
        String query = "{viewer {username: login repositories(first: " + Constants.NUMBER_OF_REPOSITORIES_PER_REQUEST +
                ") { pageInfo { hasNextPage endCursor } items: edges { " +
                "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";

        Call<QLGithubResponse> initialCall = RetrofitCallBuilder.callForGithubRepositories(query);
        // Send an asynchronous request for the first bulk of repositories
        // If there are more repositories to be fetched, this initial call will subsequently setup & execute
        // other calls to fetch the rest
        initialCall.enqueue(new FetchRepositoriesCallback(listener));
    }

    private void initializeUI() {
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
