package view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.*;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projeta.oneswitch.R;

import java.util.ArrayList;

import data.Globale;
import data.Profil;
import data.db.ProfilsDAO;

/**
 * Created by Alexis on 18/01/2015.
 */
public class ProfilSelection extends ListActivity{

    final Context context = this;
    private ArrayAdapter<Profil> adapter;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // On va récupérer les différents profils dans la bdd interne
        ProfilsDAO pDao = new ProfilsDAO(this);
        pDao.open();
        ArrayList<Profil> profils = pDao.getProfils();
        pDao.close();


       adapter = new ArrayAdapter<Profil>(this,
                android.R.layout.simple_list_item_1, profils);
        setListAdapter(adapter);

        registerForContextMenu(getListView());

        setTitle(Globale.engine.getProfil().getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        this.swapProfil(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextMenu.add(0, 1, 0, "Supprimer");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                return true;
            case 1:
                this.deleteProfil(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void swapProfil(int position){

        // On sauvegarde les changements qu'il ya éventuellement eu sur le profil courant dans la bdd
        ProfilsDAO pDao = new ProfilsDAO(context);
        pDao.open();
        pDao.saveProfil(Globale.engine.getProfil());
        pDao.close();

        // On change le profil courant avec celui qui a été cliqué
        Profil p = (Profil) getListAdapter().getItem(position);
        Globale.engine.setProfil(p);
        Toast.makeText(this, "Nouveau profil courrant : " + p.getName(), Toast.LENGTH_LONG).show();

        clearPref();
        this.finish();
    }

    public void deleteProfil(int position){

        Profil p = (Profil) getListAdapter().getItem(position);
        // On supprime le profil selectionné de la bdd interne
        ProfilsDAO pDao = new ProfilsDAO(context);
        pDao.open();
        pDao.deleteProfil(p.getId());
        pDao.close();

        // Si le profil supprimé était le profil courrant on met un profil par défaut
        if(Globale.engine.getProfil().getId() == p.getId()){
            Globale.engine.setDefaultProfil();
        }

        adapter.remove(p);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Le profil : "+p.getName()+" a été supprimé", Toast.LENGTH_LONG).show();

        clearPref();

    }

    public void clearPref()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

    }

}
