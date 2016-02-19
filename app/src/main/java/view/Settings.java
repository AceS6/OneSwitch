package view;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;

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
        if(id == android.R.id.home){ // actions on "previous" button
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
            Preference pref_scroll_speed = findPreference("scroll_speed");
            pref_scroll_speed.setSummary(getPreferenceScreen().getSharedPreferences().getInt("scroll_speed", Globale.engine.getProfil().getScrollSpeed())+"");
            Preference pref_service_color = findPreference("service_color");
            pref_service_color.setSummary(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("service_color", Globale.engine.getProfil().getServiceColorInt())));
            Preference pref_service_opacity = findPreference("service_opacity");
            pref_service_opacity.setSummary(getPreferenceScreen().getSharedPreferences().getInt("service_opacity", Globale.engine.getProfil().getServiceOpacity())+"");
            Preference pointingline_color_v = findPreference("pointingline_color_v");
            pointingline_color_v.setSummary(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_v", Globale.engine.getProfil().getColorLineVInt())));
            Preference pointingline_color_h = findPreference("pointingline_color_h");
            pointingline_color_h.setSummary(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_h", Globale.engine.getProfil().getColorLineHInt())));
            Preference pointingline_size = findPreference("pointingline_size");
            pointingline_size.setSummary(getPreferenceScreen().getSharedPreferences().getInt("pointingline_size", Globale.engine.getProfil().getLineSize())+"");
            Preference pref_speed = findPreference("speed");
            pref_speed.setSummary(getPreferenceScreen().getSharedPreferences().getInt("speed", Globale.engine.getProfil().getLineSpeed())+"");
            Preference pointingsquare_color = findPreference("pointingsquare_color");
            pointingsquare_color.setSummary(OneSwitchData.getSemiTransparentColor(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt("pointingline_color_v", Globale.engine.getProfil().getColorSquareInt()))));
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
            else if(key.equals("scroll_speed")){
                pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt(key, 5)+"");
            }
            else if(key.equals("service_opacity")){
                pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt(key, 5)+"");
            }
            else if(key.equals("service_color")){
                int color=sharedPreferences.getInt(key, 0x00FF00);
                pref.setSummary("#"+Integer.toHexString(color));
            }
            else if(key.equals("pointingline_color_v")){
                int color=sharedPreferences.getInt(key, 0x00FF00);
                pref.setSummary("#"+Integer.toHexString(color));
            }
            else if(key.equals("pointingline_color_h")){
                int color=sharedPreferences.getInt(key, 0x00FF00);
                pref.setSummary(OneSwitchData.getHexaColor(color));
            }
            else if(key.equals("pointingline_size")){
                pref.setSummary(sharedPreferences.getInt(key, 5)+"");
            }
            else if(key.equals("speed")){
                pref.setSummary(getPreferenceScreen().getSharedPreferences().getInt(key, 5)+"");
            }
            else if(key.equals("pointingsquare_color")){
                pref.setSummary(OneSwitchData.getSemiTransparentColor(OneSwitchData.getHexaColor(getPreferenceScreen().getSharedPreferences().getInt(key, 0x00FF00))));
            }
        }
    }
}