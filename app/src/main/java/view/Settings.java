package view;

import service.OneSwitchService;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.projeta.oneswitch.R;
import data.Globale;
import data.OneSwitchData;

public class Settings extends PreferenceActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new InnerPreferenceFragment()).commit();

        setTitle(Globale.engine.getProfil().getName());
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){	// actions on "previous" button
        	this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
	
	public static class InnerPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference pref = findPreference("pref_pointing");
            pref.setSummary(getPreferenceScreen().getSharedPreferences().getString("pref_pointing", Globale.engine.getProfil().getPointing()));
            
            Preference pref_speed = findPreference("speed");
            pref_speed.setSummary(getPreferenceScreen().getSharedPreferences().getInt("speed", Globale.engine.getProfil().getLineSpeed())+"");
           
            Preference pointingline_color_v = findPreference("pointingline_color_v");
            pointingline_color_v.setSummary(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_v", Globale.engine.getProfil().getColorLineVInt())));
            
            Preference pointingline_color_h = findPreference("pointingline_color_h");
            pointingline_color_h.setSummary(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_h", Globale.engine.getProfil().getColorLineHInt())));
            
            Preference pointingline_size = findPreference("pointingline_size");
            pointingline_size.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingline_size", Globale.engine.getProfil().getLineSize())+"");
            
            Preference pointingsquare_color = findPreference("pointingsquare_color");
            pointingsquare_color.setSummary(OneSwitchData.getSemiTransparentColor(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_v", Globale.engine.getProfil().getColorSquareInt()))));
            
            Preference pointingsquare_width = findPreference("pointingsquare_width");
            pointingsquare_width.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingsquare_width", Globale.engine.getProfil().getSquareWidth())+"");
            
            Preference pointingsquare_height = findPreference("pointingsquare_height");
            pointingsquare_height.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingsquare_height", Globale.engine.getProfil().getSquareHeight())+"");
        }
        
        
        @Override
		public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);

        }

        @Override
		public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

    	@Override
    	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
    			String key) {
    		// TODO Auto-generated method stub
            Preference pref = findPreference(key);
    		 if (key.equals("pref_pointing")) {
    	            // Set summary to be the user-description for the selected value
    	            pref.setSummary(sharedPreferences.getString(key, this.getResources().getString(R.string.linepointing)));
    	      }
    		 else if(key.equals("speed")){
    	            pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt(key, 10)+"");
    		 }
    		 else if(key.equals("pointingline_color_v")){
    			 int color=sharedPreferences.getInt(key, 0x00FF00); 	
    			 System.out.println(String.format("#%X", color)); //use lower case x for lowercase hex
    			 pref.setSummary("#"+Integer.toHexString(color));
    		 }
    		 else if(key.equals("pointingline_color_h")){
    			 int color=sharedPreferences.getInt(key, 0x00FF00); 	
    			 pref.setSummary(OneSwitchData.getHexaColor(color));
    		 }
    		 else if(key.equals("pointingline_size")){
    			 pref.setSummary(sharedPreferences.getInt(key, R.integer.pointage_ligne_taille_defaut)+"");
    		 }
    		 else if(key.equals("pointingsquare_color")){
    	           pref.setSummary(OneSwitchData.getSemiTransparentColor(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt(key, 0x00FF00))));
    		 }
    		 else if(key.equals("pointingsquare_height")){
    			 pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingsquare_height", 50)+"");
    		 }
    		 else if(key.equals("pointingsquare_width")){
    			 pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingsquare_width", 50)+"");
    		 }
    	        

    	}
    	
    }
    
}
