package com.gato.dvbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText etUsuario,etPassword;

    private Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsuario=(EditText)findViewById(R.id.etUsuario);
        etPassword=(EditText) findViewById(R.id.etContrasena);
        btnIniciar=(Button) findViewById(R.id.btnIniciarS);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
    }

    public void iniciar(){
        try {
            DVBUSQL admin = new DVBUSQL(this);
            SQLiteDatabase bd = admin.getReadableDatabase();

            String usuario = etUsuario.getText().toString();
            String password = etPassword.getText().toString();

            Cursor cursor = bd.rawQuery("SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?", new String[]{usuario, password});
            if (cursor.getCount() > 0) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                IniciarS();
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error en la base de datos: " + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void Registrar(View v)
    {
        Intent r = new Intent(MainActivity.this, Registro.class);
        startActivity(r);
    }
    public void IniciarS() {
        Intent intent = new Intent(MainActivity.this, PCarga.class);
        startActivity(intent);
        finish();
    }
}