package control.listener;


import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.projeta.oneswitch.R;

import data.Globale;
import view.custom.ContactServiceAdapter;

public class OverlayPhoneListener implements View.OnTouchListener, GestureDetector.OnGestureListener, View.OnClickListener, Animation.AnimationListener {

    private GestureDetector gestureDetector;
    private ListView list;
    private ContactServiceAdapter csAdapter;
    private ImageButton i1, i2;
    private View globalview;
    private WindowManager windowmanager;
    private ImageButton currentView;
    private Context c;
    private Service s;
    private Animation hyperspaceJumpAnimation, appear, disappear;
    private ProgressTask progress;

    public OverlayPhoneListener(Context c, WindowManager windowmanager, View globalView, Service s){

        FrameLayout background = (FrameLayout) globalView.findViewById(R.id.frame_background);
        background.setBackgroundColor(Globale.engine.getProfil().getServiceColorInt());
        background.setAlpha(Globale.engine.getProfil().getServiceOpacityFloat());

        gestureDetector = new GestureDetector(c, this);
        this.c=c;
        this.s = s;
        this.windowmanager=windowmanager;
        this.globalview=globalView;

        this.i1 = (ImageButton) globalView.findViewById(R.id.imageButton1);
        this.i2 = (ImageButton) globalView.findViewById(R.id.imageButton2);

        i1.setVisibility(View.INVISIBLE);
        i2.setVisibility(View.INVISIBLE);

        csAdapter = new ContactServiceAdapter(this.c, Globale.engine.getProfil().getContacts());

        this.list = (ListView) globalView.findViewById(R.id.listViewContacts);
        this.list.setFocusable(false);
        this.list.setItemsCanFocus(false);
        list.setAdapter(csAdapter);

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

        this.appearCompletely();
        Globale.engine.setFocus(1);
        this.currentView = null;
        progress = new ProgressTask(this);
        progress.execute();
    }

    public void call(int contactKey) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + Globale.engine.getProfil().getContacts().get(contactKey).getNumber()));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            s.startActivity(callIntent);
        } catch (ActivityNotFoundException e){
            Log.e("Calling", "Call failed", e);
        }

        LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        globalview = inflater.inflate(R.layout.service_optionpointing, null);
        windowmanager.addView(globalview, params);
        globalview.setOnTouchListener(new ServiceEventListener(globalview.getContext(), windowmanager, globalview, s));
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

        if(currentView == null){

            this.call(Globale.engine.getFocus());

        } else {

            if (currentView == i1) {

                LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
                globalview = inflater.inflate(R.layout.service_callerpointing, null);
                windowmanager.addView(globalview, params);
                globalview.setOnTouchListener(new OverlayCallerListener(globalview.getContext(), windowmanager, globalview, s));

            } else if (currentView == i2) {

                LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
                globalview = inflater.inflate(R.layout.service_optionpointing, null);
                windowmanager.addView(globalview, params);
                globalview.setOnTouchListener(new ServiceEventListener(globalview.getContext(), windowmanager, globalview, s));

            }

        }

        progress.cancel(true);
        disappearCompletely();
        currentView = null;


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
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        //windowmanager.removeView();
    }

    private void disappearCompletely(){
        i1.startAnimation(disappear);
        i2.startAnimation(disappear);
    }

    private void appearCompletely(){
        i1.startAnimation(appear);
        i2.startAnimation(appear);
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

    public void changeFocus(int id){

        Globale.engine.setFocus(id);
        csAdapter.notifyDataSetChanged();

        if(id == 8){
            currentView = i1;
            currentView.setAlpha(1.0f);
        } else if( id == 9){
            currentView.setAlpha(0.5f);
            currentView = i2;
            currentView.setAlpha(1.0f);
        } else if (id == 1){

            if(currentView != null){
                currentView.setAlpha(0.5f);
                currentView = null;
            }

        } else {
            currentView = null;
        }

    }


    // L'AsyncTask est bien une classe interne statique
    private static class ProgressTask extends AsyncTask<Void, Integer, Boolean> {

        private OverlayPhoneListener sel;
        private int sleepTime;

        // Progression du téléchargement
        private boolean progress = true;
        private int progression = 0;

        public ProgressTask(OverlayPhoneListener sel){
            this.sel = sel;
            this.sleepTime = (15-(Globale.engine.getProfil().getScrollSpeed()))*100;
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

                    Thread.sleep(sleepTime);
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
            if(progression < 9){
                progression++;
            } else {
                progression = 1;
            }

            sel.changeFocus(progression);
        }

    }
}
