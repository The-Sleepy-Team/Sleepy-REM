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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SeekBar;
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
    String[] graphData = {"GRAPH_DATA"};
    String[] tempData = {"TEMPERATURE"};
    public static final String INITIALIZED = "initialized2";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Spinner spinner;
    private String graph_data = "";
    private String temp_data = "";
    private String temp = "";

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
        prefs = getSharedPreferences(prefName, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();

        boolean initialized = prefs.getBoolean(INITIALIZED, Boolean.FALSE);

        if (!initialized) {

            //prefsEditor.putBoolean(INITIALIZED, Boolean.TRUE);

            try{
                graph_data =  new MailRead().execute(graphData).get();
                for(String retval: graph_data.split("\n")){
                    retval = retval.trim();
                    if(retval.equals("PREDICTIONS:")){
                        //Do nothing
                    }
                    else if(retval.equals("DESIRED_TEMP:")){
                        prefsEditor.putString("PREDICTIONS", temp);
                        prefsEditor.commit();
                        temp = "";
                    }
                    else if(retval.equals("LOG:")) {
                        prefsEditor.putString("DESIRED_TEMP", temp);
                        prefsEditor.commit();
                        temp = "";
                    }
                    else{
                        temp += retval + "\n";
                        //System.out.println(temp.substring(0,3));
                    }
                }
                prefsEditor.putString("LOG", temp);

                temp_data = new MailRead().execute(tempData).get();
                TextView inside_temp = (TextView) this.findViewById(R.id.insideTemp);
                prefsEditor.putString("inside_temp", temp_data.trim());
                inside_temp.setText(temp_data.trim() + "F");


                prefsEditor.commit();
            }
            catch(Exception e){
                System.out.println(e.toString());
            }

        }

        TextView inside_temp = (TextView) this.findViewById(R.id.insideTemp);
        inside_temp.setText(prefs.getString("inside_temp", "-") + "F");

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends SleepyRaspberryPi a WINDOW_OPEN command
                //then enters Second Activity
                prefs = getSharedPreferences(prefName, 0);
                SharedPreferences.Editor prefsEditor = prefs.edit();

                String mode = prefs.getString("mode","AUTO");

                if (mode.equals("MANUAL")) {

                    prefsEditor.putInt("windows_manual", 100);
                    prefsEditor.commit();

                    SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);

                    seekBar.setProgress(100);

                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=WINDOW_POSITION, 100");
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
                //sends SleepyRaspberryPi a WINDOW_OPEN command
                //then enters Second Activity
                prefs = getSharedPreferences(prefName, 0);
                SharedPreferences.Editor prefsEditor = prefs.edit();

                String mode = prefs.getString("mode","AUTO");

                if (mode.equals("MANUAL")) {

                    prefsEditor.putInt("windows_manual", 0);
                    prefsEditor.commit();

                    SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);

                    seekBar.setProgress(0);

                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=WINDOW_POSITION, 0");
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

               if(mode.equals("AUTO"))
                {
                    //sends SleepyRaspberryPi a SET_DESIRED_TEMP command
                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=SET_MODE, AUTO");
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(SecondActivity.this, "Please make sure that the selected Auto temperature is correct.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                }
                else {
                   //sends SleepyRaspberryPi a SET_DESIRED_TEMP command
                   MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                   String[] toArr = {"sleepyraspberrypi@gmail.com"};
                   m.setTo(toArr);
                   m.setFrom("sleepymrwindow@gmail.com");
                   m.setSubject("REQUEST_ACTION_NOW=SET_MODE, MANUAL");
                   m.setBody(" ");

                   try {
                       if (m.send()) {
                           Toast.makeText(SecondActivity.this, "Manual Mode Activated.", Toast.LENGTH_LONG).show();
                       } else {
                           Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                       }
                   } catch (Exception e) {
                       //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                       Log.e("MailApp", "Could not send email", e);
                   }
               }

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
        //prefs = getSharedPreferences(prefName, 0);
        //SharedPreferences.Editor prefsEditor = prefs.edit();

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(24);
        graph.getViewport().setScrollable(true);

        String[] predictions = prefs.getString("PREDICTIONS", "").split("\n");
        DataPoint[] datapoints = new DataPoint[predictions.length+1];
        int counter = 0;
        for(String s: predictions){
            String[] s2 = s.split(", ");
            datapoints[counter] = new DataPoint(Float.parseFloat(s2[0]), Float.parseFloat(s2[1]));
            counter++;
        }
        datapoints[counter] = new DataPoint(24.0, datapoints[counter-1].getY());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datapoints);

        String[] desired_temp = prefs.getString("DESIRED_TEMP", "").split("\n");
        DataPoint[] datapoints2 = new DataPoint[2];
        datapoints2[0] = new DataPoint(0.0, Float.parseFloat(desired_temp[0]));
        datapoints2[1] = new DataPoint(24.0, Float.parseFloat(desired_temp[0]));
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(datapoints2);


        String[] log = prefs.getString("LOG", "").split("\n");
        DataPoint[] datapoints3 = new DataPoint[log.length];
        counter = 0;

        for(String s: log){
            String[] s2 = s.split(", ");
            datapoints3[counter] = new DataPoint(Float.parseFloat(s2[0]), Float.parseFloat(s2[1]));
            counter++;
        }
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(datapoints3);

        graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (F)");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (24 hour clock)");
        graph.getGridLabelRenderer().setPadding(30);
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
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graph.getLegendRenderer().setTextSize(16);
        //graph.setPadding(100, 100, 100, 100);


        //Seekbar setup
        final SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
        final SharedPreferences.Editor settingsEditor = settings.edit();

        final int previous = settings.getInt("windows_manual", 0);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);

        seekBar.setProgress(previous);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = previous;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                //text.setText(Integer.toString(progress));
                //Toast.makeText(ThirdActivity.this, "Changing seekbar's progress.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(ThirdActivity.this, "Started tracking seekbar.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekbar) {
                //Toast.makeText(ThirdActivity.this, "Stopped tracking seekbar.", Toast.LENGTH_SHORT).show();
                if (settings.getString("mode", "AUTO").equals("MANUAL")) {
                    settingsEditor.putInt("windows_manual", progress);
                    settingsEditor.commit();


                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=WINDOW_POSITION, " + Integer.toString(progress));
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(SecondActivity.this, "The windows will open to " + Integer.toString(progress) + "%", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SecondActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                } else {
                    Toast.makeText(SecondActivity.this, "Please set mode to MANUAL.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
        final SharedPreferences.Editor settingsEditor = settings.edit();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.sync_setting) {


            try{

                temp_data = new MailRead().execute(tempData).get();
                TextView inside_temp = (TextView) this.findViewById(R.id.insideTemp);
                settingsEditor.putString("inside_temp", temp_data.trim());
                inside_temp.setText(temp_data.trim() + "F");

                String graph_data = "";
                String temp = "";
                graph_data =  new MailRead().execute(graphData).get();
                for(String retval: graph_data.split("\n")){
                    if(retval.equals("PREDICTIONS:")){
                        //Do nothing
                    }
                    else if(retval.equals("DESIRED_TEMP:")){
                        settingsEditor.putString("PREDICTIONS", temp);
                        settingsEditor.commit();
                        temp = "";
                    }
                    else if(retval.equals("LOG:")) {
                        settingsEditor.putString("DESIRED_TEMP", temp);
                        settingsEditor.commit();
                        temp = "";
                    }
                    else{
                        temp += retval + "\n";
                    }
                }
                settingsEditor.putString("LOG", temp);
                settingsEditor.commit();
            }
            catch(Exception e){

            }
            GraphView graph = (GraphView) findViewById(R.id.graph);
            graph.setTitle("Temperature Forecast");

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(24);
            graph.getViewport().setScrollable(true);

            DataPoint[] datapoints = new DataPoint[24];
            int counter = 0;
            for(String s: prefs.getString("PREDICTIONS", "").split("\n")){
                String[] s2 = s.split(", ");
                datapoints[counter] = new DataPoint(Float.parseFloat(s2[0]), Float.parseFloat(s2[1]));
                counter++;
            }
            datapoints[counter] = new DataPoint(24.0, datapoints[counter-1].getY());
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datapoints);

            String[] desired_temp = prefs.getString("DESIRED_TEMP", "").split("\n");
            DataPoint[] datapoints2 = new DataPoint[2];
            datapoints2[0] = new DataPoint(0.0, Float.parseFloat(desired_temp[0]));
            datapoints2[1] = new DataPoint(24.0, Float.parseFloat(desired_temp[0]));
            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(datapoints2);


            String[] log = prefs.getString("DESIRED_TEMP", "").split("\n");
            DataPoint[] datapoints3 = new DataPoint[log.length];
            counter = 0;

            for(String s: log){
                String[] s2 = s.split(", ");
                datapoints3[counter] = new DataPoint(Float.parseFloat(s2[0]), Float.parseFloat(s2[1]));
                counter++;
            }
            LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(datapoints3);

            graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (F)");
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (24 hour clock)");
            graph.getGridLabelRenderer().setPadding(30);
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
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            graph.getLegendRenderer().setTextSize(16);
        }


        return super.onOptionsItemSelected(item);
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

