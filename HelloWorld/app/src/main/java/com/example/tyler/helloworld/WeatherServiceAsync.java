package com.example.tyler.helloworld;

import android.os.AsyncTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tyler on 1/30/2016.
 */
public class WeatherServiceAsync extends AsyncTask<String, Void, String>{

    private final MainActivity WeatherActivity;

    public WeatherServiceAsync(MainActivity weatherActivity)
    {
        this.WeatherActivity = weatherActivity;
    }

    @Override
    protected String doInBackground(String... urls){
        String response = "";

        for (String url : urls)
        {
            HttpURLConnection con = null ;
            InputStream is = null;

            try{
                con = (HttpURLConnection)(new URL(url)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                // Let's read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while (  (line = br.readLine()) != null )
                    buffer.append(line + "\r\n");

                is.close();
                con.disconnect();
                return buffer.toString();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            finally {
                try {
                    is.close();
                } catch (Throwable t) {
                }
                try {
                    con.disconnect();
                } catch (Throwable t) {
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        String test = result;
        try{
            JSONObject jsonResult = new JSONObject(test);

            double temp = jsonResult.getJSONObject("main").getDouble("temp");
            temp = ConvertTemperatureToFarenheit(temp);
            this.WeatherActivity.SetTemp(temp);

/*            double temp_min = jsonResult.getJSONObject("main").getDouble("temp_min");
            temp_min = ConvertTemperatureToFarenheit(temp_min);
            this.WeatherActivity.SetMinTemp(temp_min);*/

/*            double temp_max = jsonResult.getJSONObject("main").getDouble("temp_max");
            temp_max = ConvertTemperatureToFarenheit(temp_max);
            this.WeatherActivity.SetMaxTemp(temp_max);*/

            double pressure = jsonResult.getJSONObject("main").getDouble("pressure");

            double humidity = jsonResult.getJSONObject("main").getDouble("humidity");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private double ConvertTemperatureToFarenheit(double temperature) {
        return (temperature * (1.8))-459.67;
    }
}
