package ar.com.sustentate.com.DAO;

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
    public static final int VERSION_BASE_DE_DATOS = 2;

    public DatabaseHelper(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
        this.context = context;

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        String creaTablaTips="CREATE TABLE TIPS ("+ DAOTablaTips.ID +" PRIMARY KEY NOT NULL,"+DAOTablaTips.TITLE+" text NOT NULL,"+DAOTablaTips.DATE+" text NOT NULL,"+ DAOTablaTips.URL_TIP+" text NOT NULL,"+DAOTablaTips.TEXT+" text NOT NULL);";
        String creaTablaEventos="CREATE TABLE EVENTOS ("+ DAOTablaEventos.ID+" text NOT NULL,"+DAOTablaEventos.TITLE+" text NOT NULL,"+DAOTablaEventos.DATE+" text NOT NULL,"+DAOTablaEventos.DATE_END+" text NOT NULL,"+ DAOTablaEventos.URL_EVENTO+" TEXT,"+DAOTablaEventos.TEXT+" text NOT NULL,"+DAOTablaEventos.PLACE+
         "  text NOT NULL);";

        db.execSQL(creaTablaTips);
        db.execSQL(creaTablaEventos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
