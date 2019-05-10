package com.example.hikerswatch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.PackageInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
LocationManager locationManager;
LocationListener locationListener;
Geocoder geocoder;
TextView alt;
TextView lat;
TextView lon;
TextView acc;
TextView add;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1000,locationListener);
            Location last=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(last==null)
            {
                add.setText("Address: \n" + "Not Found");
            }
            else
            {
                geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list=geocoder.getFromLocation(last.getLatitude(),last.getLongitude(),1);
                    if(list!=null&&list.size()>0)
                    {
                        Log.i("add",list.get(0).toString());
                        Address ad=list.get(0);

                        add.setText("Address: \n" + ad.getAddressLine(0));


                        Log.i("ll",last.toString());
                        lat.setText("Latitude: " + last.getLatitude());
                        lon.setText("Longitude: " + last.getLongitude());
                        alt.setText("Altitude: " + last.getAltitude());
                        acc.setText("Accuracy: " + last.getAccuracy());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alt=findViewById(R.id.Alt);
        lat=findViewById(R.id.Lat);
        lon=findViewById(R.id.Lon);
        add=findViewById(R.id.Add);
        acc=findViewById(R.id.Acc);
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(list!=null&&list.size()>0)
                    {
                        Log.i("add",list.get(0).toString());
                        Log.i("loc",location.toString());
                        Address ad=list.get(0);

                        add.setText("Address: \n" + ad.getAddressLine(0));
                        lat.setText("Latitude: " + ad.getLatitude());
                        lon.setText("Longitude: " + ad.getLongitude());
                        alt.setText("Altitude: " + location.getAltitude());
                        acc.setText("Accuracy: " + location.getAccuracy());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(Build.VERSION.SDK_INT<23)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1000,locationListener);

        }
        else
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1000,locationListener);
               Location last=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(last==null)
                {
                    add.setText("Address: \n" + "Not Found");
                }
                else
                {
                    geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> list=geocoder.getFromLocation(last.getLatitude(),last.getLongitude(),1);
                        if(list!=null&&list.size()>0)
                        {
                            Log.i("add",list.get(0).toString());
                            Address ad=list.get(0);
                            add.setText("Address: \n" + ad.getAddressLine(0));
                            Log.i("ll",last.toString());
                            lat.setText("Latitude: " + last.getLatitude());
                            lon.setText("Longitude: " + last.getLongitude());
                            alt.setText("Altitude: " + last.getAltitude());
                            acc.setText("Accuracy: " + last.getAccuracy());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
