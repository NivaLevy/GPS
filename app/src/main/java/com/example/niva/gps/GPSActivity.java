package com.example.niva.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends AppCompatActivity {

    private final int MIN_DISTANCE_UPDATE = 1;
    private final int MIN_TIME_UPDATE = 1000;
    private TextView lblDistance;
    private Location myLocatoion;
    private float distance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        lblDistance = (TextView) findViewById(R.id.lblDistance);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String displayText = "My current location is: " +
                        "\nLongitude: " + longitude + ",\tLatitude: " + latitude;
                Toast.makeText(getApplicationContext(), displayText, Toast.LENGTH_SHORT).show();

                if (myLocatoion != null) {
                    float currentDistance = location.distanceTo(myLocatoion);
                    distance += currentDistance;
                    lblDistance.setText("" + distance);
                    if(distance % 10 == 0)
                        Toast.makeText(getApplicationContext(),distance+"", Toast.LENGTH_LONG).show();
                }
                myLocatoion = new Location(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_SHORT).show();

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, locationListener);
    }

}
