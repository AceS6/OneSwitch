package view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // On va récupérer les différents profils dans la bdd interne
        ProfilsDAO pDao = new ProfilsDAO(this);
        pDao.open();
        ArrayList<Profil> profils = pDao.getProfils();
        pDao.close();


        ArrayAdapter<Profil> adapter = new ArrayAdapter<Profil>(this,
                android.R.layout.simple_list_item_1, profils);
        setListAdapter(adapter);

        registerForContextMenu(getListView());
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
        Profil p = (Profil) getListAdapter().getItem(position);

        Globale.engine.setProfil(p);
        Toast.makeText(this, "Profil courrant : " + p.getName(), Toast.LENGTH_LONG).show();
        this.finish();
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

    public void deleteProfil(int position){

        Profil p = (Profil) getListAdapter().getItem(position);
        // On supprime le profil selectionné de la bdd interne
        ProfilsDAO pDao = new ProfilsDAO(context);
        pDao.open();
        pDao.deleteProfil(p.getId());
        pDao.close();

        // Si le profil supprimé était le profil courrant on met un profil par défaut
        if(Globale.engine.getProfil().getId() == p.getId()){
            Profil defaultP = new Profil(-1, "Default", "Line Pointing", 50, 10, "#000000", "#000000", "#000000", 50, 50);
            Globale.engine.setProfil(defaultP);
        }

        ((BaseAdapter) getListView().getAdapter()).notifyDataSetChanged();

    }

}
