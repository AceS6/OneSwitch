package control.listener;

import com.robotium.solo.Solo;

import android.app.Instrumentation;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import pointing.line.MoveHorizontalLine;
import pointing.line.MoveVerticalLine;
import service.OneSwitchService;

public class OverlayTouchListener implements OnTouchListener, Runnable{

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

                Log.d(TAG, "width="+horizontalLine.getLeft());
                Log.d(TAG, "height="+verticalLine.getTop());
                windowmanager.removeView(globalview);
                OneSwitchService.clickOnScreen(horizontalLine.getLeft(), verticalLine.getTop());
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
