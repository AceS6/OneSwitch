package data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import data.Profil;

/**
 * Created by Alexis on 18/01/2015.
 */
public class ProfilsDAO {

    // ---------- ATTRIBUTES

    protected final static int VERSION = 1;
    protected final static String NOM = "oneswitch.db";
    protected SQLiteDatabase db;
    protected Profils handler;
    private Context context;

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



    // ---------- CONSTRUCTOR

    public ProfilsDAO(Context context) {

        this.handler = new Profils(context, NOM, null, VERSION);
        this.context = context;
    }


    // ---------- METHODS

    public SQLiteDatabase open() {
        db = handler.getWritableDatabase();
        return db;
    } // ---------------------------------------------------------- open()

    public void close() {
        db.close();
    } // ---------------------------------------------------------- close()

    public SQLiteDatabase getDb() {
        return db;
    } // ---------------------------------------------------------- getDb()


    // ----- PROFILS

    public ArrayList<Profil> getProfils(){
        ArrayList<Profil> ret = new ArrayList<Profil>();
        Profil tmp = null;
        int tmpId, tmpLineSpeed, tmpLineSize, tmpSquareWidth, tmpSquareHeight;
        String tmpName, tmpPointing, tmpLineColorH, tmpLineColorV, tmpSquareColor;

        Cursor c = db.query(PROFIL, new String[] {PROFIL_ID, PROFIL_NAME, PROFIL_POINTING, PROFIL_LINE_SPEED, PROFIL_LINE_SIZE, PROFIL_LINE_COLORH, PROFIL_LINE_COLORV, PROFIL_SQUARE_WIDTH, PROFIL_SQUARE_HEIGHT, PROFIL_SQUARE_COLOR}, null, null, null, null, null);
        while(c.moveToNext()){

            tmpId = c.getInt(0);
            tmpName = c.getString(1);
            tmpPointing = c.getString(2);
            tmpLineSpeed = c.getInt(3);
            tmpLineSize = c.getInt(4);
            tmpLineColorH = c.getString(5);
            tmpLineColorV = c.getString(6);
            tmpSquareWidth = c.getInt(7);
            tmpSquareHeight = c.getInt(8);
            tmpSquareColor = c.getString(9);

            tmp = new Profil(tmpId, tmpName, tmpPointing, tmpLineSpeed, tmpLineSize, tmpLineColorH, tmpLineColorV, tmpSquareColor, tmpSquareWidth, tmpSquareHeight);
            ret.add(tmp);
        }
        c.close();

        return ret;
    }

    public Profil getProfil(int idProfil){
        Profil ret = null;
        int tmpId, tmpLineSpeed, tmpLineSize, tmpSquareWidth, tmpSquareHeight;
        String tmpName, tmpPointing, tmpLineColorH, tmpLineColorV, tmpSquareColor;

        Cursor c = db.query(PROFIL, new String[] {PROFIL_ID, PROFIL_NAME, PROFIL_POINTING, PROFIL_LINE_SPEED, PROFIL_LINE_SIZE, PROFIL_LINE_COLORH, PROFIL_LINE_COLORV, PROFIL_SQUARE_WIDTH, PROFIL_SQUARE_HEIGHT, PROFIL_SQUARE_COLOR},  PROFIL_ID + " LIKE '" + idProfil + "'", null, null, null, null);

        while(c.moveToNext()) {

            tmpId = c.getInt(0);
            tmpName = c.getString(1);
            tmpPointing = c.getString(2);
            tmpLineSpeed = c.getInt(3);
            tmpLineSize = c.getInt(4);
            tmpLineColorH = c.getString(5);
            tmpLineColorV = c.getString(6);
            tmpSquareWidth = c.getInt(7);
            tmpSquareHeight = c.getInt(8);
            tmpSquareColor = c.getString(9);

            ret = new Profil(tmpId, tmpName, tmpPointing, tmpLineSpeed, tmpLineSize, tmpLineColorH, tmpLineColorV, tmpSquareColor, tmpSquareWidth, tmpSquareHeight);
        }
        c.close();

        return ret;
    }

    public double insertProfil(Profil p){
        long ret = 0;

        if(p !=  null){

            // On rentre le profil dans la bdd
            ContentValues values = new ContentValues();
            values.put(PROFIL_NAME, p.getName());
            values.put(PROFIL_POINTING, p.getPointing());
            values.put(PROFIL_LINE_SPEED, p.getLineSpeed());
            values.put(PROFIL_LINE_SIZE, p.getLineSize());
            values.put(PROFIL_LINE_COLORH, p.getColorLineH());
            values.put(PROFIL_LINE_COLORV, p.getColorLineV());
            values.put(PROFIL_SQUARE_WIDTH, p.getSquareWidth());
            values.put(PROFIL_SQUARE_HEIGHT, p.getSquareHeight());
            values.put(PROFIL_SQUARE_COLOR, p.getColorSquare());

            ret = db.insert(PROFIL, null, values);

            Toast.makeText(context, "Nouveau profil : "+p.getName(), Toast.LENGTH_LONG).show();

        }
        return ret;
    }

    public void deleteProfil(int idProfil){
        db.delete(PROFIL, PROFIL_ID + "=" + idProfil, null);
    }

    public void saveProfil(Profil p){

        ContentValues values=new ContentValues();

        values.put(PROFIL_NAME , p.getName());
        values.put(PROFIL_POINTING,p.getPointing());
        values.put(PROFIL_LINE_SPEED, p.getLineSpeed());
        values.put(PROFIL_LINE_SIZE , p.getLineSize());
        values.put(PROFIL_LINE_COLORH , p.getColorLineH());
        values.put(PROFIL_LINE_COLORV , p.getColorLineV());
        values.put(PROFIL_SQUARE_WIDTH , p.getSquareWidth());
        values.put(PROFIL_SQUARE_HEIGHT , p.getSquareHeight());
        values.put(PROFIL_SQUARE_COLOR , p.getColorSquare());

        db.update(PROFIL, values, PROFIL_ID + "=" +  p.getId(), null);

    }

}
