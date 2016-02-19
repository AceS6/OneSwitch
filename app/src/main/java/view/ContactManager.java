package view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.projeta.oneswitch.R;

import data.Contact;
import data.Globale;
import view.custom.ContactAdapter;

/**
 * Created by Alexis on 19/03/2015.
 */
public class ContactManager extends ListActivity {

    private final Context context = this;
    private ContactAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(Globale.engine.getProfil().getName());
        this.cAdapter = new ContactAdapter(this, Globale.engine.getProfil().getContacts());
        getListView().setAdapter(cAdapter);
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
        this.manageContact(position);
    }

    private void manageContact(final int position){
        final Contact c = cAdapter.getItem(position);

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.contact_dialog, null);

        //Création de l'AlertDialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        alert.setView(alertDialogView);

        alert.setTitle("Contact n°"+ c.getKey());

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText etName = (EditText) alertDialogView.findViewById(R.id.EditTextName);
                EditText etNumber = (EditText) alertDialogView.findViewById(R.id.EditTextNumber);

                Globale.engine.getProfil().getContacts().get(c.getKey()).setName(etName.getText().toString());
                Globale.engine.getProfil().getContacts().get(c.getKey()).setNumber(etNumber.getText().toString());

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();

        cAdapter.notifyDataSetChanged();
    }

}
