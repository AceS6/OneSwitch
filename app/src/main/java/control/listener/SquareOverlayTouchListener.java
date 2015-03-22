package control.listener;

import android.app.Service;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import data.Globale;
import pointing.square.MoveHorizontalSquareHalf;
import pointing.square.MoveHorizontalSquareQuarter;
import pointing.square.MoveVerticalSquareHalf;
import pointing.square.MoveVerticalSquareQuarter;

public class SquareOverlayTouchListener implements OnTouchListener{

	private WindowManager windowmanager;
	private WindowManager.LayoutParams params;
	private View globalview;
	private View selection;
	private int state;
	private int iconSize, margin;
    private Service s;
	
	public final int HALF=1, QUARTER=2;;
	
	private final String TAG="OverlayTouchListener";
	
	private MoveVerticalSquareHalf verticalMove;
	private MoveHorizontalSquareHalf horizontalMove;
	
	private MoveVerticalSquareQuarter verticalMove_quarter;
	private MoveHorizontalSquareQuarter horizontalMove_quarter;
	
	public SquareOverlayTouchListener(WindowManager windowmanager, View globalview, WindowManager.LayoutParams params, View selection, int width, int height, Service s){
		this.windowmanager=windowmanager;
		this.globalview=globalview;
		this.selection=selection;
		this.params=params;
		this.s = s;
        windowmanager.addView(globalview, params);
		verticalMove=new MoveVerticalSquareHalf(selection, horizontalMove, 1, height/2);
		horizontalMove=new MoveHorizontalSquareHalf(selection, verticalMove, 1,width/2);
		horizontalMove.execute();
		
		this.iconSize=(int) (32*Globale.engine.getProfil().getDensity());
		margin=iconSize/2;
		this.state=HALF;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

}
