package com.sustentate.app.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gfbla on 22/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private Context context;

    public static final String NOMBRE_BASE_DE_DATOS = "sustentatedb";

    //si valor cambiado, ejecuta el codigo en onUpgrade
    public static final Integer VERSION_BASE_DE_DATOS = 1;

    public DatabaseHelper(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
        this.context = context;

    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        //String creaTablaFuentes ="CREATE TABLE FUENTES (id varchar(20) PRIMARY KEY NOT NULL default '',name varchar(20) NOT NULL default '',description text NOT NULL default '',category varchar(20) NOT NULL default '');";
        //String creaTablaArticulos="CREATE TABLE ARTICULOS (author varchar(20) NOT NULL default '',title varchar(20) PRIMARY KEY NOT NULL default '',description text NOT NULL default '',url text NOT NULL default '',urlToImage text NOT NULL default '',publishedAt varchar(20) NOT NULL default '',fuente text NOT NULL default '');";
        //String creaTablaFuentes ="CREATE TABLE FUENTES (id TEXT PRIMARY KEY NOT NULL, name TEXT NOT NULL, description text NOT NULL, category text NOT NULL);";
        //String creaTablaArticulos="CREATE TABLE ARTICULOS (author text NOT NULL, title text PRIMARY KEY NOT NULL, description text NOT NULL, url text NOT NULL, urlToImage text NOT NULL, publishedAt text NOT NULL, " + DAOTablaArticulos.ID_FUENTE + " text NOT NULL);";

        //String creaTablaFuentes ="CREATE TABLE FUENTES ("+DAOTablaFuente.ID+" text PRIMARY KEY NOT NULL,"+DAOTablaFuente.NOMBRE+" text NOT NULL,"+DAOTablaFuente.DESCRIPCION+" text NOT NULL,"+DAOTablaFuente.CATEGORIA+" text NOT NULL);";
        String creaTablaTips="CREATE TABLE TIPS ("+ DAOTablaTips.ID +" PRIMARY KEY NOT NULL,"+DAOTablaTips.TITLE+" text NOT NULL,"+DAOTablaTips.DATE+" text NOT NULL,"+ DAOTablaTips.URL_TIP+" text NOT NULL,"+DAOTablaTips.TEXT+" text NOT NULL);";

       // db.execSQL(creaTablaFuentes);
        db.execSQL(creaTablaTips);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
