package com.example.tyler.helloworld;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
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

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.LegendRenderer;


public class SecondActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String prefName = "MyPrefs";
    private SharedPreferences preferenceSettingsUnique;
    private SharedPreferences.Editor preferenceEditorUnique;
    int id=0;
    int id2=0;
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
                //sends SleepyRaspberryPi a WINDOW_OPEN command
                //then enters Second Activity
                prefs = getSharedPreferences(prefName, 0);
                String mode = prefs.getString("mode","AUTO");

                if (mode.equals("MANUAL")) {
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
                else
                {
                    Toast.makeText(SecondActivity.this, "Please set mode to MANUAL to use.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends SleepyRaspberryPi a WINDOW_CLOSE command
                //then enters Second Activity
                prefs = getSharedPreferences(prefName, 0);
                String mode = prefs.getString("mode","AUTO");
                if(mode.equals("MANUAL")) {
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
                else
                {
                    Toast.makeText(SecondActivity.this, "Please set mode to MANUAL to use.", Toast.LENGTH_SHORT).show();
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
                String mode = spinner.getSelectedItem().toString();
                editor.putString("mode", mode);

                editor.apply();

               /* if(mode == "AUTO")
                {
                    Toast.makeText(SecondActivity.this, "Please make sure that the selected Auto temperature is correct.", Toast.LENGTH_LONG).show();

                    prefs = getSharedPreferences(prefName, 0);
                    preferenceEditorUnique = preferenceSettingsUnique.edit();

                    //sends SleepyRaspberryPi a SET_DESIRED_TEMP command
                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=SET_DESIRED_TEMP, " + preferenceSettingsUnique.getString("auto_temp", "60"));
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(SecondActivity.this, "Temperature Set to " + preferenceSettingsUnique.getString("auto_temp", "60"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                }*/

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        final List<String> list2 = new ArrayList<String>();
        list2.add("60");
        list2.add("62");
        list2.add("64");
        list2.add("66");
        list2.add("68");
        list2.add("70");
        list2.add("72");
        list2.add("74");
        list2.add("76");
        list2.add("78");
        list2.add("80");

        final Spinner spinner2 =(Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        prefs = getSharedPreferences(prefName, 0);
        id2 = prefs.getInt("last_val2", 0);
        spinner2.setSelection(id2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs = getSharedPreferences(prefName, 0);
                SharedPreferences.Editor editor = prefs.edit();
                id2 = prefs.getInt("last_val2",0);
                String mode = prefs.getString("mode", "AUTO");

                if (mode.equals("AUTO") & (id2 != position)) {

                    editor.putInt("last_val2", position);
                    String selected_item = spinner2.getSelectedItem().toString();
                    editor.putString("auto_temp", spinner2.getSelectedItem().toString());

                    editor.apply();

                    //sends SleepyRaspberryPi a SET_DESIRED_TEMP command
                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=SET_DESIRED_TEMP, " + selected_item);
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(SecondActivity.this, "Auto temperature has been set to " + selected_item + " F.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }

                }
                if (mode.equals("MANUAL") & (id2 != position))
                {
                    Toast.makeText(SecondActivity.this, "Please change mode to AUTO to change this setting.", Toast.LENGTH_SHORT).show();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        try{
            RetrieveWeather();
        }
        catch(Exception e)
        {

        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("Temperature Forecast");

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 48),
                new DataPoint(1, 48),
                new DataPoint(2, 48),
                new DataPoint(3, 47),
                new DataPoint(4, 47),
                new DataPoint(5, 47),
                new DataPoint(6, 48),
                new DataPoint(7, 48),
                new DataPoint(8, 48),
                new DataPoint(9, 48),
                new DataPoint(10, 57),
                new DataPoint(11, 57),
                new DataPoint(12, 57),
                new DataPoint(13, 64),
                new DataPoint(14, 64),
                new DataPoint(15, 64),
                new DataPoint(16, 66),
                new DataPoint(17, 66),
                new DataPoint(18, 66),
                new DataPoint(19, 62),
                new DataPoint(20, 62),
                new DataPoint(21, 62),
                new DataPoint(22, 57),
                new DataPoint(23, 57),
                new DataPoint(24, 57)
        });
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 65),
                new DataPoint(1.5, 65),
                new DataPoint(2, 65),
                new DataPoint(3, 65),
                new DataPoint(4, 65),
                new DataPoint(5, 65),
                new DataPoint(6, 65),
                new DataPoint(7, 65),
                new DataPoint(8, 65),
                new DataPoint(9, 65),
                new DataPoint(10, 65),
                new DataPoint(11, 65),
                new DataPoint(12, 65),
                new DataPoint(13, 65),
                new DataPoint(14, 65),
                new DataPoint(15, 65),
                new DataPoint(16, 65),
                new DataPoint(17, 65),
                new DataPoint(18, 65),
                new DataPoint(19, 65),
                new DataPoint(20, 65),
                new DataPoint(21, 65),
                new DataPoint(22, 65),
                new DataPoint(23, 65),
                new DataPoint(24, 65)
        });
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 70),
                new DataPoint(24, 70)
        });
        graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (F)");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (24 hour clock)");
        graph.addSeries(series);
        graph.addSeries(series2);
        graph.addSeries(series3);
        series.setTitle("Forecasted Temperature");
        series2.setTitle("Desired Temperature");
        series3.setTitle("Actual Temperature");
        Paint p1 = new Paint();
        Paint p2 = new Paint();
        Paint p3 = new Paint();
        series.setColor(Color.BLACK);
        series2.setColor(Color.BLUE);
        series3.setColor(Color.GREEN);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
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

    private void RetrieveWeather() throws IOException
    {
        String url = "http://api.openweathermap.org/data/2.5/weather?id=5359777&APPID=fefa0ed2dcc3abbe8f23b20837512564";
        WeatherServiceAsync2 task = new WeatherServiceAsync2(this);
        task.execute(url);
    }
    public void SetTemp(double temperature)
    {
        TextView view = (TextView) this.findViewById(R.id.outsideTemp);
        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTemperature = df.format(temperature);
        view.setText(formattedTemperature + "F");
    }
    public void setOutsideWeather(String forecast, double temperature)
    {
        TextView view = (TextView) this.findViewById(R.id.outsideTemp);
        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTemperature = df.format(temperature);
        view.setText(formattedTemperature + "F" + "\n" + forecast);
    }
    public void SetError()
    {
        TextView view2 = (TextView) this.findViewById(R.id.outsideTemp);
        view2.setText("Weather Unavailable.");
    }
}

