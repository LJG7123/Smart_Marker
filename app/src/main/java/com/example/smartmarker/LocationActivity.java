package com.example.smartmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartmarker.repositories.RepositoryAccount;

public class LocationActivity extends AppCompatActivity {

    private RepositoryAccount repositoryAccount = RepositoryAccount.getInstance();
    private EditText round_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        round_setting = (EditText) findViewById(R.id.round_setting);



    }

    public void check(View view)
    {
        String roundsetting = round_setting.getText().toString();

        repositoryAccount.setRange(roundsetting);

        Intent intent = new Intent(LocationActivity.this, MainActivity.class);
        startActivity(intent);

    }
}