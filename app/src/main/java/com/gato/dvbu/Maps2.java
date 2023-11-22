package com.gato.dvbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Maps2 extends AppCompatActivity {

    WebView wb;
    Double Latitud=0.0, Longitud=0.0, LatitudActual=0.0, LongitudActual=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        wb = (WebView) findViewById(R.id.wbv);
        wb.getSettings().setJavaScriptEnabled(true);

        WebSettings settings = wb.getSettings();
        settings.setDomStorageEnabled(true);

        Intent intent = getIntent();
        Latitud=intent.getDoubleExtra("Latitud", 0);
        Longitud=intent.getDoubleExtra("Longitud", 0);
        LatitudActual=intent.getDoubleExtra("Latitud Actual", 0);
        LongitudActual=intent.getDoubleExtra("Longitud Actual", 0);

        wb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if (URLUtil.isNetworkUrl(url))
                {
                    return false;
                }
                if(appInstalledOrNot(url))
                {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

                return true;
            }
        });
        wb.loadUrl("https://www.google.com/maps?" + "saddr="+LatitudActual+","+LongitudActual+"&daddr="+Latitud+","+Longitud);
        Toast.makeText(this,"Cargando Indicaciones, Porfavor espera...", Toast.LENGTH_LONG).show();
    }
    boolean appInstalledOrNot(String url)
    {
        PackageManager pm=getPackageManager();
        try {
            pm.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
        }
        catch(PackageManager.NameNotFoundException e)
        {

        }
        return false;
    }
}