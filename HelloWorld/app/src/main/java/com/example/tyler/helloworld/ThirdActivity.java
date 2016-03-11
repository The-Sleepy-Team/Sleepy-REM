package com.example.tyler.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = getSharedPreferences("MyPrefs", 0);
        settingsEditor = settings.edit();

        final TextView text = (TextView) findViewById(R.id.blindStatus);

        final ImageView img = (ImageView) findViewById(R.id.blindPic);
        //receive blind data here
        //int status = 50;
        final int previous = settings.getInt("blinds_manual", 0);
        if (previous <= 40)
        {
            img.setImageResource(R.drawable.blinds_closed);
        }
        else if ((previous > 40) && (previous <= 75))
        {
            img.setImageResource(R.drawable.blinds_semi);
        }
        else
        {
            img.setImageResource(R.drawable.blinds_open);
        }

        Switch mySwitch = (Switch) findViewById(R.id.switch1);

        //set the switch to current status
        if (settings.getString("blinds_mode", "AUTO").equals("AUTO"))
        {
            mySwitch.setChecked(true);
        }
        else
        {
            mySwitch.setChecked(false);
        }

        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingsEditor.putString("blinds_mode", "AUTO");
                    settingsEditor.commit();

                    //sends SleepyRaspberryPi a SET_BLINDS_MODE command
                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=SET_BLINDS_MODE, AUTO");
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(ThirdActivity.this, "The blinds are switched to AUTO.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                } else {
                    settingsEditor.putString("blinds_mode", "MANUAL");
                    settingsEditor.commit();

                    //sends SleepyRaspberryPi a SET_BLINDS_MODE command
                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=SET_BLINDS_MODE, MANUAL");
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(ThirdActivity.this, "The blinds are switched to MANUAL.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                }
            }
        });

        //Check the current state
        if(mySwitch.isChecked())
        {
            settingsEditor.putString("blinds_mode", "AUTO");
            settingsEditor.commit();

            //sends SleepyRaspberryPi a SET_BLINDS_MODE command
            MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

            String[] toArr = {"sleepyraspberrypi@gmail.com"};
            m.setTo(toArr);
            m.setFrom("sleepymrwindow@gmail.com");
            m.setSubject("REQUEST_ACTION_NOW=SET_BLINDS_MODE, AUTO");
            m.setBody(" ");

            try {
                if (m.send()) {
                    Toast.makeText(ThirdActivity.this, "The blinds are on AUTO.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ThirdActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }
        }
        else
        {
            settingsEditor.putString("blinds_mode", "MANUAL");
            settingsEditor.commit();
            //sends SleepyRaspberryPi a SET_BLINDS_MODE command
            MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

            String[] toArr = {"sleepyraspberrypi@gmail.com"};
            m.setTo(toArr);
            m.setFrom("sleepymrwindow@gmail.com");
            m.setSubject("REQUEST_ACTION_NOW=SET_BLINDS_MODE, MANUAL");
            m.setBody(" ");

            try {
                if (m.send()) {
                    Toast.makeText(ThirdActivity.this, "The blinds are on MANUAL.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ThirdActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }

        }

        //Seekbar setup

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);

        seekBar.setProgress(previous);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int progress = previous;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                progress = progressValue;
                //text.setText(Integer.toString(progress));
                //Toast.makeText(ThirdActivity.this, "Changing seekbar's progress.", Toast.LENGTH_SHORT).show();
                if (progress <= 40) {
                    img.setImageResource(R.drawable.blinds_closed);
                } else if ((progress > 40) && (progress <= 75)) {
                    img.setImageResource(R.drawable.blinds_semi);
                } else {
                    img.setImageResource(R.drawable.blinds_open);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                //Toast.makeText(ThirdActivity.this, "Started tracking seekbar.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekbar)
            {
                //Toast.makeText(ThirdActivity.this, "Stopped tracking seekbar.", Toast.LENGTH_SHORT).show();
                if (settings.getString("blinds_mode", "AUTO").equals("MANUAL")) {
                    settingsEditor.putInt("blinds_manual", progress);
                    settingsEditor.commit();

                    if (progress <= 40) {
                        img.setImageResource(R.drawable.blinds_closed);
                    } else if ((progress > 40) && (progress <= 75)) {
                        img.setImageResource(R.drawable.blinds_semi);
                    } else {
                        img.setImageResource(R.drawable.blinds_open);
                    }

                    MailSend m = new MailSend("sleepymrwindow@gmail.com", "123abc123ABC");

                    String[] toArr = {"sleepyraspberrypi@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("sleepymrwindow@gmail.com");
                    m.setSubject("REQUEST_ACTION_NOW=BLINDS_OPEN_POSITION, " + Integer.toString(progress));
                    m.setBody(" ");

                    try {
                        if (m.send()) {
                            Toast.makeText(ThirdActivity.this, "The blinds will open to " + Integer.toString(progress) + "%", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Communication Error.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }
                }
                else{
                    Toast.makeText(ThirdActivity.this, "Please set mode to MANUAL.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
