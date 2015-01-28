package view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.*;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

    public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
        final Profil p = (Profil) getListAdapter().getItem(position);
        Toast.makeText(this, "clic long : " + p.getName(), Toast.LENGTH_LONG).show();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Supprimer ce profil?");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

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

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
        return true;
    }

}
