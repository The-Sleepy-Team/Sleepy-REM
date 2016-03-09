package com.example.tyler.helloworld;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView img = (ImageView) findViewById(R.id.blindPic);
        //receive blind data here
        int status = 50;
        if (status <= 40)
        {
            img.setImageResource(R.drawable.blinds_closed);
        }
        else if ((status > 40) && (status <= 75))
        {
            img.setImageResource(R.drawable.blinds_semi);
        }
        else
        {
            img.setImageResource(R.drawable.blinds_open);
        }
    }

}
