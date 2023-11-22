package com.gato.dvbu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class DVBUSQL extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "usuarios_db";
    private static final int DATABASE_VERSION = 1;

    public DVBUSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUsersTable = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "correo TEXT UNIQUE," +
                "contraseña TEXT)";
        db.execSQL(createUsersTable);


        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", "Mery");
        contentValues.put("correo", "mery12@gmail.com");
        contentValues.put("contraseña", "patito123");
        db.insert("usuarios", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
