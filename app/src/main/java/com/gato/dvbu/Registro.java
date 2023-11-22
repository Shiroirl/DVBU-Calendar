package com.gato.dvbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private EditText etNombre,etApellidos,etCorreo,etContraseña;
    private RadioButton rbtnHombre,rbtnMujer, rbtnOtro;
    private Button btnRegistrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre=(EditText) findViewById(R.id.etNombre);
        etApellidos=(EditText) findViewById(R.id.etApellidos);
        etCorreo=(EditText)findViewById(R.id.etCorreo);
        etContraseña=(EditText) findViewById(R.id.etClave);
        rbtnHombre=(RadioButton)findViewById(R.id.rbMasculino);
        rbtnMujer=(RadioButton)findViewById(R.id.rbFemenino);
        rbtnOtro=(RadioButton)findViewById(R.id.rbOtro);
        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);

        Bundle bundle=getIntent().getExtras();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

    }

    public void guardar() {
        DVBUSQL admin = new DVBUSQL(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombre = etNombre.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String correo = etCorreo.getText().toString();
        String contraseña = etContraseña.getText().toString();
        String genero="null";
        if (rbtnHombre.isChecked()) {
            genero = "Hombre";
        } else if (rbtnMujer.isChecked()) {
            genero = "Mujer";
        } else if (rbtnOtro.isChecked()) {
        genero = "Usa tu imaginación";
        }

        if (nombre.equals("")) {
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_LONG).show();
        } else if (apellidos.equals("")) {
            Toast.makeText(this, "Debes ingresar tus apellidos", Toast.LENGTH_LONG).show();
        }else if(correo.equals("null")){
            Toast.makeText(this,"Debes seleccionar un correo",Toast.LENGTH_LONG).show();
        } else if (contraseña.equals("null")) {
            Toast.makeText(this, "Debes seleccionar una contraseña", Toast.LENGTH_LONG).show();
        } else if (genero.equals("")) {
            Toast.makeText(this, "Debes ingresar tu genero", Toast.LENGTH_LONG).show();
        } else {
            ContentValues datosUsuario = new ContentValues();
            datosUsuario.put("nombre", nombre);
            datosUsuario.put("apellidos", apellidos);
            datosUsuario.put("correo", correo);
            datosUsuario.put("contraseña", contraseña);
            datosUsuario.put("genero", genero);

            long resultado = bd.insert("usuarios", null, datosUsuario);

            if (resultado != -1) {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                Reedireccion();
            } else {
                Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
            }

        bd.close();
        }
    }
    public void Volver(View v)
    {
        Intent m = new Intent(Registro.this, MainActivity.class);
        startActivity(m);
        finish();
    }
    public void Reedireccion() {
        Intent intent = new Intent(Registro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}