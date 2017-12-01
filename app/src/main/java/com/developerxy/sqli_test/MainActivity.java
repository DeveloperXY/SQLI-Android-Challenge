package com.developerxy.sqli_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String query = "{viewer {username: login repositories(first: 30) { items: edges { repository: node { name url createdAt description license primaryLanguage { name }}}}}}";
    }
}
