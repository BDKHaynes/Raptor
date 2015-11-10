package com.example.kyle.raptorproject;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThermostatFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
//
        View content = inflater.inflate(R.layout.fragment_thermostat, container, false);
//        Button onButton = (Button) content.findViewById(R.id.thermostatOnButton);
//        onButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Button button=(Button) v;
//                String currentText = button.getText().toString();
//                switch(currentText) {
//                    case "On":
//                        new SocketTask().execute();
//                        button.setText("Off");
//                        break;
//                    case "Off":
//                        button.setText("On");
//                        break;
//                    default:
//                        button.setText("Error");
//                }
//            }
//        });
        return content;
//    }


//private class SocketTask extends AsyncTask {
//
//    @Override
//    protected Object doInBackground(Object[] params) {
//        Log.d("Here:", "Task happened");
//        HttpURLConnection urlConnection = null;
//
//        try {
//            URL url = new URL("http://www.android.com/");
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            StringBuffer response = new StringBuffer();
//            while ((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
//            String responseStr = response.toString();
//            Log.d("Server Response", responseStr);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally{
//                urlConnection.disconnect();
//            }
//
//
//
//
////        try {
////            Socket socket = new Socket("192.168.0.166", 8080);
////            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
////            byte[] buffer = new byte[1024];
////            int bytesRead;
////            InputStream inputStream = socket.getInputStream();
////            while ((bytesRead = inputStream.read(buffer)) != -1){
////                byteArrayOutputStream.write(buffer,0,bytesRead);
////                response += byteArrayOutputStream.toString("UTF-8");
////            }
////            Log.d("Here:", "We got something " + response);
////        } catch (Exception e) {
////            Log.d("Here:", "failed to create socket " + e.toString());
////        }
////        return null;
//    }
//}
    }
}

