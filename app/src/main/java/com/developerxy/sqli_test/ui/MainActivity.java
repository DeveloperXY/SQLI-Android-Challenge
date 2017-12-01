package com.developerxy.sqli_test.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.application.GraphQLApplication;
import com.developerxy.sqli_test.retrofit.RetrofitCallBuilder;
import com.developerxy.sqli_test.retrofit.models.QLGitHubRepository;
import com.developerxy.sqli_test.ui.fragments.RepositoriesListFragment;
import com.developerxy.sqli_test.ui.fragments.RepositoryDetailsFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        RepositoriesListFragment.ReposListFragmentListener {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitCallBuilder.init(((GraphQLApplication) getApplication()));

        displayFragment(0); // Display the first fragment at startup
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified())
            searchView.setIconified(true);
        else {
            // If the back button was pressed & the user is actually at the details fragment,
            // send him back to the list fragment, else close the app
            Fragment fragment = getFragmentManager().findFragmentById(R.id.frame_container);
            if (fragment instanceof RepositoryDetailsFragment)
                displayFragment(0);
            else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Notify the repositories fragment that the content of the SearchView has changed
        return ((RepositoriesListFragment) getFragmentManager().findFragmentById(R.id.frame_container))
                .onSearchQueryChanged(query);
    }

    private void displayFragment(int position) {
        displayFragment(position, null);
    }

    private void displayFragment(int position, QLGitHubRepository repository) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (position) {
            case 0:
                fragment = RepositoriesListFragment.newInstance();
                ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                break;
            case 1:
                fragment = RepositoryDetailsFragment.newInstance(repository);
                ft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
                break;
        }

        ft.replace(R.id.frame_container, fragment, "fragment" + position);
        ft.commit();
    }

    @Override
    public void onRepositorySelected(QLGitHubRepository repository) {
        displayFragment(1, repository);
    }
}
