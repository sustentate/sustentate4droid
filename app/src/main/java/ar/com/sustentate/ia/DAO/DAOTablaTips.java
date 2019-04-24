package ar.com.sustentate.ia.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ar.com.sustentate.ia.models.Tip;

/**
 * Created by emzas on 8/4/2018.
 */

public class DAOTablaTips extends DatabaseHelper{


    public DAOTablaTips(Context context) {
        super(context);
    }

    public static final String NOMBRE_DE_LA_TABLA = "TIPS";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String URL_TIP= "url";
    public static final String DATE = "date";



    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void insertarDatosEnTabla(Tip tip) {
        if(tip == null){

        }else{
            SQLiteDatabase baseDeDatos = getWritableDatabase();
            ContentValues row = new ContentValues();

            row.put(ID, tip.getId());
            row.put(TITLE, tip.getTitle());
            row.put(TEXT, tip.getText());
            row.put(URL_TIP, tip.getImageUrl());
            row.put(DATE, tip.getDate());
            baseDeDatos.insert(NOMBRE_DE_LA_TABLA, null, row);
            baseDeDatos.close();
        }
    }

    public void insertarLosTips(List<Tip> tips) {
        for (Tip tip : tips) {
            try {
                if (tip.equals(null) || tipsUni(tip)) {

                } else {
                    insertarDatosEnTabla(tip);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Tip> consultaDeTips(){
        List<Tip> tips= new ArrayList<>();
        String id;
        String title;
        String text;
        String urlTip;
        String date;
        SQLiteDatabase baseDeDatos = getReadableDatabase();
        String sentenciaConsulta="SELECT * FROM "+NOMBRE_DE_LA_TABLA ;
        Cursor cursor = baseDeDatos.rawQuery(sentenciaConsulta,null);
        while(cursor.moveToNext()){
            Tip tip=new Tip();

            id= cursor.getString(cursor.getColumnIndex(ID));
            date=cursor.getString(cursor.getColumnIndex(DATE));
            title=cursor.getString(cursor.getColumnIndex(TITLE));
            text=cursor.getString(cursor.getColumnIndex(TEXT));
            urlTip=cursor.getString(cursor.getColumnIndex(URL_TIP));


            tip.setId( Long.parseLong(id));
            tip.setTitle(title);
            tip.setImageUrl(urlTip);
            tip.setText(text);
            tip.setDate(Long.parseLong(date));
            tips.add(tip);
        }
        cursor.close();
        baseDeDatos.close();
        return tips;
    }
    public boolean tipsUni(Tip tip) throws ParseException {
        boolean some = false;

        List<Tip> eventos = consultaDeTips();

        for (Tip tip1: eventos) {
            if (tip.getId() == tip1.getId()){
                some = true;
            }
        }

        return some;
    }




    }
