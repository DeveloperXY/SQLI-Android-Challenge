package com.developerxy.sqli_test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.developerxy.sqli_test.R;
import com.developerxy.sqli_test.retrofit.GraphQLClient;
import com.developerxy.sqli_test.retrofit.ServiceGenerator;
import com.developerxy.sqli_test.retrofit.models.QLGithubRepository;
import com.developerxy.sqli_test.retrofit.models.QLGithubResponse;
import com.developerxy.sqli_test.retrofit.models.QLQuery;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<QLGithubRepository> repositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String query = "{viewer {username: login repositories(first: 30) { items: edges { " +
                "repository: node { name url createdAt description license primaryLanguage { name } isPrivate}}}}}";
        QLQuery qlQuery = new QLQuery(query);
        System.out.println(qlQuery);

        String access_token = getResources().getString(R.string.github_token);
        Log.e("GitHub Personal Token", access_token);

        GraphQLClient client = ServiceGenerator.createService(GraphQLClient.class, access_token);
        client.getAllRepositories(qlQuery)
                .enqueue(new Callback<QLGithubResponse>() {
                    @Override
                    public void onResponse(Call<QLGithubResponse> call, Response<QLGithubResponse> res) {
                        QLGithubResponse response = res.body();
                        if (response != null) {
                            repositories = response.extractRepositories();
                            Log.i("GraphQLClient", String.format("Retrieved %d repositories.", repositories.size()));
                        } else {
                            Log.i("GraphQLClient", "GitHub response is null.");
                        }
                    }

                    @Override
                    public void onFailure(Call<QLGithubResponse> call, Throwable t) {
                        System.out.println("Server error: " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Server error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
