package control.listener;

import pointing.square.MoveHorizontalSquareHalf;
import pointing.square.MoveHorizontalSquareQuarter;
import pointing.square.MoveVerticalSquareHalf;
import pointing.square.MoveVerticalSquareQuarter;
import data.Globale;

import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import com.projeta.oneswitch.R;

public class SquareOverlayTouchListener extends PointingSystem implements OnTouchListener{

	private WindowManager windowmanager;
	private WindowManager.LayoutParams params;
	private View globalview;
	private View selection;
	private int state;
	private int iconSize, margin;
	
	public final int HALF=1, QUARTER=2;;
	
	private final String TAG="OverlayTouchListener";
	
	private MoveVerticalSquareHalf verticalMove;
	private MoveHorizontalSquareHalf horizontalMove;
	
	private MoveVerticalSquareQuarter verticalMove_quarter;
	private MoveHorizontalSquareQuarter horizontalMove_quarter;
	
	public SquareOverlayTouchListener(WindowManager windowmanager, View globalview, WindowManager.LayoutParams params, View selection, int width, int height){
		this.windowmanager=windowmanager;
		this.globalview=globalview;
		this.selection=selection;
		this.params=params;
		
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
		//verticalMove.pause();
		v.performClick();	
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN)
		{
			if(this.state==this.HALF){
				horizontalMove.cancel(true);
				verticalMove.cancel(true);
				RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) selection.getLayoutParams();
				int margin = params.leftMargin;
				params.width=params.width/2;
				params.height=params.height/2;
				params.leftMargin=margin;
				selection.setLayoutParams(params);
				
				verticalMove_quarter = new MoveVerticalSquareQuarter(selection, horizontalMove_quarter, 1);
				horizontalMove_quarter = new MoveHorizontalSquareQuarter(selection, verticalMove_quarter, 1);
	
				horizontalMove_quarter.execute();
				this.state=QUARTER;
			}
			else if(this.state==QUARTER){
                windowmanager.removeView(globalview);
                //OneSwitchService.clickOnScreen(horizontalLine.getLeft(), verticalLine.getTop());
                if(Globale.engine.getServiceState()) {
                    listen(windowmanager, globalview);
                }
			}
		}
		return false;
	}

}
