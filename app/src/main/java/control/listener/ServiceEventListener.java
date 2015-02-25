package control.listener;

import java.util.Timer;
import java.util.TimerTask;

import com.projeta.oneswitch.R;

import control.thread.EventSelection;

import data.Globale;
import service.OneSwitchService;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class ServiceEventListener implements OnTouchListener, OnGestureListener, OnClickListener, AnimationListener {
    
	private GestureDetector gestureDetector;
	private View b1, b2, b3;
    private View globalview;
	private WindowManager windowmanager;
	private Context c;
	private Animation hyperspaceJumpAnimation, appear, disappear;
	
	private EventSelection thread;
	
    public ServiceEventListener(Context c, WindowManager windowmanager, View globalView, View b1, View b2, View b3){
        gestureDetector = new GestureDetector(c, this);
        this.c=c;
        this.windowmanager=windowmanager;
        this.globalview=globalView;
        this.b1=b1;
        this.b2=b2;
        this.b3=b3;    
        
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(c, R.anim.hyperspace_jump);   
        appear = AnimationUtils.loadAnimation(c, R.anim.appear);   
        disappear = AnimationUtils.loadAnimation(c, R.anim.disappear); 
		hyperspaceJumpAnimation.setFillAfter(true);
		appear.setFillAfter(true);
		disappear.setFillAfter(true);
		
		appear.setAnimationListener(this);
		disappear.setAnimationListener(this);
		hyperspaceJumpAnimation.setAnimationListener(this);
		/*
*/
		//b1.startAnimation(appear);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
		
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {	
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
        windowmanager.removeView(globalview);
        OneSwitchService.startPointing(globalview, OneSwitchService.LINE_POINTING);
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		 Log.e("", "Longpress detected");  
		 this.appearCompletely();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        disappearCompletely();
			if(v==b1){
				OneSwitchService.startPointing(v, OneSwitchService.LINE_POINTING);
			}
			else if(v==b2){
				OneSwitchService.startPointing(v, OneSwitchService.SQUARE_POINTING);
			}
			else if(v==b3){
                Globale.engine.setServiceState(false);
            }
	}
	
	private void disappearCompletely(){
		b1.startAnimation(disappear);
		b2.startAnimation(disappear);
		b3.startAnimation(disappear);
	}
	
	private void appearCompletely(){
		b1.startAnimation(appear);
		b2.startAnimation(appear);
		b3.startAnimation(appear);
	}
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if(animation==disappear){
			windowmanager.removeView(globalview);
		}
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}


}
