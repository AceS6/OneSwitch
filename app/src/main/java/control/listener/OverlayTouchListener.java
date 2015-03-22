package control.listener;

import android.app.Instrumentation;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.robotium.solo.Solo;

import data.actions.Click;
import pointing.line.MoveHorizontalLine;
import pointing.line.MoveVerticalLine;

public class OverlayTouchListener implements View.OnTouchListener,GestureDetector.OnGestureListener, Runnable{

    private GestureDetector gestureDetector;
    private Context c;
    private WindowManager windowmanager;
    private WindowManager.LayoutParams params;
    private View globalview;
    private View horizontalLine, verticalLine;
    private int state;
    private Service s;

    public final int HORIZONTALMOVE=1, VERTICALMOVE=2, END=3;

    private final String TAG="OverlayTouchListener";

    private MoveHorizontalLine horizontalMove;
    private MoveVerticalLine verticalMove;


    public OverlayTouchListener(Context c, WindowManager windowmanager, View globalview, WindowManager.LayoutParams params, View horizontalLine, View verticalLine, int width, int height, Service s){
        gestureDetector = new GestureDetector(c, this);
        this.c=c;
        this.s = s;
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
    public void run() {
        // TODO Auto-generated method stub
        Solo solo = new Solo(new Instrumentation());
        solo.clickOnScreen(horizontalLine.getLeft(), verticalLine.getTop());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {

        if(state == HORIZONTALMOVE)
            horizontalMove.cancel(true);
        if(state == VERTICALMOVE)
            verticalMove.cancel(true);

        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        // TODO Auto-generated method stub
        globalview.performClick();
        Log.d(TAG, "OnTouchButton");
        if (state == HORIZONTALMOVE) {
            state = VERTICALMOVE;
            verticalMove = new MoveVerticalLine(verticalLine);
            verticalMove.execute();
        } else if (state == VERTICALMOVE) {
            state = END;

            RelativeLayout.LayoutParams paramsH = (RelativeLayout.LayoutParams) horizontalLine.getLayoutParams();
            RelativeLayout.LayoutParams paramsV = (RelativeLayout.LayoutParams) verticalLine.getLayoutParams();

            Log.d(TAG, "width=" + horizontalLine.getLeft());
            Log.d(TAG, "height=" + verticalLine.getTop());

            int width = horizontalLine.getLeft();
            int height = verticalLine.getTop();

            new Click(c,windowmanager, globalview, s).execute(width, height, Click.SHORT);

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
    public void onLongPress(MotionEvent motionEvent) {

        // TODO Auto-generated method stub
        globalview.performClick();
        Log.d(TAG, "OnLongTouchButton");
        if (state == HORIZONTALMOVE) {
            state = VERTICALMOVE;
            verticalMove = new MoveVerticalLine(verticalLine);
            verticalMove.execute();
        } else if (state == VERTICALMOVE) {
            state = END;

            RelativeLayout.LayoutParams paramsH = (RelativeLayout.LayoutParams) horizontalLine.getLayoutParams();
            RelativeLayout.LayoutParams paramsV = (RelativeLayout.LayoutParams) verticalLine.getLayoutParams();

            Log.d(TAG, "width=" + horizontalLine.getLeft());
            Log.d(TAG, "height=" + verticalLine.getTop());

            int width = horizontalLine.getLeft();
            int height = verticalLine.getTop();

            new Click(c, windowmanager, globalview, s).execute(width, height, Click.LONG);

        }
    }

}
