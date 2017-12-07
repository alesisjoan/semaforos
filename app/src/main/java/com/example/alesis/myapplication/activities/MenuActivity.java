package com.example.alesis.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.alesis.myapplication.R;
import com.example.alesis.myapplication.services.SemaforoService;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void topOnClick(View view){
        Intent in = new Intent(MenuActivity.this, CurrentSemaforo.class);
        startActivity(in);
    }

    public void bottomOnClick(View view){
        Intent in = new Intent(MenuActivity.this, MapsActivity.class);
        startActivity(in);
    }

    public void etlOnClick(View view){
        SemaforoService.getInstance().etl(this.getApplicationContext());
    }

}
