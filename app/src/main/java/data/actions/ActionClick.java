package data.actions;

import android.Manifest;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.projeta.oneswitch.R;

import control.listener.ServiceEventListener;

/**
 * Created by sauray on 20/03/15.
 */
public class ActionClick extends AsyncTask<Integer, Void, Boolean>{

    private Context c;
    private WindowManager windowManager;
    private View globalview;
    private Service s;

    public ActionClick(Context c, WindowManager windowmanager, View globalview, Service s){
        this.s = s;
        this.c = c;
        this.windowManager = windowmanager;
        this.globalview = globalview;
        windowManager.removeViewImmediate(globalview);
    }
    @Override
    protected Boolean doInBackground(Integer... integers) {

        if(integers.length == 0){
            throw new IllegalArgumentException("An action has to be provided.");
        }

        PackageManager pm = c.getPackageManager();
        if (pm.checkPermission(Manifest.permission.INJECT_EVENTS, c.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            Instrumentation mInst = new Instrumentation();
            mInst.sendKeyDownUpSync(integers[0]);
            return true;
        }
        else {
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
