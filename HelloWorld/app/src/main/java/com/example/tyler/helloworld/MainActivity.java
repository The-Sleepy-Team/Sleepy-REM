package com.example.tyler.helloworld;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.tyler.helloworld.Location;
import com.example.tyler.helloworld.Weather;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.System.out;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.*;
import javax.activation.*;

public class MainActivity extends AppCompatActivity {

/*    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;

    private TextView hum;
    private ImageView imgView;

    private TextView maxTemp;
    private TextView minTemp;*/

    //private TextView temp;

    private SharedPreferences preferenceSettingsUnique;
    private SharedPreferences.Editor preferenceEditorUnique;

    public static final String INITIALIZED = "initialized";

    //For MailRead to retrieve Async readings
    private String mail_content;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //For window and blind positions
    String window_cont = "";
    String blind_cont = "";
    String[] WINDOW_POSITION = {"WINDOW_POSITION"};
    String[] BLIND_POSITION = {"BLIND_POSITION"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //String city = "London,UK";

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Preferences pref = new Preferences(getApplicationContext());

        preferenceSettingsUnique = getSharedPreferences("MyPrefs", 0);
        preferenceEditorUnique = preferenceSettingsUnique.edit();

        //Here for testing purposes
        //preferenceEditorUnique.clear().commit();

        boolean initialized = preferenceSettingsUnique.getBoolean(INITIALIZED, Boolean.FALSE);

        if (!initialized) {

            preferenceEditorUnique.putBoolean(INITIALIZED, Boolean.TRUE);

            preferenceEditorUnique.putInt("last_val", 0);
            preferenceEditorUnique.putString("mode", "AUTO");
            preferenceEditorUnique.putInt("last_val2", 0);
            preferenceEditorUnique.putString("auto_temp", "60");
            preferenceEditorUnique.putString("blinds_mode", "AUTO");
            preferenceEditorUnique.putInt("blinds_manual", 0);
            preferenceEditorUnique.putInt("windows_manual", 0);
            try {
                window_cont = new MailRead().execute(WINDOW_POSITION).get();
                blind_cont = new MailRead().execute(BLIND_POSITION).get();
                TextView window_pos = (TextView) this.findViewById(R.id.current_status4);
                window_pos.setText(window_cont.trim() + "%");
                preferenceEditorUnique.putString("window_status", window_cont.trim());
                TextView blind_pos = (TextView) this.findViewById(R.id.current_status5);
                blind_pos.setText(blind_cont.trim() + "%");
                preferenceEditorUnique.putString("blind_status", blind_cont.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            preferenceEditorUnique.commit();
        }

        //Here for testing purposes
        //preferenceEditorUnique.clear().commit();

        TextView view = (TextView) this.findViewById(R.id.current_status6);
        if (preferenceSettingsUnique.getString("mode", "AUTO").equals("AUTO")) {
            view.setText("Windows are currently set to " + preferenceSettingsUnique.getString("mode", "AUTO") + ", " + preferenceSettingsUnique.getString("auto_temp", "60") + "F");
        } else {
            view.setText("Windows are currently set to " + preferenceSettingsUnique.getString("mode", "AUTO"));
        }

        TextView view2 = (TextView) this.findViewById(R.id.current_status7);
        if (preferenceSettingsUnique.getString("blinds_mode", "AUTO").equals("AUTO")) {
            view2.setText("Blinds are currently set to " + preferenceSettingsUnique.getString("blinds_mode", "AUTO"));
        } else {
            view2.setText("Blinds are currently set to " + preferenceSettingsUnique.getString("blinds_mode", "AUTO"));
        }

        TextView window_pos = (TextView) this.findViewById(R.id.current_status4);
        window_pos.setText(preferenceSettingsUnique.getString("window_status", "-") + "%");

        TextView blind_pos = (TextView) this.findViewById(R.id.current_status5);
        blind_pos.setText(preferenceSettingsUnique.getString("blind_status", "-") + "%");


        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSecondActivity();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThirdActivity();
            }
        });

        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("MailApp", "Could not send email", e);
                }

                goToSecondActivity();
            }
        });

        //cityText = (TextView) findViewById(R.id.cityText);
        /*
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.condIcon);*/

        //maxTemp = (TextView) findViewById(R.id.textView);
        //temp = (TextView) findViewById(R.id.textView);

        //JSONWeatherTask task = new JSONWeatherTask();
        //task.execute(new String[]{city});
        try {
            RetrieveWeather();
        } catch (Exception e) {
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.sync_setting) {
            preferenceSettingsUnique = getSharedPreferences("MyPrefs", 0);
            preferenceEditorUnique = preferenceSettingsUnique.edit();
            try {
                window_cont = new MailRead().execute(WINDOW_POSITION).get();
                blind_cont = new MailRead().execute(BLIND_POSITION).get();
                TextView window_pos = (TextView) this.findViewById(R.id.current_status4);
                window_pos.setText(window_cont.trim() + "%");
                preferenceEditorUnique.putString("window_status", window_cont.trim());
                TextView blind_pos = (TextView) this.findViewById(R.id.current_status5);
                blind_pos.setText(blind_cont.trim() + "%");
                preferenceEditorUnique.putString("blind_status", blind_cont.trim());
                preferenceEditorUnique.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void goToThirdActivity() {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    /* private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

         @Override
         protected Weather doInBackground(String... params) {
             Weather weather = new Weather();
             String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

             try {
                 weather = JSONWeatherParser.getWeather(data);

                 // Let's retrieve the icon
                 weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

             } catch (JSONException e) {
                 e.printStackTrace();
             }
             return weather;

         }


         @Override
         protected void onPostExecute(Weather weather) {
             super.onPostExecute(weather);

             if (weather.iconData != null && weather.iconData.length > 0) {
                 Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                 imgView.setImageBitmap(img);
             }

             *//*cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");*//*

            double tempTemp = (weather.temperature.getMaxTemp() - 273.15) * ((9/5) + 32);

            double tempTemp2 = (weather.temperature.getMinTemp() - 273.15) * ((9/5) + 32);

            maxTemp.setText("" + Math.round(tempTemp) + "F");
            minTemp.setText("" + Math.round(tempTemp2) + "F");

        }
    }*/
    private void RetrieveWeather() throws IOException {
        String url = "http://api.openweathermap.org/data/2.5/weather?id=5359777&APPID=fefa0ed2dcc3abbe8f23b20837512564";

        WeatherServiceAsync task = new WeatherServiceAsync(this);

        task.execute(url);
    }

    public void SetTemp(double temperature) {
        TextView view = (TextView) this.findViewById(R.id.textView);

        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTemperature = df.format(temperature);

        view.setText(formattedTemperature + "F");
    }

    public void SetError() {
        TextView view2 = (TextView) this.findViewById(R.id.textView);
        view2.setText("Weather Unavailable.");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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

/*    public void SetMaxTemp(double temperature)
    {
        TextView view = (TextView) this.findViewById(R.id.textView2);

        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTemperature = df.format(temperature);

        view.setText("H: " + formattedTemperature + "F");
    }

        public void SetMinTemp(double temperature)
    {
        TextView view = (TextView) this.findViewById(R.id.textView);

        DecimalFormat df = new DecimalFormat("###.##");
        String formattedTemperature = df.format(temperature);

        view.setText("L: " + formattedTemperature + "F");
    }
    */
}
