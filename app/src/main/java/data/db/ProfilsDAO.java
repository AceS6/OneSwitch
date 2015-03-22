package data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import data.Contact;
import data.Profil;

/**
 * Created by Alexis on 18/01/2015.
 */
public class ProfilsDAO {

    // ---------- ATTRIBUTES

    protected final static int VERSION = 1;
    protected final static String NOM = "oneswitch2.db";
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
    public static final String PROFIL_SCROLL_SPEED = "scrollspeed";


    // ----- CONTACT

    public static final String CONTACT = "contact";
    public static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_NUMBER = "number";
    public static final String CONTACT_KEY = "key";
    public static final String CONTACT_PROFIL = "profil";



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
        int tmpId, tmpLineSpeed, tmpLineSize, tmpSquareWidth, tmpSquareHeight, tmpScrollSpeed;
        String tmpName, tmpPointing, tmpLineColorH, tmpLineColorV, tmpSquareColor;
        HashMap<Integer, Contact> tmpContacts = new HashMap<Integer, Contact>();

        Cursor c = db.query(PROFIL, new String[] {PROFIL_ID, PROFIL_NAME, PROFIL_POINTING, PROFIL_LINE_SPEED, PROFIL_LINE_SIZE, PROFIL_LINE_COLORH, PROFIL_LINE_COLORV, PROFIL_SQUARE_WIDTH, PROFIL_SQUARE_HEIGHT, PROFIL_SQUARE_COLOR, PROFIL_SCROLL_SPEED}, null, null, null, null, null);
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
            tmpScrollSpeed = c.getInt(10);
            tmpContacts = this.getContacts(tmpId);

            tmp = new Profil(tmpId, tmpName, tmpPointing, tmpLineSpeed, tmpLineSize, tmpLineColorH, tmpLineColorV, tmpSquareColor, tmpSquareWidth, tmpSquareHeight, tmpScrollSpeed, tmpContacts);
            ret.add(tmp);
        }
        c.close();

        return ret;
    }

    public Profil getProfil(int idProfil){

        Profil ret = null;
        int tmpId, tmpLineSpeed, tmpLineSize, tmpSquareWidth, tmpSquareHeight, tmpScrollSpeed;
        String tmpName, tmpPointing, tmpLineColorH, tmpLineColorV, tmpSquareColor;
        HashMap<Integer, Contact> tmpContacts = new HashMap<Integer, Contact>();

        Cursor c = db.query(PROFIL, new String[] {PROFIL_ID, PROFIL_NAME, PROFIL_POINTING, PROFIL_LINE_SPEED, PROFIL_LINE_SIZE, PROFIL_LINE_COLORH, PROFIL_LINE_COLORV, PROFIL_SQUARE_WIDTH, PROFIL_SQUARE_HEIGHT, PROFIL_SQUARE_COLOR, PROFIL_SCROLL_SPEED}, PROFIL_ID + " LIKE '" + idProfil + "'", null, null, null, null);
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
            tmpScrollSpeed = c.getInt(10);
            tmpContacts = this.getContacts(tmpId);

            ret = new Profil(tmpId, tmpName, tmpPointing, tmpLineSpeed, tmpLineSize, tmpLineColorH, tmpLineColorV, tmpSquareColor, tmpSquareWidth, tmpSquareHeight, tmpScrollSpeed, tmpContacts);
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
            values.put(PROFIL_SCROLL_SPEED, p.getScrollSpeed());


            ret = db.insert(PROFIL, null, values);
            p.setId((int) ret);
            this.insertContacts(p);

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
        values.put(PROFIL_SCROLL_SPEED, p.getScrollSpeed());

        this.saveContacts(p);

        db.update(PROFIL, values, PROFIL_ID + "=" + p.getId(), null);

    }


    // ----- CONTACT

    public HashMap<Integer, Contact> getContacts(int idProfil){

        HashMap<Integer, Contact> ret = new HashMap<Integer, Contact>();
        int tmpId, tmpKey;
        String tmpName, tmpNumber;

        Cursor c = db.query(CONTACT, new String[] {CONTACT_ID, CONTACT_NAME, CONTACT_NUMBER, CONTACT_KEY}, CONTACT_PROFIL + " LIKE '" + idProfil + "'", null, null, null, null);
        while(c.moveToNext()){

            tmpId = c.getInt(0);
            tmpName = c.getString(1);
            tmpNumber = c.getString(2);
            tmpKey = c.getInt(3);

            ret.put(tmpKey, new Contact(tmpId, tmpName, tmpNumber, tmpKey));

            Log.d("Selection contact", tmpName);

        }
        c.close();

        return ret;
    }

    public double insertContacts(Profil p){
        long ret = 0;
        Contact tmpContact = null;
        HashMap<Integer, Contact> contacts = p.getContacts();

        if(contacts !=  null){

            Iterator<Contact> itContacts= contacts.values().iterator();
            while (itContacts.hasNext()) {
                tmpContact = itContacts.next();

                // On rentre le contact dans la bdd
                ContentValues values = new ContentValues();
                values.put(CONTACT_NAME, tmpContact.getName());
                values.put(CONTACT_NUMBER, tmpContact.getNumber());
                values.put(CONTACT_KEY, tmpContact.getKey());
                values.put(CONTACT_PROFIL, p.getId());

                ret = db.insert(CONTACT, null, values);

                Log.d("Insertion contact", ret+ "");

            }
        }
        return ret;
    }


    public double saveContacts(Profil p){
        long ret = 0;
        Contact tmpContact = null;
        HashMap<Integer, Contact> contacts = p.getContacts();

        if(contacts !=  null){

            Iterator<Contact> itContacts= contacts.values().iterator();
            while (itContacts.hasNext()) {
                tmpContact = itContacts.next();

                // On rentre le contact dans la bdd
                ContentValues values = new ContentValues();
                values.put(CONTACT_NAME, tmpContact.getName());
                values.put(CONTACT_NUMBER, tmpContact.getNumber());
                values.put(CONTACT_KEY, tmpContact.getKey());
                values.put(CONTACT_PROFIL, p.getId());

                ret = db.update(CONTACT, values, CONTACT_ID + "=" + tmpContact.getId(), null);

                Log.d("Save contact", ret+ "");

            }
        }
        return ret;
    }




}