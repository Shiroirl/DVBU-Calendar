package com.gato.dvbu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.VolumeShaper;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Map;

public class Maps extends AppCompatActivity {

    MapController mapController;
    EditText etLat, etLong;
    MapView mapApp;
    Button btnB;
    Double latCurrentPosition=0.0, longCurrentPosition=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        AccessPermission();

        etLat = (EditText) findViewById(R.id.editTextLatitud);
        etLong = (EditText) findViewById(R.id.editTextLongitud);
        btnB = (Button) findViewById(R.id.btnBuscar);
        mapApp = (MapView) findViewById(R.id.mapViewOEV);

        mapApp.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        mapApp.setBuiltInZoomControls(true);
        mapApp.setMultiTouchControls(true);
        mapController=(MapController) mapApp.getController();
        mapController.setZoom(15);
        GeoPoint pointcenter = new GeoPoint(-33.44912271264629, -70.66241330004699);

        mapController.setCenter(pointcenter);

        final MyLocationNewOverlay myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapApp);
        myLocationNewOverlay.enableMyLocation();
        mapApp.getOverlays().add(myLocationNewOverlay);
        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapApp.getController().animateTo(myLocationNewOverlay.getMyLocation());
                latCurrentPosition = myLocationNewOverlay.getMyLocation().getLatitude();
                longCurrentPosition = myLocationNewOverlay.getMyLocation().getLongitude();
            }
        });

        GeoPoint mPoint = new GeoPoint(-33.44912271264629, -70.66241330004699);
        Marker myMarkerPoint = new Marker(mapApp);
        myMarkerPoint.setPosition(mPoint);
        myMarkerPoint.setTitle("Instituto Santo Tomas Santiago Centro");
        myMarkerPoint.setIcon(this.getResources().getDrawable(R.mipmap.ic_launcher_round));
        myMarkerPoint.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapApp.getOverlays().add(myMarkerPoint);

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Toast.makeText(getApplicationContext(), p.getLatitude()+"-"+p.getLongitude(), Toast.LENGTH_SHORT).show();
                etLat.setText(String.valueOf(p.getLatitude()));
                etLong.setText(String.valueOf(p.getLongitude()));
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay eventsOverlay = new MapEventsOverlay(getApplicationContext(), mapEventsReceiver);
        mapApp.getOverlays().add(eventsOverlay);

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Maps.this,Maps2.class);
                intent.putExtra("Latitud: ", Double.parseDouble(etLat.getText().toString()));
                intent.putExtra("Longitud: ", Double.parseDouble(etLong.getText().toString()));
                intent.putExtra("Latitud Act: ", latCurrentPosition);
                intent.putExtra("Longitud Act: ", longCurrentPosition);
                startActivity(intent);
            }
        });

    }

    void AccessPermission()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 1002);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1003);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1004);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1005);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permiso: FINE LOCATION concedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permiso: FINE LOCATION no concedido.", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1002:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permiso: ACCESS INTERNET concedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permiso: ACCESS INTERNET no concedido.", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1003:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permiso: NETWORK STATE concedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permiso: NETWORK STATE no concedido.", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1004:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permiso: READ EXTERNAL STORAGE concedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permiso: READ EXTERNAL STORAGE no concedido.", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1005:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permiso: WRITE EXTERNAL STORAGE concedido.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permiso: WRITE EXTERNAL STORAGE no concedido.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}