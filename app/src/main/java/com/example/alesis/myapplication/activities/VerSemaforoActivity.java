/*
package com.example.alesis.myapplication.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import com.example.alesis.myapplication.R;
import com.example.alesis.myapplication.model.Semaforo;

public class VerSemaforoActivity extends Activity implements LocationListener {
    static TextView textView;
    private final String isGettingCloser = "ACERCANDOSE";
    private final String isGettingAway = "ALEJANDOSE";
    float lastDistance = 0l;

    float speed;
    Semaforo nextSemaforo;

    int cx = 0;

    private final double lamadridLat = -32.898081d;
    private final double lamadridLong = -68.859561d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        VerSemaforoActivity locationListener = new VerSemaforoActivity();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) locationListener);
    }

    private static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.speed = location.getSpeed();





        String log = "";

        float distLamadridActualpoint1 = distFrom(lamadridLat, lamadridLong,
                location.getLatitude(), location.getLongitude());

        cx++;

        log += cx + " "+String.valueOf(distLamadridActualpoint1);

        if (lastDistance < distLamadridActualpoint1) {
            log += "\n" + isGettingAway;
        } else {
            log += "\n" + isGettingCloser;
        }

        log += "\n";

        lastDistance = distLamadridActualpoint1;

        if (textView == null){
            textView = new TextView(this);
            setContentView(textView);
        }
        textView.setText(log);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}*/
