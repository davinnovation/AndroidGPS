package com.example.davinnovation.gpstest;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    TextView mTextNow;
    TextView mTextStats;
    LocationManager mLocMgr;
    Button mbtnNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextNow = (TextView)findViewById(R.id.textNow);
        mTextStats = (TextView)findViewById(R.id.textState);
        mbtnNow = (Button)findViewById(R.id.btnNow);

        mLocMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        mbtnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String locProv = mLocMgr.getBestProvider(getCriteria(),true);
                //mLocMgr.requestLocationUpdates(locProv, 3000, 3, mLocListener);
                mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, mLocListener);
                mLocMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 3, mLocListener);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    LocationListener mLocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mTextNow.setText("Lat: " + location.getLatitude() + "\nLng: " +  location.getLongitude() + "\nAlt: " +  location.getAltitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                    mTextStats.setText("Provider out of service");break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    mTextStats.setText("Provider Temporarily Unavailabe");break;
                case LocationProvider.AVAILABLE:
                    mTextStats.setText("Provider Available");break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            mTextStats.setText("Provider Enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            mTextStats.setText("Provider Disabled");
        }
    };

    public static  Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }

    public void onResume(){
        super.onResume();
        // String locProv = mLocMgr.getBestProvider(getCriteria(),true);
        //mLocMgr.requestLocationUpdates(locProv, 3000, 3, mLocListener);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, mLocListener);
        mLocMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 3, mLocListener);
    }

    public void onPause() {
        super.onPause();
        mLocMgr.removeUpdates(mLocListener);
        mTextStats.setText("Location Service Stop");
    }
}
