package com.example.davinnovation.gpstest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    boolean isGPSEnabled,isNetworkEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button
        findViewById(R.id.GetGPS_button).setOnClickListener(gpsButtonclick);

        //GPS
        LocationManager locationmanager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER); //GPS provider check
        isNetworkEnabled = locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // Network provider check

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                Log.d("GPS","latitude: " + lat + ", longitude: " + lng);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("GPS","onstatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("GPS","onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("GPS","onProviderDisabled");
            }
        };

        locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        String locationProvider = locationmanager.GPS_PROVIDER;
        Location lastKnownLocation = locationmanager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null)
        {
            double lng = lastKnownLocation.getLongitude();
            double lat = lastKnownLocation.getLatitude();

            Log.d("GPS","latitude: " + lat + ", longitude: " + lng);
        }
    }

    Button.OnClickListener gpsButtonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
