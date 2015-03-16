package control.listener;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.projeta.oneswitch.R;

/**
 * Created by Antoine on 05/02/2015.
 */
public abstract  class PointingSystem {

    protected final Thread clickOnScreen(Context c, final int x, final int y){
        PackageManager pm = c.getPackageManager();
        if (pm.checkPermission(Manifest.permission.INJECT_EVENTS, c.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            return new Thread(new Runnable(){

                @Override
                public void run() {
                    Instrumentation mInst = new Instrumentation();
                    mInst.sendKeyDownUpSync( KeyEvent.KEYCODE_A );
                    mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                    mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),MotionEvent.ACTION_UP, x, y, 0));
                }
            });
        } else {
            Toast.makeText(c, "Permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }
    }


    public void listen(WindowManager windowmanager, View globalview){
        LayoutInflater inflater = LayoutInflater.from(globalview.getContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        globalview = inflater.inflate(R.layout.service_optionpointing, null);
        windowmanager.addView(globalview, params);
        globalview.setOnTouchListener(new ServiceEventListener(globalview.getContext(), windowmanager, globalview));
    }
}
