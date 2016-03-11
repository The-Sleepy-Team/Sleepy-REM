package com.example.tyler.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.text.InputFilter;
import android.text.Spanned;

public class Settings extends AppCompatActivity {

    private String mode = "WINDOWS";
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = getSharedPreferences("MyPrefs", 0);
        settingsEditor = settings.edit();

        final List<String> list = new ArrayList<String>();
        list.add("WINDOWS");
        list.add("BLINDS");

        final Spinner spinner =(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        final EditText hours = (EditText) findViewById(R.id.hours);
        hours.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
        final EditText minutes = (EditText) findViewById(R.id.minutes);
        minutes.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        final EditText percentage = (EditText) findViewById(R.id.percentage);
        percentage.setFilters(new InputFilter[]{new InputFilterMinMax("0", "100")});
        final TextView windows = (TextView) findViewById(R.id.windows);
        final TextView blinds = (TextView) findViewById(R.id.windows);

        if(settings.getString("WINDOWS", "") == ""){
            windows.setVisibility(View.GONE);
        }
        else{
            String windows_text = settings.getString("WINDOWS", "");
            windows.setText(windows_text);
            windows.setVisibility(View.VISIBLE);
        }
        if(settings.getString("BLINDS", "") == ""){
            blinds.setVisibility(View.GONE);
        }
        else{
            String blinds_text = settings.getString("BLINDS", "");
            blinds.setText(blinds_text);
            blinds.setVisibility(View.VISIBLE);
        }

        Button button3 = (Button) findViewById(R.id.button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mode + " " + hours.getText() + " " + minutes.getText() + " " + percentage.getText() + "\n";
                s = settings.getString(mode, "") + s;
                settingsEditor.putString(mode, s);
                settingsEditor.commit();

                if(settings.getString("WINDOWS", "") != ""){
                    String windows_text = settings.getString("WINDOWS", "");
                    windows.setText(windows_text);
                    windows.setVisibility(View.VISIBLE);
                }
                if(settings.getString("BLINDS", "") != ""){
                    String blinds_text = settings.getString("BLINDS", "");
                    blinds.setText(blinds_text);
                    blinds.setVisibility(View.VISIBLE);
                }
            }
        });


        Button button4 = (Button) findViewById(R.id.button2);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsEditor.putString("WINDOWS","");
                settingsEditor.putString("BLINDS","");
                settingsEditor.commit();
                windows.setVisibility(View.GONE);
                blinds.setVisibility(View.GONE);
            }
        });
    }

    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}
