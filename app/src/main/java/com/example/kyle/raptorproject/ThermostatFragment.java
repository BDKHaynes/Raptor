package com.example.kyle.raptorproject;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThermostatFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View content = inflater.inflate(R.layout.fragment_thermostat, container, false);
        Button onButton = (Button) content.findViewById(R.id.thermostatOnButton);
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button=(Button) v;
                String currentText = button.getText().toString();
                switch(currentText) {
                    case "On":
                        new SocketTask().execute();
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
        return content;
    }


private class SocketTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        Log.d("Here:", "Task happened");
        String response = null;
        try {
            Socket socket = new Socket("192.168.1.119", 8000);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer,0,bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
            Log.d("Here:", "We got something " + response);
        } catch (Exception e) {
            Log.d("Here:", "failed to create socket " + e.toString());
        }
        return null;
    }
}

}

