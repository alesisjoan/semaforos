package com.example.alesis.myapplication.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.alesis.myapplication.R;
import com.example.alesis.myapplication.model.Semaforo;
import com.example.alesis.myapplication.services.SemaforoService;

import java.util.Date;

public class CurrentSemaforo extends AppCompatActivity implements LocationListener {

    SemaforoService semaforoService = SemaforoService.getInstance();

    public TextView timer;
    public TextView nextGreen;
    public TextClock textClock;

    public TextView info;
    public CountDownTimer countDownTimer;

//    private Handler handler = new Handler();



    private Semaforo current;
    private static final String INICIANDO = "Iniciando...";
    private static final String PROXIMO_SEMAFORO = "Proximo semaforo: ";

    private static final float MIN_SPEED = 4f;

    private static Context mContext;
    public View myView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_semaforo);
//        myView = inflater.inflate(R.layout.activity_current_semaforo, container, false);
        this.timer = (TextView) findViewById(R.id.Timer);
        this.info = (TextView) findViewById(R.id.Info);
        this.nextGreen = (TextView) findViewById(R.id.nextGreen);
        this.textClock = (TextClock) findViewById(R.id.textClock);
        this.textClock.setFormat24Hour("HH:mm:ss");
        text(INICIANDO);
        mContext = this.getApplicationContext();
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, (LocationListener) this);
        semaforoService.getAllSemaforosProcessed();

    }

    private void text(String t){
        info.setText(t);
    }

    private void timer(int t){
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer =  new CountDownTimer(t*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            public void onFinish() {
                timer.setText("0");
            }
        }.start();
    }


    @Override
    public void onLocationChanged(Location location) {
        if(location.getSpeed() > MIN_SPEED || true){
            Semaforo nearSemaforo = semaforoService.getNearSemaforo(location, mContext);
            if(null!=nearSemaforo && current!=nearSemaforo) {

                text("Procesando...");
                current = nearSemaforo;
                int timeToGreen = current.getTimeToGreen();
                this.nextGreen.setText(new Date(current.nextGreen()).toString());
                Long now = System.currentTimeMillis();
                text("Ahora: "+new Date(now).toString()+"\n Proximo: "+new Date(now+(timeToGreen*1000)).toString());
                timer(timeToGreen);
            }
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
}
