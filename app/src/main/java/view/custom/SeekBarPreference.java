package view.custom;

	import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import data.Globale;

	public class SeekBarPreference extends DialogPreference {
	        
	    private Context context; 
	    private SeekBar sensitivityLevel = null;
	    private LinearLayout layout = null;
	    public SeekBarPreference(Context context, AttributeSet attrs) { 
	        super(context, attrs); 
	        this.context = context;
	    } 
	    
	    protected void onPrepareDialogBuilder(Builder builder) {  
	        layout = new LinearLayout(context); 
	        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); 
	        layout.setMinimumWidth(400); 
	        layout.setPadding(20, 20, 20, 20); 
	        sensitivityLevel = new SeekBar(context); 
            sensitivityLevel.setMax(10);
	        sensitivityLevel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)); 
	        sensitivityLevel.setProgress(getPersistedInt(0));
	        layout.addView(sensitivityLevel); 
	        builder.setView(layout);
	        //super.onPrepareDialogBuilder(builder); 
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
	            else if(this.getKey().equals("pointingsquare_height")){
	            	Globale.engine.getProfil().setSquareHeight(sensitivityLevel.getProgress());
	            }    
	            else if(this.getKey().equals("pointingsquare_width")){
	            	Globale.engine.getProfil().setSquareWidth(sensitivityLevel.getProgress());
	            }
	        } 
	        super.onDialogClosed(positiveResult);
	    } 


}
