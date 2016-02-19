package data.actions;

import android.Manifest;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.projeta.oneswitch.R;

import control.listener.ServiceEventListener;
import data.Globale;

/**
 * Created by sauray on 16/03/15.
 */
public class Swipe extends AsyncTask<Integer, Void, Boolean> {

    private Context c;
    private WindowManager windowManager;
    private View globalview;
    private Service s;

    public static final Integer LEFT=1, RIGHT=2, UP=3, DOWN=4, SETTINGS=5;

    public Swipe(Context c, WindowManager windowmanager, View globalview, Service s){
        this.s = s;
        this.c=c;
        this.windowManager = windowmanager;
        this.globalview = globalview;
        windowManager.removeViewImmediate(globalview);
        Log.d("swap", "swap");
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {

        if(integers.length == 0){
            throw new IllegalArgumentException("A direction should be provided to the swipe");
        }
        PackageManager pm = c.getPackageManager();
        if (pm.checkPermission(Manifest.permission.INJECT_EVENTS, c.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            Instrumentation inst = new Instrumentation();

            int width = Globale.engine.getWidth() - 50;
            int height = Globale.engine.getHeight() - 50;

            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis();
            if(integers[0]==LEFT){
                Log.d("left", "swap");
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_DOWN, 0,  Globale.engine.getHeight()/ 2, 0));

                int i=0;
                while(i<width){
                    inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_MOVE,i, height / 2, 0));
                    i = i+50;
                }
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_UP, i, height / 2, 0));
            }
            else if (integers[0]==RIGHT){
                Log.d("right", "swap");
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_DOWN, width,  height / 2, 0));

                int i = width;
                while(i > 0){
                    inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_MOVE,i, height / 2, 0));
                    i=i-50;
                }
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_UP, i, height / 2, 0));
            }
            else if (integers[0]==SETTINGS){
                width = Globale.engine.getWidth();
                height = Globale.engine.getHeight() - 50;
                Log.d("up", "swap");
                int i = 0;
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_DOWN, width/2,  i, 0));

                while(i < height){
                    inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_MOVE,width/2, i, 0));
                    i=i+50;
                }
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_UP, width/2, i, 0));
            }
            else if (integers[0]==UP){
                width = Globale.engine.getWidth();
                height = Globale.engine.getHeight() - 50;
                Log.d("up", "swap");
                int i = height/5;
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_DOWN, width/2,  i, 0));

                while(i < height){
                    inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_MOVE,width/2, i, 0));
                    i=i+50;
                }
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_UP, width/2, i, 0));
            }

            else if (integers[0]==DOWN){
                width = Globale.engine.getWidth();
                height = Globale.engine.getHeight() - 50;
                Log.d("up", "swap");
                int i = height- height/5;
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_DOWN, width/2,  i, 0));

                while(i > 0){
                    inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_MOVE,width/2, i, 0));
                    i=i-50;
                }
                inst.sendPointerSync(MotionEvent.obtain(downTime, eventTime,
                        MotionEvent.ACTION_UP, width/2, i, 0));
            }

            return true;
        }

        return false;
    }

    protected void onPostExecute(Boolean result){
        if(result==false){
            Toast.makeText(c, "Permission not granted", Toast.LENGTH_LONG).show();
        }
        LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        globalview = inflater.inflate(R.layout.service_optionpointing, null);
        windowManager.addView(globalview, params);
        globalview.setOnTouchListener(new ServiceEventListener(globalview.getContext(), windowManager, globalview, s));
    }
}
