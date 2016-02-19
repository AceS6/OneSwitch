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

/**
 * Created by sauray on 16/03/15.
 */
public class Click extends AsyncTask<Integer, Void, Boolean>{

    private Context c;
    private WindowManager windowManager;
    private View globalview;
    private Service s;

    public static final int SHORT=1, LONG=2;

    public Click(Context c, WindowManager windowmanager, View globalview, Service s){
        this.s = s;
        this.c=c;
        this.windowManager = windowmanager;
        this.globalview = globalview;
        windowmanager.removeViewImmediate(globalview);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        PackageManager pm = c.getPackageManager();
        if (pm.checkPermission(Manifest.permission.INJECT_EVENTS, c.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            Instrumentation mInst = new Instrumentation();
            Log.i(integers[0]+"", "width");
            Log.i(integers[1]+"", "height");

            int x = integers[0];
            int y = integers[1];
            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis();
            if(integers.length > 2 && integers[2] == Click.LONG){
                Log.i("Click", "long click");
                mInst.sendPointerSync(MotionEvent.obtain(downTime,
                        eventTime, MotionEvent.ACTION_DOWN, x, y, 0));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mInst.sendPointerSync(MotionEvent.obtain(downTime+1000,
                        eventTime+1000,MotionEvent.ACTION_UP, x, y, 0));
            }
            else{
                Log.i("Click", "short click");
                mInst.sendPointerSync(MotionEvent.obtain(downTime,
                        eventTime, MotionEvent.ACTION_DOWN, x, y, 0));
                mInst.sendPointerSync(MotionEvent.obtain(downTime,
                        eventTime,MotionEvent.ACTION_UP, x, y, 0));
            }

            return true;
        } else {
            return false;
        }
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
