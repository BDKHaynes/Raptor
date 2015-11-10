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
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView seekBarValue = (TextView)findViewById(R.id.tempSetting);

        Button onButton = (Button)findViewById(R.id.thermostatOnButton);
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button=(Button) v;
                String currentText = button.getText().toString();
                switch(currentText) {
                    case "On":
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

    private class SocketTask extends AsyncTask {

        @Override
        protected String doInBackground(Object[] params) {
            Log.d("Here:", "Task happened");
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://192.168.0.166:8080");
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
                TextView seekBarValue = (TextView)findViewById(R.id.tempSetting);

                Log.d("Server Response", responseStr);
                return responseStr;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }


//        try {
//            Socket socket = new Socket("192.168.0.166", 8080);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            InputStream inputStream = socket.getInputStream();
//            while ((bytesRead = inputStream.read(buffer)) != -1){
//                byteArrayOutputStream.write(buffer,0,bytesRead);
//                response += byteArrayOutputStream.toString("UTF-8");
//            }
//            Log.d("Here:", "We got something " + response);
//        } catch (Exception e) {
//            Log.d("Here:", "failed to create socket " + e.toString());
//        }
            return null;
        }

    }
}
