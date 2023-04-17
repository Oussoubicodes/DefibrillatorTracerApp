package com.example.defibrillatortracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest.Builder;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_UPDATE_INTERVAL = 30;
    private static final int FAST_UPDATE_INTERVAL = 5;
    private static final int PERMISSIONS_FINE_LOCATION = 99;

    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_address, tv_updates, tv_noDefibs;

    Switch sw_locationupdates, sw_gps;

    Button btn_newLocation, btn_showLocations , btn_showMap , btn_help;

    //API's for location service. Majority of features in app use this
    FusedLocationProviderClient fusedLocationProviderClient;

    //variable to see if we are tracking location or not
    boolean updateOn = false;

    //currentLocation
    Location currentLocation;

    //list of saved locations
    List<Location> savedLocations;

    //Location request is a config file for all setting to FusedLocationProvider

    private LocationRequest locationRequest;
    private LocationRequest.Builder builder;


    LocationCallback locationCallBack;


    //@SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_address = findViewById(R.id.tv_address);
        tv_updates = findViewById(R.id.tv_updates);
        sw_locationupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);
        btn_newLocation = findViewById(R.id.saveLocation);
        btn_showLocations = findViewById(R.id.showLocations);
        tv_noDefibs = findViewById(R.id.noDefibs);
        btn_showMap = findViewById(R.id.btn_ShowMap);
        btn_help = findViewById(R.id.btn_help);

        //set all properties of LocationRequest

        //Default Location Check Occurs every 30000ms
        builder = new LocationRequest.Builder(DEFAULT_UPDATE_INTERVAL * 1000)
                //Checks every 10000ms when set to the most frequent update
                .setMinUpdateIntervalMillis(FAST_UPDATE_INTERVAL * 1000)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest = builder.build();

        //Checks every 10000ms when set to the most frequent update
        // locationRequest.setMinUpdateIntervalMillis(1000 * FAST_UPDATE_INTERVAL);

        //Triggered whenever location update interval is met
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                updateUIValues(locationResult.getLastLocation());
            }
        };

        btn_newLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the gps location
                MyApplication myApplication = (MyApplication) getApplicationContext();
                savedLocations = myApplication.getMyLocations();
                savedLocations.add(currentLocation);

            }
        });

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HelpScreen.class);
                startActivity(i);
            }
        });

       btn_showLocations.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,showDefibLocationsList.class);
               startActivity(i);
           }
       });

       btn_showMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(MainActivity.this,MapsActivity.class);
               startActivity(i);
           }
       });{

        }

        sw_gps.setOnClickListener((View v) -> {
            if (sw_gps.isChecked()) {
                //most accurate - use GPS
                builder.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                locationRequest = builder.build();
                tv_sensor.setText("Using GPS sensors");
            } else {
                builder.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                locationRequest = builder.build();
                tv_sensor.setText("Using Towers + WIFI");
            }


            sw_locationupdates.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sw_locationupdates.isChecked()) {
                        startLocationUpdates();
                    } else {
                        stopLocationUpdates();
                    }
                }
            }));

            updateGPS();
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        tv_updates.setText("Location is being tracked");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();

       /* LocationServices.getFusedLocationProviderClient(getApplicationContext())
                .requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();*/
    }

    private void stopLocationUpdates() {
        tv_updates.setText("Location is NOT being tracked");
        tv_lat.setText("Location is NOT being tracked");
        tv_lon.setText("Location is NOT being tracked");
        tv_speed.setText("Location is NOT being tracked");
        tv_accuracy.setText("Location is NOT being tracked");
        tv_address.setText("Location is NOT being tracked");
        tv_sensor.setText("Location is NOT being tracked");
        tv_altitude.setText("Location is NOT being tracked");

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case PERMISSIONS_FINE_LOCATION:
                //If request was from the permission update the GPS
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                } else {
                    Toast.makeText(this, "This app needs permission to be granted", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void updateGPS() {
        //Get permissions from the user to track GPS
        //Get the current location from the fused client
        //update the UI - set all properties in their associated text view items.

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //user has permisson
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //we got permission.Put Values of location
                    updateUIValues(location);
                    currentLocation = location;

                }
            });
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_FINE_LOCATION);
            }
        }



    }

    //update text view values with new location
    //@SuppressLint("SetTextI18n")
    private void updateUIValues(Location location){
        tv_lat.setText(String.valueOf(location.getLatitude()));
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if(location.hasAltitude()){
            tv_altitude.setText(String.valueOf(location.getAltitude()));
        } else {
            tv_lon.setText("Not Available");
        }

        if(location.hasSpeed()){
            tv_speed.setText(String.valueOf(location.getSpeed()));
        } else {
            tv_speed.setText("Not Available");
        }

        Geocoder geocoder = new Geocoder((MainActivity.this));

        try {
            List <Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
        } catch (Exception e){
            tv_address.setText("Unable to get street address");
        }

        MyApplication myApplication = (MyApplication)getApplicationContext();
        savedLocations = myApplication.getMyLocations();

        //show Number of Defibs(Defibrillators) saved
        tv_noDefibs.setText(Integer.toString(savedLocations.size()));


    }
}