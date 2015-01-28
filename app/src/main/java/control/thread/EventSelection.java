package control.thread;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class EventSelection extends Thread{
	
	private View b1, b2, b3;
	private Context c;
	
	public EventSelection(Context c, View b1, View b2, View b3){
		this.b1=b1;
		this.b2=b2;
		this.b3=b3;
		this.c=c;
	}
	
	public void run(){
	    if (b1.getAnimation()!=null&&!b1.getAnimation().hasEnded()){
	        checkIfAnimationDone();
	    } else{
	    	Toast.makeText(c, "b1 fini", Toast.LENGTH_SHORT).show();
	    }
	    
	    if (b2.getAnimation()!=null && !b2.getAnimation().hasEnded()){
	        checkIfAnimationDone();
	    } else{
	    	Toast.makeText(c, "b2 fini", Toast.LENGTH_SHORT).show();
	    }
	    
	    if (b3.getAnimation()!=null && !b3.getAnimation().hasEnded()){
	        checkIfAnimationDone();
	    } else{
	    	Toast.makeText(c, "b3 fini", Toast.LENGTH_SHORT).show();
	    }
	}
	
    private void checkIfAnimationDone(){
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(this, timeBetweenChecks);
    }
    

	
}
