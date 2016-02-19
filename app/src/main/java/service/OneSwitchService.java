package service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

    private static Binder binder;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new Binder();
    }

    public class Binder extends android.os.Binder{
        public OneSwitchService getService(){
            return OneSwitchService.this;
        }
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

        Globale.engine.getProfil().setPointing(sharedPref.getString("pref_pointing", "Line Pointing"));

        this.listen();

        return super.onStartCommand(intent, flags, startId);
    }

    public void listen(){

        LayoutInflater inflater = LayoutInflater.from(this);

        final WindowManager windowmanager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, 0, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;

        View globalview = inflater.inflate(R.layout.service_optionpointing, null);
        windowmanager.addView(globalview, params);
        globalview.setOnTouchListener(new ServiceEventListener(this, windowmanager, globalview, this));

    }
    public void startLinepointing(View v){
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
                globalview_line.setOnTouchListener(new OverlayTouchListener(v.getContext(),windowmanager, globalview_line, params, horizontalLine, verticalLine, new int[]{0, 0, 0, 0}, this));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                LayoutParams selectParams = selection.getLayoutParams();
                selectParams.width=width/2;
                selectParams.height=height/2;
                selection.setLayoutParams(selectParams);
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, this));
                break;

            default:

                break;
        }
    }

    public static void startPointing(View v){
        startPointing(v, new int[]{0, 0, 0, 0});
    }

    public static void startPointing(View v, int[] viewParameters){
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
                globalview_line.setOnTouchListener(new OverlayTouchListener(v.getContext(),windowmanager, globalview_line, params, horizontalLine, verticalLine, viewParameters, binder.getService()));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, binder.getService()));
                break;

            default:

                break;

        }
    }
    public static void startPointing(View v, int id){
        startPointing(v, id, new int[]{0, 0, 0, 0});
    }

    public static void startPointing(View v, int id, int[] viewParameters){

        WindowManager windowmanager;
        LayoutInflater inflater = LayoutInflater.from(v.getContext());

        windowmanager = (WindowManager) v.getContext().getSystemService(WINDOW_SERVICE);
        windowmanager.removeViewImmediate(v);
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
                globalview_line.setOnTouchListener(new OverlayTouchListener(v.getContext(),windowmanager, globalview_line, params, horizontalLine, verticalLine, viewParameters, binder.getService()));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                LayoutParams selectParams = selection.getLayoutParams();
                selectParams.width= Globale.engine.getWidth();
                selectParams.height=Globale.engine.getHeight();
                selection.setLayoutParams(selectParams);
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, binder.getService()));
                break;

            default:

                break;

        }
    }

    public static void startTestPointing(View v){

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
                globalview_line.setOnTouchListener(new OverlayTouchListener(v.getContext(),windowmanager, globalview_line, params, horizontalLine, verticalLine, new int[]{0,0,0,0}, binder.getService()));
                break;

            case OneSwitchService.SQUARE_POINTING:
                View globalview_square = inflater.inflate(R.layout.service_squarepointing, null);
                View selection = globalview_square.findViewById(R.id.square_selection);
                selection.setBackgroundColor(Color.parseColor(Globale.engine.getProfil().getColorSquare()));
                LayoutParams selectParams = selection.getLayoutParams();
               /* selectParams.width=Globale.engine.getProfil().getSquareWidth();
                selectParams.height=Globale.engine.getProfil().getSquareHeight();*/
                selection.setLayoutParams(selectParams);
                globalview_square.setOnTouchListener(new SquareOverlayTouchListener(windowmanager, globalview_square, params, binder.getService()));
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
}
