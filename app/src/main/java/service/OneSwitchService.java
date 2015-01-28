package service;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Toast;

import com.projeta.oneswitch.R;

import control.listener.OverlayTouchListener;
import control.listener.ServiceEventListener;
import control.listener.SquareOverlayTouchListener;
import data.Globale;
import data.OneSwitchData;

public class OneSwitchService extends Service{

    public final static int LINE_POINTING=1, SQUARE_POINTING=2;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting OneSwitch Service", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int pointingline_color_v = sharedPref.getInt("pointingline_color_v", -60160);
        Log.d("", "loading color :"+pointingline_color_v);
        Globale.engine.getProfil().setColorLineV(pointingline_color_v);
        int pointingline_color_h = sharedPref.getInt("pointingline_color_h", -65429);
        Log.d("", "loading color :"+pointingline_color_h);
        Globale.engine.getProfil().setColorLineH(pointingline_color_h);

        Globale.engine.getProfil().setLineSize(sharedPref.getInt("pointingline_size", 1));

        Globale.engine.getProfil().setColorSquare(sharedPref.getInt("pointingsquare_color", -65429));

        Globale.engine.getProfil().setLineSpeed(sharedPref.getInt("speed", 10));

        Globale.engine.getProfil().setSquareWidth(sharedPref.getInt("pointingsquare_width", 100));
        Globale.engine.getProfil().setSquareHeight(sharedPref.getInt("pointingsquare_height", 100));

        this.listen();

        return super.onStartCommand(intent, flags, startId);
    }

    public void listen(){

        LayoutInflater inflater = LayoutInflater.from(this);

        WindowManager windowmanager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        View globalview = inflater.inflate(R.layout.action_selection, null);
        windowmanager.addView(globalview, params);
        globalview.setOnTouchListener(new ServiceEventListener(this, windowmanager, globalview.findViewById(R.id.button1), globalview.findViewById(R.id.button2), globalview.findViewById(R.id.button3)));

    }

    public static void startPointing(View v){

        int width = Globale.engine.getWidth();
        int height = Globale.engine.getHeight();
        WindowManager windowmanager;
        LayoutInflater inflater = LayoutInflater.from(v.getContext());

        windowmanager = (WindowManager) v.getContext().getSystemService(WINDOW_SERVICE);
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        String pointing = sharedPref.getString(OneSwitchData.pointing_key, v.getContext().getResources().getString(R.string.linepointing));
        int id = OneSwitchService.getServiceId(pointing);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        switch(id){

            case OneSwitchService.LINE_POINTING:
                View globalview_line = inflater.inflate(R.layout.service_linepointing, null);
                View horizontalLine = globalview_line.findViewById(R.id.horizontal_line);
                horizontalLine.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorLineH()));
                LayoutParams paramsHLine = horizontalLine.getLayoutParams();
                paramsHLine.height=Globale.engine.getProfil().getLineSize();
                horizontalLine.setLayoutParams(paramsHLine);
                horizontalLine.invalidate();

                View verticalLine = globalview_line.findViewById(R.id.vertical_line);
                verticalLine.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorLineV()));
                LayoutParams paramsVLine = verticalLine.getLayoutParams();
                paramsVLine.width=Globale.engine.getProfil().getLineSize();
                verticalLine.setLayoutParams(paramsVLine);
                verticalLine.invalidate();
                globalview_line.setOnTouchListener(new OverlayTouchListener(windowmanager, globalview_line, params, horizontalLine, verticalLine, width, height));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                LayoutParams selectParams = selection.getLayoutParams();
                selectParams.width=Globale.engine.getProfil().getSquareWidth();
                selectParams.height=Globale.engine.getProfil().getSquareHeight();
                selection.setLayoutParams(selectParams);
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, selection, width, height));
                break;

            default:

                break;

        }
    }

    public static void startPointing(View v, int id){

        int width = Globale.engine.getWidth();
        int height = Globale.engine.getHeight();
        WindowManager windowmanager;
        LayoutInflater inflater = LayoutInflater.from(v.getContext());

        windowmanager = (WindowManager) v.getContext().getSystemService(WINDOW_SERVICE);
        SharedPreferences sharedPref;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        String pointing = sharedPref.getString(OneSwitchData.pointing_key, v.getContext().getResources().getString(R.string.linepointing));

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        switch(id){

            case OneSwitchService.LINE_POINTING:
                View globalview_line = inflater.inflate(R.layout.service_linepointing, null);
                View horizontalLine = globalview_line.findViewById(R.id.horizontal_line);
                horizontalLine.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorLineH()));
                LayoutParams paramsHLine = horizontalLine.getLayoutParams();
                paramsHLine.height=Globale.engine.getProfil().getLineSize();
                horizontalLine.setLayoutParams(paramsHLine);
                horizontalLine.invalidate();

                View verticalLine = globalview_line.findViewById(R.id.vertical_line);
                verticalLine.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorLineV()));
                LayoutParams paramsVLine = verticalLine.getLayoutParams();
                paramsVLine.width=Globale.engine.getProfil().getLineSize();
                verticalLine.setLayoutParams(paramsVLine);
                verticalLine.invalidate();
                globalview_line.setOnTouchListener(new OverlayTouchListener(windowmanager, globalview_line, params, horizontalLine, verticalLine, width, height));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                LayoutParams selectParams = selection.getLayoutParams();
                selectParams.width=Globale.engine.getProfil().getSquareWidth();
                selectParams.height=Globale.engine.getProfil().getSquareHeight();
                selection.setLayoutParams(selectParams);
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, selection, width, height));
                break;

            default:

                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static String getServiceName(int service){
        String ret=null;
        switch(service){
            case OneSwitchService.LINE_POINTING:
                ret="Line Pointing";
                break;
            case OneSwitchService.SQUARE_POINTING:
                ret="Square Pointing";
                break;
            default:
                ret="Unknown Service";
                break;
        }
        return ret;
    }

    public static int getServiceId(String service){
        int ret=0;
        if(service.equals("Line Pointing")){
            ret=1;
        }
        else if(service.equals("Square Pointing")){
            ret=2;
        }

        return ret;
    }

    public static void clickOnScreen(final int x, final int y) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                Instrumentation mInst = new Instrumentation();
                mInst.sendKeyDownUpSync( KeyEvent.KEYCODE_A );
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0));
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),MotionEvent.ACTION_UP, x, y, 0));
            }
        }).start();

    }

   /* public static void execute(String command) throws Exception{
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(command);
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        }catch(IOException e){
            throw new Exception(e);
        }catch(InterruptedException e){
            throw new Exception(e);
        }

    }*/


}
