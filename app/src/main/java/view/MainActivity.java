package view;

import data.Profil;
import data.db.ProfilsDAO;
import service.OneSwitchService;

import com.projeta.oneswitch.R;
import data.Globale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends Activity {

    final Context context = this;
    private boolean serviceActivated;
    private Switch service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        service = (Switch)this.findViewById(R.id.switch1);

        Globale.engine.setWidth(width);
        Globale.engine.setHeight(height);
        serviceActivated=false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.add_profil){
            addProfil();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startLine(View v){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Intent intent = new Intent(MainActivity.this, OneSwitchService.class);
        intent.putExtra("service", OneSwitchService.LINE_POINTING);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        startService(intent);
        this.finish();
    }

    public void startSquare(View v){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        Globale.engine.getProfil().setDensity(density);


        Intent intent = new Intent(MainActivity.this, OneSwitchService.class);
        intent.putExtra("service", OneSwitchService.SQUARE_POINTING);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        startService(intent);
        this.finish();
    }

    public void startPointing(View v){
        Log.d("start", "start pointing");
        if(this.serviceActivated){
            OneSwitchService.startPointing(v);
        }
        else{
            Toast.makeText(this, "Le service n'est pas démarré", Toast.LENGTH_LONG).show();
        }
    }

    public void startService(View v){
        if(!serviceActivated){
            startService(new Intent(this, OneSwitchService.class));
            Toast.makeText(this, "OneSwitch démarré", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "OneSwitch arrété", Toast.LENGTH_LONG).show();
        }
        serviceActivated=!serviceActivated;
    }

    public void settings(View v){
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    public void profilSelection(View v){
        Intent intent = new Intent(MainActivity.this, ProfilSelection.class);
        startActivity(intent);
    }

    public void addProfil(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Créer un nouveau profil");
        alert.setMessage("Nom : ");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Profil p = new Profil(-1, value, "Line Pointing", 50, 10, "#000000", "#000000", "#000000", 50, 50);

                // On envoi le nouveau profil à la bdd interne
                ProfilsDAO pDao = new ProfilsDAO(context);
                pDao.open();
                int idP = (int) pDao.insertProfil(p);
                pDao.close();

                // On met à jour le nouveau profil dans l'application avec le bon identifiant
                p.setId(idP);
                Globale.engine.setProfil(p);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
