package com.gato.dvbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class PCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcarga);

        TimerTask carga = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(PCarga.this, Menu.class);
                startActivity(intent);
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(carga, 5000);
    }
}