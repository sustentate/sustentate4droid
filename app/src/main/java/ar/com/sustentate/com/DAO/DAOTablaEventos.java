package ar.com.sustentate.com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static final String DESCRIPTION = "description";
    public static final String LINK= "url";
    public static final String PUBLISHED= "published";
    public static final String PROMOTED= "promoted";
    public static final String DATE = "date";
    public static final String ADDRESS = "place";
    public static final String PRICE= "price";
    public static final String TYPE= "type";



    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void insertarDatosEnTabla(Evento evento) throws ParseException {
        if(evento == null ){

        }else{
            SQLiteDatabase database = getWritableDatabase();
            ContentValues row = new ContentValues();

            row.put(ID , evento.getId());
            row.put(TITLE, evento.getTitle());
            row.put(DESCRIPTION, evento.getDescription());
            row.put(DATE, String.valueOf(evento.getStartDateTime()));
            row.put(ADDRESS, String.valueOf(evento.getAddress()));
            row.put(LINK, String.valueOf(evento.getLink()));
            row.put(PUBLISHED, String.valueOf(evento.isPublished()));
            row.put(PROMOTED, String.valueOf(evento.getPromoted()));
            row.put(PRICE, String.valueOf(evento.getPrice()));
            row.put(ADDRESS, String.valueOf(evento.getAddress()));
            //TODO: Mirar esto
            //row.put(TYPE, String.valueOf(evento.getType().toString()));
            database.insert(NOMBRE_DE_LA_TABLA, null, row);
            database.close();

        }
    }

    public void insertarLosEventos(List<Evento> eventos) throws ParseException {
        for (Evento evento : eventos){
            if(evento.equals(null) || eventosUni(evento)){

            }else {
                insertarDatosEnTabla(evento);
            }
        }
    }

    public List<Evento> consultaEventos() throws ParseException {
        List<Evento> eventos = new ArrayList<>();
        String id;
        String title;
        String description;
        String link;
        String date;
        String address;
        String published;
        String price;
        String type;
        String promoted;

        SQLiteDatabase database = getReadableDatabase();
        String request = "SELECT * FROM " + NOMBRE_DE_LA_TABLA;
        Cursor cursor = database.rawQuery(request,null);

        while(cursor.moveToNext()){
            Evento evento = new Evento();

            id = cursor.getString(cursor.getColumnIndex(ID));
            title = cursor.getString(cursor.getColumnIndex(TITLE));
            description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            link = cursor.getString(cursor.getColumnIndex(LINK));
            date = cursor.getString(cursor.getColumnIndex(DATE));
            address = cursor.getString(cursor.getColumnIndex(ADDRESS));
            price = cursor.getString(cursor.getColumnIndex(PRICE));
            promoted = cursor.getString(cursor.getColumnIndex(PROMOTED));
            type = cursor.getString(cursor.getColumnIndex(TYPE));
            published = cursor.getString(cursor.getColumnIndex(PUBLISHED));

            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
            Date dater = (Date) df.parse(date);

            evento.set_id(id);
            evento.setAddress(address);
            evento.setDescription(description);
            evento.setTitle(title);
            evento.setPrice(Long.parseLong(price));
            evento.setPromoted(Boolean.parseBoolean(promoted));
            evento.setLink(link);
            evento.setStartDateTime(dater);
            eventos.add(evento);
        }

        cursor.close();
        database.close();
        return eventos;
    }

    public boolean eventosUni(Evento evento) throws ParseException {
        boolean some = false;

        List<Evento> eventos = consultaEventos();

        for (Evento evento1 : eventos) {
            if (evento.getId() == evento1.getId()){
                some = true;
            }
        }

        return some;
    }



}
