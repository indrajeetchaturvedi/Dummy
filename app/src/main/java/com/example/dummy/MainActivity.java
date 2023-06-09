package com.example.dummy;


import android.os.Build;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dummy.util.Constants;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;

public class MainActivity extends AppCompatActivity {

    public static PubNub pubnub; // Pubnub instance

    Button driverButton, passengerButton; // Buttons that redirect user to proper view

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        driverButton = (Button) findViewById(R.id.driverButton);
        passengerButton = (Button) findViewById(R.id.passengerButton);
        initPubnub();

        // Send user to Driver Activity or Passenger Activity using intents
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Driver.class));
            }
        });
        passengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Passanger.class));
            }
        });


        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.S_V2)
        {
            checkPermission();
        }
    }

    /*
        Creates PNConfiguration instance and enters Pubnub credentials to create Pubnub instance.
        This Pubnub instance will be used whenever we need to create connection to Pubnub.
     */
    private void initPubnub()
    {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(Constants.PUBNUB_SUBSCRIBE_KEY);
        pnConfiguration.setPublishKey(Constants.PUBNUB_PUBLISH_KEY);
        pnConfiguration.setSecure(true);
        pubnub = new PubNub(pnConfiguration);
        Log.d("config", String.valueOf(pubnub)) ;
    }

    /*
        Checks user's location permission to see whether user has granted access to fine location and coarse location.
        If not it will request these permissions.
     */
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

}