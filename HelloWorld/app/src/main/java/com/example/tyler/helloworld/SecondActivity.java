package com.example.tyler.helloworld;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String prefName = "MyPrefs";
    int id=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar childToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(childToolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        /*Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThirdActivity();
            }
        });*/

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends SleepyRaspberryPi a WINDOW_CLOSE command
                //then enters Second Activity
                MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                String[] toArr = {"sleepyraspberrypi@gmail.com"};
                m.setTo(toArr);
                m.setFrom("sleepymrwindow@gmail.com");
                m.setSubject("REQUEST_ACTION_NOW=WINDOW_OPEN");
                m.setBody(" ");

                try {
                    if (m.send()) {
                        Toast.makeText(SecondActivity.this, "Window Opening Now.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("MailApp", "Could not send email", e);
                }

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends SleepyRaspberryPi a WINDOW_CLOSE command
                //then enters Second Activity
                MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                String[] toArr = {"sleepyraspberrypi@gmail.com"};
                m.setTo(toArr);
                m.setFrom("sleepymrwindow@gmail.com");
                m.setSubject("REQUEST_ACTION_NOW=WINDOW_CLOSE");
                m.setBody(" ");

                try {
                    if (m.send()) {
                        Toast.makeText(SecondActivity.this, "Window Closing Now.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("MailApp", "Could not send email", e);
                }

            }
        });

        final List<String> list = new ArrayList<String>();
        list.add("AUTO");
        list.add("MANUAL");

        final Spinner spinner =(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        prefs = getSharedPreferences(prefName, 0);
        id = prefs.getInt("last_val",0);
        spinner.setSelection(id);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs = getSharedPreferences(prefName, 0);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putInt("last_val", position);
                editor.putString("mode", spinner.getSelectedItem().toString());

                editor.apply();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void goToThirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Second Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tyler.helloworld/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Second Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tyler.helloworld/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}

