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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alesis.myapplication.R;
import com.example.alesis.myapplication.services.SemaforoService;

public class SaveSemaforoActivity extends Activity implements View.OnClickListener, LocationListener {

    private Button save_button;
    TextView info;
    private Location currentLocation;

    SemaforoService semaforoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_semaforo);


        semaforoService = new SemaforoService();

        save_button = (Button) findViewById(R.id.save_button);
        info = (TextView) findViewById(R.id.info);

        save_button.setOnClickListener(SaveSemaforoActivity.this);
        //Here VerSemaforoActivity.this is a Current Class Reference (context)

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SaveSemaforoActivity locationListener = new SaveSemaforoActivity();
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


    @Override
    public void onClick(View v) {

        try {
            String infoTxt;
            if(currentLocation != null){
                semaforoService.saveSemaforo(semaforoService.getCurrent(currentLocation, this.getApplicationContext()));
                 infoTxt = "Guardado";
            }else{
                infoTxt = "Current Location is Null";
            }

            updateInfo(infoTxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateInfo(String infoText){
        this.info.setText(infoText);
    }

    @Override
    public void onLocationChanged(Location location) {

        this.currentLocation = location;

        String locInfo = "error";
        if(location != null){
            locInfo = "Latitude: " + location.getLatitude() + " | Longitude: " + location.getLongitude();
        }

        updateInfo(locInfo);
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
}
*/
