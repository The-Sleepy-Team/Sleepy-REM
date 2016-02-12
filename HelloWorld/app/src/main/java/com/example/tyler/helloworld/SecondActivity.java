package com.example.tyler.helloworld;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar childToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(childToolbar);

        android.support.v7.app.ActionBar ab =  getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThirdActivity();
            }
        });

    }

    private void goToThirdActivity()
    {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

}
