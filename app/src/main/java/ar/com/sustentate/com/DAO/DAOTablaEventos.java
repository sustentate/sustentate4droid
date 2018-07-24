package ar.com.sustentate.com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.com.models.Evento;

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
            row.put(URL_EVENTO, evento.getImageUrl());
            row.put(DATE, String.valueOf(evento.getDate()));
            row.put(DATE_END, String.valueOf(evento.getDate()));
            row.put(PLACE, String.valueOf(evento.getDateEnd()));
            database.insert(NOMBRE_DE_LA_TABLA, null, row);
            database.close();

        }
    }

    public void insertarLosEventos(List<Evento> eventos){
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
        String request = "SELECT * FROM " + NOMBRE_DE_LA_TABLA;
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


            evento.setDate(Long.parseLong(date));
            evento.setDateEnd(Long.parseLong(endDate));
            evento.setId(Long.parseLong(id));
            evento.setPlace(place);
            evento.setText(text);
            evento.setTitle(title);
            evento.setImageUrl(urlEvento);
            eventos.add(evento);
        }

        cursor.close();
        database.close();
        return eventos;
    }



}
