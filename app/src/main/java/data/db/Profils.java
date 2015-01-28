package data.db;

/**
 * Created by Alexis on 18/01/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Profils extends SQLiteOpenHelper {


    // ---------- BASE DE DONNES

    // ---------- TABLE
    // ----- PROFIL

    public static final String PROFIL = "profil";
    public static final String PROFIL_ID = "id";
    public static final String PROFIL_NAME = "name";
    public static final String PROFIL_POINTING = "pointing";
    public static final String PROFIL_LINE_SPEED = "linespeed";
    public static final String PROFIL_LINE_SIZE = "linesize";
    public static final String PROFIL_LINE_COLORH = "linecolorh";
    public static final String PROFIL_LINE_COLORV = "linecolorv";
    public static final String PROFIL_SQUARE_WIDTH = "squarewidth";
    public static final String PROFIL_SQUARE_HEIGHT = "squareheight";
    public static final String PROFIL_SQUARE_COLOR = "squarecolor";





    // ---------- TABLE CREATION
    private static final String PROFIL_CREATE = "CREATE TABLE " + PROFIL + "("
            + PROFIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PROFIL_NAME + " TEXT DEFAULT 'unknown',"
            + PROFIL_POINTING + " TEXT DEFAULT 'Line Pointing',"
            + PROFIL_LINE_SPEED + " INTEGER DEFAULT 50,"
            + PROFIL_LINE_SIZE + " INTEGER DEFAULT 10,"
            + PROFIL_LINE_COLORH + " TEXT DEFAULT '#f44336',"
            + PROFIL_LINE_COLORV + " TEXT DEFAULT ' #f44336',"
            + PROFIL_SQUARE_WIDTH + " INTEGER DEFAULT 50,"
            + PROFIL_SQUARE_HEIGHT + " INTEGER DEFAULT 50,"
            + PROFIL_SQUARE_COLOR + " TEXT DEFAULT ' #f44336'"
            + ");";


    // ---------- CONSTRUCTOR

    public Profils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // ---------- METHODS

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PROFIL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Profils.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + PROFIL);
        onCreate(db);
    }

}