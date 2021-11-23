package com.android.myworks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.format.Formatter;
import android.util.Log;

import com.android.myworks.ui.bottom_sheet.BottomSheetFragment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        long vibratePattern[] = {0, 3500};
        v.vibrate(vibratePattern, 0);

         MediaPlayer mp = MediaPlayer.create(this, R.raw.soho);
         mp.start();


    }

    public class GetPublicIP extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String publicIP = "";
            try  {
                java.util.Scanner s = new java.util.Scanner(
                        new java.net.URL(
                                "https://api.ipify.org")
                                .openStream(), "UTF-8")
                        .useDelimiter("\\A");
                publicIP = s.next();
                System.out.println("My current IP address is " + publicIP);
                Log.e("SCENE", "My current IP address is: "+ publicIP );
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            return publicIP;
        }

        @Override
        protected void onPostExecute(String publicIp) {
            super.onPostExecute(publicIp);

            Log.e("PublicIP", publicIp+"");
            //Here 'publicIp' is your desire public IP
        }
    }
}