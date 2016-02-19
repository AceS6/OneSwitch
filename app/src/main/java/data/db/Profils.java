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
    public static final String PROFIL_SQUARE_COLOR = "squarecolor";
    public static final String PROFIL_SCROLL_SPEED = "scrollspeed";
    public static final String PROFIL_SERVICE_COLOR = "servicecolor";
    public static final String PROFIL_SERVICE_OPACITY = "servicespeed";



    // ----- CONTACT

    public static final String CONTACT = "contact";
    public static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_NUMBER = "number";
    public static final String CONTACT_KEY = "key";
    public static final String CONTACT_PROFIL = "profil";



    // ---------- TABLE CREATION
    private static final String PROFIL_CREATE = "CREATE TABLE " + PROFIL + "("
            + PROFIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PROFIL_NAME + " TEXT DEFAULT 'unknown',"
            + PROFIL_POINTING + " TEXT DEFAULT 'Line Pointing',"
            + PROFIL_LINE_SPEED + " INTEGER DEFAULT 5,"
            + PROFIL_LINE_SIZE + " INTEGER DEFAULT 5,"
            + PROFIL_LINE_COLORH + " TEXT DEFAULT '#f44336',"
            + PROFIL_LINE_COLORV + " TEXT DEFAULT ' #f44336',"
            + PROFIL_SQUARE_COLOR + " TEXT DEFAULT ' #f44336',"
            + PROFIL_SCROLL_SPEED + " INTEGER DEFAULT 5,"
            + PROFIL_SERVICE_COLOR + " TEXT DEFAULT '#BBDEFB',"
            + PROFIL_SERVICE_OPACITY + " INTEGER DEFAULT 5"
            + ");";

    private static final String CONTACT_CREATE = "CREATE TABLE " + CONTACT + "("
            + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTACT_NAME + " TEXT DEFAULT 'unknown',"
            + CONTACT_NUMBER + " TEXT DEFAULT '',"
            + CONTACT_KEY + " INTEGER DEFAULT 0,"
            + CONTACT_PROFIL + " INTEGER NOT NULL CONSTRAINT fkcontact_profil REFERENCES " + PROFIL + " (id) "
            + ");";


    // ---------- CONSTRUCTOR

    public Profils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // ---------- METHODS

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PROFIL_CREATE);
        database.execSQL(CONTACT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Profils.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + PROFIL);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT);
        onCreate(db);
    }

}