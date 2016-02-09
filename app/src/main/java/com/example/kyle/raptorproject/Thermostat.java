package com.example.kyle.raptorproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Thermostat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat);

        //SeekBar for selecting thermostat values
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        //TextView for displaying SeekBar value
        final TextView seekBarValue = (TextView) findViewById(R.id.goalTemp);

        //Button sets thermostat value or turns off thermostat
        final Button offButton = (Button) findViewById(R.id.thermostatOffButton);

        Button setButton = (Button) findViewById(R.id.thermostatSetButton);

        //Add Button Click Listener to monitor if button is clicked
        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String currentText = button.getText().toString();
                switch (currentText) {
                    case "On": //Click Button To Turn Off Thermostat

                        button.setText("Off");
                        break;
                    case "Off":
                        button.setText("On");
                        break;
                    default:
                        button.setText("Error");
                }
            }
        });

        //Add Button Click Listener to monitor if button is clicked
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String currentText = button.getText().toString();
                SocketTask task = new SocketTask();
                String responseStr = null;
                try {
                    responseStr = (String) task.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                seekBarValue.setText(responseStr);
                offButton.setText("On"); //whenever you attempt to set a value the thermostat should be on
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        getTemperature();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thermostat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTemperature() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SocketTask task = new SocketTask();
                    String responseStr = null;

                    task.execute("getTemperature");


                }
            }
        }).start();

    }

    private class getTemperatureClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return "";
        }

        protected void onPostExecute(String result) {
            TextView seekBarValue = (TextView) findViewById(R.id.currentTemp);
            seekBarValue.setText(result);
        }
    }

    private class SocketTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... uri) {
            Log.d("Here:", "Task happened");
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://192.168.1.123:8080/" + uri[0]);
                Log.d("SocketTask:", "Attempting to connect to " + url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                String responseStr = response.toString();
                TextView seekBarValue = (TextView) findViewById(R.id.currentTemp);

                Log.d("Server Response", responseStr);
                return responseStr;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Exception", e.getMessage());
            } finally {
                urlConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String responseStr) {
            TextView seekBarValue = (TextView) findViewById(R.id.currentTemp);
            seekBarValue.setText(responseStr);
        }

    }
}
