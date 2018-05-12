package com.sustentate.app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sustentate.app.models.Evento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by emzas on 12/5/2018.
 */

public class DAOTablaEventos extends DatabaseHelper {
    public DAOTablaEventos(Context context) {super(context);}

    public static final String NOMBRE_DE_LA_TABLA = "EVENTOS";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String URL_EVENTO= "url";
    public static final String DATE = "date";
    public static final String DATE_END = "date_end";
    public static final String PLACE = "place";

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void insertarDatosEnTabla(Evento evento){
        if(evento == null){

        }else{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues row = new ContentValues();

            row.put(ID , evento.getId());
            row.put(TITLE, evento.getTitle());
            row.put(TEXT, evento.getText());
            row.put(URL_EVENTO, evento.getUrlImagen());
            row.put(DATE, String.valueOf(evento.getDate()));
            row.put(DATE_END, String.valueOf(evento.getDate()));
            row.put(PLACE, String.valueOf(evento.getDateEnd()));
            database.insert(NOMBRE_DE_LA_TABLA, null, row);
            database.close();

        }
    }

    public void insertarLosTips(List<Evento> eventos){
        for (Evento evento : eventos){
            if(evento.equals(null)){

            }else {
                insertarDatosEnTabla(evento);
            }
        }
    }

    public List<Evento> consultaEventos(){
        List<Evento> eventos = new ArrayList<>();
        String id;
        String title;
        String text;
        String urlEvento;
        String date;
        String endDate;
        String place;

        SQLiteDatabase database = getReadableDatabase();
        String request = "SELECT * FROM" + NOMBRE_DE_LA_TABLA;
        Cursor cursor = database.rawQuery(request,null);

        while(cursor.moveToNext()){
            Evento evento = new Evento();

            id = cursor.getString(cursor.getColumnIndex(ID));
            title = cursor.getString(cursor.getColumnIndex(TITLE));
            text = cursor.getString(cursor.getColumnIndex(TEXT));
            urlEvento = cursor.getString(cursor.getColumnIndex(URL_EVENTO));
            date = cursor.getString(cursor.getColumnIndex(DATE));
            endDate = cursor.getString(cursor.getColumnIndex(DATE_END));
            place = cursor.getString(cursor.getColumnIndex(PLACE));

            SimpleDateFormat simple1 = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat simple2 = new SimpleDateFormat("dd-MMM-yyyy");

            try {
                Date date1 = simple1.parse(date);
                Date date2 = simple2.parse(endDate);
                evento.setDate(date1);
                evento.setDateEnd(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            evento.setId(Long.parseLong(id));
            evento.setPlace(place);
            evento.setText(text);
            evento.setTitle(title);
            evento.setUrlImagen(urlEvento);
            eventos.add(evento);
        }

        cursor.close();
        database.close();
        return eventos;
    }



}
