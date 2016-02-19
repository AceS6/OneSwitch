package view.custom;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import data.Globale;

	public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {
	        
	    private Context context; 
	    private SeekBar sensitivityLevel = null;
	    private LinearLayout layout = null;
        private TextView tv = null;
	    public SeekBarPreference(Context context, AttributeSet attrs) { 
	        super(context, attrs); 
	        this.context = context;
	    } 
	    
	    protected void onPrepareDialogBuilder(Builder builder) {  
	        layout = new LinearLayout(context); 
	        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);
	        layout.setMinimumWidth(400); 
	        layout.setPadding(20, 20, 20, 20); 
	        sensitivityLevel = new SeekBar(context); 
            sensitivityLevel.setMax(10);
	        sensitivityLevel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)); 
	        sensitivityLevel.setProgress(getPersistedInt(0));
            this.tv = new TextView(this.getContext());
            tv.setGravity(LinearLayout.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(16.0f);
            layout.addView(tv);
            this.tv.setText("" + sensitivityLevel.getProgress());
            sensitivityLevel.setOnSeekBarChangeListener(this);
	        layout.addView(sensitivityLevel); 
	        builder.setView(layout);
	    } 
	    
	    protected void onDialogClosed(boolean positiveResult) { 
	        if(positiveResult){ 
	            persistInt(sensitivityLevel.getProgress());
	            
	            if(this.getKey().equals("pointingline_size")){
	            	Globale.engine.getProfil().setLineSize(sensitivityLevel.getProgress());
	            }
	            else if(this.getKey().equals("speed")){
	            	Globale.engine.getProfil().setLineSpeed(sensitivityLevel.getProgress());
	            }
                else if(this.getKey().equals("scroll_speed")){
                    Globale.engine.getProfil().setScrollSpeed(sensitivityLevel.getProgress());
                }
                else if(this.getKey().equals("service_opacity")){
                    Globale.engine.getProfil().setServiceOpacity(sensitivityLevel.getProgress());
                }

	        } 
	        super.onDialogClosed(positiveResult);
	    }


        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            this.tv.setText(""+sensitivityLevel.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
