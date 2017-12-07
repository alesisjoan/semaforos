package com.example.alesis.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alesis.myapplication.R;
import com.example.alesis.myapplication.model.Semaforo;
import com.example.alesis.myapplication.services.SemaforoService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        SensorEventListener,
        View.OnClickListener, LocationListener {

    //Our Map
    private GoogleMap mMap;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    private Location mlocation;
    private LatLng latLngToSave;

    //Buttons
    private Button buttonSave;
    private TextView textViewConsole;
    private Switch aSwitch;
    private Semaforo sToSave;


    //Google ApiClient
    private GoogleApiClient googleApiClient;

    SemaforoService semaforoService = SemaforoService.getInstance();

//    private Marker currentMarker;

    private static final String TEXT_UPDATING_LOCATION = "Actualizando location...";
    private static final String TEXT_SEMAFORO_SAVE = "Semaforo guardado!";
    private static final String TEXT_WAITING_ACTION = "Esperando nueva instruccion...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners
        buttonSave = (Button) findViewById(R.id.save);
        aSwitch = (Switch) findViewById(R.id.switch1);
        textViewConsole = (TextView) findViewById(R.id.console);
        buttonSave.setOnClickListener(this);
        aSwitch.setOnClickListener(this);
        textViewConsole.setOnClickListener(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
 /*   private void getCurrentLocation() {
//        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (mlocation != null) {
            //Getting longitude and latitude
            longitude = mlocation.getLongitude();
            latitude = mlocation.getLatitude();

            //moving the map to location
            moveMap();
            this.setTextViewConsole("Long: " + longitude + ". Lat: " + longitude + ". Speed: " + mlocation.getSpeed());
        }

    }*/

    private void setTextViewConsole(String msg) {
        this.textViewConsole.setText(msg);
    }

    //Function to move the map
    /*
    private void moveMap() {

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        showAllSemaforos();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
//        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onClick(View v) {
        if(v == buttonSave){
            this.saveSemaforo();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        mover current marker

//        getCurrentLocation();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

/*    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {
        this.mlocation = location;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        /*if(this.mMap!=null) {
            if (this.currentMarker != null) {
                this.currentMarker.remove();
            }

            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            this.currentMarker = this.mMap.addMarker(markerOptions);

            //move map camera
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }*/
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

    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
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

    private void saveSemaforo() {
        try {
            this.setTextViewConsole(TEXT_WAITING_ACTION);
            if(!aSwitch.isChecked()){
                sToSave = new Semaforo();
                sToSave.setLocation(mlocation);
                sToSave.setGreenStart(new Date().getTime());
                sToSave.setId(new Date().getTime());
            }else{
                sToSave.setGreenStartAgain(new Date().getTime());
                semaforoService.saveSemaforo(sToSave);
                aSwitch.setChecked(false);
                this.setTextViewConsole(TEXT_SEMAFORO_SAVE);
                sToSave = null;
            }

        } catch (Exception e) {
            this.setTextViewConsole("ERROR: " + e.getCause().getMessage());
        }
    }

    private void showAllSemaforos() {
        List<Semaforo> semaforos = this.semaforoService.getAllSemaforosProcessed();
        if(semaforos!=null) {
            for (Semaforo s : semaforos) {
                LatLng latLng = new LatLng(s.getLocation().getLatitude(), s.getLocation().getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true)
                        .alpha(0.7f)
                        .title(s.getId().toString())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                setTextViewConsole("Semaforo " + s.getStreetName());
            }
        }
    }

}