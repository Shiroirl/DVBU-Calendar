package com.gato.dvbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class About extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor sensorGiroscopio;
    private OrientationEventListener orientationEventListener;
    private WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        if (sensorManager != null) {
            sensorGiroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if (sensorGiroscopio == null) {

                Toast.makeText(this, "Este dispositivo no cuenta con esta funcion", Toast.LENGTH_SHORT).show();
            } else {

                sensorManager.registerListener(this, sensorGiroscopio, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        orientationEventListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
            }
        };
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float z = event.values[2]; // Velocidad angular en el eje Z

            // Cambiar la orientación de la pantalla automáticamente
            if (z > 2.0f) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (z < -2.0f) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        orientationEventListener.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorGiroscopio != null) {
            sensorManager.registerListener(this, sensorGiroscopio, SensorManager.SENSOR_DELAY_NORMAL);
            orientationEventListener.enable();
        }
    }
    public void Volver(View v)
    {
        Intent m = new Intent(About.this, Menu.class);
        startActivity(m);
        finish();
    }
}