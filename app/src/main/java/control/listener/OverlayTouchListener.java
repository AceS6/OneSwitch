package control.listener;

import android.Manifest;
import android.app.Instrumentation;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.projeta.oneswitch.R;
import com.robotium.solo.Solo;

import java.io.IOException;

import data.Globale;
import pointing.line.MoveHorizontalLine;
import pointing.line.MoveVerticalLine;
import service.OneSwitchService;

public class OverlayTouchListener extends PointingSystem implements OnTouchListener, Runnable{

    private WindowManager windowmanager;
    private WindowManager.LayoutParams params;
    private View globalview;
    private View horizontalLine, verticalLine;
    private int state;

    public final int HORIZONTALMOVE=1, VERTICALMOVE=2, END=3;

    private final String TAG="OverlayTouchListener";

    private MoveHorizontalLine horizontalMove;
    private MoveVerticalLine verticalMove;


    public OverlayTouchListener(WindowManager windowmanager, View globalview, WindowManager.LayoutParams params, View horizontalLine, View verticalLine, int width, int height){
        this.windowmanager=windowmanager;
        this.globalview=globalview;
        this.horizontalLine=horizontalLine;
        this.verticalLine=verticalLine;
        this.params=params;

        windowmanager.addView(globalview, params);
        horizontalMove = new MoveHorizontalLine(horizontalLine);
        horizontalMove.execute();
        state=HORIZONTALMOVE;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        v.performClick();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN)
        {
            Log.d(TAG, "OnTouchButton");
            if(state==HORIZONTALMOVE){
                state=VERTICALMOVE;
                horizontalMove.cancel(true);
                verticalMove = new MoveVerticalLine(verticalLine);
                verticalMove.execute();
            }
            else if(state==VERTICALMOVE){
                verticalMove.cancel(true);
                state=END;

                RelativeLayout.LayoutParams paramsH = (RelativeLayout.LayoutParams) horizontalLine.getLayoutParams();
                RelativeLayout.LayoutParams paramsV = (RelativeLayout.LayoutParams) verticalLine.getLayoutParams();

                Log.d(TAG, "width="+horizontalLine.getLeft());
                Log.d(TAG, "height="+verticalLine.getTop());

                int width = horizontalLine.getLeft();
                int height = verticalLine.getTop();

                windowmanager.removeView(globalview);
                if (globalview.getContext().getPackageManager().checkPermission(Manifest.permission.INJECT_EVENTS, globalview.getContext().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Runtime r = Runtime.getRuntime();
                        r.exec("su -c input tap " + width + " " + height);

                        if (Globale.engine.getServiceState()) {
                            listen(windowmanager, globalview);
                        }
                    } catch (IOException e) {
                        Toast.makeText(globalview.getContext(), "Permission not granted", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(globalview.getContext(), "Permission not granted", Toast.LENGTH_LONG).show();
                    listen(windowmanager, globalview);
                }


            }
        }
        return false;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        Solo solo = new Solo(new Instrumentation());
        solo.clickOnScreen(horizontalLine.getLeft(), verticalLine.getTop());
    }

}
