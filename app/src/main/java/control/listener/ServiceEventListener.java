package control.listener;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.projeta.oneswitch.R;

import control.thread.EventSelection;
import data.Globale;
import service.OneSwitchService;

public class ServiceEventListener implements OnTouchListener, OnGestureListener, OnClickListener, AnimationListener {

    private GestureDetector gestureDetector;
    private ImageButton i1, i2, i3,i4, i5, i6, i7, i8;
    private ImageButton current;
    private View globalview;
    private WindowManager windowmanager;
    private Context c;
    private Animation hyperspaceJumpAnimation, appear, disappear;
    private ProgressTask progress;

    private EventSelection thread;

    public ServiceEventListener(Context c, WindowManager windowmanager, View globalView){

        gestureDetector = new GestureDetector(c, this);
        this.c=c;
        this.windowmanager=windowmanager;
        this.globalview=globalView;
        this.i1 = (ImageButton) globalView.findViewById(R.id.imageButton);
        this.i2 = (ImageButton) globalView.findViewById(R.id.imageButton2);
        this.i3 = (ImageButton) globalView.findViewById(R.id.imageButton3);
        this.i4 = (ImageButton) globalView.findViewById(R.id.imageButton4);
        this.i5 = (ImageButton) globalView.findViewById(R.id.imageButton5);
        this.i6 = (ImageButton) globalView.findViewById(R.id.imageButton6);
        this.i7 = (ImageButton) globalView.findViewById(R.id.imageButton7);
        this.i8 = (ImageButton) globalView.findViewById(R.id.imageButton8);

        current = null;

        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);
        i3.setVisibility(View.INVISIBLE);
        i4.setVisibility(View.INVISIBLE);
        i5.setVisibility(View.INVISIBLE);
        i6.setVisibility(View.INVISIBLE);
        i7.setVisibility(View.INVISIBLE);
        i8.setVisibility(View.INVISIBLE);


        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(c, R.anim.hyperspace_jump);
        appear = AnimationUtils.loadAnimation(c, R.anim.appear);
        disappear = AnimationUtils.loadAnimation(c, R.anim.disappear);
        hyperspaceJumpAnimation.setFillAfter(true);
        appear.setFillAfter(true);
        disappear.setFillAfter(true);

        appear.setAnimationListener(this);
        disappear.setAnimationListener(this);
        hyperspaceJumpAnimation.setAnimationListener(this);

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
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        windowmanager.removeView(globalview);
        boolean stop = false;

        if(current == null){
            OneSwitchService.startPointing(globalview, OneSwitchService.LINE_POINTING);
        } else {

            if (current == i1) {
                Toast.makeText(c, "Swap right", Toast.LENGTH_SHORT).show();
            } else if (current == i2) {
                Toast.makeText(c, "Pinch in", Toast.LENGTH_SHORT).show();
            } else if (current == i3) {
                Toast.makeText(c, "Scroll up", Toast.LENGTH_SHORT).show();
            } else if (current == i4) {
                Globale.engine.setServiceState(false);
                stop = true;
            } else if (current == i5) {
                Toast.makeText(c, "Pinch out", Toast.LENGTH_SHORT).show();
            } else if (current == i6) {
                Toast.makeText(c, "Scroll up", Toast.LENGTH_SHORT).show();
            } else if (current == i7) {
                Toast.makeText(c, "Scroll Settings", Toast.LENGTH_SHORT).show();
            } else if (current == i8) {
                Toast.makeText(c, "Swap left", Toast.LENGTH_SHORT).show();
            }

            progress.cancel(true);
            disappearCompletely();
            current = null;

               if(!stop) {
                   LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
                   WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                           WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
                   globalview = inflater.inflate(R.layout.service_optionpointing, null);
                   windowmanager.addView(globalview, params);
                   globalview.setOnTouchListener(new ServiceEventListener(globalview.getContext(), windowmanager, globalview));
               }
        }

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
        current = i2;
        progress = new ProgressTask(this);
        progress.execute();
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        //windowmanager.removeView();
    }

    private void disappearCompletely(){
        i1.startAnimation(disappear);
        i2.startAnimation(disappear);
        i3.startAnimation(disappear);
        i4.startAnimation(disappear);
        i5.startAnimation(disappear);
        i6.startAnimation(disappear);
        i7.startAnimation(disappear);
        i8.startAnimation(disappear);
    }

    private void appearCompletely(){
        i1.startAnimation(appear);
        i2.startAnimation(appear);
        i3.startAnimation(appear);
        i4.startAnimation(appear);
        i5.startAnimation(appear);
        i6.startAnimation(appear);
        i7.startAnimation(appear);
        i8.startAnimation(appear);
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

    public void changeCurrentImage(int id){
        current.setAlpha(0.5f);

        switch(id){
            case 1:
                this.current = i2;
                break;

            case 2:
                this.current = i3;
                break;

            case 3:
                this.current = i1;
                break;

            case 4:
                this.current = i7;
                break;

            case 5:
                this.current = i8;
                break;

            case 6:
                this.current = i6;
                break;

            case 7:
                this.current = i5;
                break;

            case 8:
                this.current = i4;
                break;

            default:
                this.current = i1;
                break;
        }

        current.setAlpha(1.0f);
    }


    // L'AsyncTask est bien une classe interne statique
    private static class ProgressTask extends AsyncTask<Void, Integer, Boolean>{

        private ServiceEventListener sel;

        // Progression du téléchargement
        private boolean progress = true;
        private int progression = 0;

        public ProgressTask(ServiceEventListener sel){
            this.sel = sel;
        }

        @Override
        protected void onPreExecute () {

        }

        @Override

        protected void onPostExecute (Boolean result) {

        }

        @Override

        protected Boolean doInBackground (Void... arg0) {

            try {

                while(progress) {

                    Thread.sleep(1000);
                    publishProgress();

                }

                return true;

            }catch(InterruptedException e) {

                e.printStackTrace();

                return false;

            }

        }

        @Override

        protected void onProgressUpdate (Integer... prog) {
            change();
        }

        private void change(){
            if(progression < 8){
                progression++;
            } else {
                progression = 1;
            }

            sel.changeCurrentImage(progression);
        }

    }
}
